package com.hycu.service;

import com.hycu.model.DlvVO;

import java.util.List;

public interface DlvService {
    int getTotalRecords(String userId);

    List<DlvVO> getDlvInfo(int currentPage, int recordsPerPage,String userId);

	List<DlvVO> getDlvCorp();

	List<DlvVO> getMallCorp();

	DlvVO getDlvMainCount(String userId);

	List<DlvVO> getTrckNum(String userId);

	/** 배송 레코드 1건 적재 (폴링 시점마다 append). */
	void saveDelivery(DlvVO dlv);
}