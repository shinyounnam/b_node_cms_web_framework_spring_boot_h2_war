<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");   
    String ACCP_STATUS = header.getString("ACCP_STATUS");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");

    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>

<!-- contents -->
<div class="contents">
	<div class="navi"><a href="">가입신청</a> &nbsp; > &nbsp; <span class="blue">가입신청 현황</span></div>
	<div class="pt30"></div>
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		String searchType4 = "";
		String searchType5 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("3")) searchType3 = "selected";
		if (SEARCH_TYPE.equals("4")) searchType4 = "selected";
		if (SEARCH_TYPE.equals("5")) searchType5 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >직업소개소명</option>
			<option value="2" <%=searchType2%> >대표자</option>
			<option value="3" <%=searchType3%> >담당자</option>
			<option value="4" <%=searchType4%> >대표자 전화번호</option>
			<option value="5" <%=searchType5%> >대표번호</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:150px" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<div class="tab01">
		<ul>
			<li><a href="/attend_req">전체 리스트</a></li>
			<li  class="on"><a href="/attend_req1">접수 중</a></li>
			<li><a href="/attend_req2">승인 완료</a></li>
			<li><a href="/attend_req3">보류/반려</a></li>
		</ul>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="12%" />
			<col width="*%" />
			<col width="10%" />
			<col width="15%" />
			<col width="20%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>직업소개소 명</th>
			<th>담당자</th>
			<th>담당자연락처</th>
			<th>신청일</th>
			<th>쿠폰</th>
			<th>상태</th>
		</tr>
		<%
		int i = 0;
		int pageIndex = 0;
		int pageSeq;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++){
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		%>
		<tr>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=pageSeq%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("CORP_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MAN_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MAN_TEL")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("REQ_DATE")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("VIP_YN")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("ACCP_STATUS")%></a></td>
		</tr>
		<%
		    pageIndex = pageIndex + 1;
		}
		%>
	</table>
	<script>
		var totalPage = '<%=obj1_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','15','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
		function OnDetail(aCorpID)
		{
			location.href = "/attend_req_detail/info/"+aCorpID;
		}
	</script>
	
</div>

<script type="text/javascript">

	function OnPage(aPage)
	{
		var ACCP_STATUS = "1";
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/attend_req1/move/" + ACCP_STATUS +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var ACCP_STATUS = "1";
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/attend_req1/move/" + ACCP_STATUS +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
</script>



<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	