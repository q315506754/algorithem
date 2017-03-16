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


console.log('-------------descructing-----------------');
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

    move3({x: 3, y: 8}); // [3, 8]
    move3({x: 3}); // [3, undefined]
    move3({}); // [undefined, undefined]
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

    console.log(String.raw`Hi\n${2+3}!`);
    console.log(String.raw`Hi\\n`);

    console.log(  String.raw({ raw: 'test' }, 0, 132, 32,345,23));
}