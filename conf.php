<?php

//if(!defined("IP")) define("IP","192.168.1.108");
if(!defined("IP")){
    if(strtolower(PHP_OS) == 'linux'){
        define("IP","10.48.30.87");
    }else{
        define("IP","192.168.1.108");
    }
}

if(!defined("MOBILEINFO")){
    if(strtolower(PHP_OS) == 'linux'){
        define("MOBILEINFO","./libs/mobile_info.xml");
    }else{
        define("MOBILEINFO","E:/javawork/ci_server/mobile_info.xml");
    }
}


if(!defined("PORT")){
    if(strtolower(PHP_OS) == 'linux'){
        define("PORT","8801");
    }else{
        define("PORT","3204");
    }
}