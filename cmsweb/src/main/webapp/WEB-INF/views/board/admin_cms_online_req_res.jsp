<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>

<!-- contents -->
<div class="contents">
	<h2>온라인 문의 답변</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">온라인 문의 답변</span></div>
	<div class="pt30"></div>
	<h3>문의 내용</h3>
	<table class="write_A" summary="">
		<colgroup>
			<col width="25%" />
			<col width="75%" />
		</colgroup>
		<tr>
			<th>구분</th>
			<td style="text-align:left;">
				<%=obj0_0.getString("GBN_NM")%>
			</td>	
		</tr>
		<tr>
			<th>이름</th>
			<td style="text-align:left;">
				<%=obj0_0.getString("NAME")%>
			</td>	
		</tr>
		<tr>
			<th>이메일</th>
			<td style="text-align:left;">
				<%=obj0_0.getString("EMAIL")%>
			</td>
		</tr>
		<tr>
			<th>연락처</th>
			<td style="text-align:left;">
				<%=obj0_0.getString("TEL")%>
				<input type="hidden" id="TEL" name="TEL" value="<%=obj0_0.getString("TEL")%>"/>
			</td>
		</tr>
		<tr>
			<th>문의제목</th>
			<td style="text-align:left;">
				<%=obj0_0.getString("REQ_TITLE")%>
			</td>
		</tr>
		<tr>
			<th>문의내용</th>
			<td style="text-align:left;">
				<%=obj0_0.getString("REQ_CONTENT")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>답변 내용</h3>
	<table class="write_A" summary="">
		<colgroup>
			<col width="25%" />
			<col width="75%" />
		</colgroup>
		<tr>
			<th>답변 내용</th>
			<td>
				<textarea id="RES_CONTENT" style="width:650px" maxlength="2000"><%=obj1_0.getString("RES_CONTENT")%></textarea>
				<br/>
				최대 한글 4000Byte<br/>
				<span style="color:red">60byte 이내일 경우 SMS로 발송됩니다. 60byte이상일 경우 LMS로 발송됩니다.</span>
			</td>	
		</tr>
	</table>
	<div class="pt30"></div>	
	<div class="alignright">
		<input type="hidden" id="REQ_DATE" value="<%=obj0_0.getString("REG_DATE")%>"/>
		<input type="hidden" id="REQ_TITLE" value="<%=obj0_0.getString("REQ_TITLE")%>"/>
		<input type="hidden" id="REQ_CONTENT" value="<%=obj0_0.getString("REQ_CONTENT")%>"/>
		<input type="hidden" id="UP_ID" value="<%=obj0_0.getInt("ID")%>"/>
		<input type="hidden" id="EMAIL" value="<%=obj0_0.getString("EMAIL")%>"/>
		<input type="hidden" id="RES_ID" value="<%=obj1_0.getInt("ID")%>"/>
		<a class="btn03" href="javascript:OnSend('<%=obj0_0.getInt("ID")%>');">보내기</a>
		<a class="btn03" href="javascript:OnDelete('<%=obj0_0.getInt("ID")%>');">삭제</a>
		<a class="btn04" href="javascript:OnCancel();">취소</a>
	</div>

</div>
<!-- contents -->


<script type="text/javascript">
	function OnSend(aUP_ID)
	{
		if ($("#RES_CONTENT").val() == "")
		{
			alert("답변 내용을 입력해주세요.");
			return;
		}

		if (confirm("SMS로 답변 내용을 보내시겠습니까?"))
		{
			var data = {};		
			data.REQ_DATE = $("#REQ_DATE").val();
			data.REQ_TITLE = $("#REQ_TITLE").val();
			data.REQ_CONTENT = $("#REQ_CONTENT").val();
			data.UP_ID = aUP_ID;
			data.RES_CONTENT = $("#RES_CONTENT").val();
			data.TEL = $("#TEL").val();

			$.ajax({
				url:"/admin_cms_online_req_res/save_send_sms",
				type:"POST",
				data:JSON.stringify(data),
				contentType:"application/json",
				success:function(result) {
					Callback_Send(result);
				},
				error:function(err) {
					alert(err.responseText);
				}
			});
		}
	}

	function Callback_Send(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.result == "ok")
		{
			alert("보냈습니다.");
		}
		else
		{
			alert(jsonData.message);
		}
	} 

	function OnDelete(aUP_ID)
	{
		if ($("#RES_ID").val() == 0)
		{
			alert("답변 내용이 저장되지 않아서, 삭제할 수 없습니다.");
			return;
		}
		if (confirm("삭제하시겠습니까?"))
		{
			var data = {};		
			data.RES_ID = $("#RES_ID").val();

			$.ajax({
				url:"/admin_cms_online_req_res/delete",
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

	function Callback_Delete(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.result == "ok")
		{
			alert("삭제했습니다.");
			location.href = "/admin_cms_online_req_res/info/"+ $("#UP_ID").val();
		}
		else
		{
			alert(jsonData.message);
		}
	} 
	function OnCancel()
	{
		location.href =  "/admin_cms_online_res";
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	