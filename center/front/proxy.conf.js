const PROXY_CONFIG = [
  {
    context: [
      "/api"
    ],
    target: "https://localhost:8769/",
    logLevel: "debug",
    secure: true,
    changeOrigin: true
  }
]
module.exports = PROXY_CONFIG;
