<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
%>

<!-- contents -->
<div class="contents">
	<div class="navi"><a href="">가입신청</a> &nbsp; > &nbsp; <span class="blue">쿠폰 생성하기</span></div>
	<div class="pt30"></div>
	<table class="list_A" summary="쿠폰생성">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>쿠폰명</th>
			<td colspan="3" style="text-align:left;">
				<input type="text" id="NAME" name="NAME" style="width:250px" value=""/>
			</td>
		</tr>
		<tr>
			<th>상태</th>
			<td colspan="3" style="text-align:left;">
				<input type="radio" name="VIP_STATUS" value="1" checked/>진행&nbsp;&nbsp;
				<input type="radio" name="VIP_STATUS" value="2"/>중지
			</td>
		</tr>
		<tr>
			<th>기간</th>
			<td colspan="3" style="text-align:left;">
				<input type="text" id="S_DATE" name="S_DATE" style="width:150px" value="<%=S_DATE%>"/>
				~
				<input type="text" id="E_DATE" name="E_DATE" style="width:150px" value="<%=E_DATE%>"/>
			</td>
		</tr>
		<tr>
			<th>용도</th>
			<td style="text-align:left;">
				<select id="VIP_USE" name="VIP_USE" style="width:150px">
					<option value="1">이벤트</option>
					<option value="2">지원</option>
					<option value="3">직접영업</option>
					<option value="4">기타</option>
				</select>
			</td>
			<th>매수</th>
			<td style="text-align:left;">
				<input type="text" id="VIP_USE_CNT" name="VIP_USE_CNT" style="width:150px" value=""/>
			</td>
		</tr>
		<tr>
			<th>방식/쿠폰번호</th>
			<td colspan="3" style="text-align:left;">
				<input type="radio" name="VIP_TYPE" value="1" checked>단일
				이니셜
				<input type="text" id="INIT" name="INIT" value="VTR" style="width:100px" maxlength="3"/>
				번호
				<input type="text" id="VIP_NO" name="VIP_NO" value="" style="width:100px" maxlength="7"/>
				ex) VTR09 -> 이니셜 [VTR] 번호 [09]
				<input type="radio" name="VIP_TYPE" value="2" > 자동				
			</td>
		</tr>
		<tr>	
			<th>내용</th>
			<td colspan="3" style="text-align:left;">
				<textarea id="CONTENT" name="CONTENT" cols="20" rows="5" style="width:100%"></textarea>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>	
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnCancel()" class="btn01">취소</a>
		<a href="javascript:OnSave()" class="btn01">저장</a>
		<a href="javascript:OnList()" class="btn01">목록</a>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		$("#S_DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy-mm-dd',
			buttonImage: '/images/day.gif' });
		$("#E_DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy-mm-dd',
			buttonImage: '/images/day.gif' });
	});
</script>
<script type="text/javascript">
	
	function OnCancel()
	{
		location.href="/vip_no_list";
	}

	function OnSave()
	{
		if ($("#NAME").val() == "")
		{
			alert("쿠폰명을 입력해주세요.");
			return;
		}

		if ($("#S_DATE").val() == "")
		{
			alert("기간을 입력해주세요.");
			return;
		}

		if ($("#E_DATE").val() == "")
		{
			alert("기간을 입력해주세요.");
			return;
		}
		
		if ($("#VIP_USE_CNT").val() == "")
		{
			alert("매수를 입력해주세요.");
			return;
		}

		if ($("input[name=VIP_TYPE]:checked").val() == "1" && $("#VIP_NO").val() == "")
		{
			alert("쿠폰번호를 입력해주세요.");
			return;
		}

		if ($("#CONTENT").val() == "")
		{
			alert("내용을 입력해주세요.");
			return;
		}

		var data = {};		
		data.NAME = $("#NAME").val();
		data.VIP_STATUS = $("input[name=VIP_STATUS]:checked").val();
		data.S_DATE = $("#S_DATE").val();
		data.E_DATE = $("#E_DATE").val();
		data.VIP_USE = $("#VIP_USE option:selected").val();
		data.VIP_USE_CNT = $("#VIP_USE_CNT").val();
		data.VIP_TYPE = $("input[name=VIP_TYPE]:checked").val();
		data.VIP_NO = $("#VIP_NO").val();
		data.CONTENT = $("#CONTENT").val();

		$.ajax({
			url: '/vip_no_insert/insert',
            type: 'POST',
            data: JSON.stringify(data),
			contentType: 'application/json',
            success: function(data) 
			{
				Callback_OnSave(data);
            },
			error: function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnSave(jsonResult)
	{
		var jsonData = JSON.parse(jsonResult);
		if (jsonData.result == "ok")
		{
			alert("저장하였습니다.");
			location.href = "/vip_no_list";
		}
		else
		{
			alert(jsonData.message);
		}
		
	}

	function OnList()
	{
		location.href = "/vip_no_list";
	}

</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	