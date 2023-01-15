import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ReadyView from '../views/ReadyView.vue'
import StartView from '../views/StartView.vue'
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
        props: route => ({
            userId: route.query.userId,
        }),
    },
    {
        path: '/start',
        name: 'start',
        component: StartView,
        props: route => ({
            userId: route.query.userId,
        }),
    },
    {
        path: '/check-card',
        name: 'check card',
        component: CheckCardView,
        props: route => ({
            userId: route.query.userId,
        }),
    },
    {
        path: '/display-card',
        name: 'display card',
        component: DisplayCardView,
        props: route => ({
            userId: route.query.userId,
        }),
    },
    {
        path: '/admin',
        name: 'admin',
        component: AdminView,
        props: route => ({
            userId: route.query.userId,
        }),
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


export default router
