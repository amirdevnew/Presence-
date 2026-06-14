// Amir_collaboration - AuthDao sistem
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;

public class AuthDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public AuthDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <User> getAllUser(){
        ArrayList<User> listUser = new ArrayList<>();
        
        try{
            String sqlAllUser = "SELECT * FROM user";
            ps = conDB.prepareStatement(sqlAllUser);
            rs = ps.executeQuery();
            
            while(rs.next()){
                User user = new User();
                user.setRole(rs.getInt("role"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                
                if(rs.getString("alamat") !=null){
                    user.setAlamat(rs.getString("alamat"));
                }else{
                    user.setAlamat("");
                }
                
                if(rs.getString("nohp") !=null){
                    user.setNohp(rs.getString("nohp"));
                }else{
                    user.setNohp("");
                }
                
                listUser.add(user);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listUser;
        }
    public void simpanData(User user,String page){
        String sqlsimpan = null;
        if(page.equals("edit")){
            sqlsimpan ="UPDATE user SET username=?, password=?, alamat=?, nohp=?, role=? WHERE email=?";
        }
        if(page.equals("tambah")){
            sqlsimpan = "INSERT INTO user (username, password, alamat, nohp, email, role) VALUES(?,?,?,?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
           
            if(user.getAlamat().equals(""))ps.setString(3, null);
            else ps.setString(3, user.getAlamat());
            
     
            if(user.getNohp().equals(""))ps.setString(4, null);
            else ps.setString(4, user.getNohp());
            
            
            if(page.equals("edit")) {
                ps.setInt(5, user.getRole());
                ps.setString(6, user.getEmail());
            } else if(page.equals("tambah")) {
                ps.setString(5, user.getEmail());
                ps.setInt(6, user.getRole());
            }
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    public void hapusData(String email){
        String sqlHapus = "DELETE FROM user WHERE email=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, email);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("method hapus data error " +e.getMessage());
        }
    }
    
    public User getRecordByEmail(String email){
        User user = new User();
        String sqlSearch = "SELECT * FROM user WHERE email=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getInt("role"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setNohp(rs.getString("nohp"));
                user.setAlamat(rs.getString("alamat"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by nis ada kesalahan" +e.getMessage());
            }
        return user;
    }
    
    public User getRecordById(String id){
        User user = new User();
        String sqlSearch = "SELECT * FROM user WHERE id=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getInt("role"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setNohp(rs.getString("nohp"));
                user.setAlamat(rs.getString("alamat"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by nis ada kesalahan" +e.getMessage());
            }
        return user;
    }
    
        public static void main(String[] args) {
//            AuthDao sisdao = new AuthDao();
//            System.out.println(sisdao.getAllUser());
//            User user = new User();
//            sis.setEmail("S04");
//            sis.setUsername("Ade");
//            sis.setNohp("");
//            sis.setAlamat("");
//            sis.setPassword("");
//                    
//            sisdao.simpanData(user, "tambah");
        }
}
    

