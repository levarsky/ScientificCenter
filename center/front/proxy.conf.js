const PROXY_CONFIG = [
  {
    context: [
      "/api"
    ],
    target: "https://localhost:8769",
    logLevel: "debug",
    secure: false,
    changeOrigin: true
  }
]
module.exports = PROXY_CONFIG;
