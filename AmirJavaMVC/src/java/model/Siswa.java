/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Amir
 */
public class Siswa {

    private String nis, nama, jenkel , telepon , alamat;
   
    public String getNis() {
       return nis;
    }
    
    public void setNis(String nis) {
        this.nis = nis;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getJenkel() {
        return jenkel;
    }
    
    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }
    
    public String getAlamat() {
        return alamat;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public String getTelepon() {
        return telepon;
    }
    
    public void setTelepon(String telepon){
        this.telepon = telepon;
    }
    
}
