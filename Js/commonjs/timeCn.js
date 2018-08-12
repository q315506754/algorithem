function timeCn(seconds) {
    var unit = ["年","月","天","时","分","秒"];
    var base = [1,12,30,24,60,60];
    var padZero = [0,0,0,1,1,1];
    var sec = parseInt(seconds);

    var retArr = [];
    for(var i=unit.length-1;i>=0;i--){
        if(i == 0) {
            retArr[i] =  sec;
        } else {
            retArr[i] =  sec % base[i];
            sec=Math.floor(sec/base[i]);
        }
    }

    var ret = "";
    var started = false;
    for(var i=0;i<retArr.length;i++){
        var one = retArr[i];

        if(one>0) {
            started = true;
        }

        if(started || (!started && i == retArr.length-1)) {
            var str = one < 10 && padZero[i] ? "0" : "";
            ret = ret + str + one + unit[i];
        }
    }

    return ret;
}