<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int i = 0;
    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
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
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >이름</option>
			<option value="2" <%=searchType2%> >전화번호</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:200px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="">
		<colgroup>
			<col width="5%" />
			<col width="10%" />
			<col width="10%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>이름</th>
			<th>휴대폰번호</th>
			<th>개인정보</br>활용동의</th>
			<th>슬로건제안<br/>[한국어]</th>
			<th>슬로건제안<br/>[영어]</th>
			<th>슬로건제안<br/>[중국어]</th>
			<th>등록일</th>
		</tr>
		<%
		int nPAGE = PAGE;
		int nSEQ = (nPAGE-1)*15;
		i =0;
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		    nSEQ = nSEQ + 1;
		%>
		<tr>
			<td><%=pageSeq%></td>
			<td><%=obj.getString("S_NAME")%></td>
			<td><%=obj.getString("S_TEL")%></td>
			<td><%=obj.getString("S_PV_YN_NM")%></td>
			<td class="left">
				1.(<%=obj.getString("S_KOR1")%>)<br/>
				2.(<%=obj.getString("S_KOR2")%>)<br/>
			</td>
			<td class="left">
				1.(<%=obj.getString("S_ENG1")%>)<br/>
				2.(<%=obj.getString("S_ENG2")%>)<br/>
			</td>
			<td class="left">
				1.(<%=obj.getString("S_CHN1")%>)<br/>
				2.(<%=obj.getString("S_CHN2")%>)<br/>
			</td>
			<td>	
				<%=obj.getString("REG_DATE")%>
			</td>
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
</div>
<!-- contents -->

<script type="text/javascript">
	
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/admin_cms_event_s_board/move" +
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
		location.href = "/admin_cms_event_s_board/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	