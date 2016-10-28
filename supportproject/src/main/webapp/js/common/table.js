define(function(require, exports, module) {
    var base = require('base');
    var template = require('template');
    var colors = ['#89b522','#a1cd3a','#c7dd3c','#d3e55e','#c3ed95','#a3d9bf','#bfe9af','#d5edb3','#89b522','#a1cd3a'];
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
        var fields = base.C(id,1);
        var show_data = [];
        var i = 1;
        var first_data;
        $.each(data, function(){
            var _temp_data = this;
            var _temp_show_data = {};
            $.each(fields, function(){
                 if ( this.index ) {
                    _temp_show_data[this.map] = i;       
                 } else if (this.process) { 
                    var length = 100;
                    if ( i == 1 ) {
                        first_data = _temp_data[this.processfield];
                    } else {
                        length =  (_temp_data[this.processfield] / first_data) * length;
                    }
                    _temp_show_data[this.map] = length;
                 } else if(this.todo){
                    _temp_show_data[this.map] = this.callback(i-1,colors);
                 }else {
                    _temp_show_data[this.map] = _temp_data[this.map];
                 }
            });
            show_data.push(_temp_show_data);
            i++;
        }) 
        $('#'+id).html(template('table_tpl_top',{list:show_data,fields:fields}));
       
    }

      //data_table排行的表格模板
   exports.data_table_rander = function(id, data){
        var fields = base.C(id,1);
        var show_data = [];
        var i = 1;
        var first_data;
        $.each(data, function(){
            var _temp_data = this;
            var _temp_show_data = {};
            $.each(fields, function(){
                 if ( this.index ) {
                    _temp_show_data[this.map] = i;       
                 } else if (this.process) { 
                    var length = 260;
                    if ( i == 1 ) {
                        first_data = _temp_data[this.processfield];
                    } else {
                        length =  (_temp_data[this.processfield] / first_data) * length;
                    }
                    _temp_show_data[this.map] = length;
                 } else if(this.todo){
                    _temp_show_data[this.map] = this.callback(i-1,colors);
                 }else {
                    _temp_show_data[this.map] = _temp_data[this.map];
                 }
            });
            show_data.push(_temp_show_data);
            i++;
        }) 
       $('#'+id).html(template('table_trend_tpl_top',{list:show_data,fields:fields}));
       
    }

    //datatable地图表格模板
    exports.data_table_map_rander = function(id, data, map_type){
        var fields = base.C(id,1);
        var show_data = [];
        var i = 1;
        var first_data;
        $.each(data, function(){
            var _temp_data = this;
            var _temp_show_data = {};
            $.each(fields, function(){
                if ( this.index ) {
                    _temp_show_data[this.map] = i;
                } else if (this.process) {
                    var length = 250;
                    if ( i == 1 ) {
                        first_data = _temp_data[this.processfield];
                    } else {
                        length =  (_temp_data[this.processfield] / first_data) * length;
                    }
                    _temp_show_data[this.map] = length;
                } else if(this.todo){
                    _temp_show_data[this.map] = this.callback(i-1,colors);
                }else {
                    _temp_show_data[this.map] = _temp_data[this.map];
                }
            });
            show_data.push(_temp_show_data);
            i++;
        })
        $('#'+id).html(template('table_map_tpl_top',{list:show_data,fields:fields,type:map_type}));
    }


})

 