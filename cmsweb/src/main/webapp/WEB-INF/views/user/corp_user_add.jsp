<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");  
%>

<div class="contents">
	<h2>써치 회사 등록</h2>
	<div class="navi"><a href="">직업소개소 회원</a> &nbsp; > &nbsp; <span class="blue">써치 회사 등록</span></div>
	<div class="pt30"></div>
	<form id="frmBaseEdit" name="frmBaseEdit" method="post" enctype="multipart/form-data" action="/corp_user_add/submit">
		<table class="list_A" summary="">
			<colgroup>
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
			</colgroup>
			<tr>
				<th>*직업소개소명</th>
				<td colspan="4" style="text-align:left;">
					<input id="CORP_NAME" name="CORP_NAME" type="text" style="width:30%"/>
					<input type="hidden" id="CORP_ID" value="0"/>
				</td>
			</tr>
			<tr>
				<th>유료직업소개사업신고번호</th>
				<td colspan="4" style="text-align:left;">
					<input id="BIZ_NO" name="BIZ_NO" type="text" style="width:30%"/>
				</td>
			</tr>
			<tr>
				<th>직업소개소 로고</th>
				<td colspan="4" style="text-align:left;">
					이미지 첨부
					<input type="file" id="image" name="image"/>
					<span class="help-inline">(최적 사이즈 : 480x135)</span>
					<div class="preview-image" style=>
					<img src="http://images.veteranscout.co.kr/images/cms_corp_img/logo/images/unnamed.png" style="height:30px;" alt="thumbnail"/>
					</div>
				</td>
			</tr>
			<tr>
				<th>대표자</th>
				<td style="text-align:left">
					<input type="text" id="REP_NAME" name="REP_NAME" style="width:80%" placeholder="대표자명"/>
				</td>
				<th>대표자연락처</th>
				<td style="text-align:left" colspan="2">
					<input type="text" id="REP_TEL" name="REP_TEL" style="width:80%" placeholder="대표자연락처"/>
				</td>
			</tr>
			<tr>
				<th rowspan="3">*주소</th>
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
				<th>홈페이지</th>
				<td style="text-align:left">
					<input type="text" id="HOME_URL" name="HOME_URL" style="width:80%" placeholder="홈페이지"/>
				</td>
				<th>이메일</th>
				<td style="text-align:left" colspan="2">
					<input type="text" id="EMAIL" name="EMAIL" style="width:80%" placeholder="이메일"/>
				</td>
			</tr>
			<tr>
				<th>*담당자</th>
				<td style="text-align:left">
					<input type="text" id="MAN_NAME" name="MAN_NAME" style="width:90%" placeholder="담당자이름"/>
				</td>
				<th>*직급</th>
				<td style="text-align:left" colspan="2">
					<input type="text" id="MAN_PSTN" name="MAN_PSTN" style="width:90%" placeholder="담당자 직급"/>
				</td>
			</tr>
			<tr>
				<th>*담당자 연락처</th>
				<td style="text-align:left" colspan="4">
					<input type="text" id="MAN_TEL" name="MAN_TEL" style="width:90%" placeholder="담당자 휴대폰"/>
				</td>
			</tr>
			<tr>
				<th>*소개</th>
				<td style="text-align:left" colspan="4">
					<textarea id="INTRO_TXT" style="width:95%;height:250px;" name="INTRO_TXT" row="10" placeholder="소개를 입력해주세요." ></textarea>
				</td>
			</tr>
		</table>
		<div class="pt30"></div>
		<div style="width:100%;border:0;text-align:right;">
			<a href="javascript:OnSave();" class="btn03">저장</a>
			<a href="javascript:OnCancel();" class="btn04">취소</a>
			<input type="hidden" id="hidIndex" value="1"/>
		</div>
	</form>
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
				geocoder.addressSearch(aADDR1, function(status, result) {

				// 정상적으로 검색이 완료됐으면 
				if (status.length > 0) {
					
						//alert(JSON.stringify(result));

						var coords = new daum.maps.LatLng(status[0].y, status[0].x);
						
						$("#POS_LAT").val(status[0].y);
						$("#POS_LNG").val(status[0].x);
						// $("#ADDR1").val(result.addr[0].title);
						var strChamGo = status[0].address.address_name;
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
						// $("#POST_CODE").val(result.addr[0].zipcode);
						// 결과값으로 받은 위치를 마커로 표시합니다
						var marker = new daum.maps.Marker({
							map: map,
							position: coords
						});

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


		<script type='text/javascript'>

			function OnSave()
			{
				if ($("#CORP_NAME").val() == "")
				{
					alert("직업소개소명을 입력해주세요.");
					$("#CORP_NAME").focus();
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

				if ($("#MAN_PSTN").val()=="")
				{
					alert("담당자 직급을 입력해주세요.");
					$("#MAN_PSTN").focus();
					return false;
				}

				if ($("#MAN_TEL").val()=="")
				{
					alert("담당자 연락처를 입력해주세요.");
					$("#MAN_TEL").focus();
					return false;
				}

				if ($("#INTRO_TXT").val()=="")
				{
					alert("직업소개소 소개를 입력해주세요.");
					$("#INTRO_TXT").focus();
					return false;
				}

				var data = {};
				data.CORP_ID = $("#CORP_ID").val();
				data.CORP_NAME = $("#CORP_NAME").val();
				data.BIZ_NO = $("#BIZ_NO").val();
				data.REP_NAME = $("#REP_NAME").val();
				data.REP_TEL = $("#REP_TEL").val();

				data.POST_CODE = $("#POST_CODE").val();
				data.ADDR1 = $("#ADDR1").val();
				data.ADDR2 = $("#ADDR2").val();
				data.POS_LAT = $("#POS_LAT").val();
				data.POS_LNG = $("#POS_LNG").val();
				data.CHAMGO = $("#CHAMGO").val();
				
				data.HOME_URL = $("#HOME_URL").val();
				data.EMAIL = $("#EMAIL").val();
				data.MAN_NAME = $("#MAN_NAME").val();
				data.MAN_PSTN = $("#MAN_PSTN").val();
				data.MAN_TEL = $("#MAN_TEL").val();
				data.INTRO_TXT = $("#INTRO_TXT").val();
				
				if ($("#image").val()!= "")
				{
					document.frmBaseEdit.submit();
				}
				else
				{
					$.ajax({
		              		url: '/corp_user_add/insert_multi',
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
			}
			function Callback_Save(data)
			{
				var jsonData = JSON.parse(data);
				if (jsonData.result=="ok")
				{
					alert("저장하였습니다");
					// 파일이 없으면 업로드 하지 않는다.
					location.href="/user_search_mng";
				}
				else
				{
					// 에러발생시 팝업
					alert(jsonData.message);
				}
			}
			
			function OnCancel()
			{
				location.href="/user_search_mng";
				return false;
			}

			function OnCorpUser()
			{
				OpenWin("/slt_corp_user/info","slt_corp_user",1000,700);
			}

			function SetUserId(aUserID, aUserName)
			{
				$("#CORP_ID").val(aUserID);
				$("#CORP_NAME").val(aUserName);
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
			}
		</script>	

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	