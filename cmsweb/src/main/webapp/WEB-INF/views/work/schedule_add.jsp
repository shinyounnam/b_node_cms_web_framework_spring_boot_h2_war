<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");    
    JSONArray data1 = data.getJSONArray(1);
    JSONArray data2 = data.getJSONArray(2);
    JSONArray data3 = data.getJSONArray(3);
    JSONArray data4 = data.getJSONArray(4);
    JSONObject obj = null;
    int i = 0;
%>
<%
    String strHelpType = data2.toString();
    String strSkillType = data3.toString();
    String strGitaType = data4.toString();
%>
		
<div class="contents">
	<h2>써치 현장 등록</h2>
	<div class="navi"><a href="">직업소개소 회원</a> &nbsp; > &nbsp; <span class="blue">써치 현장 등록</span></div>
	<div class="pt30"></div>
	<table class="list_A" summary="">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>직업소개소명</th>
			<td colspan="4" style="text-align:left;">
				<input id="CORP_NAME" name="CORP_NAME" type="text" style="width:30%"/>
				<input type="hidden" id="CORP_ID" value=""/>
				<a href="javascript:OnCorpUser();" class="btn01">검색</a>
			</td>
		</tr>
		<tr>
			<th>날짜</th>
			<td style="text-align:left" colspan="4">
				<input id="WORK_DATE" name="WORK_DATE" type="text" style="width:20%"/>
				~
				<input id="TO_WORK_DATE" name="TO_WORK_DATE" type="text" style="width:20%"/>
			</td>
		</tr>
		<tr>
			<th>현장명</th>
			<td colspan="4" style="text-align:left">
				<input type="text" id="NAME" name="NAME" style="width:80%" placeholder="현장 명칭을 입력해주세요"/>
			</td>
		</tr>
		<tr>
			<th rowspan="3">주소</th>
			<td colspan="4" style="text-align:left">
				<input type="text" id="POST_CODE" name="POST_CODE" style="width:30%" placeholder="우편번호"/>
				<input type="text" id="CHAMGO" name="CHAMGO" style="width: 30%;" placeholder="참고항목"/>
				<a href="#" onclick="SearchAddress();" class="btn01">주소검색</a>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="text-align:left">
				<input type="text" id="ADDR1" name="ADDR1" style="width:80%" placeholder="도로명주소"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="text-align:left">
				 <input type="text" id="ADDR2" name="ADDR2" style="width:80%" placeholder="상세주소"/>
				 <input type="hidden" id="POS_LAT" name="POS_LAT" value=""/>
				 <input type="hidden" id="POS_LNG" name="POS_LNG" value=""/>
				 <br/>
				 <div id="ADDR_MAP" style="width:300px;height:250px;"></div>
			</td>
		</tr>
		<tr>
			<th rowspan="2">현장담당자</th>
			<th>성명</th>
			<td style="text-align:left">
				<input type="text" id="MAN_NAME" name="MAN_NAME" style="width:90%" placeholder="담당자이름"/>
			</td>
			<th>직급</th>
			<td style="text-align:left">
				<input type="text" id="MAN_PSTN" name="MAN_PSTN" style="width:90%" placeholder="담당자 직급"/>
			</td>
		</tr>
		<tr>
			<th>연락처</th>
			<td style="text-align:left">
				<input type="text" id="MAN_TEL" name="MAN_TEL" style="width:90%" placeholder="담당자 휴대폰"/>
			</td>
			<th>현장전화</th>
			<td style="text-align:left">
				 <input type="text" id="WORK_TEL" name="WORK_TEL" style="width:90%" placeholder="담당자 직통전화"/>
			</td>
		</tr>
		<tr>
			<th>*작업내용</th>
			<td style="text-align:left;" colspan="4">
				<textarea id="JOB_CONTENT" style="width:95%;height:250px;" name="JOB_CONTENT" row="20" placeholder="구제적인 작업내용을 작성해주세요." maxlength="30000"></textarea>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h2>현장 인력정보</h2><br/>
	<div style="width:100%;text-align:right;border:0;height:20px;">
		* 노임은 수수료를 제외한 금액을 제시하여 주시기 바랍니다.
	</div>
	<div class="pt30"></div>
	<table id="WorkTable" class="list_A" summary="">
		<colgroup>
			<col width="40%" />
			<col width="30%" />
			<col width="20%" />
			<col width="10%" />
		</colgroup>
		<tr>
			<th>직종</th>
			<th>노임</th>
			<th>인원수</th>
			<th>도구</th>
		</tr>
		<tr>
			<td>
				<select id="JOB_GUBUN1" name="JOB_GUBUN" onchange="OnChangeJobGubun('1');" style="display: inline-block; width: 100px; margin-right: 10px;" class="form-control">
				  <%

					for (i=0;i<data1.length();i++) { 
                        obj = data1.getJSONObject(i);
					    if (i == 0) {
						%>
						    <option value="<%=obj.getString("CODE")%>" selected><%=obj.getString("NAME")%></option>
						<%
						}
						else
						{
						%>
						    <option value="<%=obj.getString("CODE")%>"><%=obj.getString("NAME")%></option>
						<%
						}
					}
					%>
				</select>
				<select id="JOB_CODE1" name="JOB_CODE" style="display: inline-block; width: 130px;" class="form-control">
				  <%
					for (i=0;i<data2.length();i++) { 
                        obj = data2.getJSONObject(i);
					    if (i == 0) {
						%>
						    <option value="<%=obj.getString("CODE")%>" selected><%=obj.getString("NAME")%></option>
						<%
						}
						else
						{
						%>
						    <option value="<%=obj.getString("CODE")%>"><%=obj.getString("NAME")%></option>
						<%
						}
					}
					%>
				</select>
			</td>
			<td>
				<input type="text" id="WORK_PAY1" name="WORK_PAY" value="" style="width: 75%; display: inline-block;" class="form-control">원 / 일
			</td>
			<td>
				<input type="text" id="WORK_CNT1" name="WORK_CNT" value="" style="width: 75%; display: inline-block;" class="form-control">명
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<div style="width:100%;border:0;text-align:right;">
		<a href="javascript:OnWorkAdd()" class="btn01">인력정보 추가</a>
	</div>
	<div class="pt30"></div>
	<div style="width:100%;border:0;text-align:right;">
		<a href="javascript:OnSave();" class="btn03">저장</a>
		<a href="javascript:OnCancel();" class="btn04">취소</a>
		<input type="hidden" id="hidIndex" value="1"/>
	</div>
</div>

  <script>
    $(document).ready(function() {
      $('#WORK_DATE').datepicker({
        dateFormat: 'yy-mm-dd'
	  });
	  $('#TO_WORK_DATE').datepicker({
        dateFormat: 'yy-mm-dd'
	  });
    });
	$(document).ready(function(){
		SetOnlyNumber("WORK_PAY1");
		SetOnlyNumber("WORK_CNT1");
	});
  </script>
<script>
		 // 우편번호 찾기 찾기 화면을 넣을 element
		var element_wrap = document.getElementById('wrap');
		var mapContainer = document.getElementById('ADDR_MAP'), // 지도를 표시할 div 
			mapOption = {
				center: new daum.maps.LatLng(37.50185744481673, 127.04271854624938), // 지도의 중심좌표
				level: 3 // 지도의 확대 레벨
			};  

		// 지도를 생성합니다    
		var map = new daum.maps.Map(mapContainer, mapOption); 

		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new daum.maps.services.Geocoder();
		var markers = [];
		
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
							if (strChamGo.indexOf("|") > -1)
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
						
						for (var i=0;i<markers.length;i++)
						{
							if (markers[i] != null)
							{
								markers[i] = null;
							}
						}

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

		function SetMapPos(aPosLat, aPosLng)
		{
			for (var i=0;i<markers.length;i++)
			{
				if (markers[i] != null)
				{
					markers[i] = null;
				}
			}

			var coords = new daum.maps.LatLng(aPosLat, aPosLng);

			// 결과값으로 받은 위치를 마커로 표시합니다
			var marker = new daum.maps.Marker({
				map: map,
				position: coords
			});

			markers.push(marker);

			map.setCenter(coords);
		}
	</script>

<script type='text/javascript'>

			var jsonHelpType = JSON.parse('<%=strHelpType%>');
			var jsonSkillType = JSON.parse('<%=strSkillType%>');
			var jsonGitaType = JSON.parse('<%=strGitaType%>');
			

			function OnSave()
			{
				if ($("#CORP_ID").val() == "")
				{
					alert("직업소개소를 검색하여, 입력해주세요.");
					$("#CORP_NAME").focus();
					return false;
				}

				if ($("#WORK_DATE").val() == "")
				{
					alert("시작일을 입력해주세요.");
					$("#WORK_DATE").focus();
					return false;
				}
				if ($("#TO_WORK_DATE").val() == "")
				{
					alert("종료일을 입력해주세요.");
					$("#TO_WORK_DATE").focus();
					return false;
				}

				if ($("#WORK_DATE").val() < GetNowDate())
				{
					alert("날짜를 이전일로 등록할 수 없습니다. 다시 입력해주세요.");
					$("#WORK_DATE").focus();
					return false;
				}
				if ($("#NAME").val() == "")
				{
					alert("현장명을 입력해주세요.");
					$("#NAME").focus();
					return false;
				}
				if ($("#POST_CODE").val() == "")
				{
					alert("우편번호를 입력해주세요.");
					$("#POST_CODE").focus();
					return false;
				}
				if ($("#ADDR1").val() == "")
				{
					alert("주소를 입력해주세요.");
					$("#ADDR1").focus();
					return false;
				}
				if ($("#ADDR2").val() == "")
				{
					alert("상세주소를 입력해주세요.");
					$("#ADDR2").focus();
					return false;
				}
				if ($("#CHAMGO").val() == "")
				{
					alert("참고항목을 입력해주세요.");
					$("#CHAMGO").focus();
					return false;
				}
				if ($("#POS_LAT").val() == "")
				{
					alert("위치 LAT를 입력해주세요.");
					$("#POS_LAT").focus();
					return false;
				}
				if ($("#POS_LNG").val() == "")
				{
					alert("위치 LNG를 입력해주세요.");
					$("#POS_LNG").focus();
					return false;
				}
				if ($("#MAN_NAME").val()=="")
				{
					alert("담당자 성명을 입력해주세요.");
					$("#MAN_NAME").focus();
					return false;
				}
				if ($("#MAN_TEL").val()=="")
				{
					alert("담당자 연락처를 입력해주세요.");
					$("#MAN_TEL").focus();
					return false;
				}
				if ($("#WORK_TEL").val()=="")
				{
					alert("현장 전화를 입력해주세요.");
					$("#WORK_TEL").focus();
					return false;
				}
				if ($("#JOB_CONTENT").val()=="")
				{
					alert("작업내용을 입력해주세요.");
					$("#JOB_CONTENT").focus();
					return false;
				}

				var length = $("select[name=JOB_GUBUN]").length;
				for (var i=0;i<length;i++)
				{
					if ($("input[name=WORK_PAY]")[i].value =="")
					{
						alert("노임을 입력해주세요.");
						$("input[name=WORK_PAY]")[i].focus();
						return false;
					}
					if ($("input[name=WORK_CNT]")[i].value =="")
					{
						alert("인원수를 입력해주세요.");
						$("input[name=WORK_CNT]")[i].focus();
						return false;
					}
				}

				var data = {};
				data.CORP_ID = $("#CORP_ID").val();
				data.CORP_NAME = $("#CORP_NAME").val();
				data.WORK_DATE = $("#WORK_DATE").val();
				data.TO_WORK_DATE = $("#TO_WORK_DATE").val();
				data.NAME = $("#NAME").val();
				data.POST_CODE = $("#POST_CODE").val();
				data.ADDR1 = $("#ADDR1").val();
				data.ADDR2 = $("#ADDR2").val();
				data.CHAMGO = $("#CHAMGO").val();
				data.MAN_NAME = $("#MAN_NAME").val();
				data.MAN_PSTN = $("#MAN_PSTN").val();
				data.MAN_TEL = $("#MAN_TEL").val();
				data.WORK_TEL = $("#WORK_TEL").val();
				data.POS_LAT = $("#POS_LAT").val();
				data.POS_LNG = $("#POS_LNG").val();
				data.JOB_CONTENT = $("#JOB_CONTENT").val();
				data.DATA = GetWorkInfoJson();
				$.ajax({
		              		url: '/schedule_add/insert_multi',
		              		type: 'POST',
		                    data: JSON.stringify(data),
							contentType: 'application/json',
		              		success: function(data) 
							{
								Callback_Save(data);
		              		},
							error: function(err) {
							alert(err.responseText);
							}
		          	});
			}
			function Callback_Save(data)
			{
				var jsonData = JSON.parse(data);
				if (jsonData.result=="ok")
				{
					// 파일이 없으면 업로드 하지 않는다.
					location.href="/work_search_mng";
				}
				else
				{
					// 에러발생시 팝업
					alert(jsonData.message);
				}
			}
			
			function GetWorkInfoJson()
			{
				var result = "";
				var length = $("select[name=JOB_GUBUN]").length;
				var data = [];
				
				var i = 0;
				for (i =0; i<length; i++)
				{
					var jsondata = {};
					jsondata.JOB_SEQ = i+1;
					jsondata.JOB_GUBUN = $("select[name=JOB_GUBUN]")[i].value;
					jsondata.JOB_CODE = $("select[name=JOB_CODE]")[i].value;
					jsondata.WORK_PAY = $("input[name=WORK_PAY]")[i].value.replace(",","");
					jsondata.WORK_CNT = $("input[name=WORK_CNT]")[i].value.replace(",","");
					data.push(jsondata);
				}
				return JSON.stringify(data);
			}

			function OnCancel()
			{
				location.href="/work_search_mng";
				return false;
			}

			function OnChangeJobGubun(aIndex)
			{
				var strJobGubun = $("#"+"JOB_GUBUN"+aIndex).val();
				var strJobType = "";
				var i = 0;
				if (strJobGubun=="1")
				{
					strJobType = "HELP";
					for (i=0;i<jsonHelpType.length;i++)
					{	
						if (i==0)
						{
							$("#"+"JOB_CODE"+aIndex+" option").remove();
							$("#"+"JOB_CODE"+aIndex).append('<option value="'+jsonHelpType[i].CODE+'" selected>'+jsonHelpType[i].NAME+'</option>');
						}
						else
						{
							$("#"+"JOB_CODE"+aIndex).append('<option value="'+jsonHelpType[i].CODE+'" >'+jsonHelpType[i].NAME+'</option>');
						}
					}
					
				}
				else if (strJobGubun=="2")
				{
					strJobType = "SKILL";
					for (i=0;i<jsonSkillType.length;i++)
					{	
						if (i==0)
						{
							$("#"+"JOB_CODE"+aIndex+" option").remove();
							$("#"+"JOB_CODE"+aIndex).append('<option value="'+jsonSkillType[i].CODE+'" selected>'+jsonSkillType[i].NAME+'</option>');
						}
						else
						{
							$("#"+"JOB_CODE"+aIndex).append('<option value="'+jsonSkillType[i].CODE+'" >'+jsonSkillType[i].NAME+'</option>');
						}
					}
				}
				else if (strJobGubun=="3")
				{
					strJobType = "GITA_TYPE";
					for (i=0;i<jsonSkillType.length;i++)
					{	
						if (i==0)
						{
							$("#"+"JOB_CODE"+aIndex+" option").remove();
							$("#"+"JOB_CODE"+aIndex).append('<option value="'+jsonGitaType[i].CODE+'" selected>'+jsonGitaType[i].NAME+'</option>');
						}
						else
						{
							$("#"+"JOB_CODE"+aIndex).append('<option value="'+jsonGitaType[i].CODE+'" >'+jsonGitaType[i].NAME+'</option>');
						}
					}
				}
			}
			function OnWorkAdd()
			{
				var strTableID = "WorkTable";
				var table = document.all ? document.all[strTableID] : document.getElementById(strTableID);
				var nIndex = parseInt($("#hidIndex").val());
				nIndex = nIndex + 1;
				$("#hidIndex").val(nIndex);
				var row = table.insertRow(table.rows.length);
				row.bgColor = "#ffffff";
				row.align = "center";
				var cell = row.insertCell(0);
				var strBody = "";
				


				var i = 0;
				strBody += "<select id='JOB_GUBUN"+nIndex+"' name='JOB_GUBUN' onchange='OnChangeJobGubun("+nIndex+");' style='display: inline-block; width: 100px; margin-right: 10px;' class='form-control'>";
				strBody += "<option value='1' selected>조공</option>";
				strBody += "<option value='2'>기공</option>";
				strBody += "<option value='3'>기타</option>";
				strBody += "</select>";
				strBody += "<select id='JOB_CODE"+nIndex+"' name='JOB_CODE' style='display: inline-block; width: 130px;' class='form-control'>";
				for (i=0;i<jsonHelpType.length;i++)
				{
					if (i==0)
					{
						strBody += "<option value='"+jsonHelpType[i].CODE+"' selected>"+jsonHelpType[i].NAME+"</option>";
					}
					else
					{
						strBody += "<option value='"+jsonHelpType[i].CODE+"' >"+jsonHelpType[i].NAME+"</option>";
					}
				}
				strBody += "</select>";
				cell.innerHTML = strBody;
				strBody = "";
				strBody += "<input type='text' id='WORK_PAY"+nIndex+"' name='WORK_PAY' style='width: 75%; display: inline-block;'  class='form-control'/> 원 / 일";
				cell = row.insertCell(1);
				cell.innerHTML = strBody;
				strBody = "";
				strBody += "<input type='text' id='WORK_CNT"+nIndex+"'  name='WORK_CNT' style='width: 75%; display: inline-block;'  class='form-control'/>명";
				cell = row.insertCell(2);
				cell.innerHTML = strBody;
				strBody = "";
				strBody += "<a id='DELETE_ROW"+nIndex+"' class='btn01' onclick='OnDeleteRow("+nIndex+");'>삭제</a>";
				cell = row.insertCell(3);
				cell.innerHTML = strBody;
				SetOnlyNumber("WORK_PAY"+nIndex);
				SetOnlyNumber("WORK_CNT"+nIndex);
			}
			function OnDeleteRow(aIndex)
			{
				if (confirm("삭제하시겠습니까?"))
				{
					var obj = $("#"+"DELETE_ROW"+aIndex).parent().parent();
					obj.remove();
				}	
			}

			function OnLoadSchedule()
			{
				OpenWin("/schedule_load","schedule_load",1000,700);
			}

			function OnCorpUser()
			{
				OpenWin("/slt_corp_user/info","slt_corp_user",1000,700);
			}

			function SetUserId(data)
			{
				var jsonData = JSON.parse(data);
				
				$("#CORP_ID").val(jsonData.CORP_ID);
				$("#CORP_NAME").val(jsonData.CORP_NAME);
				$("#POST_CODE").val(jsonData.POST_CODE);
				$("#CHAMGO").val(jsonData.CHAMGO);
				$("#ADDR1").val(jsonData.ADDR1);
				$("#ADDR2").val(jsonData.ADDR2);
				$("#POS_LAT").val(jsonData.POS_LAT);
				$("#POS_LNG").val(jsonData.POS_LNG);
				$("#MAN_NAME").val(jsonData.MAN_NAME);
				$("#MAN_TEL").val(jsonData.MAN_TEL);
				$("#MAN_PSTN").val(jsonData.MAN_PSTN);

				SetMapPos(jsonData.POS_LAT, jsonData.POS_LNG);
			}

			function SetUserInfo(strValue)
			{
				var CORP_ID,CORP_NAME,POST_CODE,CHAMGO,ADDR1,ADDR2,POS_LAT,POS_LNG,MAN_NAME,MAN_TEL,MAN_PSTN;
				var arrValue = strValue.split("|");
				CORP_ID = arrValue[0];
				CORP_NAME = arrValue[1];
				POST_CODE = arrValue[2];
				CHAMGO = arrValue[3];
				ADDR1 = arrValue[4];
				ADDR2 = arrValue[5];
				POS_LAT = arrValue[6];
				POS_LNG = arrValue[7];
				MAN_NAME = arrValue[8];
				MAN_TEL = arrValue[9];
				MAN_PSTN = arrValue[10];
				
				$("#CORP_ID").val(CORP_ID);
				$("#CORP_NAME").val(CORP_NAME);
				$("#POST_CODE").val(POST_CODE);
				$("#CHAMGO").val(CHAMGO);
				$("#ADDR1").val(ADDR1);
				$("#ADDR2").val(ADDR2);
				$("#POS_LAT").val(POS_LAT);
				$("#POS_LNG").val(POS_LNG);
				$("#MAN_NAME").val(MAN_NAME);
				$("#MAN_TEL").val(MAN_TEL);
				$("#MAN_PSTN").val(MAN_PSTN);

				SetMapPos(POS_LAT, POS_LNG);
			}

		</script>	


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	