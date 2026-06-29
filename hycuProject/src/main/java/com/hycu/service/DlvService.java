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
}