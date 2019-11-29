
//////////////////////////////////////
function Validator(opt) {
    this.opt = opt;
    var _this = this;


    //init
    //bind event
    (function () {
        var rules = opt.rules;
        for(var i=0;i<rules.length;i++){
            var rule = rules[i];

            var ruleType = rule.type || "text";
            var $obj = findObj(rule);

            // if(ruleType == 'text'){
            if($obj.size() > 0){

                $obj.data("_rule",rule);

                //失去焦点
                $obj.blur(function () {
                    // console.log(rule.name);
                    var $this = $(this);
                    var $interval;
                    // console.log($this.data("_rule").name);

                    if (!$interval) {
                        $interval = setInterval(function () {
                            // console.log($this.data("_rule").name);
                            // console.log($this.data("_rule"));

                            var rs = valiOne($this.data("_rule").name);
                            if (rs || !$this.is(":visible")) {
                                clearInterval($interval);
                            }
                        }, 500);
                    }

                    // setTimeout(function () {
                    //     valiOne($this.data("_rule").name);
                    // });
                });
            }
        }
    })();



    function validate() {
        var rules = opt.rules;
        var ret = true;

        for(var i=0;i<rules.length;i++){
            var rule = rules[i];

            var t = valiOne(rule.name);
            if(!t){
                ret = false;
            }
        }

        return ret;
    }

    function clear(name) {
        var rule = findRuleByName(name);
        var $obj = findObj(rule);
        if (rule.clear) {
            rule.clear($obj);
        }
    }
    function clearAll() {
        var rules = opt.rules;

        for(var i=0;i<rules.length;i++){
            var rule = rules[i];
            clear(rule.name);
        }
    }
    function findObj(rule) {
        var selector = rule.selector || "input[name='" + rule.name + "']";
        var $obj = opt.container.find(selector);
        return $obj;
    }
    function findRuleByName(name) {
        var rules = opt.rules;
        var ret = true;

        for(var i=0;i<rules.length;i++){
            var rule = rules[i];

            if(name == rule.name){
                return rule;
            }
        }
        return {};
    }

    function valiOne(name) {
        try{
            var rule = findRuleByName(name);
            var $obj = findObj(rule);

            var ruleType = rule.type || "text";

            var v = $obj.val();

            var checkEmpty =  rule.checkEmpty || function () {
                return $.trim(v)=="";
            }

            var checkAll = function () {
                if(checkEmpty()) {
                    if(rule.whenEmpty) {
                        return !!rule.whenEmpty($obj);
                    }
                }

                if(rule.rules) {
                    var crules = rule.rules || [];
                    for(var i=0;i<crules.length;i++){
                        var cruleOne = crules[i];

                        //正整数检测
                        if(cruleOne.type == 'integer') {
                            // console.log('integer');
                            if(!checkEmpty() && !/^\d+$/.test(v)){
                                if(cruleOne.error) {
                                    cruleOne.error($obj);
                                    return false;
                                }
                            }
                        }

                        //邮箱检测
                        if(cruleOne.type == 'email') {
                            // console.log('integer');
                            // var reg =/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                            var reg =/^\S*$/;
                            if(!checkEmpty() && !/reg/.test(v)){
                                if(cruleOne.error && !cruleOne.error($obj)) {
                                    return false;
                                }
                            }
                        }

                        //手机检测
                        if(cruleOne.type == 'mobile') {
                            // console.log('integer');
                            // var reg = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/ ;
                            var reg = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/ ;
                            if(!checkEmpty() && !reg.test(v)){
                                if(cruleOne.error && !cruleOne.error($obj)) {
                                    return false;
                                }
                            }
                        }

                        //自定义检测
                        if(cruleOne.type == 'custom') {

                            // console.log('integer');
                            if(!cruleOne.check($obj,v)){
                                if(cruleOne.error && !cruleOne.error($obj)) {
                                    return false;
                                }
                            }
                        }
                    }

                }

                return true;
            }


            if(!checkAll()){
                return false;
            } else {
                if(rule.clear) {
                    rule.clear($obj);
                }
            }
        }catch (e){
            return false;
        }
        return true;
    }

    return {
        validate:validate
        ,clear:clear
        ,clearAll:clearAll
        ,valiOne:valiOne
    }
}


//////////////////////////////////////
