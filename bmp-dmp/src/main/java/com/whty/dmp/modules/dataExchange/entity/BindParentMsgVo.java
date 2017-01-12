package com.whty.dmp.modules.dataExchange.entity;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 家长学生关系信息数据  对应表 t_bind_paernt 
 * @author zhangmingxing
 * @date 2016年9月12日
 */
public class BindParentMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	//学生/家长邦定状态 0：邦定 1：解邦 2：待审核 
	public static final String PARENT_STATUS_BIND   = "0";
	public static final String PARENT_STATUS_UNBIND = "1";
	public static final String PARENT_STATUS_VERIFY = "2";
	
	
	/*数据交换*/
	private String id; //ID  对应的表字段名称是 bidn_id
	private String parentId; //家长用户ID
	private String childId; //学生用户ID

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	
}
