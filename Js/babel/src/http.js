var http = require('http')
var url = require('url')

function start (route) {
  function onRequest (request, response) {
    let parse = url.parse(request.url)
    console.log(parse)
    var pathname = parse.pathname
    console.log('Request for ' + pathname + ' received.')

    route(pathname)

    response.writeHead(200, {'Content-Type': 'text/plain'})
    response.write('Hello World')
    response.end()
  }

  http.createServer(onRequest).listen(8888)
  console.log('Server has started.')
}

exports.start = start
