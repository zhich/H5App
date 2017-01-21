var comTag="JIEJUEH5";
var Scanner = {
    scan: function (params, callback, fail) {
        var json = {
            'params': params
        };
        return exec_asyn("Scanner", "scan", JSON.stringify(json), callback,
            fail);
    }
};


// html5应用
var App = {

    /**
     * 监听器
     */
    listener:null,

    /**
     * 获取应用ID
     *
     * @return 当前运行时的HTML5子应用ID
     */
    getAppId: function () {
        return exec_syn("App", "getAppId", null);
    },

    getClientVersion: function () {
        return exec_syn("App", "getClientVersionName", null);
    },

    //获取通讯录
    getContacts : function() {
        return exec_syn("App", "getContacts", null);
    },

    // 获取版本号
    getVersion: function () {
        return exec_syn("App", "getVersion", null);
    },

    // 截断android 返回键事件，用户在按返回键的时候就不会退出html5渲染界面了。
    overrideBackPressed: function (bound) {
        var json = {
            "bound": bound
        };
        return exec_syn("App", "overrideBackPressed", JSON.stringify(json));
    },

    // 退出应用，html5渲染界面关闭
    exitApp: function () {
        return exec_syn("App", "exitApp", null);
    },

    // 设置硬键盘监听，当用户按下返回，菜单，搜索按键的时候，onKeyEvent回调函数会被触发。
    // 事件如上 backpress、menupress、searchpress
    setKeyEventListener: function (listener) {
        window.listener = listener;
    },

    // 按键事件监听。如若setKeyEventListener设置了监听器，则当用户按下返回，菜单，搜索按键的时候，此函数会被触发
    // 事件如上 backpress、menupress、searchpress
    onKeyEvent: function (event) {
        if (typeof window.listener == 'function') {
            window.listener(event);
        }
    },

    // 设置html5应用标题内容。html5应用的标题是由android实现。
    setTitle: function (title) {
        var json = {
            "title": title
        }
        exec_syn("App", "setTitle", JSON.stringify(json));
    },
    
    setTitleColor : function(color) {
        var json = {
            "color" : color
        }
        exec_syn("App", "setTitleColor", JSON.stringify(json));
    },

    // 设置html5应用标题背景颜色。
    setTitleBackgroundColor: function (color) {
        var json = {
            "color": color
        }
        exec_syn("App", "setTitleBackground", JSON.stringify(json));
    },

    // 设置html5应用背景图片。
    setTitleBackgroundPath: function (path) {
        var json = {
            "path": path
        }
        exec_syn("App", "setTitleBackground", JSON.stringify(json));
    },

    /**
     * 设置html5应用右上角图片及事件
     * @param imgUrl    应用图片相对路径  String
     * @param funcStr   回调JS函数字符串   String
     * @param text  文本内容    String
     * @param textAlign     文本位置[left, right, top, bottom]    String
     */
    setTopBarImage: function (imgUrl, funcStr, text, textAlign) {
        var json = {"imageUrl": imgUrl, "callbackJS": funcStr, "text": text, "textAlign": textAlign};
        exec_syn("App", "setTopBarImage", JSON.stringify(json));
    },

    //显示html5应用右上角图片
    showTopBarImage: function () {
        exec_syn("App", "showTopBarImage", "{}");
    },

    //隐藏标题栏图片
    hideTopBarImage: function () {
        exec_syn("App", "hideTopBarImage", "{}");
    },

    getScreen: function () {
        return exec_syn("App", "getScreen", null);
    },

    /**
     * 设置右上角图标及事件
     * @param path 跳转页面的绝对路径 例： /aaa/bbb/*.htm
     * @param type 图标类型 String billhistory列表
     * 都为空值时则隐藏右上角图标
     */
    setRightBtnBackground: function (path, type) {
        var json = {
            "path": path || '',
            "type": type || ''
        }
        exec_syn("App", "setRightBtnBackground", JSON.stringify(json));
    },

    /**
    * 隐藏title
    */
    dismissTopbar: function(){
        return exec_syn("App", "dismissTopbar", "{}");
    },

    /**
    * 显示title
    */
    showTopbar: function(){
        exec_syn("App", "showTopbar", "{}");
    },

    /**
     * 显示title
     */
    showBack: function(isShow){
    
        var json = {
            "isShow": isShow || '0'
        }
        exec_syn("App", "showBack", JSON.stringify(json));
    },

    showTabbBar: function(isShow){
        var json = {
        "isShow": isShow || '0'
        }
        exec_syn("App", "showTabbBar", JSON.stringify(json));
    },

   /**
    * 获取本地图片或调用摄像头
    */
    getImage: function () {
        return exec_syn("App", "getImage", null);
    },

    // 打开摄像头
    takePhoto: function () {
        return exec_syn("App", "takePhoto", null);
    },

    // 打开本地图片
    pickPicture: function () {
        return exec_syn("App", "pickPicture", null);
    },

    //获取城市编码
    getCityCode: function(){
        return exec_syn("App", "getCityCode", null);
    },

    // 获取地位信息
    getLocinformation:function(){
        return exec_syn("App","getLocinformation");
    },

    //获取城市
    getCity:function(){
         return exec_syn("App", "getCity", null);
    },

    //是否设置了手势密码isSetGesturePassword
    isSetGesturePassword:function(){
        return exec_syn("App","isSetGesturePassword",null);
    },

    //取消手势密码
    cancelGesturePassword:function(){
        return exec_syn("App","cancelGesturePassword",null);
    },

    //进入设置手势密码界面
    toGestureEditActivity:function(){
        return exec_syn("App","toGestureEditActivity",null);
    },

    /**
     * call alipay sdk pay. 调用SDK支付
     * @param productName    商品名称  String
     * @param productDescribe   商品描述   String
     * @param productOrder   商品订单   String
     * @param money  商品价钱，单位元    String
     * @param payType  支付产品类型    String
     */
    alipay:function(productName, productDescribe,productOrder, money , payType,alipay_success,alipay_fail){
        var json = {"productName": productName,"productDescribe":productDescribe , "productOrder": productOrder, "money": money,"payType":payType};
        return exec_asyn("App","alipay",JSON.stringify(json),alipay_success,alipay_fail);
    },

    /**
     * 微信支付
     * @param body    订单描述，暂为商品名  String
     * @param toaltFee   价钱，单位分   String
     * @param orderNo  订单号    String
     * @param payType  支付产品类型    String
     */
    weixinPay:function(body, toaltFee, orderNo, payType,weixin_success,weixin_fail){
        var json = {"body":body, "toaltFee":toaltFee,"orderNo":orderNo , "payType":payType};
        return exec_asyn("App","weixinPay",JSON.stringify(json),weixin_success,weixin_fail);
    },

    /**
     * 跳转到原生页面
     *
     * @param viewName -
     *            预定义的页面名称
     * @param args －
     *            bundle参数
     * @param callback -
     *            调用成功返回时调用的函数
     * @param fail －
     *            调用失败时执行的函数
     * @since 1.0.8
     */
    jumpToNativeView: function (viewName, args, callback, fail) {
        if (typeof args == 'undefined')
            args = {};
        var json = args;
        json["viewName"] = viewName;
        exec_asyn("App", "jumpToNativeView", JSON.stringify(json), callback,
            fail);
    },

    /**
     * 关闭按钮触发事件
     * @param orderNo
     */
    closeH5: function (orderNo) {
        var json = {
            orderNo: orderNo
        };
        exec_syn("App", "closeH5", JSON.stringify(json));
    },
    checkLogin : function(loginReturn) {//kiner
        var json = {
            "key": "loginInfo",
            "defValue": "",
            "prefname": "JIEJUETAG",
            "loginReturn": loginReturn
        };
        exec_syn("App","checkLogin",JSON.stringify(json));
        //exec_syn("App", "checkLogin", JSON.stringify(json));
    },
    checkLoginWithStatus : function(doSuccess) {
        var json = {
            "key": "loginInfo",
            "defValue": "",
            "prefname": "JIEJUETAG"
        };
        //alert(JSON.stringify(json));
        exec_asyn("App", "checkLoginWithStatus", JSON.stringify(json),doSuccess,function(){});
    },
    webviewReaload : function() {
        var json = {
        };
        exec_syn("App", "webviewReaload", null);
    },
    toFrontPage:function(){
        //alert("返回首页")
        return exec_syn("App","toFrontPage",null);
    },
    setLoginStatus:function(loginStatus){
        var json = {
            "loginStatus":loginStatus
        }
        var json_str = JSON.stringify(json);
        return exec_syn("App","setLoginStatus",json_str);
    },
    /**
     * 获取环境url
     */
    getUrl: function(){
        return exec_syn("App","getUrl",null);
    },

    setTopRightBar:function(args){
        exec_syn("App", "setTopRightBar", JSON.stringify(args));
    },
    hideTopRightBar:function(){
        exec_syn("App", "hideTopRightBar", "{}");
    },
    showTopRightBar:function(){
        exec_syn("App", "showTopRightBar", "{}");
    }
    

    
}

/*
 * native提供uitl。
 */

var util = {

    /*
     * js与Android交互，通过调用该接口实现base64编码。
     *
     * @params {JSON} data {id_no:1;name:'ztm';card:'6225'} @return {JSON}
     * result {id_no:1;name:"enRt";card:"NjIyNQ=="}
     */
    base64Encode: function (jsonobj) {

        if (typeof (jsonobj) == "undefined" || typeof (jsonobj) != "object") {
            return false;
        } else {
            var _resultJson = {};
            var _result = "";
            _result = exec_syn("UtilPlugin", "base64Encode", JSON
                .stringify(jsonobj));
            _resultJson = JSON.parse(_result);
            return _resultJson;
        }
    },
    /*
     * js与Android交互，通过调用该接口实现base64解码。
     *
     * @params {JSON} data {id_no:1;name:"enRt";card:"NjIyNQ=="} @return {JSON}
     * result {id_no:1;name:'ztm';card:'6225'}
     */
    base64Decode: function (jsonobj) {

        if (typeof (jsonobj) == "undefined" || typeof (jsonobj) != "object") {
            return false;
        } else {
            var _resultJson = {};
            var _result = "";
            _result = exec_syn("UtilPlugin", "base64Decode", JSON
                .stringify(jsonobj));
            _resultJson = JSON.parse(_result);
            return _resultJson;
        }
    }

};

// 键值对本地数据存储。
var Preference = {
    // 存储
    put: function (key, value, prefname) {
       // console.log("put-key:" + key + " value:" + value + " prefname:"+ prefname);
        var args = {
            "key": key,
            "value": value,
            "prefname": prefname
        };
        exec_syn("Preference", "put", JSON.stringify(args));
    },
    // 取值
    get: function (key, defValue, prefname) {
        //console.log("get-key:" + key + " defValue:" + defValue + " prefname:"+ prefname);
        var args = {
            "key": key,
            "defValue": defValue,
            "prefname": prefname
        };
        return exec_syn("Preference", "get", JSON.stringify(args));
    },
    // 存值，指定SP文件的名称 By Cloudy 2013/11/20
    putWithApp: function (key, value, spname) {
        this.put(key, value, spname);
    },
    // 取值， 指定SP文件的名称 By Cloudy 2013/11/20
    getWithApp: function (key, defValue, spname) {
        return this.get(key, defValue, spname);
    }
}


// android风格Dialog
var Dialog = {
    // 弹出等待对话框
    // title:对话框标题
    // msg:对话框内容
    // return : 动态分配的id，供取消等待
    showWaitDialog: function (title, msg) {
        var json = {
            'title': title,
            'msg': msg
        };
        return exec_syn("Dialog", "showWaitDialog", JSON.stringify(json));
    },

    // 消失对话框
    // id: showDialog调用后返回的id。
    dismissDialog: function (id) {
        var json = {
            'id': id
        };
        return exec_syn("Dialog", "dismissDialog", JSON.stringify(json));
    },
    // 弹出等待对话框
    // title:对话框标题
    // msg:对话框内容
    // return : 动态分配的id，供取消等待
    showProgressDialog: function (title, msg) {
        var json = {
            'title': title,
            'msg': msg
        };
        return exec_syn("Dialog", "showProgressDialog", JSON.stringify(json));
    },
    alert: function (msg) {
        window.alert(msg);
    }
}
// Toast提示。功能如同android Toast
var Toast = {
    LENGTH_LONG: 1,
    LENGTH_SHORT: 0,
    /*
     *
     * @params {String} text the text to make toast. @params {Int} duration
     * LENGTH_SHORT:short,LENGTH_LONG:long
     *
     */
    makeText: function (text, duration) {
        var json = {
            "text": text,
            "duration": duration
        }
        exec_syn("Toast", "makeText", JSON.stringify(json));
    }
}

// 联系人
var Contacts = {
    // 打开联系人
    openContacts: function (success, fail) {
        exec_asyn("Contacts", "openContacts", '{}', success, fail);
    },
    // 打电话
    tel: function (tel) {
        var json = {

            'tel': tel
        }
        return exec_syn("Contacts", "call", JSON.stringify(json));
    }
}

/**
 * HTML5与Android异步交互
 *
 */

var JIEJUEH5 = {
    idCounter: 0, //参数序列计数器
    INPUT_CMDS: {}, //入参服务与命令名
    INPUT_ARGS: {}, //入参的参数
    OUTPUT_RESULTS: {}, //输出的结果
    CALLBACK_SUCCESS: {}, //输出的结果成功时调用的方法
    CALLBACK_FAIL: {},    //输出的结果失败时调用的方法
    /*
     * exec/exec_asyn调用的方法
     * @params {JSONObject} cmd 		服务名和动作命令
     * @params {String} args			参数
     * @params {JS FUNCTION} success			成功时回调函数
     * @params {JS FUNCTION} fail			失败时回调函数
     */
    callNative: function (cmd, args, success, fail) {
        var key = "ID_" + (++this.idCounter);
        if(args==null){
           args = "{}";
        }

        this.INPUT_CMDS[key] = cmd;
        this.INPUT_ARGS[key] = args;
//        alert(cmd);
        window.ptvAction.setCmds(cmd, key);
        window.ptvAction.setArgs(args, key);
        if (typeof success != 'undefined') this.CALLBACK_SUCCESS[key] = success;
        if (typeof fail != 'undefined') this.CALLBACK_FAIL[key] = fail;
        var iframe = document.createElement("IFRAME");
        iframe.setAttribute("src", comTag+"://ready?id=" + key);
        document.documentElement.appendChild(iframe);
        iframe.parentNode.removeChild(iframe);
        iframe = null;

        return this.OUTPUT_RESULTS[key]; //同步调用时返回值
    },
    /*
     * 获取执行服务和动作
     * @params {String} key			队列标识
     */
    getInputCmd: function (key) {
        return this.INPUT_CMDS[key];
    },
    /*
     * 获取执行参数
     * @params {String} key			队列标识
     */
    getInputArgs: function (key) {
        return this.INPUT_ARGS[key];
    },
    /*
     * 回调返回结果函数
     * @params {String} result		后台处理的结果
     * @params {String} key			队列标识
     */

    callBackJs: function (result, key) {

        this.OUTPUT_RESULTS[key] = result;
        var obj = JSON.parse(result);
        var message = obj.message;

        try{
            JSON.parse(message);
        }catch(e){
            try{
                message = JSON.stringify(message);
            }catch(e){
                alert(e.message);
            }

        }

        var status = obj.status;
        if (status == 0) {
            if (typeof this.CALLBACK_SUCCESS[key] != "undefined"){
                setTimeout("JIEJUEH5.CALLBACK_SUCCESS['" + key + "']('" + message + "')", 0);
            }

        } else {
            if (typeof this.CALLBACK_FAIL[key] != "undefined")
                setTimeout("JIEJUEH5.CALLBACK_FAIL['" + key + "']('" + message + "')", 0);
        }
    }
};


var exec_syn = function (service, action, args) {
    var json = {
        "service": service,
        "action": action
    };

    var result_str = prompt(JSON.stringify(json), args);
    var result, status, message;
    try {
        result = JSON.parse(result_str);
    } catch (e) {
        console.error(e.message);
    }
    if (result) {
        status = result.status;
        message = result.message;
    }
    if (status == 0) {

        return message;
    } else {

        console.error("service:" + service + " action:" + action + " error:"+ message);

    }
}
// Webserver 端口
var port;

/*
 * Html5与Android异步交互接口
 * 
 * @params {String} service The name of the service to use @params {String}
 * action Action to be run in proxy @params {JSON} args Arguments to pass to the
 * method,it's maybe null if the method doesn't need arguments @params
 * {Function} success The success callback @params {Function} fail The fail
 * callback
 */
var exec_asyn = function (service, action, args, success, fail) {

     var json = {
         "service": service,
         "action": action
     };
     function doSuccess(result){
        try {
            result = JSON.parse(result);//如果是json字符串的话转成json字符串
            success(result);
        } catch (e) {
            console.error(e.message);
            success(result);//如果不是json字符串的话直接处理
            return null;
        }
    }

     //JIEJUEH5.callNative(JSON.stringify(json), args, success, fail);
     JIEJUEH5.callNative(JSON.stringify(json), args, doSuccess, fail);
}
