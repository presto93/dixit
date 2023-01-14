const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        proxy: {
            // /api 및 /api/* 요청에 대해 프록시 설정
            '/api': {
                target: 'http://localhost:8080', // 프록시를 설정할 도메인
                changeOrigin: true,
            },
        }
    }
})
