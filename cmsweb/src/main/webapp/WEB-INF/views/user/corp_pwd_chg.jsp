<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%    
    JSONObject header = (JSONObject)request.getAttribute("header");  
    int CORP_ID = header.getInt("CORP_ID");
%>


<script type='text/javascript'>

	function OnChgPwd()
	{	
		if ($("#PWD").val() == "")
		{
			alert("새 비밀번호를 입력해주세요.");
			return false;
		}
		if ($("#RE_PWD").val() == "")
		{
			alert("새 비밀번호 재입력을 입력해주세요.");
			return false;
		}

		if ($("#PWD").val()!=$("#RE_PWD").val())
		{
			alert("새 비밀번호와 새 비밀번호 재입력값과 틀립니다. 다시 입력해주세요.");
			return false;
		}

		if (confirm("비밀번호를 변경하시겠습니까?"))
		{
			var data = {};
			data.CORP_ID = $("#CORP_ID").val();
			data.PWD = $("#PWD").val();
			$.ajax({
	              		url: '/corp_pwd_chg/update_pwd',
	              		type: 'POST',
	                        data: JSON.stringify(data),
				contentType: 'application/json',
	              		success: function(data) 
				{
					Callback_Update(data);
	              		},
				error: function(err) {
					alert(err.responseText);
				}
	          	});
		}
	}
	function Callback_Update(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.data[0][0].result=="ok")
		{
			alert("비밀번호가 변경되었습니다.");
			self.close();
		}
		else
		{
			alert(jsonData[0][0].message);
		}
	}
	function OnClose()
	{
		self.close();
	}
</script>

 <div id="pop_wrap">
	<div class="title">
		비밀번호 변경
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div class="pop_contents">
	<h3>기본정보</h3>
	<table class="write_B" summary="기본정보">
		<colgroup>
			<col width="30%" />
			<col width="70%" />
		</colgroup>
		<tr>
			<td>
				새 비밀번호
			</td>
			<td>
				<input type="password" id="PWD" name="PWD" style="width:200px" class="form-control"/>
			</td>
		</tr>
		<tr>
			<td>
				새 비밀번호 재입력
			</td>
			<td>
				<input type="password" id="RE_PWD" name="RE_PWD" style="width:200px" class="form-control"/>
			</td>
		</tr>
	</table>
	<div style="height:20px;width:100%"></div>
	<div style="text-align:center;">
		<input type="hidden" id="CORP_ID" name="CORP_ID" value="<%=CORP_ID%>"/>
		<a class="btn03" href="javascript:OnChgPwd();">변경하기</a>
	</div>
 </div>


<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	