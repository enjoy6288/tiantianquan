(function($){
	var cnt=0;
	var ms = {
		init:function(obj,args){
			return (function(){
				cnt++;
				ms.fillHtml(obj,args);
				ms.bindEvent(obj,args);
			})();
		},
		//填充html
		fillHtml:function(obj,args){
			return (function(){
				obj.empty();
				//上一页
				if(args.current > 1){
					obj.append('<a href="javascript:;" class="prevPage">上一页</a>');
				}else{
					obj.remove('.prevPage');
					obj.append('<a href="javascript:;" class="disabled">上一页</a>');
				}
				//中间页码
				if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
				}
				if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
					obj.append('<span >...</span>');
				}
				var start = args.current -2,end = parseInt(args.current)+2;
				if((start > 1 && args.current < 4)||args.current == 1){
					end++;
				}
				if(args.current > args.pageCount-4 && args.current >= args.pageCount){
					start--;
				}
				for (;start <= end; start++) {
					if(start <= args.pageCount && start >= 1){
						if(start != args.current){
							obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
						}else{
							obj.append('<a href="javascript:;" class="current">'+ start +'</a>');
						}
					}
				}
				if(parseInt(args.current) + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
					obj.append('<span >...</span>');
				}
				if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
				}
				//下一页
				if(args.current < args.pageCount){
					obj.append('<a href="javascript:;" class="nextPage">下一页</a>');
				}else{
					obj.remove('.nextPage');
					obj.append('<a href="javascript:;" class="disabled">下一页</a>');
				}
			})();
		},
		//绑定事件
		bindEvent:function(obj,args){
			return (function(){

				if(cnt > 1){
					obj.unbind('click');
					obj.on("click","a.tcdNumber",function(){
						var current = parseInt($(this).text());
						ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current);
						}
					});
					//上一页
					obj.on("click","a.prevPage",function(){
						var current = parseInt(obj.children("a.current").text());
						ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current-1);
						}
					});
					//下一页
					obj.on("click","a.nextPage",function(){
						var current = parseInt(obj.children("a.current").text());
						ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current+1);
						}
					});
					//跳转某页
					$(".page_btn").on("click",function(){
						var reg = new RegExp("^[0-9]*$");

						if(!reg.test($('.page_value').val()) || parseInt($('.page_value').val())<1){
							ms.fillHtml(obj,{"current":1,"pageCount":args.pageCount});
							if(typeof(args.backFn)=="function"){
								args.backFn(1);
							}
						}else if($('.page_value').val() > args.pageCount ){
							ms.fillHtml(obj,{"current":args.pageCount,"pageCount":args.pageCount});
							if(typeof(args.backFn)=="function"){
								args.backFn(args.pageCount);
							}
						}else{
							ms.fillHtml(obj,{"current":$('.page_value').val(),"pageCount":args.pageCount});
							if(typeof(args.backFn)=="function"){
								args.backFn($('.page_value').val());
							}
						}
						
					})
					//回车输入
					$(document).keypress(function(event) {
						if(event.keyCode == 13 && event.target.className == "page_value"){
							var reg = new RegExp("^[0-9]*$");
							if(!reg.test($('.page_value').val()) || parseInt($('.page_value').val())<1){
								ms.fillHtml(obj,{"current":1,"pageCount":args.pageCount});
								if(typeof(args.backFn)=="function"){
									args.backFn(1);
								}
							}else if($('.page_value').val() > args.pageCount ){
								ms.fillHtml(obj,{"current":args.pageCount,"pageCount":args.pageCount});
								if(typeof(args.backFn)=="function"){
									args.backFn(args.pageCount);
								}
							}else{
								ms.fillHtml(obj,{"current":$('.page_value').val(),"pageCount":args.pageCount});
								if(typeof(args.backFn)=="function"){
									args.backFn($('.page_value').val());
								}
							}
						}else{
							return;
						}
					});
				}else{
					obj.on("click","a.tcdNumber",function(){
						var current = parseInt($(this).text());
						ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current);
						}
					});
					//上一页
					obj.on("click","a.prevPage",function(){
						var current = parseInt(obj.children("a.current").text());
						ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current-1);
						}
					});
					//下一页
					obj.on("click","a.nextPage",function(){
						var current = parseInt(obj.children("a.current").text());
						ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
						if(typeof(args.backFn)=="function"){
							args.backFn(current+1);
						}
					});
					//跳转某页
					$(".page_btn").on("click",function(){
						var current = parseInt(obj.children("a.current").text());
						var reg = new RegExp("^[0-9]*$");
						if(!reg.test($('.page_value').val()) || parseInt($('.page_value').val())<1){
							ms.fillHtml(obj,{"current":1,"pageCount":args.pageCount});
							if(typeof(args.backFn)=="function"){
								args.backFn(1);
							}
						}else if($('.page_value').val() > args.pageCount){
							ms.fillHtml(obj,{"current":args.pageCount,"pageCount":args.pageCount});
							if(typeof(args.backFn)=="function"){
								args.backFn(args.pageCount);
							}
						}else{
							ms.fillHtml(obj,{"current":$('.page_value').val(),"pageCount":args.pageCount});
							if(typeof(args.backFn)=="function"){
								args.backFn($('.page_value').val());
							}
						}
						
					})
					//回车输入
					$(document).keypress(function(event) {
						if(event.keyCode == 13 && event.target.className == "page_value"){
							var reg = new RegExp("^[0-9]*$");
							if(!reg.test($('.page_value').val()) || parseInt($('.page_value').val())<1){
								ms.fillHtml(obj,{"current":1,"pageCount":args.pageCount});
								if(typeof(args.backFn)=="function"){
									args.backFn(1);
								}
							}else if($('.page_value').val() > args.pageCount ){
								ms.fillHtml(obj,{"current":args.pageCount,"pageCount":args.pageCount});
								if(typeof(args.backFn)=="function"){
									args.backFn(args.pageCount);
								}
							}else{
								ms.fillHtml(obj,{"current":$('.page_value').val(),"pageCount":args.pageCount});
								if(typeof(args.backFn)=="function"){
									args.backFn($('.page_value').val());
								}
							}
						}else{
							return;
						}
					});
				}
				
			})();
		}
	}
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount : 10,
			current : 1,
			backFn : function(){}
		},options);
		ms.init(this,args);
	}
})(jQuery);