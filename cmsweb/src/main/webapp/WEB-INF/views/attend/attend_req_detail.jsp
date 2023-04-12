<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header"); 
    JSONObject data0_0 = data.getJSONArray(0).getJSONObject(0);
    int i = 0;
    JSONObject obj = null;
%>

<!-- contents -->
<div class="contents">
	<h2>가입 신청 상세</h2>
	<div class="navi"><a href="">가입신청</a> &nbsp; > &nbsp; <span class="blue">가입신청 현황</span></div>
	<div class="pt30"></div>
	<h3 class="floatleft">기본정보</h3>
	<div class="status"><span>상태</span>  
	<%
	String accpStatusNm = "";
	String accpStatus1 = "";
	String accpStatus2 = "";
	String accpStatus3 = "";
	String accpStatus4 = "";
	if (data0_0.getString("ACCP_STATUS").equals("1")) accpStatusNm="접수 중";
	if (data0_0.getString("ACCP_STATUS").equals("2")) accpStatusNm="승인 완료";
	if (data0_0.getString("ACCP_STATUS").equals("3")) accpStatusNm="보류";
	if (data0_0.getString("ACCP_STATUS").equals("4")) accpStatusNm="반려";
	%>
	<%=accpStatusNm%>
	</div>

	<table class="write_A" summary="가입 신청 상세 기본정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>아이디</th>
			<td>
				<%=data0_0.getString("ID")%>
			</td>
			<th>비밀번호</th>
			<td>	
				*****
			</td>
		</tr>
		<tr>
			<th>회사명</th>
			<td>
				<%=data0_0.getString("CORP_NAME")%>
			</td>
			<th>가입일<br/>[쿠폰]</th>
			<td>
				<%=data0_0.getString("ATTEND_DATE")%><br/>
				<%=data0_0.getString("VIP_YN")%>
			</td>
		</tr>
		<tr>
			<th>사업자번호 또는 <br/>유료직업소개<br/>사업신고번호</th>
			<td>
				<%=data0_0.getString("BIZ_NO")%>
			</td>
			<th>구분</th>
			<td>
				<%=data0_0.getString("CORP_GBN_NM")%>
			</td>
		</tr>
		<tr>
			<th>대표자</th>
			<td>
				<%=data0_0.getString("REP_NAME")%>
			</td>
			<th>대표자 연락처</th>
			<td>
				<%=data0_0.getString("REP_TEL")%>
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td colspan="3">
				<%=data0_0.getString("ADDR1")%>&nbsp;
				<%=data0_0.getString("ADDR2")%>
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td colspan="3">
				<%=data0_0.getString("TEL")%>
			</td>
		</tr>
		<tr>
			<th>홈페이지</th>
			<td>
				<%=data0_0.getString("HOME_URL")%>
			</td>
			<th>이메일</th>
			<td>
				<%=data0_0.getString("EMAIL")%>
			</td>
		</tr>
		
	</table>
	<div class="pt30"></div>
	<h3>담당자 정보</h3>
	<table class="write_A" summary="가입 신청 상세 담당자 정보">
		<colgroup>
			<col width="15%" />
			<col width="20%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>담당자</th>
			<td>
				<%=data0_0.getString("MAN_NAME")%>
			</td>
			<th>직급</th>
			<td>
				<%=data0_0.getString("MAN_PSTN_NM")%>
			</td>
			<th>담당자<br/>연락처</th>
			<td>
				<%=data0_0.getString("MAN_TEL")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>직업소개소 직종 정보</h3>
	<table class="write_A" summary="직업소개소 직종 정보">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
		</colgroup>
		<tr>
			<th>주요 직종</th>
			<td>
				<div  class="category">
					<div>
						<h4>조공 분류</h4>
						<table class="none">
							<%
							JSONArray data1 = data.getJSONArray(1);
							for (i = 0; i<data1.length();i++) {
                                obj = data1.getJSONObject(i);
								if (i%2==0) { %><tr><% }
								if (obj.getString("JOB_YN").equals("Y")) {	
								%>
								    <td><input type="checkbox" name="HELP_TYPE" checked value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								} else {
								%>
								    <td><input type="checkbox" name="HELP_TYPE" value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								}
								if (i%2==1) { %></tr><% }
							}
							%>
						</table>
					</div>
					<div style="width:430px;margin-right:0;">
						<h4>기공 분류</h4>
						<table class="none" summary="기공 분류">
							<%
                            JSONArray data2 = data.getJSONArray(2);
							for (i = 0; i<data2.length();i++) {
                                obj = data2.getJSONObject(i);
								if (i%4==0) { %><tr><% }
								if (obj.getString("JOB_YN").equals("Y")) {	
								%>
									<td><input type="checkbox" name="SKILL_TYPE" checked value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								} else {
								%>
									<td><input type="checkbox" name="SKILL_TYPE" value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								}
								if (i%4==3) { %></tr><% }
							}
							%>
						</table>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>기타 직종</th>
			<td>
				<div  class="category">
					<div>
						<h4>파출</h4>
						<table class="none">
							<%
							JSONArray data5 = data.getJSONArray(5);
							for (i = 0; i<data5.length();i++) {
                                obj = data5.getJSONObject(i);
								if (i%2==0) { %><tr><% }
								if (obj.getString("JOB_YN").equals("Y")) {	
								%>
								<td><input type="checkbox" name="HELP_TYPE" checked value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								} else {
								%>
								<td><input type="checkbox" name="HELP_TYPE" value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								}
								if (i%2==1) { %></tr><% }
							}
							%>
						</table>
					</div>
					<div style="width:430px;margin-right:0;">
						<h4>기타</h4>
						<table class="none" summary="기공 분류">
							<%
                            JSONArray data4 = data.getJSONArray(4);
							for (i = 0; i<data4.length();i++) {
                                obj = data4.getJSONObject(i);
								if (i%4==0) { %><tr><% }
								if (obj.getString("JOB_YN").equals("Y")) {	
								%>
									<td><input type="checkbox" name="SKILL_TYPE" checked value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								} else {
								%>
									<td><input type="checkbox" name="SKILL_TYPE" value="<%=obj.getString("JOB_CODE")%>" /><%=obj.getString("NAME")%></td>
								<%
								}
								if (i%4==3) { %></tr><% }
							}
							%>
						</table>
					</div>
				</div>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<table class="write_A" summary="직업소개소 소개">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
		</colgroup>
		<tr>
			<th>소개</th>
			<td>
				<%=data0_0.getString("INTRO_TXT")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>직업소개소 컨택 히스토리</h3>
	<table class="write_A" summary="직업소개소 컨택 히스토리">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
		</colgroup>
		<tr>
			<th>내용등록</th>
			<td>
				<textarea id="CONTENT" name="CONTENT" cols="" rows="10" style="width:620px;height:100px;"></textarea>
				<a href="javascript:OnSaveContact();" class="btn01">등록</a>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<table id="CONTACT_LIST" class="list_A" summary="직업소개소 컨택 히스토리">
		<colgroup>
			<col width="*%" />
			<col width="10%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>내용</th>
			<th>등록자</th>
			<th>등록일</th>
		</tr>
		<tbody>
			<%
			int nViewCount = 1;
			String trDisplay = "";
            JSONArray data3 = data.getJSONArray(3);
			for (i=0;i<data3.length();i++) {
                obj = data3.getJSONObject(i);
                if (nViewCount >= 5) 
                {
                    trDisplay = "none";
                }
                else
                {
                    nViewCount += 1;
                }
			%>
			<tr style="display:<%=trDisplay%>">
				<td><%=obj.getString("CONTENT")%></td>
				<td><%=obj.getString("NAME")%></td>
				<td><%=obj.getString("UPD_DATE")%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<input type="hidden" id="VIEW_COUNT" name="VIEW_COUNT" value="<%=nViewCount%>"/>
	<a href="javascript:OnAddView();" class="btn_more">더보기 + </a>
	<div class="pt30"></div>
	<div style="text-align:right;">
		<input type="hidden" id="CORP_ID" name="CORP_ID" value="<%=data0_0.getInt("CORP_ID")%>"/>
		<a class="btn03" href="javascript:OnDelete();">삭제</a>
		<a class="btn03" href="javascript:OnAccpStatus('2');">승인 완료</a>
		<a class="btn04" href="javascript:OnAccpStatus('3');">보류</a>
		<a class="btn04" href="javascript:OnAccpStatus('4');">반려</a>
		<a class="btn04" href="javascript:OnList();">목록</a>
	</div>
</div>
<script type="text/javascript">

	function OnDelete()
	{
		if (confirm("삭제하겠습니까?"))
		{
			var data = {};
			data.CORP_ID  = $("#CORP_ID").val();

			$.ajax({
				url:"/attend_req_detail/delete_corp",
				type:"POST",
				data: JSON.stringify(data),
				contentType:"application/json",
				success: function(result){
					Callback_OnDelete(result);
				},
				error: function(err) {
					alert(err.responseText);
				}
			});
		}
		
	}
	
	function Callback_OnDelete(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result=="ok")
		{
			alert("삭제하였습니다.");
			location.href="/attend_req";
		}
	}


	function OnAccpStatus(aAccpStatus)
	{
		if (aAccpStatus=="2") 
		{
			if (!confirm("승인완료하시겠습니까?")) return;
		}
		else if (aAccpStatus=="3")
		{
			if (!confirm("보류하시겠습니까?")) return;
		}
		else if (aAccpStatus=="4")
		{
			if (!confirm("반려하시겠습니까?")) return;
		}

		var data = {};
		data.CORP_ID = $("#CORP_ID").val();
		data.ACCP_STATUS = aAccpStatus;
		
		$.ajax({
			url:"/attend_req_detail/update_accp_status",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_UpdateAccpStatus(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_UpdateAccpStatus(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result == "ok")
		{
			$("#ACCP_STATUS").val(jsonData.ACCP_STATUS);
			alert("수정되었습니다");
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnList()
	{
		location.href = "/attend_req";
	}

	function OnSaveContact()
	{
		if ($("#CONTENT").val() == "")
		{
			alert("내용을 입력해주세요.");
			return;
		}

		if (!confirm("등록하시겠습니까?")) return;

		var data = {};
		data.CORP_ID = $("#CORP_ID").val();
		data.CONTENT = $("#CONTENT").val();
		
		$.ajax({
			url:"/attend_req_detail/save_contact",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_SaveContact(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_SaveContact(result)
	{
		

		var jsonData = JSON.parse(result);

		if (jsonData.result == "ok")
		{
			var html = "<tr id='"+jsonData.data[0][0].CONT_ID+"' style='display:none' >";
			html += "<td>"+jsonData.data[0][0].CONTENT+"</td>";
			html += "<td>"+jsonData.data[0][0].NAME+"</td>";
			html += "<td>"+jsonData.data[0][0].UPD_DATE+"</td>";
			html += "</tr>";
			$("#CONTACT_LIST > tbody:last").append(html);
			alert("등록되었습니다");
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnAddView()
	{
		var totalCount = $("#CONTACT_LIST > tbody > tr").length;
		var viewCount = parseInt($("#VIEW_COUNT").val());
		var toViewCount = parseInt(viewCount) + 5;
		if (totalCount <= viewCount)
		{
			alert("추가로 등록된 데이타가 없습니다.등록해주세요.");
			return;
		}
		if (toViewCount > totalCount)
		{
			toViewCount = totalCount;
		}
		$("#VIEW_COUNT").val(toViewCount);
		var i = 0;
		for (i = viewCount-1;i<toViewCount;i++)
		{
			$("#CONTACT_LIST > tbody > tr")[i].style.display="";
		}
	}

</script>

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	