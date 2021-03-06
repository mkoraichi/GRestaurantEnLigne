/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import  Classes.Ingrediant;
import  Classes.Price;
import  DBLinking.DAO;

/**
 *
 * @author inknown
 */
public class DAOPrice extends DAO implements IDAOPrice{

    private String id = "id";
    private String date = "date";
    private String price = "price";
    private String ingredientId = "ingredientId";
    public DAOPrice() {
        super();
    }

    @Override
    public ArrayList<Price> executeToArray() {

        try {
            ResultSet rs = statement.executeQuery();
            ArrayList<Price> list = new ArrayList<Price>();
            while (rs.next())  list.add(ResultSetToObject(rs));
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int executeQuery(String query, Price o) {
        statement = createStatement(query);
        try {
            statement.setDate(1, new java.sql.Date(o.getDate().getTime()));
            statement.setFloat(2, o.getPrice());
            statement.setFloat(3, o.getId());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int create(Price o) {
        return executeQuery("INSERT INTO price(date,price,id) Values(?,?,?);",o);

    }

    @Override
    public int update(Price o) {
        return executeQuery("UPDATE price SET date=?,price=? WHERE id=?;",o);
    }

    @Override
    public int delete(int id) {
        statement = createStatement("DELETE FROM ingredient WHERE id=?;");
        try {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public Price find(int id) {
        try {
            statement = createStatement("SELECT * FROM price WHERE id=?;");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return ResultSetToObject(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Price> getAll() {
            statement = createStatement("SELECT * FROM price");
            return executeToArray();
    }
    @Override
    public ArrayList<Price> findByIngrediant(Ingrediant ingrediant) {
        try {
            statement = createStatement("SELECT * FROM price WHERE id=?;");
            statement.setInt(1, ingrediant.getId());
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeToArray();
    }

    @Override
    public Price ResultSetToObject(ResultSet rs) {
       try {
           DAOIngredient daoIngredient=new DAOIngredient();
            Price p = new Price(rs.getInt(id), rs.getDate(date), rs.getFloat(price),null);
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }





    
}
