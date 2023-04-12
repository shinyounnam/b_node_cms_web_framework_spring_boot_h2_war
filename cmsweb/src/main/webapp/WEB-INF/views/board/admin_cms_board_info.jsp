<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject data0_0 = data.getJSONArray(0).getJSONObject(0);
    JSONObject data5_0 = data.getJSONArray(5).getJSONObject(0);
    int i = 0;
    JSONObject obj = null;
%>


<%
String CONTENT_STRING = data0_0.getString("CONTENT");

%>
<!-- contents -->
<div class="contents">
	<h2>이벤트/공지사항</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">이벤트/공지사항</span></div>
	<div class="pt30"></div>
	<table class="write_A" summary="">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>구분</th>
			<td style="text-align:left">
				<%=data0_0.getString("GUBUN")%>
			</td>
			<th>대상구분</th>
			<td style="text-align:left">
				<%=data0_0.getString("USER_GBN_NM")%>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td style="text-align:left">
				<%=data0_0.getString("TITLE")%>
			</td>
			<th>작성자</th>
			<td style="text-align:left">
				<%=data0_0.getString("NAME")%>
			</td>
		</tr>
		<%
		if(data0_0.getString("GBN").equals("A2")) {
		%>
		<tr>
			<th>기간</th>
			<td style="text-align:left" colspan="3">
				<%=data0_0.getString("S_DATE")%>~<%=data0_0.getString("E_DATE")%>
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<th>이벤트 이미지 파일</th>
			<td colspan="3" style="text-align:left">
				<%
                JSONArray data2 = data.getJSONArray(2);
				if (data2.length() > 0) {					
					for (i=0;i<data2.length();i++) {
                        obj = data2.getJSONObject(i);
					%>
					    <a href="http://cms.veteranscout.co.kr<%=obj.getString("FILE_URL")%>" target="_blank"><%=obj.getString("ORG_FILE_NAME")%></a><br/>
					<%
					}
				}
				%>
			</td>
		</tr>
		<tr>
			<th>알림내용</th>
			<td colspan="3" style="text-align:left">
				<%=data0_0.getString("ALARM_CONTENT")%>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="write_A" summary="">
					<colgroup>
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
					</colgroup>					
					<tr>
						<th>근로자푸시알림발송성공</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("EMP_PUSH_Y_COUNT")%></span></td>
						<th>근로자푸시알림발송실패</th>
						<td><%=data5_0.getInt("EMP_PUSH_N_COUNT")%></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="pt10"></div>
				<div class="alignright">		
					근무희망지역
					<select id="ALARM_AREA_CODE1" name="ALARM_AREA_CODE1" style="width:200px">
						<option value="999" selelcted>미확인</option>
						<option value="2">경기도</option>
					</select>
					<a class="btn04" href="javascript:OnNoticeAlarmEmpTest();">근로자 푸시 알림 TEST 발송</a>
					<a class="btn04" href="javascript:OnNoticeAlarmEmp();">근로자 푸시 알림 발송</a>
				</div>
			</td>
		</tr>
		<tr>
			<th>알림내용</th>
			<td colspan="3" style="text-align:left">
				<%=data0_0.getString("V_ALARM_CONTENT")%>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="write_A" summary="">
					<colgroup>
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
					</colgroup>					
					<tr>
						<th>직업소개소 전체 수</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("CORP_COUNT")%></span></td>
						<th>직업소개소푸시알림 대상수(모바일아이디 있음)</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("CORP_MOB_COUNT")%></span></td>
					</tr>
					<tr>
						<th>직업소개소푸시알림발송성공</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("CORP_MOB_SEND_Y_COUNT")%></span></td>
						<th>직업소개소푸시알림발송실패</th>
						<td><%=data5_0.getInt("CORP_MOB_SEND_N_COUNT")%></td>
					</tr>
					<tr>
						<th>직업소개소푸시알림 가능수</th>
						<td colspan="3"><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("CORP_MOB_AVL_COUNT")%></span></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4">				
				<div class="alignright">		
					가입 주소 : 전체
					<a class="btn04" href="javascript:OnNoticeAlarmCorpTest();">직업소개소 푸시 알림 TEST 발송</a>
					<a class="btn04" href="javascript:OnNoticeAlarmCorp();">직업소개소 푸시 알림 발송</a>
				</div>
				<div class="pt10"></div>
			</td>
		</tr>
		<tr>
			<th>
				MMS 내용			
			</th>
			<td colspan="3" style="text-align:left">
				<%=data0_0.getString("SMS_CONTENT")%>
			</td>
		</tr>
		<tr>
			<th>
			MMS 이미지 파일
			</th>
			<td colspan="3" style="text-align:left">
				<%
                JSONArray data4 = data.getJSONArray(4);
				if (data4.length() > 0) {					
					for (i=0;i<data4.length();i++) {
                        obj = data4.getJSONObject(i);
					%>
					    <a href="<%=obj.getString("FILE_URL")%>/<%=obj.getString("ORG_FILE_NAME")%>" target="_blank"><%=obj.getString("ORG_FILE_NAME")%></a><br/>
					<%
					}
				}
				%>	
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="write_A" summary="">
					<colgroup>
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
					</colgroup>
					<tr>
						<th>전체근로자수</th>
						<td colspan="3"><%=data5_0.getInt("EMP_ALL_COUNT")%></td>
					</tr>
					<tr>
						<th>총MMS에러근로자수</th>
						<td><%=data5_0.getInt("EMP_ERR_COUNT")%></td>
						<th>MMS전화번호없음</th>
						<td><%=data5_0.getInt("EMP_TEL_EMPTY_COUNT")%></td>
					</tr>
					<tr>
						<th>MMS전화번호중복</th>
						<td><%=data5_0.getInt("EMP_DUP_COUNT")%></td>
						<th>MMS전화번호에러(숫자아님,10~11자리아님)</th>
						<td><%=data5_0.getInt("EMP_TEL_NO_ERR_COUNT")%></td>
					</tr>
					<tr>
						<th>MMS가능근로자수</th>
						<td><%=data5_0.getInt("EMP_COUNT")%></td>
						<th>MMS발송저장</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("SEND_COUNT")%></span></td>
					</tr>
					<tr>
						<th>MMS발송저장에러</th>
						<td><%=data5_0.getInt("SEND_N_COUNT")%></td>
						<th>MMS발송가능근로자수</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("SEND_AVL_COUNT")%></span></td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td colspan="4">
				<div class="pt10"></div>
				<div class="alignright">
					가입 주소
					<select id="MMS_AREA_CODE1" name="MMS_AREA_CODE1" style="width:200px">
						<option value="999" selelcted>미확인</option>
						<option value="2">경기도</option>
					</select>
					<a class="btn04" href="javascript:OnNoticeAlarmEmpSmsTest();">근로자 MMS TEST 발송</a>
					<a class="btn04" href="javascript:OnNoticeAlarmEmpSms();">근로자 MMS 발송</a>					
                </div>
                <div class="pt10"></div>
                <!-- 
				<div class="alignright">
					<a class="btn04" href="javascript:OnSmsExcel('EMP_SMS');">근로자 MMS 발송 리스트 엑셀 저장</a>
                </div>
                -->
				<div class="pt10"></div>
				<div class="alignright">
					<a class="btn04" href="javascript:OnSmsResultExcel('EMP_SMS');">근로자 MMS 발송 결과 엑셀 저장</a>
					<a class="btn04" href="javascript:OnSmsErrorExcel('EMP_SMS');">근로자 MMS 발송 결과리스트 엑셀 저장</a>
				</div>
                <div style="width:100%;text-align:left">
                    <span style="color:red;font-weight:bold;font-size:15px;">
                        * 근로자 MMS발송을 클릭하면, 삼봉빌딩에서 가까운 거리순으로 500명씩 발송되며, 발송 결과가 표시됩니다.
                    </span><br/>
                    <span style="color:red;font-weight:bold;font-size:15px;">
                        * MMS 발송저장은 MMS 발송하는 서버에 발송 저장되어, MMS 발송된 건수입니다.
                    </span><br/>
                    <span style="color:red;font-weight:bold;font-size:15px;">
                        * MMS 파일이 없는 경우 LMS로 발송됩니다. 60byte이하인 경우 SMS로 발송됩니다.
                    </span>
                </div>
            </td>
        </tr>
		<tr>
			<th>
				MMS 내용			
			</th>
			<td colspan="3" style="text-align:left">
				<%=data0_0.getString("V_SMS_CONTENT")%>
			</td>
		</tr>
        <tr>
            <td colspan="4">
                <table class="write_A" summary="">
                    <colgroup>
                        <col width="15%" />
                        <col width="35%" />
                        <col width="15%" />
                        <col width="35%" />
                    </colgroup>
                    <tr>
                        <th>전체직업소개소</th>
                        <td colspan="3"><%=data5_0.getInt("CORP_SMS_ALL_COUNT")%></td>
                    </tr>
                    <tr>
                        <th>총MMS에러 직업소개소</th>
                        <td><%=data5_0.getInt("CORP_SMS_ERR_COUNT")%></td>
                        <th>MMS전화번호없음</th>
                        <td><%=data5_0.getInt("CORP_SMS_TEL_EMPTY_COUNT")%></td>
                    </tr>
                    <tr>
                        <th>MMS전화번호중복</th>
                        <td><%=data5_0.getInt("CORP_SMS_DUP_COUNT")%></td>
                        <th>MMS전화번호에러(숫자아님,10~11자리아님)</th>
                        <td><%=data5_0.getInt("CORP_SMS_TEL_ERR_COUNT")%></td>
                    </tr>
                    <tr>
                        <th>MMS가능직업소개소</th>
                        <td><%=data5_0.getInt("CORP_SMS_ALL_AVL_COUNT")%></td>
                        <th>MMS발송저장</th>
                        <td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("CORP_SMS_SEND_Y_COUNT")%></span></td>
                    </tr>
                    <tr>
                        <th>MMS발송저장에러</th>
                        <td><%=data5_0.getInt("CORP_SMS_SEND_N_COUNT")%></td>
                        <th>MMS발송가능직업소개소</th>
                        <td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("CORP_SMS_AVL_COUNT")%></span></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="4">
				<div class="pt10"></div>
				<div class="alignright">
					가입 주소 : 전체
					<a class="btn04" href="javascript:OnNoticeAlarmCorpSmsTest();">직업소개소 MMS TEST 발송</a>		
					<a class="btn04" href="javascript:OnNoticeAlarmCorpSms();">직업소개소 MMS 발송</a>		
                </div>
                <!--
				<div class="pt10"></div>
				<div class="alignright">
					<a class="btn04" href="javascript:OnSmsExcel('CORP_SMS');">직업소개소 MMS 발송 리스트 엑셀 저장</a>
                </div>
                -->
				<div class="pt10"></div>
				<div class="alignright">
					<a class="btn04" href="javascript:OnSmsResultExcel('CORP_SMS');">직업소개소 MMS 발송 결과 엑셀 저장</a>
					<a class="btn04" href="javascript:OnSmsErrorExcel('CORP_SMS');">직업소개소 MMS 발송 결과리스트 엑셀 저장</a>
				</div>
				<div style="width:100%;text-align:left">
					<span style="color:red;font-weight:bold;font-size:15px;">
						* 직업소개소 MMS발송을 클릭하면, 삼봉빌딩에서 가까운 거리순으로 500명씩 발송되며, 발송 결과가 표시됩니다.
					</span><br/>
					<span style="color:red;font-weight:bold;font-size:15px;">
						* MMS 발송저장은 MMS 발송하는 서버에 발송 저장되어, MMS 발송된 건수입니다.
					</span><br/>
					<span style="color:red;font-weight:bold;font-size:15px;">
						* MMS 파일이 없는 경우 LMS로 발송됩니다. 60byte이하인 경우 SMS로 발송됩니다.
					</span>
				</div>
			</td>
		</tr>
		<tr>
			<th>
				메세지 내용			
			</th>
			<td colspan="3" style="text-align:left">
				메세지 발송 직업소개소 : <%=data0_0.getString("MSG_CORP_NAME")%>
				<br/>
				<%=data0_0.getString("MSG_CONTENT")%>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="write_A" summary="">
					<colgroup>
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
					</colgroup>					
					<tr>
						<th>근로자메시지알림발송성공</th>
						<td><span style="color:red;font-weight:bold;font-size:15px;"><%=data5_0.getInt("EMP_MSG_Y_COUNT")%></span></td>
						<th>근로자메시지알림발송실패</th>
						<td><%=data5_0.getInt("EMP_MSG_N_COUNT")%></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="pt10"></div>
				<div class="alignright">
					<input type="hidden" id="MSG_CORP_ID" name="MSG_CORP_ID" value="<%=data0_0.getInt("MSG_CORP_ID")%>"/>
					<a class="btn04" href="javascript:OnNoticeAlarmEmpMsgTest();">근로자 메세지 TEST 푸시 알림 발송</a>
					<a class="btn04" href="javascript:OnNoticeAlarmEmpMsg();">근로자 메세지 푸시 알림 발송</a>
				</div>
			</td>
		</tr>
		
		
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnUpdate('<%=data0_0.getInt("ID")%>');">수정</a>
		<a class="btn03" href="javascript:OnDelete('<%=data0_0.getInt("ID")%>');">삭제</a>		
		<a class="btn04" href="javascript:OnCancel();">취소</a>
		<input type="hidden" name="ID" id="ID" value="<%=data0_0.getInt("ID")%>">
	</div>	
</div>

<script>
function htmlEntityDec(str){
	if(str == "" || str == null){
		return str;
	}
	else{
		return str.replace(/&amp;/gi, "&").replace(/&#35;/gi, "#").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "'").replace(/&#39;/gi, '\\').replace(/&#37;/gi, '%').replace(/&#40;/gi, '(').replace(/&#41;/gi, ')').replace(/&#43;/gi, '+').replace(/&#47;/gi, '/').replace(/&#46;/gi, '.').replace(/&#59;/g, ";");
	}
}
</script>
<script type="text/javascript">
	
	function OnUpdate(aID)
	{
		document.location.href = "/admin_cms_board_insert/update/info/"+aID;
	}

	function OnNoticeAlarmEmpMsgTest()
	{
		if (!confirm("근로자 App에 이벤트/공지사항을 메세지로 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "EMP_MSG";
		data.TEST_YN = "Y";
		data.MSG_CORP_ID = $("#MSG_CORP_ID").val();
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmpMsgTest(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmpMsgTest(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmEmpMsg()
	{
		if (!confirm("근로자 App에 이벤트/공지사항을 메세지로 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "EMP_MSG";
		data.MSG_CORP_ID = $("#MSG_CORP_ID").val();
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmpMsg(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmpMsg(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmEmpTest()
	{
		if (!confirm("근로자 App에 이벤트/공지사항을 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "EMP";
		data.TEST_YN = "Y";
		data.AREA_CODE1 = $("#ALARM_AREA_CODE1").val();
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmpTest(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmpTest(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmEmp()
	{
		if (!confirm("근로자 App에 이벤트/공지사항을 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "EMP";
		data.AREA_CODE1 = $("#ALARM_AREA_CODE1").val();
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmp(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmp(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmCorpTest()
	{
		if (!confirm("직업소개소 App에 이벤트/공지사항을 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "CORP";
		data.TEST_YN = "Y";
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmCorpTest(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmCorpTest(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmCorp()
	{
		if (!confirm("직업소개소 App에 이벤트/공지사항을 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "CORP";
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmCorp(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmCorp(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}
	

	function OnNoticeAlarmEmpAdvm()
	{
		if (!confirm("근로자 App에 이벤트/공지사항을 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "EMP_ADVM";
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmpAdvm(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmpAdvm(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmCorpAdvm()
	{
		if (!confirm("직업소개소 App에 이벤트/공지사항을 알림 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "CORP_ADVM";
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmCorpAdvm(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmCorpAdvm(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}
	

	function OnNoticeAlarmEmpSms()
	{
		if (!confirm("근로자 핸드폰에 이벤트/공지사항을 MMS 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "EMP_SMS";
		data.AREA_CODE1 = $("#MMS_AREA_CODE1").val();
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmpSms(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmpSms(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmEmpSmsTest()
	{
		if (!confirm("근로자 핸드폰에 이벤트/공지사항을 MMS 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.TEST_YN = "Y";
		data.GBN = "EMP_SMS";
		data.AREA_CODE1 = $("#MMS_AREA_CODE1").val();
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmEmpSmsTest(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmEmpSmsTest(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmCorpSmsTest()
	{
		if (!confirm("직업소개소 핸드폰에 이벤트/공지사항을 MMS 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "CORP_SMS";
		data.TEST_YN = "Y";
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmCorpSmsTest(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmCorpSmsTest(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnNoticeAlarmCorpSms()
	{
		if (!confirm("직업소개소 핸드폰에 이벤트/공지사항을 MMS 발송하시겠습니까?"))
		{
			return;
		}
		var data = {};		
		data.N_ID = $("#ID").val();
		data.GBN = "CORP_SMS";
		$.ajax({
			url:"/admin_cms_board_info/veteran_notice_service",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnNoticeAlarmCorpSms(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnNoticeAlarmCorpSms(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result =="ok")
		{
			alert("발송하였습니다.");
			location.href = "/admin_cms_board_info/info/"+$("#ID").val();
		}
	}

	function OnDelete(aID)
	{
		if (confirm("삭제하시겠습니까?"))
		{
			var data = {};		
			data.ID = $("#ID").val();
			$.ajax({
				url:"/admin_cms_board_info/delete",
				type:"POST",
				data:JSON.stringify(data),
				contentType:"application/json",
				success:function(result) {
					Callback_Delete(result);
				},
				error:function(err) {
					alert(err.responseText);
				}
			});
		}
	}

	function Callback_Delete(data)
	{
		var jsonData = JSON.parse(data);
		if (jsonData.result == "ok")
		{
			alert("삭제하였습니다.");
			document.location.href = "/admin_cms_board";
		}
		else
		{
			alert(jsonData.message);
		}
	}

	function OnCancel()
	{
		document.location.href = "/admin_cms_board";
	}

	function OnSmsExcel(SMS_GBN)
	{
        var url = "http://xls.veteranscout.co.kr/cms_web/stat_sms_list.php";
        url += "?SMS_GBN="+SMS_GBN;		
		url += "&EVENT_ID="+$("#ID").val();
		location.href = url;
	}

	function OnSmsResultExcel(SMS_GBN)
	{
        var url = "http://xls.veteranscout.co.kr/cms_web/stat_sms_send_stat.php";
        url += "?SMS_GBN=" + SMS_GBN;
        url += "&EVENT_ID=" + $("#ID").val();
        url += "&TEST_YN=N";
		location.href = url;
	}

	function OnSmsErrorExcel(SMS_GBN)
	{
        var url = "http://xls.veteranscout.co.kr/cms_web/stat_sms_send_stat_error.php";
        url += "?SMS_GBN="+SMS_GBN;
        url += "&EVENT_ID=" + $("#ID").val();
		url += "&TEST_YN=N";
		location.href = url;
	}

</script>
<!-- contents -->
<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	