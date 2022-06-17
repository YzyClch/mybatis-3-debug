package com.yzy.executor;
//import
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CacheTest {

//  Cache

  /**
   * 总结： 每次执行更新会清空一级缓存
   *        执行提交、回滚会清空一级缓存
   *        配置了flushCache 会清空一级缓存
   *        配置了 LocalCacheScope.STATEMENT 会清空一级缓存
   *        执行了update 会清空一级缓存
   *
   */
  static Configuration configuration;
  static Environment environment;
  static Transaction tx;
  private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
    if (environment == null || environment.getTransactionFactory() == null) {
      return new ManagedTransactionFactory();
    }
    return environment.getTransactionFactory();
  }
  @Before
  public void init() throws IOException {
    String resource = "com/yzy/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(inputStream);
    Configuration configuration = xmlConfigBuilder.getConfiguration();
    TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
//    typeAliasRegistry.registerAlias("MY",MyCache.class);
    CacheTest.configuration =xmlConfigBuilder .parse();
    environment = CacheTest.configuration.getEnvironment();

    final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
    // 调用事务工厂创建Transaction对象
    tx = transactionFactory.newTransaction(environment.getDataSource(), null, true);
  }




  @Test
  public void cacheTest(){
    TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
    Cache cache=configuration.getCache("com.yzy.mapper.UserMapper");
    cache.putObject("test","value");
    Object test = cache.getObject("test");
    System.out.println(test);
  }
}
