package com.yzy.mapper;

import com.mysql.cj.jdbc.Driver;
import com.yzy.domain.User;
import com.yzy.param.QueryParam;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class MapperTest {

  public static void main(String[] args) {
  }


  static Configuration configuration;


  @Before
  public void init() throws IOException {

    String resource = "com/yzy/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    configuration = new XMLConfigBuilder(inputStream).parse();

  }

  private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
    if (environment == null || environment.getTransactionFactory() == null) {
      return new ManagedTransactionFactory();
    }
    return environment.getTransactionFactory();
  }

  @Test
  public void simpleExecutorTest() throws SQLException {
    final Environment environment = configuration.getEnvironment();
    final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
    // 调用事务工厂创建Transaction对象
    Transaction tx = transactionFactory.newTransaction(environment.getDataSource(), null, true);
    SimpleExecutor simpleExecutor = new SimpleExecutor(configuration,tx);
    MappedStatement mappedStatement = configuration.getMappedStatement("selectUserList");
    QueryParam param = new QueryParam();
    param.setName("小");
    param.setId(10);
    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    objectObjectHashMap.put("ParamAnnotationValue",param);
    BoundSql boundSql = mappedStatement.getBoundSql(objectObjectHashMap);
    List<Object> list = simpleExecutor.doQuery(mappedStatement, objectObjectHashMap, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);
    list.forEach(System.out::println);
  }


  @Test
  public void  testByXml() throws IOException {
    String resource = "com/yzy/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    QueryParam queryParam = new QueryParam();
    queryParam.setId(null);
    queryParam.setName("小");
    List<User> users = mapper.selectUserList(queryParam);
    users.forEach(System.out::println);
//    sqlSession.selectList()
  }

}
