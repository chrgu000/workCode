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
    	<sf:form id="search_form"  modelAttribute="queryBean" name="search_form" action="${ctx }/dataExchange/subscirbeLogList" method="post">
	        <div class="clearfix mgtb10">
	        		<p class="fl mgr10"><span>数据分类</span>
	        			<sf:select path="data" class="def_sel" style="width:160px;">
	        				<sf:option value="" label=""/>
							<sf:options items="${fns:getConfigDictList('data')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
						</sf:select>
	        		</p>
	        		<p class="fl mgr10"><span>操作类型</span> 
		            	<sf:select path="operatorType" class="def_sel" style="width:160px;">
							<sf:option value="" label=""/>
							<sf:options items="${fns:getConfigDictList('operatorType')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
						</sf:select>
		            </p>
		        	<p class="fl mgr10"><span>数据名称</span> <input type="text" style="width:160px;" class="def_inp" id="objName" name="objName" value="${queryBean.objName }"/></p>
		       		<p class="fl mgr10"><span>数据状态</span> 
		            	<sf:select path="dataStatus" class="def_sel" style="width:160px;">
							<sf:option value="" label=""/>
							<sf:options items="${fns:getConfigDictList('dataStatus')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
						</sf:select>
		            </p>
		       		<p class="fl">
		       			<a href="#" onclick="javascript:doQuery();" class="abtn1">查询</a>
		       		</p>
	        </div>
	        <table class="mod_table data_list" width="100%" style="border-collapse:collapse;">
	            <tr>
	                <th>数据名称</th>
	                <th>数据Id</th>
	                <th>数据类型</th>
	                <th>状态</th>
	                <th>创建时间</th>
	                <th>修改时间</th>
	                <th>操作</th>
	            </tr>
	            <c:forEach items="${dataLogList}" var="item">
	            <tr>
	                <td>${item.objName }</td>
	                <td>${item.objId }</td>
	                <td>${fns:getDictName(item.data, 'data', '')}</td>
	                <td>${fns:getDictName(item.dataStatus, 'dataStatus', '')}</td>
	                <td><fmt:formatDate value="${item.createTime }" type="both"/></td>
	                <td><fmt:formatDate value="${item.updateTime }" type="both"/></td>
	                <td><a href="#" onclick="javascript:doWatch('${item.id}');" class="mgr10">查看详情</a></td>
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
	
	$("#data").select2({
		placeholder: "选择数据分类"
	});
	
	$("#operatorType").select2({
		placeholder: "选择操作类型"
	});
	
	$("#dataStatus").select2({
		placeholder: "选择数据状态"
	});
	
})

function doWatch(id){
	var dialog = top.art.dialog.open({
		content_id:'addFrame',
		id:'addFrame',
		title: '查看数据',
	    content: '${ctx}/dataExchange/doWatchDetail?id='+id+'&type='+1,
	    width:'420px',
		height:'400px',
		padding:'5px',
		scrolling:'yes',
	    ok: true
	}); 
}



</script>
</body>
</html>