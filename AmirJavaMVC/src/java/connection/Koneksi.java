/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * A411_collaboration - Conncetion The database
 */
package connection;

import java.sql.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;


public class Koneksi {
 
    static Connection conDB;
    
    public static Connection getKoneksi(){
        
        if(conDB==null){
            MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("absensi3");
            data.setUser("root");
            data.setPassword("");
            
            try{
                conDB = data.getConnection();
                System.out.println("Koneksi sukses");
            }
            catch(SQLException e){
                System.out.println("Koneksi gagal" +e.getMessage());
             }
            }
            return conDB;
        }
        
        public static void main(String[] args){
            
            getKoneksi();
        }
    
}
