
/** swiper value **/
var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;
var slidesPerViewCnt = isMobile == true ? 1 : parseInt(Number(this.innerWidth)/366) ;
var detail_slidesPerViewCnt = isMobile == true ? 2 : parseInt(Number($('.sch_result').innerWidth())/366)*2 ;

/** hot video swiper **/
var swiper = new Swiper('.swiper-container', {
	slidesPerView 	: slidesPerViewCnt,
	spaceBetween 	: 15,
	pagination 		: { el : '.swiper-pagination' },
	autoplay 		: { delay : 4500 },
});

/** detail click swiper **/
/*
var swiperArry = [];
$(".d-swiper-container").each(function(index,element){
	var x = $(this).addClass('x'+index);
	var y = $(this).find('.d-swiper-pagination').addClass('y'+index);
	
	var dswiper = new Swiper('.x'+index, {
		slidesPerView 	: detail_slidesPerViewCnt,
		spaceBetween 	: 15,
		pagination 		: { el : '.y'+index },
		autoplay 		: { delay : 4500 },
	});	
	swiperArry.push(dswiper);
	
	$(this).parent().parent().parent().hide(); //sch_result_detail display none
});
*/ 
/** swiper update when resize */
$(window).resize(function() {
	if($(".swiper-container").length > 0){
		swiper.params.slidesPerView = parseInt(Number(this.innerWidth)/366);
		swiper.update();
	}

	/*
	$(".d-swiper-container").parent().parent().parent().show();
	$(swiperArry).each(function(index,element){
		element.params.slidesPerView = parseInt(Number($('.sch_result').innerWidth())/366)*2;
		element.update();
	})
	$(".d-swiper-container").parent().parent().parent().hide();
	*/
});



/** search word validation check **/
function fn_schIsValid(){
	var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
	var sch_val = $("#main_search").val();
	
	if(sch_val.length < 2){
		fn_toast("검색은 두글자 이상 부탁드립니다!");
		return false;
	}
	if(regExp.test(sch_val)){
		fn_toast("검색어에 특수문자는 입력하면 안돼용");
		return false;
	}
	return true;
}

/** main search **/
function fn_main_search(){
	
	if(fn_schIsValid()){
		fn_loading_title_changer();
		fn_loading_show();
		
		var data = {'search_keyword' : $("#main_search").val()};
		setTimeout(() => {
			post_to_url('/main_search', data);
		}, 500);
	}
}


/** when click the detail streamer info **/
function fn_streamer_detail(val){

	$(".sch_result").removeClass('active');
	if($(val).hasClass('active')===false) $(val).addClass('active');
	
	/* chart에 사용되는 array */
	var hist_arr = new Array(); //배열선언
	hist_arr.push(fn_hist_to_arry($(val).children('.subsc_hist')));
	hist_arr.push(fn_hist_to_arry($(val).children('.video_hist')));
	hist_arr.push(fn_hist_to_arry($(val).children('.view_hist')));
	
	if($(val).children('.sch_result_detail').css('display') == 'none'){
		$('.sch_result_detail').slideUp(150); //배경 
		$(val).children('.sch_result_detail').slideDown(200);
		
		$('.subscribe_wrap').slideUp(100,function(){
			$('.subscribe_wrap').prev().addClass('pb-4');
			$(val).children('.info_wrap').children('.subscribe_wrap').prev().removeClass('pb-4');
		});	//유툽버튼
		$(val).children('.info_wrap').children('.subscribe_wrap').slideDown(100);
		
		
		$('.status_wrap').slideUp(100);//차트
		$(val).children('.status_wrap').slideDown(100);
		
		//chart create
		if($(val).children('.status_wrap').children('canvas').attr('class') == undefined ){
			fn_callChart($(val).children('.status_wrap').children('canvas'), hist_arr);
		}
		
	}
	
	/* detail swiper update */
	/* 
	$(swiperArry).each(function(index,element){
		element.params.slidesPerView = parseInt(Number($('.sch_result').innerWidth())/366)*2;
		element.update();
	})
	*/
}


/***************** fn_hist_to_arry - hist값을 arry 로 return *****************/
function fn_hist_to_arry(element){
	var hist = element.val();
	var histArry = hist.split(',');
	var hlenght = histArry.length ;
	if(hlenght<8){
		for(var i = 1 ; i < (8-hlenght) ; i++){
			histArry.unshift('0');	
		}
	}else{
		for(var i = Number(hlenght) ; i >= 8 ; i--){
			histArry.shift();
		}
	}
	return histArry;
}

/***************** chart *******************/

/** yyyy-mm-dd 포맷 **/
function fn_format_date(){
	var date = new Date();
    var year = date.getFullYear();
    var month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    var day = date.getDate();
    day = day >= 10 ? day : '0' + day; 
    return  year + '-' + month + '-' + day;
}

/** 단위 조절 **/
function commarize() {
	// Alter numbers larger than 1k
	if (this >= 1e3) {
	    var units = ["k", "M", "B", "T"];
	    
	    // Divide to get SI Unit engineering style numbers (1e3,1e6,1e9, etc)
	    let unit = Math.floor(((this).toFixed(0).length - 1) / 3) * 3
	    // Calculate the remainder
	    var num = (this / ('1e'+unit)).toFixed(2)
	    var unitname = units[Math.floor(unit / 3) - 1]
	    	
    // output number remainder + unitname
    return num + unitname
	}
  
	// return formatted original number
	return this.toLocaleString()
}

// Add method to prototype. this allows you to use this function on numbers and strings directly
Number.prototype.commarize = commarize
String.prototype.commarize = commarize

function fn_callChart(element, arry){
	
	var nowDate = fn_format_date();
	nowDate = nowDate.split("-");
	var beforeDate = new Date();
	var labelArray = new Array(); //chart label
	
	for(var i = 7 ; i > 0 ; i--){
		beforeDate.setFullYear(nowDate[0], nowDate[1]-1, nowDate[2]-i);
		labelArray.push(beforeDate.getDate());
	}
	
	var myChart = new Chart(element, {
	    type: 'line',
	    data: {
	        labels: labelArray,
	        datasets: [{
	            label: '구독자',
	            data: arry[0],
	            borderColor: "rgba(190, 98, 114, 1)",
	            backgroundColor: "rgba(190, 98, 114, 0.4)",
	            fill: false,
	            lineTension: 0
	        },/*{
	            label: '비디오',
	            data: arry[1],
	            borderColor: "rgba(98, 98, 190, 1)",
	            backgroundColor: "rgba(98, 98, 190, 0.4)",
	            fill: true,
	            lineTension: 0.4
	        },*/{
	            label: '조회수',
	            data: arry[2],
	            borderColor: "rgba(98, 98, 190, 1)",
	            backgroundColor: "rgba(98, 98, 190, 0.4)",
	            fill: false,
	            lineTension: 0
	        }]
	    },
	    options: {
	        responsive: true,
	        title: {
	            display: false,
	            text: '라인 차트 테스트'
	        },
	        tooltips: {
	            mode: 'index',
	            intersect: true,
	        },
	        animation: {
	            duration: 0 // general animation time
	        },
	        hover: {
	            animationDuration: 0 // duration of animations when hovering an item
	        },
	        responsiveAnimationDuration: 0 // animation duration after a resize
	        ,
	        scales: {
		        xAxes: [{
	                afterTickToLabelConversion: function(data){
	                    var xLabels = data.ticks;
	                    xLabels.forEach(function (labels, i) {
	                        if (i % 2 == 1){
	                            xLabels[i] = '';
	                        }
	                    });
	                }
	            }],
	            yAxes: [{
	                ticks: {
	                	callback: function(value, index, values) {
	                		//console.log();
	                		if(value > 100000)
	                			return value / 1e6 + 'M';
	                		else if(value < 100000){
	                			return value / 1e5 + 'K';
	                		}
	                    }
	                }
	            }]
	        },
	        layout: {
	            padding: {
	                left: 0,
	                right: 0,
	                top: 15,
	                bottom: 10
	            }
	        }, 
		    
	        /*tooltips: {
	    		callbacks: {
    				label: function(tooltipItem, data) {
    					var dataLabel = data.labels[tooltipItem.index];
    					var value = ': ' + data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index].toLocaleString();
    					if (Chart.helpers.isArray(dataLabel)) {
    						dataLabel = dataLabel.slice();
    						dataLabel[0] += value;
    					} else {
    						dataLabel += value;
    					}
    					return dataLabel;
    				}
	    		}
		    }
		    */
	    }
	});
	

	
//	var dataArray = myChart.data.datasets[0].data
//	console.log(dataArray.slice(Math.max(dataArray.length - 7, 1)));
}

