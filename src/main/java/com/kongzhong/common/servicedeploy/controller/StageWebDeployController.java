package com.kongzhong.common.servicedeploy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kongzhong.common.servicedeploy.constants.Constants;
import com.kongzhong.common.servicedeploy.domain.DeployInfo;
import com.kongzhong.common.servicedeploy.service.StageWebDeployService;
import com.kongzhong.common.servicedeploy.util.ShellUtil;

@Controller
@RequestMapping("stageweb")
public class StageWebDeployController {
	
	@Autowired
	private StageWebDeployService deployService;

	private String shellFileFolder = "/wucao/auto_deploy/stageweb_shell";
	
	/**
	 * 添加项目页面
	 */
	@RequestMapping("new")
	public ModelAndView newService() {
		ModelAndView mv = new ModelAndView("stageweb/new");
		return mv;
	}

	/**
	 * 添加项目请求
	 */
	@RequestMapping("insert")
	public String insert(DeployInfo deployInfo, HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		deployInfo.setUuid(uuid);
		deployService.insert(deployInfo, request.getRemoteUser());
		return "redirect:/stageweb/detail/" + uuid;
	}

	/**
	 * 详情页面
	 */
	@RequestMapping("detail/{uuid}")
	public ModelAndView detail(@PathVariable String uuid) {
		ModelAndView mv = new ModelAndView("stageweb/detail");
		mv.addObject("detail", deployService.getDetail(uuid));
		return mv;
	}

	/**
	 * ajax查看运行状态
	 */
	@ResponseBody
	@RequestMapping("status")
	public Map<String, Boolean> ajaxStatus(String uuid) throws IOException {

		Map<String, Boolean> resultMap = new HashMap<String, Boolean>();

		DeployInfo info = deployService.getDetail(uuid);
		if(info != null) {
			String out = ShellUtil.exec("sh " + shellFileFolder + "/isrunning.sh " + info.getUuid());
			resultMap.put("10.230.10.26", StringUtils.hasText(out) && out.contains("java -jar"));
		}

		return resultMap;
	}
	
	/**
	 * ajax部署
	 */
	@ResponseBody
	@RequestMapping(value = "deploy", produces = "text/plain;charset=UTF-8")
	public String ajaxDeploy(String uuid, HttpServletRequest request) throws IOException {

		deployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_DEPLOY);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = deployService.getDetail(uuid);

		if(info != null) {
			// 打包
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/package.sh " + info.getUuid() + " " + info.getSvn() + " " + info.getProfile()));
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + info.getUuid()));
			// 上传war包
			String contextPath = info.getContextPath();
			String warName = "root";
			if(contextPath.substring(1).length() > 0) {
				warName = contextPath.substring(1);
			}
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/upload.sh " + info.getUuid() + " " + info.getFinalName() + " " + warName));
			// 启动程序
			sb.append(ShellUtil.exec("sh", shellFileFolder + "/start.sh", info.getUuid(), String.valueOf(info.getPort())));
		} else {
			sb.append("服务不存在：" + uuid);
		}

		return sb.toString();
	}
	
	/**
	 * ajax重启
	 */
	@ResponseBody
	@RequestMapping(value = "restart", produces = "text/plain;charset=UTF-8")
	public String ajaxRestart(String uuid, HttpServletRequest request) throws IOException {

		deployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_RESTART);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = deployService.getDetail(uuid);

		if(info != null) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + info.getUuid()));
			// 启动程序
			sb.append(ShellUtil.exec("sh", shellFileFolder + "/start.sh", info.getUuid(), String.valueOf(info.getPort())));
		} else {
			sb.append("服务不存在：" + uuid);
		}

		return sb.toString();
	}
	
	/**
	 * ajax停止
	 */
	@ResponseBody
	@RequestMapping(value = "stop", produces = "text/plain;charset=UTF-8")
	public String ajaxStop(String uuid, HttpServletRequest request) throws IOException {

		deployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_STOP);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = deployService.getDetail(uuid);

		if(info != null) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + uuid));
		} else {
			sb.append("服务不存在：" + uuid);
		}

		return sb.toString();
	}

	/**
	 * ajax查看日志
	 */
	@ResponseBody
	@RequestMapping(value = "log", produces = "text/plain;charset=UTF-8")
	public String ajaxShowLog(String uuid) throws IOException {
		DeployInfo info = deployService.getDetail(uuid);
		if(info != null) {
			return ShellUtil.exec("sh " + shellFileFolder + "/showlog.sh " + uuid);
		} else {
			return "服务不存在：" + uuid;
		}
	}

}
