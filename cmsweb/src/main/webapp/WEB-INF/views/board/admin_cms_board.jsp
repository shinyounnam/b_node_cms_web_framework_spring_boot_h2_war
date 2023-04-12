<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");    

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);    
    JSONObject obj = null;   
	int i =0;
%>


<!-- contents -->
<div class="contents">
	<h2>이벤트/공지사항</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">이벤트/공지사항</span></div>
	<div class="pt30"></div>
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		if (SEARCH_TYPE.equals("1")) { searchType1="selected"; }
		if (SEARCH_TYPE.equals("2")) { searchType2="selected"; }
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >제목</option>
			<option value="2" <%=searchType2%> >내용</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:200px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="">
		<colgroup>
			<col width="6%" />
			<col width="10%" />
			<col width="10%" />
			<col width="54%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>구분</th>
			<th>대상</th>
			<th>대상구분</th>
			<th>제목</th>
			<th>날짜</th>
		</tr>
		<%
		int nPAGE = PAGE;
		int nSEQ = (nPAGE-1)*15;
		

		int pageIndex = 0;
		int pageSeq=0;

        JSONArray data0 = data.getJSONArray(0);

		for (i=0;i<data0.length();i++) {

            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		    nSEQ = nSEQ + 1;
		%>
		<tr>
			<td><%=pageSeq%></td>
			<td><a href="javascript:OnUpdate('<%=obj.getInt("ID")%>');"><%=obj.getString("GUBUN")%></a></td>
			<td><a href="javascript:OnUpdate('<%=obj.getInt("ID")%>');"><%=obj.getString("USER_GBN_NM")%></a></td>
			<td class="left"><a href="javascript:OnUpdate('<%=obj.getInt("ID")%>');"><%=obj.getString("TITLE")%> - <b>
            <%=obj.getString("CLOSE_NM")%></b></a></td>
			<td><a href="javascript:OnUpdate('<%=obj.getInt("ID")%>');"><%=obj.getString("REG_DATE")%></a></td>
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
			document.write(PageMake('<%=PAGE%>','10','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
	</script>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="#" onclick="return OnInsert();">등록</a>
	</div>
	

</div>
<!-- contents -->


<script type="text/javascript">
	
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/admin_cms_board/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/admin_cms_board/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	
	function OnInsert()
	{
		location.href="/admin_cms_board_insert/insert/info";
		return false;
	}

	function OnUpdate(aID)
	{
		location.href="/admin_cms_board_info/info/"+aID;
		return false;
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	