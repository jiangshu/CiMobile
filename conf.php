<?php

//if(!defined("IP")) define("IP","192.168.1.108");
if(!defined("IP")){
    if(strtolower(PHP_OS) == 'linux'){
        define("IP","10.48.30.87");
    }else{
        define("IP","192.168.1.108");
    }
}
if(!defined("PORT")) define("PORT","3204");