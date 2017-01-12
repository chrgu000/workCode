package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 机构数据表-t_organization
 * @author cjp
 * @date 2016年9月23日
 */
public class TbOrgaMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	/*数据交换*/
	private String orgaName; //机构名称
	private String orgaCode; //机构编码
	private String orgaDomain; //机构分类
	private String regionId;  //所属区域ID(area_code或orga_id)
	private String provinceCode; //省份编码
	private String cityCode; //城市编码
	private String areaCode; //区编码
	private String orgaType; //机构所属类型
	private String parentId;  //父机构ID
	private String status; //状态
	private Integer orgaLevel;  //级别
	private Integer sortNum; //序号
	private String orgaAddress; //机构地址
	//扩展字段
	private Integer operatorType;
	
	/**
	 * 当前时间
	 */
	private Date nowTime;
	
	public String getOrgaName() {
		return orgaName;
	}

	public void setOrgaName(String orgaName) {
		this.orgaName = orgaName;
	}

	public TbOrgaMsgVo(){}
	
	public TbOrgaMsgVo(Date nowTime){
		this.nowTime = nowTime;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getOrgaType() {
		return orgaType;
	}
	public void setOrgaType(String orgaType) {
		this.orgaType = orgaType;
	}
	public String getOrgaCode() {
		return orgaCode;
	}
	public void setOrgaCode(String orgaCode) {
		this.orgaCode = orgaCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getNowTime() {
		return nowTime;
	}

	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOrgaDomain() {
		return orgaDomain;
	}

	public void setOrgaDomain(String orgaDomain) {
		this.orgaDomain = orgaDomain;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrgaLevel() {
		return orgaLevel;
	}

	public void setOrgaLevel(Integer orgaLevel) {
		this.orgaLevel = orgaLevel;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getOrgaAddress() {
		return orgaAddress;
	}

	public void setOrgaAddress(String orgaAddress) {
		this.orgaAddress = orgaAddress;
	}

	
}
