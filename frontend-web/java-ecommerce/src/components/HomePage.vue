<template>
    <div class="beheerproducten">
      <AppHeader />
      <section class="featured-products">
        <aside class="filters">
          <h3>Filter Producten</h3>
          <div class="filter-group">
            <label for="category">Category</label>
            <select id="category" v-model="selectedCategory" @change="filterProducts">
              <option value="">All</option>
              <option value="category1">Category 1</option>
              <option value="category2">Category 2</option>
              <!-- Add more categories as needed -->
            </select>
          </div>
          <div class="filter-group">
            <label for="tag">Tag</label>
            <select id="tag" v-model="selectedTag" @change="filterProducts">
              <option value="">All</option>
              <option value="tag1">Tag 1</option>
              <option value="tag2">Tag 2</option>
              <!-- Add more tags as needed -->
            </select>
          </div>
          <div class="filter-group">
            <label for="price">Price</label>
            <select id="price" v-model="selectedPrice" @change="filterProducts">
              <option value="">All</option>
              <option value="low">Low to High</option>
              <option value="high">High to Low</option>
            </select>
          </div>
          <div class="filter-group">
            <button @click="filterProducts">Apply Filters</button>
        </div>
        <div class="filter-group">
              <input class="search-field" type="text" placeholder="Zoek product..." v-model="searchQuery" @input="searchProducts" />
            </div>
            <div class="filter-group" style="margin-left: 0px;">
                <button class="search-btn" @click="searchNow">Search</button>
            </div>  
        </aside>
        <div class="content">
          <div class="products-container">
            <h3 class="products-heading">Beschikbare Producten</h3>
            <div class="products">
              <div v-for="product in featuredProducts" :key="product.id" class="product">
                <img :src="product.image" :alt="product.name" />
                <h3>{{ product.name }}</h3>
                <p>{{ product.price }}</p>
                <button @click="addToCart(product)">Add to Cart</button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </template>
  
  <script>
import AppHeader from '@/components/AppHeader.vue';

  export default {
    name: 'HomePage',
    components: {
    AppHeader
  },
    data() {
      return {
        featuredProducts: [
          { id: 1, name: 'Product 1', price: '$10.00', image: 'assets/products/.jpg' },
          { id: 2, name: 'Product 2', price: '$10.00', image: 'assets/products/.jpg' },
          { id: 3, name: 'Product 3', price: '$10.00', image: 'assets/products/.jpg' }
        ],
        searchQuery: '',
        selectedCategory: '',
        selectedTag: '',
        selectedPrice: ''
      };
    },
    methods: {
      shopNow() {
        // Logic to navigate to the products page
        console.log('Navigating to products page');
      },
      addToCart(product) {
        // Logic to add the product to the cart
        console.log('Added to cart:', product);
      },
      searchProducts() {
        // Logic to search products
        console.log('Searching for:', this.searchQuery);
      },
      filterProducts() {
        // Logic to filter products
        console.log('Filtering products');
      }
    }
  };
  </script>
