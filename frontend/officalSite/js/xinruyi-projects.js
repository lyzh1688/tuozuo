
(function ($) {
    "use strict";
    var websites = [
        "Google","NetEase", "Sohu", "Sina", "Sogou", "Baidu", "Tencent",
        "Taobao", "Tom", "Yahoo", "JavaEye", "Csdn", "Alipay"
    ];
    /* Intro Video*/
    $(document).ready(function () {
    $("#projectName").autocomplete({
        minLength: 0,
        source: function( request, response ) {
            console.log("init")
            $.ajax({
                    url : "http://119.3.19.171/tuozuo/xinruyi/v1/portal/project?queryCnt=20&projectName="+($("#projectName").val()?$("#projectName").val():''),
                    type : "get",
                    async: true,
                    dataType : "json",
                success: function( data ) {
                      response( $.map( data.data, function( item ) {
                            return {
                              label: item.name,
                              value: item.id
                            }
                      }));
                }
           });
        },
        focus: function( event, ui ) {
            console.log("focus")
            $("#projectName").val( ui.item.label );
            $("#projectId").val( ui.item.value );
              return false;
            },
        select: function( event, ui ) {
            $("#projectName").val( ui.item.label );
            $("#projectId").val( ui.item.value );
            return false;
        }

    });
        var pageNo = 1;
        var pageSize = 10;
        var pages = 0;
        //获取日志信息
        function loadData(pageNo, pageSize) {
            //每次重新从API数据接口获取数据都要先清除原先表格 <tr>的内容
            $(".cart_item").remove();
            $.ajax({
                type: "get",
                url: "http://119.3.19.171/tuozuo/xinruyi/v1/portal/project/list?pageNo=" + pageNo + "&pageSize=" + pageSize+"&industryType="+($("#industryType").val()?$("#industryType").val():'')
                +"&budget="+($("#budget").val()?$("#budget").val():'')+"&requirementStatus="+($("#projectStatus").val()?$("#projectStatus").val():'')
                +"&projectId="+($("#projectId").val()?$("#projectId").val():''),
                async: true,
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        var getlogList = data.data.projects;
                        //共多少条
                        var totalCount = data.data.total;
                        //共多少页
                        pages = Math.ceil(data.data.total / pageSize);

                        for (var i = 0; i < getlogList.length; i++) {
                            $("#logList").append(
                                '<tr class="cart_item"><td class="product-thumbnail" width=80vw><img width=80vw height=80vw src="' +
                                getlogList[i].companyLogo +
                                '"></td><td data-title="Product" class="product-name" width=350px><div style="font-size: large;font-weight: bold;">' +
                                getlogList[i].projectName +
                                '</div><div><span style="font-size: small">' +
                                getlogList[i].companyName +
                                '</span><span style="font-size: smaller;margin-left: 5px;">' +
                                getlogList[i].industryName +
                                '</span></div><div style="width:300px;padding: 5px;font-size: 8px;color: rgb(175, 175, 175);display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;text-overflow:ellipsis;">' +
                                getlogList[i].projectDesc +
                                '</div></td><td data-title="Price" class="product-price"><span class="amount">' +
                                getlogList[i].budget +
                                '万元</span></td><td data-title="Quantity" class="product-quantity"><span class="quantity">' +
                                getlogList[i].staffNum +
                                '人</span></td><td data-title="Total" class="product-subtotal"><div class="amount">' +
                                getlogList[i].beginDate + '~' + getlogList[i].endDate +
                                '</div><div class="amount">周期:' +
                                getlogList[i].projectCycle +
                                '（个月）</div></td><td data-title="Total" class="product-subtotal"><div class="amount">' +
                                getlogList[i].projectStatusDesc +
                                '</div></td></tr>'
                            );
                        }
                        displayFooter(totalCount, pageNo, pages);
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
        loadData(pageNo, pageSize);
        function getdictionary(dicName,groupid){
            $.ajax({
                type: "get",
                url: "http://119.3.19.171/tuozuo/xinruyi/v1/dict/" + dicName,
                async: true,
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        var getlogList = data.data;
                        $("#"+groupid).append(
                            '<option value =""></option>'
                        );
                        for (var i = 0; i < getlogList.length; i++) {
                            $("#"+groupid).append(
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
        function getbudget(){
            $.ajax({
                type: "get",
                url: "http://119.3.19.171/tuozuo/xinruyi/v1/dict/budget",
                async: true,
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        var getlogList = data.data;
                        $("#budget").append(
                            '<option value =""></option>'
                        );
                        for (var i = 0; i < getlogList.length; i++) {
                            $("#budget").append(
                                '<option value ="' +
                                getlogList[i].name +
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
        getdictionary('industryType','industryType')
        getbudget()
        getdictionary('projectStatus','projectStatus')
        //展示foot左面信息
        function displayFooter(totalCount, pageNo, pages) {
            var newText = '共' + totalCount + '条，' + '第' + pageNo + '页，' + '共' + pages + '页';
            $("#summary").text(newText);
        }

        //跳转页码
        $("input[name='page_num']").keydown(function (e) {
            if (e.keyCode == 13) {
                $("#jump").click();
            }
        });
        $("#jump").click(function () {
            var goPage = $("input[name='page_num']").val();
            if (goPage >= 1 && goPage <= pages && goPage != pageNo) {
                pageNo = goPage;
                loadData(pageNo, pageSize);
            } else {
                return false;
            }
        });
        //首页
        $("#firstPage").click(function () {
            pageNo = 1;
            loadData(pageNo, pageSize);
        });
        $("#search").click(function () {
            pageNo = 1;
            loadData(pageNo, pageSize);
        });
        //最后一页
        $("#lastPage").click(function () {
            pageNo = pages;
            loadData(pageNo, pageSize);
        });
        //上一页
        $("#previousPage").click(function () {
            if (pageNo == 1) {
                return false;
            } else {
                pageNo--;
                loadData(pageNo, pageSize);
            }
        });
        //下一页
        $("#nextPage").click(function () {
            console.log($("#industryType").val())
            if (pageNo == pages) {
                return false;
            } else {
                pageNo++;
                loadData(pageNo, pageSize);
            }
        });
    });

})(jQuery); 