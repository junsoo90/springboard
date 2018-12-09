package com.kuzuro.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.kuzuro.domain.BoardVO;
import com.kuzuro.domain.Criteria;
import com.kuzuro.domain.ReplyVO;
import com.kuzuro.domain.SearchCriteria;

public interface BoardService {

	// �옉�꽦 
	public void write(BoardVO vo, MultipartFile files) throws Exception;
	
	// 議고쉶
	public BoardVO read(int bno) throws Exception;
	
	// �닔�젙
	public void update(BoardVO vo, MultipartFile files) throws Exception;
	
	// �궘�젣
	public void delete(int bno) throws Exception;

	// 紐⑸줉
	public List<BoardVO> list() throws Exception;

	// 紐⑸줉 + �럹�씠吏�
	public List<BoardVO> listPage(Criteria cri) throws Exception;

	// 寃뚯떆臾� 珥� 媛��닔
	public int listCount() throws Exception;

	// 紐⑸줉 + �럹�씠吏� + 寃��깋
	public List<BoardVO> listSearch(SearchCriteria scri) throws Exception;
		
	// 寃��깋 寃곌낵 媛��닔
	public int countSearch(SearchCriteria scri) throws Exception;
	
	// �뙎湲� 議고쉶
	public List<ReplyVO> readReply(int bno) throws Exception;

	public void fileDelete(int bno) throws Exception;
	
	public BoardVO fileSearch(int bno);
	
	public String deletePasswd(int bno);
	
	public BoardVO boardInfo(int bno);
	
}
