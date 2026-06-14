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
import model.Kelas;

/**
 *
 * @author SAFARI
 */
public class KelasDao {

    /**
     * @param args the command line arguments
     */
    
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public KelasDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <Kelas> getAllKelas(){
        ArrayList<Kelas> listKelas = new ArrayList<>();
        
        try{
            String sqlAllKelas = "SELECT * FROM kelas ORDER BY kodekelas";
            ps = conDB.prepareStatement(sqlAllKelas);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Kelas kls = new Kelas();
                kls.setKodejurusan(rs.getString("kodejurusan"));
                kls.setKodekelas(rs.getString("kodekelas"));
                kls.setNamakelas(rs.getString("namakelas"));
                listKelas.add(kls);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listKelas;
    }
    public void simpanData(Kelas kls,String page){
        String sqlsimpan = null;
        if(page.equals("edit")){
            sqlsimpan ="UPDATE kelas SET namakelas=? WHERE kodekelas=? AND kodejurusan=?";
        }
        if(page.equals("tambah")){
            sqlsimpan = "INSERT INTO kelas (kodejurusan, kodekelas, namakelas) VALUES(?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan); 
            ps.setString(1, kls.getKodejurusan());
            ps.setString(2, kls.getKodekelas());
            ps.setString(3, kls.getNamakelas());
           
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    public void hapusData(String kodejurusan, String kodekelas){
        String sqlHapus = "DELETE FROM kelas WHERE kodejurusan=? AND kodekelas=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodejurusan);
            ps.setString(2, kodekelas);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("method hapus data error " +e.getMessage());
        }
    }
    
    public Kelas getRecordByKodeJurusanKelas(String kodejurusan, String kodekelas){
        Kelas kls = new Kelas();
        String sqlSearch = "SELECT * FROM kelas WHERE kodejurusan=? AND kodekelas=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, kodejurusan);
            ps.setString(2, kodekelas);
            rs = ps.executeQuery();
            
            if(rs.next()){
                kls.setKodejurusan(rs.getString("kodejurusan"));
                kls.setKodekelas(rs.getString("kodekelas"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by kode jurusan dan kelas ada kesalahan" +e.getMessage());
            }
        return kls;
    }
    
 
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
