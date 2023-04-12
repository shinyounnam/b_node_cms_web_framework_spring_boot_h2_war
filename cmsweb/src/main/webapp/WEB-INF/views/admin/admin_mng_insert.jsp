<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");        
    JSONObject header = (JSONObject)request.getAttribute("header");
    int i = 0;
    JSONObject obj = null;
%>

<!-- contents -->
<div class="contents">
	<h2>관리자 정보 등록</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">관리자 등록/수정</span></div>
	<div class="pt30"></div>
	<table class="write_A" summary="가입 신청 상세 기본정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>*아이디</th>
			<td colspan="2">
				<input type="text" id="ID" name="ID" maxlength="20" style="width:100px"/>
				<input type="button" id="ID_CHECK" onclick="javascript:OnIdCheck()" value="중복확인"/>
				<input type="hidden" id="ID_CHECK_OK" name="ID_CHECK_OK" value="N"/>
			</td>
			<th>비밀번호</th>
			<td colspan="2">
				<input type="password" id="PWD" name="PWD" maxlength="25" style="width:100px"/>
			</td>
		</tr>
		<tr>
			<th>*부서</th>
			<td colspan="2">
				<select id="GROUP_ID" name="GROUP_ID">
					<option value="">전체</option>
					<%
					JSONArray data0 = data.getJSONArray(0);
					for (i=0;i<data0.length();i++) {
                        obj = data0.getJSONObject(i);
					%>
					    <option value="<%=obj.getString("CODE")%>"><%=obj.getString("NAME")%></option>
					<%
					}
					%>
				</select>
			</td>
			<th>*팀</th>
			<td colspan="2">
				<input type="text" id="TEAM_NM" name="TEAM_NM" maxlength="20" style="width:100px"/>
			</td>
		</tr>
		<tr>
			<th>*성명</th>
			<td colspan="2">
				<input type="text" id="NAME" name="NAME" maxlength="10" style="width:100px"/>
			</td>
			<th>*직급</th>
			<td colspan="2">
				<input type="text" id="PSTN_NM" name="PSTN_NM" maxlength="20" style="width:100px"/>
			</td>
		</tr>
		<tr>
			<th>*권한</th>
			<td colspan="2" >
				<%
                JSONArray data1 = data.getJSONArray(1);
				for (i=0;i<data1.length();i++) {
                    obj = data1.getJSONObject(i);
				%>
				<input type="checkbox" name="AUTH" value="<%=obj.getString("CODE")%>"/><%=obj.getString("NAME")%><br/>
				<%
				}
				%>
			</td>
			<th>*관리등급</th>
			<td colspan="2">
				<select id="MG_LEVEL" name="MG_LEVEL" style="width:100px">
					<option value="">선택</option>
					<%
                    JSONArray data2 = data.getJSONArray(2);
					for (i=0;i<data2.length();i++) {
                        obj = data2.getJSONObject(i);
					%>
					<option value="<%=obj.getString("CODE")%>"><%=obj.getString("NAME")%></option>
					<%
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td colspan="2">
				<input type="text" id="EMAIL" name="EMAIL" maxlength="30" style="width:100px"/>
			</td>
			<th>직통전화</th>
			<td colspan="2">
				<input type="text" id="TEL" name="TEL" maxlength="20" style="width:100px"/>
			</td>
		</tr>
		<tr>
			<th>핸드폰</th>
			<td colspan="5">
				<input type="text" id="HP_TEL" name="HP_TEL" maxlength="20" style="width:100px"/>
				<br/>
				* 로그인시 해당 휴대폰 번호로 인증하여, 로그인할 수 있습니다.
			</td>
		</tr>
		<tr>
			<th>메모</th>
			<td colspan="5">
				<textarea id="MEMO" name="MEMO" style="width:700px;height:100px;"></textarea>
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

	function OnIdCheck()
	{
		if ($("#ID").val()=="")
		{
			alert("아이디를 입력해주세요.");
			return;
		}

		if ($("#ID").val().length<5)
		{
			alert("아이디를 5자리 이상 입력해주세요.");
			return;
		}
		
		var data = {};
		data.ID = $("#ID").val();

		$.ajax({
			url:"/admin_mng_insert/id_check",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_IdCheck(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
		
	}

	function Callback_IdCheck(data)
	{
		var jsonData = JSON.parse(data);		
		if (jsonData.result=="ok")
		{
			alert(jsonData.message);
			$("#ID_CHECK_OK").val("Y");
		}
		else
		{
			alert(jsonData.message);
		}
	}
	
	function OnSave()
	{
		if ($("#ID").val()=="")
		{
			alert("아이디를 입력해주세요");
			return;
		}
		if ($("#ID_CHECK_OK").val()=="N")
		{
			alert("아이디 중복 체크를 해주세요.");
			return;
		}
		if ($("#PWD").val()=="")
		{
			alert("비밀번호를 입력해주세요.");
			return;
		}

		var PWD = $("#PWD").val();
		if(ValidatePwdCheck(PWD)==false){
			alert("비밀번호는 영문, 숫자, 특수문자로 10자이상 입력해주세요.");
			return false;
		}

		if ($("#GROUP_ID").val()=="")
		{
			alert("부서를 선택해주세요.");
			return;
		}
		if ($("#TEAM_NM").val()=="")
		{
			alert("팀을 입력해주세요.");
			return;
		}
		if ($("#NAME").val()=="")
		{
			alert("성명을 입력해주세요.");
			return;
		}
		if ($("#PSTN_NM").val()=="")
		{
			alert("직급을 입력해주세요.");
			return;
		}
		if ($("#EMAIL").val()=="")
		{
			alert("이메일을 입력해주세요.");
			return;
		}
		var email = $("#EMAIL").val();
		if(ValidateEmailCheck(email)==false){
			alert("이메일이 유효하지 않습니다. 다시 입력해주세요.");
			return;
		}

		if ($("#TEL").val()=="")
		{
			alert("직통전화를 입력해주세요.");
			return;
		}

		/*
		if (ValidateTel($("#TEL").val())==false){
			alert("직통전화를 02-1234-5678 형식으로 입력해주세요.");
			return;
		}
		*/


		if ($("#HP_TEL").val()!="")
		{
			if (ValidateHpTel($("#HP_TEL").val())==false){
				alert("핸드폰 번호를 010-1234-5678 형식으로 입력해주세요.");
				return;
			}
		}

		


		var AUTH = GetAuth();
		if (AUTH == "")
		{
			alert("권한을 입력해주세요.");
			return;
		}
		if ($("#MG_LEVEL").val()=="")
		{
			alert("관리등급을 입력해주세요.");
			return;
		}
		if ($("#MEMO").val()=="")
		{
			alert("메모를 입력해주세요.");
			return;
		}

		var data = {};
		data.ID = $("#ID").val();
		data.PWD = $("#PWD").val();
		data.GROUP_ID = $("#GROUP_ID").val();
		data.TEAM_NM = $("#TEAM_NM").val();
		data.NAME = $("#NAME").val();
		data.PSTN_NM = $("#PSTN_NM").val();
		data.AUTH = AUTH;
		data.MG_LEVEL = $("#MG_LEVEL").val();
		data.EMAIL = $("#EMAIL").val();
		data.TEL = $("#TEL").val();
		data.HP_TEL = $("#HP_TEL").val();
		data.MEMO = $("#MEMO").val();


		$.ajax({
			url:"/admin_mng_insert/insert",
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
			location.href="/admin_mng";
		}	
		else
		{
			alert(jsonData.message);
		}
	}

	function ValidateEmailCheck(strEmail) 
	{
		var regExp = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
		if (regExp.test(strEmail))
		{
			return true;
		}
		return false;
	}
	
	function ValidateHpTel(tel_num){
		var patternPhone = /01[0|1|6|7|8|9]-[0-9]{3,4}-[0-9]{3,4}/;

		//둘중에 하나골라 쓰면 된다.
		if(patternPhone.test(tel_num))
		{
			return true;
		}  
		return false;
	}

	function ValidateTel(tel_num){
		var patternPhone = /^\d{2,3}-\d{3,4}-\d{4}$/;

		//둘중에 하나골라 쓰면 된다.
		if(patternPhone.test(tel_num))
		{
			return true;
		}  
		return false;
	}

	function ValidatePwdCheck(pwd_string){
		var regType1 = check = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{10,}$/;
		if (regType1.test(pwd_string)){
			return true;
		}
		return false;
	}

	function GetAuth()
	{
		var result = "";
		var length = $("input[name=AUTH]").length;
		var i =0;
		for (i=0;i<length;i++)
		{
			if (result =="")
			{
				result = $("input[name=AUTH]")[i].value;
			}
			else
			{
				result = result + "," + $("input[name=AUTH]")[i].value;
			}
		}
		return result;
	}

	function OnCancel()
	{
		location.href="/admin_mng";
	}

</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	