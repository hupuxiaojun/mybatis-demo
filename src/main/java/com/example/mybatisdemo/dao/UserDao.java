package com.example.mybatisdemo.dao;

import com.example.mybatisdemo.model.Users;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过sqlSession原生方式查询
 *
 * @author : xiaojun
 * @since 16:21 2018/11/15
 */
@Component
public class UserDao {


    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public UserDao(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public Users selectByPrimaryKey(String username) {
        return sqlSessionTemplate.selectOne("com.example.mybatisdemo.mapper.UsersMapper.selectByPrimaryKey", username);
    }


    //获取元数据只能通过jdbcTemplate.execute来获取，没法通过sqlSessionTemplate,因为sqlSessionTemplate.getConnection()被动态代理了，一获取连接就会关闭
    public Object getTablesMetadata() throws SQLException {
        return jdbcTemplate.execute(new ConnectionCallback<Object>() {
            @Nullable
            @Override
            public Object doInConnection(Connection con) throws SQLException, DataAccessException {
                //连接参数上配置useInformationSchema=true,使用DatabaseMetaDataUsingInfoSchema实现类,查询information_schema库中的tables表，否则没法拿到很全的信息
                DatabaseMetaData databaseMetaData = con.getMetaData();
                ResultSet rs = databaseMetaData.getTables("test1", null, null, null);
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                List<List<Object>> tables = new ArrayList<>();
                while (rs.next()) {
                    List<Object> tableInfo = new ArrayList<>();
                    for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                        tableInfo.add(rs.getObject(i + 1));
                    }
                    tables.add(tableInfo);
                }

                System.out.println(tables);
                return tables;
            }
        });

    }

    public Object getTablesMetadataFromSqlSession() throws SQLException {
        SqlSession sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
        Connection con = sqlSession.getConnection();
        //连接参数上配置useInformationSchema=true,使用DatabaseMetaDataUsingInfoSchema实现类,查询information_schema库中的tables表，否则没法拿到很全的信息
        DatabaseMetaData databaseMetaData = con.getMetaData();
        ResultSet rs = databaseMetaData.getTables("test1", null, null, null);
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        List<List<Object>> tables = new ArrayList<>();
        while (rs.next()) {
            List<Object> tableInfo = new ArrayList<>();
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                tableInfo.add(rs.getObject(i + 1));
            }
            tables.add(tableInfo);
        }
        SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
        System.out.println(tables);
        return tables;
    }


    public Object getTableColumns(String table) throws SQLException {
        return jdbcTemplate.execute(new ConnectionCallback<Object>() {
            @Nullable
            @Override
            public Object doInConnection(Connection con) throws SQLException, DataAccessException {
                //连接参数上配置useInformationSchema=true,使用DatabaseMetaDataUsingInfoSchema实现类,查询information_schema库中的tables表，否则没法拿到很全的信息
                DatabaseMetaData databaseMetaData = con.getMetaData();
                ResultSet rs = databaseMetaData.getColumns("test1", null, table, null);
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                List<List<Object>> columns = new ArrayList<>();
                while (rs.next()) {
                    List<Object> columnInfo = new ArrayList<>();
                    for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                        columnInfo.add(rs.getObject(i + 1));
                    }
                    columns.add(columnInfo);
                }

                System.out.println(columns);
                return columns;
            }
        });

    }


}
