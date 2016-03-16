$(document).ready(function() {

	var uuid = $("#text-uuid").html();

	// 初始化刷新运行状态
	refreshStatus();

	// 部署按钮
	$("#btn-deploy").click(function () {
		ajaxShell("../deploy", {uuid: uuid}, function() {
			refreshStatus();
		});
	});

	// 重启按钮
	$("#btn-restart").click(function () {
		ajaxShell("../restart", {uuid: uuid}, function() {
			refreshStatus();
		});
	});

	// 停止按钮
	$("#btn-stop").click(function () {
		ajaxShell("../stop", {uuid: uuid}, function() {
			refreshStatus();
		});
	});

	// 查看日志
	$(".btn-showlog").click(function () {
		ajaxShell("../log", {uuid: uuid, server: $(this).attr("data-ip")});
	});


	/**
	 * ajax请求后台运行脚本
	 */
	function ajaxShell(url, postData, successCallback) {
		$("#loader-modal").openModal({dismissible: false});
		$.ajax({
			url: url,
			type: "POST",
			data: postData,
			cache: false,
			dataType: "text",
			success: function (data) {
				$("#loader-modal").closeModal();
				$("#layer-modal .modal-content").html(data.replace(/\n/g,"<br>"));
				$("#layer-modal").openModal({dismissible: false});
				if(successCallback) {
					successCallback();
				}
			},
			error: function () {
				$("#loader-modal").closeModal();
				layerAlert("发生异常，请重试！");
			}
		});
	}

	/**
	 * 刷新服务器运行状态
	 */
	function refreshStatus() {
		$.ajax({
			url: "../status",
			type: "POST",
			data: {uuid: uuid},
			cache: false,
			dataType: "json",
			success: function (data) {
				$(".text-ip").each(function() {
					var ip = $(this).html();
					var running = data[ip];
					$(this).next().find("span").hide();
					if(running === true) {
						$(this).next().find(".green-text").show();
					} else {
						$(this).next().find(".red-text").show();
					}
				});
			},
			error: function () {
				layerAlert("发生异常，请重试！");
			}
		});
	}

	/**
	 * 用于代替alert
	 * @param text
	 */
	function layerAlert(text) {
		$("#alert-modal .text-alert").html(text);
		$("#alert-modal").openModal({dismissible: false});
	}
	
});