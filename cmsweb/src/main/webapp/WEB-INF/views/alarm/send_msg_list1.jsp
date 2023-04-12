<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    int CORP_ID = header.getInt("CORP_ID");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int i = 0;
    JSONObject obj = null;
    JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);
%>

<!-- contents -->
<div class="contents">
	<h2>직업소개소 알림</h2>
	<div class="navi"><a href="">알림 관리</a> &nbsp; > &nbsp; 직업소개소 알림 &nbsp; > &nbsp; <span class="blue">알림 내역 리스트</span></div>
	<div class="pt20"></div>
	<div class="agency floatleft">직업소개소 : <%=data[0][0].CORP_NAME%></div>
	<div class="floatright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >보낸사람</option>
			<option value="2" <%=searchType2%> >내용</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:100px;" value="<%=SEARCH_TXT%>"/>
		<input type="hidden" id="CORP_ID" name="CORP_ID" value="<%=CORP_ID%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<div class="tab01">
		<ul>
			<li><a href="javascript:OnMove('1');">보낸 메시지</a></li>
			<li class="on"><a href="javascript:OnMove('2');">받은 메시지</a></li>
			<li><a href="javascript:OnMove('3');">보낸 알림</a></li>
			<li><a href="javascript:OnMove('4');">받은 알림</a></li>
		</ul>
	</div>
	<div class="pt30"></div>
	<div class="pt30"></div>
	<table class="list_A" summary="직업소개소 알림">
		<colgroup>
			<col width="10%" />
			<col width="18%" />
			<col width="18%" />
			<col width="*%" />
			<col width="18%" />
		<tr>
			<th>NO</th>
			<th>받은 시간</th>
			<th>보낸 사람</th>
			<th>내용</th>
			<th>확인여부</th>
		</tr>
		<% 
		int nSeq = 0
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data1 = data.getJSONArray(1);

		for (var i = 0; i < data1.length(); i++) { 
            obj = data1.getJSONObject(i);
		    pageSeq = (obj2_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		    nSeq = nSeq + 1
		%>
		<tr>
			<td><%=pageSeq%></td>
			<td><%=obj.getString("RECV_DATE")%></td>
			<td><%=obj.getString("SEND_NAME")%></td>
			<td>
				<a href="javascript:OnMsgInfo('<%=obj.getString("BASE_TYPE")%>','<%=obj.getInt("CORP_ID")%>','<%=obj.getInt("MSG_ID%>');">
				<%=obj.getString("CONTENT")%>
				</a>
			</td>
			<td><%=obj.getString("READ_YN_NM")%></td>
		</tr>
		<% 
		    pageIndex = pageIndex + 1;
		} 
		%>
	</table>
	<script>
		var totalPage = '<%=obj2_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','15','<%=obj2_0.getInt("TOTAL_PAGE")%>'));
		}
		function OnMsgInfo(aBaseType, aCorpID, aMsgID)
		{
			var url = "/msg_info1/info/"+aBaseType+"/"+aCorpID+"/"+aMsgID;
			OpenWin(url,"msg_info1",1000,600);
		}
	</script>
</div>
<script type="text/javascript">
	
	function OnPage(aPage)
	{
		var CORP_ID = $("#CORP_ID").val();
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/send_msg_list1/move" + 
				"/" + CORP_ID + 
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE+"/";
	}
	

	function OnSearch()
	{
		var CORP_ID = $("#CORP_ID").val();
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		location.href = "/send_msg_list1/move" + 
				"/" + CORP_ID + 
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE+"/";
	}
	function OnMove(aType)
	{
		switch(aType)
		{
		case "1":
			location.href = "/send_msg_list/info/" + $("#CORP_ID").val() + "/";
			break;
		case "2":
			location.href = "/send_msg_list1/info/" + $("#CORP_ID").val() + "/";
			break;
		case "3":
			location.href = "/send_msg_list2/info/" + $("#CORP_ID").val() + "/";
			break;
		case "4":
			location.href = "/send_msg_list3/info/" + $("#CORP_ID").val() + "/";
			break;
		}
	}
	
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	