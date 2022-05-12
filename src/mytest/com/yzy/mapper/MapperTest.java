package com.yzy.mapper;

import com.mysql.cj.jdbc.Driver;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


public class MapperTest {

  public static void main(String[] args) {
  }


  @Test
  public void  testByXml() throws IOException {
    String resource = "com/yzy/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
//    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//    UserMapper2 mapper2 = sqlSession.getMapper(UserMapper2.class);
//    mapper.selectALl();
//    mapper2.selectCacheTest();
    sqlSession.selectList("select  * from user");
//    sqlSession.select();
    System.out.println(sqlSession);
  }

}
