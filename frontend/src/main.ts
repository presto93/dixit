import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import {loadFonts} from './plugins/webfontloader'
import Vue3Storage, {StorageType} from 'vue3-storage'

loadFonts()

// .use(store)
createApp(App)
    .use(router)
    .use(vuetify)
    .use(Vue3Storage, {namespace: "pro_", storage: StorageType.Local})
    .mount('#app')
