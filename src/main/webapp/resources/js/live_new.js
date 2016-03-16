$(document).ready(function() {

	$("#form-new").submit(function() {
		var name = $("#input-name").val();
		var finalName = $("#input-finalname").val();
		var server1 = $("#input-server1").val();
		var server2 = $("#input-server2").val();
		var server3 = $("#input-server3").val();
		var server4 = $("#input-server4").val();
		var svn = $("#input-svn").val();
		var owner = $("#input-owner").val();


		if(name.length === 0) {
			layerAlert("请填写项目名称！");
			return false;
		}
		if(finalName.length === 0) {
			layerAlert("请填写finalName！");
			return false;
		}
		if(server1.length === 0) {
			layerAlert("请填写服务器1！");
			return false;
		}
		if(owner.length === 0) {
			layerAlert("请填写负责人！");
			return false;
		}
		if(server1.indexOf("172.19.20.") !== 0) {
			layerAlert("服务器1填写错误！");
			return false;
		}
		if(server2.length > 0 && server2.indexOf("172.19.20.") !== 0) {
			layerAlert("服务器2填写错误！");
			return false;
		}
		if(server3.length > 0 && server3.indexOf("172.19.20.") !== 0) {
			layerAlert("服务器3填写错误！");
			return false;
		}
		if(server4.length > 0 && server4.indexOf("172.19.20.") !== 0) {
			layerAlert("服务器4填写错误！");
			return false;
		}
		if(svn.indexOf("http://10.230.10.11/svn/hongdian/") !== 0) {
			layerAlert("SVN地址错误！");
			return false;
		}

	});

	/**
	 * 用于代替alert
	 * @param text
	 */
	function layerAlert(text) {
		$("#alert-modal .text-alert").html(text);
		$("#alert-modal").openModal({dismissible: false});
	}

});