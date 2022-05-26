package service;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {
    List<Customer> customers = new ArrayList<>();

    public CustomerDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo2006?useSSL=false", "root", "1234");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void add(Customer customer) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into customer(id,name,age)values (?,?,?)")) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setInt(3, customer.getAge());
//            dùng,excuteUpdate ,không cần ResultSet
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
        }
    }

    @Override
    public int findIndexById(int id) {
        return 0;
    }

    @Override
    public Customer findById(int id) throws SQLException {
        Customer customer = null;
        String query = "select * from Customer where id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idf = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                customer = new Customer(idf, name, age);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection();
//             gọi connection để lấy ra đối tượng  PreparedStatement
//             xây dựng cầu nối giữa jv với csdl
//             PreparedStatement : đối tượng đặc biệt để truy vấn đến insert,update,..
             PreparedStatement preparedStatement = connection.prepareStatement("select *from demo2006.customer")) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            //truy vấn trả về bảng thì cần ResultSet để hứng bảng.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                customers.add(new Customer(id, name, age));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi rồi");
        }
        return customers;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void update(int id, Customer customer) throws SQLException {
        String query = "UPDATE Customer set name = ?, age = ? where id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, String.valueOf(customer.getAge()));
            preparedStatement.setString(3, String.valueOf(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


