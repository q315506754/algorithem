var http = require("http");
var fs = require('fs');
var url = require('url');

let MIMEMAP={
    "css":"text/css",
    "js":"application/javascript"
}

function getSuffix(str){
    return str.substr(str.lastIndexOf(".")+1);
}
function getMIME(suffix){
    if(MIMEMAP[suffix]){
        return MIMEMAP[suffix];
    }
    return "text/html";
}

http.createServer(function (request, response) {
    // 解析请求，包括文件名
    var pathname = url.parse(request.url).pathname;

    // 输出请求的文件名
    console.log("Request for " + pathname + " received.");
    let suffix=getSuffix(pathname);
    console.log("Suffix :" + suffix + "");

    // 从文件系统中读取请求的文件内容
    fs.readFile("web/"+pathname.substr(1), function (err, data) {
        //fs.readFile("/web/index.html", function (err, data) {
        if (err) {
            console.log(err);
            // HTTP 状态码: 404 : NOT FOUND
            // Content Type: text/plain
            response.writeHead(404, {'Content-Type': 'text/html'});
        } else {
            // HTTP 状态码: 200 : OK
            // Content Type: text/plain
            response.writeHead(200, {'Content-Type': getMIME(suffix)});

            // 响应文件内容
            response.write(data.toString());
        }
        //  发送响应数据
        response.end();
    });
}).listen(8888);

console.log("Server has started.");

//localhost:8888
