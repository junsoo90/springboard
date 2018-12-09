package com.kuzuro.controller;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kuzuro.domain.BoardVO;
import com.kuzuro.domain.Criteria;
import com.kuzuro.domain.PageMaker;
import com.kuzuro.domain.ReplyVO;
import com.kuzuro.domain.SearchCriteria;
import com.kuzuro.service.BoardService;
import com.kuzuro.service.ReplyService;
import com.mysql.cj.util.StringUtils;

@Controller

@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	BoardService service;

	@Inject
	ReplyService RepService;

	MultipartFile files;

	// 疫뀐옙 占쎌삂占쎄쉐 get
	@RequestMapping(value = "/testResult", method = RequestMethod.GET)
	public void GettestResult() {

		System.out.println("in test.jsp");
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void Gettest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("in test.jsp");
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String Posttest() {

		return "home";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite(HttpSession session, Model model) throws Exception {
		logger.info("get write");

		Object loginInfo = session.getAttribute("member");

		if (loginInfo == null) {
			model.addAttribute("msg", false);
		}

	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String fileUpload(BoardVO board, @RequestParam MultipartFile files, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(request.getParameter("title"));
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setPasswd(request.getParameter("passwd"));
		board.setWriter(request.getParameter("writer"));

		logger.info("file upload");

		service.write(board, files);

		return "redirect:/board/listSearch";

	}

	@RequestMapping(value = "/fileDown/{bno}")
	private void fileDown(@PathVariable int bno, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		System.out.println("filedown in");

		System.out.println("bno = " + request.getParameter("bno"));
		// int bno = Integer.parseInt(request.getParameter("bno"));
		BoardVO vo = service.boardInfo(bno);

		String fileName = vo.getFilename();
		String fileurl = vo.getFileurl();
		String fileoriname = vo.getFileOriname();

		String resultfiledown = fileurl + fileName;
		System.out.println("resultfiledown = " + resultfiledown);
		byte fileByte[] = FileUtils.readFileToByteArray(new File(resultfiledown));

		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",
				"attachment; fileName=\"" + URLEncoder.encode(fileoriname, "UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);

		response.getOutputStream().flush();
		response.getOutputStream().close();

	}

	// 疫뀐옙 筌뤴뫖以�
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception {
		logger.info("get list");

		List<BoardVO> list = service.list();

		model.addAttribute("list", list);
	}

	// 疫뀐옙 鈺곌퀬�돳
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public Model getRead(@RequestParam("bno") int bno, HttpServletRequest request,
			@ModelAttribute("scri") SearchCriteria scri, Model model, HttpServletResponse response) throws Exception {
		logger.info("get read");

		Cookie cookies[] = request.getCookies();
		Map mapcookie = new HashMap();

		if (request.getCookies() != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie temp = cookies[i];
				mapcookie.put(temp.getName(), temp.getValue());
			}
			System.out.println("쿠키 없당");
		}

		String cookie_viewcnt = (String) mapcookie.get("viewcnt");
		String new_cookie_viewcnt = "|" + bno;

		if (StringUtils.indexOfIgnoreCase(cookie_viewcnt, new_cookie_viewcnt) == -1) {

			Cookie cookie = new Cookie("viewcnt", cookie_viewcnt + new_cookie_viewcnt);

			response.addCookie(cookie);

		}

		System.out.println("cookie = " + request.getCookies());
		System.out.println("bno = " + bno);

		BoardVO vo = service.read(bno);

		System.out.println("fileoriname = " + vo.getFileOriname());
		System.out.println("fileurl = " + vo.getFileurl());

		model.addAttribute("read", vo);
		model.addAttribute("scri", scri);

		List<ReplyVO> repList = RepService.readReply(bno);
		model.addAttribute("repList", repList);
		System.out.println("read");
		return model;
	}

	// 疫뀐옙 占쎈땾 占쎌젟
	@RequestMapping(value = "/modify")
	public void getModify(@RequestParam("bno") int bno, @ModelAttribute("scri") SearchCriteria scri,
			RedirectAttributes rttr, Model model) throws Exception {
		logger.info("get modify");
		System.out.println("get modify");

		BoardVO board = service.boardInfo(bno);

		System.out.println("bno = " + bno);
		System.out.println(board.getTitle());
		model.addAttribute("modify", board);
		model.addAttribute("scri", scri);
		System.out.println("get ok");

	}

	@RequestMapping(value = "/fileDeleteCheck", method = RequestMethod.GET)
	private String POSTFileDeleteCheck(BoardVO vo, @RequestParam("bno") int bno,
			@RequestParam("inputpasswd") String inputpasswd, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {

		System.out.println("post checkpasswd");
		System.out.println(inputpasswd);

		
		rttr.addAttribute("bno", bno);
		BoardVO board = service.boardInfo(bno);
		// service.fileDelete(board);

		String filename = board.getFilename();
		System.out.println("bno = " + bno);
		System.out.println("board.getfilename = " + board.getFilename());

		String fileurl = board.getFileurl();

		if (board.getPasswd().equals(inputpasswd)) {
			System.out.println("맞앗어");
			File file = new File(fileurl + filename);
			service.fileDelete(bno);
			if (file.exists() || filename != null || filename != "") {
				if (file.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			} else {
				System.out.println("파일이 존재하지 않습니다.");
			}

			System.out.println("board.getfilename = " + board.getFilename());

		} else {
			System.out.println("틀렸어");

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();

			writer.println("<script> alert('비밀번호 불일치');  ");
			writer.println("history.back();</script>");
			writer.flush();
		}

		return "redirect:/board/read?bno={bno}";
	}

	@RequestMapping(value = "/passwdCheck")
	private String GetpasswdCheck(BoardVO vo, @RequestParam("bno") int bno,
			@RequestParam("inputpasswd") String inputpasswd, @RequestParam("files") MultipartFile files,
			HttpServletRequest request, @ModelAttribute("scri") SearchCriteria scri, Model model,
			RedirectAttributes rttr, HttpServletResponse response) throws Exception {

		System.out.println("get modify");

		BoardVO board = service.boardInfo(bno);

		String oldfile = board.getFileOriname();
		System.out.println("service.boardInfo(bno) ok");

		String oldpasswd = board.getPasswd();
		System.out.println(inputpasswd);
		System.out.println("oldpass = " + board.getPasswd());

		System.out.println("bno = " + bno);

		System.out.println("oldfile = " + oldfile);
		System.out.println("files name = " + files.getOriginalFilename());
		System.out.println(files.isEmpty());
		
		if (oldpasswd.equals(inputpasswd)) {
			System.out.println("같아");

			if (!files.isEmpty()) {
				System.out.println("새로 올린 파일이 있어요");
				if (board.getFilename() != null || board.getFilename() != "") {
					String fileurl = board.getFileurl();
					String filename = board.getFilename();
					File file = new File(fileurl + filename);

					if (file.exists() || filename != null || filename != "") { // 기존파일 삭제
						if (file.delete()) {
							System.out.println("파일삭제 성공");
						} else {
							System.out.println("파일삭제 실패");
						}
					} else {
						System.out.println("파일이 존재하지 않습니다.");
					}
				}
			}

			vo.setContent(request.getParameter("content"));
			vo.setTitle(request.getParameter("title"));
			System.out.println("........");
			service.update(vo, files);
		}

		else {
			System.out.println("달라");
			PrintWriter writer = response.getWriter();
			response.setCharacterEncoding("utf-8");
			writer.println("<script type=text/javascript > alert('비밀번호 불일치');  ");
			writer.println("history.back();</script>");
			writer.flush();
		}

		
		
		rttr.addAttribute("bno", bno);
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		System.out.println("================================");

		return "redirect:/board/read?bno={bno}";

	}

	// 疫뀐옙 占쎄텣占쎌젫
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void getDelete(@RequestParam("bno") int bno, @ModelAttribute("scri") SearchCriteria scri, Model model)
			throws Exception {
		logger.info("get delete");
		System.out.println("get delete");
		model.addAttribute("bno", bno);
		model.addAttribute("scri", scri);
	}

	// 疫뀐옙 占쎄텣占쎌젫 POST
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String postDelete(@RequestParam("bno") int bno, @RequestParam("passwd") String passwd,
			HttpServletResponse response, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr)
			throws Exception {
		logger.info("post delete");

		System.out.println("post delete");
		String oldpasswd = service.boardInfo(bno).getPasswd();
		System.out.println("passwd = " + passwd);
		System.out.println("oldpasswd = " + oldpasswd);

		if (oldpasswd.equals(passwd)) {
			System.out.println("일치");
			service.delete(bno);
		} else {
			System.out.println("불일치");
			PrintWriter writer = response.getWriter();
			response.setCharacterEncoding("utf-8");
			writer.println("<script type=text/javascript > alert('비밀번호 불일치');  ");
			writer.println("history.back();</script>");
			writer.flush();
		}
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

		return "redirect:/board/listSearch";
	}

	// 疫뀐옙 筌뤴뫖以� + 占쎈읂占쎌뵠筌욑옙
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		logger.info("get list page");

		List<BoardVO> list = service.listPage(cri);
		model.addAttribute("list", list);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCount());
		model.addAttribute("pageMaker", pageMaker);

	}

	// 疫뀐옙 筌뤴뫖以� + 占쎈읂占쎌뵠筌욑옙 + 野껓옙占쎄퉳
	@RequestMapping(value = "/listSearch", method = RequestMethod.GET)
	public void listSearch(@ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception {
		logger.info("get list search");

		List<BoardVO> list = service.listSearch(scri);
		model.addAttribute("list", list);

		System.out.println(list);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount());
		model.addAttribute("pageMaker", pageMaker);
	}

	// 占쎈솊疫뀐옙 占쎌삂占쎄쉐
	@RequestMapping(value = "/replyWrite", method = RequestMethod.POST)
	public String replyWrite(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("reply write");

		RepService.writeReply(vo);

		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

		return "redirect:/board/read";
	}

	// 占쎈솊疫뀐옙 占쎈땾占쎌젟 POST
	@RequestMapping(value = "/replyUpdate", method = RequestMethod.POST)
	public String replyUpdate(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("reply update");

		RepService.replyUpdate(vo);

		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

		return "redirect:/board/read";
	}

	// 占쎈솊疫뀐옙 占쎄텣占쎌젫 POST
	@RequestMapping(value = "/replyDelete", method = RequestMethod.POST)
	public String replyDelete(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("reply delete");

		RepService.replyDelete(vo);

		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

		return "redirect:/board/read";
	}

	// 占쎈솊疫뀐옙 占쎈땾占쎌젟 GET
	@RequestMapping(value = "/replyUpdate", method = RequestMethod.GET)
	public void getReplyUpdate(@RequestParam("rno") int rno, @ModelAttribute("scri") SearchCriteria scri, Model model)
			throws Exception {
		logger.info("reply update");

		ReplyVO vo = null;

		vo = RepService.readReplySelect(rno);

		model.addAttribute("readReply", vo);
		model.addAttribute("scri", scri);
	}

	// 占쎈솊疫뀐옙 占쎈땾占쎌젟 GET
	@RequestMapping(value = "/replyDelete", method = RequestMethod.GET)
	public void getReplyDelete(@RequestParam("rno") int rno, @ModelAttribute("scri") SearchCriteria scri, Model model)
			throws Exception {
		logger.info("reply delete");

		ReplyVO vo = null;

		vo = RepService.readReplySelect(rno);

		model.addAttribute("readReply", vo);
		model.addAttribute("scri", scri);
	}

}
