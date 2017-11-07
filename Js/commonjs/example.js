var x = 5;
var addX = function (value) {
    return value + x;
};
console.log('example');
global.warning = true;

module.exports.x = x;
module.exports.addX = addX;

//直接 node 执行
if (!module.parent) {
    // ran with `node something.js`
    console.log('直接 node 执行');
} else {
    //被其它js引入
    console.log('引入');
}