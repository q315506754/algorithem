var fs = require("fs");


// 异步读取
fs.readFile('input.txt', function (err, data) {
    if (err) {
        return console.error(err);
    }
    console.log("readFile异步读取: " + data.toString());
});

// 同步读取
var data = fs.readFileSync('input.txt');
console.log("同步读取: " + data.toString());

console.log("程序执行完毕。");


//fs.open(path, flags[, mode], callback)
// r	以读取模式打开文件。如果文件不存在抛出异常。
// r+	以读写模式打开文件。如果文件不存在抛出异常。
// rs	以同步的方式读取文件。
// rs+	以同步的方式读取和写入文件。
// w	以写入模式打开文件，如果文件不存在则创建。
// wx	类似 'w'，但是如果文件路径存在，则文件写入失败。
// w+	以读写模式打开文件，如果文件不存在则创建。
// wx+	类似 'w+'， 但是如果文件路径存在，则文件读写失败。
// a	以追加模式打开文件，如果文件不存在则创建。
// ax	类似 'a'， 但是如果文件路径存在，则文件追加失败。
// a+	以读取追加模式打开文件，如果文件不存在则创建。
// ax+	类似 'a+'， 但是如果文件路径存在，则文件读取追加失败。
// 异步打开文件
console.log("准备打开文件！");
fs.open('input.txt', 'r+', function(err, fd) {
    if (err) {
        return console.error(err);
    }
    console.log("文件打开成功！" + fd); //4
    // console.log(util.inspect(fd));

    //以下为异步模式下关闭文件的语法格式：
    // fs.close(fd, callback)
});


fs.stat('input.txt', function (err, stats) {
    console.log(stats.isFile());         //true
    console.log(stats);      //
    // { dev: 617379568,
    //     mode: 33206,
    //     nlink: 1,
    //     uid: 0,
    //     gid: 0,
    //     rdev: 0,
    //     blksize: undefined,
    //     ino: 32088147345121000,
    //     size: 67,
    //     blocks: undefined,
    //     atime: 2017-10-13T05:46:18.859Z,
    //     mtime: 2017-10-13T05:46:18.861Z,
    //     ctime: 2017-10-13T05:46:18.895Z,
    //     birthtime: 2017-10-13T05:42:33.096Z }

    // stats.isFile()	如果是文件返回 true，否则返回 false。
    // stats.isDirectory()	如果是目录返回 true，否则返回 false。
    // stats.isBlockDevice()	如果是块设备返回 true，否则返回 false。
    // stats.isCharacterDevice()	如果是字符设备返回 true，否则返回 false。
    // stats.isSymbolicLink()	如果是软链接返回 true，否则返回 false。
    // stats.isFIFO()	如果是FIFO，返回true，否则返回 false。FIFO是UNIX中的一种特殊类型的命令管道。
    // stats.isSocket()	如果是 Socket 返回 true，否则返回 false。
})



var buf = new Buffer(1024);

//fs.read(fd, buffer, offset, length, position, callback)
console.log("准备打开已存在的文件！");
fs.open('input.txt', 'r+', function(err, fd) {
    if (err) {
        return console.error(err);
    }
    console.log("文件打开成功！");
    console.log("准备读取文件：");
    fs.read(fd, buf, 0, buf.length, 0, function(err, bytes){
        if (err){
            console.log(err);
        }
        console.log(bytes + "  字节被读取");

        // 仅输出读取的字节
        if(bytes > 0){
            console.log(buf.slice(0, bytes).toString());
        }

        // 关闭文件
        fs.close(fd, function(err){
            if (err){
                console.log(err);
            }
            console.log("文件关闭成功");
        });
    });
});

//fs.ftruncate(fd, len, callback) 截取文件
fs.open('input.txt', 'r+', function(err, fd) {
    if (err) {
        return console.error(err);
    }
    console.log("文件打开成功！");
    console.log("截取10字节后的文件内容。");//

    // 截取文件
    fs.ftruncate(fd, 10, function(err){
        if (err){
            console.log(err);
        }
        console.log("文件截取成功。");
        console.log("读取相同的文件");
        fs.read(fd, buf, 0, buf.length, 0, function(err, bytes){
            if (err){
                console.log(err);
            }

            // 仅输出读取的字节
            if(bytes > 0){
                console.log(buf.slice(0, bytes).toString());
            }

            // 关闭文件
            fs.close(fd, function(err){
                if (err){
                    console.log(err);
                }
                console.log("文件关闭成功！");
            });
        });
    });
});

// 以下为删除文件的语法格式：
// fs.unlink(path, callback)

// fs.unlink('input.txt', function(err) {
//     if (err) {
//         return console.error(err);
//     }
//     console.log("文件删除成功！");
// });

//以下为创建目录的语法格式：
// fs.mkdir(path[, mode], callback)
fs.mkdir("tmp/test/",function(err){
    if (err) {
        return console.error(err);
    }
    console.log("目录创建成功。");

    // { Error: ENOENT: no such file or directory, mkdir 'D:\tmp\test'
    //     at Error (native)
    //     errno: -4058,
    //         code: 'ENOENT',
    //     syscall: 'mkdir',
    //     path: 'D:\\tmp\\test' }


});


// 以下为读取目录的语法格式：
// fs.readdir(path, callback)

console.log("查看 /tmp 目录");
// fs.readdir("../src",function(err, files){

// .代表当前路径
// ..代表上级
fs.readdir(".",function(err, files){
    if (err) {
        return console.error(err);
    }
    files.forEach( function (file){
        console.log( file );
    });
});

//以下为删除目录的语法格式：
// fs.rmdir(path, callback)
// fs.rmdir("/tmp/test",function(err){
//     if (err) {
//         return console.error(err);
//     }
//     console.log("读取 /tmp 目录");
//     fs.readdir("/tmp/",function(err, files){
//         if (err) {
//             return console.error(err);
//         }
//         files.forEach( function (file){
//             console.log( file );
//         });
//     });
// });