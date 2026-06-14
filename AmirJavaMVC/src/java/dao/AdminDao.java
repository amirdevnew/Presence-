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
import model.ListPresensiModel;
import model.LokasiModel;
import model.User;

public class AdminDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public AdminDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <LokasiModel> getAllLokasi(){
        ArrayList<LokasiModel> listLokasi = new ArrayList<>();
        
        try{
            String sqlAllLokasi = "SELECT * FROM lokasi";
            ps = conDB.prepareStatement(sqlAllLokasi);
            rs = ps.executeQuery();
            
            while(rs.next()){
                LokasiModel lokasi = new LokasiModel();
                lokasi.setId(rs.getInt("id"));
                lokasi.setNamaLokasi(rs.getString("namaLokasi"));
                lokasi.setKodeLokasi(rs.getString("kodeLokasi"));
                
                
                listLokasi.add(lokasi);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listLokasi;
        }
    
    
    public ArrayList <ListPresensiModel> getAllPresensi(){
        ArrayList<ListPresensiModel> presensiAll = new ArrayList<>();
        
        try{
            String sqlAllLokasi = "SELECT * FROM list_presensi";
            ps = conDB.prepareStatement(sqlAllLokasi);
            rs = ps.executeQuery();
            
            while(rs.next()){
                ListPresensiModel presensi = new ListPresensiModel();
                
                int id = rs.getInt("id"), id_lokasi = rs.getInt("id_lokasi"), id_user = rs.getInt("id_user");
                
                String waktu = rs.getString("waktu"), feel = rs.getString("feel");
                
                LokasiModel lokasi = this.getRecordById(String.valueOf(id_lokasi));
                User user = new AuthDao().getRecordById(String.valueOf(id_user));
                presensi.setNamaUser(user.getUsername());
                presensi.setNamaLokasi(lokasi.getNamaLokasi());
                
                presensi.setId(id);
                presensi.setIdLokasi(id_lokasi);
                presensi.setIdUser(id_user);
                
                
                presensi.setWaktu(waktu);
                presensi.setFeel(feel);
                
                
                presensiAll.add(presensi);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return presensiAll;
        }
    
    
    public void simpanData(LokasiModel lokasi,String page){
        String sqlsimpan = null;
        if(page.equals("editLokasi")){
            sqlsimpan ="UPDATE lokasi SET namaLokasi=? WHERE kodeLokasi=?";
        }
        if(page.equals("tambahLokasi")){
            sqlsimpan = "INSERT INTO lokasi (namaLokasi, kodeLokasi) VALUES(?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan);
            ps.setString(1, lokasi.getNamaLokasi());
            ps.setString(2, lokasi.getKodeLokasi());
           
            
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    
    
    public void simpanDataListPresensi(ListPresensiModel presensi,String page){
        String sqlsimpan = null;
        if(page.equals("editPresensi")){
            sqlsimpan ="UPDATE list_presensi SET id_lokasi=?, id_user=?, waktu=?, feel=? WHERE id=?";
        }
        if(page.equals("tambahPresensi")){
            sqlsimpan = "INSERT INTO list_presensi (id_lokasi, id_user, waktu, feel) VALUES(?,?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan);
            ps.setInt(1, presensi.getIdLokasi());
            ps.setInt(2, presensi.getIdUser());
            ps.setString(3, presensi.getWaktu());
            ps.setString(4, presensi.getFeel());
            if(page.equals("editPresensi")) {
                ps.setInt(5, presensi.getId());
            }
           
            
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    
    
    public LokasiModel getRecordByKode(String kodeLokasi){
        LokasiModel lokasi = new LokasiModel();
        String sqlSearch = "SELECT * FROM lokasi WHERE kodeLokasi=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, kodeLokasi);
            rs = ps.executeQuery();
            
            if(rs.next()){
                lokasi.setId(rs.getInt("id"));
                lokasi.setNamaLokasi(rs.getString("namaLokasi"));
                lokasi.setKodeLokasi(rs.getString("kodeLokasi"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by nis ada kesalahan" +e.getMessage());
            }
        return lokasi;
    }
    
    public LokasiModel getRecordById(String id){
        LokasiModel lokasi = new LokasiModel();
        String sqlSearch = "SELECT * FROM lokasi WHERE id=?";
        ResultSet rs_id;
        PreparedStatement ps_id;
        try{
            ps_id= conDB.prepareStatement(sqlSearch);
            ps_id.setString(1, id);
            rs_id = ps_id.executeQuery();
            
            if(rs_id.next()){
                lokasi.setId(rs_id.getInt("id"));
                lokasi.setNamaLokasi(rs_id.getString("namaLokasi"));
                lokasi.setKodeLokasi(rs_id.getString("kodeLokasi"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by nis ada kesalahan" +e.getMessage());
            }
        return lokasi;
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
    

