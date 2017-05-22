var webpack = require('webpack');
//var HtmlWebpackPlugin = require('html-webpack-plugin');//Hot Module Replacement
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  devtool: 'eval-source-map',//配置生成Source Maps，选择合适的选项
  entry:  __dirname + "/app/main.js",//已多次提及的唯一入口文件
  output: {
    path: __dirname + "/public",//打包后的文件存放的地方
    filename: "[name]-[hash].js"//打包后输出文件的文件名
    // filename: "bundle-dev.js"//打包后输出文件的文件名
  },

  //  http://localhost:8080/
  devServer: {
    contentBase: "./public",//本地服务器所加载的页面所在的目录
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
           template: __dirname + "/app/index.tmpl.html"
       }),
    new webpack.BannerPlugin("aaa Copyright Flying Unicorns inc.")//在这个数组中new一个就可以了
	,new webpack.optimize.UglifyJsPlugin(),//minify
    new webpack.HotModuleReplacementPlugin()//热加载插件
  ],

}


//cnpm install -g  webpack
//cnpm install --save-dev extract-text-webpack-plugin
//注：“__dirname”是node.js中的一个全局变量，它指向当前执行脚本所在的目录。

//安装可以装换JSON的loader  JSON->Object
//cnpm install --save-dev json-loader

// npm一次性安装多个依赖模块，模块之间用空格隔开
//cnpm install --save-dev babel-core babel-loader babel-preset-es2015 babel-preset-react

//cnpm install --save react react-dom

////安装
//cnpm install --save-dev style-loader css-loader

//首先安装postcss-loader 和 autoprefixer（自动添加前缀的插件）

//cnpm install --save-dev postcss-loader autoprefixer

//Loaders和Plugins常常被弄混，但是他们其实是完全不同的东西，可以这么来说，loaders是在打包构建过程中用来处理源文件的（JSX，Scss，Less..），一次处理一个，插件并不直接操作单个文件，它直接对整个构建过程其作用。

//Webpack有很多内置插件，同时也有很多第三方插件，可以让我们完成更加丰富的功能。

//cnpm install --save-dev html-webpack-plugin


//安装react-transform-hmr
//cnpm install --save-dev babel-plugin-react-transform react-transform-hmr