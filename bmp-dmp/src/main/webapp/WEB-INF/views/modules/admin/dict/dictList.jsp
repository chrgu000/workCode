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
    	<sf:form id="search_form"  modelAttribute="queryBean" name="search_form" action="${ctx }/admin/dictList" method="post">
	        <div class="clearfix mgtb10">
	        		<p class="fl mgr10"><span>字典类型</span>
	        			<sf:select id="type" path="type" name="type" style="width: 160px;">
	        				<sf:option value="" label=""/>
	        				<sf:options items="${typeList}" htmlEscape="false"/>
	        			</sf:select>
	        		</p>
		        	<p class="fl mgr10"><span>字典名称</span> <input type="text" class="def_inp" id="name" name="name" value="${queryBean.name }"/></p>
		            <p class="fl mgr10"><span>字典值</span> <input type="text" class="def_inp" id="value" name="value" value="${queryBean.dValue }" /></p>
		       		<p class="fl">
		       			<a href="#" onclick="javascript:doQuery();" class="abtn1">查询</a>
		       			<a href="#" onclick="javascript:doAdd();" class="abtn1">新增</a>
		       		</p>
	        </div>
	        <table class="mod_table data_list" width="100%" style="border-collapse:collapse;">
	            <tr>
	                <th>字典名称</th>
	                <th>字典键</th>
	                <th>字典值</th>
	                <th>类型</th>
	                <th>创建时间</th>
	                <th>更新时间</th>
	                <th>操作</th>
	            </tr>
	            <c:forEach items="${dictList}" var="dict">
	            <tr>
	                <td>${dict.name }</td>
	                <td>${dict.dKey }</td>
	                <td>${dict.dValue }</td>
	                <td>${dict.type }</td>
	                <td><fmt:formatDate value="${dict.createTime }" type="both"/></td>
	                <td><fmt:formatDate value="${dict.updateTime }" type="both"/></td>
	                <td><a href="#" onclick="javascript:doEdit('${dict.id}');" class="mgr10">修改</a><a href="#" onclick="javascript:doDel('${dict.id}');">删除</a></td>
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
	$("#type").select2({
		placeholder: "选择一个类型",
	    allowClear: true
	});
	$("#type").change(function(){
		doQuery();
	});
})

function doAdd(){
	var dialog = top.art.dialog.open({
		content_id:'addFrame',
		id:'addFrame',
		title: '新增数据',
	    content: '${ctx}/admin/dictAdd',
		padding:'5px',
		scrolling:'no',
	    cancel:true,
	    ok: function(){
	    	return doSave('addFrame');
	    }		
	}); 
}

function doDel(id){
	 if (confirm("确认删除该定时任务?")) {
		$.post('${ctx}/admin/dictDelete', {id : id}, function(text, status) {
			doQuery();
		});
	}
}

function doEdit(id){
	var dialog = top.art.dialog.open({
		content_id:'updateFrame',
		id:'updateFrame',
		title: '修改数据',
	    content: '${ctx}/admin/dictEdit?id='+id,
		padding:'5px',
		scrolling:'no',
	    cancel:true,
	    ok: function(){
	    	return doSave('updateFrame');
	    }		
	}); 
}

</script>
</body>
</html>