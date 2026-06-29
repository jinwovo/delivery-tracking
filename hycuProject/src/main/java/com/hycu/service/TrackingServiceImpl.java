package com.hycu.service;

import com.hycu.model.DlvVO;
import com.hycu.model.TrackingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

    private final SweetTrackerClient client;
    private final DlvService dlvService;

    @Autowired
    public TrackingServiceImpl(SweetTrackerClient client, DlvService dlvService) {
        this.client = client;
        this.dlvService = dlvService;
    }

    @Override
    public boolean register(String trckNum, String companyCode, String goodsNm, String shopCode, String userId) {
        TrackingResponse resp = client.fetch(companyCode, trckNum);
        if (!resp.isOk()) {
            return false;
        }

        DlvVO dlv = new DlvVO();
        dlv.setTrckNum(trckNum);
        dlv.setDlvCompanyCode(companyCode);
        dlv.setShopCode(shopCode);
        dlv.setUsrId(userId);

        if (resp.isNotFound()) {            // 104: 등록만 하고 추적 정보는 없음
            dlv.setDlvStat("00");
            dlv.setGoodsNm(goodsNm);
        } else {
            if (resp.getLevel() == null) {  // 상태를 알 수 없으면 저장하지 않음
                return false;
            }
            dlv.setDlvStat("0" + resp.getLevel());
            dlv.setGoodsNm(resp.getItemName());
        }

        dlvService.saveDelivery(dlv);
        return true;
    }

    @Override
    public void refreshUserDeliveries(String userId) {
        List<DlvVO> targets = dlvService.getTrckNum(userId);
        for (DlvVO target : targets) {
            TrackingResponse resp = client.fetch(target.getDlvCompanyCode(), target.getTrckNum());
            if (resp.isOk() && !resp.isNotFound() && resp.getLevel() != null) {
                DlvVO dlv = new DlvVO();
                dlv.setTrckNum(target.getTrckNum());
                dlv.setDlvCompanyCode(target.getDlvCompanyCode());
                dlv.setDlvStat("0" + resp.getLevel());
                dlv.setGoodsNm(resp.getItemName());
                dlv.setUsrId(userId);
                dlvService.saveDelivery(dlv);
            }
        }
    }
}
