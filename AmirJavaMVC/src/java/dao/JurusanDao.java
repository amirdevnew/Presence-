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
import model.jurusan;

public class JurusanDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public JurusanDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <jurusan> getAllJurusan(){
        ArrayList<jurusan> listJurusan = new ArrayList<>();
        
        try{
            String sqlAllJurusan = "SELECT * FROM jurusan ORDER BY kodejurusan";
            ps = conDB.prepareStatement(sqlAllJurusan);
            rs = ps.executeQuery();
            
            while(rs.next()){
                jurusan jur = new jurusan();
                jur.setKodejurusan(rs.getString("kodejurusan"));
                jur.setNamajurusan(rs.getString("namajurusan"));
                listJurusan.add(jur);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listJurusan;
        }
    public void simpanData(jurusan jur,String page){
        String sqlsimpan = null;
        if(page.equals("edit")){
            sqlsimpan ="UPDATE jurusan SET namajurusan=? WHERE kodejurusan=? ";
        }
        if(page.equals("tambah")){
            sqlsimpan = "INSERT INTO jurusan (namajurusan,kodejurusan) VALUES(?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan); 
            ps.setString(1, jur.getNamajurusan());
            ps.setString(2, jur.getKodejurusan());
           
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    public void hapusData(String kodejurusan){
        String sqlHapus = "DELETE FROM jurusan WHERE kodejurusan=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodejurusan);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("method hapus data error " +e.getMessage());
        }
    }
    
    public jurusan getRecordByKodeJurusan(String kodejurusan){
        jurusan jur = new jurusan();
        String sqlSearch = "SELECT * FROM jurusan WHERE kodejurusan=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, kodejurusan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                jur.setKodejurusan(rs.getString("kodejurusan"));
                jur.setNamajurusan(rs.getString("namajurusan"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by kodejurusan ada kesalahan" +e.getMessage());
            }
        return jur;
    }
    
        public static void main(String[] args) {
            JurusanDao jurdao = new JurusanDao();
            System.out.println(jurdao.getAllJurusan());
            
            jurusan jur = new jurusan();
            
            jurdao.simpanData(jur, "tambah");
            
        }
}
    

