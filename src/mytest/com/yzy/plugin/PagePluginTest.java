package com.yzy.plugin;

import com.yzy.mapper.UserMapper;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import org.junit.Test;

import java.io.InputStream;

public class PagePluginTest {

  static SqlSession sqlSession;
  static Configuration configuration;

  static {
    String resource = "com/yzy/config/mybatis-config.xml";
    try {
      InputStream inputStream = Resources.getResourceAsStream(resource);
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      sqlSession = sqlSessionFactory.openSession();
      configuration = sqlSession.getConfiguration();
    }catch (Exception ignored){}


  }




  @Test
  public void pageTest(){

    MapperRegistry mapperRegistry=new MapperRegistry(configuration);
    mapperRegistry.addMapper(UserMapper.class);
    UserMapper mapper = mapperRegistry.getMapper(UserMapper.class, sqlSession);
    System.out.println(mapper.pageTest(new Page(1, 10), 3, "aa"));
  }

}
