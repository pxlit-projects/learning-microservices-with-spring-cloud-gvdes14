<template>
  <header>
    <h1>Welkom op de Productenpagina</h1>
    <nav>
      <ul class="navbar">
        <li>
          <router-link to="/">Home</router-link>
        </li>
        <li>
          <router-link to="/winkelwagen">Winkelwagen</router-link>
        </li>
        <li v-if="isLoggedIn"> <!-- Render conditionally -->
          <router-link to="/beheer">Beheer</router-link>
        </li>
      </ul>
    </nav>
    <nav>
      <ul class="navbar-login">
        <li>
          <!-- Login Toggle Slider -->
          <div class="login-slider">
            <label class="switch">
              <input type="checkbox" v-model="isLoggedIn" @change="handleLoginToggle">
              <span class="slider round"></span>
            </label>
            <span>{{ isLoggedIn ? 'Admin' : 'Guest' }}</span>
          </div>
        </li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'AppHeader',
  data() {
    return {
      isLoggedIn: false, // Default to logged out
    };
  },
  methods: {
    // Create Cookie
    setCookie(name, value, days) {
      let expires = '';
      if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = `; expires=${date.toUTCString()}`;
      }
      document.cookie = `${name}=${value || ''}${expires}; path=/`;
    },
    // Delete cookie
    removeCookie(name) {
      document.cookie = `${name}=; Max-Age=-99999999;`;
    },
    checkCookie(name) {
      const cookieString = document.cookie;
      const cookies = cookieString.split('; ').reduce((acc, cookie) => {
        const [key, value] = cookie.split('=');
        acc[key] = value;
        return acc;
      }, {});
      return cookies[name] !== undefined;
    },
    handleLoginToggle() {
      if (this.isLoggedIn) {
        // User will be logged in, create cookie
        this.setCookie('userCookie', 'loggedIn', 20);
      } else {
        // User will be logged out, remove cookie
        this.removeCookie('userCookie');
      }
    },
  },
  mounted() {
    this.isLoggedIn = this.checkCookie('userCookie');
  },
};
</script>

<style scoped>
header {
  background-color: #333;
  color: white;
  padding: 10px 0;
}

nav ul {
  list-style-type: none;
  padding: 0;
}

nav li {
  display: inline;
  margin: 0 15px;
}

nav a {
  color: white;
  text-decoration: none;
}

.navbar-login {
  text-align: right;
  float: right;
  position: absolute;
  top: 10px;
  right: 10px;
}

.navbar li {
  cursor: pointer;
  margin: 0px 50px;
}

/* Style for slider */
.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 24px;

}

.switch input {
  display: none;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: '';
  height: 18px;
  width: 18px;
  left: 4px;
  bottom: 3px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #4caf50;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.login-slider {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  font-weight: bold;
  color: white;
  margin-right: 50px;
}
</style>
