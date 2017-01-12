package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 账号数据
 * @author cjp
 * @date 2016年9月12日
 */
public class AccountMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户账号状态 0:正常 1:未激活 2:锁定
	 * */
	public static final String ACCOUNT_STATUS0 = "0";
	public static final String ACCOUNT_STATUS1 = "1";
	public static final String ACCOUNT_STATUS2 = "2";
	public static final String ACCOUNT_STATUS3 = "3";
	public static final String ACCOUNT_STATUS_CANCEL = "-1";
	
	
	/*数据交换*/
	private String account; //账号
	private String userId; //用户ID
	private String password; //用户密码
	private String status; //状态
	
	/*额外字段*/
	private String sysGenerator;//是否系统生成
	private Integer accountType;//账号类型
	private Date endTime;//截止时间
	
	private Date nowTime;  //当前时间
	private Integer operatorType; //操作类型
	
	public AccountMsgVo(){}
	
	public AccountMsgVo(Date nowTime){
		this.nowTime = nowTime;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSysGenerator() {
		return sysGenerator;
	}
	public void setSysGenerator(String sysGenerator) {
		this.sysGenerator = sysGenerator;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	

}
