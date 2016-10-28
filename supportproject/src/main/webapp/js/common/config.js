define(function(require, exports, module) { 
    exports.dimension_fields = [
        {'name':'排名', 'map':'index', 'index':true},//0
        {'name':'颜色', 'color':true, 'todo':true,'map':'color','callback':function(index,colors){return colors[index]}},//1
        {'name':'品牌','map':'name'},//2
        {'name':'产品数量','map':'amount'},//3
        {'name':'口碑指数','map':'reputation'},//4
        {'name':'环比变化','map':'compare','compare':true},//5
        {'name':'好评率','map':'positive_rate'},//6
        {'name':'','map':'process','process':true,'processfield':'positive_rate'}, //好评率条7
        {'name':'差评率','map':'negative_rate'},//8
        {'name':'产品','map':'name'},//9
        {'name':'','map':'process','process':true,'processfield':'negative_rate'}, //差评率条10
        {'name':'市场热度','map':'hotsale'},//11
        {'name':'','map':'process','process':true,'processfield':'compare'},//环比变化条12
        {'name':'','map':'process','process':true,'processfield':'hotsale'},//市场热度变化条13
        {'name':'省份', 'map':'name'}, //14
        {'name':'市场热度','map':'reputation'}, //15
        {'name':'环比涨幅','map':'compare','compare':true},//16
        {'name':'环比跌幅','map':'compare','compare':true},//17
        {'name':'型号数量','map':'model'},//18
        {'name':'品牌','map':'brand'},//19
        {'name':'占比','map':'ratio'},//20
        {'name':'热度指数','map':'hotsale'}//21
    ]

    exports.menus = [
        {
            'name':'首页', 
            'icon':'fa fa-user',
            'router':'dashboard'
        },
        {
            'name':'虚拟人检索', 
            'icon':'fa fa-search', 
            'router':'fictitiou_retrieval'
        },
        {
            'name':'IP属性库', 
            'icon':'fa fa-bullseye', 
            'router':'attribute_list'
        },
        {
            'name':'UA库',
            'icon':'fa fa-bullseye',
            'router':'https://www.baidu.com'
        },
        {
            'name':'虚拟人监测', 
            'icon':'fa fa-xu', 
            'router':'monitor_list'
        },
        {
            'name':'系统管理', 
            'icon':'fa fa-manage', 
            'son':[
                {'name':'用户管理','router':'user_manage'},
                {'name':'部门管理','router':'department_manage'},
                {'name':'角色管理','router':'role_manage'},
                {'name':'权限管理','router':'jurisdiction_manage'},
                {'name':'日志管理','router':'journal_manage'}
            ]
        }

    ]

    exports.get_table_config = function (id) {
       var index_fields = exports[id];
       var current_fields = []; 
       for ( key in index_fields )  {
            current_fields[key] = exports.dimension_fields[index_fields[key]];
       }
       return current_fields;
    }

    /*页面上所有表格*/
    //行业概览-品牌在售产品
    exports.brand_sale_table = [0,1,2,3,18];

    //市场热度-品牌市场热度榜
    exports.brand_sell_table=[0,1,2,11,5];

    //市场热度-品牌市场热度涨幅榜
    exports.brand_grow_table=[0,2,11,16,12];
    
    //市场热度-品牌市场热度跌幅榜
    exports.brand_decline_table=[0,2,11,17,12];
     
    //市场热度-产品市场热度榜
    exports.product_sell_table=[0,1,9,11,5];
   
    //市场热度-产品市场热度涨幅榜
    exports.product_grow_table=[0,9,11,16,12];
    
    //市场热度-产品市场热度跌幅榜
    exports.product_decline_table=[0,9,11,17,12];

    //市场热度-用户口碑-品牌口碑排行-表格
    exports.market_brand_table = [0,1,2,4,5];

    //用户口碑-品牌口碑好评-表格
    exports.market_brand_good_table = [0,2,6,7];

    //用户口碑-品牌口碑差评-表格
    exports.market_brand_bad_table = [0,2,8,10];

    //产品口碑-产品口碑排行-表格
    exports.market_product_table = [0,1,9,4,5];

    //产品口碑-产品口碑好评-表格
    exports.market_product_good_table = [0,9,6,7];

    //产品口碑-产品口碑差评-表格
    exports.market_product_bad_table = [0,9,8,10]; 

    //品牌洞察-品牌走势-品牌在售产品top排行
    exports.brand_trend_table=[0,2,11,13,19];

    //品牌洞察-购买地域-表格
    exports.brand_area_table=[0,14,15,20,5];

    //产品洞察-购买地域-表格
    exports.product_area_table=[0,14,15,20,5];

    //首页-产品市场热度
    exports.dashboard_product_sell_table=[0,9,21,5];

    //首页-品牌市场热度
     exports.dashboard_brand_sell_table=[0,2,21,5];

    /*表格结束*/
  
    //品牌市场热度
     exports['brand_sell_chart_pie'] = {
        'chart' : [
            {'field':'data','map':'hotsale'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }
     //产品市场热度
     exports['product_sell_chart_pie'] = {
        'chart' : [
            {'field':'data','map':'hotsale'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    //行业市场热度走势
    exports['host_highcharts_chart_line'] = {
        'chart' : [
            {'field':'data','map':'amount'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    //图标配置
    exports['market_flot_chart_pie'] = {
        'chart' : [
            {'field':'data','map':'model'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }
    
    
    exports['highcharts_chart_config_stack'] = {
        'chart' : [
            {'field':'data','map':'amount'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }


    // 用户口碑-情感走势-饼图
    exports['market_highcharts_chart_emotion_pie'] = {
        'chart' : [
            {'field':'data','map':'reputation'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    // 用户口碑-品牌口碑-饼图
    exports['market_brand_pie'] = {
        'chart' : [
            {'field':'data','map':'reputation'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    // 用户口碑-产品口碑-饼图
    exports['market_product_pie'] = {
        'chart' : [
            {'field':'data','map':'reputation'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    // 用户口碑-用户声音-用户焦点
    exports['market_user_focus_pie'] = {
        'chart' : [
            {'field':'data','map':'reputation'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    // 品牌口碑-品牌口碑类型
    exports['types_pie'] = {
        'chart' : [
            {'field':'data','map':'reputation'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }
    //品牌市场热度走势
    exports['brand_trend_highcharts_chart_line'] = {
        'chart' : [
            {'field':'data','map':'amount'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }
   
    //产品市场热度走势
    exports['product_trend_highcharts_chart_line'] = {
        'chart' : [
            {'field':'data','map':'amount'},
            {'field':'label','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

     //价格变化对市场热度影响走势
    exports['product_model_highcharts_chart_line'] = {
        'chart' : [
            {'field':'data','map':'hotsale','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    //产品各销售平台价格变化
    exports['product_platform_highcharts_chart_line'] = {
        'chart' : [
            {'field':'data','map':'price','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    //竞品洞察-品牌发现-市场热度对比
    exports['competition_highcharts_sell_host_line'] = {
        'chart' : [
            {'field':'data','map':'hotsale','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

//竞品洞察-品牌发现-在售产品量对比
    exports['competition_highcharts_brand_amount_bar'] = {
        'chart' : [
            {'field':'data','map':'hotsale','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }
   
    //竞品洞察-品牌发现-价格段对比
    exports['competition_highcharts_product_price_bar'] = {
        'chart' : [
            {'field':'data','map':'price','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }
  
    //竞品洞察-品牌发现-口碑声量走势对比
   exports['competition_highcharts_trand_model_bar'] = {
        'chart' : [
            {'field':'data','map':'count','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

    //竞争洞察-产品竞争-口碑类型对比
    exports['product_praise_type_bar'] = {
        'chart' : [
            {'field':'data','map':'count','isarr':true},
            {'field':'name','map':'name'},
            {'field':'color','todo':true,'map':function(index,colors){return colors[index]}}
        ]
    }

});
