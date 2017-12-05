var webpack = require('webpack');
//var HtmlWebpackPlugin = require('html-webpack-plugin');//Hot Module Replacement
//var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var CleanWebpackPlugin = require('clean-webpack-plugin'); // 删除文件

module.exports = {
  // devtool: 'source-map',//配置生成Source Maps，选择合适的选项
  // devtool: 'eval-source-map',//配置生成Source Maps，选择合适的选项
  // entry:  __dirname + "/src/main.js",//已多次提及的唯一入口文件
  entry:  {
      main:  __dirname + "/src/js/index.js",
      page2:[__dirname + "/src/js/page2.js"],
      //添加要打包在vendeors里面的库
      commons: ['jquery']//必须依赖了才添加 否则报错
  },
  output: {
      path: __dirname + "/dist",//打包后的文件存放的地方
      // filename: "[name]-[hash].js"//打包后输出文件的文件名
      filename: "js/[name].js"//打包后输出文件的文件名
  },

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
        // loader: 'style-loader!css-loader?modules'//添加对样式表的处理 注：感叹号的作用在于使同一文件能够使用不同类型的loader
        //   loader:ExtractTextPlugin.extract({ fallback:'style-loader',  use:'css-loader?modules!postcss'})
          loader:ExtractTextPlugin.extract({ fallback:'style-loader',  use:'css-loader?modules'})

		//css-loader使你能够使用类似@import 和 url(...)的方法实现 require()的功能,
		//style-loader将所有的计算后的样式加入页面中，二者组合在一起使你能够把样式表嵌入webpack打包后的JS文件中。
		
		//最近有一个叫做 CSS modules 的技术就意在把JS的模块化思想带入CSS中来，通过CSS模块，所有的类名，
      }
    ]
  },
    // postcss: [
    //     require('autoprefixer')
    // ],

   plugins: [
   //把入口文件里面的数组打包成vendors.js
   new webpack.optimize.CommonsChunkPlugin({
       name: "commons",
       // (the commons chunk name)

       filename: "js/commons.js",
       // (the filename of the commons chunk)

       // minChunks: 3,
       // (Modules must be shared between 3 entries)

       // chunks: ["pageA", "pageB"],
       // (Only use these entries)
   }),
   new HtmlWebpackPlugin({
       template: __dirname + "/src/html/index.tmpl.html",
       filename:'main.html',
       hash:true , //commons.js?ce7633e5f90d57c5c1ef
       chunks:['main','commons']
   }),
   new HtmlWebpackPlugin({
       template: __dirname + "/src/html/page2.tmpl.html",
       // filename: __dirname+'/build/html/index-build.html',
       filename:'page2.html',
       hash:true , //commons.js?ce7633e5f90d57c5c1ef
       chunks:['page2','commons']
   }),
       new CleanWebpackPlugin(['dist'], {
           // root: ROOT_PATH,
           // verbose: true,
           // dry: false,
           //exclude: ["dist/1.chunk.js"]
       }),
    new webpack.BannerPlugin("Copyright Jiangli(product).")//在这个数组中new一个就可以了
	,
   // new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.optimize.UglifyJsPlugin({
        compress: {
            warnings: false
        },
        sourceMap: true,//这里的soucemap 不能少，可以在线上生成soucemap文件，便于调试
        mangle: true
    }),
	new ExtractTextPlugin("css/[name].css")
  ],

}
