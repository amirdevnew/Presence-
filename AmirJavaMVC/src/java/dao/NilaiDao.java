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
import model.Nilai;

/**
 *
 * @author Amir
 */
public class NilaiDao {

    /**
     * @param args the command line arguments
     */
    
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public NilaiDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <Nilai> getAllNilai(){
        ArrayList<Nilai> listNilai = new ArrayList<>();
        
        try{
            String sqlAllNilai = "SELECT * FROM nilai ORDER BY nilaisiswa DESC";
            ps = conDB.prepareStatement(sqlAllNilai);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Nilai nilai = new Nilai();
                nilai.setKodejurusan(rs.getString("kodejurusan"));
                nilai.setKodekelas(rs.getString("kodekelas"));
                nilai.setNis(rs.getString("nis"));
                nilai.setIdmapel(rs.getString("idmapel"));
                nilai.setNilaisiswa(rs.getString("nilaisiswa"));
                listNilai.add(nilai);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listNilai;
    }
    
    
    public void simpanData(Nilai nilai,String page){
        String sqlsimpan = null;
        if(page.equals("edit")){
            sqlsimpan ="UPDATE nilai SET nis=?, nilaisiswa=? WHERE kodejurusan=? AND kodekelas=? AND idmapel=?";
        }
        if(page.equals("tambah")){
            sqlsimpan = "INSERT INTO nilai (kodejurusan, kodekelas, nis, idmapel, nilaisiswa) VALUES(?,?,?, ?, ?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan); 
            if(page.equals("tambah")) {
                ps.setString(1, nilai.getKodejurusan());
                ps.setString(2, nilai.getKodekelas());
                ps.setString(3, nilai.getNis());
                ps.setString(4, nilai.getIdmapel());
                ps.setString(5, nilai.getNilaisiswa());
            } else if(page.equals("edit")) {
                ps.setString(1, nilai.getNis());
                ps.setString(2, nilai.getNilaisiswa());
                ps.setString(3, nilai.getKodejurusan());
                ps.setString(4, nilai.getKodekelas());
                ps.setString(5, nilai.getIdmapel());
            }
           
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    
    
    public void hapusData(String kodejurusan, String kodekelas, String nis, String idmapel){
        String sqlHapus = "DELETE FROM nilai WHERE kodejurusan=? AND kodekelas=? AND nis=? AND idmapel=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodejurusan);
            ps.setString(2, kodekelas);
            ps.setString(3, nis);
            ps.setString(4, idmapel);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("method hapus data error " +e.getMessage());
        }
    }
    
    
    public Nilai getRecordByKodeJurusanKelasNisMapel(String kodejurusan, String kodekelas, String nis, String idmapel){
        Nilai nilai = new Nilai();
        String sqlSearch = "SELECT * FROM nilai WHERE kodejurusan=? AND kodekelas=? AND nis=? AND idmapel=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, kodejurusan);
            ps.setString(2, kodekelas);
            ps.setString(3, nis);
            ps.setString(4, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nilai.setKodejurusan(rs.getString("kodejurusan"));
                nilai.setKodekelas(rs.getString("kodekelas"));
                nilai.setNis(rs.getString("nis"));
                nilai.setIdmapel(rs.getString("idmapel"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by kode jurusan dan kelas ada kesalahan" +e.getMessage());
            }
        return nilai;
    }
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
