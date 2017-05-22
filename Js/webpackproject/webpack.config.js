var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var CleanWebpackPlugin = require('clean-webpack-plugin'); // 删除文件

module.exports = {
  devtool: 'eval-source-map',//配置生成Source Maps，选择合适的选项
  // entry:  __dirname + "/src/main.js",//已多次提及的唯一入口文件
    entry:  {
        main:  __dirname + "/src/main.js",
        page2:[__dirname + "/src/page2.js"]
    },
  output: {
    path: __dirname + "/target",//打包后的文件存放的地方
    // filename: "[name]-[hash].js"//打包后输出文件的文件名
    filename: "[name]-dev.js"//打包后输出文件的文件名
  },

  //  http://localhost:8080/
  devServer: {
    contentBase: "./target",//本地服务器所加载的页面所在的目录
    //colors: true,//终端中输出结果为彩色
    //   port	设置默认监听端口，如果省略，默认为”8080“
    historyApiFallback: true,//不跳转
    inline: true,//实时刷新 当源文件改变时会自动刷新页面
	hot: true //hot deploy
  } ,
  
   module: {//在配置文件里添加JSON loader
    loaders: [
      {
        test: /\.json$/,
        loader: "json-loader"
      },
	   {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader',//在webpack的module部分的loaders里进行配置即可
       // query: {
         // presets: ['es2015','react']
       // }
      },
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader?modules'//添加对样式表的处理 注：感叹号的作用在于使同一文件能够使用不同类型的loader
		
		//css-loader使你能够使用类似@import 和 url(...)的方法实现 require()的功能,
		//style-loader将所有的计算后的样式加入页面中，二者组合在一起使你能够把样式表嵌入webpack打包后的JS文件中。
		
		//最近有一个叫做 CSS modules 的技术就意在把JS的模块化思想带入CSS中来，通过CSS模块，所有的类名，
      }
    ]
  },
  
   plugins: [
       new HtmlWebpackPlugin({
           template: __dirname + "/src/index.tmpl.html",
           filename:'main.html',
           hash:true , //commons.js?ce7633e5f90d57c5c1ef
           chunks:['main','commons']
       }),
       new HtmlWebpackPlugin({
           template: __dirname + "/src/page2.tmpl.html",
           // filename: __dirname+'/build/html/index-build.html',
           filename:'page2.html',
           hash:true , //commons.js?ce7633e5f90d57c5c1ef
           chunks:['page2','commons']
       }),
       new CleanWebpackPlugin(['target'], {
       }),
    new webpack.BannerPlugin("Copyright Jiangli."),//在这个数组中new一个就可以了
    new webpack.HotModuleReplacementPlugin()//热加载插件
  ],

}
