package com.example.models;

public class Category {
    private String id_danhmuc;
    private String name_danhmuc;

    public Category(){}
    
    public Category(String id_danhmuc, String name_danhmuc) {
        this.id_danhmuc = id_danhmuc;
        this.name_danhmuc = name_danhmuc;
    }
    public String getId_danhmuc() {
        return id_danhmuc;
    }
    public void setId_danhmuc(String id_danhmuc) {
        this.id_danhmuc = id_danhmuc;
    }
    public String getName_danhmuc() {
        return name_danhmuc;
    }
    public void setName_danhmuc(String name_danhmuc) {
        this.name_danhmuc = name_danhmuc;
    }

    
}
