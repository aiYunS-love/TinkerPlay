package com.baiyx.tinkerplay.Other.Play;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserImplFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/*
    数据库
    1、数据一旦提交，就不可回滚
    2、哪些数据会导致数据的自动提交
        >DDL(create,alter,drop,truncate)操作一旦执行，都会自动提交
        ->set autocommit =false  对DDL操作无效
        >DML(insert,update,delete)默认的情况下，就会自动提交
        ->我们可以通过set autocommit =false 的方式取消DML操作的自动提交
        >默认在关闭链接时，会自动提交数据
 */
public class DB extends Thread {

    private static final String driverClass = "com.mysql.cj.jdbc.Driver";

    // 开发环境数据库
    private String developUrl = "jdbc:mysql://xxx.xxx.xx.xxx:3306/xxx?autoreconnect=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
    // 性能测试环境数据库
    private String performanceTestUrl = "jdbc:mysql://xxx.xxx.xx.xxx:3306/xxx?autoreconnect=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
    // 测试环境数据库
    private String sitUrl = "jdbc:mysql://xxx.xxx.xx.xxx:3306/xxx?autoreconnect=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
    // 准生产环境数据库
    private String uatUrl = "jdbc:mysql://xxx.xxx.xx.xxx:3306/xxx?autoreconnect=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
    // 生产环境数据库
    private String productionUrl = "jdbc:mysql://xxx.xxx.xx.xxx:3306/xxx?autoreconnect=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";

    private static final String username = "root";
    private static final String password = "root";

    private Connection connection;
    private java.sql.Statement statement;

    static HashSet<String> writeResault = new HashSet<>();

    private LinkedHashMap<String, ArrayList<String>> sqlMap;
    public static LinkedHashMap<String, String> errorInfo = new LinkedHashMap<>();

    public DB() {
    }

    public DB(String environment, LinkedHashMap<String, ArrayList<String>> sqlMap) {
        try {
            Class.forName(driverClass);
            switch (environment) {
                case "develop":
                    connection = DriverManager.getConnection(developUrl, username, password);
                    break;
                case "performanceTest":
                    connection = DriverManager.getConnection(performanceTestUrl, username, password);
                    break;
                case "sit":
                    connection = DriverManager.getConnection(sitUrl, username, password);
                    break;
                case "uat":
                    connection = DriverManager.getConnection(uatUrl, username, password);
                    break;
                case "prd":
                    connection = DriverManager.getConnection(productionUrl, username, password);
                    break;
            }
            // 关闭自动提交,开启事务
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            this.sqlMap = sqlMap;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean b = true;
        for (String key : sqlMap.keySet()) {
            ArrayList<String> arrsql = sqlMap.get(key);
            boolean isSuccess = true;
            for (String sql : arrsql) {
                try {
                    if ((sql.toLowerCase().contains("drop") && !sql.toLowerCase().replace(" ", "").contains("droptableifexists")) || (sql.toLowerCase().contains("delete") && !sql.toLowerCase().contains("deleted"))) {
                        rollback();
                        ;
                        isSuccess = false;
                        errorInfo.put(key + " ==> " + sql, "当前sql疑似存在危险的drop或delete语句, 请谨慎确认!!!");
                        break;
                    }
                    statement.execute(sql);
                } catch (SQLException throwables) {
                    isSuccess = false;
                    // 记录错误的sql语句
                    errorInfo.put(key, sql);
                    // 执行发生错误则回滚
                    rollback();
                    throwables.printStackTrace();
                    break;
                }
            }
            // 一个文件里面所有的SQL执行成功,则提交一次
            if (isSuccess) {
                try {
                    connection.commit();
                    writeResault.add(key);
                } catch (SQLException throwables) {
                    // 提交发生错误则回滚
                    rollback();
                    throwables.printStackTrace();
                }
            } else {
                b = isSuccess;
            }
        }
        // 关闭连接
        this.close();
        if (b) {
            System.out.println(this.getName() + " --> 子线程执行成功!");
        } else {
            System.out.println(this.getName() + " --> 子线程执行失败!");
        }
    }

    private void rollback() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean chenkSql(String sql) {
        try {
            SqlParser.Config config = SqlParser.config();
            SqlParserImplFactory factory = config.parserFactory();
            SqlParser parser = SqlParser.create(sql, config.withParserFactory(factory));
            SqlNode node = parser.parseStmt();
            return true;
        } catch (SqlParseException e) {
            return false;
        }
    }

    public static String analyzeSql(String sql) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Statement statement = CCJSqlParserUtil.parse(sql);
            if (statement instanceof Alter) {
                // 处理 ALTER TABLE 语句
                stringBuilder.append("新增: ");
                Alter alter = (Alter) statement;
                String tableName = alter.getTable().getName();
                List<AlterExpression> columnNames = alter.getAlterExpressions();
                if (columnNames != null && columnNames.size() > 0) {
                    for (int i = 0; i < columnNames.size(); i++) {
                        stringBuilder.append(columnNames.get(i).getColDataTypeList().get(0).getColumnName());
                        if (sql.contains("COMMENT") || sql.contains("comment")) {
                            stringBuilder.append("(");
                            List<String> columnSpecs = columnNames.get(i).getColDataTypeList().get(0).getColumnSpecs();
                            stringBuilder.append(columnSpecs.get(indexOfIgnoreCase(columnSpecs,"COMMENT") + 1));
                            stringBuilder.append(")");
                        }
                        if (i != columnNames.size() - 1) {
                            stringBuilder.append("、");
                        } else {
                            stringBuilder.append("，");
                        }
                    }
                }
                stringBuilder.append(columnNames.size());
                stringBuilder.append(" 个字段");
            } else if (statement instanceof CreateTable) {
                // 处理其他类型的 SQL 语句
                stringBuilder.append("新建: ");
                stringBuilder.append(((CreateTable) statement).getTable().getName());
                List<String> tableOptionsStrings = ((CreateTable) statement).getTableOptionsStrings();
                if (tableOptionsStrings.contains("COMMENT") || tableOptionsStrings.contains("comment")) {
                    stringBuilder.append("(");
                    stringBuilder.append(tableOptionsStrings.get(indexOfIgnoreCase(tableOptionsStrings,"comment") + 2));
                    stringBuilder.append(")");
                }
                stringBuilder.append(" 表");
            } else if (statement instanceof Update) {
                stringBuilder.append("更新: ");
                stringBuilder.append(((Update) statement).getTable().getName());
                stringBuilder.append("表的 ");
                ArrayList<UpdateSet> updateSet = ((Update) statement).getUpdateSets();
                for (int i = 0; i < updateSet.size(); i++) {
                    stringBuilder.append(updateSet.get(i).getColumns().get(0));
                    stringBuilder.append(", ");
                }
                stringBuilder.append(updateSet.size());
                stringBuilder.append("个字段");
            }
            return stringBuilder.toString();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int indexOfIgnoreCase(List<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1; // 如果未找到匹配项
    }
}
