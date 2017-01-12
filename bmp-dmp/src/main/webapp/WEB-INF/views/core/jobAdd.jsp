<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务添加</title>
<link href="${ctxStatic}/css/whty/w_public.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/whty/wdstyle.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="mgtb10">
    	<sf:form action="${ctx}/core/jobSave" modelAttribute="quartzJobVo" method="post" id="jobAddForm" name="jobAddForm" class="txq_infor">
          	<table border="0" width="100%">
              <tr>
                <td align="right">任务名</td>
                <td><input type="text" id="jobName" name="jobName" style="width: 160px;" class="def_inp" placeholder="任务名"/> <span><em class="red">*</em></span></td>
              	<td align="right">任务组</td>
                <td><input type="text" id="jobGroup" name="jobGroup" class="def_inp" placeholder="任务组" style="width:160px;"/> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
                <td align="right">目标对象</td>
                <td><input type="text" id="targetObject" name="targetObject" style="width: 160px;" class="def_inp" placeholder="springBean对象"/></td>
              	<td align="right">目标方法</td>
                <td><input type="text" id="targetMethod" name="targetMethod" class="def_inp" placeholder="springBean方法" style="width:160px;"/> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
              	<td align="right">时间表达式</td>
                <td><input type="text" id="cronExpression" name="cronExpression" style="width: 160px;" class="def_inp" placeholder="0/5 * * * * ?"/> <span><em class="red">*</em></span></td>
              	<td align="right">目标类</td>
                <td><input type="text" id="beanClass" name="beanClass" class="def_inp" placeholder="springBean类路径" style="width:160px;"/></td>
              </tr>
              <tr>
                <td align="right">任务状态</td>
                <td>
                	<sf:select path="jobStatus" class="def_sel" style="width:182px;">
						<sf:options items="${fns:getConfigDictList('jobStatus')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
					</sf:select>
                	<span><em class="red">*</em></span>
                </td>
              	<td align="right">任务描述</td>
                <td>
                	<input type="text" id="description" name="description" class="def_inp" placeholder="任务描述" style="width:160px;"/>
                </td>
              </tr>
            </table>
        </sf:form>
    </div>
    
<script type="text/javascript" src="${ctxStatic}/js/jquery.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/global.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/whty/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.artDialog.js"></script> 
<script type="text/javascript" src="${ctxStatic}/js/jquery.artDialog.plugins.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/validation/messages_zh.min.js"></script>
<script type="text/javascript">
	/*errorPlacement 表明放在下方显示，如果不加默认在右边显示*/
	$().ready(function() {
		$("#jobAddForm").validate({
			rules: {
				jobName: "required",
				jobGroup: "required",
				targetMethod: {
					required: true,
					minlength: 2
				},
				cronExpression: "required"
			},
			messages: {
				jobName: "请输入任务名",
				jobGroup: "请输入任务组",
				targetMethod: {
					required: "请输入目标方法",
					minlength: "目标方法的长度大于2位"
				},
				cronExpression: "请输入定时任务时间表达式"
			},
			errorPlacement: function (error, element) {
				$('<br/>').appendTo(element.parent());  
		        error.appendTo(element.parent());  
	        }
		});
	});
	
	//由于artDialog 弹出层不主动关闭时无法提交，建议采用ajax提交数据
	function jobAdd(){
		if($("#jobAddForm").valid()) {
			var data = $("#jobAddForm").serialize();
			$.ajax({
	               "type" : "post",  
	               "url" : "${ctx}/core/jobSave",
	               "data" : data,                     
	               "success" : function(resp) {
	            	   if(resp.result){
	            		   alert("保存成功!");
		            	   onReload();
		            	   window.top.art.dialog({id:'addFrame'}).close();
	            	   }else{
	            		   alert("保存失败!");
	            	   }
	            	   
	               },
					"error" : function(resp) {
				   		alert("保存失败!");
				   }  
	           });
		}
		return false;
	}
	
	function onReload(){
		doReload();
	}
</script>
</body>
</html>