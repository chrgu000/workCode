<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务列表</title>
<link href="${ctxStatic}/css/whty/w_public.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/whty/wdstyle.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/js/select2/select2.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="bo_main txq_main">
    <div class="mgtb10">
    	<sf:form id="search_form"  modelAttribute="quartzJobVo"  name="search_form" action="${ctx }/core/jobList" method="post">
	        <div class="clearfix mgtb10">
		        	<p class="fl mgr10"><span>任务名</span> <input type="text" class="def_inp" id="jobName" name="jobName" value="${quartzJobVo.jobName }" placeholder="任务名" /></p>
		            <p class="fl mgr10"><span>任务组</span> <input type="text" class="def_inp" id="jobGroup" name="jobGroup" value="${quartzJobVo.jobGroup }" placeholder="任务组" /></p>
		       		<p class="fl mgr10"><span>运行状态</span>
	        			<sf:select path="jobStatus" class="def_sel" style="width:160px;">
	        				<sf:option value="" label=""/>
							<sf:options items="${fns:getConfigDictList('jobStatus')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
						</sf:select>
	        		</p>
		       		<p class="fl">
		       			<a href="#" onclick="javascript:doQuery();" class="abtn1">查询</a>
		       			<a href="#" onclick="javascript:doAdd();" class="abtn1">新增</a>
		       		</p>
	        </div>
	        <table class="mod_table data_list" width="100%" style="border-collapse:collapse;">
	            <tr>
	                <th>任务名</th>
	                <th>任务组</th>
	                <th>Corn</th>
	                <th>执行对象</th>
	                <th>类路径</th>
	                <th>执行方法</th>
	                <th>状态</th>
	                <th>描述</th>
	                <th>操作</th>
	            </tr>
	            <c:forEach items="${jobList}" var="job">
	            <tr>
	                <td>${job.jobName }</td>
	                <td>${job.jobGroup }</td>
	                <td>${job.cronExpression }</td>
	                <td>${job.targetObject }</td>
	                <td>${job.beanClass }</td>
	                <td>${job.targetMethod }</td>
	                <td>${fns:getDictName(job.jobStatus, 'jobStatus', '')}</td>
	                <td>${job.description }</td>
	                <td>
	                <c:choose>
	                	<c:when test="${job.jobStatus==0}">
	                		 <a href="#" onclick="javascript:doStop('${job.id}');" class="mgr10">停止</a>
	                	</c:when>
	                	<c:otherwise>
	                		<a href="#" onclick="javascript:doStart('${job.id}');" class="mgr10">启动</a>
	                	</c:otherwise>
	                </c:choose>
	                <a href="#" onclick="javascript:doEdit('${job.id}');" class="mgr10">修改</a>
	                <a href="#" onclick="javascript:doDel('${job.id}');">删除</a></td>
	            </tr>
	            </c:forEach>
	        </table>
	        <div class="turnPage t_c mgtb10">
	            <%@ include file="/WEB-INF/views/include/pagination.jsp"%>	
	        </div>
        </sf:form>
    </div>
</div>

<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/global.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/whty/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.artDialog.js"></script> 
<script type="text/javascript" src="${ctxStatic}/js/jquery.artDialog.plugins.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/select2/select2.min.js"></script>

<script>

$(function(){
	//tab
	$("div[name='slide']").tabControl("*[name='slideTag'] a" , "*[name='slideCont']" , '.more');
	//改变偶数行背景色
	changTd(".mod_table","#f6f7f6");
	//复选框多选行
	ckAllItm(".txq_ckall");
	
	//执行分页跳转框输入弹出效果
	$(".num_text").inputPageFocus({
	  btnClass:'.anim'
	});
	$("#jobStatus").select2({
		placeholder: "选择运行状态"
	});
	$("#jobStatus").change(function(){
		doQuery();
	});

})

function doAdd(){
	var dialog = top.art.dialog.open({
		content_id:'addFrame',
		id:'addFrame',
		title: '新增数据',
	    content: '${ctx}/core/jobAdd',
	    width:'620px',
		height:'300px',
		padding:'5px',
		scrolling:'yes',
	    cancel:true,
	    ok: function(){
	    	return doSave('addFrame');
	    }		
	}); 
}

function doEdit(id){
	var dialog = top.art.dialog.open({
		content_id:'updateFrame',
		id:'updateFrame',
		title: '修改数据',
	    content: '${ctx}/core/jobEdit?id='+id,
	    width:'620px',
		height:'300px',
		padding:'5px',
		scrolling:'no',
	    cancel:true,
	    ok: function(){
	    	return doSave('updateFrame');
	    }		
	}); 
}

function doDel(id){
	 if (confirm("确认删除该定时任务?")) {
		$.post('${ctx}/core/jobDelete', {id : id}, function(text, status) {
			doQuery();
		});
	}
}

function doStop(id){
	 if (confirm("确认暂停该定时任务?")) {
		$.post('${ctx}/core/stopJob', {id : id}, function(text, status) {
			doQuery();
		});
	}
}

function doStart(id){
	 if (confirm("确认开启该定时任务?")) {
		$.post('${ctx}/core/startJob', {id : id}, function(text, status) {
			doQuery();
		});
	}
}


</script>
</body>
</html>