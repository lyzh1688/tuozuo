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
    
    let areaSearch = new areaSearchTip();
    let totalNum = 0;
    let currentpage = 1;
    let pagesize = 6;
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
        $.ajax({
          type: "get",
          url:
            "http://119.3.19.171/tuozuo/organbiz/v1/name/creation?area=" +
            $("#area-Keyword").val() +
            "&industry=" +
            $("#industry-Keyword").val() +
            "&source=" +
            $("#dictType").val() +
            "&preferWord=" +
            $("#keyword2").val() +
            "&isTwoWords=" +
            $("#wordNum").val() +
            "&type=" +
            $("#companyType").val() +
            "&pageNo=1&pageSize=" +
            pagesize,
          async: true,
          dataType: "json",
          success: function (data) {
            $("#result-container").show();
            $("body").mLoading("hide");
            $("#result-content").html("");
            console.log(data);
            if (data.code == 0) {
              totalNum = data.data.totalNum;
              currentpage = 1;
              let obj = [];
              for (let i of data.data.names) {
                let tmpstr =
                  '<div class="item-name "><p style="font-size: 18px;font-weight: bold;">全称：' +
                  i.fullName +
                  "</p>";
                for (let j in i.strokeNums) {
                  tmpstr +=
                    '<div class="txts-container"><div class="txts" style="background: url(images/bgtian.png);">' +
                    i.name[j] +
                    '</div><span style="padding:3px;">笔画：' +
                    i.strokeNums[j] +
                    "</span></div>";
                }
                tmpstr +=
                  '<div style="font-size: 18px;font-weight: bold;padding-top: 10px;">来源：</div><div style="font-size: 18px;font-weight: bold;padding: 10px;word-wrap: break-word;word-break:break-all;">' +
                  i.reference +
                  '</div><a class="to-check-name" data-name="' +
                  i.name +
                  '" data-area="' +
                  $("#area-Keyword").val() +
                  '" data-industry="' +
                  $("#industry-Keyword").val() +
                  '"><i class="fa fa-bookmark"></i>去核名</a></div>';
                obj.push(tmpstr);
              }
              $("#result-content").html(obj.join(""));
              $(".to-check-name").on("click", function () {
                // console.log($(this).context.dataset, $(".to-check-name"));
                window.open("company-helper-check-name.html?name="+$(this).context.dataset.name+"&area="+$(this).context.dataset.area+"&industry="+$(this).context.dataset.industry)
              });
              if (pagesize >= totalNum) {
                $("#change-group").hide();
              }
              $('html, body').animate({
                scrollTop: $("#result-container").offset().top
            }, 500);
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
    $("#wordNumContainer").hide();
    console.log($("#wordNum").val());
    $("#keyword2")
      .on("input focus", function () {
        var t = $(this).val();
        if (t == "") {
          $("#wordNum").val("true");
          $("#wordNumContainer").hide();
        } else {
          $("#wordNumContainer").show();
        }
      })
      .on("blur", function () {
        var t = /[\u4e00-\u9fa5]{1,10}/g,
          i = $("#keyword2").val().match(t);
        if (i) {
          $("#keyword2").val(i[0][0]);
          if (i[0][0] == "") {
            $("#wordNum").val("true");
            $("#wordNumContainer").hide();
          } else {
            $("#wordNumContainer").show();
          }
        }
      });
    $("#change-group").on("click", function () {
      currentpage += 1;
      $("body").mLoading("show");
      $.ajax({
        type: "get",
        url:
          "http://119.3.19.171/tuozuo/organbiz/v1/name/creation?area=" +
          $("#area-Keyword").val() +
          "&industry=" +
          $("#industry-Keyword").val() +
          "&source=" +
          $("#dictType").val() +
          "&preferWord=" +
          $("#keyword2").val() +
          "&isTwoWords=" +
          $("#wordNum").val() +
          "&type=" +
          $("#companyType").val() +
          "&pageNo=" +
          currentpage +
          "&pageSize=" +
          pagesize,
        async: true,
        dataType: "json",
        success: function (data) {
          $("#result-container").show();
          $("body").mLoading("hide");
          $("#result-content").html("");
          console.log(data);
          if (data.code == 0) {
            totalNum = data.data.totalNum;
            let obj = [];
            for (let i of data.data.names) {
              let tmpstr =
                '<div class="item-name " ><p style="font-size: 18px;font-weight: bold;">全称：' +
                i.fullName +
                "</p>";
              for (let j in i.strokeNums) {
                tmpstr +=
                  '<div class="txts-container"><div class="txts" style="background: url(images/bgtian.png);">' +
                  i.name[j] +
                  '</div><span style="padding:3px;">笔画：' +
                  i.strokeNums[j] +
                  "</span></div>";
              }
              tmpstr +=
                '<div style="font-size: 18px;font-weight: bold;padding-top: 10px;">来源：</div><div style="font-size: 18px;font-weight: bold;padding: 10px;word-wrap: break-word;word-break:break-all;">' +
                i.reference +
                '</div><a class="to-check-name" data-name="' +
                i.name +
                '" data-area="' +
                $("#area-Keyword").val() +
                '" data-industry="' +
                $("#industry-Keyword").val() +
                '"><i class="fa fa-bookmark"></i>去核名</a></div>'
              obj.push(tmpstr);
            }
            $("#result-content").html(obj.join(""));
            $(".to-check-name").on("click", function () {
                // console.log($(this).context.dataset, $(".to-check-name"));
                window.open("company-helper-check-name.html?name="+$(this).context.dataset.name+"&area="+$(this).context.dataset.area+"&industry="+$(this).context.dataset.industry)
              });
            if (totalNum - pagesize * currentpage <= pagesize) {
              $("#change-group").hide();
            }
            $('html, body').animate({
              scrollTop: $("#result-container").offset().top
          }, 500);
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
    });
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
      if ($("#companyType").val() == null || $("#companyType").val() == "") {
        alert("公司类型必须填写");
        return;
      }
      if ($("#dictType").val() == null || $("#dictType").val() == "") {
        alert("请选择词库类型");
        return;
      }
      $(".container_78").hide();
      $("#imgVer").show();
    });
    
  });
})(jQuery);
