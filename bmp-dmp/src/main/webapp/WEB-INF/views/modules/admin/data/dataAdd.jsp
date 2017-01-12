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
    	<sf:form action="${ctx}/admin/dataSave" modelAttribute="configDataVo" method="post" id="addForm" name="addForm" class="txq_infor">
          	<table border="0" width="100%">
              <tr>
                <td align="right">数据名称</td>
                <td><input type="text" id="name" name="name" class="def_inp" placeholder="数据名称" style="width: 160px;" /> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
                <td align="right">数据编码</td>
                <td><input type="text" id="code" name="code" class="def_inp" placeholder="数据编码" style="width: 160px;" /> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
              	<td align="right">数据类型</td>
                <td> 
                	<sf:select path="dataType" class="def_sel" style="width:182px;">
						<sf:options items="${fns:getConfigDictList('dataType')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
					</sf:select>
                <span><em class="red">*</em></span></td>
              </tr>
              <tr>
              	<td align="right">是否启用</td>
                <td>
                	<sf:select path="jobStatus" class="def_sel" style="width:182px;">
						<sf:options items="${fns:getConfigDictList('jobStatus')}" itemLabel="name" itemValue="dValue" htmlEscape="false"/>
					</sf:select>
                <span><em class="red">*</em></span></td>
              </tr>
              <tr>
              	<td align="right">新增时间</td>
                <td>
                	<input type="text" id="createTime" name="createTime"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'createTime\')||\'2020-10-01\'}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px;" class="Wdate"><span>
                	<em class="red">*</em></span>
                </td>
              </tr>
              <tr>
              	<td align="right">更新时间</td>
                <td>
                	<input type="text" id="updateTime" name="updateTime"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'updateTime\')||\'2020-10-01\'}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px;" class="Wdate"><span>
                	<em class="red">*</em></span>
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
		$("#addForm").validate({
			rules: {
				name: {
					required: true,
					maxlength: 100
				},
				code: {
					required: true,
					maxlength: 100
				},
				dataType:"required",
				jobStatus:"required",
				createTime: {
					required: true,
					date: true
				},
			},
			messages: {
				name: {
					required: "请输入数据名称",
					maxlength: "数据名称的长度不大于100位"
				},
				code: {
					required: "请输入数据编码",
					maxlength: "数据编码的长度不大于100位"
				},
				dataType: {
					required: "请选择数据类型"
				},
				jobStatus: {
					required: "请选择是否启用"
				}
			},
			errorPlacement: function (error, element) {
				$('<br/>').appendTo(element.parent());  
		        error.appendTo(element.parent());  
	        }
		});
	});
	
	//由于artDialog 弹出层不主动关闭时无法提交，建议采用ajax提交数据
	function jobAdd(){
		if($("#addForm").valid()) {
			var data = $("#addForm").serialize();
			$.ajax({
	               "type" : "post",  
	               "url" : "${ctx}/admin/dataSave",
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