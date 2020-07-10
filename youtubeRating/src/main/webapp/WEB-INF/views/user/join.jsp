<%--
	join
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<jsp:include page="../common/header.jsp" flush="true" />
  	
  	<%--
  	//TODO 
  	
  	1. email 형태 확인. ok
  	2. email 중복 확인. ok
  	3. 패스워드 안정성 검사. ok
  	4. 패스워드 확인 ok
  	5. 닉네임 중복체크, 길이검사 ok
  	6. 봇 검사를 위한......자동 가입 방지 문자 입력? later 
  	7. 소셜 로그인! 페북, 카카오톡, 지메일 
	  	
  	 --%>
  	
<!-- join -->

<div class="containerWrap">

	<section class="container advertise">광go</section>

	<section class="page-section">
		<div class="container">

			<table id="joinTable" class="display" style="color: white; width: 100%">
				<colgroup>
					<col style="width: 15%">
					<col style="width: *">
					<col style="width: 20%">
				</colgroup>

				<tbody id="joinDone" style="display: none;">
					<tr>
						<td>
							<span>회원가입 성공!<br/>잠시 후 홈화면으로 이동합니다.</span>
						</td>
					</tr>
				</tbody>
				<tbody id="joinPage">
					<tr>
						<th>이메일</th>
						<td><input type="text" id="txtId" maxlength="30" /> <span class="" id="email_valid"></span></td>
						<td><button class="" id="emailOverlapBtn" onclick="javascript:fn_overlap_chk('email');">중복확인</button></td>
					</tr>

					<tr>
						<th>패스워드</th>
						<td colspan="2"><input type="password" id="txtPassword1" /></td>
					</tr>

					<tr>
						<th>패스워드 확인</th>
						<td colspan="2"><input type="password" id="txtPassword2" /> <span class="" id="pwd_valid"></span></td>
					</tr>

					<tr>
						<th>닉네임</th>
						<td><input type="text" id="txtNickName" maxlength="10" /> <span class="" id="nick_valid"></span></td>
						<td>
							<button class="" onclick="javascript:fn_rand_nick();">랜덤생성</button>
							<button class="" id="nickOverlapBtn" onclick="javascript:fn_overlap_chk('nickname');">중복확인</button>
						</td>
					</tr>

					<tr>
						<td colspan="3">
							<button class="" id="btn_join_submit" onclick="javascript:fn_confirm_join();">가입하기</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>

	<section class="container advertise">광고</section>
	
</div>

<!-- js Crypto -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/pbkdf2.js"></script>
<script src="/resources/js/join_script.js" type="text/javascript">
	
</script><%-- 공통 스크립트 리스트 --%>
<script>
	var iv = CryptoJS.enc.Hex.parse('${ivKey}');
	var salt = CryptoJS.enc.Hex.parse('${saltKey}');
	var passPhrase = '${key}';

	function fn_confirm_join() {
		$("#btn_join_submit").attr("disabled", true);

		if (fn_join_validation()) {

			var encPw = fn_pw_encryto_SHA256($("#txtPassword1").val());
			var encPwUseValidation = fn_pw_encryto_PBKDF2($("#txtPassword1").val(), iv);

			$.ajax({
						url : "pwChkAndJoin",
						type : "POST",
						data : {
							ID : $("#txtId").val(),
							PW : encPw + '',
							PW_VALIDATION : encPwUseValidation + '',
							NICK_NAME : $("#txtNickName").val(),
							KEY : passPhrase + '',
							ivKey : iv + '',
							saltKey : salt + '',
							AGE : '',
							GENDER : ''
						},
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						dataType : "json",
						success : function(data) {
							if (data.id_msg != "") {
								fn_toast(data.id_msg);
							} else if (data.pw_msg != "") {
								fn_toast(data.pw_msg);
							} else {
								if (data.join_result == "fail") {
									fn_toast("회원가입 실패");
								} else if (data.join_result == "suc") {
									//TODO 회원가입 성공.
									$("#joinPage").css("display","none");
									$("#joinDone").css("display","block");
									setTimeout(() => { location.href="/"; }, 1500);
								} else {
									//TODO 회원가입 에러
									fn_toast(data.join_result);
								}
							}
						},
						error : function(data) {
							console.log(data);
						},
						complete : function() {
							$("#btn_join_submit").attr("disabled", false);
						}
					});

		}
		$("#btn_join_submit").attr("disabled", false);
	}
</script>


<jsp:include page="../common/footer.jsp" flush="true" />
