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
import model.mapel;

public class MapelDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public MapelDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <mapel> getAllMapel(){
        ArrayList<mapel> listMapel = new ArrayList<>();
        
        try{
            String sqlAllMapel = "SELECT * FROM mapel ORDER BY idmapel";
            ps = conDB.prepareStatement(sqlAllMapel);
            rs = ps.executeQuery();
            
            while(rs.next()){
                mapel ma = new mapel();
                ma.setIdmapel(rs.getString("idmapel"));
                ma.setNamamapel(rs.getString("namamapel"));
                listMapel.add(ma);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listMapel;
        }
    public void simpanData(mapel ma,String page){
        String sqlsimpan = null;
        if(page.equals("edit")){
            sqlsimpan ="UPDATE mapel SET namamapel=? WHERE idmapel=? ";
        }
        if(page.equals("tambah")){
            sqlsimpan = "INSERT INTO mapel (namamapel, idmapel) VALUES(?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan); 
            ps.setString(1, ma.getNamamapel());
            ps.setString(2, ma.getIdmapel());
           
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    public void hapusData(String idmapel){
        String sqlHapus = "DELETE FROM mapel WHERE idmapel=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, idmapel);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("method hapus data error " +e.getMessage());
        }
    }
    
    public mapel getRecordByIdMapel(String idmapel){
        mapel ma = new mapel();
        String sqlSearch = "SELECT * FROM mapel WHERE idmapel=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                ma.setIdmapel(rs.getString("idmapel"));
                ma.setNamamapel(rs.getString("namamapel"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by idmapel ada kesalahan" +e.getMessage());
            }
        return ma;
    }
    
        public static void main(String[] args) {
            MapelDao madao = new MapelDao();
            System.out.println(madao.getAllMapel());
            
            mapel ma = new mapel();
            
            madao.simpanData(ma, "edit");
            
        }
}
    

