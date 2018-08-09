package com.github.service;

import com.github.Application;
import com.github.model.CourseType;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
* 测试
* Created by leolin on 12/27/2017.
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)  // 指向到 根目录下的Application
public class CourseTypeTest {

    @Autowired
    private CourseTypeService courseTypeService;

    @Resource
    private RedisTemplate redisTemplate;


    @Test
    public void test34114(){
        redisTemplate.delete(redisTemplate.keys("objectCache*"));
    }

    /**
     * 插入分片测试
     */
    @Test
    public void save(){
        Integer flag = 300;
        Integer status = 200;
        for(int i = 1 ;i< 20 ; i++) {
            CourseType courseType = new CourseType();
            courseType.setTypeName(i);
            courseType.setStatus(status ++);
            courseTypeService.insert(courseType);
            System.out.println(courseType.toString());

//            for(int j=0; j < 4;j++ ){
//                Course course = new Course();
//                course.setCourseId(courseType.getId());
//                course.setCourseName("course_"+i+"_"+j);
//                course.setIddb01(i);
//                course.setIdtb02(j);
//                courseService.add(course);
//                System.out.println(course.toString());
//            }

            CourseType courseType2 = new CourseType();
            courseType2.setTypeName(i);
            courseType2.setStatus(status ++);
            courseTypeService.insert(courseType2);

        }
    }


    /**
     * 查询分片测试
     */
    @Test
    public void testQueryPage(){
        CourseType courseType = new CourseType();

//        List<CourseType> list1 = courseTypeService.selectTypeByCondition(courseType,1,50);
        List<CourseType> list2 = courseTypeService.selectTypeByCondition(courseType,1,50);


        List<CourseType> list3 = courseTypeService.upTypeByCondition(courseType,1,50);
//        CacheManager ;

        for(CourseType ct : list3) {
            System.out.println(ct.toString());
        }
//        System.out.println(list2.toString());

    }

    @Test
    public void testQueryPage2(){
        System.out.println(System.currentTimeMillis());
        CourseType courseType = new CourseType();
        List<CourseType> list1 = courseTypeService.selectTypeByCondition2(courseType, 1, 2);
        System.out.println(list1.toString());
        System.out.println(System.currentTimeMillis());

        List<CourseType> list2 = courseTypeService.selectTypeByCondition2(courseType, 1, 2);


        System.out.println(list2.toString());
        System.out.println(System.currentTimeMillis());
    }


    @Test
    public void test344(){
        courseTypeService.addDatas();
    }


    @Test
    public void testRedisExecute(){
        String lockKey = "123";
        String lockValue = "123";
        String UNLOCK_LUA;

        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();

        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String result = null;

                Object[] keys = new Object[]{lockKey.getBytes()};

                if (nativeConnection instanceof RedisAsyncCommands) {
                    RedisAsyncCommands commands = ((RedisAsyncCommands) nativeConnection).getStatefulConnection().async();
//                    RedisFuture future = commands.del(lockKey.getBytes());
                    // lua 函数返回的值为数值类型。
                    RedisFuture future = commands.eval(UNLOCK_LUA, ScriptOutputType.INTEGER, keys, lockValue.getBytes());

                    future.handle(new BiFunction<String, Throwable, String>() {
                        @Override
                        public String apply(String value, Throwable throwable) {
                            if(throwable != null) {
                                return "default value";
                            }
                            return value;
                        }
                    }).thenAccept(new Consumer<String>() {
                        @Override
                        public void accept(String value) {
                            System.out.println("Got value: " + value);
                        }
                    });

                    result = future.getError();
                    Object obj = null;
                    try {
                        obj = future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    // 直接删除key的值。
                    //commands.del(lockKey);
                }
                return false;
            }
        });
    }
}
