$(document).ready(function(){
     App.isShowBack(false);
     App.setTitle('首页');
});

$('#alipay').on('click',function(){
     var productName = '产品名称';
     var productDescribe = '产品描述';
     var productOrder = '产品订单';
     var money = '100';
     var payType = '支付类型';
     App.alipay(productName, productDescribe, productOrder, money, payType, function (result) {
            var data = {
                payResult: result,
                productName: productName,
                productDescribe: productDescribe,
                productOrder: productOrder,
                money: money,
                payType: payType
            };
            var alipay_result =  JSON.stringify(data);
            alert('支付结果：' + alipay_result);
        }
     );
});