

(function ($) {
    "use strict";
    var websites = [
        "Google", "NetEase", "Sohu", "Sina", "Sogou", "Baidu", "Tencent",
        "Taobao", "Tom", "Yahoo", "JavaEye", "Csdn", "Alipay"
    ];
    /* Intro Video*/
    $(document).ready(function () {
        let areaSearch = new areaSearchTip
        // console.log(areaSearch)
        areaSearch.start({
            result: ".area-search-results",
            moniSelect: ".moni_select",
            ajaxUrl: "http://119.3.19.171//tuozuo/organbiz/v1/name/dict/area",
            maxLength: 2
        })
        let industrySearch = new searchTip
        industrySearch.start({
            result: ".industry-search-results",
            moniSelect: ".moni_select",
            ajaxUrl: "http://119.3.19.171//tuozuo/organbiz/v1/name/dict/industry",
            maxLength: 2
        })
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
                console.log($("#area-Keyword").val(), $("#industry-Keyword").val(), $("#keyword2").val(), $("#wordNum").val(), $("#companyType").val(), $("#dictType").val())
                //alert('执行登录函数');
                console.log("success")
                $("#imgVer").hide()
                $(".container_78").show()
                $("body").mLoading("show")
                $.ajax({
                    type: "get",
                    url: "http://119.3.19.171/tuozuo/organbiz/v1/name/verification?area=" + $("#area-Keyword").val() + "&industry=" + $("#industry-Keyword").val()
                        + "&name=" + $("#keyword2").val(),
                    async: true,
                    dataType: "json",
                    success: function (data) {
                        $("body").mLoading("hide")
                        console.log(data)
                        if (data.code == 0) {
                            var getlogList = data.data;
                            $("#" + groupid).append(
                                '<option value =""></option>'
                            );
                            for (var i = 0; i < getlogList.length; i++) {
                                $("#" + groupid).append(
                                    '<option value ="' +
                                    getlogList[i].superClass +
                                    '">' +
                                    (getlogList[i].subClass.length !== 0 ? getlogList[i].subClass[0] : getlogList[i].superClass) +
                                    '</option>'
                                );
                            }
                        }
                        if (data.code != 0) {
                            alert(data.msg);
                        }
                    },
                    error: function () {
                        $("body").mLoading("hide")
                        alert("系统繁忙，请联系管理员");
                    }
                });
            },
            error: function () {
                console.log("error")
                //alert('错误什么都不执行')
            }
        });
        function getdictionary(dicName, groupid) {
            $.ajax({
                type: "get",
                url: "http://119.3.19.171//tuozuo/organbiz/v1/name/dict/" + dicName,
                async: true,
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        var getlogList = data.data;
                        $("#" + groupid).append(
                            '<option value =""></option>'
                        );
                        for (var i = 0; i < getlogList.length; i++) {
                            $("#" + groupid).append(
                                '<option value ="' +
                                getlogList[i].superClass +
                                '">' +
                                (getlogList[i].subClass.length !== 0 ? getlogList[i].subClass[0] : getlogList[i].superClass) +
                                '</option>'
                            );
                        }
                    }
                    if (data.code != 0) {
                        alert(data.msg);
                    }
                },
                error: function () {
                    alert("系统繁忙，请联系管理员");
                }
            });
        }
        // $("body").mLoading("true")
        getdictionary('type', 'companyType')
        getdictionary('reference', 'dictType')
        $("#to_next").on('click', function () {
            console.log("click")
            if ($("#area-Keyword").val() == null || $("#area-Keyword").val() == '') {
                alert('地区必须填写！')
                return
            }
            if ($("#industry-Keyword").val() == null || $("#industry-Keyword").val() == '') {
                alert('请选择行业类型！')
                return
            }
            if ($("#keyword2").val() == null || $("#keyword2").val() == '') {
                alert('公司名称必须填写')
                return
            }
            $(".container_78").hide()
            $("#imgVer").show()
        })
    });

})(jQuery); 