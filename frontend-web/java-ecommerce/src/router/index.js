// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/HomePage.vue';
import AdminPage from '@/components/AdminPage.vue';
import ShoppingCart from '@/components/ShoppingCart.vue';
import OrderConfirmation from '@/components/OrderConfirmation.vue';


const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: Home
  },
  {
    path: '/beheer',
    name: 'AdminPage',
    component: AdminPage
  },
  {
    path: '/winkelwagen',
    name: 'Winkelwagen',
    component: ShoppingCart
  },
  {
    path: '/winkelwagen',
    name: 'OrderConfirmation',
    component: OrderConfirmation
  },

];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;