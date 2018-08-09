package com.github.conf;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.github.cache.enums.ChannelTopicEnum;
import com.github.cache.layering.LayeringCacheManager;
import com.github.cache.setting.FirstCacheSetting;
import com.github.cache.setting.SecondaryCacheSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
* @author yuhao.wang
*/
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CaffeineCacheConfig extends CachingConfigurerSupport {

    // redis缓存的有效时间单位是秒
    @Value("${redis.default.expiration}")
    private long redisDefaultExpiration;

    // 查询缓存有效时间
    @Value("${select.cache.timeout}")
    private long selectCacheTimeout;
    // 查询缓存自动刷新时间
    @Value("${select.cache.refresh}")
    private long selectCacheRefresh;

    @Autowired
    private CacheProperties cacheProperties;

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();

        //使用fastjson序列化
//        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        KryoRedisSerializer serializer = new KryoRedisSerializer<>(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "redisTemplateSub")
    public RedisTemplate<Object, Object> redisTemplateSub(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();

        //使用fastjson序列化
//        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * RedisCacheManager 默认使用 defaultConfiguration, 使用自定义redisCacheConfiguration 必须进行设置
     * @param
     * @param
     * @return
     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory,RedisCacheConfiguration redisCacheConfiguration) {
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
//        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
//        return cacheManager;
//    }


    @Bean
    @Primary
    public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate,RedisTemplate<Object, Object> redisTemplateSub,RedisConnectionFactory factory,RedisCacheConfiguration redisCacheConfiguration) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
        LayeringCacheManager layeringCacheManager = new LayeringCacheManager(redisTemplate,redisTemplateSub,redisCacheConfiguration,redisCacheWriter);
        // Caffeine缓存设置
        setFirstCacheConfig(layeringCacheManager);

        // redis缓存设置
        setSecondaryCacheConfig(layeringCacheManager);

        // 允许存null，防止缓存击穿
        layeringCacheManager.setAllowNullValues(true);
        return layeringCacheManager;
    }

    /**
     *  设置@cacheable 序列化方式
     *  @cacheable 设置过期时间2小时
     *  @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
//        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        KryoRedisSerializer serializer = new KryoRedisSerializer<>(Object.class);

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();

        // 修改默认配置的序列化， 缓存空值，使用前缀等
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer)).entryTtl(Duration.ofHours(2));
        configuration = configuration.entryTtl(Duration.ofSeconds(redisDefaultExpiration));
        return configuration;
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListener) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener, ChannelTopicEnum.REDIS_CACHE_DELETE_TOPIC.getChannelTopic());
        container.addMessageListener(messageListener, ChannelTopicEnum.REDIS_CACHE_CLEAR_TOPIC.getChannelTopic());
        return container;
    }

    private void setFirstCacheConfig(LayeringCacheManager layeringCacheManager) {
        // 设置默认的一级缓存配置
        String specification = this.cacheProperties.getCaffeine().getSpec();
        if (StringUtils.hasText(specification)) {
            layeringCacheManager.setCaffeineSpec(CaffeineSpec.parse(specification));
        }

        // 设置每个一级缓存的过期时间和自动刷新时间
        Map<String, FirstCacheSetting> firstCacheSettings = new HashMap<>();
        firstCacheSettings.put("objectCache", new FirstCacheSetting("initialCapacity=5,maximumSize=5000,expireAfterWrite=10s"));
        layeringCacheManager.setFirstCacheSettings(firstCacheSettings);
    }

    private void setSecondaryCacheConfig(LayeringCacheManager layeringCacheManager) {
        // 设置使用缓存名称（value属性）作为redis缓存前缀
//        layeringCacheManager.setUsePrefix(true);
        //这里可以设置一个默认的过期时间 单位是秒
        layeringCacheManager.setSecondaryCacheDefaultExpiration(redisDefaultExpiration);

        // 设置每个二级缓存的过期时间和自动刷新时间
        Map<String, SecondaryCacheSetting> secondaryCacheSettings = new HashMap<>();
        secondaryCacheSettings.put("objectCache", new SecondaryCacheSetting(selectCacheTimeout, selectCacheRefresh));
//        secondaryCacheSettings.put("people1", new SecondaryCacheSetting(5, 0, false,true));
//        secondaryCacheSettings.put("people2", new SecondaryCacheSetting(false, selectCacheTimeout, selectCacheRefresh));
//        secondaryCacheSettings.put("people3", new SecondaryCacheSetting(selectCacheTimeout, selectCacheRefresh, false, true));
        layeringCacheManager.setSecondaryCacheSettings(secondaryCacheSettings);
    }

//    /**
//     * 显示声明缓存key生成器
//     *
//     * @return
//     */
//    @Bean
//    public KeyGenerator keyGenerator() {
//
//        return new SimpleKeyGenerator();
//    }
}
