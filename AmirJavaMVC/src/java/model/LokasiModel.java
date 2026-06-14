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
public class LokasiModel {
    private String namaLokasi, kodeLokasi;
    private int id;
   
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNamaLokasi() {
        return namaLokasi;
    }
    
    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }
    
    public String getKodeLokasi() {
        return kodeLokasi;
    }
    
    public void setKodeLokasi(String kodeLokasi) {
        this.kodeLokasi = kodeLokasi;
    }
}
