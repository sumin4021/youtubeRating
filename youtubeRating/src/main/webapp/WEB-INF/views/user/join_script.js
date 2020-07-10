	
	/**/
	$("#txtId").keyup(function(event){
		$("#emailOverlapBtn").attr("disabled",false);
		$("#email_valid").html("이메일 형식이 아닙니다.");
		if(fn_isEmail($(this).val())){
			$("#email_valid").html("");
		}
		if(fn_email_vaildation($(this).val())){
			$("#email_valid").html("너무 깁니다!");
		}
	});
	
	$("#txtPassword2").keyup(function(){
		if($("#txtPassword1").val() != $("#txtPassword2").val()){
			$("#pwd_valid").html("일치하지 ㅇ낳습니다.");
		}else{
			$("#pwd_valid").html("");
		}
	});
	
	$("#txtNickName").keyup(function(){
		$("#nickOverlapBtn").attr("disabled", false);
		if(fn_nick_vaildation($(this).val())){
			$("#nick_valid").html("너무 깁니다!");
		}
	});
	/**/	
	
	
	
	
	/**/
	function fn_isEmail(value) {
		var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		return regExp.test(value);
	}
	
	function fn_email_vaildation(value){
		if( value.length > 30 ) return true;
		else return false;
	}
	
	function fn_nick_vaildation(value) {
		if( value.length > 10 ) return true;
		else return false;
	}

	
	
	
	function fn_join_validation() {
		
		var id 	= $("#txtId").val(); 
		var pw 	= $("#txtPassword1").val();
		var pw2 	= $("#txtPassword2").val();
		var nick	= $("#txtNickName").val();
		var olBtn_email	= $("#emailOverlapBtn").attr("disabled");
		var olBtn_nick 	= $("#nickOverlapBtn").attr("disabled");
		
		if (id.length == 0) {
			fn_toast("아이디를 입력해주세요");
			return false;
		}
		if (nick.length == 0) {
			fn_toast("닉네임을 입력해주세요");
			return false;
		}else if($("#nick_valid").html() != ""){
			fn_toast($("#nick_valid").html());
			return false;
		}
		if ($("#email_valid").html() != ""){
			fn_toast($("#email_valid").html());
			return false;
		} 
		if(pw.indexOf(id) > -1) { 
			fn_toast("패스워드는 ID를 포함할 수 없습니다."); 
			return false;
		} 
		if (pw.length == 0) {
			fn_toast("패스워드를 입력해주세요");
			return false;
		} else {
			if (pw != pw2) {
				fn_toast("패스워드가 일치하지 않습니다.");
				return false;
			}
		}
		if(olBtn_email == undefined || olBtn_nick == undefined){
			fn_toast("중복확인 버튼을 눌러주세요.");
			return false;
		}
		
		return true;
	}
	
	
	
	function fn_pw_encryto_PBKDF2(password,iv) {
		
		var key128Bits = CryptoJS.PBKDF2(passPhrase, salt, {
			keySize: 128 / 32,
			iterations: 1000
		});
		var encrypted = CryptoJS.AES.encrypt(
			password,
			key128Bits, { 
			iv: iv
		});
		return encrypted.toString();
	}
	
	function fn_pw_encryto_SHA256(pwassword){
		return CryptoJS.SHA256(pwassword);
	}
	
	
	
	
	/* ovrelap check */
	function fn_overlap_chk(value){
		
		var putData ;
		if(value == "email"){
			if($("#txtId").val() == ""){fn_toast("이메일 값을 채워주세요."); return false;}
			if($("#email_valid").html() != ""){ fn_toast($("#email_valid").html()); return false; } 
			putData = {ID: $("#txtId").val(), PLATFRM : 'H'}
		}
		else if(value == "nickname"){
			if($("#txtNickName").val() == ""){fn_toast("닉네임 값을 채워주세요."); return false;}
			if($("#nick_valid").html() != ""){ fn_toast($("#nick_valid").html()); return false; } 
			putData = {NICK_NAME : $("#txtNickName").val(), PLATFRM : 'H'}
		}
		
		$.ajax({
			url : "overlapCheck",
			type : "POST",
			data : putData,
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			dataType : "json",
			success : function(data) {
				if(data.email != undefined && data.nick == undefined){
					if(data.email != 'ok' && data.email == 'overlap') {
						fn_toast("중복된 이메일이 있습니다.");
					} else {
						fn_toast("사용가능합니다.");
						$("#emailOverlapBtn").attr("disabled", true);
					}
				}else {
					if(data.nick != 'ok' && data.nick == 'overlap') {
						fn_toast("중복된 닉네임이 있습니다.");
					} else {
						fn_toast("사용가능합니다.");
						$("#nickOverlapBtn").attr("disabled", true);
					}
				}
			},
			error : function(data) {
				console.log(data);
			}
		});
	}
	
	
	/* random nick maker */
	function fn_rand_nick(){
		$.ajax({
			url : "randNickMaker",
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			dataType : "json",
			success : function(data) {
				$("#nickOverlapBtn").attr("disabled", false);
				if(data.nick!=undefined || data.nick!="" || data.nick!=null){
					$("#txtNickName").val(data.nick);
				}
			},
			error : function(data) {
				console.log(data);
			}
		});
	}
	/**/
