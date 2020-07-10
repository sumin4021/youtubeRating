	

	/** 엔터키 **/
	$(document).ready(function(){
		// chrome samesite대비 수정.
		document.cookie = 'cross-site-cookie=bar; SameSite=None; Secure';
		
		$("#user_id").keypress(function(e) { if(e.keyCode == 13) fn_login(); });
		$("#user_pw").keypress(function(e) { if(e.keyCode == 13) fn_login(); });
		$("#main_search").keypress(function(e) {
			if($("#main_search_btn").attr("disabled") != "disabled"){
				if(e.keyCode == 13) fn_main_search(); 
			}
		});

	});
	
	
	/** TOAST **/
	var toast_cnt = 0;
	function fn_toast(msg) {
		if(toast_cnt == 0){
			var x = document.getElementById("snackbar");
			x.innerHTML = msg;
			x.className = "show";
			var timer = setTimeout(function(){ 
				x.className = x.className.replace("show", "");
				toast_cnt = 0;
			}, 1800);
		}
		toast_cnt++;
	}	
	
	/** LOADING **/
	var loading_cnt = 0;
	function fn_loading_show() {
		$("#main_search").attr("disabled",true);
		$("#main_search_btn").attr("disabled",true);
		if(loading_cnt == 0){
			var x = document.getElementById("loading");
			var y = document.getElementById("loading-title");
			x.className = "show";
			y.className = "show";
			loading_cnt = 0;
		}
		loading_cnt++;
	}
	function fn_loading_hide() {
		$("#main_search").attr("disabled",false);
		$("#main_search_btn").attr("disabled",false);
		var x = document.getElementById("loading");
		var y = document.getElementById("loading-title");
		x.className = "hide";
		y.className = "hide";
		loading_cnt = 0;
	}
	/** 랜덤문자열 가져와서뿌림. **/
	function fn_loading_title_changer(){
		var myLoadingMsg = ["로딩중입니다!","now Loading . . .", "로딩중이랑께!","loading!","기다리슈~","대기."];
		var rnum = Math.floor(Math.random() * myLoadingMsg.length);
		var x = document.getElementById("loading-title");
		x.innerHTML = myLoadingMsg[rnum];
	}
	
	

	/** POST_TO_URL- js만으로 post방식 전달가능. **/
	function post_to_url(path, params, method) {
		
	    method = method || "post"; 
	    var form = document.createElement("form");
	    form.setAttribute("method", method);
	    form.setAttribute("action", path);
	    for(var key in params) {
	        var hiddenField = document.createElement("input");
	        hiddenField.setAttribute("type", "hidden");
	        hiddenField.setAttribute("name", key);
	        hiddenField.setAttribute("value", params[key]);
	        form.appendChild(hiddenField);
	    }
	    document.body.appendChild(form);
	    form.submit();
	}

	
	
	/*패스워드 해싱*/
	function fn_pw_encryto_SHA256(pwassword){
		return CryptoJS.SHA256(pwassword);
	}
	
	function fn_isEmail(value) {
		var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		return regExp.test(value);
	}
	
	function fn_login_validation(id, pw){
		
		if (id.length == 0) {
			fn_toast("아이디를 입력해주세요");
			return false;
		}
		if(!fn_isEmail(id)){
			fn_toast("아이디는 이메일형식 이어야합니다.");
			return false;
		}
		if(pw.indexOf(id) > -1) { 
			fn_toast("패스워드는 ID를 포함할 수 없습니다."); 
			return false;
		} 
		if (pw.length == 0) {
			fn_toast("패스워드를 입력해주세요");
			return false;
		}
		return true;
	}
	
	
	
	
	/**
	 * 문자열이 빈 문자열인지 체크하여 결과값을 리턴한다.
	 * 
	 * @param str : 체크할 문자열
	 */
    function isEmpty(str){
        if(typeof str == "undefined" || str == null || str == "" || str == 'null')
            return true;
        else
            return false ;
    }
     
    /**
	 * 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다.
	 * @param str : 체크할 문자열
	 * @param defaultStr : 문자열이 비어있을경우 리턴할 기본 문자열
	 */
    function nvl(str, defaultStr){
         
        if(typeof str == "undefined" || str == null || str == "")
            str = defaultStr ;
         
        return str ;
    }
    
	

	/** 로그인 **/
	function fn_login(){
		var user_id = $("#user_id").val();
		var user_pw = $("#user_pw").val();
		
		/*로그인 유효성 검증*/
		if(fn_login_validation(user_id, user_pw)){
			
			$.ajax({
				url : "login",
				type : "POST",
				data : { ID: user_id, PW: ''+fn_pw_encryto_SHA256(user_pw) },
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				dataType : "json",
				success : function(data) {
					if(data.isLogin === 's'){
						location.reload();
					}else if(data.isLogin === 'f'){
						fn_toast("아이디 또는 패스워드를 확인해주세요.");
					}
				},
				error : function(data) {
					console.log("err"+data);
				}
			});
		}
	}	
	
	/** 로그아웃 **/
	function fn_logout(nick){
		var nowURL = window.location.pathname;
		post_to_url('logout', {nick, nowURL});//, {'q':'a'});
	}
