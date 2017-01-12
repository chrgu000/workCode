package com.whty.dmp.core.base.vo;

import java.util.Date;

/**
 * 继承基础Entity
 * 包含创建、更新、删除标记
 * @author cjp 2016年9月11日
 */
public abstract class DataEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	public static final String CREATE_USER = "dmp_user";
	
	private Date createTime; //创建时间
	private String createBy; //创建人
	private Date updateTime; //更新时间
	private String updateBy; //更新人
	private String delFlag; //删除标记
	private String dataExFrom; //数据来源（0：本地、1：数据交换）
	private String dataExFlag;  //数据标记状态（0：新增未同步，1：修改未同步，2：删除未同步，9：已同步）
	
	public DataEntity() {
		super();
		//this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getDataExFrom() {
		return dataExFrom;
	}

	public void setDataExFrom(String dataExFrom) {
		this.dataExFrom = dataExFrom;
	}

	public String getDataExFlag() {
		return dataExFlag;
	}

	public void setDataExFlag(String dataExFlag) {
		this.dataExFlag = dataExFlag;
	}
	
}
