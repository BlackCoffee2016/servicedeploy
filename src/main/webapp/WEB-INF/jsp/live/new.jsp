<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<title>自动化部署平台</title>
<link rel="stylesheet" href="http://cdn.bootcss.com/materialize/0.97.0/css/materialize.min.css">
<link href="${pageContext.request.contextPath}/resources/css/icon.css" rel="stylesheet">
<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/materialize/0.97.0/js/materialize.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/live_new.js"></script>
</head>
<body style="font-family: 'Roboto', 'Droid Sans Fallback', '微软雅黑';min-height: 100vh;display: flex;flex-direction: column;">

	<nav>
		<div class="nav-wrapper">
			<a href="${pageContext.request.contextPath}/" class="brand-logo center">自动化部署平台</a>
		</div>
	</nav>

	<form id="form-new" method="post" action="${pageContext.request.contextPath}/live/insert" style="flex: 1 0 auto;">
		<div class="container" style="padding-top: 20px;">

			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">
							<span class="card-title black-text">新建项目</span>

								<div class="row">
									<div class="input-field col s12">
										<input type="text" id="input-name" name="name">
										<label for="input-name">项目名称</label>
									</div>
									<div class="input-field col s12">
										<input type="text" id="input-finalname" name="finalName">
										<label for="input-finalname">finalName</label>
									</div>
									<div class="input-field col s12">
										<input type="text" id="input-owner" name="owner">
										<label for="input-owner">负责人</label>
									</div>
									<div class="input-field col s12">
										<input type="text" id="input-parameter" name="parameter">
										<label for="input-parameter">启动参数</label>
									</div>
									<div class="input-field col s6">
										<input type="text" id="input-server1" name="server1">
										<label for="input-server1">服务器1</label>
									</div>
									<div class="input-field col s6">
										<input type="text" id="input-server2" name="server2">
										<label for="input-server2">服务器2</label>
									</div>
									<div class="input-field col s6">
										<input type="text" id="input-server3" name="server3">
										<label for="input-server3">服务器3</label>
									</div>
									<div class="input-field col s6">
										<input type="text" id="input-server4" name="server4">
										<label for="input-server4">服务器4</label>
									</div>
									<div class="input-field col s12">
										<input type="text" id="input-svn" name="svn">
										<label for="input-svn">SVN地址</label>
									</div>
									<div class="input-field col s12">
										<input type="checkbox" id="input-forcekill" name="forceKill">
										<label for="input-forcekill">是否需要强制kill</label>
									</div>
								</div>

						</div>
						<div class="card-action">
							<p>
								<button type="submit" class="btn waves-light waves-effect white-text">提交</button>
							</p>
						</div>
					</div>
				</div>
			</div>

		</div>
	</form>
	
	<footer class="page-footer" style="padding-top: 0; margin-top: 40px;">
       <div class="footer-copyright">
         <div class="container">
         Copyright © 2016 <a class="grey-text text-lighten-4" href="http://xxgblog.com" target="_blank">http://xxgblog.com</a>. All rights reserved.
         <a class="grey-text text-lighten-4 right" href="https://github.com/wucao/JDeploy" target="_blank">GitHub</a>
         </div>
       </div>
    </footer>

	<div id="alert-modal" class="modal">
		<div class="modal-content">
			<p class="grey-text">提示</p>
			<p class="text-alert"></p>
		</div>
		<div class="modal-footer">
			<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">关闭</a>
		</div>
	</div>

</body>
</html>