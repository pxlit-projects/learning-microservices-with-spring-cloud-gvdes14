// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/style.css'; // Import global CSS

const app = createApp(App);

app.use(router); // Voeg hier de router toe
app.mount('#app');
