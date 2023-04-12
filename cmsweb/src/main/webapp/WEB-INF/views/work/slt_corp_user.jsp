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
	직업소개소 검색
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>
<!-- contents -->
<div class="pop_contents">
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:150px">
			<option value="1" <%=searchType1%> >직업소개소명</option>
			<option value="2" <%=searchType2%> >담당자</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:200px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt10"></div>
	<h3 class="floatleft">회원 검색 결과</h3>
	<table class="list_B" summary="메시지 보내기">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
			<col width="15%" />
			<col width="15%" />
			<col width="10%"/>
		</colgroup>
		<tr>
			<th>직업소개소명</th>
			<th>전화번호</th>
			<th>담당자</th>
			<th>가입일</th>
			<th>선택</th>
		</tr>
		<%
		JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		%>
		<tr>
			<td>
				<%=obj.getString("CORP_NAME")%>
			</td>
			<td>
				<%=obj.getString("MAN_TEL")%>
			</td>
			<td>
				<%=obj.getString("MAN_NAME")%>
			</td>
			<td>
				<%=obj.getString("ACCP_DATE")%>
			</td>
			<td>
			    <!--CORP_ID,CORP_NAME,POST_CODE,CHAMGO,ADDR1,ADDR2,POS_LAT,POS_LNG,MAN_NAME,MAN_TEL,MAN_PSTN-->
				<a class="btn01" href="javascript:OnSelect('<%=obj.getInt("CORP_ID")+"|"+obj.getString("CORP_NAME")+"|"+obj.getString("POST_CODE")+"|"+obj.getString("CHAMGO")+"|"+obj.getString("ADDR1")+"|"+obj.getString("ADDR2")+"|"+obj.getDouble("POS_LAT")+"|"+obj.getDouble("POS_LNG")+"|"+obj.getString("MAN_NAME")+"|"+obj.getString("MAN_TEL")+"|"+obj.getString("MAN_PSTN")%>')";>
					선택
				</a>
				
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
		location.href = "/slt_corp_user/move" +
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
		location.href = "/slt_corp_user/move" + 
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
	function OnSelect(strValue)
	{		
		opener.SetUserInfo(strValue);
		self.close();
	}


	
</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>