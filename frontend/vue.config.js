const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,

    devServer: {
        port:3000,
        proxy: {
            // /api 및 /api/* 요청에 대해 프록시 설정
            '/api': {
                target: 'http://localhost:8080/', // 프록시를 설정할 도메인
                changeOrigin: true,
            },
            '/ws': {
                target: 'http://localhost:8080/', // 프록시를 설정할 도메인
                ws: true,
                changeOrigin: true,
            },
        },
    },

    pluginOptions: {
      vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		}
    },
    publicPath: '/dixit',
    outputDir: '..\\src\\main\\resources\\static\\'
})
