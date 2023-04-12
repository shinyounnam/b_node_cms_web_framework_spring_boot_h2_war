<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%    
    JSONObject header = (JSONObject)request.getAttribute("header");
    int ADMIN_ID = header.getInt("IN_ADMIN_ID");
%>
<!-- wrap -->
 <div id="pop_wrap">
	<div class="title">
		비밀번호 변경
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div class="pop_contents">
	<table class="write_B" summary="기본정보">
		<colgroup>
			<col width="20%" />
			<col width="80%" />
		</colgroup>
		<tr>
			<th>비밀번호</th>
			<td colspan="4">
				<input type="password" id="PWD" name="PWD" maxlength="20" style="width:100px" value=""/>
			</td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td colspan="4">
				<input type="password" id="RE_PWD" name="RE_PWD" maxlength="20" style="width:100px" value=""/>
			</td>
		</tr>
		
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<input type="hidden" id="ADMIN_ID" name="ADMIN_ID" value="<%=ADMIN_ID%>"/>
		<a class="btn03" href="javascript:OnUpdate();">수정</a>
		<a class="btn03" href="javascript:OnClose();">닫기</a>
	</div>

</div>


<script type="text/javascript">
	function OnClose()
	{
		self.close();
	}
	
	function OnUpdate()
	{
		if ($("#PWD").val() == "")
		{
			alert("비밀번호를 입력해주세요.");
			return false;
		}
		var PWD = $("#PWD").val();
		if(ValidatePwdCheck(PWD)==false){
			alert("비밀번호는 영문, 숫자, 특수문자로 10자이상 입력해주세요.");
			return false;
		}
		
		if ($("#RE_PWD").val() == "")
		{
			alert("비밀번호 확인을 입력해주세요.");
			return false;
		}
		var RE_PWD = $("#RE_PWD").val();
		if(ValidatePwdCheck(RE_PWD)==false){
			alert("비밀번호 확인은 영문, 숫자, 특수문자로 10자이상 입력해주세요.");
			return false;
		}

		if ($("#PWD").val() != $("#RE_PWD").val())
		{
			alert("비밀번호와 비밀번호 확인값이 다릅니다.\r\n다시 입력해주세요.");
			return false;
		}

		var data = {};
		data.ADMIN_ID = $("#ADMIN_ID").val();
		data.PWD = $("#PWD").val();

		$.ajax({
			url:"/admin_mng_pwd/update_pwd",
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
			alert("비밀번호를 변경하였습니다.");
			self.close();
		}	
		else
		{
			var ERROR_CODE = jsonData.ERROR_CODE;
            if (ERROR_CODE == "2"){
                alert("이전에 변경했던 비밀번호와 같습니다. 다른 비밀번호를 입력해주세요.");
            }
            else{
                alert(jsonData.message);
            }
		}
	}

	function ValidatePwdCheck(pwd_string){
		var regType1 = check = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{10,}$/;
		if (regType1.test(pwd_string)){
			return true;
		}
		return false;
	}
</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>