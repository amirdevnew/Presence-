/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Amir411
 */
public class ListPresensiModel {
    private int id, id_lokasi, id_user;
    
    private String waktu, feel, namaLokasi, namaUser;
   
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdLokasi() {
        return id_lokasi;
    }
    
    public void setIdLokasi(int idLokasi) {
        this.id_lokasi = idLokasi;
    }
    
    public int getIdUser() {
        return id_user;
    }
    
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }
    
    public String getWaktu() {
        return waktu;
    }
    
    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
    
    public String getFeel() {
        return feel;
    }
    
    public void setFeel(String feel) {
        this.feel = feel;
    }
    
    public String getNamaLokasi() {
        return namaLokasi;
    }
    
    public void setNamaLokasi(String feel) {
        this.namaLokasi = feel;
    }
    
    public String getNamaUser() {
        return namaUser;
    }
    
    public void setNamaUser(String feel) {
        this.namaUser = feel;
    }
}
