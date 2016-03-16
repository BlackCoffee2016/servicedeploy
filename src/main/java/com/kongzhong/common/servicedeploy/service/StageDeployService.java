package com.kongzhong.common.servicedeploy.service;

import com.kongzhong.common.servicedeploy.constants.Constants;
import com.kongzhong.common.servicedeploy.domain.DeployInfo;
import com.kongzhong.common.servicedeploy.domain.LogInfo;
import com.kongzhong.common.servicedeploy.mapper.LogMapper;
import com.kongzhong.common.servicedeploy.mapper.StageDeployMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StageDeployService {
	
	@Autowired
	private StageDeployMapper stageDeployMapper;

	@Autowired
	private LogMapper logMapper;

	public List<DeployInfo> getList() {
		return stageDeployMapper.getList();
	}

	public DeployInfo getDetail(String uuid) {
		return stageDeployMapper.getDetail(uuid);
	}

	@Transactional
	public void insert(DeployInfo deployInfo, String username) {
		stageDeployMapper.insert(deployInfo);

		LogInfo LogInfo = new LogInfo();
		LogInfo.setUuid(deployInfo.getUuid());
		LogInfo.setUsername(username);
		LogInfo.setType(Constants.TYPE_CREATE);
		logMapper.insert(LogInfo);

	}

	public void insertLog(String uuid, String username, int type) {
		LogInfo logInfo = new LogInfo();
		logInfo.setUuid(uuid);
		logInfo.setUsername(username);
		logInfo.setType(type);
		logMapper.insert(logInfo);
	}

	@Transactional
	public void delete(String uuid, String username) {
		stageDeployMapper.delete(uuid);

		LogInfo logInfo = new LogInfo();
		logInfo.setUuid(uuid);
		logInfo.setUsername(username);
		logInfo.setType(Constants.TYPE_DELETE);
		logMapper.insert(logInfo);
	}
}
