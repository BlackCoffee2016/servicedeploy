$(document).ready(function() {
	
	$("#live-search").change(function() {
		$("#java-live tbody tr").each(function() {
			var val = $("#live-search").val();
			var name = $(this).children().get(0).innerHTML;
			var uuid = $(this).children().get(1).innerHTML;
			var finalName = $(this).children().get(2).innerHTML;
			var ip = $(this).children().get(3).innerHTML;
			var owner = $(this).children().get(4).innerHTML;
			if(name.indexOf(val) != -1 || uuid.indexOf(val) != -1
					|| finalName.indexOf(val) != -1 || ip.indexOf(val) != -1 || owner.indexOf(val) != -1) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});
	
	$("#stage-search").change(function() {
		$("#java-stage tbody tr").each(function() {
			var val = $("#stage-search").val();
			var name = $(this).children().get(0).innerHTML;
			var uuid = $(this).children().get(1).innerHTML;
			var finalName = $(this).children().get(2).innerHTML;
			var owner = $(this).children().get(3).innerHTML;
			if(name.indexOf(val) != -1 || uuid.indexOf(val) != -1
					|| finalName.indexOf(val) != -1 || owner.indexOf(val) != -1) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});
	
	$("#stageweb-search").change(function() {
		$("#java-web-stage tbody tr").each(function() {
			var val = $("#stageweb-search").val();
			var name = $(this).children().get(0).innerHTML;
			var uuid = $(this).children().get(1).innerHTML;
			var finalName = $(this).children().get(2).innerHTML;
			var contentPath = $(this).children().get(3).innerHTML;
			var port = $(this).children().get(4).innerHTML;
			var owner = $(this).children().get(5).innerHTML;
			if(name.indexOf(val) != -1 || uuid.indexOf(val) != -1
					|| finalName.indexOf(val) != -1 || owner.indexOf(val) != -1
					|| port.indexOf(val) != -1 || contentPath.indexOf(val) != -1) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});
	
	$("form").submit(function() {
		return false;
	});
	
});