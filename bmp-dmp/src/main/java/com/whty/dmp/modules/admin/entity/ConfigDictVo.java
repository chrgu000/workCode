package com.whty.dmp.modules.admin.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whty.dmp.core.base.vo.DataEntity;
import com.whty.dmp.core.mybatis.Page;

/**
 * config_dict 表，配置字典表
 * @author cjp
 * @date 2016年9月21日
 */
public class ConfigDictVo extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;// 名称
	private String dKey;// 键
	private String dValue;// 值
	private String type;// 类型
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
	public ConfigDictVo(){
		super();
	}
	
	public ConfigDictVo(String type){
		this.type = type;
	}
		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getdKey() {
		return dKey;
	}

	public void setdKey(String dKey) {
		this.dKey = dKey;
	}

	public String getdValue() {
		return dValue;
	}

	public void setdValue(String dValue) {
		this.dValue = dValue;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
