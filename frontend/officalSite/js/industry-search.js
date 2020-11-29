!function () {
    function SearchTip() {
        return _this = this,
            this
    }
    var that, $option, data = [], _config, _this;
    SearchTip.prototype.start = function (t) {
        var i = this;
        return $option = $.extend(!0, {
            result: ".search-results",
            moniSelect: ".moni_select",
            ajaxUrl: "js/data.js",
            activeClass: "active",
            maxLength: !1,
            $body: $("body")
        }, t),
            i.bindInput.call($.extend(!0, i, $option)),
            "absolute" != $($option.result).css("position") && "fixed" != $($option.result).css("position") ? $($option.result).css("position", "relative") : "",
            i.keyDown.call(i),
            this.getDefaultIndustry(),
            this
    }
        ,
        //字符串格式化
        String.prototype.format = function () {
            var values = arguments;
            return this.replace(/\{(\d+)\}/g, function (match, index) {
                if (values.length > index) {
                    return values[index];
                } else {
                    return "";
                }
            });
        },
        SearchTip.prototype.getDefaultIndustry = function () {
            var t = this;
            $.ajax({
                url: $option.ajaxUrl ? $option.ajaxUrl : "http://119.3.19.171//tuozuo/organbiz/v1/name/dict/industry",
                success: function (i) {
                    data = []
                    if (i.data.length !== 0) {
                        for (let j of i.data) {
                            data = [...data, ...j.subClass]
                        }
                    }
                    // console.log(i.data, typeof i.data),
                        t.createDefaultIndustry(i.data)
                }
            })
        }
        ,
        SearchTip.prototype.createDefaultIndustry = function (t) {
            var i = [];
            this.$defaultDom = $("<div>", {
                "class": "recomment_industry hide"
            }),
                i.push("    <div class='info'>"),
                t.forEach(function (t) {
                    i.push("        <dl>"),
                        i.push("            <dt>{0}</dt>".format(t.superClass)),
                        i.push("            <dd>"),
                        t.subClass.forEach(function (t) {
                            i.push("                <span data-id='{0}'>{0}</span>".format(t))
                        }),
                        i.push("            </dd>"),
                        i.push("        </dl>")
                }),
                i.push("    </div>"),
                this.$defaultDom.html(i.join("")),
                this.defaultIndustryClick()
        }
        ,
        SearchTip.prototype.defaultIndustryClick = function () {
            var t = this;
            this.$body.append(this.$defaultDom),
                $("dd span", this.$defaultDom).on("click", function () {
                    var i = $(this).data();
                    
                    $("#industry-Keyword").val(i.id).data("v", i.id).blur(),
                        $(t.result).find(".moni_select").hide(),
                        $(t.$defaultDom).addClass("hide")
                })
        }
        ,
        SearchTip.prototype.bindInput = function () {
            return _config = {
                li: "<li>",
                $ul: $("<ul>", {
                    "class": "result"
                }).css({}),
                $a: $("<a>")
            },
                this.$input = $("#industry-Keyword"),
                // console.log(this.$input),
                this.$input.on("input focus", function () {
                    
                    var t = $(this).val();

                    if (_this.getData(t),
                        $(this).data("v", ""),
                        $(_this.result).addClass("active"),
                        "" != t)
                        $(_this.$defaultDom).addClass("hide"),
                            $(_this.result).find(".moni_select").show();
                    else {
                        $(_this.result).find(".moni_select").hide();
                        var i = $(this).offset();
                        $(_this.$defaultDom).removeClass("hide").css({
                            left: i.left - 92,
                            top: i.top + 46
                        })
                    }
                }).on("blur", function () {
                    // console.log("1231",_this.$input)
                    var t = /[\u4e00-\u9fa5]{1,10}/g
                        , i = _this.$input.val().match(t);
                    $(_this.result).removeClass("active"),
                        _this.$input.val(i ? i[0] : ""),
                        setTimeout(function () {
                            $("body").triggerHandler("click"),
                                _this.$input.parent().removeClass("active")
                        }, 200)
                }),
                this.$input.siblings(".industry.arrow-down").on("click", function () {
                    $(_this.result).hasClass("active") || _this.$input.trigger("focus")
                }),
                this
        }
        ,
        SearchTip.prototype.getData = function (val) {
            if (data.length !== 0) {
                $(".result", _this.result).html("");
                var obj = [];
                $.each(data, function (t, i) {
                    if (i.includes(val) || val == "") {
                    // console.log(i.includes(val),i,val)
                        obj.push("<li data-id='1'>" + i + "</li>")
                    }
                }),
                    _config.$ul.show().append(obj.join("")),
                    // console.log( _config.$ul),
                    $(".result",_this.result).html(_config.$ul),
                    _this.bindClick()
            }
            // console.log("val",val)
            // $.get($option.ajaxUrl + "?q=" + val + "&count=10", function() {
            //     data = eval(arguments[0]),
            //     $(".result", _this.result).html("");
            //     var obj = [];
            //     $.each(data.data, function(t, i) {
            //     console.log(t, "|",i,"|")
            //         obj.push("<li data-id='1'>" + i + "</li>")
            //     }),
            //     _config.$ul.show().append(obj.join("")),
            //     $(".result", _this.result).html(_config.$ul),
            //     _this.bindClick()
            // })
        }
        ,
        SearchTip.prototype.bindClick = function () {
            $("li", _config.$ul).on("click", function () {
                var t = $(this);
                
                _config.$ul.parent().hide(),
                    $("#industry-Keyword").val(t.text()).data("v", JSON.stringify(t.text()))
            }),
                $("body").on("click", function (t) {
                    var i = $(t.target);
                    0 == i.closest(_this.result).length && (_config.$ul.parent().hide(),
                        _this.$defaultDom.addClass("hide"))
                })
                
        }
        ,
        SearchTip.prototype.keyDown = function (t) {
            var i = this;
            $(this.result).on("keyup", function (t) {
                switch (t.keyCode) {
                    case 38:
                        i.keyDown_up.call(i);
                        break;
                    case 40:
                        i.keyDown_down.call(i);
                        break;
                    case 13:
                        i.confirm.call(i)
                }
            })
        }
        ,
        SearchTip.prototype.keyDown_up = function () {
            var t = this
                , i = $("ul>li.active", this.result).index();
            $("ul>li", this.result).length,
                0 === i ? i = 0 : i -= 1,
                $("ul>li", this.result).eq(i).addClass(t.activeClass).siblings().removeClass(t.activeClass)
        }
        ,
        SearchTip.prototype.keyDown_down = function () {
            var t = this
                , i = $("ul>li.active", this.result).index()
                , s = $("ul>li", this.result).length;
            i === s - 1 ? i = s - 1 : i += 1,
                $("ul>li", this.result).eq(i).addClass(t.activeClass).siblings().removeClass(t.activeClass)
        }
        ,
        SearchTip.prototype.confirm = function () {
            var t = this
                , i = $("ul>li.active", this.result);
                
            i.index() != -1 && $("#industry-Keyword").val(i.text()).data("v", JSON.stringify(i.text())),
                _config.$ul.hide()
        }
        ,
        window.searchTip = SearchTip
}();
