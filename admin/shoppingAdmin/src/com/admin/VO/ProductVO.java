package com.admin.VO;

public class ProductVO {

	private int pSeq; // 상품번호
	private String pName; // 상품이름
	private int pPrice; // 가격
	private int pStock; // 재고
	private String pOption; // 상품옵션
	private String pDetail; // 상품상세
	
	private int pLev;
	private int pRef;
	private int pStep;
	
	public int getpLev() {
		return pLev;
	}
	public void setpLev(int pLev) {
		this.pLev = pLev;
	}
	public int getpRef() {
		return pRef;
	}
	public void setpRef(int pRef) {
		this.pRef = pRef;
	}
	public int getpStep() {
		return pStep;
	}
	public void setpStep(int pStep) {
		this.pStep = pStep;
	}
	public void setpSeq(int pSeq) {
		this.pSeq = pSeq;
	}
	public int getpSeq() {
		return pSeq;
	}
	public void setpSEq(int pSEq) {
		this.pSeq = pSEq;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public int getpPrice() {
		return pPrice;
	}
	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}
	public int getpStock() {
		return pStock;
	}
	public void setpStock(int pStock) {
		this.pStock = pStock;
	}
	public String getpOption() {
		return pOption;
	}
	public void setpOption(String pOption) {
		this.pOption = pOption;
	}
	public String getpDetail() {
		return pDetail;
	}
	public void setpDetail(String pDetail) {
		this.pDetail = pDetail;
	}
	
	
}
