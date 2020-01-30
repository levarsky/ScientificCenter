const PROXY_CONFIG = [
  {
    context: [
      "/sellers",
      "/registration"
    ],
    target: "https://localhost:8088",
    logLevel: "debug",
    secure: false,
    changeOrigin: true
  }
]
module.exports = PROXY_CONFIG;
