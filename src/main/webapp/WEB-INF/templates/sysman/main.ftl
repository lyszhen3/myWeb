<#include "common/host_config.ftl">
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>四季星座网管理后台</title>
    <script type="text/javascript" src="${back_man_host}/js/jquery/jquery-1.8.2.min.js"></script>
</head>

<frameset rows="80,*" cols="*" frameborder="no" border="0" framespacing="0">
    <frame src="${back_man_host }/index/topPanel.do" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
    <frameset cols="187,*" frameborder="no" border="0" framespacing="0">
        <frame src="${back_man_host }/index/leftPanel.do" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
        <frame src="${back_man_host }/index/rightPanel.do" name="rightFrame" id="rightFrame" title="rightFrame" />
    </frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>