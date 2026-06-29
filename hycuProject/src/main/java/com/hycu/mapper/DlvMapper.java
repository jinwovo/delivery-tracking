package com.hycu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hycu.model.DlvVO;

public interface DlvMapper {
    void insertDlvInfo(DlvVO dlvVO);

	int selectDlvInfo(DlvVO dlvVO);

    int getTotalRecords(@Param("usrId") String usrId);

	List<DlvVO> getDlvCorp();

	List<DlvVO> getMallCorp();

	DlvVO getDlvMainCount(@Param("usrId") String usrId);

	List<DlvVO> selectDlvInfoList(@Param("offset") int offset, @Param("limit") int limit, @Param("usrId") String usrId);

	List<DlvVO> getTrckNum(@Param("usrId") String usrId);

}