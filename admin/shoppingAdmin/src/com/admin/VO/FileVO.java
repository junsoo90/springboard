package com.admin.VO;

public class FileVO {

	private int seq; // 파일번호 
	private int filecnt; // 파일갯수
	private String orgfilename; // 원본파일이름
	private String filelocation; // 파일 위치
	private String savefilename; // 저장된 파일 이름
	
	public int getFilecnt() {
		return filecnt;
	}
	public void setFilecnt(int filecnt) {
		this.filecnt = filecnt;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getOrgfilename() {
		return orgfilename;
	}
	public void setOrgfilename(String orgfilename) {
		this.orgfilename = orgfilename;
	}
	public String getFilelocation() {
		return filelocation;
	}
	public void setFilelocation(String filelocation) {
		this.filelocation = filelocation;
	}
	public String getSavefilename() {
		return savefilename;
	}
	public void setSavefilename(String savafilename) {
		this.savefilename = savafilename;
	}
	
	
}
