
(function ($) {
    "use strict";
    var websites = [
        "Google", "NetEase", "Sohu", "Sina", "Sogou", "Baidu", "Tencent",
        "Taobao", "Tom", "Yahoo", "JavaEye", "Csdn", "Alipay"
    ];
    /* Intro Video*/
    $(document).ready(function () {
        function getdictionary(dicName, groupid) {
            $.ajax({
                type: "get",
                url: "http://119.3.19.171/tuozuo/xinruyi/v1/dict/" + dicName,
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
                                getlogList[i].id +
                                '">' +
                                getlogList[i].name +
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
        function getarea(type, code, id) {
            $.ajax({
                type: "get",
                url: "http://119.3.19.171/tuozuo/xinruyi/v1/search/area?areaLevel=" + type + "&areaCode=" + code,
                async: true,
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        if (type == 'city') {
                            $('.city').remove();
                            $('.district').remove();
                            $('#city').val('');
                            $('#district').val('');
                        } else {
                            $('.district').remove();
                            $('#district').val('');
                        }
                        var getlogList = data.data;
                        $("#" + id).append(
                            '<option class=' + type + ' value =""></option>'
                        );
                        for (var i = 0; i < getlogList.length; i++) {
                            $("#" + id).append(
                                '<option class=' + type + '  value ="' +
                                getlogList[i].areaCode +
                                '">' +
                                getlogList[i].areaName +
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
        getarea('province', '', 'province')
        getdictionary('industryType', 'industryType')
        $("#province").change(function (event) {
            console.log($("#province").val())
            if ($("#province").val() && $("#province").val() !== '') {
                getarea('city', $("#province").val(), 'city')

            }
        })
        $("#city").change(function (event) {
            console.log($("#city").val())
            if ($("#city").val() && $("#city").val() !== '') {
                getarea('district', $("#city").val(), 'district')

            }
        })
        $("#submitBtn").click(
            function () {
                console.log(232323)
                if ($("#companyName").val() == null || $("#companyName").val() == '') {
                    alert('公司名称必须填写！')
                    return
                }
                if ($("#industryType").val() == null || $("#industryType").val() == '') {
                    alert('请选择行业类型')
                    return
                }
                if ($("#contact").val() == null || $("#contact").val() == '') {
                    alert('联系方式必须填写')
                    return
                }
                if ($("#province").val() == null || $("#province").val() == '') {
                    alert('请选择省份')
                    return
                }
                if ($("#city").val() == null || $("#city").val() == '') {
                    alert('请选择市县')
                    return
                }
                if ($("#district").val() == null || $("#district").val() == '') {
                    alert('请选择区域')
                    return
                }
                $.ajax({
                    type: "post",
                    url: "http://119.3.19.171/tuozuo/xinruyi/v1/company/cooperation",
                    async: true,
                    dataType: "json",
                    contentType:"application/json;charset=utf-8",
                    data: JSON.stringify({
                        companyName: $("#companyName").val(),
                        contact: $("#contact").val(),
                        industryType: $("#industryType").val(),
                        province: $("#province").val(),
                        city: $("#city").val(),
                        district: $("#district").val()
                    }),
                    success: function (data) {
                        if (data.code == 0) {
                            alert("申请发送成功，请等待工作人员联系您！");
                            $("#companyName").val('')
                            $("#contact").val('')
                            $("#industryType").val('')
                            $("#province").val('')
                            $("#city").val('')
                            $("#district").val('')
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
        )
    });

})(jQuery); 