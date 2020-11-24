
(function ($) {
    "use strict";
    var websites = [
        "Google","NetEase", "Sohu", "Sina", "Sogou", "Baidu", "Tencent",
        "Taobao", "Tom", "Yahoo", "JavaEye", "Csdn", "Alipay"
    ];
    /* Intro Video*/
    $(document).ready(function () {
        $("#imgVer").hide()
        imgVer({
            el: '$("#imgVer")',
            width: '260',
            height: '116',
            img: [
                'images/to1.jpg',
                'images/to2.jpg',
                'images/to3.jpg'
            ],
            success: function () {
                //alert('执行登录函数');
                console.log("success")
                $("#imgVer").hide()
                $(".container_78").show()
            },
            error: function () {
                console.log("error")
                //alert('错误什么都不执行')
            }
        });
        $("#to_next").on('click', function () {
            console.log("click")
            $(".container_78").hide()
            $("#imgVer").show()
        })
    });

})(jQuery); 