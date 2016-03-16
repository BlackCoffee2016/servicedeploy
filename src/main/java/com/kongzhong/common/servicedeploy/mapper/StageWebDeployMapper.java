package com.kongzhong.common.servicedeploy.mapper;

import com.kongzhong.common.servicedeploy.domain.DeployInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StageWebDeployMapper {

	@Select("select uuid,name,final_name as finalName,context_path as ContextPath,port,svn,owner,profile"
			+ " from web_application_stage where is_delete=0")
	public List<DeployInfo> getList();

	@Select("select uuid,name,final_name as finalName,context_path as ContextPath,port,svn,owner,profile"
			+ " from web_application_stage where uuid=#{uuid} and is_delete=0")
	public DeployInfo getDetail(String uuid);

	@Insert("insert into web_application_stage (uuid,name,final_name,context_path,port,svn,owner,profile)"
			+ " values (#{uuid},#{name},#{finalName},#{contextPath},#{port},#{svn},#{owner},#{profile})")
	public void insert(DeployInfo deployInfo);
}
