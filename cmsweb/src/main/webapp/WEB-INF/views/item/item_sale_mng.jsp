<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
    String SALE_TYPE = header.getString("SALE_TYPE");
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);    
	JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);    
    JSONObject obj = null;   
%>


<!-- contents -->
<div class="contents">

	<h2>아이템 판매 내역</h2>
	<div class="navi"><a href="">아이템 관리</a> &nbsp; > &nbsp; <span class="blue">아이템 판매 내역</span></div>
		<div class="pt30"></div>
 
	<%
	String searchType1 = "";
	String searchType2 = "";
	String searchType3 = "";
	String searchType4 = "";
	if (SEARCH_TYPE.equals("0")) searchType1 = "selected";
	if (SEARCH_TYPE.equals("1")) searchType2 = "selected";
	if (SEARCH_TYPE.equals("2")) searchType3 = "selected";
	if (SEARCH_TYPE.equals("3")) searchType4 = "selected";

	%>		
			<div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">기간 &nbsp; 
				<input type="text" id="S_DATE" name="S_DATE" value="<%=S_DATE%>" style="width:110px;">&nbsp;
				<!-- a href=""><img src="http://cms.veteranscout.co.kr/images/day.gif" alt="" /></a-->&nbsp; ~ &nbsp;
				<input type="text" id="E_DATE" name="E_DATE" value="<%=E_DATE%>"" style="width:110px;">&nbsp;<!-- a href=""><img src="http://cms.veteranscout.co.kr/images/day.gif" alt="" /></a-->&nbsp;&nbsp;
				<input type="radio" id="SALE_TYPE" name="SALE_TYPE" value="0" checked>전체
				<input type="radio" id="SALE_TYPE" name="SALE_TYPE" value="1" >쿠폰 제외
				<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:110px;">
					<option value="0"  <%=searchType1%>>전체</option>
					<option value="1"  <%=searchType2%>>직업소개소명</option>
					<option value="2"  <%=searchType3%>>아이디</option>
					<option value="3"  <%=searchType4%>>판매번호</option>
				</select>
				<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" value="<%=SEARCH_TXT%>" style="width:150px;" />
				<a  href="javascript:OnSearch();" class="btn01">검색</a>				
			</div>

			<div class="pt50"></div>
			<div class="floatleft arrow">검색결과 :  <script>WriteSDate()</script> ~ <script>WriteEDate()</script> 매출 합계는 <span class="blue"><%=obj2_0.getInt("PAY_PRIC_SUM")%></span>원 입니다.</div>
			<div class="pt10"></div>
			
				<table class="list_A" summary="아이템 판매 내역">
				<colgroup>
					
					<col width="10.5%" />
					<col width="10.5%" />
					<col width="10.5%" />
					<col width="*%" />
					<col width="10.5%" />
					<col width="10.5%" />					
				</colgroup>
					
					<tr>
						<th>NO</th>
						<th>날짜</th>
						<th>아이디(직업소개소명)</th>
						<th>내역</th>						
						<th>판매금액</th>
						<th>구분</th>						
					</tr>
					<%
					int i = 0;
					int nPAGE = PAGE;
					int nSEQ = (nPAGE-1)*20;
			
					int pageIndex = 0;
					int pageSeq = 0;
					String CPON_NONm = "";
					String Dawon_Nm = "";
                    String strsvcCd = "";

                    JSONArray data0 = data.getJSONArray(0);
				 
					for (i=0;i<data0.length();i++){
                        
                        obj = data0.getJSONObject(i);

					    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
					    nSEQ = nSEQ + 1;
					%>
					
					
					<%
					if (obj.getString("USE_GBN").equals("3")){
						CPON_NONm = "(" + obj.getString("CPON_NUM")  + ")" ;
					}else {
						CPON_NONm = "";
					}
	 
					
					if (obj.getString("USE_GBN").equals("2") || obj.getString("USE_GBN").equals("4")){
					    Dawon_Nm = " - " ;
					}else {
					    Dawon_Nm = "";
					}
					
	
	
					if (obj.getInt("PAY_MTHD") == 1) {  
					    strsvcCd = "01" ;
					}else if (obj.getInt("PAY_MTHD") == 8 ) {
					    strsvcCd = "07";
					}else if (obj.getInt("PAY_MTHD") == 6) {
					    strsvcCd = "02";
					}else if (obj.getInt("PAY_MTHD") == 9) {
					    strsvcCd = "09";
					}else if (obj.getInt("PAY_MTHD") == 7) {
					    strsvcCd = "";
					}else{
					    strsvcCd = "";
					}

					%>
					<tr>
						<td><%=pageSeq%></td>
						<td><%=obj.getString("BUY_DATE")%></td>
						<td><a href="javascript:GOPage('<%=obj.getInt("ID_KEY")%>');">
                            <%=obj.getString("ID")%><br/>
                            (<%=obj.getString("NAME")%>)
                            </a>
                        </td>
						<td>
							긴급일자리 알리미 <%=obj.getString("ITEM_NAME")%> * <%=obj.getInt("REQ_QTY")%>개<br/>
							 <%=obj.getString("PAY_NO")%>
						</td>
						<td><%=obj.getString("PAY_PRIC")%></td>						
						<td>
						
							<%=obj.getString("USE_STATUS")%>
							</br><%=obj.getString("PAY_MTHD_NM")%>
							</br><%=obj.getString("USE_GBN_NM")%><%=CPON_NONm%>  
							<%
							if (obj.getString("USE_GBN").equals("1"))
                            {
							%>			
								</br>
								<a href="javascript:OnItemCancel('<%=obj.getInt("INCOME_ID")%>','<%=obj.getString("PAY_NO")%>','<%=strsvcCd%>','<%=obj.getInt("PRICE")%>','<%=obj.getString("APP_PACKAGE_NAME")%>');" class="btn01">승인취소</a>
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
					document.write(PageMake('<%=PAGE%>','15','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
				}
			</script>
		</div>
		<!-- contents -->
	</div>
	<!-- container -->
 </div>

 <script type="text/javascript">
	
	
	var S_DATE = "<%=S_DATE%>";
	var E_DATE = "<%=E_DATE%>";
	var SALE_TYPE = "<%=SALE_TYPE%>";
	
	
	function WriteSDate()
	{
		var result = S_DATE.substring(0,4)+" 년 "+
			     S_DATE.substring(5,7)+" 월 "+
			     S_DATE.substring(8,10)+" 일 ";
	
		document.write(result);
	}
	
	
	function WriteEDate()
	{
		var result = E_DATE.substring(0,4)+" 년 "+
			     E_DATE.substring(5,7)+" 월 "+
			     E_DATE.substring(8,10)+" 일 ";
	
		document.write(result);
	}
	
	
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var SALE_TYPE = $(":input:radio[name=SALE_TYPE]:checked").val();
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var PAGE = aPage;
		
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/item_sale_mng/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+SALE_TYPE+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var SALE_TYPE = $(":input:radio[name=SALE_TYPE]:checked").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/item_sale_mng/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+SALE_TYPE+
				"/"+PAGE;
	}
	

	function OnItemCancel(INCOME_ID,PAY_NO,strsvcCd,PAY_PRIC,APP_PACKAGE_NAME) {
	
		if (confirm("승인 취소하시겠습니까?"))
		{
			var data = {};
			data.INCOME_ID = INCOME_ID;
			data.PAGE = "1";
			data.SEARCH_TYPE = $("#SEARCH_TYPE").val();
			data.SEARCH_TXT = $("#SEARCH_TXT").val();
			data.S_DATE = $("#S_DATE").val();
			data.E_DATE = $("#E_DATE").val();
			data.SALE_TYPE = $(":input:radio[name=SALE_TYPE]:checked").val();
			$.ajax({
				url: '/item_sale_mng/item_cancel',
		  		type: 'POST',
				data: JSON.stringify(data),
				contentType: 'application/json',
				success: function(data) 
				{
					var jsonData = JSON.parse(data);
					var jsonResult = jsonData.data[0][0];
					if (jsonResult.result=="ok")
					{
						alert("승인취소 완료되었습니다.");
						OnPage("<%=PAGE%>");
					}
					else
					{
						var ERROR_CODE = jsonResult.ERROR_CODE;
						if (ERROR_CODE=="1"){
							alert("아이템 입금 정보가 없습니다.");
						}
						else if (ERROR_CODE=="2"){
							alert("아이템 사용이 사용되어 취소할 수 없습니다.");
						}
						else if (ERROR_CODE=="3"){
							alert("구매 아이템이 아니어서, 취소할 수 없습니다.");
						}
						else if (ERROR_CODE=="4"){
							alert("구매후 7일이 지난 아이템이어서, 취소할 수 없습니다.");
						}
						else{
							// 에러발생시 팝업
							alert(jsonResult.message);
						}
						
					}
	            },
				error: function(err) {
					alert(err.responseText);
				}
			});
		}
	}
		
	
	
	function GOPage(s_GO_ID_KEY)
	{
		var GO_ID_KEY ;
		GO_ID_KEY = s_GO_ID_KEY ;
 		location.href = "/item_use_view" +
				"/"+GO_ID_KEY;
	}
	
	


	$(document).ready(function() {
		
		$("#S_DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy-mm-dd',
			buttonImage: '/images/day.gif' });
		$("#E_DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy-mm-dd',
			buttonImage: '/images/day.gif' });

		 $('input:radio[name=SALE_TYPE]:input[value=' + SALE_TYPE + ']').attr("checked", true);

		   
	});

	
</script>

 
<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	