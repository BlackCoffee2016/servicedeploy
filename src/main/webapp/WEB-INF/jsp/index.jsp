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
<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
</head>
<body style="font-family: 'Roboto', 'Droid Sans Fallback', '微软雅黑'; min-height: 100vh;display: flex;flex-direction: column;">

	<nav>
		<div class="nav-wrapper">
			<a href="${pageContext.request.contextPath}/" class="brand-logo center">自动化部署平台</a>
		</div>
	</nav>

	<div class="container" style="padding-top: 20px; width: 90%;flex: 1 0 auto;">
		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s6"><a href="#java-live">Java后台服务Live</a></li>
					<li class="tab col s6"><a href="#java-stage">Java后台服务Stage</a></li>
					<li class="tab col s6"><a href="#java-web-stage">Java Web项目Stage</a></li>
				</ul>
			</div>
		</div>
		
		<div id="java-live">
			<div class="row">
				<div class="input-field col s6 offset-s2">
					<nav>
						<div class="nav-wrapper">
							<form>
								<div class="input-field">
									<input id="live-search" type="search" required>
									<label for="live-search">
										<i class="material-icons">search</i>
									</label>
								</div>
							</form>
						</div>
					</nav>
				</div>
				<div class="input-field col s4">
					<a class="waves-effect waves-light btn red lighten-2" href="${pageContext.request.contextPath}/live/new" style="line-height: 64px; height: 64px;">创建</a>
				</div>
			</div>
			<table class="hoverable">
				<thead>
					<tr>
						<td>项目名称</td>
						<td>UUID</td>
						<td>finalName</td>
						<td>服务器</td>
						<td>负责人</td>
						<td>详情</td>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="liveItem" items="${liveList}">
					<tr>
						<td>${liveItem.name}</td>
						<td>${liveItem.uuid}</td>
						<td>${liveItem.finalName}</td>
						<td>${liveItem.servers}</td>
						<td>${liveItem.owner}</td>
						<td><a href="${pageContext.request.contextPath}/live/detail/${liveItem.uuid}" class="btn waves-effect waves-light red lighten-2">详情</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div id="java-stage" class="row">
			<div class="row">
				<div class="input-field col s6 offset-s2">
					<nav>
						<div class="nav-wrapper">
							<form>
								<div class="input-field">
									<input id="stage-search" type="search" required>
									<label for="stage-search">
										<i class="material-icons">search</i>
									</label>
								</div>
							</form>
						</div>
					</nav>
				</div>
				<div class="input-field col s4">
					<a class="waves-effect waves-light btn red lighten-2" href="${pageContext.request.contextPath}/stage/new" style="line-height: 64px; height: 64px;">创建</a>
				</div>
			</div>
			<table class="hoverable">
				<thead>
				<tr>
					<td>项目名称</td>
					<td>UUID</td>
					<td>finalName</td>
					<td>负责人</td>
					<td>详情</td>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="stageItem" items="${stageList}">
					<tr>
						<td>${stageItem.name}</td>
						<td>${stageItem.uuid}</td>
						<td>${stageItem.finalName}</td>
						<td>${stageItem.owner}</td>
						<td><a href="${pageContext.request.contextPath}/stage/detail/${stageItem.uuid}" class="btn waves-effect waves-light red lighten-2">详情</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div id="java-web-stage" class="row">
			<div class="row">
				<div class="input-field col s6 offset-s2">
					<nav>
						<div class="nav-wrapper">
							<form>
								<div class="input-field">
									<input id="stageweb-search" type="search" required>
									<label for="stageweb-search">
										<i class="material-icons">search</i>
									</label>
								</div>
							</form>
						</div>
					</nav>
				</div>
				<div class="input-field col s4">
					<a class="waves-effect waves-light btn red lighten-2" href="${pageContext.request.contextPath}/stageweb/new" style="line-height: 64px; height: 64px;">创建</a>
				</div>
			</div>
			<table class="hoverable">
				<thead>
				<tr>
					<td>项目名称</td>
					<td>UUID</td>
					<td>finalName</td>
					<td>HTTP地址</td>
					<td>负责人</td>
					<td>详情</td>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="item" items="${stageWebList}">
					<tr>
						<td>${item.name}</td>
						<td>${item.uuid}</td>
						<td>${item.finalName}</td>
						<td><a href="${item.httpUrl}" target="_blank">${item.httpUrl}</a></td>
						<td>${item.owner}</td>
						<td><a href="${pageContext.request.contextPath}/stageweb/detail/${item.uuid}" class="btn waves-effect waves-light red lighten-2">详情</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
	
	<footer class="page-footer" style="padding-top: 0; margin-top: 40px;">
       <div class="footer-copyright">
         <div class="container">
         Copyright © 2016 <a class="grey-text text-lighten-4" href="http://xxgblog.com" target="_blank">http://xxgblog.com</a>. All rights reserved.
         <a class="grey-text text-lighten-4 right" href="https://github.com/wucao/JDeploy" target="_blank">GitHub</a>
         </div>
       </div>
    </footer>

	
</body>
</html>