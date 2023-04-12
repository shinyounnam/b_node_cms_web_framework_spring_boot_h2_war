<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int PAGE_SIZE = header.getInt("PAGE_SIZE");

    int i = 0;
    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>

<div class="title">
	이벤트 검색
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>
<!-- contents -->
<div class="pop_contents">
	<div class="alignright">
		<%
		String searchType1 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:150px">
			<option value="1" <%=searchType1%> >이벤트명</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:200px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt10"></div>
	<h3 class="floatleft">이벤트 검색 결과</h3>
	<table class="list_B" summary="메시지 보내기">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
			<col width="15%" />
			<col width="15%" />
			<col width="10%"/>
		</colgroup>
		<tr>
			<th>이벤트구분</th>
			<th>이벤트명</th>
			<th>시작일</th>
			<th>종료일</th>
			<th>선택</th>
		</tr>
		<%
		JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		%>
		<tr>
			<td>
				<%=obj.getString("GBN_NM")%>
			</td>
			<td>
				<%=obj.getString("TITLE")%>
			</td>
			<td>
				<%=obj.getString("S_DATE")%>
			</td>
			<td>
				<%=obj.getString("E_DATE")%>
			</td>
			<td>
				<a class="btn01" href=javascript:OnSelect('<%=obj.getInt("ID")%>','<%=obj.getString("TITLE")%>');>선택</a>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<script>
		var TOTAL_COUNT = "<%=obj1_0.getInt("TOTAL_COUNT")%>";
		if (TOTAL_COUNT > 0)
		{
			document.write(PageMake("<%=PAGE%>","<%=PAGE_SIZE%>","<%=obj1_0.getInt("TOTAL_PAGE")%>"));
		}
	</script>
	<div class="pt30"></div>
		<div class="alignright">
			<input type="hidden" id="PAGE_SIZE" name="PAGE_SIZE" value="<%=PAGE_SIZE%>"/>
			<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
			<a class="btn04" href="javascript:OnClose();">닫기</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	
	function OnClose()
	{
		self.close();
	}

	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE_SIZE = $("#PAGE_SIZE").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/slt_event/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE_SIZE+
				"/"+PAGE+
				"/";
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE_SIZE = $("#PAGE_SIZE").val();
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/slt_event/move" + 
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE_SIZE+
				"/"+PAGE+
				"/";
	}
	/*
	function OnSelect(aUserID,aUserName)
	{

		opener.SetUserId(aUserID, aUserName);
		self.close();
	}
	*/
	function OnSelect(eventId, title)
	{
		
		opener.SetEventId(eventId,title);
		self.close();
	}
</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>