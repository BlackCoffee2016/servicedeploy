package com.kongzhong.common.servicedeploy.controller;

import com.kongzhong.common.servicedeploy.constants.Constants;
import com.kongzhong.common.servicedeploy.domain.DeployInfo;
import com.kongzhong.common.servicedeploy.service.StageDeployService;
import com.kongzhong.common.servicedeploy.util.ShellUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 测试环境（10.230.10.26）的后台服务自动化部署
 * @author wucao
 */
@Controller
@RequestMapping("stage")
public class StageDeployController {
	
	@Autowired
	private StageDeployService stageDeployService;

	private String shellFileFolder = "/wucao/auto_deploy/stage_shell";
	
	/**
	 * 添加项目页面
	 */
	@RequestMapping("new")
	public ModelAndView newService() {
		ModelAndView mv = new ModelAndView("stage/new");
		return mv;
	}

	/**
	 * 添加项目请求
	 */
	@RequestMapping("insert")
	public String insert(DeployInfo deployInfo, HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		deployInfo.setUuid(uuid);
		stageDeployService.insert(deployInfo, request.getRemoteUser());
		return "redirect:" + request.getContextPath() + "/stage/detail/" + uuid;
	}

	/**
	 * 详情页面
	 */
	@RequestMapping("detail/{uuid}")
	public ModelAndView detail(@PathVariable String uuid) {
		ModelAndView mv = new ModelAndView("stage/detail");
		mv.addObject("detail", stageDeployService.getDetail(uuid));
		return mv;
	}

	/**
	 * ajax查看运行状态
	 */
	@ResponseBody
	@RequestMapping("status")
	public Map<String, Boolean> ajaxStatus(String uuid) throws IOException {

		Map<String, Boolean> resultMap = new HashMap<String, Boolean>();

		DeployInfo info = stageDeployService.getDetail(uuid);
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

		stageDeployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_DEPLOY);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = stageDeployService.getDetail(uuid);

		if(info != null) {
			// 打包
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/package.sh " + info.getUuid() + " " + info.getSvn() + " " + info.getProfile()));
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + info.getUuid() + " " + info.isForceKill()));
			// 上传jar包
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/upload.sh " + info.getUuid() + " " + info.getFinalName()));
			// 启动程序
			sb.append(ShellUtil.exec("sh", shellFileFolder + "/start.sh", info.getUuid(), info.getFinalName(), info.getParameter()));
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

		stageDeployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_RESTART);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = stageDeployService.getDetail(uuid);

		if(info != null) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + info.getUuid() + " " + info.isForceKill()));
			// 启动程序
			sb.append(ShellUtil.exec("sh", shellFileFolder + "/start.sh", info.getUuid(), info.getFinalName(), info.getParameter()));
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

		stageDeployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_STOP);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = stageDeployService.getDetail(uuid);

		if(info != null) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + uuid + " " + info.isForceKill()));
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
		DeployInfo info = stageDeployService.getDetail(uuid);
		if(info != null) {
			return ShellUtil.exec("sh " + shellFileFolder + "/showlog.sh " + uuid);
		} else {
			return "服务不存在：" + uuid;
		}
	}

	/**
	 * ajax删除项目（目前弃用）
	 * @RequestMapping("delete")
	 */
	public String delete(String uuid, HttpServletRequest request) throws IOException {
		stageDeployService.delete(uuid, request.getRemoteUser());
		return "redirect:/";
	}

}
