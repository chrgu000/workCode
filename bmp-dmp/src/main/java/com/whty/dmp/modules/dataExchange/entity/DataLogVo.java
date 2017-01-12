package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whty.dmp.core.base.vo.DataEntity;
import com.whty.dmp.core.mybatis.Page;

/**
 * 数据日志表
 * 对应data_publish_log和data_subscribe_log
 * 为了数据分开展示，所以存两个表
 * @author cjp
 * @date 2016年9月18日
 */
public class DataLogVo extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	private String objId;//对象ID
	private String serviceCode;//服务码--也可以决定了数据的类型
	private String dataJson;//数据流--json串
	private String data;//数据类型(0：机构、1:用户、2:班级、3、账号、4、用户班级)
	private String operatorType;//操作类型(1:新增、2:修改、3:删除)
	private String dataStatus;//数据状态(0:成功、1:失败)
	private String errorMsg;//错误信息
	private Page page;
	
	@JsonIgnore
	public Page getPage() {
		if (page == null) {
			page = new Page();
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	public DataLogVo(){}
	
	public DataLogVo(String serviceCode,String operatorType,String data,Date createTime){
		this.serviceCode = serviceCode;
		this.data = data;
		this.operatorType = operatorType;
		this.setCreateTime(createTime);
	}
	
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getDataJson() {
		return dataJson;
	}
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	

}
