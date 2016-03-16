$(document).ready(function() {

	$("#form-new").submit(function() {
		var name = $("#input-name").val();
		var finalName = $("#input-finalname").val();
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
		if(owner.length === 0) {
			layerAlert("请填写负责人！");
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