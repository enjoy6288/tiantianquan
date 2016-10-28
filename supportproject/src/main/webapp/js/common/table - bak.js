define(function(require, exports, module) {
    var base = require('base');
    var template = require('template');
    base.tpHelper(template);
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

    //排行的表格模板
    exports.table_rander = function(id, data){
        var table_conf = base.C(id);
        var fields = table_conf.fields;
        var show_data = [];
        var show_th = [];
        var i = 1;
        var first_data;
        $.each(data, function(){
            var _temp_data = this;
            var _temp_show_data = [];
            $.each(fields, function(){
                 if (this.index) {
                    _temp_show_data.push(i);       
                 } else if (this.process) { 
                    var length = 100;
                    if ( i == 1 ) {
                        first_data = _temp_data[this.processfield];
                    } else {
                        length =  (_temp_data[this.processfield] / first_data) * length;
                    }
                    _temp_show_data.push(length);
                 } else {
                    _temp_show_data.push(_temp_data[this.map]);
                 }
            });
            show_data.push(_temp_show_data);
            i++;
        })
        $.each(fields, function(){             
             show_th.push(this.name);
        })  
        $('#'+id).html(template('table_tpl_top',{list:show_data,th_list:show_th}));
    }


})