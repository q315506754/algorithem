var util = require('util');
function Base() {
    this.name = 'base';
    this.base = 1991;
    this.sayHello = function() {
        console.log('Hello ' + this.name);
    };
}
Base.prototype.showName = function() {
    console.log(this.name);
};
function Sub() {
    this.name = 'sub';
}
//util.inherits(constructor, superConstructor)是一个实现对象间原型继承 的函数。
util.inherits(Sub, Base);


var objBase = new Base();
objBase.showName();//base
objBase.sayHello();//Hello base
console.log(objBase);//Base {name = 'base',base = 1991, sayHello: [Function]}

var objSub = new Sub();
objSub.showName();//sub
// objSub.sayHello(); //TypeError: objSub.sayHello is not a function
console.log(objSub);//Sub{name = 'sub'}

// 仅仅继承了Base 在原型中定义的函数，而构造函数内部创造的 base 属 性和 sayHello 函数都没有被 Sub 继承。

// 同时，在原型中定义的属性不会被console.log(xx.toString) 作为对象的属性输出。


function Person() {
    this.name = 'byvoid';
    this.toString = function() {
        return this.name;
    };
}
var obj = new Person();
//util.inspect(object,[showHidden],[depth],[colors])是一个将任意对象转换 为字符串的方法，通常用于调试和错误输出。
// 它至少接受一个参数 object，即要转换的对象。
console.log(util.inspect(obj));
console.log(util.inspect(obj, true));



console.log(util.isArray(new Array()));//true
console.log(util.isRegExp(new RegExp('another regexp')));//true
console.log(util.isRegExp(/sdsdsds/));//true
console.log(util.isDate(new Date()));//true
console.log(util.isDate(Date()));//false
console.log(new Date());//2017-10-13T06:34:36.491Z
console.log( Date());//Fri Oct 13 2017 14:34:36 GMT+0800 (中国标准时间)
console.log(typeof new Date());//object
console.log(typeof  Date());//string
console.log(util.isError(new Error()));//true
console.log(util.isError(new TypeError()));//true
console.log(util.isError({ name: 'Error', message: 'an error occurred' }));//false


