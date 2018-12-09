package com.kuzuro.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kuzuro.domain.BoardVO;
import com.kuzuro.domain.Criteria;
import com.kuzuro.domain.ReplyVO;
import com.kuzuro.domain.SearchCriteria;
import com.kuzuro.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;

	// �옉�꽦
	@Override
	public void write(BoardVO vo, MultipartFile files) throws Exception {
		dao.write(vo,files);
	}

	// 議고쉶
	
	@Transactional
	@Override
	public BoardVO read(int bno) throws Exception {
		dao.updateViewCnt(bno);
		return dao.read(bno);
	}

	// �닔�젙
	@Override
	public void update(BoardVO vo, MultipartFile files) throws Exception {
		dao.update(vo,files);
	}

	// �궘�젣
	@Override
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}

	// 紐⑸줉
	@Override
	public List<BoardVO> list() throws Exception {
		return dao.list();
	}

	// 紐⑸줉 + �럹�씠吏�
	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		return dao.listPage(cri);
	}

	// 寃뚯떆臾� 珥� 媛��닔
	@Override
	public int listCount() throws Exception {
		return dao.listCount();
	}

	// 紐⑸줉 + �럹�씠吏� + 寃��깋
	@Override
	public List<BoardVO> listSearch(SearchCriteria scri) throws Exception {
		return dao.listSearch(scri);
	}

	// 寃��깋 寃곌낵 媛��닔
	@Override
	public int countSearch(SearchCriteria scri) throws Exception {
		return dao.countSearch(scri);
	}

	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		return dao.readReply(bno);
	}

	

	public void fileDelete(int bno) throws Exception {
		dao.fileDelete(bno);
	}

	public String deletePasswd(int bno) {
		return dao.deletePasswd(bno);
	}

	@Override
	public BoardVO fileSearch(int bno) {
		return dao.fileSearch(bno);
	}
	public BoardVO boardInfo(int bno) {
		return dao.boardInfo(bno);
	}

}