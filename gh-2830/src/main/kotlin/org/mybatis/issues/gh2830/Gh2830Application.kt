package org.mybatis.issues.gh2830

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.mybatis.spring.annotation.MapperScan
import org.apache.ibatis.annotations.Mapper

@SpringBootApplication
@MapperScan("com.example.mapper")
class Gh2830Application

fun main(args: Array<String>) {
  runApplication<Gh2830Application>(*args)
}
