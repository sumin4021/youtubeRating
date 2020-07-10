<%--
	header
--%>
<%@page import="smp.ytr.dao.vo.UserVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>

<%
	UserVO userInfoVO = (UserVO)session.getAttribute("session_user_info");
%>


<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8" >
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<title>Youtube Rating</title>

	<!-- bootstrap -->
	<link href="/resources/otherLib/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="/resources/otherLib/css/all.min.css" rel="stylesheet" type="text/css"/>
	<link href="/resources/otherLib/css/fontawesome.min.css" rel="stylesheet" type="text/css"/>
	
	<!-- Custom fonts -->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>
	
	<!-- Custom css -->
	<link href="/resources/css/common.css" rel="stylesheet" type="text/css">
	<link href="/resources/css/common_574.css" rel="stylesheet" type="text/css">
	<link href="/resources/css/common_768.css" rel="stylesheet" type="text/css">
	<link href="/resources/css/nav.css" rel="stylesheet" type="text/css">

	<!-- jquery -->
	<script src="/resources/otherLib/js/jquery.min.js" type="text/javascript"> </script>
	
	<!-- bootstrap -->
	<script src="/resources/otherLib/js/bootstrap.min.js" type="text/javascript"> </script>
	<script src="/resources/otherLib/js/bootstrap.bundle.min.js" type="text/javascript"> </script>
	<script src="/resources/otherLib/js/all.min.js" type="text/javascript"> </script>
	<script src="/resources/otherLib/js/pooper.min.js" type="text/javascript"> </script>
	
	<!-- 로그인용 js Crypto -->
	<script src="/resources/otherLib/js/crypto_sha256.js"></script>

	<!-- common script -->
	<script src="/resources/js/cmmn_script.js"></script>
	
	<!-- youtube css -->
	<script src="https://apis.google.com/js/platform.js"></script>
</head>


<!-- custom alert -->
<div id="snackbar"></div>

<!-- loading -->
<div id="loading"></div>
<div id="loading-title-wrap"> <div id="loading-title"></div> </div>

<script type="text/javascript">
	history.replaceState({}, null, location.pathname);
</script>


<body id="page-top">
	
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
		<div class="container">
			<a class="navbar-brand js-scroll-trigger" href="/">Youtube Rating</a>
			<% if(userInfoVO!=null){ %>
			<span class="navbar-nickname">반가워요 "<a class="navbar-nickname-link" href="javascript:fn_toast('준비중입니다.!')"><%=userInfoVO.getNICK_NAME()%></a>"</span> 
			<% } %>
			
			<ul class="navbar-nav text-uppercase ml-auto" id="login_view">
				<li class="nav-item"><a class="nav-link" href="/board">board</a></li>
				<li class="nav-item"><a class="nav-link" href="javascript:fn_toast('준비중입니다.!')">statis</a></li>
	
				<% if(userInfoVO==null){ %>
				<li class="nav-item"><a class="nav-link" href="#myModal" data-toggle="modal" data-target="#myModal">login</a></li>
				<% }else{ %>
				<li class="nav-item"><a class="nav-link" href="javascript:fn_logout('<%=userInfoVO.getID()%>');">logout</a></li>
				<% } %>
				
			</ul>
		</div>
	</nav>


	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">로그인!!!!!!</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="">
			
						<input type="text" id="user_id" placeholder="email"/>
						<input type="password" id="user_pw" placeholder="pw" pattern="[A-Za-z0-9]*"/>
						<button class="btn btn-login" type="button" onclick="javascript:fn_login();">LOGIN</button>
						
					</div>
					
					<div class="join-us">
						<button class="btn btn-join" onclick="javascript:location.href='/join';">JOIN US</button>
					</div>
					
					<div class="social-login">
						<span>소셜로그인.</span>
						
						<br/>
						<!-- KAKAO
						<a id="kakao_login" href="https://kauth.kakao.com/oauth/authorize?
									client_id=ad7f2fd636fc8f915a672681570571af&
									redirect_uri=http://localhost/social/kakao&
									response_type=code"> 
							<img src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg" width="222" />
						</a>
						 -->
						<br/>
						<!-- GOOGLE -->
						<a id="google_login" href="https://accounts.google.com/o/oauth2/v2/auth?
									scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile&
									access_type=offline&
									include_granted_scopes=true&
									response_type=code&
									state=state_parameter_passthrough_value&
									redirect_uri=http://localhost/social/google&
									client_id=943953451988-s4dotjeqoclmas61kqrr3njdtl2tfo2c.apps.googleusercontent.com">
							<img src="//developers.google.com/identity/images/btn_google_signin_light_normal_web.png" width="222">
						</a>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	
	