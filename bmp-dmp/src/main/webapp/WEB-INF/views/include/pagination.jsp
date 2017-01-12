<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
<input type="hidden" id="totalPage" name="totalPage" value="${page.totalPage}" />
<span>
<a href="#" onclick="skipToFirstPage()">首页</a>

<a href="#" onclick="skipToPrePage()">上一页</a>

<a id="pageNo" href="javascript:void(0)">${page.pageNo }</a>

<a href="#" onclick="skipToNextPage()">下一页</a>

<a href="#" onclick="skipToLastPage()">尾页</a>

</span><span class="mglr15 txt">共 ${page.totalPage } 页 | ${page.count } 条记录</span>
<span class="txt">去第</span>
<div class="mglr5 page_num_wrap t_l">
     <input id="pageJump" type="text" value="" class="num_text" >
     <p class="anim" style="left: 0px;">
          <input type="button" value="确定" onclick="skipTo()" class="cfm">
          <span class="txt">页</span>
      </p>
</div>

<script>
	
	function skipToFirstPage() {
		if($("#totalPage").val() <= 1){
			return false;
		}
		skipPage(1);
	}
	function skipToPrePage() {
		var curPage = document.getElementById("pageNo").value;
		var page = Number(curPage) - 1;
		if(page <= 1){
			return false;
		}
		skipPage(page);
	}
	function skipTo() {
		var page = document.getElementById("pageJump").value;
		if(page==$("#pageNo")){
			return false;
		}
		skipPage(page);
	}
	function skipToNextPage() {
		var curPage = document.getElementById("pageNo").value;
		if(curPage == $("#totalPage").val()){
			return false;
		}
		var page = Number(curPage) + 1;
		skipPage(page);
	}
	function skipToLastPage() {
		if($("#pageNo").val()==$("#totalPage").val()){
			return false;
		}
		var page = document.getElementById("totalPage").value;
		skipPage(page);
	}
	function skipPage(page) {
		var curPage = Number(page);
		if (curPage > 2147483647) {
			curPage = 2147483647;
		} else if (curPage <= 0) {
			curPage = 1;
		}
		document.getElementById("pageNo").value = curPage;
		document.search_form.submit();
	}
</script>