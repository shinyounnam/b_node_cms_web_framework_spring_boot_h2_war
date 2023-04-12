<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    JSONObject obj = null;
    int VIP_ID = header.getInt("VIP_ID");
    int i = 0;
%>

<!-- contents -->
<div class="contents">
	<div class="navi"><a href="">가입신청</a> &nbsp; > &nbsp; <span class="blue">쿠폰 상세 정보</span></div>
	<div class="pt30"></div>
	<div class="pt30"></div>
	<table class="list_A" summary="쿠폰상세 정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>쿠폰명</th>
			<td colspan="3"><%=obj0_0.getString("NAME")%></td>
		</tr>
		<tr>
			<th>등록일</th>
			<td><%=obj0_0.getString("REG_DATE")%></td>
			<th>상태</th>
			<td>
			<%
			String strVipStatus = obj0_0.getString("VIP_STATUS");
			if(strVipStatus.equals("1")) {
			%>
			<input type="radio" name="VIP_STATUS" value="1" checked/>진행
			<%
			} else { 
			%>
			<input type="radio" name="VIP_STATUS" value="1" />진행
			<%
			}
			%>
			<%
			if(strVipStatus.equals("2")) {
			%>
			<input type="radio" name="VIP_STATUS" value="2" checked/>중지
			<%
			} else { 
			%>
			<input type="radio" name="VIP_STATUS" value="2" />중지
			<%
			}
			%>
			</td>
		</tr>
		<tr>
			<th>기간</th>
			<td colspan="3">
				<%=obj0_0.getString("S_DATE")%>
				~
				<%=obj0_0.getString("E_DATE")%>
			</td>
		</tr>
		<tr>
			<th>용도</th>
			<td><%=obj0_0.getString("VIP_USE_NM")%></td>
			<th>매수</th>
			<td><%=obj0_0.getInt("VIP_USE_CNT")%></td>
		</tr>
		<tr>
			<th>방식/쿠폰번호</th>
			<td colspan="3">
				[<%=obj0_0.getString("VIP_TYPE_NM")%>] 
				<a href="javascript:OnUseList('<%=obj0_0.getInt("VIP_ID")%>')" class="btn01">내역보기</a>
				<%
				if(obj0_0.getString("VIP_TYPE").equals("1")) {
				%>
				쿠폰번호 : <%=obj0_0.getString("VIP_NO")%>
				<%
				}
				%>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="3">
				<%=obj0_0.getString("CONTENT")%>
			</td>
		</tr>
	</table>
	
	<%
    JSONArray data1 = data.getJSONArray(1);
	if (data1.length() > 0) {
	%>
	<div class="pt30"></div>
	<table class="list_A" summary="쿠폰상세 정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tbody>
			<tr>
				<th>날짜</th>
				<th>종류</th>
				<th>코드</th>
			</tr>
	<%
		for (i = 0; i < data1.length(); i++) {
            obj = data1.getJSONObject(i);
	%>
	
			<tr>
				<td><%=obj.get("START_DATE").toString()%></td>
				<td><%=obj.get("ITEM_NAME").toString()%></td>
				<td><%=obj.get("VIP_NO").toString()%></td>
			</tr>
		
	<%
		}
	%>
		</tbody>
	</table>
	<%
	}
	%>
	<div class="pt30"></div>	
	<div style="width:100%;text-align:right;">
		<input type="hidden" id="VIP_ID" name="VIP_ID" value="<%=VIP_ID%>"/>
		<a href="javascript:OnDelete()" class="btn01">삭제</a>
		<a href="javascript:OnUpdate()" class="btn01">저장</a>
		<a href="javascript:OnList()" class="btn01">목록</a>
	</div>
</div>

<script type="text/javascript">

	function OnDelete()
	{
		if (confirm("삭제하겠습니까?"))
		{
			var data = {};		
			data.VIP_ID = $("#VIP_ID").val();
			

			$.ajax({
				url: '/vip_no_info/delete',
	            type: 'POST',
	            data: JSON.stringify(data),
				contentType: 'application/json',
	            success: function(data) 
				{
					Callback_OnDelete(data);
	            },
				error: function(err) {
					alert(err.responseText);
				}
			});
		}
	}

	function Callback_OnDelete(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result == "ok")
		{
			alert("삭제하였습니다.");
			location.href = "/vip_no_list";
		}
		else
		{
			alert(jsonData.message);
		}
	}

	
	function OnUpdate()
	{
		var data = {};		
		data.VIP_ID = $("#VIP_ID").val();
		data.VIP_STATUS = $("input[name=VIP_STATUS]:checked").val();
		

		$.ajax({
			url: '/vip_no_info/update_vip_status',
            type: 'POST',
            data: JSON.stringify(data),
			contentType: 'application/json',
            success: function(data) 
			{
				Callback_OnUpdate(data);
            },
			error: function(err) {
				alert(err.responseText);
			}
		});
	}
	
	function Callback_OnUpdate(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result == "ok")
		{
			alert("저장하였습니다.");
			location.href = "/vip_no_list";
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnList()
	{
		location.href = "/vip_no_list";
	}

	function OnUseList()
	{
		var url = "/vip_no_use_list/info/"+$("#VIP_ID").val();
		OpenWin(url,"work_detail",900,600);
	}

</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	