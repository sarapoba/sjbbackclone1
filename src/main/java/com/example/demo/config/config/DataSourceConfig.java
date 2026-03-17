package com.example.demo.config.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    // 이 어노테이션을 쓰면 yml 파일 밑에있는 모든 설정을 한 번에 불러와서 설정 가능
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Bean
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @ConfigurationProperties(prefix = "spring.datasource.slave")
    @Bean
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 명시한 객체가 잘 Bean으로 등록되면
    @DependsOn({"masterDataSource", "slaveDataSource"})
    @Bean
    public DataSource routingDataSource(
            @Qualifier("masterDataSource") DataSource master,
            @Qualifier("slaveDataSource")DataSource slave
    ){
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("MASTER", master);
        dataSourceMap.put("SLAVE", slave);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(master);

        return routingDataSource;
    }

    @DependsOn({"routingDataSource"})
    // 똑같은 타입의 스프링 Bean이 있을 때 최우선으로 해당 Bean을 사용하게 하는 어노테이션
    @Primary
    @Bean
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}