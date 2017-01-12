package com.whty.dmp.modules.dataExchange.disruptor;

import java.util.List;

/**
 * @author xiaom
 *
 */
public class SendMsg {
	
	public SendMsg(long id, List<?> data, String serviceCode) {
		this.id = id;
		this.data = data;
		this.serviceCode = serviceCode;
	}
	
	public SendMsg(){
		
	}

	private long id;
	
	private List<?> data;
	
	private String serviceCode;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
