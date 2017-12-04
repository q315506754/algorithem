// export default function () {
//     console.log('foo export default function ()');
// }

// 或者写成

function foo() {
    console.log('foo');
}

//SyntaxError: D:/algorithem/Js/moduledemo/src/module2.js: Only one default export
// allowed per module. (11:0)
// export default foo;

// 正确
// export var a = 1;

// 正确
var a = 1;
var b = 2;
// export default a;

// 正确
// export default 42;

export {a};
export {b};
export {foo as default};

// MyClass.js
// export default class { ... }