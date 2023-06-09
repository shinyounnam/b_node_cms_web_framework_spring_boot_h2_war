<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>
<link rel="stylesheet" href="/daumeditor/css/editor.css" type="text/css" charset="utf-8"/>
<script src="/daumeditor/js/editor_loader.js?environment=production" type="text/javascript" charset="utf-8"></script>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    String ACTION = header.getString("ACTION");
    int i = 0;
    JSONObject obj = null;
    String GBN = "";
    String NAME = "";
    String TITLE = "";
    String CONTENT = "";
    JSONArray data1 = data.getJSONArray(0);
    if(data1.length() > 0){
        obj = data1.getJSONObject(0);
        GBN = obj.getString("GBN");
        NAME = obj.getString("NAME");
        TITLE = obj.getString("TITLE");
        CONTENT = obj.getString("CONTENT");
    }
    int ID = header.getInt("ID");
%>
<!-- contents -->
<div class="contents">
	<h2>자주 묻는 질문</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">자주 묻는 질문</span></div>
	<div class="pt30"></div>
	<% if (ACTION.equals("INSERT")) { %>
	    <form name="frm" method="post" action="/admin_cms_faq_insert/insert_submit" enctype="multipart/form-data">
	<% } else if (ACTION.equals("UPDATE")) { %>
	    <form name="frm" method="post" action="/admin_cms_faq_insert/update_submit" enctype="multipart/form-data">
	<% } %>
	<table class="list_A" summary="">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>구분</th>
			<td style="text-align:left">
				<select id="GBN" name="GBN" style="width:200px">
					<option value="">선택</option>
					<%                  
					data1 = data.getJSONArray(1);
					for (i=0;i<data1.length();i++) {
                        obj = data1.getJSONObject(i);
						if (GBN.equals(obj.getString("CODE"))) {
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
			<th>작성자</th>
			<td style="text-align:left">
				<%=NAME%>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td colspan="3" style="text-align:left">
				<input type="text" id="TITLE" name="TITLE" maxlength="100" style="width:300px" value="<%=TITLE%>"/>
			</td>
		</tr>
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
			<th>첨부파일</th>
			<td colspan="3">
				<input type="file" class="form-control" name="image" id="image" style="width:300px;">
			</td>
		</tr>
		
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:saveContent();">저장</a>
		<a class="btn04" href="javascript:history.back(-1);">취소</a>
		<textarea id="oldcontent" style="display:none;"><%=CONTENT%></textarea>
		<input type="hidden" name="CONTENT" id="CONTENT" value="<%=CONTENT%>">
		<input type="hidden" name="ID" id="ID" value="<%=ID%>">
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

	if($("#TITLE").val() == ""){
		alert("제목을 입력하세요.");
		$("#TITLE").focus();
		return false;
	}

	var validator = new Trex.Validator();
	var content = editor.getContent();
	if (!validator.exists(content)) {
		alert('내용을 입력하세요');
		return false;
	}

	$("#CONTENT").val(content);

	return true;
}


</script>
<!-- contents -->

<script type="text/javascript">
	function OnCancel(aID)
	{
		document.location.href = "/admin_cms_faq_info/info/"+ aID;
	}
</script>




<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	