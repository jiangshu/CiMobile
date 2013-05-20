<?php
/**
 * Created by JetBrains PhpStorm.
 * User: jiangshuguang
 * Date: 13-3-29
 * Time: 下午6:55
 * To change this template use File | Settings | File Templates.
 */

?>
<html>
<head>
    <meta charset="utf-8"/>
    <link type="text/css" rel="stylesheet" href="./static/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="./static/css/down.css"/>
    <script type="text/javascript" src="./static/js/jquery-1.8.2.js"></script>
</head>
<body>
<div class="header">
    <div class="logo">CIMobile</div>
    <div class="nav">
        <ul>
            <li><a href="index.php">全部</a></li>
            <li><a href="filter.php">过滤</a></li>
            <li style="background-color: #686868;"><a href="down.php">下载</a></li>
        </ul>
    </div>
</div>
<div class="body">


     <div class="down">
         命令发送端:<a href="./libs/CIMobile_Cmd.jar">下载</a>
     </div>

    <div class="down">
        服务器端：<a href="./libs/CIMobile_Cmd.jar">下载</a>
    </div>

    <div class="down">
        <div style="margin-bottom: 10px">
            APP:<a href="down_soft.php?soft=CIMobi.apk">下载</a>
        </div>
        <div>
            <img src="./static/image/cimobi.png"/>
        </div>

    </div>
</div>
</body>
</html>