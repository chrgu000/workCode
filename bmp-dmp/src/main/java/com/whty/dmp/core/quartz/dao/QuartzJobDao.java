package com.whty.dmp.core.quartz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whty.dmp.core.quartz.vo.QuartzJobVo;

@Repository
public interface QuartzJobDao {
	
	public QuartzJobVo selectOne(QuartzJobVo queryBean);
	
	public List<QuartzJobVo> selectList(QuartzJobVo queryBean);
	
	public int delete(QuartzJobVo queryBean);
	
	public int insert(QuartzJobVo queryBean);
	
	public int update(QuartzJobVo queryBean);
}
