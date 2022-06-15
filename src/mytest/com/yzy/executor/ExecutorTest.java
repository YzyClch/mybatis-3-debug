package com.yzy.executor;


import com.yzy.param.QueryParam;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ExecutorTest {


  static Configuration configuration;
  static Environment environment;
  static Transaction tx;

  @Before
  public void init() throws IOException {

    String resource = "com/yzy/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    configuration = new XMLConfigBuilder(inputStream).parse();
    environment = configuration.getEnvironment();

    final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
    // 调用事务工厂创建Transaction对象
    tx = transactionFactory.newTransaction(environment.getDataSource(), null, true);
  }

  private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
    if (environment == null || environment.getTransactionFactory() == null) {
      return new ManagedTransactionFactory();
    }
    return environment.getTransactionFactory();
  }

  @Test
  public void simpleExecutorTest() throws SQLException {

    SimpleExecutor simpleExecutor = new SimpleExecutor(configuration,tx);
    MappedStatement mappedStatement = configuration.getMappedStatement("selectUserList");
    QueryParam param = new QueryParam();
    param.setName("小");
    param.setId(10);
    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    objectObjectHashMap.put("ParamAnnotationValue",param);
    BoundSql boundSql = mappedStatement.getBoundSql(objectObjectHashMap);
    List<Object> list = simpleExecutor.doQuery(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);
    List<Object> list2 = simpleExecutor.doQuery(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);

    list.forEach(System.out::println);
  }

  /**
   * 原理
   * 把所有的statement都存放到了statementList中
   * 在doUpdate()时 调用 Statement 的 addBatch，
   * 在commit()时调用 Statement 的 executeBatch
   * @throws SQLException
   */
  @Test
  public void batchExecutorTest() throws SQLException {
    BatchExecutor batchExecutor = new BatchExecutor(configuration, tx);

    MappedStatement mappedStatement = configuration.getMappedStatement("updateUser");

    QueryParam param = new QueryParam();
    param.setName("修改11");
    param.setId(1);
    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    objectObjectHashMap.put("ParamAnnotationValue",param);

    MappedStatement mappedStatement2 = configuration.getMappedStatement("updateUser");
    QueryParam param2 = new QueryParam();
    param2.setName("修改22");
    param2.setId(2);
    HashMap<Object, Object> objectObjectHashMap2 = new HashMap<>();
    objectObjectHashMap2.put("ParamAnnotationValue",param2);

    batchExecutor.doUpdate(mappedStatement,objectObjectHashMap);
    batchExecutor.doUpdate(mappedStatement2,objectObjectHashMap2);

    batchExecutor.commit(false);
  }


  /**
   * ReuseExecutor会缓存 Statement，如果sql一致，可以重用就不用每次都新建Statement
   * @throws SQLException
   */
  @Test
  public void reuseExecutor() throws SQLException {
    ReuseExecutor reuseExecutor = new ReuseExecutor(configuration, tx);

    MappedStatement mappedStatement = configuration.getMappedStatement("selectUserList");
    QueryParam param = new QueryParam();
    param.setName("小");
    param.setId(10);
    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    objectObjectHashMap.put("ParamAnnotationValue",param);
    BoundSql boundSql = mappedStatement.getBoundSql(objectObjectHashMap);
    List<Object> list = reuseExecutor.doQuery(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);
    List<Object> list2 = reuseExecutor.doQuery(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);

  }

  @Test
  public void CachingExecutorTest() throws SQLException {
    CachingExecutor cachingExecutor = new CachingExecutor(new SimpleExecutor(configuration, tx));
    QueryParam param = new QueryParam();
//    param.setName("小");
    param.setId(1);
    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    objectObjectHashMap.put("ParamAnnotationValue",param);
    MappedStatement mappedStatement = configuration.getMappedStatement("selectUserList");
    BoundSql boundSql = mappedStatement.getBoundSql(objectObjectHashMap);
    //初次调用加入一级、二级缓存
    List<Object> list = cachingExecutor.query(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    cachingExecutor.commit(false);
    //第二次查询二级缓存
    List<Object> list2 = cachingExecutor.query(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    System.out.println(list2);
  }












}
