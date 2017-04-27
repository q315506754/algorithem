var webpack = require('webpack');
//var HtmlWebpackPlugin = require('html-webpack-plugin');//Hot Module Replacement
//var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
  devtool: 'source-map',//��������Source Maps��ѡ����ʵ�ѡ��
  entry:  __dirname + "/app/main.js",//�Ѷ���ἰ��Ψһ����ļ�
  output: {
    path: __dirname + "/public",//�������ļ���ŵĵط�
    filename: "bundle.js"//���������ļ����ļ���
  },
  
  devServer: {
    contentBase: "./public",//���ط����������ص�ҳ�����ڵ�Ŀ¼
    //colors: true,//�ն���������Ϊ��ɫ
    historyApiFallback: true,//����ת
    inline: true,//ʵʱˢ��
	hot: true //hot deploy
  } ,
  
   module: {//�������ļ������JSON loader
    loaders: [
      {
        test: /\.json$/,
        loader: "json-loader"
      },
	   {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader',//��webpack��module���ֵ�loaders��������ü���
       // query: {
         // presets: ['es2015','react']
       // }
      },
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader?modules'//��Ӷ���ʽ��Ĵ��� ע����̾�ŵ���������ʹͬһ�ļ��ܹ�ʹ�ò�ͬ���͵�loader
		
		//css-loaderʹ���ܹ�ʹ������@import �� url(...)�ķ���ʵ�� require()�Ĺ���,
		//style-loader�����еļ�������ʽ����ҳ���У����������һ��ʹ���ܹ�����ʽ��Ƕ��webpack������JS�ļ��С�
		
		//�����һ������ CSS modules �ļ��������ڰ�JS��ģ�黯˼�����CSS������ͨ��CSSģ�飬���е�������
      }
    ]
  },
  
   plugins: [
    new webpack.BannerPlugin("Copyright Flying Unicorns inc.")//�����������newһ���Ϳ�����
	,
    new webpack.HotModuleReplacementPlugin()//�ȼ��ز��
	,
 //   new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.optimize.UglifyJsPlugin(),
	new ExtractTextPlugin("style.css")
  ],

}

//ע����__dirname����node.js�е�һ��ȫ�ֱ�������ָ��ǰִ�нű����ڵ�Ŀ¼��

//��װ����װ��JSON��loader  JSON->Object
//cnpm install --save-dev json-loader

// npmһ���԰�װ�������ģ�飬ģ��֮���ÿո����
//cnpm install --save-dev babel-core babel-loader babel-preset-es2015 babel-preset-react

//cnpm install --save react react-dom

////��װ
//cnpm install --save-dev style-loader css-loader

//���Ȱ�װpostcss-loader �� autoprefixer���Զ����ǰ׺�Ĳ����

//cnpm install --save-dev postcss-loader autoprefixer

//Loaders��Plugins������Ū�죬����������ʵ����ȫ��ͬ�Ķ�����������ô��˵��loaders���ڴ��������������������Դ�ļ��ģ�JSX��Scss��Less..����һ�δ���һ�����������ֱ�Ӳ��������ļ�����ֱ�Ӷ������������������á�

//Webpack�кܶ����ò����ͬʱҲ�кܶ�����������������������ɸ��ӷḻ�Ĺ��ܡ�

//npm install --save-dev html-webpack-plugin


//��װreact-transform-hmr
//npm install --save-dev babel-plugin-react-transform react-transform-hmr

//ExtractTextPlugin������CSS��JS�ļ�
//npm install --save-dev extract-text-webpack-plugin