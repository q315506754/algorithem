var exp = require('./example')
var me = require('./myevent')

console.log('global.warning:'+global.warning)

// module.id 模块的识别符，通常是带有绝对路径的模块文件名。
// module.filename 模块的文件名，带有绝对路径。
// module.loaded 返回一个布尔值，表示模块是否已经完成加载。
// module.parent 返回一个对象，表示调用该模块的模块。
// module.children 返回一个数组，表示该模块要用到的其他模块。
// module.exports 表示模块对外输出的值。
console.log(module);
console.log(module.exports);
//为了方便，Node为每个模块提供一个exports变量，指向module.exports。
console.log(exports);
console.log(exp);
// console.log(require);
// console.log(module.require);
console.log(require.main);
console.log(require.main===module);

me.on("ready", function () {
    console.log("module myevent.. is ready");
});