package com.hycu.service;

import com.hycu.mapper.DlvMapper;
import com.hycu.model.DlvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DlvServiceImpl implements DlvService {
    @Autowired
    private DlvMapper dlvMapper;

    @Autowired
    public DlvServiceImpl(DlvMapper dlvMapper) {
        this.dlvMapper = dlvMapper;
    }

    @Override
    public int getTotalRecords(String userId) {
        return dlvMapper.getTotalRecords(userId);
    }

    @Override
    public List<DlvVO> getDlvInfo(int currentPage, int recordsPerPage,String usrId) {
        int offset = (currentPage - 1) * recordsPerPage;
        return dlvMapper.selectDlvInfoList(offset, recordsPerPage, usrId);
    }

	@Override
	public List<DlvVO> getDlvCorp() {
        return dlvMapper.getDlvCorp();
	}

	@Override
	public List<DlvVO> getMallCorp() {
        return dlvMapper.getMallCorp();
	}

	@Override
	public DlvVO getDlvMainCount(String userId) {
        return dlvMapper.getDlvMainCount(userId);

	}

	@Override
	public List<DlvVO> getTrckNum(String userId) {
        return dlvMapper.getTrckNum(userId);
	}
    
}