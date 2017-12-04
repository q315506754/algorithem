// ES6模块
import { stat, exists, readFile } from 'fs';  //“编译时加载”或者静态加载 必须在行首

//commonjs
var rstat = require('fs').stat;
//npm install babel-core babel-preset-es2015
//babel-node src/index
console.log(module);
console.log(stat);
console.log(rstat);
console.log(rstat==stat);//true
console.log(rstat===stat);//true

//整体加载
import * as json1 from './module1';
var rjson1 = require('./module1');
rjson1.aaa='bbbb'
json1.aaa='bbbb'
console.log(json1);
console.log(rjson1);
console.log(json1.foo);
console.log(rjson1.foo);

// 最后，export命令可以出现在模块的任何位置，只要处于模块顶层就可以。如果处于块级作用域内，就会报错，
// 下一节的import命令也是如此。这是因为处于条件代码块之中，就没法做静态优化了，违背了 ES6 模块的设计初衷。
// function foo() {
//     //SyntaxError: D:/algorithem/Js/moduledemo/src/index.js: 'import' and 'export' may
//     // only appear at the top level (22:4)
//     export default 'bar' // SyntaxError
// }
// foo()


//import命令具有提升效果，会提升到整个模块的头部，首先执行。
FFFFFFFF2() //不报错  因为import是静态执行的 先于FFFFFFFF2 调用
// 由于import是静态执行，所以不能使用表达式和变量，这些只有在运行时才能得到结果的语法结构。
//部分加载
import { f2 as FFFFFFFF2 } from './module1';
console.log(FFFFFFFF2);

//因为require是运行时加载模块，import命令无法取代require的动态加载功能。
// 因此，有一个提案，建议引入import()函数，完成动态加载。
// import()类似于 Node 的require方法，区别主要是前者是异步加载，后者是同步加载。

//
// import { 'f' + 'oo' } from 'my_module';

console.log(json1.foo);
console.log(rjson1.foo);

// import-default.js
import customName,{a,b} from './module2';
customName(); // 'foo'

console.log(`${a} ${b}`);

import * as comJson from './combination'
console.log(comJson);

// test1.js 模块
import * as constants from './constants';
console.log(constants.A); // 1
console.log(constants.B); // 3

// test2.js 模块
import {A, B} from './constants';
console.log(A); // 1
console.log(B); // 3
