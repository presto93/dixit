import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import Vue3Storage, {StorageType} from "vue3-storage"

createApp(App)
    .use(router)
    .use(Vue3Storage, { namespace: "pro_", storage: StorageType.Local })
    .mount('#app')
