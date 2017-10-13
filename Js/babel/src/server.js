var server = require("./http");
var router = require("./router");

//localhost:8888/aa/b?c=ddd
server.start(router.route);
// search: '?c=ddd',
//     query: 'c=ddd',
//     pathname: '/aa/b',
//     path: '/aa/b?c=ddd',
//     href: '/aa/b?c=ddd'
