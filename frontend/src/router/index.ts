import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ReadyView from '../views/ReadyView.vue'
import CheckCardView from '../views/CheckCardView.vue'
import DisplayCardView from '../views/DisplayCardView.vue'
import AdminView from '../views/AdminView.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: HomeView,
    },
    {
        path: '/ready',
        name: 'ready',
        component: ReadyView,
    },
    {
        path: '/check-card',
        name: 'check card',
        component: CheckCardView,
    },
    {
        path: '/display-card',
        name: 'display card',
        component: DisplayCardView,
    },
    {
        path: '/admin',
        name: 'admin',
        component: AdminView,
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


export default router
