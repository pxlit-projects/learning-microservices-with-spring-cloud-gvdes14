// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/style.css'; // Import global CSS



const app = createApp(App);

const observerErrorHandler = window.onerror;
window.onerror = function (message, source, lineno, colno, error) {
    if (String(message).includes('ResizeObserver loop completed')) {
        return; // Negeer de waarschuwing
    }
    if (observerErrorHandler) {
        observerErrorHandler(message, source, lineno, colno, error);
    }
};

app.use(router); // Voeg hier de router toe
app.mount('#app');

