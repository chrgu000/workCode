package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 账号数据
 * @author cjp
 * @date 2016年9月12日
 */
public class TbUserIdentityMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	public TbUserIdentityMsgVo(){}
	
	public TbUserIdentityMsgVo(Date nowTime){
		this.nowTime = nowTime;
	}
	
	/*数据交换*/
	private String userId; //用户ID
	private String identityId; //身份ID
	private String orgaId;  //所在机构ID
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
	
	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getOrgaId() {
		return orgaId;
	}

	public void setOrgaId(String orgaId) {
		this.orgaId = orgaId;
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
