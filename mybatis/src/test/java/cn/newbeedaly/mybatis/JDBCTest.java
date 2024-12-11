package cn.newbeedaly.mybatis;

import java.sql.*;

public class JDBCTest {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 加载 JDBC 驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            connection = DriverManager.getConnection("jdbc:mysql://www.newbeedaly.com:3306/mybatis?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "root", "123456");

            // 创建 SQL 语句
            String sql = "select id,user_name,real_name,mobile,birthday from t_user_info";

            // PreparedStatement prepareStatement = connection.prepareStatement("select * from t_user_info where id = #{id}");
            // int i = 1;
            // prepareStatement.setInt(i, 1);
            // ResultSet rs = prepareStatement.executeQuery();
            // 创建 Statement 对象
            statement = connection.createStatement();

            // 执行查询操作
            resultSet = statement.executeQuery(sql);

            // 处理查询结果
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String userName = resultSet.getString("user_name");
                String realName = resultSet.getString("real_name");
                String mobile = resultSet.getString("mobile");
                Date birthday = resultSet.getDate("birthday");
                System.out.println("id: " + id + ", userName: " + userName + ", realName: " + realName +
                        ", mobile: " + mobile + ", birthday: " + birthday);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭数据库连接
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
