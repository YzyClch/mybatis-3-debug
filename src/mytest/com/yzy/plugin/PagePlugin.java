package com.yzy.plugin;


import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.Alias;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@Alias("pagePlugin")
public class PagePlugin implements Interceptor {


  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof StatementHandler){
      System.out.println("target被代理了");
      return new StatementHandler() {
        @Override
        public Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException {
          BoundSql boundSql = ((StatementHandler) target).getBoundSql();
          String sql = boundSql.getSql();
          System.out.println(sql);
          return ((StatementHandler) target).prepare(connection, transactionTimeout);
        }

        @Override
        public void parameterize(Statement statement) throws SQLException {
          ((StatementHandler) target).parameterize(statement);
        }

        @Override
        public void batch(Statement statement) throws SQLException {
          ((StatementHandler) target).batch(statement);
        }

        @Override
        public int update(Statement statement) throws SQLException {
          return ((StatementHandler) target).update(statement);
        }

        @Override
        public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
          return ((StatementHandler) target).query(statement, resultHandler);
        }

        @Override
        public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
          return ((StatementHandler) target).queryCursor(statement);
        }

        @Override
        public BoundSql getBoundSql() {
          return ((StatementHandler) target).getBoundSql();
        }

        @Override
        public ParameterHandler getParameterHandler() {
          return ((StatementHandler) target).getParameterHandler();
        }
      };
    }
  else return target;
  }
}
