package com.kongzhong.common.servicedeploy.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class DeployInfo {
	
	private String uuid;
	private String name;
	private String finalName;
	private String parameter;
	private boolean forceKill;
	private String server1;
	private String server2;
	private String server3;
	private String server4;
	private String svn;
	private String owner;
	private String profile;
	private String contextPath;
	private int port;
	public String getHttpUrl() {
		return "http://10.230.10.26:" + port + "/" + contextPath.substring(1);
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getUuid() {
		return uuid;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFinalName() {
		return finalName;
	}
	public void setFinalName(String finalName) {
		this.finalName = finalName;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public boolean isForceKill() {
		return forceKill;
	}
	public void setForceKill(boolean forceKill) {
		this.forceKill = forceKill;
	}
	public String getServer1() {
		return server1;
	}
	public void setServer1(String server1) {
		this.server1 = server1;
	}
	public String getServer2() {
		return server2;
	}
	public void setServer2(String server2) {
		this.server2 = server2;
	}
	public String getServer3() {
		return server3;
	}
	public void setServer3(String server3) {
		this.server3 = server3;
	}
	public String getServer4() {
		return server4;
	}
	public void setServer4(String server4) {
		this.server4 = server4;
	}
	public String getSvn() {
		return svn;
	}
	public void setSvn(String svn) {
		this.svn = svn;
	}

	public String getServers() {
		String servers = server1;
		if(StringUtils.hasText(server2)) {
			servers += "/" + server2;
		}
		if(StringUtils.hasText(server3)) {
			servers += "/" + server3;
		}
		if(StringUtils.hasText(server4)) {
			servers += "/" + server4;
		}
		return servers;
	}
	public List<String> getServerList() {
		List<String> list = new ArrayList<String>();
		list.add(server1);
		if(StringUtils.hasText(server2)) {
			list.add(server2);
		}
		if(StringUtils.hasText(server3)) {
			list.add(server3);
		}
		if(StringUtils.hasText(server4)) {
			list.add(server4);
		}
		return list;
	}
}