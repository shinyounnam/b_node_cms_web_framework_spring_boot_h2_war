<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int PAGE_SIZE = header.getInt("PAGE_SIZE");

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);    
    JSONObject obj = null;   
%>

<!-- contents -->
<div class="contents">
	<h2>직업소개소 알림</h2>
	<div class="navi"><a href="">알림 관린</a> &nbsp; > &nbsp; 직업소개소 알림  &nbsp; > &nbsp; <span class="blue">알림 관리 리스트</span></div>
	<div class="pt30"></div>
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("3")) searchType3 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >직업소개소</option>
			<option value="2" <%=searchType2%> >대표자</option>
			<option value="3" <%=searchType3%> >담당자</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:100px;" value="<%=SEARCH_TXT%>"/>
		<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
		<input type="hidden" id="PAGE_SIZE" name="PAGE_SIZE" value="<%=PAGE_SIZE%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="직업소개소 알림">
		<colgroup>
			<col width="8%" />
			<col width="6%" />
			<col width="*%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>선택</th>
			<th>직업소개소 명</th>
			<th>보낸 메시지</th>
			<th>받은 메시지</th>
			<th>보낸 알림</th>
			<th>받은 알림</th>
			<th>상세보기</th>
		</tr>
		<%
		int i =0;
		int nPAGE = PAGE;
		int nPAGE_SIZE = PAGE_SIZE;
		int nSEQ = (nPAGE-1)*nPAGE_SIZE;

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
			<td>
				<input type="checkbox" name="CORP_ID" value="<%=obj.getInt("CORP_ID")%>"/>
				<input type="hidden" name="CORP_NAME" value="<%=obj.getString("CORP_NAME")%>"/>
			</td>
			<td class="left"><%=obj.getString("CORP_NAME")%></td>
			<td><%=obj.getInt("SEND_MSG_SU")%></td>
			<td><%=obj.getInt("RECV_MSG_SU")%></td>
			<td><%=obj.getInt("SEND_ALARM_SU")%></td>
			<td><%=obj.getInt("RECV_ALARM_SU")%></td>
			<td>
				<a class="btn01" href="javascript:OnMsgDetail('<%=obj.getInt("CORP_ID")%>');" style="width:100px">상세보기</a>
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
			document.write(PageMake('<%=PAGE%>','<%=PAGE_SIZE%>','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
	</script>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" onclick="javascript:OnSendMsg()" onfocus='this.blur()' target="_blank" style="cursor:pointer">메시지 보내기</a>
	</div>
</div>


<script type="text/javascript">
	
	function OnSendMsg()
	{
		if ($("input[name=CORP_ID]:checked").length == 0)
		{
			alert("메시지 보낼 직업소개소를 선택해주세요.");
			return;
		}
		var length = parseInt($("input[name=CORP_ID]").length);
		var i = 0;
		var CORP_IDS = "";
		var CORP_NAMES = "";
		for (i=0;i<length;i++)
		{
			if ($("input[name=CORP_ID]")[i].checked == true)
			{
				if (CORP_IDS=="")
				{
					CORP_IDS = $("input[name=CORP_ID]")[i].value;
					CORP_NAMES = $("input[name=CORP_NAME]")[i].value;
				}
				else
				{
					CORP_IDS = CORP_IDS + "|" + $("input[name=CORP_ID]")[i].value;
					CORP_NAMES = CORP_IDS + "|" + $("input[name=CORP_NAME]")[i].value;
				}
			}
		}
		var url = "/send_msg/info/" + CORP_IDS + "/" + CORP_NAMES;
		OpenWin(url,"send_msg",950,600);
	}
	
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE_SIZE = $("#PAGE_SIZE").val();
		$("#PAGE").val(aPage);
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/alarm_mng/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE_SIZE+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE_SIZE = $("#PAGE_SIZE").val();
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/alarm_mng/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE_SIZE+
				"/"+PAGE;
	}
	function OnMsgDetail(aCorpID)
	{
		location.href = "/send_msg_list/info/" + aCorpID+"/";
	}
</script>



<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	