<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    int i = 0;
    JSONObject obj = null;
    JSONObject data0_0 = data.getJSONArray(0).getJSONObject(0);
    int j = 0;
    String NOW_DATE = header.getString("NOW_DATE");
    int WORK_ID = header.getInt("WORK_ID");
%>

<!-- wrap -->
 <div id="pop_wrap">
	<div class="title">
		현장 상세 정보 보기
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div class="pop_contents">
		<div class="state"><%=data0_0.getString("WORK_STATUS_NM")%></div>
		<h3>기본정보</h3>
		<div class="pt10"></div>
		
	<table class="write_B" summary="기본정보">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="10%" />
			<col width="10%" />
		</colgroup>
		<tr>
			<th>직업소개소</th>
			<td colspan="5">
				<%=data0_0.getString("CORP_NAME")%>
			</td>
		</tr>
		<tr>
			<th>날짜</th>
			<td colspan="5">
				[<%=data0_0.getString("DATE_GBN_NM")%>]&nbsp;
				<%=data0_0.getString("WORK_DATE")%>~<%=data0_0.getString("TO_WORK_DATE")%>
			</td>
		</tr>
		<tr>
			<th>현장명</th>
			<td colspan="5">
				<%=data0_0.getString("SCHEDULE_NAME")%>
			</td>
		</tr>
		<tr>
			<th>현장주소</th>
			<td colspan="5">
				<%=data0_0.getString("ADDR1")%>&nbsp;
				<%=data0_0.getString("ADDR2")%>
			</td>
		</tr>
		<tr>
			<th rowspan="2">현장담당자</th>
			<th>성명</th>
			<td><%=data0_0.getString("MAN_NAME")%></td>
			<th>직급</th>
			<td colspan="2"><%=data0_0.getString("MAN_PSTN_NM")%></td>
		</tr>
		<tr>
			<th>연락처</th>
			<td><%=data0_0.getString("MAN_TEL")%></td>
			<th>현장전화</th>
			<td colspan="2"><%=data0_0.getString("WORK_TEL")%></td>
		</tr>
		<tr>
			<th>작업내용</th>
			<td colspan="5">
				<%=data0_0.getString("JOB_CONTENT")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>현장 인력정보</h3>
	<table class="write_C" summary="현장 인력정보">
		<colgroup>
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
			<col width="13%" />
			<col width="16%" />
			<col width="10%" />
			<col width="10%" />
			<col width="12%" />
		</colgroup>
		<tr>
			<th colspan="2">직종</th>
			<th colspan="2">노임</th>
			<th>인원수</th>
			<th>예약인원수</th>
			<th colspan="2">출근인원수</th>
		</tr>
		<%
		JSONArray data1 = data.getJSONArray(1);
		for (i=0;i<data1.length();i++) {
            obj = data1.getJSONObject(i);
		%>
		<tr>
			<td colspan="2">[<%=obj.getString("JOB_GBN_NM")%>]&nbsp;<%=obj.getString("JOB_TYPE_NM")%></td>
			<td colspan="2"><%=obj.getInt("WORK_PAY")%>원/일</td>
			<td><%=obj.getInt("MAN_CNT")%></td>
			<td><%=obj.getInt("BOOK_CNT")%></td>
			<td colspan="2"><%=obj.getInt("SHOW_CNT")%></td>
		</tr>
		<%
		}
		%>
		<tr>
			<th>총인원</th>
			<td><%=data0_0.getInt("TOT_MAN_CNT")%></td>
			<th>총노임</th>
			<td><%=data0_0.getInt("TOT_PAY")%></td>
			<th>상태</th>
			<td colspan="3"><%=data0_0.getString("SCHEDULE_STATUS")%></td>
		</tr>
		<tr>
			<th colspan="4">베테랑서비스 알림</th>
			<td colspan="4">
				<% if (data0_0.getString("EMC_ITEM_YN").equals("Y")) {%>[베테랑서비스 알림사용]
				<% } else { %>[베테랑서비스 알림사용안함]<%}%>
				<input type="hidden" id="H_CORP_ID" name="H_CORP_ID" value="<%=data0_0.getInt("CORP_ID")%>"/>
				<input type="hidden" id="H_WORK_ID" name="H_WORK_ID" value="<%=data0_0.getInt("WORK_ID")%>"/>				
			</td>
		</tr>
		<tr>
			<th>베테랑 <br/>서비스 요청</th>
			<th>작업일</th>
			<td colspan="2">
				<select id="H_WORK_DATE" name="H_WORK_DATE" style="width:100px">
				<%
				JSONArray data3 = data.getJSONArray(3);
				for(j=0;j<data3.length();j++) {
                    obj = data3.getJSONObject(j);
					if(NOW_DATE.equals(obj.getString("WORK_DATE"))) {
					%>
					<option value="<%=obj.getString("WORK_DATE")%>" selected><%=obj.getString("WORK_DATE")%></option>
					<%
					} else { 
					%>
					<option value="<%=obj.getString("WORK_DATE")%>"><%=obj.getString("WORK_DATE")%></option>
					<%
					}
					%>
				<%
				}
				%>
				</select>
			</td>
			<th>직종</th>
			<td colspan="2">
				<select id="H_NEED_JOB" name="H_NEED_JOB" style="width:200px">
				<%
				JSONArray data4 = data.getJSONArray(4);
				for(j=0;j<data4.length();j++) {
                    obj = data4.getJSONObject(j);
					if(j==0) {
					%>
					<option value="<%=obj.getInt("NEED_ID")%>" selected>
					[<%=obj.getString("JOB_GBN_NM")%>]<%=obj.getString("JOB_TYPE_NM")%>
					</option>
					<%
					} else { 
					%>
					<option value="<%=obj.getInt("NEED_ID")%>">
					[<%=obj.getString("JOB_GBN_NM")%>]<%=obj.getString("JOB_TYPE_NM")%>
					</option>
					<%
					}
					%>
				<%
				}
				%>
				</select>
			</td>
			<td>
				<a class="btn02" href="javascript:OnVeteranReq();">요청</a>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>배치 및 아이템</h3>
	<table class="list_B" summary="배치 및 아이템">
		<colgroup>
			<col width="8%" />
			<col width="8%" />
			<col width="10%" />
			<col width="13%" />
			<col width="13%" />
			<col width="16%" />
			<col width="16%" />
			<col width="16%" />
		</colgroup>
		
		<%
        JSONArray data2 = data.getJSONArray(2);
		for (i=0;i<data2.length();i++) {
            obj = data2.getJSONObject(i);
		%>
			<tr>
				<th colspan="3">직종</th>
				<th colspan="2">노임</th>
				<th>인원수</th>
				<th>예약인원수</th>
				<th>출근인원수</th>
			</tr>
			<tr>
				<td colspan="3">
					[<%=obj.getString("JOB_GBN_NM")%>]&nbsp;<%=obj.getString("JOB_TYPE_NM")%>
				</td>
				<td colspan="2">
					<%=obj.getInt("WORK_PAY")%>원/일
				</td>
				<td>
					<%=obj.getInt("MAN_CNT")%>명
				</td>
				<td>
					<%=obj.getInt("BOOK_CNT")%>명
				</td>
				<td>
					<%=obj.getInt("SHOW_CNT")%>명
				</td>
			</tr>
			<tr>
				<th colspan="8">배치상태</th>
			</tr>
			<tr>
				<th colspan="4">소속 인력</th>
				<th colspan="4">
					베테랑 서비스
					<% if (data0_0.getString("EMC_ITEM_YN").equals("Y")) {%>[베테랑 서비스 사용 ★]
					<% } else { %><%}%>
				</th>
			</tr>
			<tr>
				<th>요청</th>
				<th>대기</th>
				<th>수락</th>
				<th>거절</th>
				<th>보류</th>
				<th>지원</th>
				<th>지원 안함</th>
				<th>선발</th>
			</tr>
			<tr>
				<td><%=obj.getInt("BASE_REQ_CNT")%></td>
				<td><%=obj.getInt("BASE_WAIT_CNT")%></td>
				<td><%=obj.getInt("BASE_RES_YCNT")%></td>
				<td><%=obj.getInt("BASE_RES_NCNT")%></td>
				<td><%=obj.getInt("EMC_WAIT_CNT")%></td>
				<td><%=obj.getInt("EMC_OK_YCNT")%></td>
				<td><%=obj.getInt("EMC_REJECT_NCNT")%></td>
				<td><%=obj.getInt("EMC_SELECT_YCNT")%></td>
			</tr>
		<%
		}
		%>
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnClose();">닫기</a>
	</div>

</div>
<script type="text/javascript">
	function OnClose()
	{
		self.close();
	}


	function OnVeteranReq()
	{
		var data = {};
		var aWorkID = $("#H_WORK_ID").val();
		var aNeedID = $("#H_NEED_JOB option:selected").val();
		var aWorkDate = $("#H_WORK_DATE option:selected").val();
		data.WORK_ID = aWorkID;
		data.NEED_ID = aNeedID;
		data.WORK_DATE = aWorkDate;
		$.ajax({
			url: '/work_detail/req_check_veteran_service_info',
            type: 'POST',
            data: JSON.stringify(data),
			contentType: 'application/json',
            success: function(data) 
			{
				Callback_OnVeteranReq(data);
            },
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnVeteranReq(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result == "ok")
		{
			OnVeteranReqOpen()
		}
		else
		{	
			alert(jsonData.message);

		}
	}

	function OnVeteranReqOpen()
	{
		var aWorkID = $("#H_WORK_ID").val();
		var aNeedID = $("#H_NEED_JOB option:selected").val();
		var aWorkDate = $("#H_WORK_DATE option:selected").val();
		var url = "/work_detail_v_req/info/"+aWorkID+"/"+aNeedID+"/"+aWorkDate;
		OpenWin(url,"work_detail_v_req",700,600);
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	