Array.prototype.contains=function (s) {
    return this.indexOf(s)>=0
}
Array.prototype.intersect=function (arr) {
    let contains = false;
    for(let sval of this){
        if(arr.contains(sval)) {
            contains=true;
            break;
        }
    }
    return contains;
}
Array.prototype.allMatch=function (predi) {
    for(let sval of this){
        if(!predi(sval)){
           return false;
        }
    }
    return true;
}
Array.prototype.existDup=function () {
    for(let sval of this){
        if(this.indexOf(sval)!=this.lastIndexOf(sval)){
            return true;
        }
    }
    return false;
}

var CmdParser =  class {
    constructor(cmd) {
        this.cmd = cmd;
    }

    getCmdArr(){
       let arr =  this.cmd.replace(/\d+/g,' ').replace(/\s+/g,' ').trim();
        return arr.split(" ");
    }

    splitGradually(str){
        let ret =[];
        for(let i = str.length-2;i>=0;i--){
            let x = '';
            let y = '';
            for(let j=0;j<=i;j++){
                x += str.charAt(j);
            }
            for(let j=i+1;j<=str.length-1;j++){
                y += str.charAt(j);
            }

            let tArr = [Number.parseInt(x),Number.parseInt(y)];
            if(!tArr.existDup()){
                ret.push(tArr);
            }
        }
        return ret;
    }

    //include space
    getContinuantNumberArrayForCmd(maxNumber) {
        let rArr =  this.cmd.match(/\d*/g);
        let ret = [];
        for(let s of rArr) {
            if(s.length>0){
                ret=this.getContinuantNumberArray(s,maxNumber,ret);
            }
        }
        return ret;
    }

    //"13 523n".match(/\d*/g)
    //new  CmdParser('13 523n').getContinuantNumberArray(12)
    getContinuantNumberArray(ps,maxNumber,pArr) {
        // console.log(...arguments);
        let rCmd =  ps.replace(/\s+/g,"").trim();
        // console.log(rCmd);
        let arr = rCmd.match(/\d+/g);
        let [a] = arr;
        if (a){

            let ret = [];
            if(pArr){
                ret.push(...pArr);
            }
            let str='';
            var $this = this;

            let safeNoDupPush = function (str) {
                var n = Number.parseInt(str);

                if(n<=maxNumber && !ret.contains(n)){
                    ret.push(n);
                    return true;
                }else {

                    for(let sArr of $this.splitGradually(str)){
                        if(!sArr.intersect(ret) && sArr.allMatch(x=>x<=maxNumber)){
                            for(let sval of sArr){
                                ret.push(Number.parseInt(sval));
                            }
                            return true;
                        }
                    }
                }
            }


            for(let c of a){
                // console.log(c);
                let oldStr = str;
                str += c;

                let number = Number.parseInt(str);

                if(number>maxNumber){
                    //maxContinuant
                    if(safeNoDupPush(oldStr)){
                        str=c;
                    }
                }
            }

            if(str!=''){
                safeNoDupPush(str);
            }

            return ret;
        }
        return [];
    }

};
// new  CmdParser('123456789101112n').splitGradually("123456");
// new  CmdParser('123456789101112n').splitGradually("12");
// new  CmdParser('123456789101112n').splitGradually("12");
// new  CmdParser('1 2345n').getContinuantNumberArrayForCmd(12);
// new  CmdParser('123456789101112n').getContinuantNumberArrayForCmd(12);
// new  CmdParser('109876543211112n').getContinuantNumberArrayForCmd(12);
// new  CmdParser('7891012n').getContinuantNumberArrayForCmd(12);
// new  CmdParser('789101 2n').getContinuantNumberArrayForCmd(12);
// new  CmdParser('3 46791012n').getContinuantNumberArrayForCmd(12);
// new  CmdParser('3 4 6 79 10 1 2n').getCmdArr();
// new  CmdParser('ad b3 4 6 79 10 1 2n').getCmdArr();