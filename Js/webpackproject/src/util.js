var ArrayUtil={
    a:function (s) {
        console.log(s+' ArrayUtil.a invoked');
    }
}
var StringUtil={
    a:function (s) {
        console.log(s+' StringUtil.a invoked');
    }
}
var NumberUtil={
    a:function (s) {
        console.log(s+' NumberUtil.a invoked');
    }
}
// export default ArrayUtil
export { ArrayUtil as default}
export {NumberUtil as nUtil,StringUtil as sUtil}

// 从其他模块导出：
// export { foo, bar } from "baz";
// export { foo as FOO, bar as BAR } from "baz";
// export * from "baz";