

import $ from 'jquery'  //Babel
// var $ = require("jquery");  //Browserify/Webpack

// AMD (Asynchronous Module Definition) require.js'
// define(["jquery"], function($) {
//
// });
import './part1'
import './main.css';//使用require导入css文件
// import ArrayUtil from './util'
import {default as ArrayUtil,nUtil,sUtil} from './util'


ArrayUtil.a("main");
nUtil.a("main");
sUtil.a("main");

$(function () {
    console.log('main jquery loaded..');
});
console.log($);


console.log('asdasdasasdsa');
console.log(document);
