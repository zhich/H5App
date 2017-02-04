var comTag="H5App";

var App = {

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

    // 设置html5应用标题内容。html5应用的标题是由android实现。
    setTitle: function (title) {
        var json = {
            "title": title
        }
        exec_syn("App", "setTitle", JSON.stringify(json));
    },

    /**
     * 显示title
     */
    isShowBack: function(isShow){
        var json = {
            "isShow": isShow
        }
        exec_syn("App", "isShowBack", JSON.stringify(json));
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
    }
}

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

     H5App.callNative(JSON.stringify(json), args, doSuccess, fail);
}

/**
 * HTML5与Android异步交互
 *
 */
var H5App = {

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

        window.h5Action.setCmds(cmd, key);
        window.h5Action.setArgs(args, key);

        if (typeof success != 'undefined'){
            this.CALLBACK_SUCCESS[key] = success;
        }
        if (typeof fail != 'undefined') {
            this.CALLBACK_FAIL[key] = fail;
        }
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
                setTimeout("H5App.CALLBACK_SUCCESS['" + key + "']('" + message + "')", 0);
            }

        } else {
            if (typeof this.CALLBACK_FAIL[key] != "undefined"){
                setTimeout("H5App.CALLBACK_FAIL['" + key + "']('" + message + "')", 0);
            }
        }
    }
};