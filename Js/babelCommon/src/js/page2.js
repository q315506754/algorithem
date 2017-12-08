// require('es5-shim');//ES5的API兼容报错
// require('es5-shim/es5-sham');
require('console-polyfill');//console-polyfill 问题解决
require('es6-promise');//Promise 兼容
require('babel-polyfill');//Promise 兼容

import $ from 'jquery'  //Babel
import '../css/page2.css';//使用require导入css文件
import '../css/cm.css';//

console.log('page 2 included');

$(function () {
    console.log('page2jquery loaded..');
});