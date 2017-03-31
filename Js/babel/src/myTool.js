// import 'babel-polyfill';
var d = (x => x * 2)(1)
console.log(d);

function bar(x = 2, y = x) {
    return [x, y];
}
console.log(bar()); // [2, 2]

// 报错
// let x = x;

var tmp = new Date();

function f() {
    console.log(tmp);
    if (false) {
        var tmp = 'hello world';
    }
}

f(); // undefined

// CommonJS的写法
// require('system.global/shim')();
//
// // ES6模块的写法
// import shim from 'system.global/shim'; shim();


// CommonJS的写法
// var global = require('system.global')();

// ES6模块的写法
// import getGlobal from 'system.global';
// const global2 = getGlobal();

// CommonJS模块
// let { stat, exists, readFile } = require('fs');

// 等同于
// let _fs = require('fs');
// let stat = _fs.stat;
// let exists = _fs.exists;
// let readfile = _fs.readfile;


// ES6模块
// import { stat, exists, readFile } from 'fs';
// import语句会执行所加载的模块，因此可以有下面的写法。
//上面代码加载了两次lodash，但是只会执行一次。
// import { firstName as name11, lastName, year} from './module1';
// console.log(name11);
// console.log(lastName);
// console.log(year);


import * as module1 from './module1';
console.log(module1.firstName);
console.log(module1.lastName);
console.log(module1.year);
console.log(module1.multiplyV1(3,2));


console.log('-------------destructing-----------------');
{
    let [a, b, c] = [1, 2, 3];
    console.log(a,b,c);

    let [x, y = 'b'] = ['a', undefined];
    console.log(x,y);

    let { foo, bar ,baz } = { foo: "aaa", bar: "bbb" };
    console.log(foo,bar,baz);

    let { foo:foo111,  bar:bar111} = { foo: "aaa", bar: "bbb" };
    console.log(foo111,bar111);
}
{let obj = {
        p: [
            'Hello',
            { y: 'World' }
        ]
    };

    let { p: [x, { y }] } = obj;
    console.log(x,y);

}
{
    let obj = {};
    let arr = [];

    ({ foo: obj.prop, bar: arr[0] } = { foo: 123, bar: true });

    console.log(obj);
    console.log(arr);

    var { message: msg = 'Something went wrong' } = {};
    // msg // "Something went wrong"
    console.log(msg);

    var {x:x1 = 3} = {x: undefined};
    // x1 // 3
    console.log(x1);
    var {x:x2 = 3} = {x: null};
    // x2 // null
    console.log(x2);
}
{
    // 错误的写法 因为JavaScript引擎会将{x}理解成一个代码块，从而发生语法错误。
    // 只有不将大括号写在行首，避免JavaScript将其解释为代码块，才能解决这个问题。
    let x;
    // {x} = {x: 1};
    // 正确的写法
    ({x} = {x: 1});
    console.log(x);
// SyntaxError: syntax error

    let { log, sin, cos } = Math;
    console.log(log,sin,cos);

    let arr = [1, 2, 3];
    let {0 : first, [arr.length - 1] : last} = arr;
    console.log(first,last);
}

{
    const [a, b, c, d, e] = 'hello';
    console.log(a, b, c, d, e,len);
    let {length:len} = 'hello';
    console.log(len);
}
{
    let {toString: s1} = 123;
    s1 === Number.prototype.toString // true

    let {toString: s2} = true;
    s2 === Boolean.prototype.toString // true

    //解构赋值的规则是，只要等号右边的值不是对象或数组，就先将其转为对象。
    // 由于undefined和null无法转为对象，所以对它们进行解构赋值，都会报错。
    // let { prop: x } = undefined; // TypeError
    // let { prop: y } = null; // TypeError
}
{
    function add([x, y]){
        return x + y;
    }

    let z = add([1, 2]); // 3
    console.log(z);

    let n = [[1, 2], [3, 4]].map(([a, b]) => a + b);
    // [ 3, 7 ]
    console.log(n);

    function move({x = 0, y = 0} = {}) {
        return [x, y];
    }

    move({x: 3, y: 8}); // [3, 8]
    move({x: 3}); // [3, 0]
    move({}); // [0, 0]
    move(); // [0, 0]

    function move2({x, y} = { x: 0, y: 0 }) {
        console.log([x, y]);
        return [x, y];
    }

    move2({x: 3, y: 8}); // [3, 8]
    move2({x: 3}); // [3, undefined]
    move2({}); // [undefined, undefined]
    move2(); // [0, 0]

    function move3({px:x, py:y} = { px:0, py:0 }) {
        console.log("move3:"+[x, y]);
        return [x, y];
    }

    move3({x: 3, y: 8}); //,
    move3({x: 3}); // ,
    move3({}); //,
    move3(); // [0, 0]

  var n1=  [1, undefined, 3].map((x = 'yes') => x);
// [ 1, 'yes', 3 ]
  var n2=  [1, undefined, 3].map((x = 'yes') => x);
// [ 1, 'yes', 3 ]
    console.log(n1);
    console.log(n2);
}

{
    let { x=0, y=0 } = {x: 3};
    console.log(x,y);
}

{
    // （1）交换变量的值
    let x = 1;
    let y = 2;

    [x, y] = [y, x];
    console.log(x,y);

    // （2）从函数返回多个值
    // 返回一个数组
    function example() {
        return [1, 2, 3];
    }
    let [a, b, c] = example();
    console.log(a,b,c);

// 返回一个对象
    function example2() {
        return {
            foo: 1,
            bar: 2
        };
    }
    let { foo, bar } = example2();
    console.log(foo, bar );
}
{
    // /（3）函数参数的定义
    // 参数是一组有次序的值
    function f1([x, y, z]) {  }
    f1([1, 2, 3]);

// 参数是一组无次序的值
    function f2({x, y, z}) { }
    f2({z: 3, y: 2, x: 1});
}
{
    // （4）提取JSON数据
    // 解构赋值对提取JSON对象中的数据，尤其有用。

    let jsonData = {
        id: 42,
        status: "OK",
        data: [867, 5309]
    };

    let { id, status, data: number } = jsonData;

    console.log(id, status, number);
    // 42, "OK", [867, 5309]

    // （5）函数参数的默认值
    //
    // jQuery.ajax = function (url, {
    //     async = true,
    //     beforeSend = function () {},
    //     cache = true,
    //     complete = function () {},
    //     crossDomain = false,
    //     global = true,
    //     // ... more config
    // }) {
    //     // ... do stuff
    // };

}
{
    // （6）遍历Map结构
    // 任何部署了Iterator接口的对象，都可以用for...of循环遍历。
    // Map结构原生支持Iterator接口，配合变量的解构赋值，获取键名和键值就非常方便。

    var map = new Map();
    map.set('firstKey', 'helloValue');
    map.set('secondKey', 'worldValue');

    for (let [key, value] of map) {
        console.log(key + " iss " + value);
    }

// first is hello
// second is world
//     如果只想获取键名，或者只想获取键值，可以写成下面这样。

// 获取键名
    for (let [key] of map) {
        // ...
        console.log(key);
    }

// 获取键值
    for (let [,value] of map) {
        // ... d
        console.log(value);
    }

    // （7）输入模块的指定方法
    // 加载模块时，往往需要指定输入哪些方法。解构赋值使得输入语句非常清晰。
    // const { SourceMapConsumer, SourceNode } = require("source-map");
}



console.log('-------------string-----------------');
{
    console.log("\u{20BB7}");
    // "𠮷"

    console.log("\u{41}\u{42}\u{43}");
    // "ABC");

    var s = "𠮷";

    console.log(s.length); // 2
     console.log(s.charAt(0) );// ''
     console.log(s.charAt(1) );// ''
     console.log(s.charCodeAt(0) );// 55362
     console.log(s.charCodeAt(1) );// 57271

    for (let codePoint of 'foo') {
        console.log(codePoint)
    }

    console.log('x'.repeat(3) );// "xxx"
   console.log( 'hello'.repeat(2)); // "hellohello"
    console.log('na'.repeat(0)); // ""
    console.log('na'.repeat(2.9)); // ""

    // 普通字符串
    // `In JavaScript '\n' is a line-feed.`

// 多行字符串
//         `In JavaScript this is
//  not legal.`

    console.log(`string text line 1
string text line 2`);

    // 字符串中嵌入变量
    var name = "Bob", time = "today";

    console.log( `Hello ${name}, how are you ${time}?`);


    var x = 1;
    var y = 2;

    console.log( `${x} + ${y} = ${x + y}`);
// "1 + 2 = 3"

        console.log( `${x} + ${y * 2} = ${x + y * 2}`);
// "1 + 4 = 5"

    var obj = {x: 1, y: 2};
    console.log( `${obj.x + obj.y}`);
// 3

    function fn() {
        return "Hello World";
    }

    console.log( `foo ${fn()} bar`);

    // 写法一
    let str33 = 'return ' + '`Hello ${name}!`';
    let func33 = new Function('name', str33);
    console.log(str33);
    console.log( func33('Jack')); // "Hello Jack!"

// 写法二
    let str44 = '(name) => `Hello ${name}!`';
    console.log(str44);
    let func44 = eval.call(null, str44);
    console.log( func44('Jack') );// "Hello Jack!"
}
{
    //模板函数
    var a = 5;
    var b = 10;

    function tag(s, v1, v2) {
        console.log(s[0]);
        console.log(s[1]);
        console.log(s[2]);
        console.log(v1);
        console.log(v2);

        return "OK";
    }
    tag`Hello ${ a + b } world ${ a * b}`;
    // "Hello "
// " world "
// ""
// 15
// 50
// "OK"

    //“标签模板”的一个重要应用，就是过滤HTML字符串，防止用户输入恶意内容。

    // 标签模板的另一个应用，就是多语言转换（国际化处理）。



    function tag2(strings) {
        console.log(strings);
        console.log(strings.raw[0]);
        // "First line\\nSecond line"
    }

    tag2`First line\nSecond line`
    // 上面代码中，t

    console.log(String.raw`Hi\n${2+3}!`);//Hi\n5!
    console.log(String.raw`Hi\\n`);

    console.log(  String.raw({ raw: 'test' }, 0, 132, 32,345,23));//t0e132s32t
}

console.log('-------------number-----------------');
{
    console.log(Number('0b111'));//8进制
    console.log(Number('0o10') );//10进制

    Number.isFinite(15); // true
    Number.isFinite(0.8); // true
    Number.isFinite(NaN); // false
    Number.isFinite(Infinity); // false
    Number.isFinite(-Infinity); // false
    Number.isFinite('foo'); // false
    Number.isFinite('15'); // false
    Number.isFinite(true); // false
    Number.isNaN(NaN) // true
    Number.isNaN(15) // false
    Number.isNaN('15') // false
    Number.isNaN(true) // false
    Number.isNaN(9/NaN) // true
    Number.isNaN('true'/0) // true
    Number.isNaN('true'/'true') // true

    // ES5的写法
    parseInt('12.34') // 12
    parseFloat('123.45#') // 123.45

// ES6的写法
    Number.parseInt('12.34') // 12
    Number.parseFloat('123.45#') // 123.45
    // 这样做的目的，是逐步减少全局性方法，使得语言逐步模块化。
    Number.isInteger(25) // true
    Number.isInteger(25.0) // true ，在Jav aScript内部，整数和浮点数是同样的储存方法，所以3和3.0被视为同一个值。
    Number.isInteger(25.1) // false
    Number.isInteger("15") // false
    Number.isInteger(true) // false


    //Math.trunc方法用于去除一个数的小数部分，返回整数部分。
    Math.trunc(4.1) // 4
    Math.trunc(4.9) // 4
    Math.trunc(-4.1) // -4
    Math.trunc(-4.9) // -4
    Math.trunc(-0.1234) // -0

    //Math.sign方法用来判断一个数到底是正数、负数、还是零。
    Math.sign(-5) // -1
    Math.sign(5) // +1
    Math.sign(0) // +0
    Math.sign(-0) // -0
    Math.sign(NaN) // NaN
    Math.sign('foo'); // NaN
    Math.sign();      // NaN

    //Math.cbrt方法用于计算一个数的立方根。
    Math.cbrt(-1) // -1
    Math.cbrt(0)  // 0
    Math.cbrt(1)  // 1
    Math.cbrt(2)  // 1.2599210498948734

    // JavaScript的整数使用32位二进制形式表示，Math.clz32方法返回一个数的32位无符号整数形式有多少个前导0。
    //count leading zero bits in 32-bit binary representations of a number“
    Math.clz32(0) // 32
    Math.clz32(1) // 31
    Math.clz32(1000) // 22
    Math.clz32(0b01000000000000000000000000000000) // 1
    Math.clz32(0b00100000000000000000000000000000) // 2
    Math.clz32(0) // 32
    Math.clz32(1) // 31
    Math.clz32(1 << 1) // 30
    Math.clz32(1 << 2) // 29
    Math.clz32(1 << 29) // 2
    Math.clz32(3.2) // 30 对于小数，Math.clz32方法只考虑整数部分。
    Math.clz32(3.9) // 30
    Math.clz32() // 32
    Math.clz32(NaN) // 32
    Math.clz32(Infinity) // 32
    Math.clz32(null) // 32
    Math.clz32('foo') // 32
    Math.clz32([]) // 32
    Math.clz32({}) // 32
    Math.clz32(true) // 31

//    Math.hypot方法返回所有参数的平方和的平方根。
    Math.hypot(3, 4);        // 5
    Math.hypot(3, 4, 5);     // 7.0710678118654755
    Math.hypot();            // 0
    Math.hypot(NaN);         // NaN
    Math.hypot(3, 4, 'foo'); // NaN
    Math.hypot(3, 4, '5');   // 7.0710678118654755
    Math.hypot(-3);          // 3


    // Math.expm1(x)返回ex - 1，即Math.exp(x) - 1。
    Math.expm1(-1) // -0.6321205588285577
    Math.expm1(0)  // 0
    Math.expm1(1)  // 1.718281828459045

    // Math.log1p(x)方法返回1 + x的自然对数，即Math.log(1 + x)。如果x小于-1，返回NaN。
    Math.log1p(1)  // 0.6931471805599453
    Math.log1p(0)  // 0
    Math.log1p(-1) // -Infinity
    Math.log1p(-2) // NaN

    // Math.log10(x)返回以10为底的x的对数。如果x小于0，则返回NaN。
    Math.log10(2)      // 0.3010299956639812
    Math.log10(1)      // 0
    Math.log10(0)      // -Infinity
    Math.log10(-2)     // NaN
    Math.log10(100000) // 5

    // Math.log2(x)返回以2为底的x的对数。如果x小于0，则返回NaN。
    Math.log2(3)       // 1.584962500721156
    Math.log2(2)       // 1
    Math.log2(1)       // 0
    Math.log2(0)       // -Infinity
    Math.log2(-2)      // NaN
    Math.log2(1024)    // 10
    Math.log2(1 << 29) // 29

    // let a = 2;
    // a **= 2;
// 等同于 a = a * a;
    // ES2016 新增了一个指数运算符（**）。
    // console.log(a);// 4
    // console.log( 2 ** 3 )// 8
}

console.log('-------------Array-----------------');
{

//    Array.from方法用于将两类对象转为真正的数组：类似数组的对象（array-like object）和可遍历（iterable）的对象
    let arrayLike = {
        '0': 'a',
        '1': 'b',
        '2': 'c',
        length: 3
    };

// ES5的写法
    var arr1 = [].slice.call(arrayLike); // ['a', 'b', 'c']

// ES6的写法
    let arr2 = Array.from(arrayLike); // ['a', 'b', 'c']

    //字符串和Set结构都具有Iterator接口，因此可以被Array.from转为真正的数组。
    console.log(Array.from('hello'));//["h", "e", "l", "l", "o"]

    let namesSet = new Set(['a', 'b'])
    Array.from(namesSet) // ['a', 'b']


    // arguments对象
    function foo() {
        var args = Array.from(arguments);
        // ...
    }

    // arguments对象
    function foo2() {
        var args = [...arguments];
    }

    // 所谓类似数组的对象，本质特征只有一点，即必须有length属性。
    // 因此，任何有length属性的对象，都可以通过Array.from方法转为数组，
    // 而此时扩展运算符就无法转换。
    console.log(Array.from({ length: 3 }));// [ undefined, undefined, undefined ]

    Array.from(arrayLike, x => x * x);
// 等同于
    Array.from(arrayLike).map(x => x * x);

    Array.from([1, 2, 3], (x) => x * x);
// [1, 4, 9]

    Array.from([1, , 2, , 3], (n) => n || 0)
// [1, 0, 2, 0, 3]

    Array.from({ length: 2 }, () => 'jack') //初始化一个数组 相当于fill
// ['jack', 'jack']


    Array() // []
    Array(3) // [, , ,]
    Array(3, 11, 8) // [3, 11, 8]
    Array.of(3, 11, 8) // [3,11,8]
    Array.of(3) // [3]
    Array.of(3).length // 1
    Array.of() // []
    Array.of(undefined) // [undefined]
    Array.of(1) // [1]
    Array.of(1, 2) // [1, 2]


//     target（必需）：从该位置开始替换数据。
// start（可选）：从该位置开始读取数据，默认为0。如果为负值，表示倒数。
// end（可选）：到该位置前停止读取数据，默认等于数组长度。如果为负值，表示倒数。
    console.log( [1, 2, 3, 4, 5].copyWithin(0, 3));
// // [4, 5, 3, 4, 5]
//
//     // 将3号位复制到0号位
    console.log( [1, 2, 3, 4, 5].copyWithin(0, 3, 4));
// // [4, 2, 3, 4, 5]
//
//     // -2相当于3号位，-1相当于4号位
//     [1, 2, 3, 4, 5].copyWithin(0, -2, -1)
// // [4, 2, 3, 4, 5]
    console.log([1, 2, 3, 4, 5].copyWithin(0, -2, -1));

    // find方法，用于找出第一个符合条件的数组成员。
    console.log([1, 4, -5, 10].find((n) => n < 0));
// -5

    // 依次为当前的值、当前的位置和原数组。
    var f = [1, 5, 10, 15].find(function(value, index, arr) {
        return value > 9;
    }) // 10
    console.log(f);

    // 如果所有成员都不符合条件，则返回-1。
    var fi = [1, 5, 10, 15].findIndex(function(value, index, arr) {
        return value > 9;
    }) // 10
    console.log(fi);

    console.log(['a', 'b', 'c'].fill(7));
    // [7, 7, 7]

    console.log(new Array(3).fill(7));
// [7, 7, 7]

    console.log(['a', 'b', 'c'].fill(7, 1, 2));
// ['a', 7, 'c']

    console.log("keys:"+['a', 'b'].keys());
    for (let index of ['a', 'b'].keys()) {
        console.log(index);
    }
// 0
// 1

    console.log("values:"+['a', 'b'].values());
    for (let elem of ['a', 'b'].values()) {
        console.log(elem);
    }
// 'a'
// 'b'

    console.log("entries:"+['a', 'b'].entries());
    for (let [index, elem] of ['a', 'b'].entries()) {
        console.log(index, elem);
    }
// 0 "a"
// 1 "b"

    let letter = ['a', 'b', 'c'];
    let entries = letter.entries();
    console.log(entries.next().value); // [0, 'a']
    console.log(entries.next().value); // [1, 'b']
    console.log(entries.next().value); // [2, 'c']

    console.log(0 in [undefined, undefined, undefined] );// true
   console.log( 0 in [, , ,] );// false


//ES5对空位的处理，已经很不一致了，大多数情况下会忽略空位。
//     forEach(), filter(), every() 和some()都会跳过空位。
// map()会跳过空位，但会保留这个值
//     join()和toString()会将空位视为undefined，而undefined和null会被处理成空字符串。
    // forEach方法

    // ES6则是明确将空位转为undefined。
    console.log(Array.from(['a',,'b']));
// [ "a", undefined, "b" ]

    console.log("...:"+[...['a',,'b']]);
    // [ "a", undefined, "b" ]

    console.log([,'a','b',,].copyWithin(2,0) );// [,"a",,"a"]

    let arr = [, ,];
    for (let i of arr) {
        console.log(1);
    }
// 1

//     // entries()
    console.log([...[,'a'].entries()]); // [[0,undefined], [1,"a"]]
    console.log([[,'a'].entries()]); // [{}]
//
// // keys()
   console.log( [...[,'a'].keys()]); // [0,1]
//
// // values()
    console.log([...[,'a'].values()]); // [undefined,"a"]
//
// // find()
   console.log( [,'a'].find(x => true)); // undefined
//
// // findIndex()
   console.log( [,'a'].findIndex(x => true)); // 0
}

console.log('-------------Function-----------------');
{
    function Point(x = 0, y = 0) {
        this.x = x;
        this.y = y;
    }

    var p = new Point();
    console.log(p); // { x: 0, y: 0 }

    let x = 99;
    function foo(p = x + 1) {
        console.log(p);
    }

    foo() // 100

    x = 100;
    foo() // 101
}
{
    function foo({x, y = 5}) {
        console.log(x, y);
    }

    foo({}) // undefined, 5
    foo({x: 1}) // 1, 5
    foo({x: 1, y: 2}) // 1, 2
    // foo() // TypeError: Cannot read property 'x' of undefined

    function fetch(url, { body = '', method = 'GET', headers = {} } = {}) {
        console.log(method);
    }

    fetch('http://example.com', {})
// "GET"

    fetch('http://example.com')
}
{
    // 例一
    function f(x = 1, y) {
        return [x, y];
    }
    f() // [1, undefined]
    f(2) // [2, undefined])
    // f(, 1) // 报错
    f(undefined, 1) // [1, 1]
}
{
    // 例二
    function f(x, y = 5, z) {
        return [x, y, z];
    }

    f() // [undefined, 5, undefined]
    f(1) // [1, 5, undefined]
    // f(1, ,2) // 报错
    f(1, undefined, 2) // [1, 5, 2]
    console.log(f(1, null, 2) );// [1, null, 2]
}
{
    //函数的length属性，将返回没有指定默认值的参数个数。
    console.log((function (a) {}).length); // 1
    console.log((function (a = 5) {}).length );// 0
    console.log((function (a, b, c = 5) {}).length );// 2
    console.log((function (a, b= 5, c ) {}).length );// 1
    console.log((function (a= 5, b, c ) {}).length );// 0


}
{
    var x = 1;

    function f(x, y = x) {
        console.log(y);
    }

    f(2) // 2
}
{
    function throwIfMissing() {
        throw new Error('Missing parameter');
    }

    function foo(mustBeProvided = throwIfMissing()) {
        return mustBeProvided;
    }

    // foo()
// Error: Missing parameter

    foo(123)
// Ok
}
{
    // rest 参数之后不能再有其他参数（即只能是最后一个参数），否则会报错。
    // function f(a, ...b, c) {
    // // ...
    // }
    console.log((function(a) {}).length ); // 1
    console.log((function(...a) {}).length ); // 0
   console.log((function(a, ...b) {}).length ); // 1
}

console.log('-------------spread ...-----------------');
{
    console.log(...[1, 2, 3]);
    console.log([1, 2, 3]);
    // console.log([...document.querySelectorAll('div')]);

    // 该运算符主要用于函数调用。 展开数组
    function push(array, ...items) {
        array.push(...items);
    }

    function add(x, y) {
        return x + y;
    }

    var numbers = [4, 38];
    console.log(add(...numbers) );// 42

    // ES5的写法
    function f(x, y, z) {
        // ...
    }
    var args = [0, 1, 2];
    f.apply(null, args);

// ES6的写法
    function f(x, y, z) {
        // ...
    }
    var args = [0, 1, 2];
    f(...args);

    // ES5
    new (Date.bind.apply(Date, [null, 2015, 1, 1]))
// ES6
    new Date(...[2015, 1, 1]);
}
{
    // 扩展运算符的应用
    // （1）合并数组
    // ES5
    let more = [4,5,6];
    console.log([1, 2].concat(more));
// ES6
    console.log([1, 2, ...more]);

    var arr1 = ['a', 'b'];
    var arr2 = ['c'];
    var arr3 = ['d', 'e'];

// ES5的合并数组
    console.log(arr1.concat(arr2, arr3));
// [ 'a', 'b', 'c', 'd', 'e' ]

// ES6的合并数组
   console.log( [...arr1, ...arr2, ...arr3]);
// [ 'a', 'b', 'c', 'd', 'e' ]

// （2）与解构赋值结合
    // ES5
    // a = list[0], rest = list.slice(1)
// ES6
//     [a, ...rest] = list

    // const [first, ...rest] = [1, 2, 3, 4, 5];
    // first // 1
    // rest  // [2, 3, 4, 5]
    //
    // const [first, ...rest] = [];
    // first // undefined
    // rest  // []:
    //
    // const [first, ...rest] = ["foo"];
    // first  // "foo"
    // rest   // []

    // 如果将扩展运算符用于数组赋值，只能放在参数的最后一位，否则会报错。
    // const [...butLast, last] = [1, 2, 3, 4, 5];
    // // 报错
    //
    //     const [first, ...middle, last] = [1, 2, 3, 4, 5];
    // // 报错

// （4）字符串
//     扩展运算符还可以将字符串转为真正的数组。

    console.log([...'hello']);
// [ "h", "e", "l", "l", "o" ]

// （5）实现了Iterator接口的对象
//     任何Iterator接口的对象，都可以用扩展运算符转为真正的数组。

    // var nodeList = document.querySelectorAll('div');
    // var array = [...nodeList];

    // 对于那些没有部署Iterator接口的类似数组的对象，扩展运算符就无法将其转为真正的数组。
let arrayLike = {
    '0': 'a',
    '1': 'b',
    '2': 'c',
    length: 3
};

// TypeError: Cannot spread non-iterable object.
//     let arr = [...arrayLike];

// （6）Map和Set结构，Generator函数
    let map = new Map([
        [1, 'one'],
        [2, 'two'],
        [3, 'three'],
    ]);

    let arr = [...map.keys()]; // [1, 2, 3]
    console.log(arr);

    var go = function*(){
        yield 1;
        yield 2;
        yield 3;
    };

    console.log([...go()] );// [1, 2, 3]


    // 如果对没有iterator接口的对象，使用扩展运算符，将会报错。
    // var obj = {a: 1, b: 2};
    // let arr = [...obj]; // TypeError: Cannot spread non-iterable object
}
{
    var f = function () {};

// ES5
    f.name // ""

// ES6
    f.name // "f"

    console.log((new Function).name); // "anonymous"


    function foo() {};
    console.log(foo.bind({}).name); // "bound foo"

    console.log((function(){}).bind({}).name); // "bound "
}
console.log('-------------Rambda-----------------');
{
    const full = ({ first, last }) => first + ' ' + last;

// 等同于
    function full2(person) {
        return person.first + ' ' + person.last;
    }
}
{
    //es7
    //bind
//     var method = obj::obj.foo;
// // 等同于
//     var method2= ::obj.foo;
//
//     let log = ::console.log;
// // 等同于
//     var log2= console.log.bind(console);
}
console.log('-------------Object-----------------');
{
    var foo = 'bar';
    var baz = {foo};
    // baz // {foo: "bar"}
    console.log(baz);

// 等同于
//     var baz = {foo: foo};

    function f(x, y) {
        return {x, y};
    }
// 等同于
    function f2(x, y) {
        return {x: x, y: y};
    }
    console.log(f(3,4));

    var o = {
        method() {
            return "Hello!";
        },
        method2(dd) {
            return "Hello!"+dd;
        }
    };

// 等同于
//     var o = {
//         method: function() {
//             return "Hello!";
//         }
//     };
    console.log(o.method());
    console.log(o.method2("asdasd"));

    // CommonJS模块输出变量，就非常合适使用简洁写法。
    // module.exports = { getItem, setItem, clear };

    // var cart = {
    //     _wheels: 4,
    //
    //     get wheels () {
    //         return this._wheels;
    //     },
    //
    //     set wheels (value) {
    //         if (value < this._wheels) {
    //             throw new Error('数值太小了！');
    //         }
    //         this._wheels = value;
    //     }
    // }
    // console.log(cart.wheels(99));
    // console.log(cart.wheels());
    var obj = {
            * m(){
        yield 'hello world';
        }
    };
    console.log(obj.m());
}
{
    let propKey = 'foo';

    //ES6 允许字面量定义对象时，用方法二（表达式）作为对象的属性名，即把表达式放在方括号内。
    let obj = {
        [propKey]: true,
        ['a' + 'bc']: 123
    };
    console.log(obj);

    var lastWord = 'last word';

    var a = {
        'first word': 'hello',
        [lastWord]: 'world'
    };

    console.log(a['first word']); // "hello"
    console.log(a[lastWord]); // "world"
    console.log( a['last word'] );// "world"
}
{
    let obj = {
        ['h' + 'ello']() {
            return 'hi';
        }
    };

    console.log(obj.hello()); // hi

}
{
    const obj = {
        get foo() {},
        set foo(x) {}
    };
    //obj.foo.name
    // TypeError: Cannot read property 'name' of undefined

    const descriptor = Object.getOwnPropertyDescriptor(obj, 'foo');

    console.log(descriptor.get.name );// "get foo"
    console.log(descriptor.set.name );// "set foo"
}
{
    const key1 = Symbol('description');
    const key2 = Symbol();
    let obj = {
        [key1]() {},
        [key2]() {},
    };
    console.log(obj[key1]);
    console.log(obj[key2]);
    console.log(key1);
    console.log(key2);
    console.log(obj);
}
{
    Object.is('foo', 'foo')
// true
    Object.is({}, {})
// false

    +0 === -0 //true
    NaN === NaN // false

    Object.is(+0, -0) // false
    Object.is(NaN, NaN) // true
}
{
    var target = { a: 1 };

    var source1 = { b: 2 };
    var source2 = { c: 3 };

    Object.assign(target, source1, source2);
    console.log(target); // {a:1, b:2, c:3}
    console.log(source1); // {b:2}
    console.log(source2); // { c:3}

    console.log(Object.assign(2));//[Number: 2]
    // console.log(Object.assign(undefined));// 报错
    // console.log(Object.assign(null));// 报错

    // 其他类型的值（即数值、字符串和布尔值）不在首参数，也不会报错。
    // 但是，除了字符串会以数组形式，拷贝入目标对象，其他值都不会产生效果。
    // 这是因为只有字符串的包装对象，会产生可枚举属性。
    var v1 = 'abc';
    var v2 = true;
    var v3 = 10;

    var obj = Object.assign({}, v1, v2, v3);
    console.log(obj); // { "0": "a", "1": "b", "2": "c" }

    // Object.assign方法实行的是浅拷贝，而不是深拷贝。
    // 也就是说，如果源对象某个属性的值是对象，那么目标对象拷贝得到的是这个对象的引用。

    var obj1 = {a: {b: 1}};
    var obj2 = Object.assign({}, obj1);

    obj1.a.b = 2;//;
    console.log(obj2.a.b); // 2

    console.log(Object.defineProperty({}, 'invisible', {
        enumerable: false,
        value: 'hello'
    }));

    console.log(Object(true));// {[[PrimitiveValue]]: true}
    console.log(Object(10) ); //  {[[PrimitiveValue]]: 10}
   console.log( Object('abc') );// {0: "a", 1: "b", 2: "c", length: 3, [[PrimitiveValue]]: "abc"}
}
{
    // （1）为对象添加属性
    class Point {
        constructor(x, y) {
            Object.assign(this, {x, y});
        }
    }
    var point = new Point(44,55);
    console.log(point);

    //（2）为对象添加方法
    Object.assign(Point.prototype, {
        someMethod(arg1, arg2) {
          console.log('someMethod');
        },
        anotherMethod() {
            console.log('anotherMethod');
        }
    });

    point.someMethod();
    point.anotherMethod();
    console.log(point);

    // （3）克隆对象
    function clone(origin) {
        return Object.assign({}, origin);
    }

    var clonePoint = clone(point);
    console.log(clonePoint);
    // clonePoint.someMethod(); //TypeError: clonePoint.someMethod is not a function
    // clonePoint.anotherMethod(); //TypeError: clonePoint.anotherMethod is not a function

    function cloneExtend(origin) {
        let originProto = Object.getPrototypeOf(origin);
        return Object.assign(Object.create(originProto), origin);
    }

    // 如果想要保持继承链，可以采用下面的代码。能克隆它继承的值
    var clonePoint2 = cloneExtend(point);
    console.log(clonePoint2);
    clonePoint2.someMethod();
    clonePoint2.anotherMethod();

// （4）合并多个对象

// （5）为属性指定默认值
    const DEFAULTS = {
        logLevel: 0,
        outputFormat: 'html'
    };

    // 由于存在深拷贝的问题，DEFAULTS对象和options对象的所有属性的值，最好都是简单类型，不要指向另一个对象。
    // 否则，DEFAULTS对象的该属性很可能不起作用。
    function processContent(options) {
        options = Object.assign({}, DEFAULTS, options);
        console.log(options);
        // ...
    }
    processContent({sdd:232});
}
{
    const DEFAULTS = {
        url: {
            host: 'example.com',
            port: 7070
        },
    };

    function processContent(options) {
        options = Object.assign({}, DEFAULTS, options);
        console.log(options);
        // ...
    }

    processContent({ url: {port: 8000} });//{ url: { port: 8000 } }
}
{
    // 属性的可枚举性
    let obj = { foo: 123 };
    console.log(Object.getOwnPropertyDescriptor(obj, 'foo'));

//     ES5有三个操作会忽略enumerable为false的属性。
//
// for...in循环：只遍历对象自身的和继承的可枚举的属性
//     Object.keys()：返回对象自身的所有可枚举的属性的键名
//     JSON.stringify()：只串行化对象自身的可枚举的属性

    // ES6新增了一个操作Object.assign()，会忽略enumerable为false的属性，只拷贝对象自身的可枚举的属性。

// 。实际上，引入enumerable的最初目的，就是让某些属性可以规避掉for...in操作。
    console.log(Object.getOwnPropertyDescriptor(Object.prototype, 'toString').enumerable);
// false

   console.log( Object.getOwnPropertyDescriptor([], 'length').enumerable);
// false

    // 另外，ES6规定，所有Class的原型的方法都是不可枚举的。
    console.log(Object.getOwnPropertyDescriptor(class {foo() {}}.prototype, 'foo').enumerable);
// false

    // console.log({a:111,b:222}.keys()); //Error
    console.log(Object.keys({a:111,b:222}));//[ 'a', 'b' ]
    console.log(['a','b'].keys());//{}
    for(let a of ['a','b'].keys()){
        console.log(a);//0 \r\n 1
    }
}
{
    // （1）for...in
    //
    //     for...in循环遍历对象自身的和继承的可枚举属性（不含Symbol属性）。
    //
    // （2）Object.keys(obj)
    //
    //     Object.keys返回一个数组，包括对象自身的（不含继承的）所有可枚举属性（不含Symbol属性）。
    //
    // （3）Object.getOwnPropertyNames(obj)
    //
    //     Object.getOwnPropertyNames返回一个数组，包含对象自身的所有属性（不含Symbol属性，但是包括不可枚举属性）。
    //
    // （4）Object.getOwnPropertySymbols(obj)
    //
    //     Object.getOwnPropertySymbols返回一个数组，包含对象自身的所有Symbol属性。
    //
    // （5）Reflect.ownKeys(obj)
    //
    //     Reflect.ownKeys返回一个数组，包含对象自身的所有属性，不管是属性名是Symbol或字符串，也不管是否可枚举。
    //
    // 以上的5种方法遍历对象的属性，都遵守同样的属性遍历的次序规则。
    //
    // 首先遍历所有属性名为数值的属性，按照数字排序。
    // 其次遍历所有属性名为字符串的属性，按照生成时间排序。
    // 最后遍历所有属性名为Symbol值的属性，按照生成时间排序。
    // Reflect.ownKeys({ [Symbol()]:0, b:0, 10:0, 2:0, a:0 })
    // // ['2', '10', 'b', 'a', Symbol()]
}
console.log("------------Prototype---------------");
{
    // es6的写法
    var obj = {
        method: function() {}
    };
    obj.__proto__ = {a:11};
    console.log(obj);

// es5的写法
    var obj2 = Object.create({a:22});
    obj2.method = function() { };

// 用法
    var o = Object.setPrototypeOf({a:123}, null);
    console.log(o);

}
{
    let proto = {};
    let obj = { x: 10 };
    Object.setPrototypeOf(obj, proto);

    proto.y = 20;
    proto.z = 40;

     console.log(obj.x );// 10
    console.log( obj.y );// 20
     console.log(obj.z );// 40

    function Rectangle() {
        // ...
    }

    var rec = new Rectangle();

    Object.getPrototypeOf(rec) === Rectangle.prototype
// true

    Object.setPrototypeOf(rec, Object.prototype);
    Object.getPrototypeOf(rec) === Rectangle.prototype;
// false
}
{
    //es 2017
    // let { x, y, ...z } = { x: 1, y: 2, a: 3, b: 4 };
    // console.log(x); // 1
    // console.log(y );// 2
    // console.log(z );// { a: 3, b: 4 }
}
console.log("------------Symbol---------------");
{
    // 它是JavaScript语言的第七种数据类型，
    // 前六种是：Undefined、Null、布尔值（Boolean）、字符串（String）、数值（Number）、对象（Object）。

    let s = Symbol();

    console.log(typeof s);
// "symbol"

    const obj = {
        toString() {
            return 'abc';
        }
    };
    const sym = Symbol(obj);
    console.log(sym); // Symbol(abc)
}
{
    // 没有参数的情况
    var s1 = Symbol();
    var s2 = Symbol();

    s1 === s2 // false

// 有参数的情况
    var s1 = Symbol('foo');
    var s2 = Symbol('foo');

    s1 === s2 // false

    var sym = Symbol('My symbol');

    // "your symbol is " + sym
// TypeError: can't convert symbol to string
//         `your symbol is ${sym}`
// TypeError: can't convert symbol to string

    // 另外，Symbol值也可以转为布尔值，但是不能转为数值。

var sym = Symbol();
    Boolean(sym) // true
    !sym  // false

    if (sym) {
        // ...
    }

    // Number(sym) // TypeError
    // sym + 2 // TypeError
}
{
    var mySymbol = Symbol();

// 第一种写法
    var a1 = {};
    a1[mySymbol] = 'Hello!';

// 第二种写法
    var a2 = {
        [mySymbol]: 'Hello!'
    };

// 第三种写法
    var a3 = {};
    Object.defineProperty(a3, mySymbol, { value: 'Hello!' });

// 以上写法都得到同样结果
   console.log( a1[mySymbol]); // "Hello!"
   console.log( a2[mySymbol]); // "Hello!"
   console.log( a3[mySymbol]); // "Hello!"

    var mySymbol = Symbol();
    var a = {};

     a.mySymbol = 'Hello!';
    console.log( a[mySymbol]); // undefined
    console.log( a['mySymbol'] );// "Hello!"
    console.log(a);
}
{
    const COLOR_RED    = Symbol();
    const COLOR_GREEN  = Symbol();

    function getComplement(color) {
        switch (color) {
            case COLOR_RED:
                return COLOR_GREEN;
            case COLOR_GREEN:
                return COLOR_RED;
            default:
                throw new Error('Undefined color');
        }
    }
    console.log(getComplement(COLOR_RED));
    // console.log(getComplement("COLOR_RED")); //Error('Undefined color');
}
{
    let a = {k:2};
    var mySymbol = Symbol('mySymbol');
    a[mySymbol] = 123123;
    console.log(a);
    console.log(Object.getOwnPropertyDescriptor(a, 'mySymbol'));//undefined
    console.log(Object.getOwnPropertyDescriptor(a, mySymbol));//ok
    var objectSymbols = Object.getOwnPropertySymbols(a);
    console.log(objectSymbols);

    var foo = Symbol("foo");
    Object.defineProperty(a, foo, {
        value: "foobar",
    });

    for (var i in a) {
        console.log(i); // k
    }

    console.log(Object.getOwnPropertyNames(a));;//[ 'k' ]

    console.log(Object.getOwnPropertySymbols(a));//[ Symbol(mySymbol), Symbol(foo) ]

    console.log(Reflect.ownKeys(a));//[ 'k', Symbol(mySymbol), Symbol(foo) ]

}
{
    var s1 = Symbol.for('foo');
    var s2 = Symbol.for('foo');

    console.log(s1 === s2); // true
}{
    var s1 = Symbol('foo');
    var s2 = Symbol('foo');

    console.log(s1 === s2 );// false

    // 比如，如果你调用Symbol.for("cat")30次，每次都会返回同一个 Symbol 值，但是调用Symbol("cat")30次，
    // 会返回30个不同的Symbol值。


}
{
    // Symbol.keyFor方法返回一个已登记的 Symbol 类型值的key。
    var s1 = Symbol.for("foo");
    Symbol.keyFor(s1) // "foo"

    var s2 = Symbol("foo");
    Symbol.keyFor(s2) // undefined
}
{
    //内11个内置的Symbol值 向语言内部使用的方法。
    //Symbol.hasInstance § ⇧
    class MyClass {
        [Symbol.hasInstance](foo) {
            return foo instanceof Array;
        }
    }

    console.log([1, 2, 3] instanceof new MyClass() );// true

    class Even {
        static [Symbol.hasInstance](obj) {
            return Number(obj) % 2 === 0;
        }
    }

    1 instanceof Even // false
    2 instanceof Even // true
    12345 instanceof Even // false


    //Symbol.isConcatSpreadable
    let arr1 = ['c', 'd'];
    ['a', 'b'].concat(arr1, 'e') // ['a', 'b', 'c', 'd', 'e']
    arr1[Symbol.isConcatSpreadable] // undefined

    let arr2 = ['c', 'd'];
    arr2[Symbol.isConcatSpreadable] = false;
    ['a', 'b'].concat(arr2, 'e') // ['a', 'b', ['c','d'], 'e']

    // 类似数组的对象也可以展开，但它的Symbol.isConcatSpreadable属性默认为false，必须手动打开。
    let obj = {length: 2, 0: 'c', 1: 'd'};
    ['a', 'b'].concat(obj, 'e') // ['a', 'b', obj, 'e']

    obj[Symbol.isConcatSpreadable] = true;
    ['a', 'b'].concat(obj, 'e') // ['a', 'b', 'c', 'd', 'e']


    class A1 extends Array {
        constructor(args) {
            super(args);
            this[Symbol.isConcatSpreadable] = true;
        }
    }
    class A2 extends Array {
        constructor(args) {
            super(args);
            this[Symbol.isConcatSpreadable] = false;
        }
    }
    let a1 = new A1();
    a1[0] = 3;
    a1[1] = 4;
    let a2 = new A2();
    a2[0] = 5;
    a2[1] = 6;
    console.log([1, 2].concat(a1).concat(a2));
// [1, 2, 3, 4, [5, 6]]
}
{
    //Symbol.species
    class MyArray extends Array {
        static get [Symbol.species]() { return Array; }
    }
    var a = new MyArray(1,2,3);
    var mapped = a.map(x => x * x);

    mapped instanceof MyArray // false
    mapped instanceof Array // true
    // 上面代码中，由于构造函数被替换成了Array。所以，mapped对象不是MyArray的实例，而是Array的实例。
}
{
    // String.prototype.match(regexp)
// 等同于
//     regexp[Symbol.match](this)

    class MyMatcher {
        [Symbol.match](string) {
            return 'hello world'.indexOf(string);
        }
    }

    console.log('e'.match(new MyMatcher()) );// 1
}
{
    //Symbol.replace
    const x = {};
    x[Symbol.replace] = (...s) => console.log(s);

    'Hello'.replace(x, 'World') // ["Hello", "World"]
}
{
    //Symbol.split
    class MySplitter {
        constructor(value) {
            this.value = value;
        }
        [Symbol.split](string) {
            var index = string.indexOf(this.value);
            if (index === -1) {
                return string;
            }
            return [
                string.substr(0, index),
                string.substr(index + this.value.length)
            ];
        }
    }

    'foobar'.split(new MySplitter('foo'))
// ['', 'bar']

    'foobar'.split(new MySplitter('bar'))
// ['foo', '']

    'foobar'.split(new MySplitter('baz'))
// 'foobar'
}
{
    //对象进行for...of循环时，会调用Symbol.iterator方法，返回该对象的默认遍历器
    //Symbol.iterator
    var myIterable = {};
    myIterable[Symbol.iterator] = function* () {
        yield 1;
        yield 2;
        yield 3;
    };

    console.log([...myIterable]);// [1, 2, 3]
    for(let a of myIterable) {
        console.log(a);
    }
}
{

    //Symbol.toPrimitive
    // Number：该场合需要转成数值
    // String：该场合需要转成字符串
    // Default：该场合可以转成数值，也可以转成字符串
    let obj = {
        [Symbol.toPrimitive](hint) {
            switch (hint) {
                case 'number':
                    return 123;
                case 'string':
                    return 'str';
                case 'default':
                    return 'default';
                default:
                    throw new Error();
            }
        }
    };

     console.log(2 * obj); // 246
     console.log(3 + obj );// '3default'
     console.log(obj == 'default'); // true
     console.log(obj );// 'str'
}
{
    //Symbol.toStringTag
    // 例一
    console.log(({[Symbol.toStringTag]: 'Foo'}.toString()));
// "[object Foo]"

// 例二
    class Collection {
        get [Symbol.toStringTag]() {
            return 'xxx';
        }
    }
    var x = new Collection();
    console.log(Object.prototype.toString.call(x)); // "[object xxx]"

    console.log(Collection[Symbol.toStringTag]);//UNDEFINED
    console.log(x[Symbol.toStringTag]);//xxx
    console.log(JSON[Symbol.toStringTag]);//JSON
}
{
    // /Symbol.unscopables
    console.log(Array.prototype[Symbol.unscopables]);
    // {
//   copyWithin: true,
//   entries: true,
//   fill: true,
//   find: true,
//   findIndex: true,
//   includes: true,
//   keys: true
// }

    // 没有 unscopables 时
    class MyClass {
        foo() { return 1; }
    }

    console.log(MyClass.prototype[Symbol.unscopables]);

    var foo = function () { return 2; };

    // (function asd() {
    //     with (MyClass.prototype) {
    //         console.log(foo()); // 1
    //     }
    // })();

}
console.log("------------Set---------------");
{
    const s = new Set();

    [2, 3, 5, 4, 5, 2, 2].forEach(x => s.add(x));

    for (let i of s) {
        console.log(i);
    }
// 2 3 5 4

    // 例一
    var set = new Set([1, 2, 3, 4, 4]);
   console.log( [...set]);
// [1, 2, 3, 4]

// 例二
    var items = new Set([1, 2, 3, 4, 5, 5, 5, 5]);
   console.log( items.size); // 5

// 例三
    function divs () {
        // return [...document.querySelectorAll('div')];
    }

    // var set = new Set(divs());
    // console.log(set.size); // 56

// 类似于
//     divs().forEach(div => set.add(div));
//     console.log(set.size); // 56

    // 去除数组的重复成员
   console.log( [...new Set([1, 2, 3, 4, 5, 5, 5, 5])]);

    // 另外，两个对象总是不相等的。
    let set2 = new Set();

    set2.add({});
    // set2.size // 1

    set2.add({});
    // set2.size // 2
    console.log(set2);//Set { {}, {} }

    const s2 = new Set();
    s2.add(1).add(2).add(2);
// 注意2被加入了两次

    s2.size // 2

    s2.has(1) // true
    s2.has(2) // true
    s2.has(3) // false

    s2.delete(2);
    s2.has(2) // false
}
{
    var items = new Set([4, 2, 1, 3, 5]);
    var array = Array.from(items);
    console.log(items);
    console.log(array);
}
{
    //iterate
    // keys()：返回键名的遍历器
    // values()：返回键值的遍历器
    // entries()：返回键值对的遍历器
    // forEach()：使用回调函数遍历每个成员

    let set = new Set(['red', 'green', 'blue']);
    for (let item of set.entries()) {
        console.log(item);
    }
// ["red", "red"]
// ["green", "green"]
// ["blue", "blue"]

    set.forEach((a)=>console.log(a));
    set.forEach((value, key)=>console.log(value, key));


    // Set结构的实例默认可遍历，它的默认遍历器生成函数就是它的values方法。
Set.prototype[Symbol.iterator] === Set.prototype.values
// true

    // 扩展运算符（...）内部使用for...of循环，所以也可以用于Set结构。
    let arr = [...set];
    console.log(arr);
// ['red', 'green', 'blue']
}
{
    // 数组的map和filter方法也可以用于Set了。

    let set = new Set([1, 2, 3]);
    set = new Set([...set].map(x => x * 2));
    console.log(set);
// 返回Set结构：{2, 4, 6}

    let set2 = new Set([1, 2, 3, 4, 5]);
    set2 = new Set([...set2].filter(x => (x % 2) == 0));
// 返回Set结构：{2, 4}
    console.log(set2);
}
{
    let a = new Set([1, 2, 3]);
    let b = new Set([4, 3, 2]);

// 并集
    let union = new Set([...a, ...b]);
    console.log(union);
// Set {1, 2, 3, 4}

// 交集
    let intersect = new Set([...a].filter(x => b.has(x)));
    console.log(intersect);
// set {2, 3}

// 差集
    let difference = new Set([...a].filter(x => !b.has(x)));
    console.log(difference);
// Set {1}
}
{
    // WeakSet结构与Set类似，它与Set有两个区别。
    // 首先，WeakSet的成员只能是对象，而不能是其他类型的值。
    // 其次，WeakSet中的对象都是弱引用，即垃圾回收机制不考虑WeakSet对该对象的引用，也就是说，
    // 如果其他对象都不再引用该对象，那么垃圾回收机制会自动回收该对象所占用的内存，
    // 不考虑该对象还存在于WeakSet之中。这个特点意味着，无法引用WeakSet的成员，因此WeakSet是不可遍历的。
    // var ws = new WeakSet();
    // ws.add(1)
// TypeError: Invalid value used in weak set
//     ws.add(Symbol())
// TypeError: invalid value used in weak set
//     作为构造函数，WeakSet可以接受一个数组或类似数组的对象作为参数。
//     （实际上，任何具有iterable接口的对象，都可以作为WeakSet的参数。）
//     该数组的所有成员，都会自动成为WeakSet实例对象的成员。
    var a = [[1,2], [3,4]];
    var ws = new WeakSet(a);
    console.log(a);
    console.log(ws);
    // 注意，是a数组的成员成为WeakSet的成员，而不是a数组本身。这意味着，数组的成员只能是对象。

}
{
    var ws = new WeakSet();
    var obj = {};
    var foo = {};

    // ws.add(window);
    ws.add(obj);

    // ws.has(window); // true
    ws.has(foo);    // false
    console.log(ws.has(obj));    // true

    // ws.delete(window);
    // ws.has(window);    // false
    // WeakSet没有size属性，没有办法遍历它的成员。

    // ws.size // undefined
    // ws.forEach // undefined

    // ws.forEach(function(item){ console.log('WeakSet has ' + item)})
// TypeError: undefined is not a function

}
console.log("------------Map---------------");
{
// ，ES6提供了Map数据结构。它类似于对象，也是键值对的集合，
// 但是“键”的范围不限于字符串，各种类型的值（包括对象）都可以当作键。
    var m = new Map();
    var o = {p: 'Hello World'};

    m.set(o, 'content')
   console.log( m.get(o)); // "content"

    m.has(o) // true
    m.delete(o) // true
    m.has(o) // false
}
{
    var map = new Map([
        ['name', '张三'],
        ['title', 'Author']
    ]);

    map.size // 2
    map.has('name') // true
    map.get('name') // "张三"
    map.has('title') // true
    map.get('title') // "Author"

    // 实际上执行的是下面的算法。
    // items.forEach(([key, value]) => map.set(key, value));
}
{
    var m = new Map([
        [true, 'foo'],
        ['true', 'bar']
    ]);

    m.get(true) // 'foo'
    m.get('true') // 'bar'
}
{
    // 注意，只有对同一个对象的引用，Map结构才将其视为同一个键。这一点要非常小心。
var map = new Map();

    map.set(['a'], 555);
    map.get(['a']) // undefined
    // 内存地址是不一样的，因此get方法无法读取该键，返回undefined。
    // 由上可知，Map的键实际上是跟内存地址绑定的，只要内存地址不一样，就视为两个键。
    // 这就解决了同名属性碰撞（clash）的问题，我们扩展别人的库的时候，如果使用对象作为键名，
    // 就不用担心自己的属性与原作者的属性同名。
}
{
    let map = new Map();

    map.set(NaN, 123);
    map.get(NaN) // 123

    map.set(-0, 123);
    map.get(+0) // 123
}
{
    let map = new Map([
        ['F', 'no'],
        ['T',  'yes'],
    ]);

    for (let key of map.keys()) {
        console.log(key);
    }
// "F"
// "T"

    for (let value of map.values()) {
        console.log(value);
    }
// "no"
// "yes"

    for (let item of map.entries()) {
        console.log(item[0], item[1]);
    }
// "F" "no"
// "T" "yes"

// 或者
    for (let [key, value] of map.entries()) {
        console.log(key, value);
    }

// 等同于使用map.entries()
    for (let [key, value] of map) {
        console.log(key, value);
    }

    console.log(Map.prototype[Symbol.iterator] === Map.prototype.values);//false
    console.log(Map.prototype[Symbol.iterator] === Map.prototype.entries);//true
}
{
    //快速转数组结构
    let map = new Map([
        [1, 'one'],
        [2, 'two'],
        [3, 'three'],
    ]);

    console.log([...map.keys()]);
// [1, 2, 3]

    console.log([...map.values()]);
// ['one', 'two', 'three']

    console.log([...map.entries()]);
// [[1,'one'], [2, 'two'], [3, 'three']]

    console.log([...map]);
// [[1,'one'], [2, 'two'], [3, 'three']]
}
{
    // 互相转换
// （1）Map转为数组
    let myMap = new Map().set(true, 7).set({foo: 3}, ['abc']);
    console.log([...myMap]);
// [ [ true, 7 ], [ { foo: 3 }, [ 'abc' ] ] ]


// （2）数组转为Map
   console.log( new Map([[true, 7], [{foo: 3}, ['abc']]]));
// Map {true => 7, Object {foo: 3} => ['abc']}

    // 3）Map转为对象
    // 如果所有Map的键都是字符串，它可以转为对象。

    function strMapToObj(strMap) {
        let obj = Object.create(null);
        for (let [k,v] of strMap) {
            obj[k] = v;
        }
        return obj;
    }

    let myMap2 = new Map().set('yes', true).set('no', false);
    console.log(strMapToObj(myMap2));
// { yes: true, no: false }


    // （4）对象转为Map
    function objToStrMap(obj) {
        let strMap = new Map();
        for (let k of Object.keys(obj)) {
            strMap.set(k, obj[k]);
        }
        return strMap;
    }

   console.log( objToStrMap({yes: true, no: false}));
// [ [ 'yes', true ], [ 'no', false ] ]

}
console.log("------------Proxy---------------");
{
    //var proxy = new Proxy(target, handler);
    // 第一个参数是所要代理的目标对象（上例是一个空对象），即如果没有Proxy的介入，操作原来要访问的就是这个对象；
    // 第二个参数是一个配置对象，对于每一个被代理的操作，需要提供一个对应的处理函数，该函数将拦截对应的操作。
    var obj = new Proxy({}, {
        get: function (target, key, receiver) {
            console.log(`getting ${key}!`);
            console.log("target:"+target);
            // console.log(receiver);
            return Reflect.get(target, key, receiver);
        },
        set: function (target, key, value, receiver) {
            console.log(`setting ${key}!`);
            return Reflect.set(target, key, value, receiver);
        }
    });
   console.log( obj.count = 1);
//  setting count!
  console.log(  ++obj.count);
//  getting count!
//  setting count!
//  2
}
{
    // 一个技巧是将 Proxy 对象，设置到object.proxy属性，从而可以在object对象上调用。
    // var object = { proxy: new Proxy(target, handler) };

}
{
    // Proxy 实例也可以作为其他对象的原型对象。
    var proxy = new Proxy({}, {
        get: function(target, property) {
            return 35;
        }
    });

    let obj = Object.create(proxy);
    console.log(obj.time );// 35

    obj.aa="asdas";
    console.log(obj.aa)//asdas
}
{
    var handler = {
        get: function(target, name) {
            if (name === 'prototype') {
                return Object.prototype;
            }
            return 'Hello, ' + name;
        },

        apply: function(target, thisBinding, args) {
            return args[0];
        },

        construct: function(target, args) {
            return {value: args[1]};
        }
    };

    var fproxy = new Proxy(function(x, y) {
        return x + y;
    }, handler);

    console.log( fproxy(1, 2)); // 1    apply
     console.log(new fproxy(1,2) );// {value: 2}   construct
     console.log(fproxy.prototype === Object.prototype); // true
    console.log(typeof fproxy); // function
    var abc = function () {
    }
    console.log(abc.prototype === Object.prototype); // false
    console.log(abc.prototype === Function.prototype); // false
    console.log(abc.prototype == Object.prototype); // false
    console.log(abc.prototype == Function.prototype); // false
    console.log(typeof abc); // function

     console.log(fproxy.foo );// "Hello, foo"  get
}
{
//     下面是 Proxy 支持的拦截操作一览。
//
// 对于可以设置、但没有设置拦截的操作，则直接落在目标对象上，按照原先的方式产生结果。
//
// （1）get(target, propKey, receiver)
//
//     拦截对象属性的读取，比如proxy.foo和proxy['foo']。
//
// 最后一个参数receiver是一个对象，可选，参见下面Reflect.get的部分。
//
// （2）set(target, propKey, value, receiver)
//
//     拦截对象属性的设置，比如proxy.foo = v或proxy['foo'] = v，返回一个布尔值。
//
// （3）has(target, propKey)
//
//     拦截propKey in proxy的操作，返回一个布尔值。
//
// （4）deleteProperty(target, propKey)
//
//     拦截delete proxy[propKey]的操作，返回一个布尔值。
//
// （5）ownKeys(target)
//
//     拦截Object.getOwnPropertyNames(proxy)、Object.getOwnPropertySymbols(proxy)、Object.keys(proxy)，返回一个数组。该方法返回目标对象所有自身的属性的属性名，而Object.keys()的返回结果仅包括目标对象自身的可遍历属性。
//
// （6）getOwnPropertyDescriptor(target, propKey)
//
//     拦截Object.getOwnPropertyDescriptor(proxy, propKey)，返回属性的描述对象。
//
// （7）defineProperty(target, propKey, propDesc)
//
//     拦截Object.defineProperty(proxy, propKey, propDesc）、Object.defineProperties(proxy, propDescs)，返回一个布尔值。
//
// （8）preventExtensions(target)
//
//     拦截Object.preventExtensions(proxy)，返回一个布尔值。
//
// （9）getPrototypeOf(target)
//
//     拦截Object.getPrototypeOf(proxy)，返回一个对象。
//
// （10）isExtensible(target)
//
//     拦截Object.isExtensible(proxy)，返回一个布尔值。
//
// （11）setPrototypeOf(target, proto)
//
//     拦截Object.setPrototypeOf(proxy, proto)，返回一个布尔值。
//
// 如果目标对象是函数，那么还有两种额外操作可以拦截。
//
// （12）apply(target, object, args)
//
//     拦截 Proxy 实例作为函数调用的操作，比如proxy(...args)、proxy.call(object, ...args)、proxy.apply(...)。
//
// （13）construct(target, args)
//
//     拦截 Proxy 实例作为构造函数调用的操作，比如new proxy(...args)。
}
console.log("------------Reflect---------------");
{
// 老写法
//     try {
//         Object.defineProperty(target, property, attributes);
//         // success
//     } catch (e) {
//         // failure
//     }

// 新写法
//     if (Reflect.defineProperty(target, property, attributes)) {
//         // success
//     } else {
//         // failure
//     }

    // 老写法
    // 'assign' in Object // true

// 新写法
//     Reflect.has(Object, 'assign') // true

    // Reflect对象的方法与Proxy对象的方法一一对应，只要是Proxy对象的方法，
    // 就能在Reflect对象上找到对应的方法。这就让Proxy对象可以方便地调用对应的Reflect方法
    // ，完成默认行为，作为修改行为的基础。也就是说，不管Proxy怎么修改默认行为，你总可以在Reflect上获取默认行为。
    let target = {};
    let log = console.log;
    target=new Proxy(target, {
    set: function(target, name, value, receiver) {
            var success = Reflect.set(target,name, value, receiver);
            if (success) {
                log('property ' + name + ' on ' + target + ' set to ' + value);
            }
            return success;
        }
    });

    target.b = 23;
}
console.log("------------Promise---------------");
{
    // Promise对象代表一个异步操作，有三种状态：Pending（进行中）、Resolved（已完成，又称 Fulfilled）和Rejected（已失败）。
    // 只有异步操作的结果，可以决定当前是哪一种状态，任何其他操作都无法改变这个状态。

    // Promise对象的状态改变，只有两种可能：从Pending变为Resolved和从Pending变为Rejected。
    // 只要这两种情况发生，状态就凝固了，不会再变了，会一直保持这个结果。

    // 如果改变已经发生了，就算你再对Promise对象添加回调函数，也会立即得到这个结果。
    // 这与事件（Event）完全不同，事件的特点是，如果你错过了它，再去监听，是得不到结果的。

    // 有了Promise对象，就可以将异步操作以同步操作的流程表达出来，避免了层层嵌套的回调函数。


    // 缺点。:
    // 首先，无法取消Promise，一旦新建它就会立即执行，无法中途取消。
    // 其次，如果不设置回调函数，Promise内部抛出的错误，不会反应到外部。
    // 第三，当处于Pending状态时，无法得知目前进展到哪一个阶段（刚刚开始还是即将完成）。

}
{
    var promise = new Promise(function(resolve, reject) {
        // ... some code

        if (true/* 异步操作成功 */){
            // 将Promise对象的状态从“未完成”变为“成功”（即从Pending变为Resolved），
            // 在异步操作成功时调用，并将异步操作的结果，作为参数传递出去；
            resolve('asd');
        } else {
            // reject函数的作用是，将Promise对象的状态从“未完成”变为“失败”（即从Pending变为Rejected），
            // 在异步操作失败时调用，并将异步操作报出的错误，作为参数传递出去。
            reject(error);
        }
    });

    promise.then(function(value) {
        // success
    }, function(error) {
        // failure
    });
}
{
    function timeout(ms) {
        return new Promise((resolve, reject) => {
                setTimeout(resolve, ms, 'done');
     });
    }

    // timeout(100).then((value) => {
    //         console.log(value);
    // });

}
{
    let promise = new Promise(function(resolve, reject) {
        console.log('Promise');
        resolve();
    });

    //then方法指定的回调函数，将在当前脚本所有同步任务执行完才会执行
    promise.then(function() {
        // console.log('Resolved.');
    });

    console.log('Hi!');

// Promise
// Hi!
// Resolved
}
{
    var XMLHttpRequest = function () {

    }
    var func = ()=>{};
    Object.assign(XMLHttpRequest.prototype, {
        status:0,
        readyState:0,
        statusText:"ok",
        type:"",
        url:"",
        response:"",
        open:function (type,url){this.type=type;this.url=url},
        send:function () {
            setTimeout(()=>{this.response="response:"+this.type+" "+this.url;this.status=200;this.readyState=4;console.log(this);this.onreadystatechange();},100);
        },
        setRequestHeader:func
    });

    var getJSON = function(url) {
        var promise = new Promise(function(resolve, reject){
            var client = new XMLHttpRequest();
            client.open("GET", url);
            client.onreadystatechange = handler;
            client.responseType = "json";
            client.setRequestHeader("Accept", "application/json");
            client.send();

            function handler() {
                if (this.readyState !== 4) {
                    return;
                }
                if (this.status === 200) {
                    resolve(this.response);
                } else {
                    reject(new Error(this.statusText));
                }
            };
        });

        return promise;
    };

    // var p1 = new Promise(function (resolve, reject) {
    //     setTimeout(() => reject(new Error('fail')), 3000)
    // })
    //
    // var p2 = new Promise(function (resolve, reject) {
    //     setTimeout(() => resolve(p1), 1000)
    // })

//     p2
//         .then(result => console.log(result))
// .catch(error => console.log(error))
// Error: fail

//     上面代码中，p1是一个Promise，3秒之后变为rejected。
// p2的状态在1秒之后改变，resolve方法返回的是p1。
// 由于p2返回的是另一个 Promise，导致p2自己的状态无效了，由p1的状态决定p2的状态。
// 所以，后面的then语句都变成针对后者（p1）。又过了2秒，p1变为rejected，导致触发catch方法指定的回调函数。
}
{
    // 采用链式的then，可以指定一组按照次序调用的回调函数。这时，前一个回调函数，有可能返回的还是一个Promise对象（即有异步操作），这时后一个回调函数，就会等待该Promise对象的状态发生变化，才会被调用。

    // getJSON("/post/1.json").then(function(post) {
    //     return getJSON(post.commentURL);
    // }).then(function funcA(comments) {
    //     console.log("Resolved: ", comments);
    // }, function funcB(err){
    //     console.log("Rejected: ", err);
    // });

    // getJSON("/post/1.json").then(
    //     post => getJSON(post.commentURL)
    // ).then(
    //     comments => console.log("Resolved: ", comments),
    //     err => console.log("Rejected: ", err)
    // );
}
{
    // Promise.prototype.catch方法是.then(null, rejection)的别名，用于指定发生错误时的回调函数。
    // getJSON('/posts.json').then(function(posts) {
    //     // ...
    //     console.log(posts);
    // }).catch(function(error) {
    //     // 处理 getJSON 和 前一个回调函数运行时发生的错误
    //     console.log('发生错误！', error);
    // });
}
{
    // var promise=getJSON('/posts.json');
    // bad
    // promise
    //     .then(function(data) {
    //         // success
    //     }, function(err) {
    //         // error
    //     });

// good
//     promise
//         .then(function(data) { //cb
//             // success
//         })
//         .catch(function(err) {
//             // error
//         });
}
{
    // var someAsyncThing = function() {
    //     return new Promise(function(resolve, reject) {
    //         // 下面一行会报错，因为x没有声明
    //         resolve(x + 2);
    //     });
    // };
    //
    // someAsyncThing().then(function() {
    //     console.log('everything is great');
    // });

    // 上面代码中，someAsyncThing函数产生的Promise对象会报错，但是由于没有指定catch方法，这个错误不会被捕获，
    // 也不会传递到外层代码，导致运行后没有任何输出。
    // 注意，Chrome浏览器不遵守这条规定，它会抛出错误“ReferenceError: x is not defined”。


    // var promise = new Promise(function(resolve, reject) {
    //     resolve('ok');
    //     setTimeout(function() { throw new Error('test') }, 0)
    // });
    // promise.then(function(value) { console.log(value) });
// ok
// Uncaught Error: test

    // 上面代码中，Promise 指定在下一轮“事件循环”再抛出错误，结果由于没有指定使用try...catch语句，就冒泡到最外层，
    // 成了未捕获的错误。因为此时，Promise的函数体已经运行结束了，所以这个错误是在Promise函数体外抛出的。

    // someAsyncThing().then(function() {
    //     return someOtherAsyncThing();
    // }).catch(function(error) {
    //     console.log('oh no', error);
    //     // 下面一行会报错，因为y没有声明
    //     y + 2;
    // }).catch(function(error) {
    //     console.log('carry on', error);
    // });
// oh no [ReferenceError: x is not defined]
// carry on [ReferenceError: y is not defined]
}
{
    // var p = Promise.all([p1, p2, p3]);
    // （1）只有p1、p2、p3的状态都变成fulfilled，p的状态才会变成fulfilled，
    // 此时p1、p2、p3的返回值组成一个数组，传递给p的回调函数。

// （2）只要p1、p2、p3之中有一个被rejected，p的状态就变成rejected，
// 此时第一个被reject的实例的返回值，会传递给p的回调函数。
    // 生成一个Promise对象的数组
    // var promises = [2, 3, 5, 7, 11, 13].map(function (id) {
    //     return getJSON("/post/" + id + ".json");
    // });
    //
    // Promise.all(promises).then(function (posts) {
    //     // ...
    //     console.log('all ok:'+posts);
    // }).catch(function(reason){
    //     // ...
    //     console.log('reason:'+reason);
    // });
}
{
    // var p = Promise.race([p1, p2, p3]);
    // 上面代码中，只要p1、p2、p3之中有一个实例率先改变状态，p的状态就跟着改变。
    // 那个率先改变的 Promise 实例的返回值，就传递给p的回调函数。

}
{
// （2）参数是一个thenable对象

    // thenable对象指的是具有then方法的对象，比如下面这个对象。
    // Promise.resolve方法会将这个对象转为Promise对象，然后就立即执行thenable对象的then方法。

    // let thenable = {
    //     then: function(resolve, reject) {
    //         console.log('invoked..');
    //         resolve(42);
    //     }
    // };
    //
    // let p1 = Promise.resolve(thenable);
    // p1.then(function(value) {
    //     console.log(value);  // 42
    // });
}

{
    // var p = Promise.resolve('Hello');
    //
    // p.then(function (s){
    //     console.log(s)
    // });
    // Hello
}
{
    // setTimeout(function () {
    //     console.log('three');
    // }, 0);
    //
    // //Promise.resolve方法允许调用时不带参数，直接返回一个Resolved状态的Promise对象。
    // Promise.resolve().then(function () {
    //     console.log('two');
    // });
    //
    // console.log('one');

// one
// two
// three
}
{
    //部署 自定义方法
    Promise.prototype.done = function (onFulfilled, onRejected) {
        this.then(onFulfilled, onRejected)
            .catch(function (reason) {
                // 抛出一个全局错误
                setTimeout(() => { throw reason }, 0);
            });
    };
//    asyncFunc()
// .then(f1)
//     .catch(r1)
//     .then(f2)
//     .done();


    Promise.prototype.finally = function (callback) {
        let P = this.constructor;
        return this.then(
                value  => P.resolve(callback()).then(() => value),
        reason => P.resolve(callback()).then(() => { throw reason })
        );
    };
    // server.listen(0)
    //     .then(function () {
    //         // run test
    //     })
    //     .finally(server.stop);
}
{
    // 那么有没有一种方法，让同步函数同步执行，异步函数异步执行，并且让它们具有统一的 API 呢？
    // 回答是可以的，并且还有两种写法。第一种写法是用async函数来写。


    //第二种
    const f = () => console.log('now');
    (
        () => new Promise(
        resolve => resolve(f())
    )
    )();
    console.log('next');
// now
// next
}
{

    // // 鉴于这是一个很常见的需求，所以现在有一个提案，提供Promise.try方法替代上面的写法。
    // const f = () => console.log('now');
    // Promise.try(f);
    // console.log('next');
// now
// next
}
console.log("------------Iterator和for...of循环---------------");
{
    // Iterator的作用有三个：
    // 一是为各种数据结构，提供一个统一的、简便的访问接口；
    // 二是使得数据结构的成员能够按某种次序排列；
    // 三是ES6创造了一种新的遍历命令for...of循环，Iterator接口主要供for...of消费。

// 。凡是部署了Symbol.iterator属性的数据结构，就称为部署了遍历器接口。
//    调用这个接口，就会返回一个遍历器对象。

    //一种数据结构只要部署了Iterator接口，我们就称这种数据结构是”可遍历的“（iterable）。
    //遍历器对象除了具有next方法，还可以具有return方法和throw方法。
    // 如果你自己写遍历器对象生成函数，那么next方法是必须部署的，return方法和throw方法是否部署是可选的。

    // return方法的使用场合是，如果for...of循环提前退出（通常是因为出错，或者有break语句或continue语句），
    // 就会调用return方法。如果一个对象在完成遍历前，需要清理或释放资源，就可以部署return方法。

    // throw方法主要是配合Generator函数使用，一般的遍历器对象用不到这个方法。请参阅《Generator函数》一章。


    var it = makeIterator(['a', 'b']);

    it.next() // { value: "a", done: false }
    it.next() // { value: "b", done: false }
    it.next() // { value: undefined, done: true }

    function makeIterator(array) {
        var nextIndex = 0;
        return {
            next: function() {
                return nextIndex < array.length ?
                {value: array[nextIndex++], done: false} :
                {value: undefined, done: true};
            }
        };
    }
}
{
    let obj = {
        val:0,
        [Symbol.iterator] : function () {
            return {
                next: function () {
                    return {
                        value: ++obj.val,
                        done: obj.val>10
                    };
                }
            };
        }
    };
    console.log('tttttt');
    for(let a of obj){
        console.log(a);
    }
    console.log('tttttt2');
    for(var a of obj){
        console.log(a);
    }
    console.log('tttttt3');
    for (var i of obj){
        console.log(i);
    }

    let arr = ['a', 'b', 'c'];
    let iter = arr[Symbol.iterator]();

    console.log(iter.next()); // { value: 'a', done: false }
    console.log(iter.next()); // { value: 'b', done: false }
    console.log(iter.next() );// { value: 'c', done: false }
    console.log(iter.next() );// { value: undefined, done: true }
}
{
    function Obj(value) {
        this.value = value;
        this.next = null;
    }

    Obj.prototype[Symbol.iterator] = function() {
        var iterator = {
            next: next
        };

        var current = this;

        function next() {
            if (current) {
                var value = current.value;
                current = current.next;
                return {
                    done: false,
                    value: value
                };
            } else {
                return {
                    done: true
                };
            }
        }
        return iterator;
    }

    var one = new Obj(1);
    var two = new Obj(2);
    var three = new Obj(3);

    one.next = two;
    two.next = three;

    for (var i of one){
        console.log(i);
    }
// 1
// 2
// 3

    var $iterator = one[Symbol.iterator]();
    var $result = $iterator.next();
    while (!$result.done) {
        var x = $result.value;
        // ...
        console.log($result);
        $result = $iterator.next();
    }
    // { done: false, value: 1 }
    // { done: false, value: 2 }
    // { done: false, value: 3 }

}
{
    // 调用Iterator接口的场合
    // （1）解构赋值
    // 对数组和Set结构进行解构赋值时，会默认调用Symbol.iterator方法。

    let set = new Set().add('a').add('b').add('c');

    let [x,y] = set;
// x='a'; y='b'

    let [first, ...rest] = set;
    // first='a'; rest=['b','c'];
}
{
// （2）扩展运算符
//     扩展运算符（...）也会调用默认的iterator接口。

// 例一
    var str = 'hello';
    [...str] //  ['h','e','l','l','o']

// 例二
    let arr = ['b', 'c'];
    ['a', ...arr, 'd']
// ['a', 'b', 'c', 'd']

    // 要某个数据结构部署了Iterator接口，就可以对它使用扩展运算符，将其转为数组。
    // let arr = [...iterable];
}
{
// （3）yield*
// yield*后面跟的是一个可遍历的结构，它会调用该结构的遍历器接口。

    let generator = function* () {
        yield 1;
        yield* [2,3,4];
        yield 5;
    };

    var iterator = generator();

    console.log(iterator.next() );// { value: 1, done: false }
    console.log(iterator.next()); // { value: 2, done: false }
    console.log(iterator.next()); // { value: 3, done: false }
    console.log(iterator.next()); // { value: 4, done: false }
    console.log(iterator.next()); // { value: 5, done: false }
    console.log(iterator.next()); // { value: undefined, done: true }
}
{
// （4）其他场合
//     由于数组的遍历会调用遍历器接口，所以任何接受数组作为参数的场合，其实都调用了遍历器接口。下面是一些例子。
//
// for...of
//     Array.from()
//     Map(), Set(), WeakMap(), WeakSet()（比如new Map([['a',1],['b',2]])）
// Promise.all()
//     Promise.race()


    // for...of循环可以使用的范围包括数组、Set 和 Map 结构、
    // 某些类似数组的对象（比如arguments对象、DOM NodeList 对象）、后文的 Generator 对象，以及字符串。
}
{
    var myIterable = {};

    myIterable[Symbol.iterator] = function* () {
        yield 1;
        yield 2;
        yield 3;
    };
    console.log([...myIterable] );// [1, 2, 3]

// 或者采用下面的简洁写法

    let obj = {
        * [Symbol.iterator]() {
            yield 'hello';
            yield 'world';
        }
    };

    for (let x of obj) {
        console.log(x);
    }
    // hello
    // world
}
{
    var arr = ['a', 'b', 'c', 'd'];

    for (let a in arr) {
        console.log(a); // 0 1 2 3
    }

    for (let a of arr) {
        console.log(a); // a b c d
    }
}
{
    // for...of循环调用遍历器接口，数组的遍历器接口只返回具有数字索引的属性。这一点跟for...in循环也不一样。
    let arr = [3, 5, 7];
    arr.foo = 'hello';
    console.log(arr);//[ 3, 5, 7, foo: 'hello' ]

    for (let i in arr) {
        console.log(i); // "0", "1", "2", "foo"
    }

    for (let i of arr) {
        console.log(i); //  "3", "5", "7"
    }
}
{
    // Set 结构遍历时，返回的是一个值，而 Map 结构遍历时，返回的是一个数组
    let map = new Map().set('a', 1).set('b', 2);
    for (let pair of map) {
        console.log(pair);
    }
// ['a', 1]
// ['b', 2]

    for (let [key, value] of map) {
        console.log(key + ' : ' + value);
    }
// a : 1
// b : 2
}
{
    // 并不是所有类似数组的对象都具有 Iterator 接口，一个简便的解决方法，就是使用Array.from方法将其转为数组。
    let arrayLike = { length: 2, 0: 'a', 1: 'b' };

// 报错
//     for (let x of arrayLike) {
//         console.log(x);
//     }

// 正确
    for (let x of Array.from(arrayLike)) {
        console.log(x);
    }
}
{
    // 对象
    // 对于普通的对象，for...of结构不能直接使用，会报错，
    // 必须部署了 Iterator 接口后才能使用。但是，这样情况下，for...in循环依然可以用来遍历键名。

let es6 = {
    edition: 6,
    committee: "TC39",
    standard: "ECMA-262"
};

    for (let e in es6) {
        console.log(e);
    }
// edition
// committee
// standard

    // for (let e of es6) {
    //     console.log(e);
    // }
// TypeError: es6[Symbol.iterator] is not a function

    // 解决方法1，使用Object.keys方法将对象的键名生成一个数组，然后遍历这个数组。
    for (var key of Object.keys(es6)) {
        console.log(key + ': ' + es6[key]);
    }

    function* entries(obj) {
        for (let key of Object.keys(obj)) {
            yield [key, obj[key]];
        }
    }

    // 解决方法2，使用 Generator 函数将对象重新包装一下。
    for (let [key, value] of entries(es6)) {
        console.log(key, '->', value);
    }
    // a -> 1
    // b -> 2
    // c -> 3
}
{
    // 与其他遍历语法的比较
    //1
    // for (var index = 0; index < myArray.length; index++) {
    //     console.log(myArray[index]);
    // }

//    2
//     myArray.forEach(function (value) {
//         console.log(value);
//     });

    //3
    // for (var index in myArray) {
    //     console.log(myArray[index]);
    // }
//     数组的键名是数字，但是for...in循环是以字符串作为键名“0”、“1”、“2”等等。
// for...in循环不仅遍历数字键名，还会遍历手动添加的其他键，甚至包括原型链上的键。
// 某些情况下，for...in循环会以任意顺序遍历键名。
//     总之，for...in循环主要是为遍历对象而设计的，不适用于遍历数组。

    // for...of
    // 可以与break、continue和return配合使用。
    // for (var n of fibonacci) {
    //     if (n > 1000)
    //         break;
    //     console.log(n);
    // }
//    上面的例子，会输出斐波纳契数列小于等于1000的项。


    var ar1=[1,2,3];
    for(var i in ar1){
        console.log(typeof i);
    }
    // string
    // string
    // string
    console.log(typeof 123);
    // number
    console.log(typeof 123.3);
    // number
    console.log(typeof true);
    // boolean
}
