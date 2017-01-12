package com.whty.dmp.modules.dataExchange.entity;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 账号数据
 * @author cjp
 * @date 2016年9月12日
 */
public class UserIdentityInfoVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
    //用户身份ID
    public static final String USER_IDENTITY_CLASS_HEADTEACHER = "1";//班主任
    public static final String USER_IDENTITY_CLASS_CLASSMONITOR = "2";//班长
    public static final String USER_IDENTITY_MANAGER = "3";//管理员
    public static final String USER_IDENTITY_TERCHER = "4";//教师
    public static final String USER_IDENTITY_MEMBER = "5";//普通成员
	
	/*数据交换*/
	private String userId; //用户ID
	private String orgId; //机构ID
	private String identityId; //身份ID
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
}
