package com.kongzhong.common.servicedeploy.mapper;

import com.kongzhong.common.servicedeploy.domain.DeployInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StageDeployMapper {

	@Select("select uuid,name,final_name as finalName,parameter,force_kill as forceKill,owner"
			+ " from background_service_stage where is_delete=0")
	public List<DeployInfo> getList();

	@Select("select uuid,name,final_name as finalName,parameter,force_kill as forceKill,svn,owner,profile"
			+ " from background_service_stage where uuid=#{uuid} and is_delete=0")
	public DeployInfo getDetail(String uuid);

	@Insert("insert into background_service_stage (uuid,name,final_name,parameter,force_kill,svn,owner,profile)"
			+ " values (#{uuid},#{name},#{finalName},#{parameter},#{forceKill},#{svn},#{owner},#{profile})")
	public void insert(DeployInfo stageDeployInfo);

	@Update("update background_service_stage set is_delete=1 where uuid=#{uuid}")
	public void delete(String uuid);
}
