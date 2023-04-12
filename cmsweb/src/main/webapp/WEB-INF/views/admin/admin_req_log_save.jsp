<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
%>

<!-- contents -->
<div class="contents">
	<h2>관리자 위치정보 요청자료</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">관리자 위치정보 요청자료 등록</span></div>
	<div class="pt30"></div>
	<table class="write_A" summary="가입 신청 상세 기본정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>요청자</th>
			<td colspan="5">
				<input type="text" id="USER_NAME" name="USER_NAME" maxlength="20" style="width:100px"/>
				<input type="hidden" id="USER_TYPE" name="USER_TYPE" value=""/>
				<input type="hidden" id="USER_ID" name="USER_ID" value=""/>
				<a class="btn01" href="javascript:OnUserSelect();" style="margin-left:5px;">회원선택</a>
			</td>
		</tr>
		<tr>
			<th>목적</th>
			<td colspan="5">
				<textarea id="CONTENT" name="CONTENT" style="width:700px;height:100px;"></textarea>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnSave();">등록</a>
		<a class="btn04" href="javascript:OnCancel();">취소</a>
	</div>
</div>
<script type="text/javascript">

	
	function OnSave()
	{
		if ($("#USER_ID").val()=="")
		{
			alert("요청자를 입력해주세요");
			return;
		}
		
		if ($("#CONTENT").val()=="")
		{
			alert("목적을 입력해주세요.");
			return;
		}
		
		var data = {};
		data.USER_TYPE = $("#USER_TYPE").val();
		data.USER_ID = $("#USER_ID").val();
		data.CONTENT = $("#CONTENT").val();

		$.ajax({
			url:"/admin_req_log_save/insert",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_Save(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}
	
	function Callback_Save(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.result =="ok")
		{
			alert("저장하였습니다.");
			location.href="/admin_req_log";
		}	
		else
		{
			alert(jsonData.message);
		}
	}


	function OnCancel()
	{
		location.href="/admin_req_log";
	}
	
	function OnUserSelect()
	{
		var CORP_IDS = $("#CORP_IDS").val();
		if (CORP_IDS == "") CORP_IDS = " ";
		var EMP_IDS = $("#EMP_IDS").val();
		if (EMP_IDS == "") EMP_IDS = " ";
		var url = "/admin_slt_user/info/"+CORP_IDS+"/"+EMP_IDS+"/";
		OpenWin(url,"admin_slt_user",900,600);
	}

	function SetUserId(aUserID, aUserName)
	{
		$("#USER_TYPE").val("EMP");
		$("#USER_ID").val(aUserID);
		$("#USER_NAME").val(aUserName);
	}
</script>

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	