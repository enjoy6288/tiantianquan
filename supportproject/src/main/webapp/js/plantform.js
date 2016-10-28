define(function(require, exports,path, module) {
    var layer = require('layer');
    exports.initHtml = function(tpls,wrapId) {
        var nameAry = [{id:1,name:"百分点营销管家",simple_name:"BMM",en_name:"BMM"}];
        var itemHtmlWrapper = $('<ul class="plantform-list clearfix"></ul>');
        for (var i = 0; i < nameAry.length; i++) {
            var name = nameAry[i]['simple_name'].toLowerCase();
            var id = nameAry[i]['id'];
            itemHtmlWrapper.append('<li><label><input class="hidden" id="plantform_'+id+'" type="checkbox" name="platformIds" value="'+id+'"><img class="unselected" src="./images/planform/'+name+'1.png"/><img class="hover" src="./images/planform/'+name+'3.png"/><img class="selected" src="./images/planform/'+name+'2.png"/></label></li>');
        };
        $(wrapId).html(itemHtmlWrapper);
        $(wrapId).find('li input').change(function(){
            if(this.checked){
                $(this).closest('li').addClass('in');
            }else{
                $(this).closest('li').removeClass('in');
            }
        })
    }
    exports.initStatus = function(selectedIds) {
        try{
            var idsAry = selectedIds.split(',');
            for (var i = 0; i < idsAry.length; i++) {
                if(!idsAry[i])continue;
                console.info(document.getElementById('plantform_'+idsAry[i]));
                document.getElementById('plantform_'+idsAry[i]).checked = true;
                $('#plantform_'+idsAry[i]).change();
            };
        }catch(e){
            layer.msg('初始化平台状态出错:'+e);
        }
    }
});
