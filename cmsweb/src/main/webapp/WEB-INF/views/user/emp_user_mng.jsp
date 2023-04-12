<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");  
	String G_IMAGES_URL = header.getString("G_IMAGES_URL");
    String GBN = header.getString("GBN");
    String SORT_TYPE = header.getString("SORT_TYPE");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int i = 0;
    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>
<div class="contents">
	<h2>근로자 회원</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <a href="">근로자 회원</a> &nbsp; > &nbsp; <span class="blue">리스트</span></div>
	<div class="pt30"></div>
	<div class="floatright">
		근로자 구분 : 
		<%
		String gbn1 = "";
		String gbn2 = "";
		String gbn3 = "";
		if (GBN.equals("ALL")) gbn1 = "selected";
		if (GBN.equals("POOL")) gbn2 = "selected";
		if (GBN.equals("VETERAN")) gbn3 = "selected";
		%>
		<select id="GBN" name="GBN" style="width:150px;">
			<option value="ALL" <%=gbn1%> >전체</option>
			<option value="POOL" <%=gbn2%> >소속</option>
			<option value="VETERAN" <%=gbn3%> >베테랑</option>
		</select>

		정렬 : 
		<%
		String sortType1 = "";
		String sortType2 = "";
		if (SORT_TYPE.equals("1")) sortType1 = "selected";
		if (SORT_TYPE.equals("2")) sortType2 = "selected";
		%>
		<select id="SORT_TYPE" name="SORT_TYPE" style="width:150px;">
			<option value="1" <%=sortType1%> >신뢰도순</option>
			<option value="2" <%=sortType2%> >가입일순</option>
		</select>

		검색 : 
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		String searchType4 = "";
		String searchType5 = "";
		String searchType6 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("3")) searchType3 = "selected";
		if (SEARCH_TYPE.equals("4")) searchType4 = "selected";
		if (SEARCH_TYPE.equals("5")) searchType5 = "selected";
		if (SEARCH_TYPE.equals("6")) searchType6 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:150px;">
			<option value="1" <%=searchType1%> >근로자명</option>
			<option value="2" <%=searchType2%> >소속직업소개소명</option>
			<option value="3" <%=searchType3%> >전화번호</option>
			<option value="4" <%=searchType4%> >이벤트코드</option>
			<option value="5" <%=searchType5%> >일반가입</option>
			<option value="6" <%=searchType6%> >간편로그인</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" value="<%=SEARCH_TXT%>" style="width:150px;"/>
		<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>

	<table class="list_C" summary="가입 신청 현황">
		<colgroup>
			<col width="8%" />
			<col width="14%" />
			<col width="27%" />
			<col width="28%" />
			<col width="15%" />
			<col width="8%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>프로필 사진</th>
			<th colspan="3">지원자 정보</th>
			<th>아이템</th>
		</tr>
		<%
		i = 0;
		int nPage = PAGE;
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*10);
		%>
		<tr>
			<td rowspan="5">
				<%=pageSeq%>
			</td>
			<td rowspan="5">
				<% if (obj.getString("FILE_NAME").equals("")==false && obj.getString("FILE_NAME") != null) { %>
					<img src="<%=G_IMAGES_URL+obj.getString("FILE_URL")%><%=obj.getString("FILE_NAME")%>" style="width:100px;height:100px;" />
				<% } else { %>
					<img src="/images/no_img.gif" style="width:100px;height:100px;" />
				<% } %>
			</td>
			<td>
				<%=obj.getString("NAME")%> (<%=obj.getString("GENDER")%> / <%=obj.getInt("AGE")%>)
			</td>
			<td>
				조공:<%=obj.getString("HELP_TYPE")%><br/>
				기공:<%=obj.getString("SKILL_TYPE")%><br/>
				파출:<%=obj.getString("DISP_TYPE")%><br/>
				기타:<%=obj.getString("GITA_TYPE")%><br/>
			</td>
			<td>
				경력 <%=obj.getString("EXP_YEAR")%>
			</td>
			<td rowspan="5" class="blue1 bold">
				<% if (obj.getInt("ITEM_SU") > 0) {%>
				○<br/>보유
				<% } else { %>
				x<br/>미보유
				<% } %>
				<br/>
				<a href="javascript:OnUserDetail('<%=obj.getInt("EMP_ID")%>')" class="btn01">상세</a>
				<% if(obj.getString("POOL_YN").equals("Y")) { %>
					<br/>
					<a href="javascript:OnDeleteCorpPool('<%=obj.getInt("EMP_ID")%>')" class="btn02">소속해제</a>
				<% } %>
			</td>
		</tr>
		<tr>
			<td>
				<%=obj.getString("CORP_NAMES")%>
			</td>
			<td class="left">
				가입위치 : <%=obj.getString("A_ADDR")%><br/>
				현재위치 : <%=obj.getString("ADDR")%><br/>
				가입일 : <%=obj.getString("REG_DATE")%><br/>
				구분 : <%=obj.getString("LINK_LIST")%>						
			</td>
			<td>
				<%=obj.getString("TEL")%>
			</td>
		</tr>
		<tr>
			<th>신뢰도</th>
			<td colspan="2">
				<%=obj.getInt("TRUST_CNT")%>% [ 근무일:<%=obj.getInt("ATTEND_CNT")%> / 불참:<%=obj.getInt("ABSENT_CNT")%>] 
			</td>
		</tr>
		<tr>
			<th>최근 상태</th>
			<td><%=obj.getString("LST_WORK_NAME")%>(<%=obj.getString("LST_WORK_DATE")%>)</td>
			<td><%=obj.getString("LST_SHOW_RES_NM")%></td>
		</tr>
		<tr>
			<th>이벤트 코드</th>
			<td colspan="2">
				<%=obj.getString("EVENT_CODE")%>
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
</div>
<script type="text/javascript">

	function OnPage(aPage)
	{
		var SORT_TYPE = $("#SORT_TYPE").val();
		if (SORT_TYPE =="") SORT_TYPE = " ";
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT =="") SEARCH_TXT = " ";
		var PAGE = aPage;
		var GBN = $("#GBN").val();
		location.href = "/emp_user_mng/move/" + SORT_TYPE + 
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE + 
				"/"+GBN+
				"/END";
	}
	

	function OnSearch()
	{
		var SORT_TYPE = $("#SORT_TYPE").val();
		if (SORT_TYPE =="") SORT_TYPE = " ";
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = $("#PAGE").val();
		var GBN = $("#GBN").val();
		location.href = "/emp_user_mng/move/" + SORT_TYPE +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE + 
				"/"+GBN+
				"/END";
	}
	
	function OnUserDetail(aEmpID)
	{
		var url = "/emp_user_detail/info/" + aEmpID;
		OpenWin(url,"emp_user_detail",900,600);
	}

	function OnDeleteCorpPool(aEmpID)
	{
		if (confirm("소속 해제하시겠습니까?"))
		{
			var data = {};
			data.EMP_ID = aEmpID
			$.ajax({
	              	url: '/emp_user_mng/delete_corp_pool',
	              	type: 'POST',
	                data: JSON.stringify(data),
					contentType: 'application/json',
	              	success: function(data) 
					{
						Callback_OnDeleteCorpPool(data);
	              	},
					error: function(err) {
						alert(err.responseText);
					}
	         });
		}

	}

	function Callback_OnDeleteCorpPool(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.data[0][0].result=="ok")
		{
			alert("소속 해제되었습니다.");
			OnSearch();
		}
		else
		{
			alert(jsonData[0][0].message);
		}
	}
</script>
<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	