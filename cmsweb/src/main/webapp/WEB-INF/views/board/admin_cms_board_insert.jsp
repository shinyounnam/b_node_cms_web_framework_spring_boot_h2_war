<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>
<link rel="stylesheet" href="/daumeditor/css/editor.css" type="text/css" charset="utf-8"/>
<script src="/daumeditor/js/editor_loader.js?environment=production" type="text/javascript" charset="utf-8"></script>
<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject data0_0 = data.getJSONArray(0).getJSONObject(0);
    String ACTION = header.getString("ACTION");
    int i = 0;
    JSONObject obj = null;
%>


<!-- contents -->
<div class="contents">
	<h2>이벤트/공지사항</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">이벤트/공지사항</span></div>
	<div class="pt30"></div>
	<% if (ACTION == "INSERT") { %>
	    <form name="frm" method="post" action="/admin_cms_board_insert/insert_submit" enctype="multipart/form-data">
	<% } else if (ACTION == "UPDATE") { %>
	    <form name="frm" method="post" action="/admin_cms_board_insert/update_submit" enctype="multipart/form-data">
	<% } %>
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
				<select id="GBN" name="GBN" style="width:200px" onchange="javascript:OnGbn(this);">
					<option value="">선택</option>
					<%
                    JSONArray data1 = data.getJSONArray(1);
					for (i=0;i<data1.length();i++) {
                        obj = data1.getJSONObject(i);
						if (data0_0.getString("GBN").equals(obj.getString("CODE"))) {
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
			<th>대상구분</th>
			<td style="text-align:left">
				<select id="USER_GBN" name="USER_GBN" style="width:200px">
					<option value="">선택</option>
					<%
					String strUserGbn1 = "";
					String strUserGbn2 = "";
					String strUserGbn3 = "";
					if (data0_0.getString("USER_GBN").equals("1")) {
						strUserGbn1 = "selected";
					}
					if (data0_0.getString("USER_GBN").equals("2")) {
						strUserGbn2 = "selected";
					}
					if (data0_0.getString("USER_GBN").equals("3")) {
						strUserGbn3 = "selected";
					}
					%>
					<option value="1" <%=strUserGbn1%>>직업소개소</option>
					<option value="2" <%=strUserGbn2%>>근로자</option>
					<option value="3" <%=strUserGbn3%>>전체</option>
				</select>
			</td>
			
		</tr>
		<tr>
			<th>제목</th>
			<td style="text-align:left">
				<input type="text" id="TITLE" name="TITLE" maxlength="100" style="width:300px" value="<%=data0_0.getString("TITLE")%>"/>
			</td>
			<th>작성자</th>
			<td style="text-align:left">
				<%=data0_0.getString("NAME")%>
			</td>
		</tr>
		<%
		if(data0_0.getString("GBN").equals("A1")) {
		%>
		<tr	id="event_date" style="display:none">
			<th>기간</th>
			<td style="text-align:left" colspan="3">
				<input type="text" id="S_DATE" name="S_DATE" maxlength="10" style="width:100px" value=""/>
				~
				<input type="text" id="E_DATE" name="E_DATE" maxlength="10" style="width:100px" value=""/>
			</td>
		</tr>
		<%
		} else {
		%>
		<tr	id="event_date" style="display:">
			<th>기간</th>
			<td style="text-align:left" colspan="3">
				<input type="text" id="S_DATE" name="S_DATE" maxlength="10" style="width:100px" value="<%=data0_0.getString("S_DATE")%>"/>
				~
				<input type="text" id="E_DATE" name="E_DATE" maxlength="10" style="width:100px" value="<%=data0_0.getString("E_DATE")%>"/>
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<td colspan="4">
				<div id="tx_trex_container" class="tx-editor-container">
					<!-- 사이드바 -->
					<div id="tx_sidebar" class="tx-sidebar">
						<div class="tx-sidebar-boundary">
							<!-- 사이드바 / 첨부 -->
							<ul class="tx-bar tx-bar-left tx-nav-attach">
								<!-- 이미지 첨부 버튼 시작 -->
								<!--
									@decsription
									<li></li> 단위로 위치를 이동할 수 있다.
								-->
								<li class="tx-list">
									<div unselectable="on" id="tx_image" class="tx-image tx-btn-trans">
										<a href="javascript:;" title="사진" class="tx-text">사진</a>
										<input type="file" id="image_file" name="image_file" onchange="ImageChange()" style="visibility:hidden">
									</div>
								</li>
								<!-- 이미지 첨부 버튼 끝 -->
							</ul>
	
							<!-- 사이드바 / 우측영역 -->
							<ul class="tx-bar tx-bar-right">
								<li class="tx-list">
									<div unselectable="on" class="tx-btn-lrbg tx-fullscreen" id="tx_fullscreen">
										<a href="javascript:;" class="tx-icon" title="넓게쓰기 (Ctrl+M)">넓게쓰기</a>
									</div>
								</li>
							</ul>
							<ul class="tx-bar tx-bar-right tx-nav-opt">
								<li class="tx-list">
									<div unselectable="on" class="tx-switchtoggle" id="tx_switchertoggle">
										<a href="javascript:;" title="에디터 타입">에디터</a>
									</div>
								</li>
							</ul>
						</div>
					</div>

					<!-- 툴바 - 기본 시작 -->
					<!--
						@decsription
						툴바 버튼의 그룹핑의 변경이 필요할 때는 위치(왼쪽, 가운데, 오른쪽) 에 따라 <li> 아래의 <div>의 클래스명을 변경하면 된다.
						tx-btn-lbg: 왼쪽, tx-btn-bg: 가운데, tx-btn-rbg: 오른쪽, tx-btn-lrbg: 독립적인 그룹

						드롭다운 버튼의 크기를 변경하고자 할 경우에는 넓이에 따라 <li> 아래의 <div>의 클래스명을 변경하면 된다.
						tx-slt-70bg, tx-slt-59bg, tx-slt-42bg, tx-btn-43lrbg, tx-btn-52lrbg, tx-btn-57lrbg, tx-btn-71lrbg
						tx-btn-48lbg, tx-btn-48rbg, tx-btn-30lrbg, tx-btn-46lrbg, tx-btn-67lrbg, tx-btn-49lbg, tx-btn-58bg, tx-btn-46bg, tx-btn-49rbg
					-->
					<div id="tx_toolbar_basic" class="tx-toolbar tx-toolbar-basic"><div class="tx-toolbar-boundary">
						<ul class="tx-bar tx-bar-left">
							<li class="tx-list">
								<div id="tx_fontfamily" unselectable="on" class="tx-slt-70bg tx-fontfamily">
									<a href="javascript:;" title="글꼴">굴림</a>
								</div>
								<div id="tx_fontfamily_menu" class="tx-fontfamily-menu tx-menu" unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left">
							<li class="tx-list">
								<div unselectable="on" class="tx-slt-42bg tx-fontsize" id="tx_fontsize">
									<a href="javascript:;" title="글자크기">9pt</a>
								</div>
								<div id="tx_fontsize_menu" class="tx-fontsize-menu tx-menu" unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left tx-group-font">
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lrbg 	tx-specialchar" id="tx_specialchar">
									<a href="javascript:;" class="tx-icon" title="특수문자">특수문자</a>
								</div>
								<div id="tx_specialchar_menu" class="tx-specialchar-menu tx-menu"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lbg 	tx-bold" id="tx_bold">
									<a href="javascript:;" class="tx-icon" title="굵게 (Ctrl+B)">굵게</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-underline" id="tx_underline">
									<a href="javascript:;" class="tx-icon" title="밑줄 (Ctrl+U)">밑줄</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-italic" id="tx_italic">
									<a href="javascript:;" class="tx-icon" title="기울임 (Ctrl+I)">기울임</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-strike" id="tx_strike">
									<a href="javascript:;" class="tx-icon" title="취소선 (Ctrl+D)">취소선</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-slt-tbg 	tx-forecolor" id="tx_forecolor">
									<a href="javascript:;" class="tx-icon" title="글자색">글자색</a>
									<a href="javascript:;" class="tx-arrow" title="글자색 선택">글자색 선택</a>
								</div>
								<div id="tx_forecolor_menu" class="tx-menu tx-forecolor-menu tx-colorpallete"
									 unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-slt-brbg 	tx-backcolor" id="tx_backcolor">
									<a href="javascript:;" class="tx-icon" title="글자 배경색">글자 배경색</a>
									<a href="javascript:;" class="tx-arrow" title="글자 배경색 선택">글자 배경색 선택</a>
								</div>
								<div id="tx_backcolor_menu" class="tx-menu tx-backcolor-menu tx-colorpallete"
									 unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left tx-group-align">
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lbg 	tx-alignleft" id="tx_alignleft">
									<a href="javascript:;" class="tx-icon" title="왼쪽정렬 (Ctrl+,)">왼쪽정렬</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-aligncenter" id="tx_aligncenter">
									<a href="javascript:;" class="tx-icon" title="가운데정렬 (Ctrl+.)">가운데정렬</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-alignright" id="tx_alignright">
									<a href="javascript:;" class="tx-icon" title="오른쪽정렬 (Ctrl+/)">오른쪽정렬</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-rbg 	tx-alignfull" id="tx_alignfull">
									<a href="javascript:;" class="tx-icon" title="양쪽정렬">양쪽정렬</a>
								</div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left tx-group-tab">
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lbg 	tx-indent" id="tx_indent">
									<a href="javascript:;" title="들여쓰기 (Tab)" class="tx-icon">들여쓰기</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-rbg 	tx-outdent" id="tx_outdent">
									<a href="javascript:;" title="내어쓰기 (Shift+Tab)" class="tx-icon">내어쓰기</a>
								</div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left tx-group-list">
							<li class="tx-list">
								<div unselectable="on" class="tx-slt-31lbg tx-lineheight" id="tx_lineheight">
									<a href="javascript:;" class="tx-icon" title="줄간격">줄간격</a>
									<a href="javascript:;" class="tx-arrow" title="줄간격">줄간격 선택</a>
								</div>
								<div id="tx_lineheight_menu" class="tx-lineheight-menu tx-menu" unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="tx-slt-31rbg tx-styledlist" id="tx_styledlist">
									<a href="javascript:;" class="tx-icon" title="리스트">리스트</a>
									<a href="javascript:;" class="tx-arrow" title="리스트">리스트 선택</a>
								</div>
								<div id="tx_styledlist_menu" class="tx-styledlist-menu tx-menu" unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left tx-group-etc">
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lrbg 	tx-link" id="tx_link">
									<a href="javascript:;" class="tx-icon" title="링크 (Ctrl+K)">링크</a>
								</div>
								<div id="tx_link_menu" class="tx-link-menu tx-menu"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lrbg 	tx-table" id="tx_table">
									<a href="javascript:;" class="tx-icon" title="표만들기">표만들기</a>
								</div>
								<div id="tx_table_menu" class="tx-table-menu tx-menu" unselectable="on">
									<div class="tx-menu-inner">
										<div class="tx-menu-preview"></div>
										<div class="tx-menu-rowcol"></div>
										<div class="tx-menu-deco"></div>
										<div class="tx-menu-enter"></div>
									</div>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lrbg 	tx-horizontalrule" id="tx_horizontalrule">
									<a href="javascript:;" class="tx-icon" title="구분선">구분선</a>
								</div>
								<div id="tx_horizontalrule_menu" class="tx-horizontalrule-menu tx-menu" unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left">
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lbg 	tx-richtextbox" id="tx_richtextbox">
									<a href="javascript:;" class="tx-icon" title="글상자">글상자</a>
								</div>
								<div id="tx_richtextbox_menu" class="tx-richtextbox-menu tx-menu">
									<div class="tx-menu-header">
										<div class="tx-menu-preview-area">
											<div class="tx-menu-preview"></div>
										</div>
										<div class="tx-menu-switch">
											<div class="tx-menu-simple tx-selected"><a><span>간단 선택</span></a></div>
											<div class="tx-menu-advanced"><a><span>직접 선택</span></a></div>
										</div>
									</div>
									<div class="tx-menu-inner">
									</div>
									<div class="tx-menu-footer">
										<img class="tx-menu-confirm"
											 src="/daumeditor/images/icon/editor/btn_confirm.gif?rv=1.0.1" alt=""/>
										<img class="tx-menu-cancel" hspace="3"
											 src="/daumeditor/images/icon/editor/btn_cancel.gif?rv=1.0.1" alt=""/>
									</div>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-quote" id="tx_quote">
									<a href="javascript:;" class="tx-icon" title="인용구 (Ctrl+Q)">인용구</a>
								</div>
								<div id="tx_quote_menu" class="tx-quote-menu tx-menu" unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-bg 	tx-background" id="tx_background">
									<a href="javascript:;" class="tx-icon" title="배경색">배경색</a>
								</div>
								<div id="tx_background_menu" class="tx-menu tx-background-menu tx-colorpallete"
									 unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-rbg 	tx-dictionary" id="tx_dictionary">
									<a href="javascript:;" class="tx-icon" title="사전">사전</a>
								</div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left tx-group-undo">
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-lbg 	tx-undo" id="tx_undo">
									<a href="javascript:;" class="tx-icon" title="실행취소 (Ctrl+Z)">실행취소</a>
								</div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="		 tx-btn-rbg 	tx-redo" id="tx_redo">
									<a href="javascript:;" class="tx-icon" title="다시실행 (Ctrl+Y)">다시실행</a>
								</div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-right">
							<li class="tx-list">
								<div unselectable="on" class="tx-btn-nlrbg tx-advanced" id="tx_advanced">
									<a href="javascript:;" class="tx-icon" title="툴바 더보기">툴바 더보기</a>
								</div>
							</li>
						</ul>
					</div></div>
					<!-- 툴바 - 기본 끝 -->
					<!-- 툴바 - 더보기 시작 -->
					<div id="tx_toolbar_advanced" class="tx-toolbar tx-toolbar-advanced"><div class="tx-toolbar-boundary">
						<ul class="tx-bar tx-bar-left">
							<li class="tx-list">
								<div class="tx-tableedit-title"></div>
							</li>
						</ul>

						<ul class="tx-bar tx-bar-left tx-group-align">
							<li class="tx-list">
								<div unselectable="on" class="tx-btn-lbg tx-mergecells" id="tx_mergecells">
									<a href="javascript:;" class="tx-icon2" title="병합">병합</a>
								</div>
								<div id="tx_mergecells_menu" class="tx-mergecells-menu tx-menu" unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="tx-btn-bg tx-insertcells" id="tx_insertcells">
									<a href="javascript:;" class="tx-icon2" title="삽입">삽입</a>
								</div>
								<div id="tx_insertcells_menu" class="tx-insertcells-menu tx-menu" unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div unselectable="on" class="tx-btn-rbg tx-deletecells" id="tx_deletecells">
									<a href="javascript:;" class="tx-icon2" title="삭제">삭제</a>
								</div>
								<div id="tx_deletecells_menu" class="tx-deletecells-menu tx-menu" unselectable="on"></div>
							</li>
						</ul>

						<ul class="tx-bar tx-bar-left tx-group-align">
							<li class="tx-list">
								<div id="tx_cellslinepreview" unselectable="on" class="tx-slt-70lbg tx-cellslinepreview">
									<a href="javascript:;" title="선 미리보기"></a>
								</div>
								<div id="tx_cellslinepreview_menu" class="tx-cellslinepreview-menu tx-menu"
									 unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div id="tx_cellslinecolor" unselectable="on" class="tx-slt-tbg tx-cellslinecolor">
									<a href="javascript:;" class="tx-icon2" title="선색">선색</a>

									<div class="tx-colorpallete" unselectable="on"></div>
								</div>
								<div id="tx_cellslinecolor_menu" class="tx-cellslinecolor-menu tx-menu tx-colorpallete"
									 unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div id="tx_cellslineheight" unselectable="on" class="tx-btn-bg tx-cellslineheight">
									<a href="javascript:;" class="tx-icon2" title="두께">두께</a>

								</div>
								<div id="tx_cellslineheight_menu" class="tx-cellslineheight-menu tx-menu"
									 unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div id="tx_cellslinestyle" unselectable="on" class="tx-btn-bg tx-cellslinestyle">
									<a href="javascript:;" class="tx-icon2" title="스타일">스타일</a>
								</div>
								<div id="tx_cellslinestyle_menu" class="tx-cellslinestyle-menu tx-menu" unselectable="on"></div>
							</li>
							<li class="tx-list">
								<div id="tx_cellsoutline" unselectable="on" class="tx-btn-rbg tx-cellsoutline">
									<a href="javascript:;" class="tx-icon2" title="테두리">테두리</a>

								</div>
								<div id="tx_cellsoutline_menu" class="tx-cellsoutline-menu tx-menu" unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left">
							<li class="tx-list">
								<div id="tx_tablebackcolor" unselectable="on" class="tx-btn-lrbg tx-tablebackcolor"
									 style="background-color:#9aa5ea;">
									<a href="javascript:;" class="tx-icon2" title="테이블 배경색">테이블 배경색</a>
								</div>
								<div id="tx_tablebackcolor_menu" class="tx-tablebackcolor-menu tx-menu tx-colorpallete"
									 unselectable="on"></div>
							</li>
						</ul>
						<ul class="tx-bar tx-bar-left">
							<li class="tx-list">
								<div id="tx_tabletemplate" unselectable="on" class="tx-btn-lrbg tx-tabletemplate">
									<a href="javascript:;" class="tx-icon2" title="테이블 서식">테이블 서식</a>
								</div>
								<div id="tx_tabletemplate_menu" class="tx-tabletemplate-menu tx-menu tx-colorpallete"
									 unselectable="on"></div>
							</li>
						</ul>

					</div></div>
					<!-- 툴바 - 더보기 끝 -->
					<!-- 편집영역 시작 -->
						<!-- 에디터 Start -->
					<div id="tx_canvas" class="tx-canvas">
						<div id="tx_loading" class="tx-loading">
							<div><img src="/daumeditor/images/icon/editor/loading2.png" width="113" height="21" align="absmiddle"/></div>
						</div>
						<div id="tx_canvas_wysiwyg_holder" class="tx-holder" style="display:block;">
							<iframe id="tx_canvas_wysiwyg" name="tx_canvas_wysiwyg" allowtransparency="true" frameborder="0"></iframe>
						</div>
						<div class="tx-source-deco">
							<div id="tx_canvas_source_holder" class="tx-holder">
								<textarea id="tx_canvas_source" rows="30" cols="30"></textarea>
							</div>
						</div>
						<div id="tx_canvas_text_holder" class="tx-holder">
							<textarea id="tx_canvas_text" rows="30" cols="30"></textarea>
						</div>
					</div>
									<!-- 높이조절 Start -->
					<div id="tx_resizer" class="tx-resize-bar">
						<div class="tx-resize-bar-bg"></div>
						<img id="tx_resize_holder" src="/daumeditor/images/icon/editor/skin/01/btn_drag01.gif" width="58" height="12" unselectable="on" alt="" />
					</div>
									<div class="tx-side-bi" id="tx_side_bi">
					</div>
								<!-- 편집영역 끝 -->
							<!-- 첨부박스 시작 -->
								<!-- 파일첨부박스 Start -->
					<div id="tx_attach_div" class="tx-attach-div">
						<div id="tx_attach_txt" class="tx-attach-txt">파일 첨부</div>
						<div id="tx_attach_box" class="tx-attach-box">
							<div class="tx-attach-box-inner">
								<div id="tx_attach_preview" class="tx-attach-preview"><p></p><img src="/daumeditor/images/icon/editor/pn_preview.gif" width="147" height="108" unselectable="on"/></div>
								<div class="tx-attach-main">
									<div id="tx_upload_progress" class="tx-upload-progress"><div>0%</div><p>파일을 업로드하는 중입니다.</p></div>
									<ul class="tx-attach-top">
										<li id="tx_attach_delete" class="tx-attach-delete"><a>전체삭제</a></li>
										<li id="tx_attach_size" class="tx-attach-size">
											파일: <span id="tx_attach_up_size" class="tx-attach-size-up"></span>/<span id="tx_attach_max_size"></span>
										</li>
										<li id="tx_attach_tools" class="tx-attach-tools">
										</li>
									</ul>
									<ul id="tx_attach_list" class="tx-attach-list"></ul>
								</div>
							</div>
						</div>
					</div>
						<!-- 첨부박스 끝 -->
				</div>
			<!-- 에디터 컨테이너 끝 -->
			</td>
		</tr>		
		<tr>
			<th>알림내용(직업소개소)</th>
			<td colspan="3">
				<textarea id="V_ALARM_CONTENT" name="V_ALARM_CONTENT" cols="5" rows="5" maxlength="1000" style="width:100%" onkeydown="javascript:OnKeyDownV_AlarmContent();"><%=data0_0.getString("V_ALARM_CONTENT")%></textarea><br/>
				<span id="ON_V_ALARM_CONTENT" style="color:red">0</span> / 최대 한글 1000byte
			</td>
		</tr>
		<tr>
			<th>알림내용(근로자)</th>
			<td colspan="3">
				<textarea id="ALARM_CONTENT" name="ALARM_CONTENT" cols="5" rows="5" maxlength="1000" style="width:100%" onkeydown="javascript:OnKeyDownAlarmContent();"><%=data0_0.getString("ALARM_CONTENT")%></textarea><br/>
				<span id="ON_ALARM_CONTENT" style="color:red">0</span> / 최대 한글 1000byte
			</td>
		</tr>
		<tr>
			<th>MMS 내용(직업소개소)</th>
			<td colspan="3">
				<textarea id="V_SMS_CONTENT" name="V_SMS_CONTENT" cols="5" rows="5" maxlength="1000"  style="width:100%" onkeydown="javascript:OnKeyV_SmsContent();"><%=data0_0.getString("V_SMS_CONTENT")%></textarea><br/>
				<span id="ON_V_SMS_CONTENT" style="color:red">0</span> / 최대 한글 1000byte<br/>
				<span style="color:red">60byte 이내일 경우 SMS로 발송됩니다. 60byte이상일 경우 LMS로 발송됩니다.</span>
			</td>
		</tr>
		<tr>
			<th>MMS 내용(근로자)</th>
			<td colspan="3">
				<textarea id="SMS_CONTENT" name="SMS_CONTENT" cols="5" rows="5" maxlength="1000"  style="width:100%" onkeydown="javascript:OnKeySmsContent();"><%=data0_0.getString("SMS_CONTENT")%></textarea><br/>
				<span id="ON_SMS_CONTENT" style="color:red">0</span> / 최대 한글 1000byte<br/>
				<span style="color:red">60byte 이내일 경우 SMS로 발송됩니다. 60byte이상일 경우 LMS로 발송됩니다.</span>
			</td>
		</tr>
		<tr>
			<th>MMS 이미지 파일</th>
			<td colspan="3">	
				
				<a class="btn03" href="javascript:OnSltMmsImage();">파일선택</a>
				<input type="text" id="SMS_FILE_ID" name="SMS_FILE_ID" style="width:100px" value="<%=data0_0.getInt("SMS_FILE_ID")%>" readonly/>
				<input type="text" id="SMS_FILE_NAME" name="SMS_FILE_NAME" style="width:200px" value="<%=data0_0.getString("SMS_FILE_NAME")%>" readonly/>
				<a class="btn03" href="javascript:OnDeleteMmsImage();">파일삭제</a>
				<br/>
				
				<span style="color:red;font-weight:bold;">1) MMS에 이미지 첨부하여 보낼 파일을 선택해주세요.</span><br/>
				<span style="color:red;font-weight:bold;">2) MMS에 이미지 첨부하여 보낼 경우 파일을 업로드해주세요. 업로드할 경우 MMS 서버에 업로드됩니다.</span><br/>
			</td>
		</tr>	
		<tr>
			<th>메시지 내용</th>
			<td colspan="3">
				<textarea id="MSG_CONTENT" name="MSG_CONTENT" cols="5" rows="5" maxlength="1000"  style="width:100%" onkeydown="javascript:OnKeyMsgContent();"><%=data0_0.getString("MSG_CONTENT")%></textarea><br/>
				<span id="ON_MSG_CONTENT" style="color:red">0</span> / 최대 한글 1000byte<br/>
				메세지 발송 직업소개소:
				<input type="text" id="MSG_CORP_NAME" name="MSG_CORP_NAME" style="width:200px" value="<%=data0_0.getString("MSG_CORP_NAME")%>" readonly/>
				<input type="hidden" id="MSG_CORP_ID" name="MSG_CORP_ID" value="<%=data0_0.getInt("MSG_CORP_ID")%>"/>
				<a class="btn03" href="javascript:OnMsgCorpUser();">직업소개소 검색</a>
			</td>
		</tr>
		<tr>
			<th>이벤트 이미지 파일</th>
			<td colspan="3">
				<input type="file" class="form-control" name="image" id="image" style="width:300px;">
			</td>
		</tr>		
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:saveContent();">저장</a>
		<a class="btn04" href="javascript:history.back(-1);">취소</a>
		<textarea id="oldcontent" style="display:none;"><%=data0_0.getString("CONTENT")%></textarea>
		<input type="hidden" name="CONTENT" id="CONTENT" value="">
		<input type="hidden" name="ID" id="ID" value="<%=data0_0.getInt("ID")%>">
	</div>
	</form>
	

</div>
<script>

var config = {
	txHost: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
	txPath: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
	txService: 'sample', /* 수정필요없음. */
	txProject: 'sample', /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
	initializedId: "", /* 대부분의 경우에 빈문자열 */
	wrapper: "tx_trex_container", /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
	form: 'frm'+"", /* 등록하기 위한 Form 이름 */
	txIconPath: "/daumeditor/images/icon/editor/", /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
	txDecoPath: "/daumeditor/images/deco/contents/", /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
	canvas: {
		styles: {
			color: "#123456", /* 기본 글자색 */
			fontFamily: "굴림", /* 기본 글자체 */
			fontSize: "10pt", /* 기본 글자크기 */
			backgroundColor: "#fff", /*기본 배경색 */
			lineHeight: "1.5", /*기본 줄간격 */
			padding: "8px" /* 위지윅 영역의 여백 */
		},
		showGuideArea: false
	},
	events: {
		preventUnload: false
	},
	sidebar: {
		attachbox: {
			show: true,
			confirmForDeleteAll: true
		}
	},
	size: {
		contentWidth: 860 /* 지정된 본문영역의 넓이가 있을 경우에 설정 */
	}
};

EditorJSLoader.ready(function(Editor) {
	var editor = new Editor(config);

	Editor.modify(
		{
			"content": $("#oldcontent").text()
		}
	);
});

function saveContent(){
	Editor.save();
}


function validForm(editor) {
	if ($("#GBN").val() == "")
	{
		alert("구분을 선택해주세요.");
		$("#GBN").focus();
		return false;
	}
	
	// 이벤트일 경우 시작일, 종료일, 이벤트 링크 값 확인
	if ($("#GBN").val() == "A2")
	{
		if ($("#S_DATE").val() == "")
		{
			alert("시작일을 입력해주세요.");
			$("#S_DATE").focus();
			return false;
		}
		if ($("#E_DATE").val() == "")
		{
			alert("종료일을 입력해주세요.");
			$("#E_DATE").focus();
			return false;
		}

	}

	if($("#TITLE").val() == ""){
		alert("제목을 입력하세요.");
		$("#TITLE").focus();
		return false;
	}

	if ($("#ALARM_CONTENT").val() != "")
	{
		if (byteCheck($("#ALARM_CONTENT").val()) > 1000)
		{
			alert("알림내용은 최대 1000byte입니다.다시 입력해주세요.");
			return false;
		}
	}

	if ($("#SMS_CONTENT").val() != "")
	{
		if (byteCheck($("#SMS_CONTENT").val()) > 1000)
		{
			alert("SMS내용은 최대 1000byte입니다.다시 입력해주세요.");
			return false;
		}
	}

	if ($("#MSG_CONTENT").val() != "")
	{
		if (byteCheck($("#MSG_CONTENT").val()) > 1000)
		{
			alert("메세지 내용은 최대 1000byte입니다.다시 입력해주세요.");
			return false;
		}
	}

	var validator = new Trex.Validator();
	var content = editor.getContent();
	
	content = content.replaceAll("'","`");

	if (!validator.exists(content)) {
		alert('내용을 입력하세요');
		return false;
	}

	var title = $("#TITLE").val();
	title = title.replaceAll("'","`");
	$("#TITLE").val(title);

	$("#CONTENT").val(content);

	return true;
}


function OnKeyDownAlarmContent()
{
	var strAlarmContent = $("#ALARM_CONTENT").val();
	$("#ON_ALARM_CONTENT").html(byteCheck(strAlarmContent));
}

function OnKeyDownV_AlarmContent()
{
	var strAlarmContent = $("#V_ALARM_CONTENT").val();
	$("#ON_V_ALARM_CONTENT").html(byteCheck(strAlarmContent));
}

function OnKeyDownSmsContent()
{
	var strSmsContent = $("#SMS_CONTENT").val();
	$("#ON_SMS_CONTENT").html(byteCheck(strSmsContent));
}


function OnKeyDownV_SmsContent()
{
	var strSmsContent = $("#V_SMS_CONTENT").val();
	$("#ON_V_SMS_CONTENT").html(byteCheck(strSmsContent));
}

function OnKeyDownMSgContent()
{
	var strMsgContent = $("#MSG_CONTENT").val();
	$("#ON_MSG_CONTENT").html(byteCheck(strMsgContent));
}

OnKeyDownAlarmContent();
OnKeyDownSmsContent();
OnKeyDownMSgContent();


function byteCheck(el){
    var l= 0;
     
    for(var idx=0; idx < el.length; idx++) {
        var c = escape(el.charAt(idx));
         
        if( c.length==1 ) l ++;
        else if( c.indexOf("%u")!=-1 ) l += 2;
        else if( c.indexOf("%")!=-1 ) l += c.length/3;
    }
     
    return l;
}


function SetUserId(jsonResult)
{
	var jsonData = JSON.parse(jsonResult);

	$("#MSG_CORP_ID").val(jsonData.CORP_ID);
	$("#MSG_CORP_NAME").val(jsonData.CORP_NAME);
}

function OnMsgCorpUser()
{
	var url = "/v_req_slt_corp_user/info";
	OpenWin(url,"v_req_slt_corp_user",1024,700);
}

function OnSltMmsImage()
{
	document.domain ="veteranscout.co.kr";
	var url = "http://file.veteranscout.co.kr/slt_mms_image.asp"
	OpenWin(url,"slt_mms_image",1024,700);
}


function OnDeleteMmsImage()
{
	$("#SMS_FILE_ID").val("0");
	$("#SMS_FILE_NAME").val("");
}

function OnSltMmsImageUpload()
{
	var url = "http://file.veteranscout.co.kr/slt_mms_image_file_upload.asp"
	OpenWin(url,"slt_mms_image_file_upload",1024,700);
}

</script>
<!-- contents -->
<script type="text/javascript">
	function OnCancel(aID)
	{
		document.location.href = "/admin_cms_board_info/info/"+ aID;
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

	function OnGbn(objGBN)
	{
		
		if (objGBN.value =="A1")
		{
			// 공지사항
			$("#event_date").css("display","none");
		}
		else if (objGBN.value =="A2")
		{
			// 이벤트
			$("#event_date").css("display","");
		}
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	