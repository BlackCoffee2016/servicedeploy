package com.kongzhong.common.servicedeploy.controller;

import com.kongzhong.common.servicedeploy.constants.Constants;
import com.kongzhong.common.servicedeploy.domain.DeployInfo;
import com.kongzhong.common.servicedeploy.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kongzhong.common.servicedeploy.service.LiveDeployService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 线上环境的后台服务自动化部署
 * @author wucao
 */
@Controller
@RequestMapping("live")
public class LiveDeployController {
	
	@Autowired
	private LiveDeployService liveDeployService;

	private String shellFileFolder = "/wucao/auto_deploy/live_shell";
	
	/**
	 * 添加项目页面
	 */
	@RequestMapping("new")
	public ModelAndView newService() {
		ModelAndView mv = new ModelAndView("live/new");
		return mv;
	}

	/**
	 * 添加项目请求
	 */
	@RequestMapping("insert")
	public String insert(DeployInfo liveDeployInfo, HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		liveDeployInfo.setUuid(uuid);
		liveDeployService.insert(liveDeployInfo, request.getRemoteUser());
		return "redirect:" + request.getContextPath() + "/live/detail/" + uuid;
	}

	/**
	 * 详情页面
	 */
	@RequestMapping("detail/{uuid}")
	public ModelAndView detail(@PathVariable String uuid) {
		ModelAndView mv = new ModelAndView("live/detail");
		mv.addObject("detail", liveDeployService.getDetail(uuid));
		return mv;
	}

	/**
	 * ajax查看运行状态
	 */
	@ResponseBody
	@RequestMapping("status")
	public Map<String, Boolean> ajaxStatus(String uuid) throws IOException {

		Map<String, Boolean> resultMap = new HashMap<String, Boolean>();

		DeployInfo info = liveDeployService.getDetail(uuid);
		List<String> ips = info.getServerList();
		for(String ip : ips) {
			String out = ShellUtil.exec("sh " + shellFileFolder + "/isrunning.sh " + ip + " " + uuid);
			resultMap.put(ip, StringUtils.hasText(out) && out.contains("java -jar"));
		}
		return resultMap;
	}
	
	/**
	 * ajax部署
	 */
	@ResponseBody
	@RequestMapping(value = "deploy", produces = "text/plain;charset=UTF-8")
	public String ajaxDeploy(String uuid, HttpServletRequest request) throws IOException {

		liveDeployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_DEPLOY);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = liveDeployService.getDetail(uuid);
		List<String> ips = info.getServerList();

		// 打包
		sb.append(ShellUtil.exec("sh " + shellFileFolder + "/package.sh " + uuid + " " + info.getSvn()) + "\n\n");

		for(String ip : ips) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + ip + " " + uuid + " " + info.isForceKill()) + "\n\n");
			// 上传jar包
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/upload.sh " + ip + " " + uuid + " " + info.getFinalName()) + "\n\n");
			// 启动程序
			sb.append(ShellUtil.exec("sh", shellFileFolder + "/start.sh", ip, uuid, info.getFinalName(), info.getParameter().replace("{ip}", ip)) + "\n\n");
		}
		return sb.toString();
	}
	
	/**
	 * ajax重启
	 */
	@ResponseBody
	@RequestMapping(value = "restart", produces = "text/plain;charset=UTF-8")
	public String ajaxRestart(String uuid, HttpServletRequest request) throws IOException {

		liveDeployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_RESTART);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = liveDeployService.getDetail(uuid);
		List<String> ips = info.getServerList();

		for(String ip : ips) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + ip + " " + uuid + " " + info.isForceKill()) + "\n\n");
			// 启动程序
			sb.append(ShellUtil.exec("sh", shellFileFolder + "/start.sh", ip, uuid, info.getFinalName(), info.getParameter().replace("{ip}", ip)) + "\n\n");
		}
		return sb.toString();
	}
	
	/**
	 * ajax停止
	 */
	@ResponseBody
	@RequestMapping(value = "stop", produces = "text/plain;charset=UTF-8")
	public String ajaxStop(String uuid, HttpServletRequest request) throws IOException {

		liveDeployService.insertLog(uuid, request.getRemoteUser(), Constants.TYPE_STOP);

		StringBuilder sb = new StringBuilder();

		DeployInfo info = liveDeployService.getDetail(uuid);
		List<String> ips = info.getServerList();

		for(String ip : ips) {
			// kill进程
			sb.append(ShellUtil.exec("sh " + shellFileFolder + "/kill.sh " + ip + " " + uuid + " " + info.isForceKill()) + "\n\n");
		}
		return sb.toString();
	}

	/**
	 * ajax查看日志
	 */
	@ResponseBody
	@RequestMapping(value = "log", produces = "text/plain;charset=UTF-8")
	public String ajaxShowLog(String uuid, String server) throws IOException {
		DeployInfo info = liveDeployService.getDetail(uuid);

		if(info.getServerList().contains(server)) {
			return ShellUtil.exec("sh " + shellFileFolder + "/showlog.sh " + server + " " + uuid) + "\n\n";
		} else {
			throw new RuntimeException("不存在该服务器：" + server);
		}
	}

	/**
	 * ajax删除项目（目前弃用）
	 * @RequestMapping("delete")
	 */
	public String delete(String uuid, HttpServletRequest request) throws IOException {
		liveDeployService.delete(uuid, request.getRemoteUser());
		return "redirect:/";
	}

}
