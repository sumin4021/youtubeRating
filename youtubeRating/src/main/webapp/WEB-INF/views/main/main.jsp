<%--
	main
--%>
<%@page import="smp.ytr.util.CommonUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>

<jsp:include page="../common/header.jsp" flush="true" />

<% 

/*social login result*/
String social_login = CommonUtil.URLdecode(request.getParameter("social_login"));
String social_res;
if(social_login != null) {
	String social_login_param[] = social_login.split(";");
	if(social_login_param.length > 1) { //1보다 크면 error 혹은 fail
		social_res = social_login_param[1];
	%>
	<script type="text/javascript">
		fn_toast('<%=social_res%>');
	</script>		
	<%
	}
}
%>

<!-- 메인화면 swiper -->
<link href="/resources/otherLib/css/swiper.min.css" rel="stylesheet" type="text/css">
	
<header class="masthead">
	<div class="container">
		<div class="intro-text">
			<div class="intro-lead-in">''를 검색해보세요!</div>
			<!-- <div class="intro-heading text-uppercase">It's Nice To Meet You</div> -->
			<div class="search input-group">
				<input type="text" class="form-control" id="main_search" placeholder="channel, youtuber, streamer"/>
				<span class="input-group-btn">
					<button class="btn btn-search" type="button" id="main_search_btn" onclick="javascript:fn_main_search()"><i class="fa fa-search fa-fw"></i> Search</button>
				</span>
			</div>
		</div>
	</div>
</header>


<!-- hot -->
<section class="page-section" id="section_hot">
	<div class="">
		<div class="text-light border-bottom p-3">
			<span class="section-heading text-uppercase">hot video</span>
		</div>
		<div class="text-light border-bottom pl-3 pr-3">
			<div class="swiper-container">
				<div class="swiper-wrapper">
				<c:forEach items="${hotVideo}" var="img">
					<div class="swiper-slide"><img src="${img.IMG_SRC}" alt="" style="width:100%"></div>
				</c:forEach>
				</div>
				<!-- Add Pagination -->
				<div class="swiper-pagination"></div>
			</div>
		</div>
	</div>
</section>
  
  
<!-- board -->
<section class="page-section" id="section_board">
	<div class="container text-light ">
		<div class="text-light border-bottom p-3">
			<span class="section-heading text-uppercase">인기 게시글<span class="float-right text-lowercase"><a href="/board">more...</a></span></span>
		</div>
		<div class="row text-left p-3">
			<div class="col-6 p-3 border-bottom">
				<h5 class="">1.</h5>
				<h5 class="">2.</h5>
				<h5 class="">3.</h5>
				<h5 class="">4.</h5>
				<h5 class="">5.</h5>
			</div>
			<div class="col-6 p-3 border-bottom">
				<h5 class="">6.</h5>
				<h5 class="">7.</h5>
				<h5 class="">8.</h5>
				<h5 class="">9.</h5>
				<h5 class="">0.</h5>
			</div>
		</div>
	</div>
</section>



<script src="/resources/otherLib/js/swiper.min.js" type="text/javascript"></script>
<script src="/resources/js/main_script.js" type="text/javascript"></script>


<jsp:include page="../common/footer.jsp" flush="true" />
