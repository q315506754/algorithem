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