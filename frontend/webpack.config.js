var webpack           = require('webpack');
var path              = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");

var CURRENTDIR = path.resolve(__dirname);
var BUILD_DIR  = path.resolve(__dirname, 'public');
var APP_DIR    = path.resolve(__dirname, 'src');

var config = {
  entry: {
    javascript: APP_DIR + '/javascripts/index.tsx',
  },
  output: {
    path: BUILD_DIR,
    filename: '/bundle.js'
  },
  module: {
    loaders : [
      {
        test: /\.tsx?$/,
        include: APP_DIR,
        use: [ { loader : 'babel-loader' }, { loader: 'ts-loader'}]
      },
      {
        test: /\.scss$/,
        include: APP_DIR,
        loader: ExtractTextPlugin.extract('css-loader!sass-loader')
      },
      {
        test: /\.ya?ml$/,
        include: CURRENTDIR,
        loader: 'json-loader!yaml-loader'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: 'src/index.html'
    }),
    new ExtractTextPlugin({
      filename: '/bundle.css',
      allChunks: true
    }),
    new webpack.DefinePlugin({
      ENVIRONMENT:  JSON.stringify('development'),
      BASE_API_URL: JSON.stringify('http://localhost:8000')
    })
  ]
};

module.exports = config;
