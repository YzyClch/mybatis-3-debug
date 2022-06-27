package com.yzy.sqlSource;


import com.yzy.domain.User;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;


public class SqlSourceTest {



  static Configuration configuration;
  static Environment environment;
  static Transaction tx;

  @Before
  public void init() throws IOException {

    String resource = "com/yzy/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    configuration = new XMLConfigBuilder(inputStream).parse();}

  @Test
  public void sqlSourceTest(){
    User u=new User();
    u.setId(100);

//    DynamicSqlSource sqlSource=new DynamicSqlSource()

    DynamicContext context= new DynamicContext(configuration, u);

    System.out.println(context.getSql());

    new StaticTextSqlNode("select * from user ").apply(context);

    new IfSqlNode(new StaticTextSqlNode("where id = 100"),"id == 100").apply(context);

    System.out.println(context.getSql());

  }




}
