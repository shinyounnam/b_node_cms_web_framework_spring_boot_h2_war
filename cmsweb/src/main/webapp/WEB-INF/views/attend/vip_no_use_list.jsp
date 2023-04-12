<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");   
    
    int i = 0;
    JSONObject obj = null;
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);

    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE_SIZE = header.getInt("PAGE_SIZE");
    int VIP_ID = header.getInt("VIP_ID");
    int PAGE = header.getInt("PAGE");
%>

<!-- contents -->
 <div id="pop_wrap">
	<div class="title">
		쿠폰 (자동) 생성/이용 내역 보기
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div style="height:10px;"></div>
	<div style="width:100%;height:20px;text-align:left;">
		■ 쿠폰생성번호 : 
        <%
        if(obj0_0.has("VIP_NO")){
        %>
            <%=obj0_0.getString("VIP_NO")%>
        <%
        }        
        %>
	</div>
	<div style="height:20px;">
		* <%=obj0_0.getInt("VIP_USE_CNT")%> 매 발행 중 <%=obj0_0.getInt("VIP_USED_CNT")%>매 사용
	</div>
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("3")) searchType3 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE">
			<option value="1" <%=searchType1%> >사용자</option>
			<option value="2" <%=searchType2%> >연락처</option>
			<option value="3" <%=searchType3%> >쿠폰번호</option>
		</select>
		<input type="hidden" id="VIP_ID" name="VIP_ID" value="<%=VIP_ID%>"/>
		<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
		<input type="hidden" id="PAGE_SIZE" name="PAGE_SIZE" value="<%=PAGE_SIZE%>"/>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" value="<%=SEARCH_TXT%>" style="width:100px;"/>
		<a href="javascript:OnSearch()" class="btn01">검색</a>
	</div>
	<div class="pop_contents">			
		<table class="write_B" summary="기본정보">
			<colgroup>
				<col width="10%" />
				<col width="15%" />
				<col width="*" />
				<col width="*" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>NO</th>
					<th>쿠폰번호</th>
					<th>사용자 (연락처)</th>
					<th>사용일시</th>
					<th>상태</th>
				</tr>
			</thead>
			<tbody>
				<%				
				int pageIndex = 0;
				int pageSeq;
				JSONArray data1 = data.getJSONArray(1);
				for (i=0;i<data1.length();i++) {
                    obj = data1.getJSONObject(i);
					pageSeq = (obj2_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
				%>
					<tr>
						<td><%=pageSeq%></td>
						<td><%=obj.getString("VIP_NO")%></td>
						<td>
							<%=obj.getString("CORP_NAME")%>(<%=obj.getString("TEL")%>)
						</td>
						<td>
							<%=obj.getString("REG_DATE")%>							
						</td>
						<td>
							<%=obj.getString("USE_YN_NM")%>
						</td>
					</tr>
					
				<%
				    pageIndex = pageIndex + 1;					
				}
				%>
			</tbody>
		</table>
		<script>
			var totalPage = '<%=obj2_0.getInt("TOTAL_PAGE")%>';
			if (totalPage > 0)
			{
				document.write(PageMake('<%=PAGE%>','<%=PAGE_SIZE%>','<%=obj2_0.getInt("TOTAL_PAGE")%>'));
			}
		</script>
	</div>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnClose();">닫기</a>
	</div>

</div>
<script type="text/javascript">

	function OnPage(aPage)
	{
		var VIP_ID = $("#VIP_ID").val();
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/vip_no_use_list/move/" + VIP_ID +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var VIP_ID = $("#VIP_ID").val();
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/vip_no_use_list/move/" + VIP_ID +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	
	function OnClose()
	{
		window.close();
	}
</script>



<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	