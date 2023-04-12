<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");  
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    JSONObject data3_0 = data.getJSONArray(3).getJSONObject(0);
    JSONObject data5_0 = data.getJSONArray(5).getJSONObject(0);
    JSONObject data9_0 = data.getJSONArray(9).getJSONObject(0);
    JSONObject data11_0 = data.getJSONArray(11).getJSONObject(0);
    JSONObject data13_0 = data.getJSONArray(13).getJSONObject(0);
    JSONObject data1_0 = data.getJSONArray(1).getJSONObject(0);
    int i = 0;
    JSONObject obj = null;
    JSONObject obj1 = null;
    String CONT_YY = header.getString("CONT_YY");
    String CONT_MM = header.getString("CONT_MM");
    String CONT_DD = header.getString("CONT_DD");
%>

<!-- contents -->
<div class="contents">
	<h2>직업소개소 회원</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <a href="">직업소개소 회원</a> &nbsp; > &nbsp; <span class="blue">직업소개소 회원 정보 상세보기</span></div>
	<div class="pt30"></div>
	<h3 class="floatleft">기본정보(등급 수정)</h3>
	<table class="write_A" summary="가입 신청 상세 기본정보">
		<colgroup>
			<col width="20%" />
			<col width="30%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>회사명/아이디</th>
			<td >
				<%=obj0_0.getString("CORP_NAME")%>&nbsp;(<%=obj0_0.getString("ID")%>)
			</td>
			<th>등급</th>
			<td >
				<%
				String saleLevel1 = "";
				String saleLevel2 = "";
				String saleLevel3 = "";
				String saleLevel4 = "";
				if (obj0_0.getString("SALE_LEVEL").equals("C")) saleLevel1 = "selected";
				if (obj0_0.getString("SALE_LEVEL").equals("B")) saleLevel2 = "selected";
				if (obj0_0.getString("SALE_LEVEL").equals("A")) saleLevel3 = "selected";
				if (obj0_0.getString("SALE_LEVEL").equals("S")) saleLevel4 = "selected";
				%>
				<select id="SALE_LEVEL" name="SALE_LEVEL" style="width:100px">
					<option value="C" <%=saleLevel1%> >C 등급</option>
					<option value="B" <%=saleLevel2%> >B 등급</option>
					<option value="A" <%=saleLevel3%> >A 등급</option>
					<option value="S" <%=saleLevel4%> >S 등급</option>
				</select>
				<a href="javascript:OnUpdateSaleLevel()" class="btn02 floatright">적용</a>
				<br/>
				C등급 : 최초가입 <br/>
				B등급 : 월 10만원 이하 <br/>
				A등급 : 월 20만원 이하 + 2개월 유지<br/>
				S등급 : 월 20만원 이하 + 3개월 유지<br/>
			</td>
		</tr>
		<tr>
			<th>사업자번호 또는<br/>유료직업소개</br>사업신고번호</th>
			<td >
				<%=obj0_0.getString("BIZ_NO")%>
			</td>
			<th>쿠폰번호</th>
			<td>
				<% 
				if(obj0_0.getString("VIP_NO_YN").equals("O")) {
				%>
				쿠폰사용 : [<%=obj0_0.getString("VIP_NO")%>]
				<%
				} else {
				%>
				쿠폰미사용 
				<%
				}
				%>
			</td>
		</tr>
		<tr>
			<th>대표자</th>
			<td >
				<%=obj0_0.getString("REP_NAME")%>
			</td>
			<th>대표자 연락처</th>
			<td >
				<%=obj0_0.getString("REP_TEL")%>
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>
				<%=obj0_0.getString("ADDR1")%>&nbsp;<%=obj0_0.getString("ADDR2")%>
			</td>
			<th>구분</th>
			<td>
				<%=obj0_0.getString("CORP_GBN_NM")%>
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td colspan="3">
				<%=obj0_0.getString("TEL")%>
			</td>
		</tr>
		<tr>
			<th>홈페이지</th>
			<td >
				<%=obj0_0.getString("HOME_URL")%>
			</td>
			<th>이메일</th>
			<td >
				<%=obj0_0.getString("EMAIL")%>
			</td>
		</tr>
		<tr>
			<th>승인날짜(신청일)</th>
			<td >
				<%=obj0_0.getString("ACCP_DATE")%>(<%=obj0_0.getString("REQ_DATE")%>)
			</td>
			<th>정보수정일</th>
			<td >	
				<%=obj0_0.getString("UPD_DATE")%>
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
			<td >
				<%=obj0_0.getString("MAN_NAME")%>
			</td>
			<th>직급</th>
			<td >
				<%=obj0_0.getString("MAN_PSTN_NM")%>
			</td>
			<th>담당자</br>연락처</th>
			<td >
				<%=obj0_0.getString("MAN_TEL")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>아이템 관리</h3>
	<table class="write_A" summary="가입 신청 상세 담당자 정보">
		<tr>
			<td><img src="http://cms.veteranscout.co.kr/images/icon_item.gif"> 보유 아이템 : 긴급일자리 알리미 
			
			<span class="blue"><%=data13_0.getInt("RES_SUM_CNT")%></span>회 (환불가능 : <span class="blue"><%=data13_0.getInt("CANCEL_AVL_COUNT")%></span>회 )	
			</td>
			<td style="text-align:right;"><a href="javascript:GO_ItemPage();" class="btn01">아이템 내역 상세보기 바로가기</a></td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>직종(인력) 정보</h3>
	<table class="write_A" summary="직업소개소 직종 정보">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th class="center" rowspan="25" style="border-right:1px solid #dbdbdb;">주요 직종<br />[인력 현황]</th>
			<th class="table_blue" colspan="4">조공 : <%=data1_0.getInt("JOB_GUBUN_SU")%> 명</th>
		</tr>
		<%
		JSONArray data2 = data.getJSONArray(2);

		for (i=0;i<data2.length();i=i+2) {
            obj = data2.getJSONObject(i);
            obj1 = data2.getJSONObject(i+1);
		%>
		<tr>
			<th><%=obj.getString("NAME")%></th>
			<td>
				<%=obj.getInt("JOB_CODE_SU")%>
			</td>
			<% if( (i+1) < data2.length() ) { %>
			<th><%=obj1.getString("NAME")%></th>
			<td>
				<%=obj1.getInt("JOB_CODE_SU")%>
			</td>
			<% } %>
		</tr>
		<%
		}
		%>
		<tr>
			<th  class="table_blue" colspan="4">기공 : <%=data3_0.getInt("JOB_GUBUN_SU")%> 명</th>
		</tr>
		<%
        JSONArray data4 = data.getJSONArray(4);
		for (i=0;i < data4.length();i=i+2) 
		{
            obj = data4.getJSONObject(i);
			if((i+1) < data4.length())
			{
            	obj1 = data4.getJSONObject(i+1);
			}
			else
			{
				break;
			}
		%>
		<tr>
			<th><%=obj1.getString("NAME")%></th>
			<td>
				<%=obj1.getInt("JOB_CODE_SU")%>
			</td>
			<% 
			if((i+1) < data4.length()) 
			{ 
			%>
			<th><%=obj1.getString("NAME")%></th>
			<td>
				<%=obj1.getInt("JOB_CODE_SU")%>
			</td>
			<% 
			} 
			%>
		</tr>
		<%
		}
		%>
		<tr>
			<th  class="table_blue" colspan="4">파출 : <%=data11_0.getInt("JOB_GUBUN_SU")%> 명</th>
		</tr>
		<%
        JSONArray data12 = data.getJSONArray(12);
		for (i=0;i<data12.length();i=i+2) {
            obj = data12.getJSONObject(i);            
			if( (i+1) < data12.length() ){
				obj1 = data12.getJSONObject(i+1);
			}
			else{
				break;
			}
		%>
		<tr>
			<th><%=obj.getString("NAME")%></th>
			<td>
				<%=obj.getInt("JOB_CODE_SU")%>
			</td>
			<% if( (i+1) < data12.length() ) { %>
			<th><%=obj1.getString("NAME")%></th>
			<td>
				<%=obj1.getInt("JOB_CODE_SU")%>
			</td>
			<% } %>
		</tr>
		<%
		}
		%>
		<tr>
			<th  class="table_blue" colspan="4">기타 : <%=data9_0.getInt("JOB_GUBUN_SU")%> 명</th>
		</tr>
		<%
        JSONArray data10 = data.getJSONArray(10);

		for (i=0;i<data10.length();i=i+2) {
            obj = data10.getJSONObject(i);
            
			if( (i+1) < data10.length() ){
				obj1 = data10.getJSONObject(i+1);
			}
			else{
				break;
			}
		%>
		<tr>
			<th><%=obj.getString("NAME")%></th>
			<td>
				<%=obj.getInt("JOB_CODE_SU")%>
			</td>
			<% if( (i+1) < data10.length() ) { %>
			<th><%=obj1.getString("NAME")%></th>
			<td>
				<%=obj1.getInt("JOB_CODE_SU")%>
			</td>
			<% } %>
		</tr>
		<%
		}
		%>
		
	</table>
	<div class="pt10"></div>
	<a href="javascript:OnPopUserPoolList();" class="btn02 floatright">소속 인력 보기</a>
	<div class="pt30"></div>
	<table class="write_A" summary="직업소개소 소개">
		<colgroup>
			<col width="15%" />
			<col width="*%" />
		</colgroup>
		<tr>
			<th>소개</th>
			<td>
				<%=obj0_0.getString("INTRO_TXT")%>
			</td>
		</tr>
	</table>
	<div class="pt10"></div>
	<a href="javascript:OnUpdateCorpUser()" class="btn02 floatright">기업 정보 수정</a>
	<div class="pt30"></div>
	<h3 class=" floatleft">현장 현황</h3><a class="btn_more2 floatright" href="/work_mng">현장 관리 바로 가기</a>
	<table class="list_A" summary="현장 현황">
		<colgroup>
			<col width="20%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th rowspan="2" style="border-right:1px solid #dbdbdb;">전체 등록 현장</th>
			<th colspan="2">진행중인 현장</th>
			<th rowspan="2" style="border-left:1px solid #dbdbdb;">예약 성공 률</th>
			<th rowspan="2">출근 성공 률</th>
		</tr>
		<tr>
			<th style="border-right:1px solid #ebebeb;">소속(일반)</th>
			<th>긴급</th>
		</tr>
		<tr>
			<td><%=data5_0.getInt("TOTAL_SU")%></td>
			<td><%=data5_0.getInt("ING_SU")%></td>
			<td><%=data5_0.getInt("ING_EM_SU")%></td>
			<td><%=data5_0.getInt("WORK_BOOK_P")%></td>
			<td><%=data5_0.getInt("WORK_SHOW_P")%></td>
		</tr>
	</table>
	<div class="pt30"></div>
			
	<h3>사내 담당자 정보</h3>
	
	<%
	String MNG_NAME = "";
	String MNG_PSTN_NM = "";
	String MNG_TEL = "";
    JSONArray data6 = data.getJSONArray(6);
	if (data6.length() > 0)
	{
        obj = data6.getJSONObject(0);
		MNG_NAME = obj.getString("NAME");
		MNG_PSTN_NM = obj.getString("PSTN_NM");
		MNG_TEL = obj.getString("TEL");
	}
	%>
	<table class="write_A" summary="사내 담당자 정보">
		<colgroup>
			<col width="10%" />
			<col width="17%" />
			<col width="10%" />
			<col width="17%" />
			<col width="14%" />
			<col width="*%" />
		</colgroup>
		<tr>
			<th>담당자</th>
			<td>
				<input type="text" id="NAME" name="NAME" value="<%=MNG_NAME%>" style="width:100px;"/>
			</td>
			<th>직급</th>
			<td>
				<input type="text" id="PSTN_NM" name="PSTN_NM" value="<%=MNG_PSTN_NM%>" style="width:100px;"/>
			</td>
			<th>담당자<br/>연락처</th>
			<td>	
				<input type="text" id="TEL" name="TEL" value="<%=MNG_TEL%>" style="width:120px;"/>
			</td>
			<td>
				<input type="hidden" id="CORP_ID" name="CORP_ID" value="<%=obj0_0.getInt("CORP_ID")%>"/>
				<a href="javascript:OnUpdateUserMng()" class="btn01">담당자 수정</a>
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
			<th>컨택방법</th>
			<td>
				
				<select id="CONT_METHOD" name="CONT_METHOD" style="width:100px;">
					<option value="1" selected>전화</option>
					<option value="2">면담</option>
					<option value="3">이메일</option>
					<option value="4">기타</option>
				</select>
			</td>
			<th>컨택 일시</th>
			<td>
				
				<input type="text" id="CONT_YY" name="CONT_YY" maxlength="4" style="width:50px;" value="<%=CONT_YY%>" />/
				<input type="text" id="CONT_MM" name="CONT_MM" maxlength="2" style="width:30px;" value="<%=CONT_MM%>" />/
				<input type="text" id="CONT_DD" name="CONT_DD" maxlength="2" style="width:30px;" value="<%=CONT_DD%>" />
			</td>
		</tr>
		<tr>
			<th>내용 등록</th>
			<td colspan="3">
			<textarea id="CONTENT" name="CONTENT" cols="" rows="10" style="width:620px;height:100px;"></textarea>
			<input type="hidden" id="CONT_ID" name="CONT_ID" value=""/>
			<input type="hidden" id="CONT_ACTION" name="CONT_ACTION" value="INSERT"/>
			<a href="javascript:OnSaveContact()" class="btn01">등록</a>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<table id="CONTACT_LIST" class="write_A" summary="직업소개소 컨택 히스토리">
		<colgroup>
			<col width="10%" />
			<col width="16%" />
			<col width="28%" />
			<col width="16%" />
			<col width="28%" />
		</colgroup>
		<tbody>
			<%
			int nSEQ = 0;
			String trDisplay = "";
			int nViewCount = 0;
            JSONArray data7 = data.getJSONArray(7);
			for (i=0;i<data7.length();i++) {
                obj = data7.getJSONObject(i);
                nSEQ = nSEQ + 1;
                if (nSEQ > 5) 
                {
                    trDisplay = "display:none;";
                }
                else
                {
                    trDisplay = "display:;";
                }
			%>
			<tr style="<%=trDisplay%>">
				<th class="center" style="border-right:1px solid #dbdbdb" rowspan="2"><%=nSEQ%></th>
				<th>컨택일시</th>
				<td>
					<%=obj.getString("CONT_YY")%>/<%=obj.getString("CONT_MM")%>/<%=obj.getString("CONT_DD")%>
				</td>
				<th>컨택자</th>
				<td>
					<%=obj.getString("ADMIN_NAME")%>
				</td>
			</tr>
			<tr style="<%=trDisplay%>">
				<th>컨택메모</th>
				<td colspan="2">
					<%=obj.getString("CONTENT")%>
				</td>
				<td>
					<div class="pt10"></div>
					<div class="alignright">	
						<a href="javascript:OnUpdateContact('<%=obj.getInt("CONT_ID")%>');" class="btn02">수정</a>
						<a href="javascript:OnDeleteContact('<%=obj.getInt("CONT_ID")%>');" class="btn02">삭제</a>
					</div>
				</td>
			</tr>
			<%
			}
			if (nSEQ > 10)
			{
				nViewCount = 10;
			}
			else
			{
				nViewCount = nSEQ;
			}
			%>
		</tbody>
	</table>
	<div class="pt20"></div>
	<input type="hidden" id="VIEW_COUNT" name="VIEW_COUNT" value="<%=nViewCount%>"/>
	<input type="hidden" id="CONT_SEQ" name="CONT_SEQ" value="<%=nSEQ%>"/>
	<a href="javascript:OnAddContactView();" class="btn_more">더보기 +</a>	
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnList();">목록</a>
	</div>
</div>

<script type="text/javascript">

	function OnUpdateCorpUser()
	{
		var CORP_ID = $("#CORP_ID").val();
		location.href = "/corp_user_mng/info/" + CORP_ID;
	}


	function OnPopUserPoolList()
	{
		var url = "/user_pool_list/info/"+$("#CORP_ID").val();
		OpenWin(url,"user_pool_list",1000,600);
	}
	

	function OnUpdateUserMng()
	{
		if ($("#NAME").val()=="")
		{
			alert("담당자 이름을 입력해주세요.");
			$("#NAME").focus();
			return;
		}
		if ($("#PSTN_NM").val()=="")
		{
			alert("직급을 입력해주세요.");
			$("#PSTN_NM").focus();
			return;
		}
		if ($("#TEL").val()=="")
		{
			alert("담당자 연락처를 입력해주세요.");
			$("#TEL").focus();
			return;
		}
		
		var data = {};
		data.CORP_ID = $("#CORP_ID").val();
		data.NAME = $("#NAME").val();
		data.PSTN_NM = $("#PSTN_NM").val();
		data.TEL = $("#TEL").val();

		$.ajax({
			url:"/user_detail/update_corp_mng",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_UpdateCorpMng(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
		
	}

	function Callback_UpdateCorpMng(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result =="ok")
		{
			alert("수정하였습니다.");
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnSaveContact()
	{
		if ($("#CONT_METHOD").val()=="")
		{
			alert("컨택방법을 입력해주세요");
			return;
		}
		var CONT_DATE = $("#CONT_YY").val() + "-" +
				$("#CONT_MM").val() + "-" +
				$("#CONT_DD").val();
		var CONTENT = $("#CONTENT").val();
		if (CONTENT == "")
		{
			alert("내용을 입력해주세요");
			$("#CONTENT").focus();
			return;
		}
		
		if ($("#CONT_ACTION").val()=="INSERT")
		{
			var data = {};
			data.CORP_ID = $("#CORP_ID").val();
			data.CONT_METHOD = $("#CONT_METHOD").val();
			data.CONT_DATE = CONT_DATE;
			data.CONTENT = CONTENT;

			$.ajax({
				url:"/user_detail/insert_contact_info",
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
		else
		{
			var data = {};
			data.CONT_ID = $("#CONT_ID").val();
			data.CONT_METHOD = $("#CONT_METHOD").val();
			data.CONT_DATE = CONT_DATE;
			data.CONTENT = CONTENT;

			$.ajax({
				url:"/user_detail/update_contact_info",
				type:"POST",
				data: JSON.stringify(data),
				contentType:"application/json",
				success: function(result){
					Callback_UpdateContact(result);
				},
				error: function(err) {
					alert(err.responseText);
				}
			});
		}
		
	}	
	
	function Callback_SaveContact(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result =="ok")
		{
			alert("저장하였습니다.");
			location.href="/user_detail/info/"+$("#CORP_ID").val();
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function Callback_UpdateContact(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result == "ok")
		{
			alert("수정하였습니다.");
			location.href="/user_detail/info/"+$("#CORP_ID").val();
		}
		else
		{
			alert(jsonData.message);
		}
	}


	function OnAddContactView()
	{
		var totalCount = $("#CONTACT_LIST > tbody > tr").length;
		var viewCount = parseInt($("#VIEW_COUNT").val());
		var toViewCount = parseInt(viewCount) + 10;
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
	
	function OnUpdateContact(aContID)
	{
		var data = {};
		data.CONT_ID = aContID;

		$.ajax({
			url:"/user_detail/contact_info",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_UpdateInfoContact(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_UpdateInfoContact(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result =="ok")
		{
			$("#CONT_ACTION").val("UPDATE");
			$("#CONT_ID").val(jsonData.data[0][0].CONT_ID);
			$("#CONT_METHOD").val(jsonData.data[0][0].CONT_METHOD);
			$("#CONT_YY").val(jsonData.data[0][0].CONT_YY);
			$("#CONT_MM").val(jsonData.data[0][0].CONT_MM);
			$("#CONT_DD").val(jsonData.data[0][0].CONT_DD);
			$("#CONTENT").val(jsonData.data[0][0].CONTENT);
			$("#CONTENT").focus();
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnDeleteContact(aContID)
	{
		if (confirm("삭제하시겠습니까?"))
		{
			var data = {};
			data.CONT_ID = aContID;

			$.ajax({
				url:"/user_detail/delete_contact_info",
				type:"POST",
				data: JSON.stringify(data),
				contentType:"application/json",
				success: function(result){
					Callback_DeleteInfoContact(result);
				},
				error: function(err) {
					alert(err.responseText);
				}
			});
		}
		
	}
	
	function Callback_DeleteInfoContact(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result =="ok")
		{
			alert("삭제하였습니다.");
			location.href="/user_detail/info/"+$("#CORP_ID").val();
		}
		else
		{
			alert(jsonData.message);
		}
	}
	

	function OnUpdateItemHistory(aReqID, aIndex)
	{
		//insert_item_history
		
		if ($("select[name=IN_STAT]")[aIndex].value == "")
		{
			alert("상태를 입력해주세요.");
			return;
		}
		if ($("textarea[name=MEMO]")[aIndex].value == "")
		{
			alert("구매메모를 입력해주세요.");
			return;
		}

		var data = {};
		data.REQ_ID = aReqID;
		data.IN_STAT = $("select[name=IN_STAT]")[aIndex].value;
		data.MEMO = $("textarea[name=MEMO]")[aIndex].value;

		$.ajax({
			url:"/user_detail/insert_item_history",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_UpdateItemHistory(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
	}
	
	function Callback_UpdateItemHistory(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result =="ok")
		{
			alert("저장하였습니다.");
		}
		else
		{
			alert(jsonData.messgae);
		}
	}

	function OnGoItemList()
	{
	}

	function OnList()
	{
		location.href = "/user_mng";
	}

	function OnUpdateSaleLevel()
	{
		var data = {};
		data.CORP_ID = $("#CORP_ID").val();
		data.SALE_LEVEL = $("#SALE_LEVEL").val();
		

		$.ajax({
			url:"/user_detail/update_corp_level",
			type:"POST",
			data: JSON.stringify(data),
			contentType:"application/json",
			success: function(result){
				Callback_UpdateSaleLevel(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});

	}

	function Callback_UpdateSaleLevel(data)
	{
		var jsonData = JSON.parse(data);

		if (jsonData.result == "ok")
		{
			alert("적용되었습니다.");
		}
		else
		{
			alert(jsonData.message);
		}
	}
	
	function GO_ItemPage()
	{
		var CORP_ID = $("#CORP_ID").val();
		location.href = "/item_use_view" +
				"/"+CORP_ID;
	}
	
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	