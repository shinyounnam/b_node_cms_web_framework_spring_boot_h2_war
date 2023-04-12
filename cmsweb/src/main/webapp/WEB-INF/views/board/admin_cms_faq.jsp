<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    String GBN = header.getString("GBN");
    int i = 0;
    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>

<!-- contents -->
<div class="contents">
	<h2>자주 묻는 질문</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">자주 묻는 질문</span></div>
	
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
	<%
	String strClass0 = "";
	String strClass1 = "";
	String strClass2 = "";
	String strClass3 = "";
	String strClass4 = "";
	String strClass5 = "";
	String strClass6 = "";
	String strClass7 = "";
	String strClass8 = "";
	if (GBN.equals("ALL")) strClass0 = "on";
	if (GBN.equals("B1")) strClass1 = "on";
	if (GBN.equals("B2")) strClass2 = "on";
	if (GBN.equals("B3")) strClass3 = "on";
	if (GBN.equals("B4")) strClass4 = "on";
	if (GBN.equals("E1")) strClass5 = "on";
	if (GBN.equals("E2")) strClass6 = "on";
	if (GBN.equals("E3")) strClass7 = "on";
	if (GBN.equals("E4")) strClass8 = "on";
	%>
	<table class="list_A" summary="">
		<tr>
			<th rowspan="2">
				<a href="/admin_cms_faq" class="<%=strClass0%>">전체보기</a>
			</th>
			<th colspan="4">직업소개소</th>
			<th colspan="4">구직자</th>
		</tr>
		<tr>
			<th>
				<a href="javascript:OnMovePage('B1');" class="<%=strClass1%>">회원가입</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('B2');" class="<%=strClass2%>">앱 관련</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('B3');" class="<%=strClass3%>">이용문의</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('B4');" class="<%=strClass4%>">기타문의</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('E1');" class="<%=strClass5%>">회원가입</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('E2');" class="<%=strClass6%>">앱 관련</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('E3');" class="<%=strClass7%>">이용문의</a>
			</th>
			<th>
				<a href="javascript:OnMovePage('E4');" class="<%=strClass8%>">기타문의</a>
			</th>
		</tr>
	</table>
	<div class="pt30"></div>
	<table id="table_id" class="list_A" summary="" style="width:900px">
		<tr>
			<th style="width:100px;">NO</th>
			<th style="width:150px;">구분</th>
			<th style="width:500px;">제목</th>
			<th style="width:150px;">바로보기</th>
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
			<tr >
				<td style="width:100px;">
					<%=pageSeq%>
				</td>
				<td style="width:150px;">
					<a href="javascript:OnUpdate('<%=obj.getInt("ID")%>');"><%=obj.getString("GUBUN")%></a>
				</td>
				<td style="width:500px;">
					<a href="javascript:OnUpdate('<%=obj.getInt("ID")%>');"><%=obj.getString("TITLE")%></a>
				</td>
				<td style="width:150px;">
					<a href="javascript:OnDisplay('<%=obj.getInt("ID")%>');">
						<label id="label_<%=obj.getInt("ID")%>">▼보기</label>
						<input type="hidden" id="CONTENT_<%=obj.getInt("ID")%>" value="<%=obj.getString("CONTENT")%>"/>
					</a>
				</td>
			</tr>
			<tr id="tr_display_<%=obj.getInt("ID")%>" style="display:none;">
				<td colspan="4" style="width:900px;text-align:left;">
					<script>
						var content = "<%=obj.getString("CONTENT")%>";
						var htmlEntityValue = htmlEntityDec(content);
						document.write(htmlEntityValue);
					</script>
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
	<div class="alignright">
		<input type="hidden" id="GBN" value="<%=GBN%>"/>
		<a class="btn03" href="#" onclick="return OnInsert();">등록</a>
	</div>
	

</div>
<!-- contents -->
<script type="text/javascript">
	function htmlEntityDec(str){
		if(str == "" || str == null){
			return str;
		}
		else{
			return str.replace(/&amp;/gi, "&").replace(/&#35;/gi, "#").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "'").replace(/&#39;/gi, '\\').replace(/&#37;/gi, '%').replace(/&#40;/gi, '(').replace(/&#41;/gi, ')').replace(/&#43;/gi, '+').replace(/&#47;/gi, '/').replace(/&#46;/gi, '.').replace(/&#59;/g, ";");
		}
	}	
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		var GBN = $("#GBN").val();
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/admin_cms_faq/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE+
				"/"+GBN;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		var GBN = $("#GBN").val();
		location.href = "/admin_cms_faq/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE+
				"/"+GBN;
	}

	function OnMovePage(aGBN)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		var GBN = aGBN;
		location.href = "/admin_cms_faq/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE+
				"/"+GBN;
	}
	
	function OnInsert()
	{
		location.href="/admin_cms_faq_insert/insert/info";
		return false;
	}

	function OnUpdate(aID)
	{
		location.href="/admin_cms_faq_info/info/"+aID;
		return false;
	}

	function OnDisplay(aID)
	{
		if ($("#tr_display_"+aID).css("display") == "none")
		{
			$("#tr_display_"+aID).css("display","");
			$("#label_"+aID).html("▼접기");
		}
		else
		{
			$("#tr_display_"+aID).css("display","none");
			$("#label_"+aID).html("▼보기");
		}
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	