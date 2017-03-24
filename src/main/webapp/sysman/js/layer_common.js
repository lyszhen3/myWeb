layui.config({
	base: '/sysman/plugins/layui/modules/'
});

layui.use(['icheck', 'laypage','layer'], function() {
	var $ = layui.jquery,
		laypage = layui.laypage,
		layer = parent.layer === undefined ? layui.layer : parent.layer;
	$('input').iCheck({
		checkboxClass: 'icheckbox_flat-green'
	});

	var totalPage = '${pager.totalPages!}';
	var currentPage = '${pager.number}';
	//page
	laypage({
		cont: 'page',
		curr:parseInt(currentPage),
		pages: parseInt(totalPage),
		groups: 10,
		jump: function(obj, first) {
			//得到了当前页，用于向服务端请求对应数据
			var curr = obj.curr;
			if(!first) {
				//layer.msg('第 '+ obj.curr +' 页');
				$("#currentPage").val(parseInt(curr));
				$("#searchForm").submit();
			}else{
				layer.msg('第 '+ obj.curr +' 页');
			}
		}
	});

	$('#search').on('click', function() {
		$("#searchForm").submit();
	});


	$('.site-table tbody tr').on('click', function(event) {
		var $this = $(this);
		var $input = $this.children('td').eq(0).find('input');
		$input.on('ifChecked', function(e) {
			$this.css('background-color', '#EEEEEE');
		});
		$input.on('ifUnchecked', function(e) {
			$this.removeAttr('style');
		});
		$input.iCheck('toggle');
	}).find('input').each(function() {
		var $this = $(this);
		$this.on('ifChecked', function(e) {
			$this.parents('tr').css('background-color', '#EEEEEE');
		});
		$this.on('ifUnchecked', function(e) {
			$this.parents('tr').removeAttr('style');
		});
	});
	$('#selected-all').on('ifChanged', function(event) {
		var $input = $('.site-table tbody tr td').find('input');
		$input.iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
	});
});