var resetToCurrentDate = function ($this) {
    // $this.val(new Date().format(window.dateFormat));
    //+30s
    // var newTime =  new Date(new Date().getTime()+30000).format(window.dateFormat);

    //+2min
    var newTime =  new Date(new Date().getTime()+120000).format(window.dateFormat);
    $this.val(newTime);
}

function bindValideDateCheck($obj,df,cbWhenIllegal) {
    $obj.blur(function () {
        var $this = $(this);
        var str = $this.val();

        cbWhenIllegal = cbWhenIllegal || function () {
            $this.val("");
        };

        if (!isValidDateString(str,df)) {
            cbWhenIllegal();
        }
    });
}

function isValidDateString(str,df) {
    if (str.length == df.length) {
        return new Date(str).isValid();
    }
    return false;
}
function checkDateStrGtCur(str) {
    try {
        if(str!=null) {
            if (str.length == window.dateFormat.length) {
                var dt = new Date(str);
                if (dt.getTime() > new Date().getTime()) {
                    return true;
                }
            }
        }
    } catch (e) {
    }
    return false;
}

function diffStrDate(str1,str2) {
    return new Date(str1).diff(new Date(str2));
}

Date.prototype.format = function(format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "[hH]+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

Date.prototype.nextDays = function(days) {
    var date = new Date(this);
    date.setDate(date.getDate() + days);
    return date;
}
Date.prototype.isValid = function() {
    return !isNaN(this.getTime());
}
Date.prototype.diff = function(date2) {
    // console.log(date2);
    // console.log(this);
    return (date2- this) / (1000 * 60 * 60 * 24);
}