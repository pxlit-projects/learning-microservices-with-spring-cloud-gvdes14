<!-- src/components/AppHeader.vue -->
<template>
    <header>
      <h1>Welkom op de Productenpagina</h1>
      <nav>
        <ul class="navbar">
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/winkelwagen">Winkelwagen</router-link></li>
          <li><router-link to="/beheer">Beheer</router-link></li>

        </ul>
      </nav>
      <nav>
        <ul class="navbar-login">
          <li><a>Login</a></li> <!-- TODO Tovoegen login funtionaliteit (user + pwd)-->
        </ul>
      </nav>
    </header>
  </template>
  
  <script>
  export default {
    name: 'AppHeader'
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
    margin:0px 50px;
  }
  </style>