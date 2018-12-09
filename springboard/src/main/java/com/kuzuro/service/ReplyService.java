package com.kuzuro.service;

import java.util.List;

import com.kuzuro.domain.ReplyVO;

public interface ReplyService {

	// �뙎湲� 議고쉶
	public List<ReplyVO> readReply(int bno) throws Exception;

	// �뙎湲� �옉�꽦
	public void writeReply(ReplyVO vo) throws Exception;
	
	// �듅�젙 �뙎湲� 議고쉶
	public ReplyVO readReplySelect(int rno) throws Exception;
	
	// �뙎湲� �닔�젙
	public void replyUpdate(ReplyVO vo) throws Exception;
		
	// �뙎湲� �궘�젣
	public void replyDelete(ReplyVO vo) throws Exception;
	
	
}
