function doQuery() {
	document.search_form.submit();
}

function reload(){
	window.location.reload();
}

/**
 * 由于artDialog使用的是top.art.dialog.open 所以采用window.top.document.getElementById
 * 如果artDialog使用的是art.dialog.open，应该采用window.document.getElementById
 * window.parent
 */
function doSave(iframeId){
	return window.top.document.getElementById(iframeId).contentWindow.jobAdd();
}

/**
 * 刷新右边框
 */
function doReload(){
	window.top.document.getElementById("mainIframe").contentWindow.reload();
}

