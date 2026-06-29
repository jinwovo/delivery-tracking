package com.hycu.model;

import java.time.LocalDate;

public class MemberVO {
		private String usrId;
	    private String passWd;
	    private String usrNm;
	    private String usrPhone;
	    private String usrEmail;
		public String getUsrEmail() {
			return usrEmail;
		}
		public void setUsrEmail(String usrEmail) {
			this.usrEmail = usrEmail;
		}
		private String usrBirth;
	    private String usrRole;
	    private String sysRegDtime;
	    private String sysModDtime;
	    
	    public String getUsrId() {
			return usrId;
		}
		public void setUsrId(String usrId) {
			this.usrId = usrId;
		}
		public String getPassWd() {
			return passWd;
		}
		public void setPassWd(String passWd) {
			this.passWd = passWd;
		}
		public String getUsrNm() {
			return usrNm;
		}
		public void setUsrNm(String usrNm) {
			this.usrNm = usrNm;
		}
		public String getUsrPhone() {
			return usrPhone;
		}
		public void setUsrPhone(String usrPhone) {
			this.usrPhone = usrPhone;
		}
		@Override
		public String toString() {
			return "MemberVO [usrId=" + usrId + ", passWd=" + passWd + ", usrNm=" + usrNm + ", usrPhone=" + usrPhone
					+ ", usrEmail=" + usrEmail + ", usrBirth=" + usrBirth + ", usrRole=" + usrRole + ", sysRegDtime="
					+ sysRegDtime + ", sysModDtime=" + sysModDtime + ", getUsrId()=" + getUsrId() + ", getPassWd()="
					+ getPassWd() + ", getUsrNm()=" + getUsrNm() + ", getUsrPhone()=" + getUsrPhone()
					+ ", getUsrBirth()=" + getUsrBirth() + ", getUsrRole()=" + getUsrRole() + ", getSysRegDtime()="
					+ getSysRegDtime() + ", getSysModDtime()=" + getSysModDtime() + ", getClass()=" + getClass()
					+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
		}
		public String getUsrBirth() {
			return usrBirth;
		}
		public void setUsrBirth(String usrBirth) {
			this.usrBirth = usrBirth;
		}
		public String getUsrRole() {
			return usrRole;
		}
		public void setUsrRole(String usrRole) {
			this.usrRole = usrRole;
		}
		public String getSysRegDtime() {
			return sysRegDtime;
		}
		public void setSysRegDtime(String sysRegDtime) {
			this.sysRegDtime = sysRegDtime;
		}
		public String getSysModDtime() {
			return sysModDtime;
		}
		public void setSysModDtime(String sysModDtime) {
			this.sysModDtime = sysModDtime;
		}
		

}
