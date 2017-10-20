//字符串模板
var name = '小明';
var age = 18;

var str = `hello ,尊敬的 ${name} ,今天是您${age}岁的生日,祝您生日快乐`
console.log(str)



//解构
var json = {name:'小红',age:16};
var {name:n,age:a} = json;
console.log(`姓名:${n} 年龄:${a}`)

var json = {name2:'小灰',age2:13};
var {name2,age2} = json;
console.log(`姓名2:${name2} 年龄:${age2}`)

var [x,y,z] = [3,2,4,6]
console.log(`x:${x} y:${y} z:${z}`)

var [x1,y1,z1] = [44,66]
console.log(`x1:${x1} y1:${y1} z1:${z1}`)

//展开
function f(x, y, z) {
    console.log(`f: x:${x} y:${y} z:${z}`)
}
f([0, 1, 2]);
f(...[0, 1, 2]);


//默认值
function fd(x=1, y=2, z=3) {
    console.log(`fd: x:${x} y:${y} z:${z}`)
}
fd();
fd(22);
fd(undefined,null,333);

var {a3='aa',b3='bb',c3='cc'} = {b3:null,c3:123};
console.log(`a3:${a3} b3:${b3} c3:${c3}`);

var [x3='x3',y3='y3',z3='z3'] = [44,66];
console.log(`x3:${x3} y3:${y3} z3:${z3}`);


