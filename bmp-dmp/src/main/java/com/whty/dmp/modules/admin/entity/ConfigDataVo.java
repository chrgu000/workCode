package com.whty.dmp.modules.admin.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whty.dmp.core.base.vo.DataEntity;
import com.whty.dmp.core.mybatis.Page;

/**
 * config_data 表，配置数据表
 * 这个表的create_time和update_time 并非通常认为的，
 * 而实际是指的数据同步的新增数据时间和更新数据时间，
 * 用来筛选数据用的起始时间
 * @author cjp
 * @date 2016年9月22日
 */
public class ConfigDataVo extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;// 数据名称
	private String code;// 数据编码
	private String dataType;// 数据类型
	private String jobStatus;// 状态：启用/不启用
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
	public ConfigDataVo(){}
	
	public ConfigDataVo(String code){
		this.code = code;
		this.jobStatus = DEL_FLAG_NORMAL;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
		
	
	

}
