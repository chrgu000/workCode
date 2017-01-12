<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一基础数据库</title>
<link href="${ctxStatic}/css/whty/w_public.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/whty/wdstyle.css" rel="stylesheet" type="text/css">
</head>

<body class="index_page">
<div class="bo_header">
	<div class="bo_topbar">
    	<div class="logo"><a href="#"><img src="${ctxStatic}/images/whty/logo.png"></a><span>统一基础数据库</span></div>
    	<div class="mininav">
        	<span class="uer_name"><a href="#">nrc_admin</a><em>，你好</em>|</span>
            <span class="uer_li">
                <a class="ico1" href="#"><i></i>修改密码</a><a class="ico2" href="login.html"><i></i>注销</a><a class="ico3" href="error404.html" target="mainIframe"><i></i>下载</a>
            </span>
        </div>
    </div>
	<div class="bo_nav">
    	<span  class="place">您的位置</span>
        <a href="#" class="next">首页</a>
        <a href="#" class="next"></a>
        <span></span>
    </div>
</div>
<div class="bo_container clearfix">
	<div class="main_left">
        <div class="title">栏目结构</div>
        <div class="tree_nav" id="tree_nav">
        	<dl>
            	<dt><span><i class="ico1"></i><em>经销商管理</em></span></dt>
                <dd>
                	<ul>
                    	<li><a href="right_index_1.html" target="mainIframe">经销商信息</a></li>
                        <li><a href="统计.html" target="mainIframe">新增经销商</a></li>
                    </ul>
                </dd>
            </dl>
            <dl>
            	<dt><span><i class="ico2"></i><em>数据交换服务</em></span></dt>
                <dd>
                	<ul>
                    	<li><a href="core/jobList" target="mainIframe">定时任务配置</a></li>
                    	<li><a href="dataExchange/publishLogList" target="mainIframe">发布数据日志</a></li>
                    	<li><a href="dataExchange/subscirbeLogList" target="mainIframe">订阅数据日志</a></li>
                    </ul>
                </dd>
            </dl>
        	<dl>
            	<dt><span><i class="ico4"></i><em>系统管理</em></span></dt>
                <dd>
                	<ul>
                    	<li><a href="admin/dictList" target="mainIframe" >系统字典管理</a></li>
                    	<li><a href="admin/dataList" target="mainIframe" >数据交换管理</a></li>
                    	<li><a href="druid" target="_blank" >Druid监控</a></li>
                    </ul>
                </dd>
            </dl>
            <dl>
            	<dt><span><i class="ico5"></i><em>用户管理</em></span></dt>
                <dd>
                	<ul>
                    	<li><a href="cddddd/jobList" target="mainIframe" >用户管理</a></li>
                        <li><a href="#" target="mainIframe" >角色管理</a></li>
                    </ul>
                </dd>
            </dl>
        </div>   
    </div>
    <div class="main_right">
    	<iframe src="welcome" class="mainiframe" width="100%" name="mainIframe" id="mainIframe" frameborder=0  marginheight="0" marginwidth="0" allowTransparency="true"></iframe>
    </div>
</div>
<div class="bo_footer">
 	<span class="fl">Copyright © 2016 All Rights Reserved</span>
    <span class="fr">技术支撑：天喻教育科技有限公司</span>
</div>

<script src="${ctxStatic}/js/jquery.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/global.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/whty/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.artDialog.js"></script> 
<script type="text/javascript" src="${ctxStatic}/js/jquery.artDialog.plugins.js"></script> 
<script language="JavaScript" type="text/JavaScript">
$(function(){
	//页面容器自动全屏
	homerSize();
	
	//左侧导航收缩展开
	openSiderNav('.tree_nav');
})
$(window).resize(homerSize);


//左侧导航收缩展开
function openSiderNav(obj){
	$(obj).find('dt').each(function(index, element) {
		$(this).on("click",function(){
			$(this).parent().addClass("cur").siblings().removeClass("cur");
			if($(this).next().height()==0){
				$(this).parent().siblings().find("dd").animate({height:0},"fast");
				$(this).next().animate({height:$(this).next().find("ul").height()},"fast");
			}
			else{
				$(this).parent().removeClass("cur");
				$(this).next().animate({height:0},"fast");
			}
		})
	});
	
	$(obj).find('li').on('click',function(){
		$(".tree_nav li").removeClass("on");
		$(this).addClass("on");	
	})	
}

//页面容器自动全屏
function homerSize(){
	var bodyH = $(window).height();
	var headerH=$('.bo_header').outerHeight();
	var footerH=$('.bo_footer').outerHeight();
	var mainH=bodyH-headerH-footerH;
	$('.bo_container').css({height:mainH});
	$('.main_left').css({height:mainH});
	$('.mainiframe').css({height:mainH});
}	
</script>
</body>
</html>
