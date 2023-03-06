package com.example

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionTemplate
import javax.sql.DataSource

/**
 * @author : nguyentthai96 - nguyentthai96@gmail.com
 * @created : 25/10/2022, Tuesday
 * @last_modified :  25/10/2022, Tuesday
 **/
interface MybatisDbConfig : DbConfig {
    fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory
    fun sessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate
}
