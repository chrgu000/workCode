package com.whty.dmp.modules.dataExchange.entity;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 账号数据
 * @author cjp
 * @date 2016年9月12日
 */
public class UserRelInfoVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户群组关系中的群组类型 0:机构 1：学校 2:班级 3:群组 4:好友 5:兴趣小组 6:机构用户组 7:表示社区 8:社区小组
	 */
	public static final String GROUP_CATEGORY_ORGANIZATION = "0";// 机构，群组类型
	public static final String GROUP_CATEGORY_SCHOOL = "1";// 学校，群组类型
	public static final String GROUP_CATEGORY_CLASS = "2";// 班级，群组类型
	public static final String GROUP_CATEGORY_GROUP="3";//群组
	public static final String GROUP_CATEGORY_FRIEND="4";//好友
	public static final String GROUP_CATEGORY_INTEREST ="5";//兴趣小组
	public static final String GGROUP_CATEGOPY_USERGROUP = "6";//机构用户组/部门
	public static final String GROUP_CATEGORY_STATUS = "0";// 群组状态，正常为0
	public static final String GROUP_CATEGORY_SNS = "7"; //社区
	public static final String GROUP_CATEGORY_SNSGROUP = "8"; //社区小组
	public static final String GROUP_CATAGORY_PEC = "9" ;//央馆建立的特殊身份 也是一个群组
	
	/**
	 * 群组类型关系中状态 0:正常 1:待审批 2:已拒绝 3:删除
	 * */
	public static final String GROUP_ORGANREL_STATUS0 = "0";
	public static final String GROUP_ORGANREL_STATUS1 = "1";
	public static final String GROUP_ORGANREL_STATUS2 = "2";
	public static final String GROUP_ORGANREL_STATUS3 = "3";
	
	public UserRelInfoVo(){
		
	}
	
	public UserRelInfoVo(String userId, String orgId, String status,
			String category) {
		super();
		this.userId = userId;
		this.orgId = orgId;
		this.status = status;
		this.category = category;
	}
	
	
	/*数据交换*/
	private String userId; //用户ID
	private String orgId; //机构ID
	private String status; //机构状态
	private String category;  //机构类型
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
