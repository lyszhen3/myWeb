<#include "common/host_config.ftl">

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>店铺关闭</title>
    <link rel="stylesheet" href="${back_man_host}/sysman/plugins/layui/css/layui.css" media="all" />
</head>

<body class="beg-login-bg" >
<div class="beg-login-box" style="padding:15px;">
    <label style="margin-bottom:10px;margin-top: 10px;">关闭理由</label><br/>
    <input type="hidden" name="shopId" value="${shopId}" id="shopId"/>
    <textarea name="reason" id="reason" style="width:550px;height:150px;margin-top:10px;"></textarea>
    <div style="width:550px;">
        <button class="layui-btn" onclick="closeShop()" style="float:right;margin-top:10px;">提交</button>
    </div>
</div>
<script type="text/javascript" src="${back_man_host}/sysman/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/js/custom_common.js"></script>
<script>
    // 关店
    function closeShop(){
        var datajson = {"shopId":$("#shopId").val(),"reason":$("#reason").val()};
        var index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            type : "POST",
            url : getRealPath() + '/sysman/shop/closeShop.json',
            data : datajson,
            dataType : "json",
            async : true,
            success : function(data) {
                if(data.status == 'success'){
                    window.parent.search();
                    alert('关店成功');
                    window.parent.layer.close(index);
                    return;
                }
                layer.alert(data.msg);
            }
        });
    }
</script>
</body>

</html>