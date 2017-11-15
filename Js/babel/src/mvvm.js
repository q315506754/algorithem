var obj = {
  a: 0,
  b: 1
}

Object.defineProperty(obj, 'b', {
  enumerable: false
})

//默认情况下是enumerable属性为false，
Object.defineProperty(obj, 'c', {
    value: 1
})
Object.defineProperty(obj, 'd', {
    value: 2,
    enumerable: true
})

for (var prop in obj) {
  console.log(`obj.${prop} = ${obj[prop]}`)
}

// 结果：
'obj.a = 0'

var des = Object.getOwnPropertyDescriptor(obj,'a');
console.log(des);//{ value: 0, writable: true, enumerable: true, configurable: true }
console.log(des.get);//undefined
 des = Object.getOwnPropertyDescriptor(obj,'d');
console.log(des);
// { value: 2,
//     writable: false,
//     enumerable: true,
//     configurable: false }
console.log(des.get);//undefined
obj.d = 232
console.log(obj.d);//2

des = Object.getOwnPropertyDescriptor(obj,'a');
console.log(des && des.get);//2


// 观察者构造函数
function Observer (value) {
    this.value = value
    this.walk(value)
}

// 递归调用，为对象绑定getter/setter
Observer.prototype.walk = function (obj) {
    var keys = Object.keys(obj)
    // console.log(keys);//[ 'user', 'address' ]
    for (var i = 0, l = keys.length; i < l; i++) {
        this.convert(keys[i], obj[keys[i]])
    }
}

// 将属性转换为getter/setter
Observer.prototype.convert = function (key, val) {
    defineReactive(this.value, key, val)
}

// 创建数据观察者实例
function observe (value) {
    // 当值不存在或者不是对象类型时，不需要继续深入监听
    if (!value || typeof value !== 'object') {
        return
    }
    return new Observer(value)
}

// 定义对象属性的getter/setter
function defineReactive (obj, key, val) {
    var property = Object.getOwnPropertyDescriptor(obj, key)
    // console.log(property);
    if (property && property.configurable === false) {
        return
    }

    // 保存对象属性预先定义的getter/setter
    var getter = property && property.get
    var setter = property && property.set
    // console.log(typeof getter);
    // console.log(typeof setter);


    var childOb = observe(val)
    Object.defineProperty(obj, key, {
        enumerable: true,
        configurable: true,
        get: function reactiveGetter () {
            var value = getter ? getter.call(obj) : val
            console.log("访问："+key)
            return value
        },
        // set: function (value) {
        //     this.a = value / 2;
        // }
        set: function reactiveSetter (newVal) {
            var value = getter ? getter.call(obj) : val
            if (newVal === value) {
                return
            }
            // console.log('set '+setter);
            if (setter) {
                setter.call(obj, newVal)
                console.log('setter.call '+setter);
            } else {
                console.log(`val:${val} newVal:${newVal}`);
                val = newVal
                console.log('val = newVal '+setter);
            }
            console.log(typeof val);
            // val='sadasdsa' // 生效
            // console.log('observe '+newVal);
            // 对新值进行监听
            childOb = observe(newVal)
            console.log('更新：' + key + ' = ' + newVal)
        }
    })
}


let data = {
    user: {
        name: 'zhaomenghuan',
        age: '24'
    },
    address: {
        city: 'beijing'
    }
}
observe(data)

console.log(data.user.name)
// 访问：user
// 访问：name
//zhaomenghuan

data.user.name = 'ZHAO MENGHUAN'
// 访问：user
// 更新：name = ZHAO MENGHUAN

console.log(data.user.name)
// 访问：user
// 访问：name
// ZHAO MENGHUAN

Object.defineProperty(obj, "e", {
    enumerable: true,
    configurable: true,
    get: function reactiveGetter() {
        // var value = this.value;
        // console.log("访问：" + value)
        // return value
        return 1
    },
    set: function reactiveSetter(newVal) {
        console.log("set：" + newVal)
        // this.value = newVal
    }
});
obj.e = 11
console.log(obj.e);
console.log(obj);


const arrayProto = Array.prototype
const arrayMethods = Object.create(arrayProto)
console.log(arrayProto);
console.log(arrayMethods);

// 实现继承
var extend = function(Child, Parent) {
    // 拷贝Parent原型对象
    Child.prototype = Object.create(Parent.prototype);
    // 将Child构造函数赋值给Child的原型对象
    Child.prototype.constructor = Child;
}

// 实例
var Parent = function () {
    this.name = 'Parent';
}
Parent.prototype.getName = function () {
    return this.name;
}
var Child = function () {
    this.name = 'Child';
}
extend(Child, Parent);
var child = new Child();
console.log(child.getName())
console.log(Parent.target);