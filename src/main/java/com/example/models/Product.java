package com.example.models;


public class Product {
	String idMon;
	String tenMon;
	String idDanhMuc;
	double gia;
	String hinhAnh;
	String moTa;
	String donViTinh;
	public String getIdMon() {
		return idMon;
	}
	public void setIdMon(String idMon) {
		this.idMon = idMon;
	}
	public String getTenMon() {
		return tenMon;
	}
	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}
	public String getIdDanhMuc() {
		return idDanhMuc;
	}
	public void setIdDanhMuc(String idDanhMuc) {
		this.idDanhMuc = idDanhMuc;
	}
	public double getGia() {
		return gia;
	}
	public void setGia(double gia) {
		this.gia = gia;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public Product() {
	}
    public Product(String idMon, String tenMon, String idDanhMuc, double gia, String hinhAnh, String moTa, String donViTinh) {
        this.idMon = idMon;
        this.tenMon = tenMon;
        this.idDanhMuc = idDanhMuc;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.donViTinh = donViTinh;
    }

	@Override
	public String toString() {
		return "Product [idMon=" + idMon + ", tenMon=" + tenMon + ", idDanhMuc=" + idDanhMuc + ", gia=" + gia
				+ ", hinhAnh=" + hinhAnh + ", moTa=" + moTa + ", donViTinh=" + donViTinh + "]";
	}
	
}

