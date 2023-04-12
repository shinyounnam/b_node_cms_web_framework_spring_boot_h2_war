<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");        
    JSONObject header = (JSONObject)request.getAttribute("header");
    int i = 0;
    JSONObject obj = null;
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
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
				<%=obj0_0.getString("ID")%>
			</td>
			<th>비밀번호</th>
			<td colspan="2">
				<input type="button" onclick=javascript:OnChangePassword('<%=obj0_0.getInt("ADMIN_ID")%>'); value="비밀번호변경"/>
			</td>
		</tr>
		<tr>
			<th>*부서</th>
			<td colspan="2">
				<select id="GROUP_ID" name="GROUP_ID">
					<option value="">전체</option>
					<%
                    JSONArray data1 = data.getJSONArray(1);
					for (i=0;i<data1.length();i++) {
                        obj=data1.getJSONObject(i);
					if (obj0_0.getString("GROUP_ID").equals(obj.getString("CODE"))) {
					%>
						<option value="<%=obj.getString("CODE")%>" selected><%=obj.getString("NAME")%></option>
					<%
						} else {
					%>
						<option value="<%=obj.getString("CODE")%>"><%=obj.getString("NAME")%></option>
					<%
						}
					}
					%>
				</select>
			</td>
			<th>*팀</th>
			<td colspan="2">
				<input type="text" id="TEAM_NM" name="TEAM_NM" maxlength="20" style="width:100px" value="<%=obj0_0.getString("TEAM_NM")%>"/>
			</td>
		</tr>
		<tr>
			<th>*성명</th>
			<td colspan="2">
				<input type="text" id="NAME" name="NAME" maxlength="10" style="width:100px" value="<%=obj0_0.getString("NAME")%>"/>
			</td>
			<th>*직급</th>
			<td colspan="2">
				<input type="text" id="PSTN_NM" name="PSTN_NM" maxlength="20" style="width:100px" value="<%=obj0_0.getString("PSTN_NM")%>"/>
			</td>
		</tr>
		<tr>
			<th>*권한</th>
			<td colspan="2" >
				<%
				String strAUTH = obj0_0.getString("AUTH");
                JSONArray data2 = data.getJSONArray(2);
				for (i=0;i<data2.length();i++) {
                    obj = data2.getJSONObject(i);
					if (strAUTH.indexOf(obj.getString("CODE"))!=-1) {
				%>
						<input type="checkbox" name="AUTH" value="<%=obj.getString("CODE")%>" checked/><%=obj.getString("NAME")%><br/>
				<%
					} else {
				%>
						<input type="checkbox" name="AUTH" value="<%=obj.getString("CODE")%>"/><%=obj.getString("NAME")%><br/>
				<%
					}
				}
				%>
			</td>
			<th>*관리등급</th>
			<td colspan="2">
				<select id="MG_LEVEL" name="MG_LEVEL" style="width:100px">
					<option value="">선택</option>
					<%
                    JSONArray data3 = data.getJSONArray(3);
					for (i=0;i<data3.length();i++) {
                        obj = data3.getJSONObject(i);
						if (obj0_0.getString("MG_LEVEL").equals(obj.getString("CODE"))) {
					%>
							<option value="<%=obj.getString("CODE")%>" selected><%=obj.getString("NAME")%></option>
					<%
						} else {
					%>
							<option value="<%=obj.getString("CODE")%>"><%=obj.getString("NAME")%></option>
					<%
						}
					}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td colspan="2">
				<input type="text" id="EMAIL" name="EMAIL" maxlength="30" style="width:100px" value="<%=obj0_0.getString("EMAIL")%>"/>
			</td>
			<th>직통전화</th>
			<td colspan="2">
				<input type="text" id="TEL" name="TEL" maxlength="20" style="width:100px" value="<%=obj0_0.getString("TEL")%>"/>
			</td>
		</tr>
		<tr>
			<th>핸드폰</th>
			<td colspan="5">
				<input type="text" id="HP_TEL" name="HP_TEL" maxlength="20" style="width:100px"  value="<%=obj0_0.getString("HP_TEL")%>"/>
				<br/>
				* 로그인시 해당 휴대폰 번호로 인증하여, 로그인할 수 있습니다.
			</td>
		</tr>
		<tr>
			<th>메모</th>
			<td colspan="5">
				<textarea id="MEMO" name="MEMO" style="width:700px;height:100px;"><%=obj0_0.getString("MEMO")%></textarea>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<input type="hidden" id="ADMIN_ID" name="ADMIN_ID" value="<%=obj0_0.getInt("ADMIN_ID")%>"/>
		<a class="btn03" href="javascript:OnDelete();">삭제</a>
		<a class="btn03" href="javascript:OnUpdate();">수정</a>
		<a class="btn04" href="javascript:OnList();">목록</a>
	</div>
</div>

<script type="text/javascript">

	function OnUpdate()
	{
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
		
		data.ADMIN_ID = $("#ADMIN_ID").val();
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
			url:"/admin_mng_update/update",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_Update(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}
	
	function Callback_Update(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.result =="ok")
		{
			alert("수정하였습니다.");
			location.href = "/admin_mng";
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

	function OnDelete()
	{
		if (confirm("삭제하시겠습니까?"))
		{
			var data = {};
		
			data.ADMIN_ID = $("#ADMIN_ID").val();

			$.ajax({
				url:"/admin_mng_update/delete",
				type:"POST",
				data:JSON.stringify(data),
				contentType:"application/json",
				success:function(result) {
					Callback_Delete(result);
				},
				error:function(err) {
					alert(err.responseText);
				}
			});
		}
	}

	function Callback_Delete(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result == "ok")
		{
			alert("삭제하였습니다.");
			location.href="/admin_mng";
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function GetAuth()
	{
		var result = "";
		var length = $("input[name=AUTH]").length;
		var i =0;
		for (i=0;i<length;i++)
		{
			if ($("input[name=AUTH]")[i].checked==true)
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
			
		}
		return result;
	}

	function OnList()
	{
		location.href="/admin_mng";
	}

	function OnChangePassword(aAdminID)
	{
		var url = "/admin_mng_pwd/info/" + aAdminID;
		OpenWin(url,"admin_mng_pwd",900,600);
	}

</script>

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	