'use strict';

//字符串模板
var name = '小明';
var age = 18;

var str = 'hello ,\u5C0A\u656C\u7684 ' + name + ' ,\u4ECA\u5929\u662F\u60A8' + age + '\u5C81\u7684\u751F\u65E5,\u795D\u60A8\u751F\u65E5\u5FEB\u4E50';
console.log(str);

//解构
var json = { name: '小红', age: 16 };
var n = json.name,
    a = json.age;

console.log('\u59D3\u540D:' + n + ' \u5E74\u9F84:' + a);

var json = { name2: '小灰', age2: 13 };
var name2 = json.name2,
    age2 = json.age2;

console.log('\u59D3\u540D2:' + name2 + ' \u5E74\u9F84:' + age2);

var _ref = [3, 2, 4, 6],
    x = _ref[0],
    y = _ref[1],
    z = _ref[2];

console.log('x:' + x + ' y:' + y + ' z:' + z);

var _ref2 = [44, 66],
    x1 = _ref2[0],
    y1 = _ref2[1],
    z1 = _ref2[2];

console.log('x1:' + x1 + ' y1:' + y1 + ' z1:' + z1);

//展开
function f(x, y, z) {
    console.log('f: x:' + x + ' y:' + y + ' z:' + z);
}
f([0, 1, 2]);
f.apply(undefined, [0, 1, 2]);

//默认值
function fd() {
    var x = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 1;
    var y = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 2;
    var z = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 3;

    console.log('fd: x:' + x + ' y:' + y + ' z:' + z);
}
fd();
fd(22);
fd(undefined, null, 333);

var _b3$c = { b3: null, c3: 123 },
    _b3$c$a = _b3$c.a3,
    a3 = _b3$c$a === undefined ? 'aa' : _b3$c$a,
    _b3$c$b = _b3$c.b3,
    b3 = _b3$c$b === undefined ? 'bb' : _b3$c$b,
    _b3$c$c = _b3$c.c3,
    c3 = _b3$c$c === undefined ? 'cc' : _b3$c$c;

console.log('a3:' + a3 + ' b3:' + b3 + ' c3:' + c3);

var _ref3 = [44, 66],
    _ref3$ = _ref3[0],
    x3 = _ref3$ === undefined ? 'x3' : _ref3$,
    _ref3$2 = _ref3[1],
    y3 = _ref3$2 === undefined ? 'y3' : _ref3$2,
    _ref3$3 = _ref3[2],
    z3 = _ref3$3 === undefined ? 'z3' : _ref3$3;

console.log('x3:' + x3 + ' y3:' + y3 + ' z3:' + z3);
