package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 账号数据
 * @author cjp
 * @date 2016年9月12日
 */
public class TbUserMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
		
	/*数据交换*/
	private String name; //用户姓名
	private String account; //帐号
	private String defaultIdentity; //默认身份
	private String password;  //密码
	private String phone; //手机
	private String wechat;  //微信号
	private String qqNum;  //QQ号
	private String email;  //邮箱
	private String idCardNo; //身份证
	private String schoolRollNo; //学籍号
	private String birthday;  //出生年月
	private String gender;  //性别
	private String provinceCode;  //省份编码
	private String cityCode;  //市级编码
	private String areaCode;  //区域编码
	private String address;  //住址
	private String activeState;  //激活状态（是否使用过  0：已激活，1：未激活）
	private String defaultPwd;  //初始化密码
	private String status;  //状态
	
	private Integer operatorType; //操作类型
	private Date nowTime; //当前时间
	
	public TbUserMsgVo(){}
	
	public TbUserMsgVo(Date nowTime){
		this.nowTime = nowTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDefaultIdentity() {
		return defaultIdentity;
	}

	public void setDefaultIdentity(String defaultIdentity) {
		this.defaultIdentity = defaultIdentity;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getSchoolRollNo() {
		return schoolRollNo;
	}

	public void setSchoolRollNo(String schoolRollNo) {
		this.schoolRollNo = schoolRollNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public String getDefaultPwd() {
		return defaultPwd;
	}

	public void setDefaultPwd(String defaultPwd) {
		this.defaultPwd = defaultPwd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
	
}
