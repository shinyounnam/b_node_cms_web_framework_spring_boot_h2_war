<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String S_DATE = header.getString("S_DATE");
    String E_DATE = header.getString("E_DATE");    
    int PAGE = header.getInt("PAGE");
    

    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);         
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);         
    JSONObject obj2_0 = data.getJSONArray(2).getJSONObject(0);         
    JSONObject obj = null;   
%>



    <!-- contents -->
    <div class="contents">
        <h2>아이템 판매 내역</h2>
        <div class="navi"><a href="">통계</a> &nbsp; > &nbsp; <span class="blue">매출 통계</span></div>
        <div class="pt30"></div>
        <div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">기간 &nbsp; 
            <input type="text" id="S_DATE" name="S_DATE" value="<%=S_DATE%>" style="width:110px;">&nbsp;
            <!-- a href=""><img src="http://cms.veteranscout.co.kr/images/day.gif" alt="" /></a-->&nbsp; ~ &nbsp;
            <input type="text" id="E_DATE" name="E_DATE" value="<%=E_DATE%>"" style="width:110px;">&nbsp;<!-- a href=""><img src="http://cms.veteranscout.co.kr/images/day.gif" alt="" /></a-->&nbsp;&nbsp;
            <a  href="javascript:OnSearch();" class="btn01">검색</a>				
        </div>
        <div class="pt50"></div>		
        <table class="list_C" summary="매출 통계">
        <colgroup>
            <col width="15.5%" />
            <col width="*" />
            <col width="15.5%" />
            <col width="15.5%" />
            <col width="15.5%" />
        </colgroup>
            <tr>
                <th>날짜</th>
                <th>아이템명</th>
                <th>판매수량</th>
                <th>알리미수량</th>
                <th>판매금액</th>
            </tr>
            <tr>
                <th rowspan="4" class="center">기간내<br/>[전체]</th>
                <td>긴급일자리 알리미<br/>5회 이용권 </td>
                <td>
                    <%=obj0_0.getInt("REQ_QTY_BETWEEN_9")%><br/>
                    [<%=obj0_0.getInt("REQ_QTY_9")%>]</td>
                <td>
                    <%=obj0_0.getInt("USE_CNT_BETWEEN_9")%><br/>
                    [<%=obj0_0.getInt("USE_CNT_9")%>]
                </td>
                <td>
                    <%=obj0_0.getInt("PAY_PRIC_BETWEEN_9")%><br/>
                    [<%=obj0_0.getInt("PAY_PRIC_9")%>]
                </td>
            </tr>
            <tr>
                <td>긴급일자리 알리미<br/>10+2회 이용권 </td>
                <td>
                    <%=obj0_0.getInt("REQ_QTY_BETWEEN_10")%><br/>
                    [<%=obj0_0.getInt("REQ_QTY_10")%>]
                </td>
                <td>
                    <%=obj0_0.getInt("USE_CNT_BETWEEN_10")%><br/>
                    [<%=obj0_0.getInt("USE_CNT_10")%>]
                </td>
                <td>
                    <%=obj0_0.getInt("PAY_PRIC_BETWEEN_10")%><br/>
                    [<%=obj0_0.getInt("PAY_PRIC_10")%>]
                </td>
            </tr>
            <tr>
                <td>긴급일자리 알리미<br/>20+5회 이용권</td>
                <td>
                    <%=obj0_0.getInt("REQ_QTY_BETWEEN_11")%><br/>
                    [<%=obj0_0.getInt("REQ_QTY_11")%>]
                </td>
                <td>
                    <%=obj0_0.getInt("USE_CNT_BETWEEN_11")%><br/>
                    [<%=obj0_0.getInt("USE_CNT_11")%>]
                </td>
                <td>
                    <%=obj0_0.getInt("PAY_PRIC_BETWEEN_11")%><br/>
                    [<%=obj0_0.getInt("PAY_PRIC_11")%>]
                </td>
            </tr>
            <tr>
                <th style="background-color:#eef3f7">종합</th>
                <th style="background-color:#eef3f7">
                    <%=obj0_0.getInt("REQ_QTY_BETWEEN")%><br/>
                    [<%=obj0_0.getInt("REQ_QTY")%>]
                </th>
                <th style="background-color:#eef3f7">
                    <%=obj0_0.getInt("USE_CNT_BETWEEN")%><br/>
                    [<%=obj0_0.getInt("USE_CNT")%>]
                </th>
                <th style="background-color:#eef3f7">
                    <%=obj0_0.getInt("PAY_PRIC_BETWEEN")%><br/>
                    [<%=obj0_0.getInt("PAY_PRIC")%>]
                </th>
            </tr>
            
        <%
        int i = 0;
        int nPAGE = PAGE;
        int nSEQ = (nPAGE-1)*20;
        
        int pageIndex = 0;
        int pageSeq = 0;
        JSONArray data1 = data.getJSONArray(1);

        for (i=0;i<data1.length();i++) {
            
            obj = data1.getJSONObject(i);

            pageSeq = (obj2_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
            nSEQ = nSEQ + 1;
        %>			
            
            <tr>
                <th rowspan="3">
                    <a href="javascript:fn_link_day_sale('<%=obj.getString("BUY_DATE")%>');">
                        <%=obj.getString("BUY_DATE")%>
                    </a>
                </th>
                <td>긴급일자리 알리미<br/>5회 이용권 </td>
                <td><%=obj.getInt("REQ_QTY_9")%></td>
                <td><%=obj.getInt("USE_CNT_9")%></td>
                <td><%=obj.getInt("PAY_PRIC_9")%></td>
            </tr>
            <tr>
                <td>긴급일자리 알리미<br/>10+2회 이용권 </td>
                <td><%=obj.getInt("REQ_QTY_10")%></td>
                <td><%=obj.getInt("USE_CNT_10")%></td>
                <td><%=obj.getInt("PAY_PRIC_10")%></td>
            </tr>
            <tr>
                <td>긴급일자리 알리미<br/>20+5회 이용권 </td>
                <td><%=obj.getInt("REQ_QTY_11")%></td>
                <td><%=obj.getInt("USE_CNT_11")%></td>
                <td><%=obj.getInt("PAY_PRIC_11")%></td>
            </tr>
            
                                    
            <%
                pageIndex = pageIndex + 1;
            }
            %>
        </table>
        <script>
            var totalPage = '<%=obj2_0.getInt("TOTAL_PAGE")%>';
            if (totalPage > 0)
            {
                document.write(PageMake('<%=PAGE%>','15','<%=obj2_0.getInt("TOTAL_PAGE")%>'));
            }
        </script>
    </div>
    <div class="pt10"></div>
    <div style="width:100%;text-align:right;">
        <a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">매출통계 엑셀 저장</a>
    </div>
    <!-- contents -->
</div>



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
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var PAGE = aPage;
		location.href = "/statistics_sale/move" +
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var S_DATE = $("#S_DATE").val();
		var E_DATE = $("#E_DATE").val();
		var PAGE = "1";
		location.href = "/statistics_sale/move" +
				"/"+S_DATE+
				"/"+E_DATE+
				"/"+PAGE;
	}
	
	
	function fn_link_day_sale(strDATE)
	{
		var S_DATE = strDATE;
		var E_DATE = strDATE;
		var PAGE = "1";
		location.href = "/item_sale_mng/move" +
		"/ / "+
		"/"+S_DATE+
		"/"+E_DATE+
		"/0/1";
	}
	

	function OnExcel()
	{
 		var url = j_xls_web_url + "/stat_sale_list.php?S_DATE="+$("#S_DATE").val();
		url += "&E_DATE="+$("#E_DATE").val();
		location.href = url;
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