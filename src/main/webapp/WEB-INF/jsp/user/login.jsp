<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" type="text/css" href="/static/css/font/iconfont.css?v=1.0.1" media="all">
<link rel="stylesheet" type="text/css" href="/static/plugins/layui/css/layui.css?v=2.2.2" media="all">
<link rel="stylesheet" type="text/css" href="/static/css/main.css?v2.0.1-simple" media="all">
<title>登录工机具管理系统</title>
</head>
<body>
    <div class="layui-fluid larry-wrapper">
        <div class="layui-row layui-col-space30">
            <div class="layui-col-xs24 layui-col-sm8 layui-col-md6 layui-col-lg4  layui-col-md-offset3 layui-col-lg-offset4" style="margin-top:10%;">
                <section class="panel panel-padding">
                    <h1 class="head-title">工机具管理系统</h1>
                    <div class="layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label"><i class="iconfont">&#xe672;</i> 帐号</label>
                            <div class="layui-input-block">
                                <input type="text" name="loginName"  lay-verify="loginName" placeholder="登录帐号" autocomplete="off" class="layui-input" value="${loginName}">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label"><i class="iconfont">&#xe609;</i> 密码</label>
                            <div class="layui-input-block">
                                <input type="password" name="password"  lay-verify="password" placeholder="登录密码" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                 <!--   <div class="layui-form-item">
                            <label class="layui-form-label">验证码</label>
                            <div class="layui-input-inline" style="width:80px;">
                                <input type="text" name="pwd"  lay-verify="required" placeholder="验证码" autocomplete="off" class="layui-input">

                            </div>
                            <div class="layui-input-inline" style="width:120px;"> <img src="images/code.png" alt="" /></div>
                        </div> -->

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="login">登录</button>
                            </div>
                        </div>
                    </div>
                </section>

            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="/static/plugins/layui/layui.js?v=2.2.2"></script>
<script type="text/javascript" src="/static/js/common/layui/config.js?v=2.0.1"></script>
<script type="text/javascript">
    
	layui.use([ 'form', 'layer', 'jquery'], function() {
		var form = layui.form, layer = layui.layer,$ = layui.jquery;
		form.verify({
			loginName : function(value) { //value：表单的值、item：表单的DOM对象
				if ($.trim(value.length) == 0) {
					return '请输入账号!';
				}
				if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
					return '账号不能有特殊字符';
				}
				if (/(^\_)|(\__)|(\_+$)/.test(value)) {
					return '账号首尾不能出现下划线\'_\'';
				}
				if (/^\d+\d+\d$/.test(value)) {
					return '账号不能全为数字';
				}
			},
			password : [ /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格' ]
		});
		form.on('submit(login)', function(data) {
			var obj = data.field;
			layer.alert(obj.loginName);
			return false;
		});
	});
</script>
</html>