import java.math.BigDecimal;
import java.sql.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InsertBatch {

    private static final String URL = "jdbc:mysql://localhost:3306/shop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /**
         * 创建一千个用户
         * 创建一万个商品
         * 只创建一次
         */
        //createUser();
        //createGood();

        //创建订单 statement.execute                            10万：1秒 , 100万：7秒
//        createOrder1();
        //创建订单 preparedStatement.execute                    10万：1秒 , 100万：13秒
//        createOrder2();
        //创建订单 preparedStatement.executeLargeBatch          10万：188秒, 100万：1835秒
        createOrder3();
    }

    /**
     * 创建订单
     * Statement execute 执行
     */
    private static void createOrder1() {
        long start = System.currentTimeMillis();
        String sql = "INSERT INTO t_order (user_id,total,status,update_time,create_time) VALUES ";
        sql = IntStream.rangeClosed(1, 1_000_000)
                .mapToObj(i ->
                        Stream.of(
                                String.valueOf(i),
                                String.valueOf(i),
                                "0",
                                String.valueOf(System.currentTimeMillis()),
                                "update_time")
                                .collect(Collectors.joining(",", "(", ")")))
                .collect(Collectors.joining(",", sql, ""));
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1_000);
    }

    /**
     * 创建订单
     * preparedStatement execute
     */
    private static void createOrder2() {
        long start = System.currentTimeMillis();
        String sql = "INSERT INTO t_order (user_id,total,status,update_time,create_time) VALUES ";
        sql = IntStream.rangeClosed(1, 1_000_000)
                .mapToObj(i -> "(?,?,?,?,?)")
                .collect(Collectors.joining(",", sql, ""));
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1_000_000; i++) {
                int parameterIndex = i * 5 + 1;
                //user_id
                preparedStatement.setLong(parameterIndex++, i);
                //total
                preparedStatement.setBigDecimal(parameterIndex++, BigDecimal.valueOf(i));
                //staus
                preparedStatement.setInt(parameterIndex++, 0);
                final long currentTimeMillis = System.currentTimeMillis();
                //update_time
                preparedStatement.setLong(parameterIndex++, currentTimeMillis);
                //create_time
                preparedStatement.setLong(parameterIndex++, currentTimeMillis);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1_000);
    }

    /**
     * 创建订单 preparedStatement  executeBatch
     */
    private static void createOrder3() {
        long start = System.currentTimeMillis();
        String sql = "INSERT INTO t_order (user_id,total,status,update_time,create_time) VALUES (?,?,?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < 1_00_000; i++) {
                int parameterIndex = 1;
                //user_id
                preparedStatement.setLong(parameterIndex++, i);
                //total
                preparedStatement.setBigDecimal(parameterIndex++, BigDecimal.valueOf(i));
                //staus
                preparedStatement.setInt(parameterIndex++, 0);
                final long currentTimeMillis = System.currentTimeMillis();
                //update_time
                preparedStatement.setLong(parameterIndex++, currentTimeMillis);
                //create_time
                preparedStatement.setLong(parameterIndex++, currentTimeMillis);
                preparedStatement.addBatch();
            }
            preparedStatement.executeLargeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1_000);
    }

    /**
     * 创建10000个商品
     */
    private static void createGood() {
        String sql = "INSERT INTO t_good (name,detail,pice,stock,update_time,create_time,status) VALUES ";
        sql = IntStream.rangeClosed(1, 10_000)
                .mapToObj(i ->
                        Stream.of(
                                "\"商品" + i + "\"",
                                "\"商品" + i + "描述\"",
                                String.valueOf(i),
                                "1000",
                                String.valueOf(System.currentTimeMillis()),
                                "update_time",
                                "0")
                                .collect(Collectors.joining(",", "(", ")")))
                .collect(Collectors.joining(",", sql, ""));
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建1000用户
     */
    private static void createUser() {
        String sql = "INSERT INTO t_user (user_name,password,nick_name,update_time,create_time,status) VALUES ";
        sql = IntStream.rangeClosed(1, 1_000)
                .mapToObj(i ->
                        Stream.of(
                                "\"用户" + i + "\"",
                                "\"" + i + "\"",
                                "\"用户昵称" + i + "\"",
                                String.valueOf(System.currentTimeMillis()),
                                "update_time",
                                "0")
                                .collect(Collectors.joining(",", "(", ")")))
                .collect(Collectors.joining(",", sql, ""));
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取链接
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
