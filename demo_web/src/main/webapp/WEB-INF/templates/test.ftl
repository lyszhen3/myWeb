<#include "sysman/common/host_config.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <script type="text/javascript" src="${back_man_host}/sysman/js/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
</head>
<script>
    $(document).ready(function () {
        var socket = new SockJS('http://localhost:8080/myHandler');
        socket.onopen = function (event) {
            console.log("链接成功")
            socket.send("hello");
        };

        socket.onmessage = function (event) {
            console.log(event.data)
        };
        socket.onclose = function(){
            console.log("close");
        }
    });
</script>
<body>
${name}
<img width="120px" height="40px" src="/verCode.htm"/>

</body>
</html>