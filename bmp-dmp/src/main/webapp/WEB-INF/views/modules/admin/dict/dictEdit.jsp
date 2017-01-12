<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典添加</title>
<link href="${ctxStatic}/css/whty/w_public.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/whty/wdstyle.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="mgtb10">
    	<form action="${ctx}/admin/dictSave" method="post" id="addForm" name="addForm" class="txq_infor">
    		<input type="hidden" name="id" id="id" value="${objBean.id}">
          	<table border="0" width="100%">
              <tr>
                <td align="right">字典名称</td>
                <td><input type="text" id="name" name="name" value="${objBean.name}" class="def_inp" placeholder="字典名称" style="width: 160px;" /> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
                <td align="right">字典键</td>
                <td><input type="text" id="key" name="dKey" value="${objBean.dKey}" class="def_inp" placeholder="字典键" style="width: 160px;" /> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
              	<td align="right">字典值</td>
                <td><input type="text" id="value" name="dValue" value="${objBean.dValue}" class="def_inp" placeholder="字典值" style="width:160px;"/> <span><em class="red">*</em></span></td>
              </tr>
              <tr>
              	<td align="right">字典类型</td>
                <td><input type="text" id="type" name="type" value="${objBean.type}" class="def_inp" placeholder="字典类型" style="width: 160px;"/> <span><em class="red">*</em></span></td>
              </tr>
            </table>
        </form>
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
				key: {
					required: true,
					maxlength: 100
				},
				value: {
					required: true,
					maxlength: 100
				},
				type: {
					required: true,
					maxlength: 100
				}
			},
			messages: {
				name: {
					required: "请输入字典名称",
					maxlength: "字典名称的长度不大于100位"
				},
				name: {
					required: "请输入字典值",
					maxlength: "字典名称的长度不大于100位"
				},
				value: {
					required: "请输入字典值",
					maxlength: "字典值的长度不大于100位"
				},
				type: {
					required: "请输入字典类型",
					maxlength: "字典类型的长度不大于100位"
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
	               "url" : "${ctx}/admin/dictSave",
	               "data" : data,                     
	               "success" : function(resp) {
	            	   if(resp.result){
	            		   alert("保存成功!");
		            	   onReload();
		            	   window.top.art.dialog({id:'updateFrame'}).close();
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