package com.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.VO.BoardVO;
import com.admin.VO.FileVO;
import com.admin.dao.BoardDao;
import com.admin.paging.Paging;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataTypes;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformRequest;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;
import com.tobesoft.xplatform.tx.PlatformException;
import com.tobesoft.xplatform.tx.PlatformType;

@Controller
public class ListController {
	
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(getClass());
	private int pageSize = 10;
	private int blockCount = 10;

	@Autowired
	private BoardDao boardDao;

	@RequestMapping({ "/board/list.do" })
	public void list( 
			 @RequestParam(value = "pageNum", defaultValue = "") int currentPage,
			 @RequestParam(value = "keyField", defaultValue = "") String keyField,
			 @RequestParam(value = "keyWord", defaultValue = "") String keyWord,
			HttpServletRequest request, HttpServletResponse response, BoardVO board) throws PlatformException, UnsupportedEncodingException {

		System.out.println("/board/list in");
		// Xplatform basic object creation
		PlatformData pdata = new PlatformData();

		int nErrorCode = 0;
		String strErrorMsg = "START";

		
		
		keyWord = new String(request.getParameter("keyWord").getBytes("iso8859-1"), "UTF-8");
		
	
		try {
			System.out.println("HttpPlatformRequest in");
			HttpPlatformRequest req = new HttpPlatformRequest(request);

			req.receiveData();
			pdata = req.getData();
			
			DataSet ds = new DataSet("bbsDataSet");

			ds.addColumn("seq", DataTypes.INT, 4);
			ds.addColumn("title", DataTypes.STRING, 256);
			ds.addColumn("content", DataTypes.STRING, 400);
			ds.addColumn("name", DataTypes.STRING, 400);
			ds.addColumn("regdate", DataTypes.DATE, 20);
			ds.addColumn("hit", DataTypes.INT, 5);
			ds.addColumn("rnum", DataTypes.INT, 5);

			ds.addColumn("ref", DataTypes.INT, 5);
			ds.addColumn("lev", DataTypes.INT, 5);
			ds.addColumn("step", DataTypes.INT, 5);

			ds.addColumn("keyField", DataTypes.STRING, 20);
			ds.addColumn("KeyWord", DataTypes.STRING, 20);

			if(keyWord.toString().equals("undefined"))
				 keyWord = "";
			// DAO
			System.out.println("keyField = " + keyField);
			System.out.println("KeyWord = " + keyWord);
		
			
			HashMap<String, Object> map = new HashMap();

			 map.put("keyField", keyField);
			 map.put("keyWord", keyWord);
			 
			 int count = 0;
			if(keyField.toString().equals("orgfilename")){
				System.out.println("filecount");
				count = boardDao.getfileCount(map);
			}
			else {
				System.out.println("boardcount");
				count = boardDao.getCount(map);
			}
			
			System.out.println("count = " + count);
			System.out.println("currentPage = " + currentPage);
			Paging page = new Paging(keyField, keyWord, currentPage, count,
					this.pageSize, this.blockCount, "list.do");

			map.put("start", Integer.valueOf(page.getStartCount()));
			map.put("end", Integer.valueOf(page.getEndCount()));
			
			
			List<BoardVO> list = null;
			
			if (count > 0) {
				list = this.boardDao.list(map);
			} else {
				list = Collections.emptyList();
			}
			System.out.println("list.size() = " + list.size());
			int number = count - (currentPage - 1) * this.pageSize;
			// public List<BoardVO> list(Map<String, Object> map)

			// ResultSet -> Show the Row sets (XML) : browser
			for (int i = 0; i < list.size(); i++) {

				int row = ds.newRow(); // new Row feed

				board = list.get(i);
				ds.set(row, "seq", board.getSeq());
				ds.set(row, "title", board.getTitle());
				ds.set(row, "name", board.getTitle());
				ds.set(row, "content", board.getContent());
				ds.set(row, "regdate", board.getRegdate());
				ds.set(row, "hit", board.getHit());
				ds.set(row, "rnum", board.getRnum());
				
				ds.set(row, "ref", board.getRef());
				ds.set(row, "step", board.getStep());
				ds.set(row, "lev", board.getLev());
				
			} // for
			
			
			pdata.addDataSet(ds);

			// set the ErrorCode and ErrorMsg about success
			nErrorCode = 0;
			strErrorMsg = "SUCC";
			strErrorMsg = String.valueOf(count);
			
		} catch (Throwable th) {
			// set the ErrorCode and ErrorMsg about fail
			nErrorCode = -1;
			strErrorMsg = th.getMessage();
			System.out.println("ERROR:" + strErrorMsg);
			th.printStackTrace();
		} // try

		// save the ErrorCode and ErrorMsg for sending Client
		VariableList varList = pdata.getVariableList();

		varList.add("ErrorCode", nErrorCode);
		varList.add("ErrorMsg", strErrorMsg);

		// send the result data(XML) to Client
		HttpPlatformResponse res = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "UTF-8");
		
		res.setData(pdata);
		res.sendData(); // Send Data

		// String pagingHtml = "";
		// @SuppressWarnings({ "unchecked", "rawtypes" })
		//
		// HashMap<String, Object> map = new HashMap();
		// map.put("keyField", keyField);
		// map.put("keyWord", keyWord);
		//
		// int count = this.boardDao.getCount(map);
		//
		// Paging page = new Paging(keyField, keyWord, currentPage, count,
		// this.pageSize, this.blockCount, "list.do");
		//
		// pagingHtml = page.getPagingHtml().toString();
		//
		// map.put("start", Integer.valueOf(page.getStartCount()));
		// map.put("end", Integer.valueOf(page.getEndCount()));
		//
		// List<BoardVO> list = null;
		// if (count > 0) {
		// list = this.boardDao.list(map);
		// } else {
		// list = Collections.emptyList();
		// }
		// int number = count - (currentPage - 1) * this.pageSize;
		// int endPage = (count / pageSize) + ((count % pageSize == 0) ? 0 : 1);
		//
		// ModelAndView mav = new ModelAndView();
		//
		// mav.setViewName("boardList");
		// mav.addObject("count", Integer.valueOf(count));
		// mav.addObject("currentPage", Integer.valueOf(currentPage));
		// mav.addObject("list", list);
		// mav.addObject("pagingHtml", pagingHtml);
		// mav.addObject("number", Integer.valueOf(number));
		// mav.addObject("endPage", endPage);
		// mav.addObject("keyField", keyField);
		// mav.addObject("keyWord", keyWord);
		// System.out.println("list keyfiled = " + keyField);
		// return mav;
	}

	@SuppressWarnings({ "deprecation", "null" })
	@RequestMapping(value = "/board/insert.do")
	public void insert(HttpServletRequest request, 
			HttpServletResponse response,BoardVO board
			//,ModelAndView mv, BoardVO board
			)
			throws PlatformException {
		
		PlatformData pdata = new PlatformData();

		int nErrorCode = 0;
		String strErrorMsg = "START";

		try {
			// receive client request
			// not need to receive

			// create HttpPlatformRequest for receive data from client
			HttpPlatformRequest req = new HttpPlatformRequest(request);

			req.receiveData();
			pdata = req.getData();

			DataSet ds = pdata.getDataSet("bbsDataSet");
			
			System.out.println("name : " + ds.getString(0, "name"));
			System.out.println("title : " + ds.getString(0, "title"));
			System.out.println("pass : " + ds.getString(0, "pass"));
			System.out.println("content : " + ds.getString(0, "content"));

			System.out.println("ref : " + ds.getInt(0, "ref"));
			System.out.println("lev : " + ds.getInt(0, "lev"));
			System.out.println("step : " + ds.getInt(0, "step"));
			
			int seq = boardDao.getMaxSeq() + 1;
			int ref = ds.getInt(0, "ref");
			int step = ds.getInt(0, "step");
			int lev = ds.getInt(0, "lev");
			
			
			if (ref == 0) {
				System.out.println("초기�?");
				ref = seq;
			} else {
				BoardVO vo = new BoardVO();
				//if(votemp.getRef() == ref)
					//ref = board_seq;
				
				vo.setRef(ref);
				vo.setStep(step);
				boardDao.stepUp(vo);
				
				lev = lev + 1;
				step = step + 1;
			}
			
			board.setName(ds.getString(0, "name"));
			board.setTitle(ds.getString(0, "title"));
			board.setPass(ds.getString(0, "pass"));
			board.setContent(ds.getString(0, "content"));

			board.setRef(ref);
			board.setLev(lev);
			board.setStep(step);
			
			
			// set the ErrorCode and ErrorMsg about success
			nErrorCode = 0;
			strErrorMsg = boardDao.insert( board );

			System.out.println("board.getRef = " + board.getRef());
			System.out.println("board.getLev = " + board.getLev());
		} catch (Throwable th) {
			// set the ErrorCode and ErrorMsg about fail
			nErrorCode = -1;
			strErrorMsg = th.getMessage();
			th.printStackTrace();
			System.out.println("ERROR:" + strErrorMsg);
		} // try

		// save the ErrorCode and ErrorMsg for sending Client
		VariableList varList = pdata.getVariableList();

		varList.add("ErrorCode", nErrorCode);
		varList.add("ErrorMsg", strErrorMsg);

		// send the result data(XML) to Client
		HttpPlatformResponse res = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "UTF-8");
		res.setData(pdata);
		res.sendData(); // Send Data

		// System.out.println("insert in key field = " +
		// request.getParameter("keyField"));
		//
		// int fileMaxSize = 10*1024*1024;
		// String savePath = request.getRealPath("/upload").replaceAll("\\\\","/");
		// MultipartRequest mr;
		// try {
		// mr = new MultipartRequest(request,savePath,fileMaxSize,"UTF-8",new
		// DefaultFileRenamePolicy());
		// String seq1=mr.getParameter("seq");
		// String title=mr.getParameter("title");
		// String name=mr.getParameter("name");
		// String pass=mr.getParameter("pass");
		// String content=mr.getParameter("content");
		// String orgfilename=mr.getOriginalFileName("uploadFile");
		// String savefilename=mr.getFilesystemName("uploadFile");
		// if(orgfilename==null){
		// orgfilename="";
		// savefilename="";
		// }
		//// String ref1=mr.getParameter("ref");
		//// String lev1=mr.getParameter("lev");
		//// String step1=mr.getParameter("step");
		////
		//// System.out.println("ref 媛� : " + ref1);
		////
		// int ref=0;
		// int lev=0;
		// int step=0;
		//
		// int seq=boardDao.getMaxSeq()+1;
		//
		// if(!(seq1.equals(""))){
		// BoardVO vo2=boardDao.getInfo(Integer.parseInt(seq1));
		// String ref1=mr.getParameter("ref");
		// ref=Integer.parseInt(ref1);
		// String lev1=mr.getParameter("lev");
		// lev=Integer.parseInt(lev1);
		// String step1=mr.getParameter("step");
		// step=Integer.parseInt(step1);
		// vo2.setRef(ref);
		// vo2.setLev(lev);
		// vo2.setStep(step);
		// boardDao.stepUp(vo2);
		// lev+=1;
		// step+=1;
		//
		//
		//
		// }else{
		// ref=seq;
		// }
		//
		// BoardVO vo=new
		// BoardVO(seq,name,title,content,pass,orgfilename,savefilename,ref,lev,step);
		// boardDao.insert(vo);
		// System.out.println(savePath);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// return "redirect:list.do";
	}

	@RequestMapping(value = "/board/detail.do")
	public void detail(HttpServletRequest request, 
			HttpServletResponse response,BoardVO board) throws PlatformException {
		// Xplatform basic object creation
		
				PlatformData pdata = new PlatformData();

				int nErrorCode = 0;
				String strErrorMsg = "START";

				try {
					System.out.println("HttpPlatformRequest in");
					HttpPlatformRequest req = new HttpPlatformRequest(request);

					req.receiveData();
					pdata = req.getData();
					
					int seq = Integer.parseInt(request.getParameter("seq"));
					System.out.println("seq = " + seq);
					DataSet ds = new DataSet("bbsDataSet");
					DataSet fileds = new DataSet("fileDataSet");
					
					ds.addColumn("seq", DataTypes.INT, 256);
					ds.addColumn("title", DataTypes.STRING, 256);
					ds.addColumn("writer", DataTypes.STRING, 256);
					ds.addColumn("content", DataTypes.STRING, 1000);
					ds.addColumn("pass", DataTypes.STRING, 256);
					ds.addColumn("hit", DataTypes.INT, 256);
					ds.addColumn("regdate", DataTypes.DATE, 256);
					ds.addColumn("name", DataTypes.STRING, 256);
					
					
					ds.addColumn("ref", DataTypes.INT, 4);
					ds.addColumn("lev", DataTypes.INT, 4);
					ds.addColumn("step", DataTypes.INT, 4);
					
					fileds.addColumn("seq", DataTypes.INT, 256);
					fileds.addColumn("filecnt", DataTypes.INT, 256);
					fileds.addColumn("orgfilename", DataTypes.STRING, 256);
					fileds.addColumn("savefilename", DataTypes.STRING, 256);
					fileds.addColumn("filelocation", DataTypes.STRING, 1000);
					fileds.addColumn("location", DataTypes.STRING, 1000);
					fileds.addColumn("findex", DataTypes.INT, 1000);
					

//					ds.addColumn("keyField", DataTypes.STRING, 20);
//					ds.addColumn("KeyWord", DataTypes.STRING, 20);


					HashMap<String, Object> map = new HashMap();

					// map.put("keyField", keyField);
					// map.put("keyWord", keyWord);
					//
					// map.put("start", Integer.valueOf(page.getStartCount()));
					// map.put("end", Integer.valueOf(page.getEndCount()));
					int row = ds.newRow();
					
					BoardVO vo = boardDao.getInfo(seq);
					List<FileVO> filelist = boardDao.filegetInfo(seq);
					
					
					ds.set(row, "seq", vo.getSeq());
					ds.set(row, "title", vo.getTitle());
					ds.set(row, "content", vo.getContent());
					ds.set(row, "name", vo.getName());
					ds.set(row, "hit", vo.getHit());
					ds.set(row, "pass", vo.getPass());
					
					ds.set(row, "ref", vo.getRef());
					ds.set(row, "lev", vo.getLev());
					ds.set(row, "step", vo.getStep());
					
					for(int i=0; i<filelist.size(); i++) {
						int filerow = fileds.newRow();
						fileds.set(filerow, "filecnt", i+1);
						fileds.set(filerow, "seq", filelist.get(i).getSeq());						
						fileds.set(filerow, "orgfilename", filelist.get(i).getOrgfilename());
						fileds.set(filerow, "savefilename", filelist.get(i).getSavefilename());
						fileds.set(filerow, "filelocation", filelist.get(i).getFilelocation());
						fileds.set(filerow, "location", "Server");
					}
					
					boardDao.addHit(seq);
					pdata.addDataSet(ds);
					pdata.addDataSet(fileds);

					// set the ErrorCode and ErrorMsg about success
					nErrorCode = 0;
					strErrorMsg = "SUCC";

				} catch (Throwable th) {
					// set the ErrorCode and ErrorMsg about fail
					nErrorCode = -1;
					strErrorMsg = th.getMessage();
					System.out.println("ERROR:" + strErrorMsg);
					th.printStackTrace();
				} // try

				// save the ErrorCode and ErrorMsg for sending Client
				VariableList varList = pdata.getVariableList();

				varList.add("ErrorCode", nErrorCode);
				varList.add("ErrorMsg", strErrorMsg);

				// send the result data(XML) to Client
				HttpPlatformResponse res = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "UTF-8");

				res.setData(pdata);
				res.sendData(); // Send Data
	}

	@RequestMapping(value = "/board/download.do")
	public void download(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println();
		System.out.println("download.do in");
		
		String sRealPath = request.getSession().getServletContext()
				.getRealPath("/");
		String sPath = sRealPath + "upload";
		String sName = request.getParameter("file");
		String sFile = new String(sName.getBytes("iso8859-1"), "UTF-8");
		
		System.out.println("spath = " + sPath);
		System.out.println("sName = " + sName);
		System.out.println("sFile = " + sFile);
		byte[] buffer = new byte[1024];

		ServletOutputStream out_stream = null;
		BufferedInputStream in_stream = null;
		
		File fis = new File(sPath + "/" + sFile);

		if (fis.exists()) {
			try {
				response.setContentType("utf-8");
				response.setContentType("application/octet;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + sFile);
				
				// out.clear();
				// out = pageContext.pushBody();
				out_stream = response.getOutputStream();
				in_stream = new BufferedInputStream(new FileInputStream(fis));
				int nCnt = 0;
				while ((nCnt = in_stream.read(buffer, 0, 1024)) != -1) {
					out_stream.write(buffer, 0, nCnt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in_stream != null) {
					try {
						in_stream.close();
					} catch (Exception e) {
					}
				}
				if (out_stream != null) {
					try {
						out_stream.close();
					} catch (Exception e) {
					}
				}
			}
		} else {
			response.sendRedirect("unknownfile");
		}
	}

	@RequestMapping(value = "/board/delete.do")
	public void delete(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException {
		
		int seq = Integer.valueOf(request.getParameter("seq"));
		BoardVO vo = boardDao.getInfo(seq);

		PlatformData pdata = new PlatformData();

		int nErrorCode = 0;
		String strErrorMsg = "START";

		try {
			HttpPlatformRequest req = new HttpPlatformRequest(request);

			req.receiveData();
			pdata = req.getData();
			List<FileVO> list = boardDao.filegetInfo(seq);
			int n = boardDao.delete(seq);
			
			System.out.println("list.size() = " + list.size());
			if(!list.isEmpty()) {
				for(int i=0; i<list.size(); i++) {
					String savepath = list.get(i).getFilelocation();
					String filename = list.get(i).getSavefilename();
					
					File file = new File(savepath);
					if (file.exists()) {
						file.delete();
					} 
				}
			}
			
			if (n > 0) {
				strErrorMsg = "SUCC";
			} else {
				strErrorMsg = "FAIL";
			}
			
			nErrorCode = 0;

		} catch (Throwable th) {
			// set the ErrorCode and ErrorMsg about fail
			nErrorCode = -1;
			strErrorMsg = th.getMessage();
			System.out.println("ERROR:" + strErrorMsg);
			th.printStackTrace();
		} // try

		// save the ErrorCode and ErrorMsg for sending Client
		VariableList varList = pdata.getVariableList();

		varList.add("ErrorCode", nErrorCode);
		varList.add("ErrorMsg", strErrorMsg);

		// send the result data(XML) to Client
		HttpPlatformResponse res = new HttpPlatformResponse(response,
				PlatformType.CONTENT_TYPE_XML, "UTF-8");
		res.setData(pdata);
		res.sendData(); // Send Data


	}

	@RequestMapping(value = "/board/fileupload.do")
	public void fileUpload(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, PlatformException {
		
		System.out.println("/board/fileupload.do in");
		int seq = boardDao.getMaxSeq();
		//int seq = Integer.parseInt(request.getParameter("seq"));
		
		String sHeader = request.getHeader("Content-Type");
		if (sHeader == null) {
			return;
		}
		request.setCharacterEncoding("utf-8");
		String sRealPath = request.getSession().getServletContext()
				.getRealPath("/");
		String sPath = request.getParameter("PATH");
		String sUpPath = sRealPath + sPath;
		
		
		System.out.println(sUpPath);
		System.out.println("spath - " + sPath);

		int nMaxSize = 500 * 1024 * 1024; // 理쒕�? �뾽濡쒕�? �뙆�씪 �겕湲� 500MB(硫붽�?)濡� �젣�븳

		PlatformData resData = new PlatformData();
		VariableList resVarList = resData.getVariableList();

		String sMsg = " A ";
		try {
			MultipartRequest multi = new MultipartRequest(request, sUpPath,
					nMaxSize, "utf-8", new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();

			String sName = "";
			String savefilename = "";
			String orgfilename = "";
			int filesize = 0;
			File f = null;

			while (files.hasMoreElements()) {
				sName = (String) files.nextElement();
				savefilename = multi.getFilesystemName(sName);
				orgfilename = multi.getOriginalFileName(sName);
				f = multi.getFile(sName);
				
				if (f != null) {
					Long filesize_long = f.length();
					filesize = filesize_long.intValue();

					FileVO fileVO = new FileVO();
					
					fileVO.setSeq(seq);
					fileVO.setFilelocation(sUpPath + "\\" + savefilename);
					fileVO.setOrgfilename(orgfilename);
					fileVO.setSavefilename(savefilename);
					

					System.out.println("path = " + sUpPath);
					System.out.println("orgfilename = " + orgfilename);
					System.out.println("savefilename = " + savefilename);
					
					System.out.println("boardDao fileinsert = " + boardDao.fileInsert(fileVO));
				}

			}

			resVarList.add("ErrorCode", 200);
			resVarList.add("ErrorMsg", "fileUpload");
		} catch (Exception e) {
			resVarList.add("ErrorCode", 500);
			resVarList.add("ErrorMsg", sMsg + " " + e);
			e.printStackTrace();
		}

		HttpPlatformResponse res = new HttpPlatformResponse(response);
		res.setData(resData);
		res.sendData();
		
	}

	@RequestMapping(value = "/board/update.do")
	public void update(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, PlatformException {
		
		PlatformData pdata = new PlatformData();

		int nErrorCode = 0;
		String strErrorMsg = "START";

		try {
			// receive client request
			// not need to receive

			// create HttpPlatformRequest for receive data from client
			HttpPlatformRequest req = new HttpPlatformRequest(request);

			req.receiveData();
			pdata = req.getData();

			DataSet ds = pdata.getDataSet("bbsDataSet");
			
			System.out.println("title : " + ds.getString(0, "title"));
			System.out.println("content : " + ds.getString(0, "content"));
		
			// DAO
			BoardVO boardVO = new BoardVO();

			boardVO.setSeq(ds.getInt(0, "seq"));
			boardVO.setTitle(ds.getString(0, "title"));
			boardVO.setContent(ds.getString(0, "content"));
		
			boardDao.updateOk(boardVO);

			// set the ErrorCode and ErrorMsg about success
			nErrorCode = 0;
			strErrorMsg = "SUCC";

		} catch (Throwable th) {
			// set the ErrorCode and ErrorMsg about fail
			nErrorCode = -1;
			strErrorMsg = th.getMessage();
			System.out.println("ERROR:" + strErrorMsg);
			th.printStackTrace();
		} // try

		// save the ErrorCode and ErrorMsg for sending Client
		VariableList varList = pdata.getVariableList();

		varList.add("ErrorCode", nErrorCode);
		varList.add("ErrorMsg", strErrorMsg);

		// send the result data(XML) to Client
		HttpPlatformResponse res = new HttpPlatformResponse(response,
				PlatformType.CONTENT_TYPE_XML, "UTF-8");
		res.setData(pdata);
		res.sendData(); // Send Data
		
	}
	
	
	@RequestMapping(value = "/board/filedelete.do")
	public void fileDelete(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, PlatformException {
		System.out.println("filedelete.do");
		
		
		int seq = Integer.valueOf(request.getParameter("seq"));
		PlatformData pdata = new PlatformData();

		int nErrorCode = 0;
		String strErrorMsg = "START";

		try {
			HttpPlatformRequest req = new HttpPlatformRequest(request);

			req.receiveData();
			pdata = req.getData();
			DataSet delds = pdata.getDataSet("delfileDataSet");
			System.out.println(delds.getRowCount());
			
			if(delds.getRowCount() != 0) {
				for(int i=0; i<delds.getRowCount(); i++) {
					FileVO filevo = new FileVO();
					
					String savepath = delds.getString(i, "filelocation");
					String savefilename = delds.getString(i, "savefilename");
					
					System.out.println(delds.getString(i, "savefilename"));
					System.out.println(delds.getString(i, "filelocation"));
					System.out.println("seq = " + seq);
					filevo.setSeq(seq);
					filevo.setSavefilename(savefilename);
					
					boardDao.fileDelete(filevo);
					File file = new File(savepath);
					
					if (file.exists()) {
						file.delete();
					} 
				}
			}
			
			nErrorCode = 0;

		} catch (Throwable th) {
			// set the ErrorCode and ErrorMsg about fail
			nErrorCode = -1;
			strErrorMsg = th.getMessage();
			System.out.println("ERROR:" + strErrorMsg);
			th.printStackTrace();
		} // try

		// save the ErrorCode and ErrorMsg for sending Client
		VariableList varList = pdata.getVariableList();

		varList.add("ErrorCode", nErrorCode);
		varList.add("ErrorMsg", strErrorMsg);

		// send the result data(XML) to Client
		HttpPlatformResponse res = new HttpPlatformResponse(response,
				PlatformType.CONTENT_TYPE_XML, "UTF-8");
		res.setData(pdata);
		res.sendData(); // Send Data

	}
	
	@RequestMapping("/board/nexafileupload.do")
	public void NexacroFileupload(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException, UnsupportedEncodingException {
		System.out.println();
		System.out.println("nexafileupload.do in");
		String sHeader = request.getHeader("Content-Type");
	
		request.setCharacterEncoding("utf-8");
		String sRealPath = request.getSession().getServletContext().getRealPath("/");
		String sPath     = request.getParameter("PATH");
		String sUpPath   = sRealPath + sPath;
		int    nMaxSize  = 500 * 1024 * 1024; // 최�? ?��로드 ?��?�� ?���? 500MB(메�?)�? ?��?��
		
		PlatformData resData    = new PlatformData();
		VariableList resVarList = resData.getVariableList();
		
		String sMsg = " A ";
		try 
		{
			MultipartRequest multi = new MultipartRequest(request, sUpPath, nMaxSize, "utf-8", new DefaultFileRenamePolicy());
			Enumeration files      = multi.getFileNames();
			
			sMsg += "B ";
		
			sMsg += "C ";
			String sFName = "";
			String sName  = "";
			String stype  = "";
			File   f      = null;
			
			while (files.hasMoreElements()) 
			{
				sMsg += "D ";
				sName  = (String)files.nextElement();
				sFName = multi.getFilesystemName(sName);
				stype  = multi.getContentType(sName);
			
				f = multi.getFile(sName);			
			
			}
			
			resVarList.add("ErrorCode", 200);
			resVarList.add("ErrorMsg", sUpPath+"/"+sFName);
		} 
		catch (Exception e) 
		{
			resVarList.add("ErrorCode", 500);
			resVarList.add("ErrorMsg", sMsg + " " + e);
			e.printStackTrace();
		}

		HttpPlatformResponse res = new HttpPlatformResponse(response);
		res.setData(resData);
		res.sendData();
	}
	
	
}
