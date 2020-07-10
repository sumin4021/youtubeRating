<%--
	main_search
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true"%>
<% pageContext.setAttribute("newLineChar", "\n"); %>

<jsp:include page="../common/header.jsp" flush="true" />


<!-- 메인화면 swiper -->
<link href="/resources/otherLib/css/swiper.min.css" rel="stylesheet" type="text/css">
	
<header class="masthead">
	<div class="container">
		<div class="intro-text">
			<div class="intro-lead-in">''를 검색해보세요!</div>
			<div class="search input-group">
				<input type="text" class="form-control" id="main_search" placeholder="channel, youtuber, streamer" value="${schKeyword}"/>
				<span class="input-group-btn">
					<button class="btn btn-search" type="button" id="main_search_btn" onclick="javascript:fn_main_search()"><i class="fa fa-search fa-fw"></i> Search</button>
				</span>
			</div>
		</div>
	</div>
</header>

<section class='page-section' id='section_board'> 
	<div class='container text-light '> 
		<div class='text-light border-bottom p-3'> 
			<span class='section-heading text-uppercase'>검색결과</span> 
		</div>
		
		<c:forEach items="${streamers}" var="itm" varStatus="vs"> 
		
		<!-- loof start -->
		<div class='row sch_result' onclick='javascript:fn_streamer_detail(this)'>
			<div class='col-4 col-sm-3 v-middle'>
				<div class='thumbnail_wrap'>
					<img class='rounded-circle' alt='' src='${itm.THUMB}'>		
				</div>
			</div>
			<div class='col-8 col-sm-3 info_wrap'> 
				<div class='row title_wrap'> 
					<div class='col'>
					${itm.CHANNEL_TITLE}
					</div> 
				</div> 
				
				<c:if test="${itm.IS_HIDE == 0}"> <%-- 구독자 수 비공개 아닐경우 == 0 --%>
				<div class='row '> 
					<div class='col'>   
						<div class='cnt_tit'> 현재 구독자 : </div>
						<span class='cnt_num'> ${itm.SUBSC_CNT} </span>
					</div> 
				</div>
				</c:if>
				 
				<div class='row '> 
					<div class='col'>   
						<div class='cnt_tit'> 비디오 : </div>
						<span class='cnt_num'> ${itm.VIDEO_CNT} </span>
					</div> 
				</div> 
				
				<div class='row pb-4'> 
					<div class='col'>   
						<div class='cnt_tit'> 총 조회수 : </div>
						<span class='cnt_num'> ${itm.VIEW_CNT} </span>
					</div> 
				</div> 
				
				<div class='row subscribe_wrap'> 
					<div class='col'> 
						<a href='https://www.youtube.com/channel/${itm.CHANNEL_ID}?sub_confirmation=1'><img class='subscribe_btn' alt='' src='resources/img/subscribe.png'></img></a> 
					</div> 
				</div> 
			</div> 
			<div class='col-sm-6 status_wrap'> 
				<canvas ></canvas>  
			</div> 
			
			<div class='col-lg-12 sch_result_detail' > 
				<div class='row '> 
					<div class='col-lg-12 recent_video'> 
						<div class='text-light border-bottom p-3'> 
							<span class='section-heading text-uppercase'>채널 설명</span> 
						</div> 
						<div class='description'>
							<div class='description_detail'>
								${fn:replace(itm.CHANNEL_DESC, newLineChar, "<br/>")}
							</div>
						</div>
						<!-- 
						<div class='d-swiper-container mt-3'> 
							<div class='swiper-wrapper'>
								<div class='swiper-slide'><img src='/resources/img/subscribe.png' alt='' style='width:100%'></div> 
								<div class='swiper-slide'><img src='/resources/img/subscribe.png' alt='' style='width:100%'></div> 
								<div class='swiper-slide'><img src='/resources/img/subscribe.png' alt='' style='width:100%'></div> 
								<div class='swiper-slide'><img src='/resources/img/subscribe.png' alt='' style='width:100%'></div> 
								<div class='swiper-slide'><img src='/resources/img/subscribe.png' alt='' style='width:100%'></div> 
							</div> 
							<div class='d-swiper-pagination text-center' ></div> 
						</div> 
						-->
					</div> 
				</div> 
				
				<div class='row'> 
					<div class=''> 
					</div> 
				</div> 
				
			</div>
			 
			 
			 <!-- HIST DIVS -->
			 <input type="hidden" class="subsc_hist" value="${itm.SUBSC_HIST}">
			 <input type="hidden" class="video_hist" value="${itm.VIDEO_HIST}">
			 <input type="hidden" class="view_hist" value="${itm.VIEW_HIST}">
			 
		</div>
		<!--// loof -->
		
		</c:forEach>
		
	</div>
</section>

<script src="/resources/otherLib/js/swiper.min.js" type="text/javascript"></script>
<script src="/resources/otherLib/js/chart.min.js" type="text/javascript"></script>
<script src="/resources/js/main_script.js" type="text/javascript"></script>

<jsp:include page="../common/footer.jsp" flush="true" />
