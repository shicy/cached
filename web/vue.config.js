module.exports = {
  publicPath: "/web",
  outputDir: "../cache_service/src/main/resources/static/web",
  productionSourceMap: false,
  devServer: {
    disableHostCheck: true,
    host: "0.0.0.0",
    port: 3000,
    proxy: {
      "^/api": {
        target: "http://localhost:12302",
        pathRewrite: { "^/api": "" }
      }
    }
  }
};