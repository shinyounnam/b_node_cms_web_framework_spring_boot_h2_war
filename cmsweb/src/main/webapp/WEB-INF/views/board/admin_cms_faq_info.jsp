<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    int ID = header.getInt("ID");
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);  
    int i =0;
    JSONObject obj = null;  
%>
<!-- contents -->
<div class="contents">
	<h2>자주 묻는 질문</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">자주 묻는 질문</span></div>
	<div class="pt30"></div>
	<table class="write_A" summary="">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>구분</th>
			<td style="text-align:left">
				<%=obj0_0.getString("GUBUN")%>
			</td>
			<th>작성자</th>
			<td style="text-align:left">
				<%=obj0_0.getString("NAME")%>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td colspan="3" style="text-align:left">
				<%=obj0_0.getString("TITLE")%>
			</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan="3" style="text-align:left">
				<%
                JSONArray data2 = data.getJSONArray(2);
				if (data2.length() > 0) {                    
					for (i=0;i<data2.length();i++) {
                        obj = data2.getJSONObject(i);
					%>
					    <a href="https://cms.veteranscout.co.kr<%=obj.getString("FILE_URL")%>" target="_blank"><%=obj.getString("ORG_FILE_NAME")%></a><br/>
					<%
					}
				}
				%>
			</td>
		</tr>
		
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnUpdate('<%=ID%>');">수정</a>
		<a class="btn03" href="javascript:OnDelete('<%=ID%>');">삭제</a>
		<a class="btn04" href="javascript:history.back(-1);">취소</a>
		<input type="hidden" name="ID" id="ID" value="<%=ID%>">
	</div>
</div>
<script>
function htmlEntityDec(str){
	if(str == "" || str == null){
		return str;
	}
	else{
		return str.replace(/&amp;/gi, "&").replace(/&#35;/gi, "#").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "'").replace(/&#39;/gi, '\\').replace(/&#37;/gi, '%').replace(/&#40;/gi, '(').replace(/&#41;/gi, ')').replace(/&#43;/gi, '+').replace(/&#47;/gi, '/').replace(/&#46;/gi, '.').replace(/&#59;/g, ";");
	}
}
</script>
<script type="text/javascript">
	
	function OnUpdate(aID)
	{
		document.location.href = "/admin_cms_faq_insert/update/info/"+aID;
	}

	function OnDelete(aID)
	{
		if (confirm("삭제하시겠습니까?"))
		{
			var data = {};		
			data.ID = $("#ID").val();
			$.ajax({
				url:"/admin_cms_board_info/delete",
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
			alert("삭제하였습니다.");
			document.location.href = "/admin_cms_faq";
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnCancel()
	{
		document.location.href = "/admin_cms_faq";
	}



</script>



<!-- contents -->
<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	