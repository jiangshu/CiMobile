CiMobile
========

android持续集成框架
代码总共分为四部分：
1.android app：android客户端，
   （1）建立连接
   （2）接收和处理命令
   （3）查看log
 
2.PC服务器
   （1）接受连接
   （2）分类mobile  
   （3）接受pc client 命令，转发给 mobile端   
   
3.pc 客户端
   （1） 命令输入端

4.UI（php）
   （1）查看所有当前连接的mobile
   （2）接受用户的命令（多个参数），启动 3 pc 客户端

