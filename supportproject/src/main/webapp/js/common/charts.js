define(function(require, exports, module) {
	var template = require('template');
	var base = require('base');
    var colors = ['#89b522','#a1cd3a','#c7dd3c','#d3e55e','#c3ed95','#a3d9bf'];
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

    //柱状图
    exports.bar_echarts = function(id,data){
        var myChart = echarts.init(document.getElementById(id));
        var xdatas = [];
        var ydatas = [];
        for(key in data){
          xdatas.push(data[key].idtype);
          ydatas.push(data[key].total);
        };
        console.log(xdatas)
        var option = {      
            tooltip : {
                trigger: 'axis'
            },
            calculable : false,
            xAxis : [
                {
                  type : 'category',
                  data : xdatas,
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
               
                {
                    name:'ID量',
                    type:'bar',
                    itemStyle:{
                      normal:{color:'#1976d2'}
                    },
                    data:ydatas
                }
            ]
        };
        myChart.setOption(option);
        window.onresize = myChart.resize;
       var ecConfig = require('plugins/echarts/config');  
        myChart.on(ecConfig.EVENT.CLICK, eConsole);       
        //clickable : true,  
        function eConsole(param) {
            if (typeof param.seriesIndex != 'undefined') {
                 //switch (param.name) {
                    //case "QQ":  
                    var name = param.name;
                    $('#new').attr('data-uid',name);  
                    $('#new').click();
                    //alert(1)
                    // window.location.href = "views/1.0.0/dashboard/dashboard.html";
                    //window.open("http://www.sina.com", "_blank");//在新页面打开
                    //break;
                 //}
            }
        }
    };
    //首页柱状图
     exports.dash_bar_echarts = function(id,data){
        var myChart = echarts.init(document.getElementById(id));
        var xdatas = [];
        var ydatas = [];
        for(key in data){
          xdatas.push(data[key].idtype);
          ydatas.push(data[key].total);
        };
        console.log(xdatas)
        var option = {      
            tooltip : {
                trigger: 'axis'
            },
            calculable : false,
            xAxis : [
                {
                  type : 'category',
                  data : xdatas,
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
               
                {
                    name:'ID量',
                    type:'bar',
                    itemStyle:{
                      normal:{color:'#1976d2'}
                    },
                    data:ydatas
                }
            ]
        };
        myChart.setOption(option);
        window.onresize = myChart.resize;
    };
    //单折线图
    exports.one_line_echarts = function(id,data){
        var myChart = echarts.init(document.getElementById(id));
        var xdatas = [];
        var ydatas = [];
        console.log(data)
        for(key in data){  
          xdatas.push(data[key].lDate)
          ydatas.push(data[key].total);
        }
        var option = {
            title: {
              text: 'ID数量统计',
              textStyle: {
                fontSize: 12
              },
              x: 50,
              y: 15
            },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data:['ID总量'],
              x: 'center',
              y: 15
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true          
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data:xdatas
            }, 
            yAxis: {
              type: 'value',
              //min: 1000,
              //max: 9000,
              splitNumber: 8,
              //axisLabel: {
              //formatter: '{value} 万'
              //}
            },
            series: [
              {
                name: 'ID总量',
                type: 'line',
                itemStyle:{
                    normal:{color:'#1976d2'}
                },
                data: ydatas
              }        
            ]
        };
        myChart.setOption(option);
        window.onresize = myChart.resize
    }
    //双折线
     exports.line_echarts = function(id,data){
        var myChart = echarts.init(document.getElementById(id));
        var date = [];
        var datas1 = [];
        var datas2 = [];
        for(key in data){
          date.push(data[key].lDate);
          datas1.push(data[key].total);
          datas2.push(data[key].increment);
        }
        var option = {
            title: {
              text: 'ID数量统计',
              textStyle: {
                fontSize: 12
              },
              x: 50,
              y: 15
            },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data:['ID总量','新增ID总量'],
              x: 'center',
              y: 15
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true          
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: date
            }, 
            yAxis: {
              type: 'value',
              splitNumber: 8,
            },
            series: [
              {
                name: 'ID总量',
                type: 'line',
                itemStyle:{
                    normal:{color:'#1976d2'}
                },
                data:datas1
              },        
              {
                name: '新增ID总量',
                type: 'line',
                itemStyle:{
                    normal:{color:'#26a69a'}
                },
                data: datas2
              }   
            ]
        };
        myChart.setOption(option);
        window.onresize = myChart.resize
    }
});