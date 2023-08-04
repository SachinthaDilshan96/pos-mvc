/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import pos.mvc.model.CustomerModel;
import java.sql.Connection;
import pos.mvc.db.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author sachinthadilshan
 */
public class CustomerController {
    
    public String saveCustomer(CustomerModel customer) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        
        String query = "insert into customer values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customer.getCustId());
        preparedStatement.setString(2, customer.getTitle());
        preparedStatement.setString(3, customer.getName());
        preparedStatement.setString(4, customer.getDob());
        preparedStatement.setDouble(5, customer.getSalary());
        preparedStatement.setString(6, customer.getAddress());
        preparedStatement.setString(7, customer.getCity());
        preparedStatement.setString(8, customer.getProvince());
        preparedStatement.setString(9, customer.getZip());
        if (preparedStatement.executeUpdate()>0) {
            return "Success";
        }else{
            return "Fail";
        }
    }
    
    public ArrayList<CustomerModel> getAllCustomers() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "select * from customer";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rst = statement.executeQuery();
        ArrayList<CustomerModel> customerModels = new ArrayList<>();
        while(rst.next()){
            CustomerModel customerModel = new CustomerModel(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
            customerModels.add(customerModel);
        }
        return customerModels;
    }
    
   public CustomerModel getCustomer(String custId) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "select * from customer where custid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, custId);
        ResultSet rst = statement.executeQuery();
        while(rst.next()){
            return new CustomerModel(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        }
       return null;
   }
   
   public String updateCustomer(CustomerModel customerModel) throws SQLException{
       Connection connection = DBConnection.getInstance().getConnection();
       String query = "update customer set custtitle=?,custname=?,dob=?,salary=?,custaddress=?,city=?,province=?,postalcode=? where custid=?";
       PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customerModel.getTitle());
        preparedStatement.setString(2, customerModel.getName());
        preparedStatement.setString(3, customerModel.getDob());
        preparedStatement.setDouble(4, customerModel.getSalary());
        preparedStatement.setString(5, customerModel.getAddress());
        preparedStatement.setString(6, customerModel.getCity());
        preparedStatement.setString(7, customerModel.getProvince());
        preparedStatement.setString(8, customerModel.getZip());
        preparedStatement.setString(9, customerModel.getCustId());
        if (preparedStatement.executeUpdate()>0) {
            return "Success";
        }else{
            return "Fail";
        }
   }
   
   public String deleteCustomer(String custId) throws SQLException{
       Connection connection = DBConnection.getInstance().getConnection();
       String query = "delete from customer where CustID=?";
       PreparedStatement preparedStatement = connection.prepareCall(query);
        preparedStatement.setString(1, custId);
        if (preparedStatement.executeUpdate()>0) {
            return "Success";
        }else{
            return "Fail";
        }
        
   }
   
   
    
    
}
