package com.example

import javax.sql.DataSource

/**
 *
 * @author : nguyentthai96 - nguyentthai96@gmail.com
 * @created : 25/10/2022, Tuesday
 * @last_modified :  25/10/2022, Tuesday
 **/
interface DbConfig {
    fun dataSource() : DataSource
}
