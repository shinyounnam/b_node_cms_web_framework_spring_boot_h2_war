<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");  
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);

    String CORP_ID = header.getString("CORP_ID");
    int i = 0;
    JSONObject obj = null;
    String JOB_ID = "";
%>

<!-- contents -->
<div class="contents">
	<h2>직업소개소 회원</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <a href="">직업소개소 회원</a> &nbsp; > &nbsp; <span class="blue">기본정보 수정하기</span></div>
	<div class="pt30"></div>
	<h3 class="floatleft">기본정보 수정하기</h3>
	<table class="write_A" summary="직업소개소 회원 기본정보">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>*아이디</th>
			<td>
				<input type="text" id="ID" name="ID" maxlength="20" style="width:150px" value="<%=obj0_0.getString("ID")%>"/>
			</td>
			<th>*비밀번호 변경</th>
			<td>
				<a class="btn03" href="javascript:OnCorpChgPwd();">비밀번호 변경</a>
			</td>
		</tr>
		<tr>
			<th>*회사명</th>
			<td>
			<input type="text" id="CORP_NAME" name="CORP_NAME" maxlength="20" style="width:150px" value="<%=obj0_0.getString("CORP_NAME")%>" />
			</td>
			<th>*구분</th>
			<td>
				<%
				String corp_gbn_check_1 = "";
				String corp_gbn_check_2 = "";
				if(obj0_0.getString("CORP_GBN").equals("AGENCY"))
                {				
					corp_gbn_check_1 = "checked";
				} 
				else if(obj0_0.getString("CORP_GBN").equals("DIRECT"))
                {				
					corp_gbn_check_2 = "checked";
				}
				else 
				{ 
					corp_gbn_check_1 = "checked";
				}
				%>
				<input type="radio" id="CORP_GBN" name="CORP_GBN" value="AGENCY" <%=corp_gbn_check_1%>/>직업소개소
				<input type="radio" id="CORP_GBN" name="CORP_GBN" value="DIRECT" <%=corp_gbn_check_2%>/>구인자(직접채용)
				
			</td>
		</tr>
		<tr>
			<th>사업자번호 또는<br/>유료직업소개</br>사업신고번호</th>
			<td colspan="3">
				<input type="text" id="BIZ_NO" name="BIZ_NO" maxlength="30" style="width:250px" value="<%=obj0_0.getString("BIZ_NO")%>"/>
			</td>			
		</tr>
		<tr>
			<th>*대표자</th>
			<td>
				<input type="text" id="REP_NAME" name="REP_NAME" maxlength="20" style="width:150px" value="<%=obj0_0.getString("REP_NAME")%>"/>
			</td>
			<th>*대표자 연락처</th>
			<td>
				<input type="text" id="REP_TEL" name="REP_TEL" maxlength="20" style="width:100px" value="<%=obj0_0.getString("REP_TEL")%>"/>
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td colspan="3">
				<input type="text" id="POST_CODE" name="POST_CODE" maxlength="20" style="width:100px" value="<%=obj0_0.getString("POST_CODE")%>"/>
				<input id="CHAMGO" name="CHAMGO" type="text" value="<%=obj0_0.getString("CHAMGO")%>">
				<a class="btn03" href="javascript:SearchAddress();">주소검색</a>
				<br/>
				<input type="text" id="ADDR1" name="ADDR1" maxlength="100" style="width:350px" value="<%=obj0_0.getString("ADDR1")%>"/><br/>
				<input type="text" id="ADDR2" name="ADDR2" maxlength="50" style="width:350px" value="<%=obj0_0.getString("ADDR2")%>" /><br/>
				<input type="hidden" id="POS_LAT" name="POS_LAT" value="<%=obj0_0.getDouble("POS_LAT")%>" />
				<input type="hidden" id="POS_LNG" name="POS_LNG" value="<%=obj0_0.getDouble("POS_LNG")%>" />
				<div id="ADDR_MAP" style="width:600px;height:300px;"></div>
			</td>
		</tr>
		<tr>
			<th>*회사연락처</th>
			<td colspan="3">
				<input type="text" id="TEL" name="TEL" maxlength="20" style="width:400px" value="<%=obj0_0.getString("TEL")%>"/>
			</td>
		</tr>
		<tr>
			<th>쿠폰번호</th>
			<td colspan="3">
				<input type="text" id="VIP_NO" name="VIP_NO" maxlength="20" style="width:400px" value="<%=obj0_0.getString("VIP_NO")%>"/>
			</td>
		</tr>
		<tr>
			<th>홈페이지</th>
			<td>
				<input type="text" id="HOME_URL" name="HOME_URL" maxlength="100" style="width:250px" value="<%=obj0_0.getString("HOME_URL")%>"/>
			</td>
			<th>이메일</th>
			<td>
				<input type="text" id="EMAIL" name="EMAIL" maxlength="20" style="width:200px" value="<%=obj0_0.getString("EMAIL")%>" />
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>담당자 정보</h3>
	<table class="write_A" summary="담당자 정보">
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
			<td colspan="3">
				<input type="text" id="MAN_NAME" name="MAN_NAME" maxlength="20" style="width:200px" value="<%=obj0_0.getString("MAN_NAME")%>"/>
			</td>			
			<th>담당자연락처</th>
			<td>
				<input type="text" id="MAN_TEL" name="MAN_TEL" maxlength="20" style="width:200px" value="<%=obj0_0.getString("MAN_TEL")%>" />
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
							String helpType1 = "";
                            JSONArray data2 = data.getJSONArray(2);
                            
							for(i=0;i<data2.length();i++) { 
                                obj = data2.getJSONObject(i);
                                if(obj.get("JOB_ID")==JSONObject.NULL){
                                    JOB_ID = "";
                                }
                                else{
                                    JOB_ID = obj.get("JOB_ID").toString();
                                }

								if (i%2==0) { %><tr><%}
								helpType1 = "";
								if (obj.getString("JOB_YN").equals("Y")) helpType1="checked";
							%>
								<td>
									<input type="hidden" name="HELP_JOB_ID" value="<%=JOB_ID%>" />
									<input type="checkbox" name="HELP_TYPE" value='<%=obj.getString("JOB_CODE")%>' <%=helpType1%> /><%=obj.getString("NAME")%>
								</td>
							<% 
								if (i%2==1) { %></tr><%}
							}
							%>
						</table>
					</div>
					<div style="width:430px;margin-right:0;">
						<h4>기공 분류</h4>
						<table class="none" summary="기공 분류">
							<% 
							String skillType1 = "";
                            JSONArray data3 = data.getJSONArray(3);
							for(i=0;i<data3.length();i++) { 
                                obj = data3.getJSONObject(i);
                                if(obj.get("JOB_ID")==JSONObject.NULL){
                                    JOB_ID = "";
                                }
                                else{
                                    JOB_ID = obj.get("JOB_ID").toString();
                                }

								skillType1 = "";
								if (obj.getString("JOB_YN").equals("Y")) skillType1="checked";
								if (i%3==0) { %><tr><% }
							%>
								<td>
									<input type="hidden" name="SKILL_JOB_ID" value="<%=JOB_ID%>" />
									<input type="checkbox" name="SKILL_TYPE" value='<%=obj.getString("JOB_CODE")%>' <%=skillType1%> /><%=obj.getString("NAME")%>
								</td>
							<% 
								if (i%3==2) { %></tr><% }
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
						<table class="none" summary="파출">
							<% 
							String dispType1 = "";
                            JSONArray data5 = data.getJSONArray(5);
							for(i=0;i<data5.length();i++) { 
                                obj = data5.getJSONObject(i);
                                if(obj.get("JOB_ID")==JSONObject.NULL){
                                    JOB_ID = "";
                                }
                                else{
                                    JOB_ID = obj.get("JOB_ID").toString();
                                }

								dispType1 = "";
								if (obj.getString("JOB_YN").equals("Y")) dispType1="checked";
								if (i%2==0) { %><tr><% }
							%>
								<td>
									<input type="hidden" name="DISP_JOB_ID" value="<%=JOB_ID%>" />
									<input type="checkbox" name="DISP_TYPE" value='<%=obj.getString("JOB_CODE")%>' <%=dispType1%> /><%=obj.getString("NAME")%>
								</td>
							<% 
								if (i%2==1) { %></tr><% }
							}
							%>
						</table>
						
					</div>
					<div style="width:430px;margin-right:0;">
						<h4>기타</h4>
						<table class="none">
							<% 
							String gitaType1 = "";
                            JSONArray data4 = data.getJSONArray(4);
							for(i=0;i<data4.length();i++) { 
                                obj = data4.getJSONObject(i);
                                if(obj.get("JOB_ID")==JSONObject.NULL){
                                    JOB_ID = "";
                                }
                                else{
                                    JOB_ID = obj.get("JOB_ID").toString();
                                }
								if (i%3==0) { %><tr><%}
								gitaType1 = "";
								if (obj.getString("JOB_YN").equals("Y")) gitaType1="checked";
							%>
								<td>
									<input type="hidden" name="GITA_JOB_ID" value="<%=JOB_ID%>" />
									<input type="checkbox" name="GITA_TYPE" value='<%=obj.getString("JOB_CODE")%>' <%=gitaType1%> /><%=obj.getString("NAME")%>
								</td>
							<% 
								if (i%3==2) { %></tr><%}
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
				<textarea id="INTRO_TXT" name="INTRO_TXT" style="width:700px;" id="" cols="30" rows="10"><%=obj0_0.getString("INTRO_TXT")%></textarea>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnSave();">저장</a>
		<a class="btn04" href="javascript:OnCancel();">취소</a>
		<input type="hidden" id="CORP_ID" name="CORP_ID" value="<%=CORP_ID%>"/>
		<input type="hidden" id="DATA" name="DATA" value=""/>
	</div>
	<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script>

		 // 우편번호 찾기 찾기 화면을 넣을 element
		var markers = [];
        var pos_lat = "<%=obj0_0.getDouble("POS_LAT")%>";
		var pos_lng = "<%=obj0_0.getDouble("POS_LNG")%>";
		var element_wrap = document.getElementById('wrap');
		var mapContainer = document.getElementById('ADDR_MAP'), // 지도를 표시할 div 
			mapOption = {
				center: new daum.maps.LatLng(pos_lat, pos_lng), // 지도의 중심좌표
				level: 3 // 지도의 확대 레벨
			};  
		

		// 지도를 생성합니다    
		var map = new daum.maps.Map(mapContainer, mapOption); 
		var map_latlng = new daum.maps.LatLng(pos_lat, pos_lng);
		var marker_1 = new daum.maps.Marker({
			map: map,
			position: map_latlng
		});
		markers.push(marker_1);

		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new daum.maps.services.Geocoder();
		
		function SearchAddress() {
			new daum.Postcode({
				oncomplete: function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
					var extraRoadAddr = ''; // 도로명 조합형 주소 변수

					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
						extraRoadAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if(data.buildingName !== '' && data.apartment === 'Y'){
					   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
					}
					// 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if(extraRoadAddr !== ''){
						extraRoadAddr = ' (' + extraRoadAddr + ')';
					}
					// 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
					if(fullRoadAddr !== ''){
						fullRoadAddr += extraRoadAddr;
					}

					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('POST_CODE').value = data.zonecode; //5자리 새우편번호 사용
					document.getElementById('ADDR1').value = fullRoadAddr;
					codeAddress(fullRoadAddr);
					//document.getElementById('sample4_jibunAddress').value = data.jibunAddress;
					/*
					// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
					if(data.autoRoadAddress) {
						//예상되는 도로명 주소에 조합형 주소를 추가한다.
						var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
						document.getElementById('guide').innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';

					} else if(data.autoJibunAddress) {
						var expJibunAddr = data.autoJibunAddress;
						document.getElementById('guide').innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';

					} else {
						document.getElementById('guide').innerHTML = '';
					}
					*/
					//alert(JSON.stringify(data));
				}
			}).open();
		}

		function hideMarkers() {
			 for (var i = 0; i < markers.length; i++) {
				markers[i].setMap(null);
			}        
		}
		function codeAddress(aADDR1) {
				// 주소로 좌표를 검색합니다
				geocoder.addr2coord(aADDR1, function(status, result) {

					// 정상적으로 검색이 완료됐으면 
					 if (status === daum.maps.services.Status.OK) {
						
						//alert(JSON.stringify(result));

						var coords = new daum.maps.LatLng(result.addr[0].lat, result.addr[0].lng);
						
						$("#POS_LAT").val(result.addr[0].lat);
						$("#POS_LNG").val(result.addr[0].lng);
						$("#ADDR1").val(result.addr[0].title);
						var strChamGo = result.addr[0].newAddress;
						if (strChamGo != null)
						{
							if (strChamGo.indexOf("|")>-1)
							{
								strChamGo = strChamGo.split("|")[0];
							}
						}
						else
						{
							strChamGo = "";
						}
						$("#CHAMGO").val(strChamGo);
						$("#POST_CODE").val(result.addr[0].zipcode);
						hideMarkers();
						// 결과값으로 받은 위치를 마커로 표시합니다
						var marker = new daum.maps.Marker({
							map: map,
							position: coords
						});
						
						markers.push(marker);

						// 인포윈도우로 장소에 대한 설명을 표시합니다
						/*
						var infowindow = new daum.maps.InfoWindow({
							content: '<div style="width:150px;text-align:center;padding:6px 0;"></div>'
						});
						infowindow.open(map, marker);
						*/
						// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
						map.setCenter(coords);
					} 
					else
					{
						alert("검색된 결과가 없습니다.");
						//$("#RESULT").val("error");
					}
				});    
		}
	</script>
</div>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.min.js"></script>
<script type="text/javascript">
	
	function checkTel(str)
	{
		var regTel = /^(01[016789]{1}|070|02|0[3-9]{1}[0-9]{1})-[0-9]{3,4}-[0-9]{4}$/;
		return regTel.test(str);
	}

	function OnSave()
	{
		if ($("#REP_NAME").val() == "")
		{
			alert("대표자를 입력해주세요.");
			$("#REP_NAME").focus();
			return;
		}



		if ($("#REP_TEL").val() == "")
		{
			alert("대표자 연락처를 입력해주세요.");
			$("#REP_TEL").focus();
			return;
		}

		if (checkTel($("#REP_TEL").val()) == false)
		{
			alert("전화번호를 올바르게 입력해주세요. 예) 070-1232-2312 02-123-3231");
			$("#REP_TEL").focus();
			return false;
		}

		
		if ($("#TEL").val() == "")
		{
			alert("전화번호를 입력해주세요.");
			$("#TEL").focus();
			return;
		}

		if (checkTel($("#TEL").val()) == false)
		{
			alert("전화번호를 올바르게 입력해주세요. 예) 070-1232-2312 02-123-3231");
			$("#TEL").focus();
			return;
		}

		var data = {};
		data.CORP_ID= $("#CORP_ID").val();
		data.CORP_NAME = $("#CORP_NAME").val();
		data.BIZ_NO = $("#BIZ_NO").val();
		data.REP_NAME = $("#REP_NAME").val();
		data.REP_TEL = $("#REP_TEL").val();
		data.POST_CODE = $("#POST_CODE").val();
		data.ADDR1 = $("#ADDR1").val();
		data.ADDR2 = $("#ADDR2").val();
		data.CHAMGO = $("#CHAMGO").val();
		data.POS_LAT = $("#POS_LAT").val();
		data.POS_LNG = $("#POS_LNG").val();
		data.TEL = $("#TEL").val();
		data.HOME_URL = $("#HOME_URL").val();
		data.EMAIL = $("#EMAIL").val();
		data.MAN_NAME = $("#MAN_NAME").val();
		data.MAN_TEL = $("#MAN_TEL").val();		
		data.INTRO_TXT = $("#INTRO_TXT").val();
		data.VIP_NO = $("#VIP_NO").val();
		data.CORP_GBN = $("input[name=CORP_GBN]:checked").val();
		data.DATA = GetJobTypeString();

		$.ajax({
			url: "/corp_user_mng/update_multi",
			type: "POST",
			data: JSON.stringify(data),
			contentType: "application/json",
			success: function(result) {
				Callback_Save(result);
			},
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_Save(result)
	{
		var jsonData = JSON.parse(result);
		if (jsonData.result == "ok")
		{
			alert("수정하였습니다.");
			location.href = "/user_detail/info/" + $("#CORP_ID").val();
		}
		else
		{
			alert(jsonData.message);
		}
	}
	
	function GetJobTypeString()
	{
		
		var objHELP_TYPE = $("input[name=HELP_TYPE]");
		var objHELP_JOB_ID = $("input[name=HELP_JOB_ID]");
		var length = objHELP_TYPE.length;
		var i = 0;
		var data = [];
		
		for (i =0;i<length ;i++)
		{
			if (objHELP_TYPE[i].checked==true)
			{
				var jsonData = {};
				jsonData.JOB_ID = objHELP_JOB_ID[i].value;
				jsonData.JOB_GUBUN = "1";
				jsonData.JOB_CODE = objHELP_TYPE[i].value;
				data.push(jsonData);
			}
			
		}

		var objSKILL_TYPE = $("input[name=SKILL_TYPE]");
		var objSKILL_JOB_ID = $("input[name=SKILL_JOB_ID]");
		length = objSKILL_TYPE.length;
		for (i=0;i<length;i++)
		{
			if (objSKILL_TYPE[i].checked)
			{
				var jsonData ={};
				jsonData.JOB_ID= objSKILL_JOB_ID[i].value;
				jsonData.JOB_GUBUN="2";
				jsonData.JOB_CODE=objSKILL_TYPE[i].value;
				data.push(jsonData);
			}
			
		}

		var objGITA_TYPE = $("input[name=GITA_TYPE]");
		var objGITA_JOB_ID = $("input[name=GITA_JOB_ID]");
		length = objGITA_TYPE.length;
		for (i=0;i<length;i++)
		{
			if (objGITA_TYPE[i].checked)
			{
				var jsonData ={};
				jsonData.JOB_ID= objGITA_JOB_ID[i].value;
				jsonData.JOB_GUBUN="3";
				jsonData.JOB_CODE=objGITA_TYPE[i].value;
				data.push(jsonData);
			}
			
		}

		var objDISP_TYPE = $("input[name=DISP_TYPE]");
		var objDISP_JOB_ID = $("input[name=DISP_JOB_ID]");
		length = objDISP_TYPE.length;
		for (i=0;i<length;i++)
		{
			if (objDISP_TYPE[i].checked)
			{
				var jsonData ={};
				jsonData.JOB_ID= objDISP_JOB_ID[i].value;
				jsonData.JOB_GUBUN="4";
				jsonData.JOB_CODE=objDISP_TYPE[i].value;
				data.push(jsonData);
			}
			
		}

		return JSON.stringify(data);
	}

	function OnCancel()
	{
		location.href = "/user_detail/info/" + $("#CORP_ID").val();
	}

	function OnCorpChgPwd()
	{
		var url = "/corp_pwd_chg/info/"+$("#CORP_ID").val();
		OpenWin(url,"corp_pwd_chg",800,375);
	}

</script>

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>