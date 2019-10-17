/* global __dirname, require, module */

const path = require('path');

module.exports = {
  entry: {
    app: './src-ui/app.tsx'
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, 'dist')
  },
  resolve: {
    alias: {
      'src-ui': path.resolve(__dirname, 'src-ui/'),
    },
    extensions: ['.tsx', '.ts', '.js']
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        loader: 'ts-loader',
        options: {
          compiler: 'typescript',
          onlyCompileBundledFiles: true
        }
      }
    ]
  },
  devServer: {
    contentBase: path.join(__dirname, 'dist'),
    compress: true,
    port: 9000,
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, OPTIONS',
      'Access-Control-Allow-Headers': 'X-Requested-With, content-type'
    },
    allowedHosts: ['localhost']
  },
  devtool: 'cheap-module-eval-source-map'
};
