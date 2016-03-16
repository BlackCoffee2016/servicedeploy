package com.kongzhong.common.servicedeploy.service;

import java.util.List;

import com.kongzhong.common.servicedeploy.constants.Constants;
import com.kongzhong.common.servicedeploy.domain.LogInfo;
import com.kongzhong.common.servicedeploy.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kongzhong.common.servicedeploy.domain.DeployInfo;
import com.kongzhong.common.servicedeploy.mapper.LiveDeployMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LiveDeployService {
	
	@Autowired
	private LiveDeployMapper liveDeployMapper;

	@Autowired
	private LogMapper logMapper;

	public List<DeployInfo> getList() {
		return liveDeployMapper.getList();
	}

	public DeployInfo getDetail(String uuid) {
		return liveDeployMapper.getDetail(uuid);
	}

	@Transactional
	public void insert(DeployInfo liveDeployInfo, String username) {
		liveDeployMapper.insert(liveDeployInfo);

		LogInfo logInfo = new LogInfo();
		logInfo.setUuid(liveDeployInfo.getUuid());
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

	@Transactional
	public void delete(String uuid, String username) {
		liveDeployMapper.delete(uuid);

		LogInfo logInfo = new LogInfo();
		logInfo.setUuid(uuid);
		logInfo.setUsername(username);
		logInfo.setType(Constants.TYPE_DELETE);
		logMapper.insert(logInfo);
	}
}
