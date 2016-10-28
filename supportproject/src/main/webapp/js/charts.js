define(function(require, exports, module) {
	var base = require('base');
	//客户细分饼图 {name:"品牌", value:2},
   exports.pie = function(id,data,seriesName)
	{
		var color=[];
		var radius=[0, '75%'];
		if(seriesName == '忠诚度'){
			color=['#25ace4','#3abcbe','#6cbe04'];
		}else if(id.match(/charts\d+/)){
			color=[
				'#3f51b5','#25ace4','#4fc3f7','#3abcbe',
				'#009688','#6cbe04','#4caf50','#cddc39',
				'#f44336','#f48fb1','#c782ea','#7e57c2',
				'#ffeb3b','#f9ce1d','#ff9800','#ff5722',
			];
			radius=[0, '65%'];
		}else{
			color=['#25ace4','#6cbe04','#c782ea','#3abcbe' ];
		}
		
		if (seriesName == '异常行为类别占比'){
			radius=[0, '49%'];
		}
		var legendData=[];
		for(var i in data){
			legendData.push(data[i].name);	
		}
		var chart = echarts.init(document.getElementById(id), 'macarons');
		var option = {
				color:color,
				tooltip : {
					trigger: 'item',
					formatter: "{b}<br/> {d}% ({c})"// 忠诚度 {b}<br/> {d}% ({c})
				},
				legend: {
					show:false,
					orient : 'vertical',
					x : 'right',
					y : 'center',
					selectedMode:false,
					data:legendData
				},
				toolbox: {
					show : false,
				},
				//calculable : true,
				series : [
					{
						name:seriesName,
						type:'pie',
						radius : radius,
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter:function(a){
										a.name = a.name.length>20 ? a.name.substr(0,20)+"..." : a.name;
										return a.name+"\n"+a.percent+"%";	
										//忠诚度 a.name+"\n"+a.percent+"% （"+base.formatNum(a.value)+"）"
									}
								},
								labelLine : {
									show : true,
									length : 10
								},
								borderWidth:1,
              					borderColor:"#fff",
							},
						},
						data:data
					}
				]
			};
		chart.setOption(option);	
	}
	//活跃度 饼图{"name":"活跃度较高", "value":c_data.h}
	exports.pie_1 = function(id,data,seriesName)
	{
		if(id.match(/charts\d+/)){
			color=[
				'#3f51b5','#25ace4','#4fc3f7','#3abcbe',
				'#009688','#6cbe04','#4caf50','#cddc39',
				'#f44336','#f48fb1','#c782ea','#7e57c2',
				'#ffeb3b','#f9ce1d','#ff9800','#ff5722',
			];
		}else{
			color=['#1d89e4','#df478d','#8dc253'];	
		}
		var legendData=[];
		for(var i in data){
			legendData.push(data[i].name);	
		}
		var chart = echarts.init(document.getElementById(id), 'macarons');
		var option = {
				color:color,
				tooltip : {
					trigger: 'item',
					formatter: "{b}<br/> {d}% ({c})"
				},
				legend: {
					show:false,
					orient : 'vertical',
					x : 'right',
					y : 'center',
					selectedMode:false,
					data:legendData
				},
				toolbox: {
					show : false,
				},
				//calculable : false,
				series : [
					{
						name:seriesName,
						type:'pie',
						radius : ['50%', '70%'],
						itemStyle : {
							normal : {
								label : {
									show : true
								},
								labelLine : {
									show : true
								}
							},
							emphasis : {
								label : {
									show : true,
									position : 'center',
									textStyle : {
										fontSize : '14',
										fontWeight : 'bold'
									}
								}
							}
						},
						data:data
					}
				]
			};
       
		chart.setOption(option);	
	}
	//客户细分柱状图 {name:"品牌A", account:0.5, quantity:222},
	exports.bar = function(id,title,data,barWidth, xName, yName)
	{
		var chart = echarts.init(document.getElementById(id), 'macarons');
		var xData = [];
		var seriesData = [];
		var total = 0;
		if(id == 'categoryValue'){
			var color = [ 
					'#25ace4','#3bb4e7','#51bde9','#67c5ec',
					'#7ccdef','#92d6f2','#a8def4','#bee6f7',
				];
		}else if(id.match(/charts\d+/)){
			color=[
				'#3f51b5','#25ace4','#4fc3f7','#3abcbe',
				'#009688','#6cbe04','#4caf50','#cddc39',
				'#f44336','#f48fb1','#c782ea','#7e57c2',
				'#ffeb3b','#f9ce1d','#ff9800','#ff5722',
			];
		}else{
			var color = [ '#3abcbe','#25ace4','#c782ea','#6cbe04' ];		
		}
		for(i in data){
			xData.push(data[i].name);
			seriesData.push(data[i].account || data[i].percent);
			total += data[i].quantity;
		}
		var option = {
				title:{
					show:true, 
					text:title,
					x:'center',
					textStyle:{
						fontWeight:'normal',
						fontSize:14,
						color:'#676a6c',
						fontFamily:'Microsoft YaHei',
					}
				},
				legend: {
					data:['类别占比'],
					show:false,
				},
				grid:{
					x:40,
					y:45,
					x2:60,
					y2:30	
				},
				tooltip : {
					trigger: 'axis',
					formatter: function(params){
						params = params[0];
						return params[1]+'：'+base.percentage(params['value'])+'%'+'</br>('+base.formatNum(Math.round(params['value']*total))+')';
					},
					axisPointer:{
						type: 'none',
					},
					backgroundColor: 'rgba(0,0,0,0.5)',
					borderRadius: 4,
				},
			    xAxis :{
			            type : 'category',
			            data : xData,
						name : xName,
			    },
			    yAxis :{
					type : 'value',
					name : yName,
					axisLabel: {
					  formatter:function(params){
						return params*100+'%';
					  },
					},
					splitArea: {
						show: true,
						onGap: 'categoy',		//奇偶行颜色不同
					}
				},
			    series : [
			        {
			            type:'bar',
			            //barWidth: 47,
			            itemStyle: {
			                normal: { 
								color:function(d){
									return color[d.dataIndex];
								},
								barBorderRadius: 2,                  
			                }
			            },
			            data:seriesData,
			        }
			    ]
		};   
		if(typeof barWidth == 'undefined' || barWidth.length==''){
			option.series[0].barWidth = 47;	
		}                 
		chart.setOption(option);	
	}
	//客户细分折线图 
	exports.line = function(id, data){
		if(id.match(/charts\d+/)){
			color=[
				'#3f51b5','#25ace4','#4fc3f7','#3abcbe',
				'#009688','#6cbe04','#4caf50','#cddc39',
				'#f44336','#f48fb1','#c782ea','#7e57c2',
				'#ffeb3b','#f9ce1d','#ff9800','#ff5722',
			];
		}else{
			color=['#25ace4','#6cbe04','#c782ea','#3abcbe' ];	
		}
		var chart = echarts.init(document.getElementById(id), 'macarons');
		var xData = [];
		var lengendData = [];
		var seriesData = [];
		
		var tmp=0;
		for(i in data){
			lengendData.push(i);
			var tmpData=[];
			for(j in data[i]){
				if(tmp==0){
					xData.push(j);	
				}
				tmpData.push(data[i][j]);
			}
			seriesData.push({name:i,type:'line',data:tmpData});
			tmp++;
		}
		
		var option = {
				color:color,
				legend: {
					data:lengendData,
					show:false,
				},
				grid:{
					x:40,
					y:45,
					x2:20,
					y2:30	
				},
				tooltip : {
					trigger: 'axis',
					backgroundColor: 'rgba(0,0,0,0.5)',
				},
			    xAxis :{
			            type : 'category',
						boundaryGap : false,
			            data : xData,
			    },
			    yAxis :{
					type : 'value',
					axisLabel: {
					  formatter:function(params){
						return params*100+'%';
					  },
					},
					splitArea: {
						show: true,
						onGap: 'categoy',		//奇偶行颜色不同
					}
				},
			    series : seriesData
		};                    
		chart.setOption(option);
	}
	//客户黏度模型  类别差异对比图
	//"高黏度"(系列名称):{"R_score"(横坐标):85(纵坐标),"F_score":66,"E_score":43,"RFE_score":50},
	exports.line_1 = function(id, data){
		color=['#25ace4','#6cbe04','#c782ea','#3abcbe' ];
		var chart = echarts.init(document.getElementById(id), 'macarons');
		var xData = [];
		var lengendData = [];
		var seriesData = [];
		
		var tmp=0;
		for(i in data){
			lengendData.push(i);
			var tmpData=[];
			for(j in data[i]){
				if(tmp==0){
					xData.push(j);	
				}
				tmpData.push(data[i][j]);
			}
			seriesData.push({name:i,
							type:'line',
				            itemStyle :{
				            	normal : {
				            		label :{
				            			show : false,
				            			//position: 'top',
				            			textStyle: {
				            				align: 'left',
				            				baseline: 'top',
				            			}
				            		}
				            	}
				            },
				            data:tmpData});
			tmp++;
		}		
		var option = {
				color:color,
				legend: {
					data:lengendData,
					show:true,
					y: 285,
				},				
				grid:{
					x:40,
					y:45,
					x2:70,
					y2:45	
				},
				tooltip : {
					trigger: 'axis',
					backgroundColor: 'rgba(0,0,0,0.5)',
				},
		    toolbox: {
		        show : true,
		        feature : {            
		            magicType: {show: true, type: ['line', 'bar']}            
		        }
		    },
			    xAxis :{
			       type : 'category',
			       //name : '类型',
			       boundaryGap : false,
			       data : xData
			    },
			    yAxis :{
						type : 'value',
						name : '得分',
						splitArea: {
							show: true,
							onGap: 'categoy',		//奇偶行颜色不同
						}
				},
			    series : seriesData
		};                    
		chart.setOption(option);
	}
});
