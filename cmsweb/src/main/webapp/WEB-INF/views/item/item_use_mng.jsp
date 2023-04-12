<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");    
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);    
    JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);    
    JSONObject obj = null;   
    int i = 0;
%>
 
<!-- contents -->
<div class="contents">
    <h2>아이템 사용 내역</h2>
    <div class="navi"><a href="">아이템 관리</a> &nbsp; > &nbsp; <span class="blue">아이템 사용 내역</span></div>
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
        <select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:110px;">
            <option value="0"  <%=searchType1%>>전체</option>
            <option value="1"  <%=searchType2%>>직업소개소명</option>
            <option value="2"  <%=searchType3%>>아이디</option>
        </select>
        <input type="text" id="SEARCH_TXT" name="SEARCH_TXT" value="<%=SEARCH_TXT%>" style="width:150px;" />
        <a  href="javascript:OnSearch();" class="btn01">검색</a>				
    </div>
    <div class="pt50"></div>

    <div class="floatleft arrow">검색결과 :  <script>WriteSDate()</script> ~ <script>WriteEDate()</script> 총 <span class="blue"><%=obj2_0.getInt("USES_COUNT")%></span> 건의 사용 내역이 있습니다.</div>
    <div class="pt10"></div>			
    <table class="list_A" summary="아이템 사용 내역">
    <colgroup>
        <col width="*%" />
        <col width="16.5%" />
        <col width="16.5%" />
        <col width="16.5%" />
        <col width="16.5%" />
        <col width="16.5%" />
    </colgroup>
        <tr>
            <th>NO</th>
            <th>날짜</th>
            <th>직업소개소명 (아이디)</th>
            <th>사용알리미수량</th>
            <th>사용경로</th>
            <th>잔여알리미수량</th>
        </tr>
            <%
            i = 0;
            int nPAGE = PAGE;
            int nSEQ = (nPAGE-1)*20;
            int pageIndex = 0;
            int pageSeq = 0;
            JSONArray data0 = data.getJSONArray(0);
            for (i=0;i<data0.length();i++) {
                obj = data0.getJSONObject(i);
                pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
                nSEQ = nSEQ + 1;
            %>
                            
        <tr>
            <td><%=pageSeq%></td>
            <td><%=obj.getString("USE_DATE")%></td>
            <td><a href="javascript:GOPage('<%=obj.getInt("ID_KEY")%>');"><%=obj.getString("CORP_ID")%><br/>(<%=obj.getString("CORP_NAME")%>)</a></td>
            <td><%=obj.getInt("USE_CNT")%></td>
            <td><%=obj.getString("USE_STATUS")%></td>
            <td>
                <%=obj.getInt("RES_SUM_CNT")%>+<%=obj.getInt("CANCEL_AVL_COUNT")%>						
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

<script type="text/javascript">

	var S_DATE = "<%=S_DATE%>";
	var E_DATE = "<%=E_DATE%>";

	
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
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();

		var PAGE = aPage;
		
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/item_use_mng/move" +
				"/"+SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/item_use_mng/move" +
				"/"+SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+PAGE;
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
 
		   
	});

</script>

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	