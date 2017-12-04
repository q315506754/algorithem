// 如果在一个模块之中，先输入后输出同一个模块，import语句可以与export语句写在一起。
export { foo as comFoo, f as comF } from './module1';

// 整体输出
// export * from './module1';

export { default } from './module2';

//
// 具名接口改为默认接口的写法如下。

// export { es6 as default } from './someModule';


// 同样地，默认接口也可以改名为具名接口。
// export { default as es6 } from './someModule';