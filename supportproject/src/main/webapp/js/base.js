// 所有模块都通过 define 来定义
define(function(require, exports, module) {
	var cookie = require('cookie');
	var routes = require('routes');
	exports.hostUrl = 'http://172.24.2.17:8089';
	exports.ajax = function(options,loadingTar){
		var _this = this;
		var obj = {
			type : "POST",
			data : {},
			dataType : 'json'
		},options = options;
		var opts = $.extend({},obj,options);
		opts.success = function(data){
			//lihm 后台data的内容要修改
			console.log(data,'ajax');
			if(data.status && data.status == 2){
				location.href = './login.html';
			}
			options.success(data);
			return;
		};
		opts.error = function(){
			loadingTar&&_this.unLoading(loadingKey);
			// _this.tips('something is wrong...');
		}
		var loadingKey = loadingTar&&_this.loading(loadingTar);
		opts.url = exports.hostUrl+opts.url;
		var jsessionid = exports.getSession();
		if(typeof opts.data=='string'){
			opts.data += '&bmwjsessionid='+jsessionid;
		}else{
			opts.data.bmwjsessionid = jsessionid;
		}
		$.ajax(opts);
	};
	exports.getSession = function() {
		var jsessionid = cookie.getCookie('bmwjsessionid');
		if(!jsessionid){
			cookie.setCookie('bmwjsessionid',exports.randomString());
			jsessionid = cookie.getCookie('bmwjsessionid');
		}
		return jsessionid;
	};
	exports.randomString = function(len) {
	　　len = len || 32;
	　　var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
	　　var maxPos = $chars.length;
	　　var pwd = '';
	　　for (i = 0; i < len; i++) {
	　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	　　}
	　　return pwd;
	};
	exports.navInit = function(){
		if($(window).width()<1024){
			//$('body').addClass('mini-navbar');
		}
		$('#userName').attr('data-uid',cookie.getCookie('id')).html(decodeURIComponent('欢迎您，'+cookie.getCookie('username')));
		var subNav = $.hash("subNav");
		if(!subNav || subNav < 0){
			// subNav = 9;
			exports.route('customer_subdivision');
			return;
		}
		var tar = $('#side-menu a').eq(subNav);
		tar.closest('.li-parent').find('a>span').first().click();
		$('#side-menu a').eq(subNav).click();
		if(tar.closest('.nav-third-level').length > 0){
			setTimeout(function(){
				tar.closest('.nav-second-level').find('a>span').first().click();
			},1000)
		}
		window.onhashchange();
	}
	exports.menuInit = function(){
		var menu = decodeURIComponent(cookie.getCookie('menus'));
		var menus = menu.split(',');
		$('.m-i').each(function(){
//			var html = $(this).html();
//			var isShow = false;
//			for (var i = 0; i < menus.length; i++) {
//				if(menus[i].indexOf(html)>=0){
//					isShow = true;
//				}
//			};
//			if(isShow){
				$(this).closest('li').show()
//			}else{
//				$(this).closest('li').hide()
//			}
		})
	}
	exports.loading = function(tar,_time){
		var time = _time || 800;
		var key = 'loading_'+new Date().getTime();
		this[key] = setTimeout(function(){
			$(tar).addClass('loading');
		},time);
		this[key+'tar'] = tar;
		return key;
	}
	exports.unLoading = function(key){
		clearTimeout(this[key]);
		$(this[key+'tar']).removeClass('loading');
	}
	exports.tips = function(msg){
		var obj = {
			type : 'msg',
			msg : msg
		}
		var className = 'label-' + obj.type,labelItem = $('<span class="mgs_label">'+ obj.msg +'</span>'),wraper = $('<div class="msg_wraper"></div>');
		labelItem.addClass(className);
		$('.msg_wraper').remove();
		wraper.append(labelItem).appendTo($('body')).slideDown(600);
		clearTimeout(exports.tips.timeout);
		exports.tips.timeout = setTimeout(function(){
			$('.msg_wraper').slideUp(400,function(){
				$(this).remove();
			});
		},3000);
	};
	exports.setNav = function(text){
		var reg = new RegExp(text,'ig');
		$('#u-nav li a').each(function(){
			if(reg.test($(this).text())){
				$(this).addClass('cur');
				return false;
			}
		})
	};
	exports.jump = function(_url,_time){
		var time = _time || 1000;
		var url = _url || location.href;
		setTimeout(function(){
        	window.location.href=url;
		},time)
	};
	exports.object_leng = function(data){
		if(!data){
			return true;
		}else{
			var j = 0;
			for(var i in data){
				j++;
			}
			if(j<=1)
				return true;
		}
	}
	exports.loginCheck = function(isLogin){
	    var id = cookie.getCookie('id');
	    id=parseInt(id);
	    console.log(!(id),44);
	    if(!(id)){
	        window.location.href="./login.html";
	    }else{
	        //window.location.href="./index.html";
	    }
	    return
		var id = $(_this).attr('data-uid');
		if(!id){
			exports.jump('/user/login');
			return false;
		}
		return true;
	};
	exports.html = function(url,container){
		if(/report/.test(url)&&!/dashboard/.test(url)){
			$('.navbar').removeClass('white-bg');
		}else{
			$('.navbar').addClass('white-bg');
		}
		$.ajax({
			url : url,
			success : function(html){
				$(container).html(html);
			}
		})
	}
	exports.route = function(route,timeout){
		timeout = timeout ? timeout : 0;
		setTimeout(function(){
			if($('a[href="'+route+'"]').length > 0){
				$('a[href="'+route+'"]').click();
				//add by lihm fix style bug
				$('a[href="'+route+'"]').parent().parent().parent().addClass('active');
				$('a[href="'+route+'"]').parent().parent().addClass('in');
				//add by lihm fix style bug
				return;
			}
			var routeData = routes[route];
			var url = routeData['url'];
			var container = routeData['container'];
			exports.html(url,container);
		},timeout)
	}
	exports.arrayTrim = function(array) {
		for (var i in array) {
	        if (array[i] === null || array[i] === undefined) {
	            array[i] = 0;
	        };
	    };
	    return array;
	}
	exports.bind = function(){
		$('body').tooltip({  
			selector: "[data-toggle=tooltip]",  
			container: "body"
		})
		$(document).on('click','#sign-out',function(){
			cookie.removeCookie('bmwjsessionid');
			cookie.removeCookie('id');
			cookie.removeCookie('username');
			//cookie.removeCookie('menus');
		}) 
		$(document).on('click','.route',function(){
			var route = $(this).attr('href');
			var routeData = routes[route];
			if($(this).closest('.sidebar').length > 0){	
				$('.nav-second-level').find('.active').removeClass('active');
				var index = $('#side-menu a').index(this);
				$(this).parent().addClass('active');
			}
			try{
				var url = routeData['url'];
				var container = routeData['container'];
			}catch(e){
				return false;
			}
			var params = [];
			if(routeData.params&&routeData.params.length > 0){
				for (var i = 0; i < routeData.params.length; i++) {
					var key = routeData.params[i];
					var data = $(this).attr('data-'+key);
					$_GET[key] = data;
					if(data == undefined){
						alert('缺少'+key+'参数，请在此标签下添加：data-'+key);
						return false;
					}
					params.push(key+'='+data);
				};
				url += '?'+params.join('&');
			}
			if(index < 0||!index){
				index = $.hash('subNav');
			}
			var href = location.href;
			$.hash({
				url : encodeURIComponent(url),
				route : route,
				subNav : index,
				_t : new Date().getTime()
			});
			if(href == location.href)window.onhashchange();
			return false;
		})
		window.onhashchange=function(e,data){
			console.log('hash');
			$('.tooltip').remove();
			var route = $.hash('route')
			var routeData = routes[route];
			try{
				var container = routeData['container'];
			}catch(e){
				return false;
			}
			var url = decodeURIComponent($.hash('url'));
			exports.html(url,container);
		}
	}
	// 为数字添加分隔符
	exports.formatNum = function(num){
		if(!num)return 0;
		if(num==0)return 0;
		num+='';
		if(num.indexOf('-') !== -1)return num ;
		return num.replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g,'$1,');
	}
	exports.percentage = function(num) {
		return (num * 100).toFixed(2);
	}
	exports.rate = function(num, flag){
		if(!num)return;
		if(typeof flag=='undefined'){
			flag=true;	
		}
		//num=num.toString().substring(0,4)*100;
		//var m = Math.pow(10, 2); 
    	//num = parseInt(num * m, 10) / m;
		num = parseFloat(num.toString().substr(0, 20));
		num = num.toFixed(0);
		if(flag){
			num += '%';		
		}
		return num;
	}
	exports.translateNum = function(num){
		if (num < 1) {
			return (num*1).toFixed(2);
		}
		var numStr = num.toString();

		if (numStr.length > 3 && numStr.length <= 6) {
			return (num/1000.0).toFixed(1) + 'K';
		} else if (numStr.length > 6 && numStr.length <= 9) {
			return (num/1000000.0).toFixed(1) + 'M';
		} else if (numStr.length > 9) {
			return (num/1000000000.0).toFixed(1) + 'G';
		}
	}
	exports.formatTime = function(date, format){
		if(!date) return date;
	    date = new Date(date);
	    var map = {
	        "M": date.getMonth() + 1, //月份 
	        "d": date.getDate(), //日 
	        "h": date.getHours(), //小时 
	        "m": date.getMinutes(), //分 
	        "s": date.getSeconds(), //秒 
	        "q": Math.floor((date.getMonth() + 3) / 3), //季度 
	        "S": date.getMilliseconds() //毫秒 
	    };
	    format = format.replace(/([yMdhmsqS])+/g, function(all, t){
	        var v = map[t];
	        if(v !== undefined){
	            if(all.length > 1){
	                v = '0' + v;
	                v = v.substr(v.length-2);
	            }
	            return v;
	        }
	        else if(t === 'y'){
	            return (date.getFullYear() + '').substr(4 - all.length);
	        }
	        return all;
	    });
	    return format;
	}
	exports.tpHelper = function(template){
		template.helper('dateFormat', exports.formatTime);
		function cutString(pStr, pLen) {  
			var strLen  = 0;
			for(var i in pStr){
				
				if(isFull(pStr[i])){
					strLen++;
				}
				if(pLen <= strLen){
					var cutStr = pStr.substr(0,i);
					if(cutStr == pStr){
						return cutStr;
					}else{
						return cutStr += '...';
					}
				}
				strLen++;
			}
			return pStr;
		}
		function isFull(pChar) { 
		    if (pChar.charCodeAt(0) > 128){
		        return true;  
		    }else{
		        return false;  
		    } 
		}
		template.helper('nameLength', function (name,length) {
			if(!name) return name;
			var l = length || 30;
			if(name.replace(/[^\x00-\xff]/g,"00").length > l){
				var cutstring = cutString(name,l);
				return '<span title="" data-placement="top" data-toggle="tooltip" data-original-title="'+name+'">'+cutstring+'</span>'
			}
			return name;
		});
		template.helper('formatNumber', function (num) {
			if(!num)return 0;
			if(num==0)return 0;
			return exports.formatNum(num);
		});

		template.helper('numberTrim', function (num) {
			return !num ? 0 : num;
		});

		template.helper('percentage', function (num) {
			return exports.percentage(num);
		});
		//输入项默认值
		template.helper('defValue', function (name,length) {
			return Number(length);
		});

	}
	exports.Tlanguage = {
	  "sLengthMenu": "_MENU_ 条每页",
	  "sZeroRecords": '<div class="nothing">没有数据</div>',
	  "sInfo": "_START_~_END_ 共_TOTAL_条",
	  "sInfoEmpty": "共0条数据",
	  "sInfoFiltered": "(从 _MAX_ 条结果中搜索得到)",
	  "sSearch": "搜索:",
	  "pagingType": "simple",
	  "oPaginate": {
	    "sFirst": "首页",
	    "sPrevious": "上一页",
	    "sNext": "下一页",
	    "sLast": "尾页"
	  }

	}
	exports.bind();
	exports.navInit();
	//exports.menuInit();
	exports.timeSetting = function(time,plan_time_end,plan_time_start){
		time = time ? time:'7天';
		console.info(time,plan_time_end,plan_time_start);
		var plan_time_end_unmber = (new Date(plan_time_end)).getTime();
		var plan_time_start_unmber = (new Date(plan_time_start)).getTime();
		var mydata = (new Date()).getTime();
		if(plan_time_end_unmber<mydata){
			mydata=plan_time_end_unmber;
		}else{
			mydata=mydata-1000*60*60*24*1;
		}
		function select(time){
			switch (time){
				case '今天':
					var newdata = mydata;
					break;
				case '7天':
					var newdata = mydata-1000*60*60*24*6;
					if(plan_time_start_unmber>newdata){
						newdata=plan_time_start_unmber;
						$('.st-group button').addClass('btn-white').removeClass('btn-primary');
					}else{
						newdata=newdata;
					}
					break;
				case '30天':
					var newdata = mydata-1000*60*60*24*29;
					if(plan_time_start_unmber>newdata){
						newdata=plan_time_start_unmber;
						$('.st-group button').addClass('btn-white').removeClass('btn-primary');
					}else{
						newdata=newdata;
					}
					break;
				default :
					return time;
			}
			var newtime = new Date(newdata);
			var Day = newtime.getDate();//获取当前日
			if(Day<10){
				Day = '0'+Day;
			}
			var Month = newtime.getMonth()+1;//获取当前日
			if(Month<10){
				Month = '0'+Month;
			}
			var Year = newtime.getFullYear();//获取当前日
			var rel_time = Year+'-'+Month+'-'+Day;
			return rel_time;
		}
		$('#start_time').val(select(time));
		$('#end_time').val(select('今天'));
	}
	exports.timeSetting1 = function(time,plan_time_start){
		time = time ? time:'7天';
		var plan_time_start_unmber = (new Date(plan_time_start)).getTime();
		var mydata = (new Date()).getTime()-1000*60*60*24*1;
		function select(time){
			switch (time){
				case '今天':
					var newdata = mydata;
					break;
				case '7天':
					var newdata = mydata-1000*60*60*24*6;
					if(plan_time_start_unmber>newdata){
						newdata=plan_time_start_unmber;
						$('.st-group button').addClass('btn-white').removeClass('btn-primary');
					}else{
						newdata=newdata;
					}
					break;
				case '30天':
					var newdata = mydata-1000*60*60*24*29;
					if(plan_time_start_unmber>newdata){
						newdata=plan_time_start_unmber;
						$('.st-group button').addClass('btn-white').removeClass('btn-primary');
					}else{
						newdata=newdata;
					}
					break;
				default :
					return time;
			}
			var newtime = new Date(newdata);
			var Day = newtime.getDate();//获取当前日
			if(Day<10){
				Day = '0'+Day;
			}
			var Month = newtime.getMonth()+1;//获取当前日
			if(Month<10){
				Month = '0'+Month;
			}
			var Year = newtime.getFullYear();//获取当前日
			var rel_time = Year+'-'+Month+'-'+Day;
			return rel_time;
		}
		$('#start_time').val(select(time));
		$('#end_time').val(select('今天'));
	}
	exports.timeSetting2 = function(time){
		time = time ? time:'7天';
		var mydata = (new Date()).getTime();
		function select(time){
			switch (time){
				case '今天':
					var newdata = mydata;
					break;
				case '7天':
					var newdata = mydata-1000*60*60*24*6;
					break;
				case '30天':
					var newdata = mydata-1000*60*60*24*29;
					break;
				default :
					return time;
			}
			var newtime = new Date(newdata);
			var Day = newtime.getDate();//获取当前日
			if(Day<10){
				Day = '0'+Day;
			}
			var Month = newtime.getMonth()+1;//获取当前日
			if(Month<10){
				Month = '0'+Month;
			}
			var Year = newtime.getFullYear();//获取当前日
			var rel_time = Year+'-'+Month+'-'+Day;
			return rel_time;
		}
		$('#start_time').val(select(time));
		$('#end_time').val(select('今天'));
	}
	//加法函数，用来得到精确的加法结果,用于解决js浮点运算的bug
	exports.accAdd = function(arg1, arg2) {

	    var r1, r2, m, c;
	    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
	    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
	    c = Math.abs(r1 - r2);
	    m = Math.pow(10, Math.max(r1, r2))
	    if (c > 0) {
	        var cm = Math.pow(10, c);
	        if (r1 > r2) {
	            arg1 = Number(arg1.toString().replace(".", ""));
	            arg2 = Number(arg2.toString().replace(".", "")) * cm;
	        }
	        else {
	            arg1 = Number(arg1.toString().replace(".", "")) * cm;
	            arg2 = Number(arg2.toString().replace(".", ""));
	        }
	    }
	    else {
	        arg1 = Number(arg1.toString().replace(".", ""));
	        arg2 = Number(arg2.toString().replace(".", ""));
	    }
	    return (arg1 + arg2) / m

	}
	//减法函数，用来得到精确的减法结果,用于解决js浮点运算的bug
	exports.accSub = function(arg1,arg2){
		　　 var r1,r2,m,n;
		　　 try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		　　 try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		　　 m=Math.pow(10,Math.max(r1,r2));
		　　 //last modify by deeka
		　　 //动态控制精度长度
		　　 n=(r1>=r2)?r1:r2;
		　　 return ((arg1*m-arg2*m)/m).toFixed(n);
	}

});

$_GET = (function(){
    var url = window.document.location.href.toString();
    var u = url.split("?");
    if(typeof(u[1]) == "string"){
        u = u[1].split("&");
        var get = {};
        for(var i in u){
            var j = u[i].split("=");
            get[j[0]] = j[1];
        }
        return get;
    } else {
        return {};
    }
})();


