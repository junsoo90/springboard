package com.kuzuro.persistence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.kuzuro.domain.BoardVO;
import com.kuzuro.domain.Criteria;
import com.kuzuro.domain.ReplyVO;
import com.kuzuro.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {

	// 筌띾뜆�뵠獄쏅��뼒占쎈뮞
	@Inject
	private SqlSession sql;

	// 筌띲끋�쓠
	private static String namespace = "com.kuzuro.mappers.boardMapper";

	// 占쎌삂占쎄쉐
	@Override
	public void write(BoardVO vo, MultipartFile files) throws Exception {

		sql.insert(namespace + ".write", fileWrite(vo, files));

	}

	// 鈺곌퀬�돳
	@Override
	public BoardVO read(int bno) throws Exception {
		return sql.selectOne(namespace + ".read", bno);
	}

	// 占쎈땾占쎌젟
	@Override
	public void update(BoardVO vo, MultipartFile files) throws Exception {
		BoardVO board = fileWrite(vo, files);

		if (board.getFilename() == null || board.getFilename() == "") {
			System.out.println("파일 없는 update");
			sql.update(namespace + ".update", board);
		} else
			sql.update(namespace + ".fileupdate", board);
	}

	// 占쎄텣占쎌젫
	@Override
	public void delete(int bno) throws Exception {
		sql.delete(namespace + ".delete", bno);
	}

	// 筌뤴뫖以�
	@Override
	public List<BoardVO> list() throws Exception {
		return sql.selectList(namespace + ".list");
	}

	// 筌뤴뫖以� + 占쎈읂占쎌뵠筌욑옙
	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		return sql.selectList(namespace + ".listPage", cri);
	}

	// 野껊슣�뻻�눧占� �룯占� 揶쏉옙占쎈땾
	@Override
	public int listCount() throws Exception {
		return sql.selectOne(namespace + ".listCount");
	}

	// 筌뤴뫖以� + 占쎈읂占쎌뵠筌욑옙 + 野껓옙占쎄퉳
	@Override
	public List<BoardVO> listSearch(SearchCriteria scri) throws Exception {
		System.out.println("search keyword = " + scri.getKeyword());

		return sql.selectList(namespace + ".listSearch", scri);
	}

	// 野껓옙占쎄퉳 野껉퀗�궢 揶쏉옙占쎈땾
	@Override
	public int countSearch(SearchCriteria scri) throws Exception {
		return sql.selectOne(namespace + ".countSearch", scri);
	}

	// 占쎈솊疫뀐옙 鈺곌퀬�돳
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		return sql.selectList(namespace + ".readRpley", bno);
	}

	public void fileDelete(int bno) throws Exception {
		sql.update(namespace + ".fileDelete", bno);
	}

	public String deletePasswd(int bno) {
		return sql.selectOne(namespace + ".deletepasswd", bno);
	}

	public void updateViewCnt(int bno) {
		sql.update(namespace + ".viewcnt", bno);
	}

	public BoardVO boardInfo(int bno) {
		return sql.selectOne(namespace + ".boardinfo", bno);
	}

	public BoardVO fileSearch(int bno) {
		return sql.selectOne(namespace + "/fileSearch");
	}

	public BoardVO fileWrite(BoardVO board, MultipartFile files) throws Exception {

		if (files.isEmpty()) {
			System.out.println("올린 파일이 없어");
			return board;
		}
		System.out.println("files name = " + files.getOriginalFilename());
		System.out.println(files.isEmpty());

		String orifilename = files.getOriginalFilename();

		String extension = orifilename.substring(orifilename.indexOf(".")); // .jpg
		System.out.println(extension);

		String fileurl = "C:\\Users\\junso\\Desktop\\folder\\";
		String savefilename = dateForm() + "_" + orifilename;

		File file = new File(fileurl);
		System.out.println("exist = " + file.exists());
		System.out.println("fileurl = " + fileurl);

		if (!file.exists()) {
			System.out.println("!file exist");
			file.mkdir();
		}

		if (!files.isEmpty()) {
			System.out.println("!files.isEmpty");
			file = new File(fileurl + savefilename);
			files.transferTo(file);
		}

		System.out.println("complete");

		board.setFileOriname(orifilename);
		board.setFileurl(fileurl);
		board.setFilename(savefilename);
		System.out.println("savefilename = " + savefilename);
		System.out.println("filename = " + orifilename);

		return board;
	}

	public String savafileForm(MultipartFile files) {
		String orifilename = files.getOriginalFilename();
		String extension = orifilename.substring(orifilename.indexOf(".")); // .jpg
		return dateForm() + "_" + orifilename;
	}

	public String dateForm() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd-HH-mm-ss", Locale.KOREA);
		return dayTime.format(new Date(time));
	}
}