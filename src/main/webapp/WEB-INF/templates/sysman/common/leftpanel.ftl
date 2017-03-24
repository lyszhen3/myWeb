<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <title>后台管理模板</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="${back_man_host}/sysman/plugins/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${back_man_host}/sysman/css/global.css" media="all">
    <link rel="stylesheet" type="text/css" href="${back_man_host}/sysman/css/font-awesome.min.css">

</head>

<body>
<div class="layui-layout layui-layout-admin">

    <script type="text/javascript" src="${back_man_host}/sysman/plugins/layui/layui.js"></script>
    <script type="text/javascript" src="${back_man_host}/sysman/datas/nav.js"></script>
    <script src="${back_man_host}/sysman/js/index.js"></script>
    <script>
        layui.use('layer', function() {
            var $ = layui.jquery,
                    layer = layui.layer;

            $('#video1').on('click', function() {
                layer.open({
                    title: 'YouTube',
                    maxmin: true,
                    type: 2,
                    content: 'video.html',
                    area: ['800px', '500px']
                });
            });
        });
    </script>
</div>
</body>

</html>