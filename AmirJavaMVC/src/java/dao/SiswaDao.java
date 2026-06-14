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
import model.Siswa;

public class SiswaDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public SiswaDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList <Siswa> getAllSiswa(){
        ArrayList<Siswa> listSiswa = new ArrayList<>();
        
        try{
            String sqlAllSiswa = "SELECT * FROM sis ORDER BY nis";
            ps = conDB.prepareStatement(sqlAllSiswa);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Siswa sis = new Siswa();
                sis.setNis(rs.getString("nis"));
                sis.setNama(rs.getString("nama"));
                if(rs.getString("jenkel") !=null){
                    if(rs.getString("jenkel").equals("L"))
                   sis.setJenkel("Laki-laki");
                else sis.setJenkel("Perempuan");
                }else sis.setJenkel("");
                if(rs.getString("alamat") !=null){
                    sis.setAlamat(rs.getString("alamat"));
                }else{
                    sis.setAlamat("");
                }
                if(rs.getString("telepon") !=null){
                    sis.setTelepon(rs.getString("telepon"));
                }else{
                    sis.setTelepon("");
                }
                
                listSiswa.add(sis);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error" +e.getMessage());
        }
          return listSiswa;
        }
    public void simpanData(Siswa sis,String page){
        String sqlsimpan = null;
        if(page.equals("edit")){
            sqlsimpan ="UPDATE sis SET nama=?,jenkel=?,alamat=?, telepon=? WHERE nis=?";
        }
        if(page.equals("tambah")){
            sqlsimpan = "INSERT INTO sis (nama,jenkel,alamat,telepon,nis) VALUES(?,?,?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlsimpan);
            ps.setString(1, sis.getNama());
            if(sis.getJenkel().equals(""))ps.setString(2, null);
            else ps.setString(2, sis.getJenkel());
           
            if(sis.getAlamat().equals(""))ps.setString(3, null);
            else ps.setString(3, sis.getAlamat());
     
             if(sis.getTelepon().equals(""))ps.setString(4, null);
            else ps.setString(4, sis.getTelepon());
            ps.setString(5, sis.getNis());
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Ada Kesalahan di simpan data" +e.getMessage());
        }
    }
    public void hapusData(String nis){
        String sqlHapus = "DELETE FROM sis WHERE nis=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, nis);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("method hapus data error " +e.getMessage());
        }
    }
    
    public Siswa getRecordByNis(String nis){
        Siswa sis = new Siswa();
        String sqlSearch = "SELECT * FROM sis WHERE nis=?";
        
        try{
            ps= conDB.prepareStatement(sqlSearch);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            
            if(rs.next()){
                sis.setNis(rs.getString("nis"));
                sis.setNama(rs.getString("nama"));
                sis.setJenkel(rs.getString("jenkel"));
                sis.setTelepon(rs.getString("telepon"));
                sis.setAlamat(rs.getString("alamat"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecord by nis ada kesalahan" +e.getMessage());
            }
        return sis;
    }
    
        public static void main(String[] args) {
            SiswaDao sisdao = new SiswaDao();
            System.out.println(sisdao.getAllSiswa());
            Siswa sis = new Siswa();
            sis.setNis("S04");
            sis.setNama("Ade");
            sis.setJenkel("");
            sis.setAlamat("");
            sis.setTelepon("");
                    
            sisdao.simpanData(sis, "tambah");
        }
}
    

