// 获取项目访问绝对路径
function getRealPath() {
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： myproj/view/my.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/myproj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	// 得到了 http://localhost:8083/myproj
	var realPath = localhostPaht + '/';
	return realPath;
}

// 判断是否为null
function panIsNull(variable1) {
	if (variable1 != null && variable1 !== undefined && variable1 != '') {
		return false;
	}
	return true;
}

// 右下角弹出信息 2秒关闭
function baseBottomMsgOpen(msg) {
	layer.open({
		offset : 'rb', // 右下角弹出
		shift : 2,
		time : 3000,
		icon : 1,
		skin : 'layui-layer-demo', // 样式类名
		content : msg,
		shadeClose : true, // 开启遮罩关闭
		shade : false,
		area : [ '260px', '140px' ],
		btn : []
	});
}


