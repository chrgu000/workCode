package com.whty.dmp.modules.dataExchange.common;

import java.io.Serializable;

/**
 * 数据信息Vo
 * @author cjp
 * @date 2016年9月12日
 */
public class DataMsgVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final Integer OPER_INSERT = 1;
	public static final Integer OPER_UPDATE = 2;
	public static final Integer OPER_DELETE = 3;
	
	private Object obj;//数据对象
	private Integer operatorType;//操作类型（1：新增、2：修改、3：删除）
	private String platCode;//平台类型
	
	public DataMsgVo(){}
	
	public DataMsgVo(Object obj,Integer operatorType,String platCode){
		this.obj = obj;
		this.operatorType = operatorType;
		this.platCode = platCode;
	}
	
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
}
