package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 账号数据
 * @author cjp
 * @date 2016年9月12日
 */
public class TbUserOrgaMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	public TbUserOrgaMsgVo(){}
	
	public TbUserOrgaMsgVo(Date nowTime){
		this.nowTime = nowTime;
	}
	
	/*数据交换*/
	private String userId; //用户ID
	private String orgaId; //班级信息ID
	private String orgaDomain; //机构分类（法人，非法人）
	private String orgaType;  //机构类型(机构、年级、班级)
	private String status;  //状态
	//扩展字段
	private Integer operatorType;
	/**
	 * 当前时间
	 */
	private Date nowTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgaId() {
		return orgaId;
	}
	public void setOrgaId(String orgaId) {
		this.orgaId = orgaId;
	}


	public String getOrgaDomain() {
		return orgaDomain;
	}

	public void setOrgaDomain(String orgaDomain) {
		this.orgaDomain = orgaDomain;
	}

	public String getOrgaType() {
		return orgaType;
	}

	public void setOrgaType(String orgaType) {
		this.orgaType = orgaType;
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
