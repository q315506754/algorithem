document.write(`<script src="http://assets.zhihuishu.com/jquery/1.8.3/jquery.min.js" type="text/javascript" charset="utf-8"></script>`)
document.write(`<script src="https://assets.zhihuishu.com/jquery/1.8.3/jquery.min.js" type="text/javascript" charset="utf-8"></script>`)


//ssr https://www.youneed.win/free-ssr
var str = ""
$("tbody a").each(function (idx, ele) {
    str+=($(ele).attr('href') + '\n')||'';
})

console.log(str);

