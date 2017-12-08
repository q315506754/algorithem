// require('es5-shim');//ES5的API兼容报错
// require('es5-shim/es5-sham');
require('console-polyfill');//console-polyfill 问题解决
require('es6-promise');//Promise 兼容
require('babel-polyfill');//Promise 兼容

import $ from 'jquery'  //Babel
import md from '../index'
import config from '../data/config'
import '../css/main.css';//使用require导入css文件
import '../css/cm.css';//

$(function () {
    console.log('main jquery loaded..');

});

md();
console.log(document);
console.log(config);