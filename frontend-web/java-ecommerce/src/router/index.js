// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/HomePage.vue';
import AdminPage from '@/components/AdminPage.vue';

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
  // Voeg hier meer routes toe als dat nodig is
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
