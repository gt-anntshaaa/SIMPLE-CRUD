package com.example.realtimedatabase6;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Mahasiswa implements Serializable {
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    private String nim, nama, prodi, ipk;

    public Mahasiswa(){}
    public Mahasiswa(String nim, String nama, String prodi, String ipk){
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.ipk = ipk;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getProdi() {
        return prodi;
    }

    public String getIpk() {
        return ipk;
    }
}
