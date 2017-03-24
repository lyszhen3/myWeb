<#include "/sysman/common/host_config.ftl">

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="sysman/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="sysman/css/global.css" media="all">
    <link rel="stylesheet" type="text/css" href="sysman/css/font-awesome.min.css">
    <link rel="stylesheet" href="sysman/css/table.css" />


    <style>
        .layui-form-item{
            padding: 5px 4px;
            margin-bottom: 0px;
        }
        .layui-elem-quote{
            padding:5px;
        }
    </style>
</head>

<body>
<div class="admin-main">



        <form class="layui-form" action="cllist.htm" id="searchForm" method="post">
            <input type="hidden" name="page" id="currentPage" value="${bo.page}"/>
            <input type="hidden" name="website" value="${bo.website}"/>
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="userName" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.userName!}">
                </div>
                <label class="layui-form-label">档口号</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="shopNum" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.shopNum!}">
                </div>
                <label class="layui-form-label">市场</label>
                <div class="layui-input-inline">
                    <select name="marketId" id="selectMarket" lay-filter="market" onclick="floorajaxlist();">
                        <option value="">请选择市场</option>
                        <#list shiguMarketList as market>
                            <option value="${market.marketId}">${market.marketName}</option>
                        </#list>
                    </select>
                </div>
                <label class="layui-form-label">楼层</label>
                <div class="layui-input-inline">
                    <select name="floorId" id="selectFloor">
                        <option value="">请选择楼层</option>
                    </select>
                </div>
                <label class="layui-form-label">店铺ID</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="shopId" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.shopId!}">
                </div>
                <label class="layui-form-label">淘宝地址</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="taobaoUrl" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.taobaoUrl!}">
                </div>

            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">店铺名称</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="shopName" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.shopName!}">
                </div>
            </div>

        </form>



    <blockquote class="layui-elem-quote">
        <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
            <i class="layui-icon">&#xe615;</i> 搜索
        </a>
        <button class="layui-btn"  onclick="tanchu()">增加</button>
    </blockquote>
    <fieldset class="layui-elem-field">
        <legend>店铺列表</legend>
        <div class="layui-field-box">
            <div>
            <table class="site-table table-hover">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selected-all"></th>
                    <th>店铺ID</th>
                    <th>用户ID</th>
                    <th>用户名</th>
                    <th>店铺</th>
                    <th>市场</th>
                    <th>顶级市场</th>
                    <th>档口号</th>
                    <#--<th>会员</th>-->
                    <#--<th>匹配规则</th>-->
                    <th>taobaoNice</th>
                    <#--<th>设置合作伙伴</th>-->
                    <#--<th>开启自动抓取</th>-->
                    <th>二级域名</th>
                    <#--<th>店铺模式</th>-->
                    <th>店铺状态</th>
                    <#--<th>是否已添加</th>-->
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list list as shop>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>${shop.shopId}</td>
                    <td>
                        ${shop.userId}
                    </td>
                    <td>${shop.userName}</td>
                    <td>${shop.shopName}</td>
                    <td>${shop.marketName}</td>
                    <td>${shop.parentMarketName}</td>
                    <td>${shop.shopNum}</td>
                    <#--<td>${}</td>-->
                    <td>
                        <#if shop.synTaobao == null>未知</#if>
                        <#if shop.synTaobao ??>
                            <#if shop.synTaobao == 1>同步</#if>
                            <#if shop.synTaobao == 0>不同步</#if>
                        </#if>
                    </td>
                    <td>${shop.domain}</td>
                    <td>
                        <#if shop.shopStatus == 0>正常</#if>
                        <#if shop.shopStatus == 1>关店</#if>
                    </td>
                    <td >
                        <#if shop.shopStatus == 0>
                            <a href="javascript:void(0);" class="layui-btn layui-btn-mini" onclick="closeShop('${shop.shopId!}');">关店</a>
                        </#if>
                        <#if shop.shopStatus == 1>
                            <a href="javascript:void(0);" class="layui-btn layui-btn-mini" onclick="openShop('${shop.shopId!}');">开店</a>
                        </#if>
                        <a href="javascript:void(0);" class="layui-btn layui-btn-mini" onclick="synShop('${shop.shopId!}');">全店抓取</a>
                    </td>
                </tr>

                </#list>

                </tbody>
            </table>
            </div>
        </div>
    </fieldset>

</div>
<div class="admin-table-page">
    <div id="page" class="page">
    </div>
</div>
<script type="text/javascript" src="sysman/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="sysman/plugins/layui/layui.js"></script>
<script type="text/javascript" src="sysman/plugins/layer/layer.js"></script>
<script type="text/javascript" src="sysman/js/custom_common.js"></script>

<script>
    function search(){
        $("#searchForm").submit();
    }

    layui.config({
        base: '/sysman/plugins/layui/modules/'
    });

    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form(),
                layer = layui.layer,
                layedit = layui.layedit,
                laydate = layui.laydate;
        var editIndex = layedit.build('LAY_demo_editor');
        form.on('select(market)', function(data){
            floorajaxlist();
            form.render('select'); //刷新selec
        });
    });


    layui.use(['icheck', 'laypage','layer'], function() {
        var $ = layui.jquery,
                laypage = layui.laypage,
                layer = parent.layer === undefined ? layui.layer : parent.layer;
        $('input').iCheck({
            checkboxClass: 'icheckbox_flat-green'
        });

        var totalPage = '${bo.totalPage!}';
        var currentPage = '${bo.page}';
        console.log(totalPage)
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


    // 楼层
    function floorajaxlist(){
        var marketId = $("#selectMarket").val();
        if(panIsNull(marketId)){
            return;
        }
        var datajson = {"marketId":marketId};
        $.ajax({
            type : "POST",
            url : getRealPath() + '/sysman/floor/ajaxlist.json',
            data : datajson,
            dataType : "json",
            async : false,
            success : function(data) {
                $("#selectFloor").html('');
                var flooroption = '';
                var json_data=eval("("+data+")");
                for(var i = 0;i<json_data.length;i++){
                    flooroption += "<option value='"+json_data[i].marketId+"'>"+json_data[i].marketName+"</option>";
                }
                $("#selectFloor").html(flooroption);
            }
        });
    }

    // 开店
    function openShop(shopId){
        if(panIsNull(shopId)){
            return;
        }
        layer.open({
            type: 2,
            title:'开店',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px' , '300px'],
            content: getRealPath() + '/sysman/shop/openView.htm?shopId='+shopId
        });
    }

    // 关店
    function closeShop(shopId){
        if(panIsNull(shopId)){
            return;
        }
        layer.open({
            type: 2,
            title:'关店',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px' , '300px'],
            content: getRealPath() + '/sysman/shop/closeView.htm?shopId='+shopId
        });
    }

    // 全店抓取
    function synShop(shopId){
        if(panIsNull(shopId)){
            return;
        }
        var datajson = {"shopId":shopId};
        //var loadi = layer.load();
        $.ajax({
            type : "POST",
            url : getRealPath() + '/sysman/shop/shopGrab.json',
            data : datajson,
            dataType : "json",
            async : false,
            success : function(data) {
                //layer.close(loadi);
                if(data.status == 'success'){
                    alert('全店抓取成功');
                    return;
                }
                alert(data.msg);
            }
        });
    }


//    layui.use(['form', 'layedit', 'laydate'], function(){
//        var form = layui.form()
//                ,layer = layui.layer
//                ,layedit = layui.layedit
//                ,laydate = layui.laydate;
//
//        //创建一个编辑器
//        var editIndex = layedit.build('LAY_demo_editor');
//
//
//
//
//    });


    function tanchu(){
        layer.open({
            type:1,
            title:"添加",
            shadeClose: false,
            area: ['1000px' , '600px'],
            shade: [0.3, '#000'],
            //maxmin: true, //开启最大化最小化按钮
            content:$("#myform"),
            success: function(){



                layui.use(['layedit'], function() {


                     var layedit = layui.layedit
                     layedit.set({
                        uploadImage: {
                            url: 'upimg.json', //接口url
                            type: 'post' //默认post
                        }
                    });
                    layedit.build('LAY_demo_editor');

                });
            }



        })
    }



    var marketId = '${bo.marketId}';
    var floorId = '${bo.floorId}';
    $(document).ready(function(){
        if(!panIsNull(marketId)){
            $("#selectMarket").val(marketId);
            floorajaxlist();
            if(!panIsNull(floorId)){
                $("#selectFloor").val(floorId);
            }
        }
    });

</script>

    <form style="display: none" id="myform" class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">单行输入框</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">验证必填项</label>
            <div class="layui-input-block">
                <input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
        </div>













        <div class="layui-form-item">
            <label class="layui-form-label">开关-默认开</label>
            <div class="layui-input-block">
                <input type="checkbox" checked="" name="open" lay-skin="switch" lay-filter="switchTest" lay-text="ON|OFF">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">单选框</label>
            <div class="layui-input-block">
                <input type="radio" name="sex" value="男" title="男" checked="">
                <input type="radio" name="sex" value="女" title="女">
                <input type="radio" name="sex" value="禁" title="禁用" disabled="">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">普通文本域</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">编辑器</label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="content" lay-verify="content" id="LAY_demo_editor"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>

</body>

</html>