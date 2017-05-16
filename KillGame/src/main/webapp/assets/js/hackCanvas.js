(function ($) {
    var dtAttr = "hackCanvas";

    $.fn.hackCanvas = function(options){
        var opt = $.extend({}, defaults, options);

        this.each(function () {
            //获取画布对象
            var canvas = this;

            //
            $(this).css("background","#111");
            $(this).css("z-index","-1");
            $(this).css("position","absolute");
            $(this).css("left","0px");
            $(this).css("top","0px");
            $("body").css("color","white");


            // $(window).resize(function() {
            //     console.log('adsad');
            // });

            //获取画布的上下文
            var context =canvas.getContext("2d");

            //获取浏览器屏幕的宽度和高度
            // var W = window.innerWidth; （浏览器大小，包括滚动条,可视区域）
            // $(document.body).width() （窗口可视区的) 略小于 $(window).width()

            // $(window).width()（窗口可视区的，包括滚动条,可视区域）= document.body.clientWidth
            // $(document).width() （窗口文档的，包括非可视区域）
            var W = window.outerWidth;//（窗口文档的，包括非可视区域） 略小于 $(document).width()

            // var H = window.innerHeight;
            var H = window.outerHeight;

            //显示器宽高
            //window.screen.width
            // window.screen.height


            //设置canvas的宽度和高度
            canvas.width = W;
            canvas.height = H;
            //每个文字的字体大小
            var fontSize = 16;
            //计算列
            var colunms = Math.floor(W /fontSize);
            //记录每列文字的y轴坐标
            var drops = [];
            //给每一个文字初始化一个起始点的位置
            for(var i=0;i<colunms;i++){
                drops.push(0);
            }

            //运动的文字
            var str =opt.words;
            //4:fillText(str,x,y);原理就是去更改y的坐标位置
            //绘画的函数
            function draw(){
                context.fillStyle = "rgba(0,0,0,0.05)";
                context.fillRect(0,0,W,H);
                //给字体设置样式
                context.font = "700 "+fontSize+"px  微软雅黑";
                //给字体添加颜色
                context.fillStyle ="#00cc33";//可以rgb,hsl, 标准色，十六进制颜色
                //写入画布中
                for(var i=0;i<colunms;i++){
                    var index = Math.floor(Math.random() * str.length);
                    var x = i*fontSize;
                    var y = drops[i] *fontSize;
                    context.fillText(str[index],x,y);
                    //如果要改变时间，肯定就是改变每次他的起点
                    if(y >= canvas.height && Math.random() > 0.99){
                        drops[i] = 0;
                    }
                    drops[i]++;
                }
            };

            function randColor(){
                var r = Math.floor(Math.random() * 256);
                var g = Math.floor(Math.random() * 256);
                var b = Math.floor(Math.random() * 256);
                return "rgb("+r+","+g+","+b+")";
            }

            draw();
            var ts = setInterval(draw,opt.interval);

            $(this).data(dtAttr,ts);
        });
        return this;
    }

    var defaults = {
        words:"javascript html5 canvas",
        interval:30
    };
})(jQuery);