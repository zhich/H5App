var mySystem = 'android'; //android或ios
// 判断是否ios
var isIOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
if (isIOS) {
    mySystem = 'ios';
}
(function () {

    var osJs = "js/android.js";
    if (mySystem == 'ios') {
        osJs = "js/ios.js";
    }
    var body = document.getElementsByTagName('body')[0],
        js = document.createElement('script'),

    js.setAttribute('type', 'text/javascript');
    js.setAttribute('src', osJs);
    body.appendChild(js);

    //加入xj.js,确保在osJs.js加载完执行
//    var xjUrl = 'js/xj.js',
//        xj = document.createElement('script');
//    xj.setAttribute('type', 'text/javascript');
//    xj.setAttribute('src', xjUrl);
//
//    js.onload = function () {
//        body.appendChild(xj);
//    };

})();