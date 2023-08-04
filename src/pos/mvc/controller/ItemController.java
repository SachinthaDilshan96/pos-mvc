/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos.mvc.db.DBConnection;
import pos.mvc.model.ItemModel;

/**
 *
 * @author sachinthadilshan
 */
public class ItemController {
    
    public ArrayList<ItemModel> getAllItems() throws SQLException{
    
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "select * from item";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rst = statement.executeQuery();
            ArrayList<ItemModel> itemModels = new ArrayList<>();
            while(rst.next()){
                ItemModel itemModel = new ItemModel(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getInt(5)
                );
                itemModels.add(itemModel);
            }
            return itemModels;
        
    }
    
    public String saveItem(ItemModel item) throws SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "insert into item values (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, item.getItemCode());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setString(3, item.getPackSize());
        preparedStatement.setDouble(4, item.getUnitPrize());
        preparedStatement.setInt(5, item.getQoh());
        if (preparedStatement.executeUpdate()>0) {
            return "Success";
        }else{
            return "Fail";
        }
    }
    
}
