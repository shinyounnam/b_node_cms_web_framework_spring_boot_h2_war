<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
	String G_IMAGES_URL = header.getString("G_IMAGES_URL");
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    int i = 0;
    JSONObject obj = null;
	int EMP_ID = header.getInt("EMP_ID");


%>

 <!-- wrap -->
 <div id="pop_wrap">
	<div class="title">
		인력정보 상세보기
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div class="pop_contents">
	<h3>기본정보</h3>
	<table class="write_B" summary="기본정보">
		<colgroup>
			<col width="10%" />
			<col width="20%" />
			<col width="20%" />
			<col width="10%" />
		</colgroup>
		<tr>
			<td rowspan="2">
				<% 
				if(obj0_0.getString("FILE_NAME").equals("")==false) 
				{ 
				%>
					<img src="<%=G_IMAGES_URL+obj0_0.getString("FILE_URL")%><%=obj0_0.getString("FILE_NAME")%>" style="width:100px;height:100px;"/>
				<% 
				} 
				else 
				{ 
				%>
					<img src="/images/no_img.gif" style="width:100px;height:100px;"/>
				<% 
				} 
				%>
			</td>
			<td>
			<%=obj0_0.getString("NAME")%>(<%=obj0_0.getString("GENDER")%>/<%=obj0_0.getInt("AGE")%>)<br/>
			이벤트코드 : <%=obj0_0.getString("EVENT_CODE")%>
			</td>
			<td>
			<%=obj0_0.getString("CORP_NAMES")%><br/>
			조공:<%=obj0_0.getString("HELP_TYPE")%><br/>
			기공:<%=obj0_0.getString("SKILL_TYPE")%><br/>
			파출:<%=obj0_0.getString("DISP_TYPE")%><br/>
			기타:<%=obj0_0.getString("GITA_TYPE")%><br/>
			</td>
			<td>
			경력 <%=obj0_0.getString("EXP_YEAR")%>
			</td>
		</tr>
		<tr>			
			<td colspan="2">
				위치 : <%=obj0_0.getString("ADDR")%>
			</td>
			<td>
				<%=obj0_0.getString("TEL")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>근무 정보</h3>
	<table class="write_B" summary="근무 정보">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>신뢰도</th>
			<td colspan="2">
				<%=obj0_0.getInt("TRUST_CNT")%>% [근무일:<%=obj0_0.getInt("ATTEND_CNT")%>/불참:<%=obj0_0.getInt("ABSENT_CNT")%>]
			</td>
		</tr>
		<tr>
			<th>최근상태</th>
			<td>
			<%=obj0_0.getString("LST_WORK_NAME")%>(<%=obj0_0.getString("LST_WORK_DATE")%>)
			</td>
			<td>
			<%=obj0_0.getString("LST_SHOW_RES_NM")%>
			</td>
		</tr>
	</table>
	<%
	
	int nSEQ = 0;
	String trDisplay = "";
	int nWorkView = 0;
	int nItemView = 0;
	
	%>
	<div class="pt10"></div>
	<table id="WORK_LIST" class="list_B" summary="근무 정보">
		<colgroup>
			<col width="15%" />
			<col width="20%" />
			<col width="*%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>날짜</th>
			<th>현장명</th>
			<th>결과</th>
		</tr>
		<%
        JSONArray data1 = data.getJSONArray(1);
		for (i=0;i<data1.length();i++) 
		{
            obj = data1.getJSONObject(i);
		    nSEQ = nSEQ + 1;
		    trDisplay = "";
			if (nSEQ > 5) 
			{
				trDisplay = "display:none";
			}
			else
			{
				trDisplay = "display:";
			}
		%>
		<tr style="<%=trDisplay%>" >
			<td><%=nSEQ%></td>
			<td><%=obj.getString("WORK_DATE")%></td>
			<td><%=obj.getString("NAME")%></td>
			<td><%=obj.getString("RESULT")%></td>
		</tr>
		<%
		}

		if (nSEQ > 5) 
		{
			nWorkView = 5;
		}
		else
		{
			nWorkView = nSEQ;
		}
		%>
	</table>
	<input type="hidden" id="WORK_VIEW_COUNT" name="WORK_VIEW_COUNT" value="<%=nWorkView%>"/>
	<a href="javascript:OnAddWorkView();" class="btn_more">더보기 +</a>

	<div class="pt30"></div>
	<h3>아이템  정보</h3>
	<table class="write_B" summary="아이템 정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<%
        JSONArray data2 = data.getJSONArray(2);
		for (i=0;i<data2.length();i++) 
		{
            obj = data2.getJSONObject(i);
		%>
		<tr>
			<th>아이템/구매명</th>
			<td>
				<%=obj.getString("ITEM_NAME")%>
			</td>
			<th>구매일시</th>
			<td>
				<%=obj.getString("BUY_DATE")%>
			</td>
		</tr>
		<tr>
			<th>잔여기한/횟수</th>
			<td>
				<%=obj.getInt("RES_CNT")%>회
			</td>
			<th>결제방법</th>
			<td>
				<%=obj.getString("PAY_MTHD_NM")%>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	i = 0;
	nSEQ = 0;
	trDisplay = "";
	%>
	<div class="pt10"></div>
	<table id="ITEM_LIST" class="write_B" summary="아이템 정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>날짜</th>
			<th>아이템명</th>
			<th>결제방법</th>
		</tr>
		<%
        JSONArray data3 = data.getJSONArray(3);
		for (i=0;i<data3.length();i++) 
		{
            obj = data3.getJSONObject(i);
		    nSEQ = nSEQ + 1;
			if (nSEQ > 5) 
			{
				trDisplay="display:none;";
			}
			else
			{
				trDisplay="display:;";
			}
		%>
			<tr style="<%=trDisplay%>" >
				<td><%=nSEQ%></td>
				<td><%=obj.getString("BUY_DATE")%></td>
				<td><%=obj.getString("ITEM_NAME")%></td>
				<td><%=obj.getString("PAY_MTHD_NM")%></td>
			</tr>
		<%

		}

		if (nSEQ > 5) 
		{
			nItemView = 5;
		}
		else
		{
			nItemView = nSEQ;
		}

		%>
	</table>
	<input type="hidden" id="ITEM_VIEW_COUNT" name="ITEM_VIEW_COUNT" value="<%=nItemView%>"/>
	<input type="hidden" id="EMP_ID" name="EMP_ID" value="<%=EMP_ID%>"/>
	<a href="javascript:OnAddItemView();" class="btn_more">더보기 +</a>
</div>


<script type="text/javascript">

	function OnClose()
	{
		self.close();
	}
	
	function OnAddWorkView()
	{
		var totalCount = $("#WORK_LIST > tbody > tr").length;
		var viewCount = parseInt($("#WORK_VIEW_COUNT").val());
		var toViewCount = parseInt(viewCount) + 5;
		if (totalCount <= viewCount)
		{
			alert("추가로 등록된 데이타가 없습니다.등록해주세요.");
			return;
		}
		if (toViewCount > totalCount)
		{
			toViewCount = totalCount;
		}
		$("#WORK_VIEW_COUNT").val(toViewCount);
		var i = 0;
		for (i = viewCount-1;i<toViewCount;i++)
		{
			$("#WORK_LIST > tbody > tr")[i].style.display="";
		}
	}

	function OnAddItemView()
	{
		var totalCount = $("#ITEM_LIST > tbody > tr").length;
		var viewCount = parseInt($("#ITEM_VIEW_COUNT").val());
		var toViewCount = parseInt(viewCount) + 5;
		if (totalCount <= viewCount)
		{
			alert("추가로 등록된 데이타가 없습니다.등록해주세요.");
			return;
		}
		if (toViewCount > totalCount)
		{
			toViewCount = totalCount;
		}
		$("#ITEM_VIEW_COUNT").val(toViewCount);
		var i = 0;
		for (i = viewCount-1;i<toViewCount;i++)
		{
			$("#ITEM_LIST > tbody > tr")[i].style.display="";
		}
	}
</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	