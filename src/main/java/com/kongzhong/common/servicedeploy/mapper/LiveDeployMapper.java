package com.kongzhong.common.servicedeploy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.kongzhong.common.servicedeploy.domain.DeployInfo;
import org.apache.ibatis.annotations.Update;

public interface LiveDeployMapper {

	@Select("select uuid,name,final_name as finalName,parameter,force_kill as forceKill,"
			+ "server1,server2,server3,server4,owner from background_service where is_delete=0")
	public List<DeployInfo> getList();

	@Select("select uuid,name,final_name as finalName,parameter,force_kill as forceKill,svn,"
			+ "server1,server2,server3,server4,owner from background_service where uuid=#{uuid} and is_delete=0")
	public DeployInfo getDetail(String uuid);

	@Insert("insert into background_service (uuid,name,final_name,parameter,force_kill,server1,server2,server3,server4,svn,owner)"
			+ " values (#{uuid},#{name},#{finalName},#{parameter},#{forceKill},#{server1},#{server2},#{server3},#{server4},#{svn},#{owner})")
	public void insert(DeployInfo liveDeployInfo);

	@Update("update background_service set is_delete=1 where uuid=#{uuid}")
	public void delete(String uuid);
}
