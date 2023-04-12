<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");
    int ID_KEY = header.getInt("ID_KEY");
	int PAGE = header.getInt("PAGE");
	String SEARCH_I_CODE = header.getString("SEARCH_I_CODE");
	String SEARCH_USE_GBN = header.getString("SEARCH_USE_GBN");

	JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
	JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);
	JSONObject obj3_0 = data.getJSONArray(3).getJSONObject(0);

	JSONObject obj = null;
%>

<!-- contents -->
<div class="contents">

    <h2>아이템 사용 내역</h2>
    <div class="navi"><a href="">아이템 관리</a> &nbsp; > &nbsp; <span class="blue">아이템 사용 내역</span></div>
    <div class="pt30"></div>
 
	<%
	String search_i_code0 = "";
	String search_i_code1 = "";
	String search_i_code2 = "";
	String search_i_code3 = "";
	String search_i_code4 = "";
	
	
	
	if (SEARCH_I_CODE.equals("0")) search_i_code0 = "selected";
	if (SEARCH_I_CODE.equals("9")) search_i_code1 = "selected";
	if (SEARCH_I_CODE.equals("10")) search_i_code2 = "selected";
	if (SEARCH_I_CODE.equals("11")) search_i_code3 = "selected";
	if (SEARCH_I_CODE.equals("12")) search_i_code4 = "selected";

	String search_use_gbn1 = "";
	String search_use_gbn2 = "";
	String search_use_gbn3 = "";
	String search_use_gbn4 = "";
	String search_use_gbn5 = "";
	
	if (SEARCH_USE_GBN.equals("0")) search_use_gbn1 = "selected";
	if (SEARCH_USE_GBN.equals("1")) search_use_gbn2 = "selected";
	if (SEARCH_USE_GBN.equals("2")) search_use_gbn3 = "selected";
	if (SEARCH_USE_GBN.equals("3")) search_use_gbn4 = "selected";
	if (SEARCH_USE_GBN.equals("4")) search_use_gbn5 = "selected";
	
	
	
	%>		
			<div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">기간 &nbsp; 
				<input type="text" id="S_DATE" name="S_DATE" value="<%=S_DATE%>" style="width:110px;">&nbsp;
				<!-- a href=""><img src="/images/day.gif" alt="" /></a-->&nbsp; ~ &nbsp;
				<input type="text" id="E_DATE" name="E_DATE" value="<%=E_DATE%>"" style="width:110px;">&nbsp;<!-- a href=""><img src="http://cms.veteranscout.co.kr/images/day.gif" alt="" /></a-->&nbsp;&nbsp;
				
				<input type="hidden" id="SEARCH_I_CODE" name="SEARCH_I_CODE" value="0">
				
				<select id="SEARCH_USE_GBN" name="SEARCH_USE_GBN" style="width:110px;">
					<option value="0"  <%=search_use_gbn1%>>전체</option>
					<option value="1"  <%=search_use_gbn2%>>판매</option>
					<option value="2"  <%=search_use_gbn3%>>사용</option>
					<option value="3"  <%=search_use_gbn4%>>쿠폰</option>
					<option value="4"  <%=search_use_gbn5%>>환불</option>
				</select>				

				<a  href="javascript:OnSearch();" class="btn01">검색</a>				
			</div>


			<div class="pt50"></div>		
			<h3>판매 및 사용내역 상세보기</h3>
			<b>직업소개소명 : <span class="blue"><a href="/user_detail/info/<%=ID_KEY%>"><%=obj2_0.getString("CORP_NAME")%></a></span></b>
			<b class="floatright"><img src="/images/icon_item.gif" alt=""  /> 보유 아이템 : 긴급일자리 알리미 
				<span class="blue"><%=obj3_0.getInt("RES_SUM_CNT")%></span>회 (환불가능 :<span class="blue"><%=obj3_0.getInt("CANCEL_AVL_COUNT")%></span>회 )				
			</b>			
			<div class="pt10"></div>		
			<table class="list_B" summary="아이템 사용 내역">
			<colgroup>
				<col width="13%" />
				<col width="16%" />				
				<col width="12%" />
				<col width="*" />
			</colgroup>
			<tr>
				<th>날짜</th>
				<th>구분</th>
				<th>수량</th>				
				<th>내역</th>				
			</tr>

			<%
			int i = 0;
			int nPAGE = PAGE;
			int nSEQ = (nPAGE-1)*20;
	
			int pageIndex = 0;
			int pageSeq;
			
			String USE_GBNNm; 
			String CPON_NONm;
			String Dawon_Nm;


			JSONArray data0 = data.getJSONArray(0);
			for (i=0;i<data0.length();i++) {
				obj = data0.getJSONObject(i);
				pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
				nSEQ = nSEQ + 1;
			%>			
			
						
				<tr>
					<td><%=obj.getString("USE_DATE")%></td>
					<td><%=obj.getString("USE_GBN_NM")%></td>
					<td><%=obj.getInt("USE_CNT")%></td>
					<td><%=obj.getString("LIST_INFO")%></td>
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
	var ID_KEY = "<%=ID_KEY%>";
	
	
	function WriteSDate()
	{
		var result = S_DATE.substring(0,4)+" 년 "+
			     S_DATE.substring(5,7)+" 월 "+
			     S_DATE.substring(8,10);
	
		document.write(result);
	}
	
	
	function WriteEDate()
	{
		var result = E_DATE.substring(0,4)+" 년 "+
			     E_DATE.substring(5,7)+" 월 "+
			     E_DATE.substring(8,10);
	
		document.write(result);
	}
	
	
	function OnPage(aPage)
	{
		var SEARCH_I_CODE = $("#SEARCH_I_CODE").val();
		var SEARCH_USE_GBN = $("#SEARCH_USE_GBN").val();
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var ID_KEY = "<%=ID_KEY%>";
		var PAGE = aPage;
		
		location.href = "/item_use_view/move" +
				"/"+SEARCH_I_CODE+
				"/"+SEARCH_USE_GBN+
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+ID_KEY+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_I_CODE = $("#SEARCH_I_CODE").val();
		var SEARCH_USE_GBN = $("#SEARCH_USE_GBN").val();
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var ID_KEY = "<%=ID_KEY%>";
		var PAGE = "1";

		location.href = "/item_use_view/move" +
				"/"+SEARCH_I_CODE+
				"/"+SEARCH_USE_GBN+
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+ID_KEY+
				"/"+PAGE;
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

		   
	});

	
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	