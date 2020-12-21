package com.example.config;

import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig {

    @Bean("ssDataSource")
    public DataSource dataSource() {
        DataSource masterDataSource = masterDataSource();
        DataSource slaveDataSource = slaveDataSource();
        HashMap<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slave", slaveDataSource);
        try {
            ReplicaQueryDataSourceRuleConfiguration replicaQueryDataSourceRuleConfiguration =
                    new ReplicaQueryDataSourceRuleConfiguration(
                            "name",
                            "master",
                            Collections.singletonList("slave"),
                            "load_balancer");
            ReplicaQueryRuleConfiguration ruleConfiguration = new ReplicaQueryRuleConfiguration(
                    Collections.singletonList(replicaQueryDataSourceRuleConfiguration),
                    Collections.emptyMap());

            Properties properties = new Properties();
            properties.put("sql.show", "true");
            return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(ruleConfiguration), properties);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate masterJdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
