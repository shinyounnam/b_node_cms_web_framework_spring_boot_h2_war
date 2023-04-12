var j_xls_web_url = "http://xls.veteranscout.co.kr/cms_web";

// 팝업 띄우기 - 경로, 이름, 너비, 높이
function OpenWin(Url, Name, Width, Height){
	window.open(Url,Name,"width="+Width+",height="+Height+",scrollbars=yes,resizeable=no,left=150,top=150");
}

function OpenPostWin(Url,Name,Width,Height,frm) {
	var status =  "toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width="+Width+", height="+Height+", top=0,left=20"; 
	window.open("",Name, status);
	frm.target = Name;
	frm.action = Url;
	frm.method = "post";
	frm.submit();
}


function SetOnlyNumber(aId)
{
	$("#"+aId).keyup(function(event){
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^0-9]/gi,''));
		}
	});
}

function SetOnlyAlphabet(aId)
{
	$("#"+aId).keyup(function(event){
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^a-z]/gi,''));
		}
	});
}

function SetNotHangul(aId)
{
	$("#"+aId).keyup(function(event){
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
		}
	});
}

function SetOnlyHangul(aId)
{
	$("#"+aId).keyup(function(event){
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[a-z0-9]/gi,''));
		}
	});
}






// 숫자만 입력
function chkNumber(event){
    var key = window.event ? event.keyCode : event.which;

    if ((event.shiftKey == false) && ((key  > 47 && key  < 58) || (key  > 95 && key  < 106)
    || key  == 35 || key  == 36 || key  == 37 || key  == 39 || key  == 8  || key  == 46) || (key == 9)){
        return true;

    }else {
        return false;
    }
}

function IsNull(aID)
{
	var obj = "NOT NULL";
	if(typeof $(aID).val() == "undefined")
	{
		obj = null;
	}
	return obj;
}

// 사업자 등록번호 검사
function CheckBizID(pID){
	var CheckID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
	var i, strTemp, ChkSum = 0, ChkDigit;
	pID = pID.replace(/-/gi,'');
	for (i=0; i<=7; i++) ChkSum += CheckID[i] * pID.charAt(i);
	strTemp = "0" + (CheckID[8] * pID.charAt(8));
	strTemp = strTemp.substring(strTemp.length - 2, strTemp.length);
	ChkSum += Math.floor(strTemp.charAt(0)) + Math.floor(strTemp.charAt(1));
	ChkDigit = (10 - (ChkSum % 10)) % 10 ;
	if (Math.floor(pID.charAt(9)) == ChkDigit) return true ; // OK!
	return false;
}

function PageMake(pPage, pPageSize, pPageCount) {
	
	var Body, i;
	var nPageStart, nPageEnd;
	pPage = parseInt(pPage);
	pPageSize = parseInt(pPageSize);
	pPageCount = parseInt(pPageCount);

	//페이지 시작
	var nPageStart = parseInt(pPage/pPageSize)*pPageSize + 1;

	if (nPageStart - pPage > 0) {
		nPageStart = nPageStart - pPageSize;
	}

	//페이지 마지막
	nPageEnd = nPageStart+pPageSize-1

	if (nPageEnd >= pPageCount) {
		nPageEnd = parseInt(pPageCount);
	}

	Body = "<div class='paging'>";
	
	if (nPageStart < 2) {
		//첫번째 페이지일 경우 링크 없음
		Body = Body + "<img src='/images/btn_first.png' alt='처음으로' style='padding-right:3px;' />";
		Body = Body + "<img src='/images/btn_pre.png' alt='이전' />&nbsp;";
	}else
	{
		Body = Body + "<a href='javascript:OnPage("+1+")'><img src='/images/btn_first.png' alt='처음으로' style='padding-right:3px;' /></a>";
		Body = Body + "<a href='javascript:OnPage("+(nPageStart-pPageSize)+")'><img src='/images/btn_pre.png' alt='이전' /></a>&nbsp;";
	}
	

	for (i = nPageStart;i<=nPageEnd;i++) { 
		if (parseInt(pPage) == i) {
			//동일 페이지일 경우 링크 없음
			Body = Body + "<a class='on'>"+i+"</a>&nbsp;";
		}else{
			Body = Body + "<a href='javascript:OnPage("+i+")'>"+i+"</a>&nbsp;";
		}
	}

	if (pPageCount == nPageEnd)
	{
		//마지막 페이지일 경우 링크 없음
		Body = Body + "<img src='/images/btn_next.png' alt='다음' style='padding-right:3px;' />";
		Body = Body + "<img src='/images/btn_last.png' alt='끝으로' />";
	}
	else 
	{
		Body = Body + "<td><a href='javascript:OnPage("+(nPageStart+pPageSize)+")'><img src='/images/btn_next.png' alt='다음' style='padding-right:3px;' /></a></td>";
		Body = Body + "<td><a href='javascript:OnPage("+pPageCount+")'><img src='/images/btn_last.png' alt='끝으로' /></a></td>";
	}
	
	Body = Body + "</div>";
	return Body;
}

function getCookie(cookieName)
{
	var search = cookieName + "=";
	var cookie = document.cookie;
	// 현재 쿠키가 존재할 경우
	if( cookie.length > 0 )
	{
		// 해당 쿠키명이 존재하는지 검색한 후 존재하면 위치를 리턴.
		startIndex = cookie.indexOf( cookieName );
		// 만약 존재한다면
		if( startIndex != -1 )
		{
			// 값을 얻어내기 위해 시작 인덱스 조절
			startIndex += cookieName.length;
			// 값을 얻어내기 위해 종료 인덱스 추출
			endIndex = cookie.indexOf( ";", startIndex );
			// 만약 종료 인덱스를 못찾게 되면 쿠키 전체길이로 설정
			if( endIndex == -1) endIndex = cookie.length;
			// 쿠키값을 추출하여 리턴
			return unescape( cookie.substring( startIndex + 1, endIndex ) );
		}
		else
		{
			// 쿠키 내에 해당 쿠키가 존재하지 않을 경우
			return false;
		}
	}
	else
	{
		// 쿠키 자체가 없을 경우
		return false;
	}
}


function GetNowTime()
{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10)dd='0'+dd;
	if(mm<10)mm='0'+mm;
	var hh = today.getHours();
	if (hh<10) hh='0'+hh;
	var mi = today.getMinutes();
	if (mi<10) mi='0'+mi;
	var ss = today.getSeconds();
	if (ss<10) ss='0'+ss;
	var strTime = ''+yyyy+'-'+mm+'-'+dd+' '+hh+':'+mi+':'+ss+'';

	return strTime;
}

function GetNowDate()
{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10)dd='0'+dd;
	if(mm<10)mm='0'+mm;
	var strTime = ''+yyyy+'-'+mm+'-'+dd;

	return strTime;
}