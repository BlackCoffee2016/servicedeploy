package com.kongzhong.common.servicedeploy.service;

import java.util.List;

import com.kongzhong.common.servicedeploy.constants.Constants;
import com.kongzhong.common.servicedeploy.domain.LogInfo;
import com.kongzhong.common.servicedeploy.mapper.LogMapper;
import com.kongzhong.common.servicedeploy.mapper.StageWebDeployMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongzhong.common.servicedeploy.domain.DeployInfo;

import org.springframework.transaction.annotation.Transactional;

@Service
public class StageWebDeployService {
	
	@Autowired
	private StageWebDeployMapper stageWebDeployMapper;

	@Autowired
	private LogMapper logMapper;

	public List<DeployInfo> getList() {
		return stageWebDeployMapper.getList();
	}

	public DeployInfo getDetail(String uuid) {
		return stageWebDeployMapper.getDetail(uuid);
	}

	@Transactional
	public void insert(DeployInfo deployInfo, String username) {
		stageWebDeployMapper.insert(deployInfo);

		LogInfo logInfo = new LogInfo();
		logInfo.setUuid(deployInfo.getUuid());
		logInfo.setUsername(username);
		logInfo.setType(Constants.TYPE_CREATE);
		logMapper.insert(logInfo);

	}

	public void insertLog(String uuid, String username, int type) {
		LogInfo logInfo = new LogInfo();
		logInfo.setUuid(uuid);
		logInfo.setUsername(username);
		logInfo.setType(type);
		logMapper.insert(logInfo);
	}

}
