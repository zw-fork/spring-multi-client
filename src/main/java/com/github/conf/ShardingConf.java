//package com.github.util;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
//import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
//import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
//import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
//import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
//import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
//import com.springboot.sharding.CourseTypeSingleKeyDatabaseShardingAlgorithm;
//import com.springboot.sharding.CourseTypeSingleKeyTableShardingAlgorithm;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.*;
//
///**
//* Created by leolin on 4/23/2018.
//*/
//@Configuration
//public class ShardingConf {
//
//    @Bean(initMethod="init", destroyMethod="close", name="dataSource0")
//    @Qualifier("dataSource0")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource0(){
//        return new DruidDataSourceBuilder().create().build();
//    }
//
//    @Bean(initMethod="init", destroyMethod="close", name="dataSource1")
//    @Qualifier("dataSource1")
//    @ConfigurationProperties(prefix = "cluster.datasource")
//    public DataSource dataSource1(){
//        return new DruidDataSourceBuilder().create().build();
//    }
//
//    @Bean
//    public DataSourceRule dataSourceRule(@Qualifier("dataSource0") DataSource dataSource0,
//                                         @Qualifier("dataSource1") DataSource dataSource1){
//
//        // 可以配置多套个主从。  多个主之间写分片，多个从之间做数据读取。 目前适应业务的最好方式是2套主从。
//        // 目前配置一套主从。
//        DataSource masterSlaveDs0 = MasterSlaveDataSourceFactory.createDataSource("ms_0",
//                dataSource0, dataSource1);
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("ms_0", masterSlaveDs0);
//        return new DataSourceRule(dataSourceMap,"ms_0");
//    }
//
//    @Bean
//    public ShardingRule shardingRule(DataSourceRule dataSourceRule){
//        //表策略
//        TableRule orderTableRule = TableRule.builder("t_course_type").generateKeyColumn("id")
//                .actualTables(Arrays.asList("t_course_type_0", "t_course_type_1"))
//                .tableShardingStrategy(new TableShardingStrategy("status", new CourseTypeSingleKeyTableShardingAlgorithm()))
//                .dataSourceRule(dataSourceRule)
//                .build();
//
////        TableRule courseTableRule = TableRule.builder("t_course").generateKeyColumn("id")
////                .actualTables(Arrays.asList("t_course_0", "t_course_1"))
////                .tableShardingStrategy(new TableShardingStrategy("idtb02", new CourseTypeSingleKeyTableShardingAlgorithm()))
////                .dataSourceRule(dataSourceRule)
////                .build();
//
//        //绑定表策略，在查询时会使用主表策略计算路由的数据源，因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
//        List<BindingTableRule> bindingTableRules = new ArrayList<BindingTableRule>();
//        bindingTableRules.add(new BindingTableRule(Arrays.asList(orderTableRule)));
//        return ShardingRule.builder()
//                .dataSourceRule(dataSourceRule)
//                .tableRules(Arrays.asList(orderTableRule))
//                .bindingTableRules(bindingTableRules)
//                .databaseShardingStrategy(new DatabaseShardingStrategy("type_name", new CourseTypeSingleKeyDatabaseShardingAlgorithm()))
//                .tableShardingStrategy(new TableShardingStrategy("status", new CourseTypeSingleKeyTableShardingAlgorithm()))
//                .build();
//    }
//
//
//    @Bean("dataSource")
//    public DataSource shardingDataSource(ShardingRule shardingRule){
//        return ShardingDataSourceFactory.createDataSource(shardingRule);
//    }
//
//    //需要手动声明配置事务
//    @Bean
//    public DataSourceTransactionManager transactitonManager(@Qualifier("dataSource") DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }
////
////    @Bean
////    public DataSource getDataSource() {
////        return buildDataSource();
////    }
////
////    private DataSource buildDataSource() {
////        //设置分库映射
////        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
////        //添加两个数据库ds_0,ds_1到map里
////        dataSourceMap.put("ds_0", createDataSource("ds_0"));
////        dataSourceMap.put("ds_1", createDataSource("ds_1"));
////        //设置默认db为ds_0，也就是为那些没有配置分库分表策略的指定的默认库
////        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库，但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
////        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap,"ds_0");
////
////        //设置分表映射，将t_order_0和t_order_1两个实际的表映射到t_order逻辑表
////        //0和1两个表是真实的表，t_order是个虚拟不存在的表，只是供使用。如查询所有数据就是select * from t_order就能查完0和1表的
////        TableRule orderTableRule = TableRule.builder("t_order")
////                .actualTables(Arrays.asList("t_order_0", "t_order_1"))
////                .dataSourceRule(dataSourceRule)
////                .build();
////
////        //具体分库分表策略，按什么规则来分
////        ShardingRule shardingRule = ShardingRule.builder()
////                .dataSourceRule(dataSourceRule)
////                .tableRules(Arrays.asList(orderTableRule))
////                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new CourseTypeSingleKeyDatabaseShardingAlgorithm()))
////                .tableShardingStrategy(new TableShardingStrategy("order_id", new CourseTypeSingleKeyTableShardingAlgorithm())).build();
////
////        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
////
////        return dataSource;
////    }
////
////    private static DataSource createDataSource(final String dataSourceName) {
////        //使用druid连接数据库
////        DruidDataSource result = new DruidDataSource();
////        result.setDriverClassName(Driver.class.getName());
////        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
////        result.setUsername("root");
////        result.setPassword("");
////        return result;
////    }
//
//}
