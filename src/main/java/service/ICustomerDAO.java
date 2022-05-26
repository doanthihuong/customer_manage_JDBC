package service;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDAO {
    //        viết tắt của :  data access object
    public void add(Customer customer) throws SQLException;

    public int findIndexById(int id);

    public List<Customer> findByName(String key) throws SQLException;

    public Customer findById(int id) throws SQLException;

    public List<Customer> findAll();

    public boolean delete(int id) throws SQLException;

    public void update(int id, Customer customer) throws SQLException;
}
