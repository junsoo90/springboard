package com.admin.dao;

import java.util.List;
import java.util.Map;

import com.admin.VO.ProductVO;
import com.admin.VO.FileVO;

public interface ProductDao {

	public abstract List<ProductVO> list(Map<String, Object> paramMap);

	public abstract int getCount(Map<String, Object> paramMap);
	
	public abstract String insert(ProductVO vo);
	
	public abstract ProductVO getInfo(int seq);
	
	public abstract int addHit(int seq);

	public abstract int delete(int seq);
	
	public abstract int getMaxSeq();
	
	public abstract int updateOk(ProductVO vo);
	
	public abstract int stepUp(ProductVO vo);
	
	public abstract List<ProductVO> getBoard();
	
	public abstract int fileInsert(FileVO fileVO);
	
	public abstract List<FileVO> filegetInfo(int seq);

	public abstract int fileDelete(FileVO filevo);
	
	public int getfileCount(Map<String, Object> map);

	public abstract int updateFilename(ProductVO vo);
	
	
}
