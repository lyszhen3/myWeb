<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <title>后台管理系统|GTF-Admin</title>
    <link rel="stylesheet" href="demo/layui/css/layui.css"/>
    <link rel="stylesheet" href="demo/css/admin.css"/>
</head>
<body>
	<div class="container">
		<div class="content-search">
			<form class="layui-form layui-form-pane" action="">
				<div class="layui-inline">
					<label class="layui-form-label">姓名</label>
					<div class="layui-input-block">
						<input name="date" autocomplete="off" class="layui-input" type="text"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">手机号码</label>
					<div class="layui-input-inline">
						<input name="mobile" autocomplete="off" class="layui-input" type="text"/>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">邮箱</label>
					<div class="layui-input-inline">
						<input name="email" autocomplete="off" class="layui-input" type="text"/>
					</div>
				</div>
			</form>
		</div>
		<div class="handle-btn">
			<button class="layui-btn layui-btn-small" data-url="add.html"><i class="layui-icon">&#xe654;</i>添加</button>
			<button class="layui-btn layui-btn-small layui-btn-normal" data-url="edit.html"><i class="layui-icon">&#xe642;</i>编辑</button>
			<button class="layui-btn layui-btn-small layui-btn-danger" data-url="delete.html"><i class="layui-icon">&#xe640;</i> 删除</button>
			<button class="layui-btn layui-btn-small layui-btn-warm" data-url="add.html"><i class="layui-icon">&#xe638;</i> tab新页</button>
		</div>

		<div class="table-list">
			<table class="layui-table" lay-skin="line">
				<thead>
					<tr>
						<th>编号</th>
						<th>姓名</th>
						<th>邮编</th>

					</tr>
				</thead>
				<tbody>
					<#list  list as sg >
                    	<tr data-assign="${sg.id}">
                        	<td>${sg.id}</td>
                        	<td>${sg.name}</td>
                        	<td>${sg.email}</td>

                   		 </tr>


					</#list>

				</tbody>
			</table>
		</div>
	</div>
	<div id="table-pages" style="text-align:center"></div>
	
	<script type="text/javascript" src="demo/layui/layui.js"></script>
    <script type="text/javascript" src="demo/js/gtf.js"></script>
    <script type="text/javascript">
    	var totalPage = 10;
    </script>
</body>
</html>
</html>