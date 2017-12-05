import hi,{HELLO} from './part'
import {JSDOM} from 'jsdom'
// or even
const { window } = (new JSDOM(`...`, {
    url: "https://example.org/", //window.location.href
    referrer: "https://example.com/",
    contentType: "text/html",
    userAgent: "Mellblomenator/9000",
    includeNodeLocations: true
}));
//To enable executing scripts inside the page, you can use the {runScripts: "dangerously"} option:

//document.hidden默认为true
//当{ pretendToBeVisual: true }时，jsdom设置为假装渲染模式，将产生下列变化
//1.document.hidden=false
//2.document.visibilityState="visible"（原"prerender"）
//3.拥有方法window.requestAnimationFrame() and window.cancelAnimationFrame()（原不存在）

//默认不加载任何外部js、css、图片、iframe
//当resources: "usable"时，上述将加载，（js另需runScripts: "dangerously"启用）

const { document }=window;

console.log(JSDOM);
// console.log(window); //very long
console.log(document);
console.log(window.location.href);

window.console.log('this is console from window');//直接输出到 控制台console  "virtual console"
//创建自定义虚拟console方法如下
//const virtualConsole = new jsdom.VirtualConsole();
// const dom = new JSDOM(``, { virtualConsole });
// virtualConsole.on("error", () => { ... });
// virtualConsole.on("warn", () => { ... });
// virtualConsole.on("info", () => { ... });
// virtualConsole.on("dir", () => { ... });
//默认行为就是下面一行代码
//virtualConsole.sendTo(console);

const dom = new JSDOM(`<!DOCTYPE html><p>Hello world</p>`);
console.log(dom.window.document.querySelector("p").textContent); // "Hello world"

hi();
console.log(`constant:${HELLO}`);
