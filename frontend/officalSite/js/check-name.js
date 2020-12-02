(function ($) {
  "use strict";
  var websites = [
    "Google",
    "NetEase",
    "Sohu",
    "Sina",
    "Sogou",
    "Baidu",
    "Tencent",
    "Taobao",
    "Tom",
    "Yahoo",
    "JavaEye",
    "Csdn",
    "Alipay",
  ];
  /* Intro Video*/
  $(document).ready(function () {
    function docheckName(){
      $.ajax({
        type: "get",
        url:
          "http://119.3.19.171/tuozuo/organbiz/v1/name/verification?area=" +
          $("#area-Keyword").val() +
          "&industry=" +
          $("#industry-Keyword").val() +
          "&name=" +
          $("#keyword2").val(),
        async: true,
        dataType: "json",
        success: function (data) {
          $("#result-container").show();
          $("body").mLoading("hide");
          console.log(data);
          if (data.code == 0) {
            let htmlstr =
              '<div style="display: flex;"><div style="width: 50%;"><div style="font-size: 20px;font-weight: bold;">核对名称：' +
              $("#keyword2").val() +
              $("#industry-Keyword").val() +
              '</div><div style="padding-top: 10px;"><span style="font-size: 20px;font-weight: bold;">通过概率：</span><span style="font-size: 40px;font-weight: bold;color:';
            if (data.data.totalScores >= 80) {
              htmlstr += "green";
            } else if (
              data.data.totalScores < 80 &&
              data.data.totalScores >= 60
            ) {
              htmlstr += "green";
            } else {
              htmlstr += "red";
            }
            htmlstr +=
              ';">' +
              data.data.totalScores +
              '%</span></div><div style="padding-top: 10px;"><h3 class="widget-title">特别相似记录</h3><div class="tiny-border"></div>' +
              '<table cellspacing="0" class="shop_table shop_table_responsive shop-checkout"><tbody><tr><th class="product-name">名称</th><th class="product-price">重复度</th></tr>';
            for (let i of data.data.maxScoreRecords) {
              htmlstr +=
                '<tr class="cart_item"><td class="product-name">' +
                i.fullName +
                '</td><td class="product-total"><strong>' +
                i.totalMinusScore +
                "</strong></td></tr>";
            }
            htmlstr +=
              '</tbody></table></div></div><div id="chart" style="height: 450px;width: 50%"></div></div><div style="padding-top: 10px;"><h3 class="widget-title">分项重点记录</h3>' +
              '<div class="tiny-border"></div><table cellspacing="0" class="shop_table shop_table_responsive shop-checkout"><tbody><tr><th class="product-name">类别</th><th class="product-name">名称</th><th class="product-price">重复度</th>' +
              "</tr>";
            for (let i of data.data.pinyinDupRecords) {
              htmlstr +=
                '<tr class="cart_item"><td class="product-name">谐音重复度</td><td class="product-name">' +
                i.name +
                '</td><td class="product-total"><strong>' +
                i.score +
                "</strong></td></tr>";
            }
            for (let i of data.data.wordDupRecords) {
              htmlstr +=
                '<tr class="cart_item"><td class="product-name">字号重复度</td><td class="product-name">' +
                i.name +
                '</td><td class="product-total"><strong>' +
                i.score +
                "</strong></td></tr>";
            }
            for (let i of data.data.pinYinPosDupRecords) {
              htmlstr +=
                '<tr class="cart_item"><td class="product-name">谐音顺序</td><td class="product-name">' +
                i.name +
                '</td><td class="product-total"><strong>' +
                i.score +
                "</strong></td></tr>";
            }
            for (let i of data.data.wordPosRecords) {
              htmlstr +=
                '<tr class="cart_item"><td class="product-name">字号顺序</td><td class="product-name">' +
                i.name +
                '</td><td class="product-total"><strong>' +
                i.score +
                "</strong></td></tr>";
            }
            for (let i of data.data.industryDescRecords) {
              htmlstr +=
                '<tr class="cart_item"><td class="product-name">行业描述</td><td class="product-name">' +
                i.name +
                '</td><td class="product-total"><strong>' +
                i.score +
                "</strong></td></tr>";
            }
            htmlstr += "</tbody></table></div>";
            $("#result-content").html(htmlstr)
            var radar_chart = echarts.init(document.getElementById("chart"));
            var option = {
              title: {
                text: "重复度分布图",
              },
              tooltip: {},
              //    legend: {
              //      data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
              //    },
              radar: {
                // shape: 'circle',
                name: {
                  textStyle: {
                    color: "#fff",
                    backgroundColor: "#999",
                    borderRadius: 3,
                    padding: [3, 5],
                  },
                },
                indicator: [
                  { name: "字号重复度", max: 60 },
                  { name: "谐音重复度", max: 60 },
                  { name: "字号顺序", max: 60 },
                  { name: "谐音顺序", max: 60 },
                  { name: "行业描述", max: 60 },
                ],
                radius: 90,
              },
              series: [
                {
                  // name: '预算 vs 开销（Budget vs spending）',
                  type: "radar",
                  areaStyle: { normal: {} },
                  itemStyle: {
                    normal: {
                      color: "#a8bcd4",
                    },
                  },
                  data: [
                    {
                      value: [data.data.wordDupScores, data.data.pinyinDupScores, data.data.wordPosScores, data.data.pinYinPosScores, data.data.industryScores],
                      name: "名称重复度",
                    },
                    //  {
                    //     value : [5000, 14000, 28000, 31000, 42000, 21000],
                    //     name : '实际开销（Actual Spending）'
                    // }
                  ],
                },
              ],
            };
            radar_chart.setOption(option);
            $('html, body').animate({
              scrollTop: $("#result-container").offset().top
          }, 1000);
          }
          if (data.code != 0) {
            alert(data.msg);
          }
        },
        error: function () {
          $("body").mLoading("hide");
          alert("系统繁忙，请联系管理员");
        },
      });
    }
    function getUrlParam(name) {
      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
      var r = window.location.search.substr(1).match(reg);
      var strValue = "";
      if (r != null) {
        strValue = decodeURI(r[2])
      }
      return strValue;
    }
    if (getUrlParam("name") !== "" && getUrlParam("area") !== "" && getUrlParam("industry") !== "") {
      $("#area-Keyword").val(getUrlParam("area"))
      $("#industry-Keyword").val(getUrlParam("industry"))
      $("#keyword2").val(getUrlParam("name"))
      $("body").mLoading("show");
      docheckName()
    }
    let areaSearch = new areaSearchTip();
    // console.log(areaSearch)
    areaSearch.start({
      result: ".area-search-results",
      moniSelect: ".moni_select",
      ajaxUrl: "http://119.3.19.171//tuozuo/organbiz/v1/name/dict/area",
      maxLength: 2,
    });
    let industrySearch = new searchTip();
    industrySearch.start({
      result: ".industry-search-results",
      moniSelect: ".moni_select",
      ajaxUrl: "http://119.3.19.171//tuozuo/organbiz/v1/name/dict/industry",
      maxLength: 2,
    });
    $("#imgVer").hide();
    $("#result-container").hide();
    imgVer({
      el: '$("#imgVer")',
      width: "260",
      height: "116",
      img: ["images/to1.jpg", "images/to2.jpg", "images/to3.jpg"],
      success: function () {
        console.log(
          $("#area-Keyword").val(),
          $("#industry-Keyword").val(),
          $("#keyword2").val(),
          $("#wordNum").val(),
          $("#companyType").val(),
          $("#dictType").val()
        );
        //alert('执行登录函数');
        console.log("success");
        $("#imgVer").hide();
        $(".container_78").show();
        $("body").mLoading("show");
        docheckName()
      },
      error: function () {
        console.log("error");
        //alert('错误什么都不执行')
      },
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
            $("#" + groupid).append('<option value =""></option>');
            for (var i = 0; i < getlogList.length; i++) {
              $("#" + groupid).append(
                '<option value ="' +
                getlogList[i].superClass +
                '">' +
                (getlogList[i].subClass.length !== 0
                  ? getlogList[i].subClass[0]
                  : getlogList[i].superClass) +
                "</option>"
              );
            }
          }
          if (data.code != 0) {
            alert(data.msg);
          }
        },
        error: function () {
          alert("系统繁忙，请联系管理员");
        },
      });
    }
    // $("body").mLoading("true")
    getdictionary("type", "companyType");
    getdictionary("reference", "dictType");
    $("#to_next").on("click", function () {
      console.log("click");
      if ($("#area-Keyword").val() == null || $("#area-Keyword").val() == "") {
        alert("地区必须填写！");
        return;
      }
      if (
        $("#industry-Keyword").val() == null ||
        $("#industry-Keyword").val() == ""
      ) {
        alert("请选择行业类型！");
        return;
      }
      if ($("#keyword2").val() == null || $("#keyword2").val() == "") {
        alert("公司名称必须填写");
        return;
      }
      $(".container_78").hide();
      $("#imgVer").show();
    });
  });
})(jQuery);
