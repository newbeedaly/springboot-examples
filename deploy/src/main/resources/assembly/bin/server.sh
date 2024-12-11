#!/bin/bash
cd ../
#获取根路径
BASE_DIR=$(cd `dirname $0`; pwd)
#日志路径
cd ../
LOGS_DIR=$(cd `dirname $0`; pwd)
cd $BASE_DIR
#项目名称
APP_NAME=@project.artifactId@-@project.version@.jar

#JVM配置
JAVA_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xms256m -Xmx1024m -Xss256k -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOGS_DIR/logs/ -XX:+PrintHeapAtGC -Xloggc:$LOGS_DIR/logs/gc.log"

#判断是否有存储jmv日志的文件夹，如果没有则创建
if [ ! -d "${LOGS_DIR}/logs" ]; then
    mkdir -p ${LOGS_DIR}/logs
fi

#使用说明，用来提示输入参数
usage() {
 echo "Usage: ./server.sh [start|stop|restart|status]"
 exit 1
}


#检查程序是否在运行
pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
is_exist(){
 #如果不存在返回1，存在返回0
 if [ -z "${pid}" ]; then
  echo 1
 else
  echo 0
 fi
}

#启动方法
start(){
 res=$(is_exist)
 if [ $res -eq "0" ]; then
   echo "${APP_NAME} is already running. pid=${pid}."
 else
   nohup java $JAVA_OPTS -jar $BASE_DIR/$APP_NAME --logging.config=$BASE_DIR/config/logback-spring-@profile@.xml > /dev/null 2>&1 &
   echo "${APP_NAME} start success"
 fi

}

#停止方法
stop(){
 res=$(is_exist)
 if [ $res -eq "0" ]; then
  kill -9 $pid
 else
  echo "${APP_NAME} is not running"
 fi
}

#输出运行状态
status(){
 res=$(is_exist)
 if [ $res -eq "0" ]; then
  echo "${APP_NAME} is running. Pid is ${pid}"
 else
  echo "${APP_NAME} is NOT running."
 fi
}

#重启
restart(){
 stop
 start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
 "start")
 start
 ;;
 "stop")
 stop
 ;;
 "status")
 status
 ;;
 "restart")
 restart
 ;;
 *)
 usage
 ;;
esac
