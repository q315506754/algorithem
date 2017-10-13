var hello =require('./hello');//自定义模块

var http = require("http");//服务端的模块
//Node.js 中存在 4 类模块（原生模块和3种文件模块），
//文件模块缓存->原生模块缓存中->原生模块中->文件模块(自定义模块)
// require方法接受以下几种参数的传递：
// http、fs、path等，原生模块。
// ./mod或../mod，相对路径的文件模块。
// /pathtomodule/mod，绝对路径的文件模块。
// mod，非原生模块的文件模块。

// 代码 require('./hello') 引入了当前目录下的 hello.js 文件（./ 为当前目录，node.js 默认后缀为 js）。


const child_process = require('child_process');



// 引入 events 模块
var events = require('events');
// 创建 eventEmitter 对象
var eventEmitter = new events.EventEmitter();

// 绑定事件及事件的处理程序
eventEmitter.on('eventName', function (e) {
    console.log('event:'+e);
});

// 触发事件
setTimeout(function () {
    eventEmitter.emit('eventName')
},0);



//文件模块
var fs = require("fs");

fs.readFile('input.txt', function (err, data) {
    if (err){
        console.log(err.stack);
        return;
    }
    console.log(data.toString());
});
console.log("程序执行完毕");


//child_process.exec(command[, options], callback)
var workerProcess = child_process.exec(`ping www.qq.com`,function (error, stdout, stderr) {
    if (error) {
        console.log(error.stack);
        console.log('Error code: '+error.code);
        console.log('Signal received: '+error.signal);
    }
    console.log('stdout: ' + stdout);
    console.log('stderr: ' + stderr);
});

workerProcess.on('exit', function (code) {
    console.log('子进程已退出，退出码 '+code);
});

//child_process.spawn(command[, args][, options])
var workerProcess2 = child_process.spawn('ping', ['www.qq.com', "-a"]);
workerProcess2.stdout.on('data', function (data) {
    console.log('stdout2: ' + data);
});

workerProcess2.stderr.on('data', function (data) {
    console.log('stderr2: ' + data);
});

workerProcess2.on('close', function (code) {
    console.log('子进程已退出2，退出码 '+code);
});

var a = {
    user:"追梦子",
    fn:function(){
        console.log(this+" "+this.user);
    }
}

//child_process.fork(modulePath[, args][, options])
var workerProcess3 =child_process.fork("myfork.js", ["hahaha"]);

workerProcess3.on('close', function (code) {
    console.log('子进程3已退出，退出码 ' + code);
});



hello.world()

var b = a.fn;
a.fn(); //追梦子

// b(); //undefined

// 通过在call方法，给第一个参数添加要把b添加到哪个环境中，简单来说，this就会指向那个对象。
b.call(a,1,2);//追梦子

//apply方法和call方法有些相似，它也可以改变this的指向  第二个参数必须是一个数组
b.apply(a,[10,1]);//追梦子

// 实际上bind方法返回的是一个修改过后的函数。
var c = b.bind(a)
c()


console.log('global value...');
console.log(__filename);
console.log(__dirname);

process.on('exit', function(code) {

    // 以下代码永远不会执行
    setTimeout(function() {
        console.log("该代码不会执行");
    }, 0);

    console.log('main退出码为:', code);
});
console.log("程序执行结束");

//一个 Buffer 类似于一个整数数组，但它对应于 V8 堆内存之外的一块原始内存。
var buf = new Buffer(10);//长度为 10 字节
// var buf = new Buffer([10, 20, 30, 40, 50]);
console.log(buf.length);
//utf-8 是默认的编码方式，此外它同样支持以下编码："ascii", "utf8", "utf16le", "ucs2", "base64" 和 "hex"。
// var buf = new Buffer("www.runoob.com", "utf-8");

//buf.write(string[, offset[, length]][, encoding])
len = buf.write("www.runoob.com");
console.log("写入字节数 : "+  len);
console.log(buf.length);
//buf.toString([encoding[, start[, end]]])
console.log(buf.toString());//www.runoob 只写入了10个
console.log( buf.toString('ascii'));//www.runoob

var json = buf.toJSON(buf);
console.log(json);
// { type: 'Buffer',
//     data: [ 119, 119, 119, 46, 114, 117, 110, 111, 111, 98 ] }

{
    //Buffer.concat(list[, totalLength])
    var buffer1 = new Buffer('菜鸟教程 ');
    var buffer2 = new Buffer('www.runoob.com');
    var buffer3 = Buffer.concat([buffer1,buffer2]);
    console.log("buffer3 内容: " + buffer3.toString());
}


{
    var buffer1 = new Buffer('ABC');
    var buffer2 = new Buffer('ABCD');
    var result = buffer1.compare(buffer2);

    if(result < 0) {
        console.log(buffer1 + " 在 " + buffer2 + "之前");
    }else if(result == 0){
        console.log(buffer1 + " 与 " + buffer2 + "相同");
    }else {
        console.log(buffer1 + " 在 " + buffer2 + "之后");
    }
}

{
    var buffer1 = new Buffer('ABC');
// 拷贝一个缓冲区
    var buffer2 = new Buffer(3);
    buffer1.copy(buffer2);
    //buf.copy(targetBuffer[, targetStart[, sourceStart[, sourceEnd]]])
    console.log("buffer2 content: " + buffer2.toString());
}

{
    var buffer1 = new Buffer('runoob');
// 剪切缓冲区
    var buffer2 = buffer1.slice(0,2);
    console.log("buffer2 content: " + buffer2.toString());
}