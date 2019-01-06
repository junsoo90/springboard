package com.admin.dao;

import java.util.List;
import java.util.Map;

import com.admin.VO.BoardVO;
import com.admin.VO.FileVO;

public abstract interface BoardDao {
	
	public abstract List<BoardVO> list(Map<String, Object> paramMap);

	public abstract int getCount(Map<String, Object> paramMap);
	
	public int getfileCount(Map<String, Object> map);
	
	public abstract String insert(BoardVO vo);
	
	public abstract BoardVO getInfo(int seq);
	
	public abstract int addHit(int seq);

	public abstract int delete(int seq);
	
	public abstract int getMaxSeq();
	
	public abstract int updateFilename(BoardVO vo);
	
	public abstract int updateOk(BoardVO vo);
	
	public abstract int stepUp(BoardVO vo);
	
	public abstract List<BoardVO> getBoard();
	
	public abstract int fileInsert(FileVO fileVO);
	
	public abstract List<FileVO> filegetInfo(int seq);

	public abstract int fileDelete(FileVO filevo);
}
