// 所有模块都通过 define 来定义
define(function(require, exports, module) {
	var configs = require('config');
	var routes = require('routes');
	var cookie = require('cookie');
	var template = require('template');
	var md5 = require('md5');
	var json = require('json');
	var layer = require('layer');
	exports.apiUrl = 'http://127.0.0.1:8080';
	exports.ajax = function(options,loadingTar){
		var _this = this;
		var obj = {
			type : "POST",
			data : {},
			dataType : 'json'
		},options = options;
		var opts = $.extend({},obj,options);
		opts.success = function(data){
			//options.success(data);
			// 判断登录是否过期
			try{
				if(options.login!=true && !cookie.getCookie('id')){
					location.href = './login.html';
				}
				options.success(data);
			}catch(e){
				layer.msg(e.toString());
			}
			return;
		};
		opts.error = function(){
			loadingTar&&_this.unLoading(loadingKey);
			// _this.tips('something is wrong...');
		}
		var loadingKey = loadingTar&&_this.loading(loadingTar);
		opts.url = exports.apiUrl + opts.url;
		var jsessionid = exports.getSession();
		if(typeof opts.data=='string'){
			opts.data += '&bmejsessionid='+jsessionid;
		}else{
			opts.data.bmejsessionid = jsessionid;
		}
		$.ajax(opts);
	};
	exports.getSession = function() {
		var jsessionid = cookie.getCookie('bmejsessionid');
		if(!jsessionid){
			cookie.setCookie('bmejsessionid',exports.randomString());
			jsessionid = cookie.getCookie('bmejsessionid');
		}
		return jsessionid;
	};
	
	exports.C = function(index , type) {
		switch (type) {
			case 1 :  
				return configs.get_table_config(index);
			break;
			default : return index ? configs[index] : configs;
		} 
	};
	exports.navInit = function(){
		if($(window).width()<900){
			$('body').addClass('mini-navbar');	
		}
		if($('body').hasClass('mini-navbar')){
			$('.li-parent ul a').removeClass('nav_line');
			$('.li-parent ul a').removeClass('nav_line2');
		}
		$('#username').html(decodeURIComponent(cookie.getCookie('name')));
		var subNav = $.hash("subNav");
		var href = $('#side-menu .route').eq(1).attr('href');
		if(!subNav || subNav < 0){
			exports.route(href);
			return;
		}
		var tar = $('#side-menu a').eq(subNav);
		tar.closest('.li-parent').find('a>span').first().click();
		if ( tar.closest('.nav-second-level').length > 0 ) {
			$('#side-menu a').eq(subNav).click();
		}
		if(tar.closest('.nav-third-level').length > 0){
			setTimeout(function(){
				tar.closest('.nav-second-level').find('a>span').first().click();
			},1000)
		}
		
	}
	exports.menuInit = function(){
		var menu = decodeURIComponent(cookie.getCookie('menus'));
		var menus = menu.split(',');
		$('.m-i').each(function(){
			var html = $(this).html();
			var isShow = false;
			for (var i = 0; i < menus.length; i++) {
				if(menus[i].indexOf(html)>=0){
					isShow = true;
				}
			};
			if(isShow){
				$(this).closest('li').show()
			}else{
				$(this).closest('li').hide()
			}
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
		var url = _url || window.open(location.href);
		setTimeout(function(){
        	window.open(url);
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
	    if(isNaN(id)){
	        window.location.href="./login.html";
	    }else{
	        window.location.href="./home.html";
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
			cache : false,
			success : function(html){
				$(container).html(html);
			}
		})
	}
	exports.route = function(route,timeout){
		timeout = timeout ? timeout : 1;
		clearTimeout(exports.timeout);
		exports.timeout = setTimeout(function(){
			var tar_menu = $('#side-menu a[href="'+route+'"]:visible');
			var tar_all = $('a[href="'+route+'"]');
			if(tar_menu.length > 0){
				tar_menu.first().click();
				return;
			}else if(tar_all.length > 0){
				tar_all.last().click();
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
		var type = JSON.parse(decodeURIComponent(cookie.getCookie('permissionName')));
		console.log(type)
		var url = JSON.parse(decodeURIComponent(cookie.getCookie('url')));
		var black_url = url.UA库;
		for(var i=0;i<type.length;i++){
			if(type[i]=='系统管理'){
				delete type[i]
			}
		}
		var data =[];
		var json = {};
		json.son =[];
		var menus = configs.menus;
		for(key in menus){
			var list ={};
			var child = menus[key].son;	
			for(ckey in type){
				if(type[ckey]==menus[key].name){
					list.name = menus[key].name;
					list.icon = menus[key].icon;
					list.router = menus[key].router;
					data.push(list)
				}
				for(var item in child){
					var jsonlist ={};
					if(child[item].name==type[ckey]){
						json.name = '系统管理';
						json.icon = 'fa fa-manage';
						jsonlist.name = child[item].name;
						jsonlist.router = child[item].router;
						json.son.push(jsonlist);	
					}			
				}	
			};
		}
		if(json.name!=undefined){
			data.push(json);
		}
		console.log(data)
		var html_menu = '';
		for(key in data){
			var child = data[key].son;
			if(child!=undefined){
				html_menu +='<li class="li-parent"><a href="'+data[key].router+'" class="route"><i class="'+data[key].icon+'"></i><span class="nav-label">'+data[key].name+'</span></a><ul class="nav nav-second-level collapse">';
				for(ckey in child){
					html_menu +='<li><a href="'+child[ckey].router+'" class="route nav_line">'+child[ckey].name+'</a></li>';
				}
				html_menu += '</ul></li>';
			}else{
				if(data[key].name=='UA库'){
					html_menu += '<li class="li-parent"><a href="'+black_url+'" target="_blank"><i class="'+data[key].icon+'"></i><span class="nav-label">'+data[key].name+'</span></a></li>';
				}else{
					html_menu += '<li class="li-parent"><a href="'+data[key].router+'" class="route"><i class="'+data[key].icon+'"></i><span class="nav-label">'+data[key].name+'</span></a></li>';
				}
			}
		}
		$('#side-menu').append(html_menu);
		$('#side-menu .li-parent ul').find('a').last().removeClass('nav_line').addClass(' nav_line2');
		$('#side-menu').metisMenu();
		$('body').tooltip({
			selector: "[data-toggle=tooltip]",  
			container: "body"
		})
		$(document).on('click','#sign-out',function(){
			cookie.removeCookie('bmejsessionid');
			cookie.removeCookie('id');
			cookie.removeCookie('username');
			//cookie.removeCookie('menus');
			cookie.removeCookie('last_login_time');
			//cookie.removeCookie('user_industry');
		}); 
		$(document).on('click','#top_box',function(){
         $(document).scrollTop(0);
        });
		$(document).on('click','#change_password',function(){
			var html = template('chang_pass',{});
			layer.open({
                type: 1, 
                 area:['480px',''],  
                fix:true,
                shift: 2,
                shadeClose:true,
                closeBtn:null,
                title: null,
                border: [0],
                content: html,          
            });
            exports.valid();
            exports.input_placeholder();
		})
		$('.li-parent').children('a').click(function(){
			$('.li-parent ul').removeClass('in');
			$(this).next('ul').addClass('in');
			$(this).next('ul').css({'background':'#f8f8f9'})
		});
		$(document).on('click','.route',function(){
			var pre_params = JSON.parse($.hash('params'));
			var route = $(this).attr('href');
			var routeData = routes[route];
			if(route=='fictitiou_conduct_depict'){
				$('#top_box').show();
			}else{
				$('#top_box').hide();
			}
			if($(this).closest('.navbar-static-side').length > 0){	
				$('#side-menu').find('.active').removeClass('active');
				$(this).closest('.li-parent').find('.active').removeClass('active');
				$(this).parent().addClass('active');
				$(this).closest('.li-parent').addClass('active');
				var index = $('#side-menu a').index(this);
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
			if(index < 0){
				index = $.hash('subNav');
			}
			var hash_json = {
				url : encodeURIComponent(url),
				route : route,
				subNav : index,
				_t : new Date().getTime()
			};
			if ( !!pre_params ) {
				$.each(pre_params, function(key, value) {
					hash_json[key] = value;
				})
			}
			//var href = location.href;
			$.hash(hash_json);
			return false;
		})

		window.onhashchange=function(e,data){
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
			//exports.head_init(url);
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
		var m = Math.pow(10, 2); 
    	num = parseInt((num * m, 10) / m);
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
	    date = new Date(parseInt(date));
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
				strLen++;
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

		template.helper('formartFloat', function (num) {
			return !num ? 0 : parseFloat(num);
		});
		template.helper('formartFixed', function (num) {
			return !num ? 0 :(num/1).toFixed(2);
		});

	}
	exports.Tlanguage = {
	  "sLengthMenu": "_MENU_ 条每页",
	  "sZeroRecords": '<div class="nothing">没有数据</div>',
	  "sInfo": "_START_~_END_ 共_TOTAL_条",
	  "sInfoEmpty": "共0条数据",
	  "sInfoFiltered": "(从 _MAX_ 条结果中搜索得到)",
	  "sSearch": "搜索:",
	  "oPaginate": {
	    "sFirst": "首页",
	    "sPrevious": "前一页",
	    "sNext": "后一页",
	    "sLast": "尾页"
	  }

	}
	exports.init = function(){

		exports.bind();
		exports.navInit();
	}
	exports.input_placeholder = function(){
		 $('input[type=text],input[type=password]').each(function(){
            if ( typeof $(this).attr('placeholder') != 'undefined' && $(this).attr('placeholder') != '') {
                $(this).placeholder({isUseSpan:true});
            }
        });
	}
	//exports.menuInit();
	 exports.valid = function(){
	 	$('#form-password-index').validator({
            rules : {
                    name1 : function(element, params){
                        var reg = /^[A-Za-z0-9]+$/;
                        var reg1 = /[A-Z]/;
                        var reg2 = /[a-z]/;
                        var reg3 = /[0-9]/;
                        return (reg.test(element.value) && ( (reg1.test(element.value) && reg2.test(element.value)) || 
                            (reg1.test(element.value) && reg3.test(element.value)) || (reg2.test(element.value) && reg3.test(element.value))))||'至少包括大写字母、小写字母和数字中的2种组合'
                    },
                    len8 : function(element){
                        var v = element.value;
                        return v.replace(/[^\x00-\xff]/g,"00").length > 7 || '长度不能少于8个字符'
                    },
                    repeat: function(element){
                        var v = element.value;
                        console.log(v)
                        var v2 = $('input[name=suPasswd]').val();
                        console.log(v2)
                        return v===v2
                    },
                },
            fields: {
                oldPasswd :{
                    rule : "原密码:required;name1;",
                        msg : {
                             required : '原密码不能为空！',
                        },
                }, 
               suPasswd :{
                    rule : "新密码:required;name1;len8",
                        msg : {
                            required : '新密码不能为空！',
                        },
                },
                confirmpassword :{
                    rule : "确认密码:required; repeat",
                    msg : {
                         required : '确认密码不能为空！',
                        repeat : '两次输入的密码不一致',
                    },     
                },
            },
            valid : function(form){
                if(exports.inSubmit){
                    layer.msg('正在提交中...');
                    return;
                }
                exports.inSubmit = true;  
                var form_data = $(form).serializeArray();
               	var list = {};
               	var datas = {};
               	var name = decodeURIComponent(cookie.getCookie('name'));
               	for(key in form_data){
               		list[form_data[key].name] = form_data[key].value;
               	}
               	datas.suName = name;
               	datas.oldPasswd = $.md5(list.oldPasswd);
               	datas.suPasswd = $.md5(list.suPasswd);
               	console.log(datas)
               	exports.ajax({
					url : '/anguanproject/user/changePasswd.action',
					data : JSON.stringify(datas),
					type : 'POST',
					beforeSend: function(request) {
                        request.setRequestHeader("Content-Type", "application/json");
                    },
					success : function(data){
						if(data.code==1){
						layer.closeAll();
                      	layer.msg('修改成功');
						}
					}
				})
            }
        });
	 };

	exports.timeSetting = function(){
		var now = new Date();                    
	    var nowDayOfWeek = now.getDay();        
	    var nowDay = now.getDate();              
	    var nowMonth = now.getMonth();           
	    var nowYear = now.getYear();             
	    nowYear += (nowYear < 2000) ? 1900 : 0;  
	    var lastDay=[];
	    lastDay.push( getYesterdayDate)
	    var lastMonthDate = new Date();  //上月日期
	    lastMonthDate.setDate(1);
	    lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
	    var lastYear = lastMonthDate.getYear();
	    var lastMonth = lastMonthDate.getMonth();

    	//格式化日期：yyyy-MM-dd
	    function formatDate(date) {
	        var myyear = date.getFullYear();
	        var mymonth = date.getMonth()+1;
	        var myweekday = date.getDate();

	        if(mymonth < 10){
	            mymonth = "0" + mymonth;
	        }
	        if(myweekday < 10){
	            myweekday = "0" + myweekday;
	        }
	        return (myyear+"/"+mymonth + "/" + myweekday);
	    }

    	//获得某月的天数
	    function getMonthDays(myMonth){
	        var monthStartDate = new Date(nowYear, myMonth, 1);
	        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
	        var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
	        return   days;
	    }

	    //获得本季度的开始月份
	    function getQuarterStartMonth(){
	        var quarterStartMonth = 0;
	        if(nowMonth<3){
	            quarterStartMonth = 0;
	        }
	        if(2<6){
	            quarterStartMonth = 3;
	        }
	        if(5<9){
	            quarterStartMonth = 6;
	        }
	        if(nowMonth>8){
	            quarterStartMonth = 9;
	        }
	        return quarterStartMonth;
	    }

	    //今天
	    var getCurrentDate = new Date(nowYear, nowMonth, nowDay);
	    var getCurrentDate  = formatDate(getCurrentDate)
		//console.log(getCurrentDate +'-'+getCurrentDate )
	    //昨天
	    var getYesterdayDate = new Date(nowYear, nowMonth, nowDay - 1);
	    var getYesterdayDate =  formatDate(getYesterdayDate);

	    //获取7天前
	    var getSevnDate=new Date(nowYear, nowMonth, nowDay - 7);
	    var getSevnDate=formatDate(getSevnDate);
	    //获取15天前
	    var getfouDate=new Date(nowYear, nowMonth, nowDay - 15);
	     var getfouDate=formatDate( getfouDate);
	     //获取30前
	     var getthreeDate=new Date(nowYear, nowMonth, nowDay - 30);
	     var getthreeDate=formatDate(getthreeDate)
	      //获取182天前
	    var getEighteenDate=new Date(nowYear, nowMonth, nowDay - 90);
	    var getEighteenDate=formatDate(getEighteenDate)
	    //获得本周的开始日期
	    var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek+1);
	    var getWeekStartDate =  formatDate(getWeekStartDate);    
	    //获得上周的开始日期
	    var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -6);
	   	var getUpWeekStartDate =  formatDate(getUpWeekStartDate);
	    //获得上周的结束日期
	    var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (7 - nowDayOfWeek - 7));
	    var getUpWeekEndDate =  formatDate(getUpWeekEndDate);
 		//console.log(getUpWeekEndDate)
	    //获得本月的开始日期
	    var getMonthStartDate = new Date(nowYear, nowMonth, 1);
	    var getMonthStartDate =  formatDate(getMonthStartDate);
	    //获得上月开始时间
	    var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
	    var getLastMonthStartDate = formatDate(getLastMonthStartDate);
	    //获得上月结束时间
	    var getLastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
	    var getLastMonthEndDate = formatDate(getLastMonthEndDate);
	    var lastdata=getYesterdayDate+'-'+getYesterdayDate;
		var times_list = ['last7day','last15day','last30day','last90day','lastweek','lastmonth'];
		var timeset = [];
		for(var i=0;i<times_list.length;i++){
			switch(times_list[i]){
				//case'yesterday':
					//var newdata=getYesterdayDate+'-'+getYesterdayDate;
					//timeset.push({name:'昨天',data:newdata});
					//break;
				//case'week':
					//var newdata=getWeekStartDate+'-'+getYesterdayDate;
					//timeset.push({name:'本周',data:newdata});
					//break;
				
				//case'month':
					//var newdata=getMonthStartDate+'-'+getYesterdayDate;
					///timeset.push({name:'本月',data:newdata});
					//break;
				
				case'last7day':
					var newdata=getSevnDate+'-'+getYesterdayDate;
					timeset.push({name:'最近7天',data:newdata});
					break;
				/*case'last15day':
					var newdata=getfouDate+'-'+getYesterdayDate;
					timeset.push({name:'最近15天',data:newdata});
					break;*/
				case'last30day':
					var newdata=getthreeDate+'-'+getYesterdayDate;
					timeset.push({name:'最近30天',data:newdata});
					return timeset;
				/*case'last90day':
					var newdata=getEighteenDate+'-'+getYesterdayDate;
					timeset.push({name:'最近3个月',data:newdata});
					break;
				case'lastweek':
					var newdata=getUpWeekStartDate+'-'+getUpWeekEndDate;
					timeset.push({name:'上周',data:newdata});
					break;
				case'lastmonth':
					var newdata=getLastMonthStartDate+'-'+getLastMonthEndDate;
					timeset.push({name:'上月',data:newdata});
					return timeset;*/
				
			}
			
		}
	
		//@todo 设置每个起始时间 时间和时间戳方便转化   如{yesterday:'2015/03/15-2015/03/15'} 	

	};
	exports.timeSetting();

});
$_GET = (function(){
    var url = window.document.location.href.toString();
    console.log(url)
    var a = url.split("&");
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
debug = function (value,tag) {
	if(tag){
		console.info(tag+':');
	}
	console.info(value);
}
$.ajaxSetup({cache: true});

