//main.js
import React from 'react';
import {render} from 'react-dom';
import Greeter from './Greeter';

import './main.css';//使用require导入css文件

console.log('xxxx');
console.log(document);
// console.log($);

render(<Greeter />, document.getElementById('root'));