<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String FILE_NAME = header.getString("FILE_NAME");
    String FILE_SIZE = header.getString("FILE_SIZE");
    String CATEGORY = header.getString("CATEGORY");
%>
<script type="text/javascript" src="/javascripts/jquery-1.11.1.min.js"></script>
<script src="/daumeditor/js/popup.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="/daumeditor/css/popup.css" type="text/css"  charset="utf-8"/>

<div class="title">
	사진 첨부
	<a href="javascript:closeWindow();"><img src="/images/popup_close.gif" alt="" /></a>
</div>
<div class="pop_contents">
	<form method="post" name="frm" action="/admin_cms_board_file/insert" enctype="multipart/form-data">
	<input type="hidden" id="filerename" name="filerename" value="">
	<input type="hidden" id="filesize" name="filesize" value="">
	<div class="body">
		<dl class="alert">
		    <dt>사진 첨부 확인</dt>
		    <dd>
		    	삽입할 이미지를 등록해주세요.<br/><br/>
				<input type="file" name="image" id="image">
			</dd>
		</dl>
	</div>
	<div class="pt30"></div>
		<div class="alignright">
			<a class="btn03"  href="#" onclick="ValidateInput();" title="등록" class="btnlink">등록</a>
			<a class="btn04"  href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a>
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">
window.onload = function(){
	var strCategory = "<%=CATEGORY%>";
	if (strCategory == "contentimg") { 
		initUploader();
		ContentImage();
    }
};

function initUploader(){
	var _opener = PopupUtil.getOpener();
	
	if (!_opener) {
		alert('잘못된 경로로 접근하셨습니다.');
		return;
	}
	
	var _attacher = getAttacher('image', _opener);
	registerAction(_attacher);
}

function ContentImage(){
	if (typeof(execAttach) == 'undefined') { //Virtual Function
		return false;
	}

	var _mockdata = {
		'imageurl': 'http://cms1.veteranscout.co.kr<%=FILE_NAME%>',
		'filename': '<%=FILE_NAME%>',
		'filesize': '<%=FILE_SIZE%>',
		'imagealign': 'L',
		'originalurl': 'http://cms1.veteranscout.co.kr<%=FILE_NAME%>',
		'thumburl': 'http://cms1.veteranscout.co.kr<%=FILE_NAME%>'
	};

	execAttach(_mockdata);
	closeWindow();
}


function ValidateInput(){
	if($("#image").val() == ""){
		alert("먼저 업로드할 파일을 선택하십시요.");
		return;
	}

	var now = new Date();
	var filedatename = String(now.getFullYear())+String((now.getMonth()+1))+String(now.getDate())+String(now.getHours())+String(now.getMinutes())+String(now.getSeconds()+String(now.getMilliseconds()));
	var filerename = filedatename + "_" + ClearFilePath($("#image").val());
	//var filesize = document.getElementById("image").files[0].size;
	//var fileresize = (Number($("#tx_attach_up_size", opener.document).html().replace("MB", ""))+Number((filesize/1048576))).toFixed(2);

	//$("#tx_attach_up_size", opener.document).html(fileresize+"MB");
	$("#filerename").val(filerename);
	//$("#filesize").val(filesize);
	
	document.frm.submit();
}

function ClearFilePath(val){
    var tmpStr = val;
    
    var cnt = 0;
    while(true){
        cnt = tmpStr.indexOf("/");
        if(cnt == -1) break;
        tmpStr = tmpStr.substring(cnt+1);
    }
    while(true){
        cnt = tmpStr.indexOf("\\");
        if(cnt == -1) break;
        tmpStr = tmpStr.substring(cnt+1);
    }
    
    return tmpStr;
}
</script>
<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>