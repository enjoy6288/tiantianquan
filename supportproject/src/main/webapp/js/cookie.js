define(function(require, exports,path, module) {
    exports.path = '/';//;path||location.pathname;
    exports.setCookie =function (name, value, iDay){
        var oDate=new Date(),iDay=iDay||7;
        oDate.setDate(oDate.getDate()+iDay);
        document.cookie=name+'='+value+';expires='+oDate+';path='+this.path;
    };
    exports.getCookie =function (name){
        var arr=document.cookie.split('; ');
        for(var i=0;i<arr.length;i++){
            var arr2=arr[i].split('=');
            if(arr2[0]==name){
                return arr2[1];
            }
        }
        return '';
    };
    exports.removeCookie =function(name){
        exports.setCookie(name, 1, -1);
    };
});
