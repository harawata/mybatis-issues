package com.example

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

/**
 *
 * @author : nguyentthai96 - nguyentthai96@gmail.com
 * @created : 19/10/2022, Wednesday
 * @last_modified :  19/10/2022, Wednesday
 * @reference : https://stackoverflow.com/questions/61086749/spring-batch-unable-to-create-metadata-tables-on-postgres-and-load-actual-data
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(
    basePackages = ["com/example/mapper"],
    annotationClass = Mapper::class,
    sqlSessionFactoryRef = "appsSqlSessionFactory"
)
class AppMybatisConfiguration : MybatisDbConfig {


    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    // no so @Primary
    @Bean("appDataSource")
    @ConfigurationProperties(prefix = GlobalConstants.APPLICATION_DB_NAMESPACE_CONFIG)
    override fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["appsSqlSessionFactory"])
    override fun sqlSessionFactory(@Qualifier("appDataSource") dataSource: DataSource): SqlSessionFactory {
        val sqlSessionFactory = SqlSessionFactoryBean()
        sqlSessionFactory.setDataSource(dataSource)
//        org.springframework.boot.autoconfigure.r2dbc
//        io.r2dbc.spi.ValidationDepth
        sqlSessionFactory.setTypeAliasesPackage("com.example.*")
        sqlSessionFactory.setMapperLocations(*applicationContext.getResources("classpath:mybatis/mapper/*.xml"))
        // NTT optimize set into config
        sqlSessionFactory.setConfigLocation(resourceLoader.getResource("classpath:mybatis/mybatis-config.xml"))

        return sqlSessionFactory.`object`!!
    }

    @Bean(name = ["appsSqlSessionTemplate"])
    override fun sessionTemplate(@Qualifier("appsSqlSessionFactory") sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

}
