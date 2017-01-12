package com.whty.dmp.modules.dataExchange.entity;

import java.util.Date;

import com.whty.dmp.core.base.vo.DataEntity;

/**
 * 班级信息数据
 * @author zhangmingxing
 * @date 2016年9月13日
 */
public class ClassInfoMsgVo extends DataEntity{
	
	private static final long serialVersionUID = 1L;
	
	/*数据交换*/
	private String id; //
	private String classCode; //班级编码
	private String className; //班级名称
	private String classType; //学校类型
	private String foundTime;   //成立年份
	private String grade;     //年级
	private String gradeClass;  //班别
	private String graduationTime; //毕业年份
	private String orgaId;     //班级所在机构ID
	private String status;     //状态
	private String studyPhase;  //班级所属学段
	
	private Date nowTime;  //当前时间
	private Integer operatorType; //操作类型
	
	public ClassInfoMsgVo(){}
	
	public ClassInfoMsgVo(Date nowTime){
		this.nowTime = nowTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getFoundTime() {
		return foundTime;
	}
	public void setFoundTime(String foundTime) {
		this.foundTime = foundTime;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGradeClass() {
		return gradeClass;
	}
	public void setGradeClass(String gradeClass) {
		this.gradeClass = gradeClass;
	}
	public String getGraduationTime() {
		return graduationTime;
	}
	public void setGraduationTime(String graduationTime) {
		this.graduationTime = graduationTime;
	}
	public String getOrgaId() {
		return orgaId;
	}
	public void setOrgaId(String orgaId) {
		this.orgaId = orgaId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStudyPhase() {
		return studyPhase;
	}
	public void setStudyPhase(String studyPhase) {
		this.studyPhase = studyPhase;
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
	
}
