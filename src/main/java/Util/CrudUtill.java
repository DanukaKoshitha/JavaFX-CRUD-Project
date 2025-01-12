package Util;

import DB.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtill {
    public static  <T>T execute(String SQL,Object ... obj) throws SQLException {
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(SQL);


        if (SQL.startsWith("select") || SQL.startsWith("SELECT")){
            return  (T) pst.executeQuery();
        }else {

            for (int i=0; i<obj.length;i++){
                pst.setObject(i+1,obj[i]);
            }
            return (T) (Boolean) (pst.executeUpdate()>0);
        }
    }
}
