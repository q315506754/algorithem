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


