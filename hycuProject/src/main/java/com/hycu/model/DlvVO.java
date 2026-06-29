package com.hycu.model;

public class DlvVO {
    private String trckNum;
    private String dlvStat;
    private String dlvCompanyCode;
    private String goodsNm;
    private String dlvCompanyName;
    private String shopCode;
    private String shopName;
    private String dlvNotFin;
    private String dlvFin;
    private String dlvStrange;
	private String sysRegDtime;
	private String sysRegDtime2;
	private String usrId;
	private String dlvNow;

    public String getDlvNow() {
		return dlvNow;
	}
	public void setDlvNow(String dlvNow) {
		this.dlvNow = dlvNow;
	}
	public String getShopCode() {
		return shopCode;
	}
	public String getSysRegDtime2() {
		return sysRegDtime2;
	}
	public void setSysRegDtime2(String sysRegDtime2) {
		this.sysRegDtime2 = sysRegDtime2;
	}
	public String getDlvNotFin() {
		return dlvNotFin;
	}
	public void setDlvNotFin(String dlvNotFin) {
		this.dlvNotFin = dlvNotFin;
	}
	public String getDlvFin() {
		return dlvFin;
	}
	public void setDlvFin(String dlvFin) {
		this.dlvFin = dlvFin;
	}
	public String getDlvStrange() {
		return dlvStrange;
	}
	public void setDlvStrange(String dlvStrange) {
		this.dlvStrange = dlvStrange;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getSysRegDtime() {
		return sysRegDtime;
	}
	public void setSysRegDtime(String sysRegDtime) {
		this.sysRegDtime = sysRegDtime;
	}
    
    public String getDlvCompanyName() {
		return dlvCompanyName;
	}
	public void setDlvCompanyName(String dlvCompanyName) {
		this.dlvCompanyName = dlvCompanyName;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getGoodsNm() {
		return goodsNm;
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
	public String getDlvCompanyCode() {
		return dlvCompanyCode;
	}
	public void setDlvCompanyCode(String dlvCompanyCode) {
		this.dlvCompanyCode = dlvCompanyCode;
	}
	public String getTrckNum() {
		return trckNum;
	}
	public void setTrckNum(String trckNum) {
		this.trckNum = trckNum;
	}
	public String getDlvStat() {
		return dlvStat;
	}
	public void setDlvStat(String dlvStat) {
		this.dlvStat = dlvStat;
	}
	@Override
	public String toString() {
		return "DlvVO [trckNum=" + trckNum + ", dlvStat=" + dlvStat + ", dlvCompanyCode=" + dlvCompanyCode
				+ ", goodsNm=" + goodsNm + ", dlvCompanyName=" + dlvCompanyName + ", shopCode=" + shopCode
				+ ", shopName=" + shopName + ", dlvNotFin=" + dlvNotFin + ", dlvFin=" + dlvFin + ", dlvStrange="
				+ dlvStrange + ", sysRegDtime=" + sysRegDtime + ", usrId=" + usrId + ", dlvNow=" + dlvNow + "]";
	}

    
}