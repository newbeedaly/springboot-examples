#### 使用auth_request模块实现nginx端鉴权控制
Nginx 挂载Auth认证中心主要是采用了Nginx的auth_request模块来支持客户端请求的授权/鉴权功能。
其原理是客户端向nginx发送请求，如果响应为2xx，则身份验证为true；如果响应为4xx，则身份验证失败.
对auth_request模块不懂的，可以参看：https://blog.csdn.net/qq_38973710/article/details/114879468

#### 下载Nginx
注：nginx版本高版本已经自带了auth_request 模块，低版本需要下载后编译。
不懂的可以参考：https://blog.csdn.net/baidu_38803985/article/details/104757600

#### 配置Nginx.conf 文件示例
```
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;

events {
	worker_connections  1024;
}

http {

  include       mime.types;
  default_type  application/octet-stream;
  #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
  #                  '$status $body_bytes_sent "$http_referer" '
  #                  '"$http_user_agent" "$http_x_forwarded_for"';

  #access_log  logs/access.log  main;

  sendfile        on;
  #tcp_nopush     on;

  #keepalive_timeout  0;
  keepalive_timeout  65;

  #gzip  on;


  upstream web-auth {
     server 127.0.0.1:9082;
  }

  server {
      listen       80;
      server_name  localhost;

      #charset koi8-r;

      #access_log  logs/host.access.log  main;

      location / {
          root   html;
          index  index.html index.htm;
      }

      location /api/demo {
      			# 添加跨域处理
      			add_header Access-Control-Allow-Origin *;
    				add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
    				add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';
						if ($request_method = 'OPTIONS') {
                return 204;
            }
            auth_request /auth;
            error_page 401 403 /auth;
            auth_request_set $user $upstream_http_x_forwarded_user;
            proxy_set_header X-Forwarded-User $user;
            proxy_pass http://web-auth/api/demo;
        }

      location /auth {
          internal;
          proxy_set_header Host $host;
          proxy_pass_request_body off;
          proxy_set_header Content-Length "";
          proxy_pass http://127.0.0.1:8020/res/verify;
      }

        error_page 404 /res404.json;
      location = /res404.json {
         default_type application/json;
         return 404 '{"error_code":"404","error_msg":"资源不存在"}';
      }

        error_page 500 502 503 504 /res500.json;
      location = /res500.json {
         default_type application/json;
         return 500 '{"error_code":"500","error_msg":"服务器内部错误"}';
      }

    }


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}
```
