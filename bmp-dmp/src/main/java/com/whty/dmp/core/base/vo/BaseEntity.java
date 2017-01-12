package com.whty.dmp.core.base.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whty.dmp.core.mybatis.Page;

/**
 * 基础Bean对象
 * @author cjp 2016年9月8日
 * @param <T>
 */
public abstract class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public BaseEntity(){}
	
	private String id;
	/**
	 * 当前实体分页
	 */
	//private Page page;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/*@JsonIgnore
	public Page getPage() {
		if (page == null) {
			page = new Page();
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	*/

}
