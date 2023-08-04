/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import com.mysql.cj.xdevapi.PreparableStatement;
import java.util.ArrayList;
import pos.mvc.model.OrderDetailModel;
import pos.mvc.model.OrderModel;
import java.sql.Connection;
import pos.mvc.db.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sachinthadilshan
 */
public class OrderController {

    public String placeOrder(OrderModel orderModel, ArrayList<OrderDetailModel> orderDetailModels) throws SQLException {
            
        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            String orderQuery = "insert into orders values(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(orderQuery);
            statement.setString(1, orderModel.getOrderId());
            statement.setString(2, orderModel.getOrderDate());
            statement.setString(3, orderModel.getCustomerId());
            if(statement.executeUpdate()>0){
                boolean isOrderDetailSaved = true;
                String orderDetailQuery="insert into orderdetail values(?,?,?,?)";
                for(OrderDetailModel orderDetailModel:orderDetailModels){
                    PreparedStatement statementForOrderDetail = connection.prepareStatement(orderDetailQuery);
                    statementForOrderDetail.setString(1, orderDetailModel.getOrderId());
                    statementForOrderDetail.setString(2, orderDetailModel.getItemCode());
                    statementForOrderDetail.setInt(3, orderDetailModel.getQty());
                    statementForOrderDetail.setDouble(4, orderDetailModel.getDiscount());
                    
                    if(!(statementForOrderDetail.executeUpdate()>0)){
                        isOrderDetailSaved=false;
                    }
                    
                }
                if (isOrderDetailSaved) {
                    boolean isItemUpdated = true;
                    String itemQuery = "update item set qtyonhand=qtyonhand-? where itemcode = ?";
                    for(OrderDetailModel orderDetailModel:orderDetailModels){
                        PreparedStatement statementForItem = connection.prepareStatement(itemQuery);
                        statementForItem.setInt(1, orderDetailModel.getQty());
                        statementForItem.setString(2, orderDetailModel.getItemCode());
                        if(!(statementForItem.executeUpdate()>=0)){
                            isItemUpdated =false;
                        }
                    }
                    if(isItemUpdated){
                        connection.commit();
                        return "success";
                    }else{
                        connection.rollback();
                        return "Item update Error";
                    }
                }else{
                    connection.rollback();
                    return "Order detail Save Error";
                }
            }else{
                connection.rollback();
                return "Order save Error";
            }
            
        }catch(Exception e){
            connection.rollback();
            e.printStackTrace();
            return e.getMessage();
            
        }finally{
            connection.setAutoCommit(true);
        }
    }
    
}
