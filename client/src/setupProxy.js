const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://ec2-3-35-27-72.ap-northeast-2.compute.amazonaws.com:8080',
      changeOrigin: true,
    })
  );
};
