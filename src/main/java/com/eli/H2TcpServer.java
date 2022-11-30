package com.eli;

import org.h2.tools.Server;

import java.sql.*;

/**
 * h2数据库服务器
 */
public class H2TcpServer {
    private Server server;
    //数据库端口
    private String port = "8090";
    //数据库目录
    //数据库文件是当前目录下的freebytes文件，这里用的是相对路径，也可以使用本地绝对路径，如d://test/freebytes
    private String dbDir = "./db/h2db;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=FALSE;IFEXISTS=false;MVCC=true";
    //数据库用户名
    private String user = "test";
    //数据库密码
    private String password = "test";

    public void startH2Server() {
        try {
            System.out.println("启动h2...");
            server = Server.createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", port}).start();
        } catch (SQLException e) {
            System.out.println("启动h2出错：" + e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        H2TcpServer h2 = new H2TcpServer();
        h2.startH2Server();
        //测试h2数据库
        System.out.println("fuck");
//        h2.useH2();
        //停止h2数据库服务
        //h2.stopH2Server();
    }

    public void stopH2Server() {
        if (server != null) {
            System.out.println("关闭h2...");
            server.stop();
            System.out.println("关闭成功.");
        }
    }

    public void useH2() {
        try {
            String url = "jdbc:h2:tcp://localhost:" + port + "/" + dbDir;
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stat = conn.createStatement();
            // insert data
            stat.execute("DROP TABLE IF EXISTS TEST");
            stat.execute("CREATE TABLE TEST(NAME VARCHAR)");
            stat.execute("INSERT INTO TEST VALUES('freebytes.net')");
            // use data
            ResultSet result = stat.executeQuery("select name from test ");
            int i = 1;
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("name"));
            }
            result.close();
            stat.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
