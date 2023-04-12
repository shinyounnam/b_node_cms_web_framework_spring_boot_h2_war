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
%>


<!-- contents -->
<div class="contents">
	<h2>휴면 신청 관리</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <span class="blue">휴면 신청 관리</span></div>
	<div class="pt30"></div>
	<div class="tab01">
		<ul>
			<li ><a href="/user_restore">근로자</a></li>
			<li class="on"><a href="/vendor_restore">직업소개소/ 구인자</a></li>			
		</ul>
	</div>
	<div class="pt30"></div>
	<div class="floatright">
		
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		String searchType4 = "";
		String searchType5 = "";
		String searchType6 = "";
		String searchType7 = "";
		if (SEARCH_TYPE.equals("ALL")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("ID")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("NAME")) searchType3 = "selected";
		if (SEARCH_TYPE.equals("REP_NAME")) searchType4 = "selected";
		if (SEARCH_TYPE.equals("REP_TEL")) searchType5 = "selected";
		if (SEARCH_TYPE.equals("MAN_NAME")) searchType6 = "selected";
		if (SEARCH_TYPE.equals("MAN_TEL")) searchType7 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:130px;">
			<option value="ALL" <%=searchType1%> >전체</option>
			<option value="ID" <%=searchType2%> >아이디</option>
			<option value="NAME" <%=searchType3%> >회사명</option>
			<option value="REP_NAME" <%=searchType4%> >대표자</option>
			<option value="REP_TEL" <%=searchType5%> >대표자 전화번호</option>
			<option value="MAN_NAME" <%=searchType6%> >대표자</option>
			<option value="MAN_TEL" <%=searchType7%> >대표자 전화번호</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:100px;" value="<%=SEARCH_TXT%>"/>
		<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="8%" />
			<col width="20%" />
			<col width="10%" />
			<col width="10%" />
			<col width="*%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />			
		</colgroup>
		<tr>
			<th>NO</th>
			<th>아이디</th>
			<th>회사명</th>
			<th>대표자</th>
			<th>휴면사유</th>
			<th>휴면상태</th>
			<th>휴면전환일</th>
			<th>휴면복원일</th>
			<th>복원</th>			
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
			<td><%=pageSeq%></td>
			<td>
				<a href="javascript:OnCorpUserDetail('<%=obj.getInt("CORP_ID")%>','<%=obj.getString("RESTORE_YN")%>');">
				<u>
				<%=obj.getString("ID")%>
				</u>
				</a>
			</td>
			<td><%=obj.getString("NAME")%></td>
			<td><%=obj.getString("REP_NAME")%></td>
			<td><%=obj.getString("RES_NAME")%></td>
			<td>
				<%
				if(obj.getString("RESTORE_YN").equals("Y") && obj.getString("RESTORE_GBN").equals("ADMIN")){
					%>
					복원완료<br/>
					(<%=obj.getString("RESTORE_NAME")%>)
					<%
				} 
				else if(obj.getString("RESTORE_YN").equals("Y") && obj.getString("RESTORE_GBN").equals("USER")){
					%>
					복원완료<br/>
					(사용자)
					<%
				} 
				else{
					%>
					휴면 중
					<%
				}
				%>
			</td>
			<td><%=obj.getString("RES_CONFIRM_DATE")%></td>
			<td><%=obj.getString("RESTORE_DATE")%></td>
			<td>
				<%
				if(obj.getString("RESTORE_YN").equals("N")){
					%>
					<u><a href="javascript:OnRestoreUpdate('<%=obj.getInt("RES_ID")%>')">복원</a></u>
					<%
				}
				%>
				

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
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage ;		
		location.href = "/vendor_restore/move" +
				"?search_type=" + SEARCH_TYPE+
				"&search_txt="+encodeURIComponent(SEARCH_TXT)+
				"&page="+PAGE;
	}
	

	function OnSearch()
	{			
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();		
		var PAGE = "1";		
		location.href = "/vendor_restore/move"+
				"?search_type=" + SEARCH_TYPE+
				"&search_txt="+encodeURIComponent(SEARCH_TXT)+
				"&page="+PAGE;
	}

	function OnRestoreUpdate(res_id){
		if(!confirm("복원하시겠습니까?")){
			return;
		}
		var data = {};
		data.RES_ID = res_id;
		$.ajax({
			url:"/vendor_restore/update_restore",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_OnRestoreUpdate(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
		
	}
	function Callback_OnRestoreUpdate(result){
		var jsonData = JSON.parse(result);
		if(jsonData.result=="ok"){
			alert("복원하였습니다.");
			OnSearch();
		}
		else{
			alert(jsonData.message);
		}
	}

	function OnCorpUserDetail(corp_id, restore_yn){
		if(restore_yn=="Y"){
			var url = "/corp_user_detail/info/"+corp_id;
			OpenWin(url,"corp_user_detail",800,600);
		}
		else{
			var url = "/corp_user_detail_restore/info/"+corp_id;
			OpenWin(url,"corp_user_detail_restore",800,600);
		}
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	