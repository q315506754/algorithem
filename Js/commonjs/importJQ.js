// document.body.appendChild(`< script src="https://cdn.bootcss.com/jquery/1.8.2/jquery.js"></script>`);


var myScript= document.createElement("script");
myScript.type = "text/javascript";
myScript.src="https://cdn.bootcss.com/jquery/1.8.2/jquery.js";
document.body.appendChild(myScript);

$("#projectstatus tbody tr:visible").each(function(idx,ele){
    // console.log($(ele).find("td:eq(2) a").attr("href"));
    console.log($(ele).children("td:eq(2)").find("a").attr("href"));
    // console.log($(ele));
});