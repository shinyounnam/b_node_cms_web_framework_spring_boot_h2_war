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
	<div class="navi"><a href="">가입신청</a> &nbsp; > &nbsp; <span class="blue">쿠폰리스트</span></div>
	<div class="pt30"></div>
	<div class="alignright">
		
		<%
		String searchType1 = "";
		String searchType2 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >쿠폰명</option>
			<option value="2" <%=searchType2%> >내용</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:150px" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="쿠폰리스트">
		<colgroup>
			<col width="12%" />
			<col width="*%" />
			<col width="10%" />
			<col width="15%" />
			<col width="20%" />
			<col width="5%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>쿠폰명</th>
			<th>용도</th>
			<th>기간/동록일</th>
			<th>매수/구분</th>
			<th>상태</th>
		</tr>
		<%
		
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++){
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		%>
		<tr>
			<td><a href="javascript:OnDetail('<%=obj.getInt("VIP_ID")%>')"><%=pageSeq%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("VIP_ID")%>')"><%=obj.getString("NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("VIP_ID")%>')"><%=obj.getString("VIP_USE_NM")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("VIP_ID")%>')">
					<%=obj.getString("S_DATE")%>~<%=obj.getString("E_DATE")%><br/>
					<%=obj.getString("REG_DATE")%>
				</a>
			</td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("VIP_ID")%>')">
					<%=obj.getInt("VIP_USE_CNT")%><br/>
					<%=obj.getString("VIP_TYPE_NM")%>
				</a>
			</td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("VIP_ID")%>')"><%=obj.getString("VIP_STATUS")%></a></td>
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
		function OnDetail(aVipID)
		{
			location.href = "/vip_no_info/info/"+aVipID;
		}
	</script>
	<div class="pt30"></div>	
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnInsertVipNo();" class="btn01">쿠폰생성하기</a>
	</div>
</div>

<script type="text/javascript">

	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/vip_no_list/move/" + ACCP_STATUS +
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
		location.href = "/vip_no_list/move/" + ACCP_STATUS +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	function OnInsertVipNo()
	{
		location.href="/vip_no_insert";
	}	
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	