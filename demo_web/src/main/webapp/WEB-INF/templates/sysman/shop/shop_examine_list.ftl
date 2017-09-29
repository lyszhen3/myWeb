<#include "/sysman/common/host_config.ftl">

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Table</title>
    <link rel="stylesheet" href="${back_man_host}/sysman/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${back_man_host}/sysman/css/global.css" media="all">
    <link rel="stylesheet" type="text/css" href="${back_man_host}/sysman/css/font-awesome.min.css">
    <link rel="stylesheet" href="${back_man_host}/sysman/css/table.css" />


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



        <form class="layui-form" action="${back_man_host}/sysman/shop/examineShopList.htm" id="searchForm">
            <input type="hidden" name="currentPage" id="currentPage" value="${bo.currentPage}"/>
            <input type="hidden" name="website" value="${bo.website}"/>
            <div class="layui-form-item">
                <label class="layui-form-label">申请ID</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="applyId" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.applyId!}">
                </div>
                <label class="layui-form-label">档口号</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="tel" name="shopNum" lay-verify="phone" autocomplete="off" class="layui-input" value="${bo.shopNum!}">
                </div>
                <label class="layui-form-label">审核状态</label>
                <div class="layui-input-inline" style="width:100px;">
                    <select name="applyStatus">
                        <option value="">请选择</option>
                        <option value="0" <#if bo.applyStatus == 0>selected="selected"</#if> >未处理</option>
                        <option value="1" <#if bo.applyStatus == 1>selected="selected"</#if>>审核通过</option>
                        <option value="2" <#if bo.applyStatus == 2>selected="selected"</#if>>审核拒绝</option>
                    </select>
                </div>
            </div>
        </form>



    <blockquote class="layui-elem-quote">
        <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
            <i class="layui-icon">&#xe615;</i> 搜索
        </a>
    </blockquote>
    <fieldset class="layui-elem-field">
        <legend>申请列表</legend>
        <div class="layui-field-box">
            <div>
            <table class="site-table table-hover">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selected-all"></th>
                    <th>市场</th>
                    <th>档口号</th>
                    <th>旺旺</th>
                    <th>淘宝店链接</th>
                    <#--<th>店铺名</th>-->
                    <th>手机</th>
                    <th>服务</th>
                    <th>审核状态</th>
                    <th>申请时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list pager.content as shop>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>${shop.parentMarketName}</td>
                    <td>
                        ${shop.shopNum}
                    </td>
                    <td>
                        ${shop.imAliww}
                        <a target="_blank" href="http://www.taobao.com/webww/ww.php?ver=3&amp;touid=${shop.imAliww}&amp;siteid=cntaobao&amp;status=2&amp;charset=utf-8"><img border="0" src="http://amos.alicdn.com/realonline.aw?v=2&amp;uid=%E8%B5%B5%E6%B4%AA%E6%B3%A2&amp;site=cntaobao&amp;s=2&amp;charset=utf-8" alt="点击这里给我发消息"></a>
                    </td>
                    <td>${shop.tbUrl}</td>
                    <#--<td>${shop.shopName}</td>-->
                    <td>${shop.telephone}</td>
                    <td>${shop.tagstr}</td>
                    <td>
                        <#if shop.applyStatus ==0>待审核</#if>
                        <#if shop.applyStatus ==1>审核通过</#if>
                        <#if shop.applyStatus ==2>审核拒绝</#if>
                    </td>
                    <td>
                        ${shop.applyTime?string("yyyy-MM-dd hh:mm:ss")}
                    </td>
                    <td>
                        <#if shop.applyStatus ==0>
                            <a href="javascript:void(0);" class="layui-btn layui-btn-mini" onclick="applyPanss('${shop.ruzhuId!}');">通过</a>
                            <a href="javascript:void(0);" class="layui-btn layui-btn-mini" onclick="applyJujue('${shop.ruzhuId!}');">拒绝</a>
                        </#if>
                    </td>
                </tr>

                </#list>

                </tbody>
            </table>
            </div>
        </div>
    </fieldset>
    <div class="admin-table-page">
        <div id="page" class="page">
        </div>
    </div>
</div>
<script type="text/javascript" src="${back_man_host}/sysman/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/plugins/layui/layui.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${back_man_host}/sysman/js/custom_common.js"></script>
<script>

    function search(){
        $("#searchForm").submit();
    }

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
            pages: parseInt(totalPage) //总页数
            ,
            groups: 10 //连续显示分页数
            ,
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

    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form(),
                layer = layui.layer,
                layedit = layui.layedit,
                laydate = layui.laydate;

        //监听提交
        form.on('submit(demo1)', function(data) {
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });
    });

    // 审核通过
    function applyPanss(applyId){
        if(panIsNull(applyId)){
            return;
        }
        var datajson = {"applyId":applyId};
        $.ajax({
            type : "POST",
            url : getRealPath() + '/sysman/shop/examinePanss.json',
            data : datajson,
            dataType : "json",
            async : true,
            success : function(data) {
                if(data.status == 'success'){
                    baseBottomMsgOpen('审核通过成功');
                    $("#searchForm").submit();
                    return;
                }
                layer.alert(data.msg);
            }
        });
    }

    // 审核拒绝
    function applyJujue(applyId){
        if(panIsNull(applyId)){
            return;
        }
        layer.open({
            type: 2,
            title:'审核拒绝理由',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['600px' , '300px'],
            content: getRealPath() + '/sysman/shop/examineRefuseView.htm?applyId='+applyId
        });
    }


</script>
</body>

</html>