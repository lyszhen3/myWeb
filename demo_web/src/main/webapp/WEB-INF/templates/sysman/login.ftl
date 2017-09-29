<#include "common/host_config.ftl">

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>四季星座网后台管理登录</title>
    <link rel="stylesheet" href="${back_man_host}/sysman/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${back_man_host}/sysman/css/login.css" />
</head>

<body class="beg-login-bg" onkeydown='onEnterDown(event);'>
<div class="beg-login-box">
    <header>
        <h1>四季星座网后台管理登录</h1>
    </header>
    <div class="beg-login-main">
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe612;</i>
                </label>
                <input type="text" name="userName" id="userName" lay-verify="userName" autocomplete="off" placeholder="这里输入登录名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input type="password" id="password" name="password" lay-verify="password" autocomplete="off" placeholder="这里输入密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="beg-pull-right">
                    <button class="layui-btn layui-btn-primary" onclick="login();">
                        <i class="layui-icon">&#xe650;</i> 登录
                    </button>
                </div>
                <div class="beg-clear"></div>
            </div>
    </div>
    <footer>
        <p>杭州石谷网络科有限公司 © </p>
    </footer>
</div>
<script type="text/javascript" src="${back_man_host}/sysman/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/js/custom_common.js"></script>
<script>

    if (top.location != self.location){
        top.location=self.location;
    }

    function onEnterDown(evt){
        var e = (evt) ? evt : window.event;
        if(e.keyCode == 13){
            login();
        }
    }

    function login(){
        var userName = $("#userName").val();
        var password = $("#password").val();
        if(panIsNull(userName)){
            layer.alert('请输入用户名');
            return;
        }
        if(panIsNull(password)){
            layer.alert('请输入密码');
            return;
        }
        var loadi = layer.load();
        var datajson = {"userName":userName,"password":password};
        $.ajax({
            type : "POST",
            url : getRealPath() + 'sysman/system/loginIn.json',
            data : datajson,
            dataType : "json",
            async : true,
            success : function(data) {
                layer.close(loadi);
                if(data.status == 'success'){
                    location.href = getRealPath() + 'sysman/index/main.htm';
                    return;
                }
                layer.alert(data.msg);
            }
        });
    }
</script>
</body>

</html>