define(function(require, exports, module) {
    var containerId = '#container';
    var version ='1.0.0';   
    var viewsPath = './views/' + version + '/';
    //首页
    exports['dashboard'] = {
        container : containerId,
        url : viewsPath+'dashboard/dashboard.html'
    }
    //首页-虚拟人总数
    exports['virtual_total'] = {
        container : containerId,
        url : viewsPath+'dashboard/virtual_total.html'
    }
    //首页-趋势图
    exports['trend_chart'] = {
        container : containerId,
        url : viewsPath+'dashboard/trend_chart.html',
        params:['uid']
    }
    //首页-昨日增长虚拟人
    exports['virtual_add'] = {
        container : containerId,
        url : viewsPath+'dashboard/virtual_add.html',
        params:['type']
    }

    //虚拟人检索
    exports['fictitiou_retrieval'] = {
        container : containerId,
        url : viewsPath+'fictitiouRetrieval/fictitiou_retrieval.html'
    }
    //虚拟人列表 
     exports['fictitiou_list'] = {
        container : containerId,
        url : viewsPath+'fictitiouRetrieval/fictitiou_list.html',
        params : ['uid','type']
    }
    //网络行为刻画
    exports['fictitiou_conduct_depict'] = {
        container : containerId,
         url : viewsPath+'fictitiouRetrieval/fictitiou_conduct_depict.html',
         params : ['id','name']
    }
    //IP属性库
    exports['attribute_list'] = {
        container : containerId,
        url : viewsPath+'ipAttribute/attribute_list.html'
    }
    //ip属性表
     exports['attribute_depict'] = {
        container : containerId,
        url : viewsPath+'ipAttribute/attribute_depict.html',
        params:['id']
    }
    //虚拟人监测
    exports['monitor_list'] = {
        container : containerId,
        url : viewsPath+'monitorRetrieval/monitor_list.html'
    }
    //虚拟人列表
     exports['virtualhuman_list'] = {
        container : containerId,
        url : viewsPath+'monitorRetrieval/virtualhuman_list.html',
        params:['id','name']
    }

    //监测
    exports['retrieval_monitor'] = {
        container : containerId,
        url : viewsPath+'monitorRetrieval/retrieval_monitor.html',
        params:['uid']
    }
    //用户管理
    exports['user_manage'] = {
        container:containerId,
         url : viewsPath+'systemManage/user_manage.html'
    }
    //部门管理
     exports['department_manage'] = {
        container:containerId,
        url : viewsPath+'systemManage/department_manage.html'
    }
    //角色管理
     exports['role_manage'] = {
        container:containerId,
        url : viewsPath+'systemManage/role_manage.html'
    }
    //权限管理
     exports['jurisdiction_manage'] = {
        container:containerId,
        url : viewsPath+'systemManage/jurisdiction_manage.html'
    }
    //日志管理
    exports['journal_manage'] = {
        container:containerId,
        url : viewsPath+'systemManage/journal_manage.html'
    }
});
