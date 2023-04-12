<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");   
    String SORT_TYPE = header.getString("SORT_TYPE");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
    JSONObject obj = null;
%>


<!-- contents -->
<div class="contents">
	<h2>직업소개소 회원</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <span class="blue">직업소개소 회원</span></div>
	<div class="pt30"></div>
	<div class="floatleft  arrow">총 <span class="blue bold"><%=obj1_0.getInt("TOTAL_COUNT")%></span>개소</div>
	<div class="floatright">
		정렬&nbsp;:&nbsp;
		<%
		String sortType1 = "";
		String sortType2 = "";
		String sortType3 = "";
		String sortType4 = "";
		String sortType5 = "";
		if (SORT_TYPE.equals("1")) sortType1 = "selected";
		if (SORT_TYPE.equals("2")) sortType2 = "selected";
		if (SORT_TYPE.equals("3")) sortType3 = "selected";
		if (SORT_TYPE.equals("4")) sortType4 = "selected";
		if (SORT_TYPE.equals("5")) sortType5 = "selected";
		%>
		<select id="SORT_TYPE" name="SORT_TYPE" style="width:200px;">
			<option value="1" <%=sortType1%> >정보수정일</option>
			<option value="2" <%=sortType2%> >컨택히스토리</option>
			<option value="3" <%=sortType3%> >승인완료일</option>
			<option value="4" <%=sortType4%> >긴급인력요청이벤트일</option>
			<option value="4" <%=sortType5%> >긴급인력선발이벤트일</option>
		</select>
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		String searchType4 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("3")) searchType3 = "selected";
		if (SEARCH_TYPE.equals("4")) searchType4 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:130px;">
			<option value="1" <%=searchType1%> >직업소개소명</option>
			<option value="2" <%=searchType2%> >대표자</option>
			<option value="3" <%=searchType3%> >담당자</option>
			<option value="4" <%=searchType4%> >연락처</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:100px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="8%" />
			<col width="*%" />
			<col width="8%" />
			<col width="10%" />
			<col width="12%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="8%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>직업소개소 명</th>
			<th>담당자</th>
			<th>사내담당자</th>
			<th>담당자연락처</th>
			<th>공고/아이템</th>
			<th>이벤트<br/>긴급인력<br/>요청수</th>
			<th>이벤트<br/>긴급인력<br/>선발수</th>
			<th>
				<% if (SORT_TYPE.equals("1")) { %>정보 수정일
				<% } else if (SORT_TYPE.equals("2")) { %>컨택히스토리
				<% } else if (SORT_TYPE.equals("3")) { %>승인완료일
				<% } else if (SORT_TYPE.equals("4")) { %>긴급인력요청</br>이벤트일<%  %>
				<% } else if (SORT_TYPE.equals("5")) { %>긴급인력선발</br>이벤트일<%  %>
				<% } %>
			</th>
			<th>등급</th>
		</tr>	
		<%
		int i = 0;
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);            
		%>
		<tr>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=pageSeq%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("CORP_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MAN_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MNG_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MAN_TEL")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')">
				<%=obj.getInt("ING_TOTAL")%>(<%=obj.getInt("WORK_TOTAL")%>)/<%=obj.getInt("RES_TOTAL")%>
			    </a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getInt("EVENT_COUNT")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getInt("EVENT_RES_COUNT")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("SORT_DATE")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("SALE_LEVEL")%></a></td>
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
			location.href = "/user_detail/info/"+aCorpID;
		}
	</script>
</div>

<script type="text/javascript">
	function OnPage(aPage)
	{
		var SORT_TYPE = $("#SORT_TYPE").val();		
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage ;		
		location.href = "/user_mng/move?sort_type=" + SORT_TYPE +
				"&search_type=" + SEARCH_TYPE+
				"&search_txt="+encodeURIComponent(SEARCH_TXT)+
				"&page="+PAGE;
	}
	

	function OnSearch()
	{
		var SORT_TYPE = $("#SORT_TYPE").val();		
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();		
		var PAGE = "1";		
		location.href = "/user_mng/move?sort_type=" + SORT_TYPE +
				"&search_type=" + SEARCH_TYPE+
				"&search_txt="+encodeURIComponent(SEARCH_TXT)+
				"&page="+PAGE;
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	