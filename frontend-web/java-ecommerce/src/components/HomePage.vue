<template>
  <div class="beheerproducten">
    <AppHeader/>
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
        <!--
        <div class="filter-group">
          <label for="tag">Tag</label>
          <select id="tag" v-model="selectedTag" @change="filterProducts">
            <option value="">All</option>
            <option value="tag1">Tag 1</option>
            <option value="tag2">Tag 2</option>

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
        -->
        <div class="filter-group">
          <button @click="filterProducts">Apply Filters</button>
        </div>
        <div class="filter-group" style="margin-left: 0px;">
          <input class="search-field" type="text" placeholder="Zoek product..." v-model="searchQuery"
                 @input="searchProducts"/>
        </div>
      </aside>
      <div class="content">
        <div class="products-container">
          <h3 class="products-heading">Beschikbare Producten</h3>
          <div class="products">
            <div v-for="product in filteredProducts" :key="product.id" class="product-card">
              <img src="/assets/products/dummy-image.png" :alt="product.name" class="product-image"/>
              <div class="product-details">
                <h3 class="product-name">{{ product.name }} - id ({{ product.id }})</h3>
                <p class="product-price">Prijs : {{ product.price }} eur</p>
                <p class="product-category">Categorie : {{ product.category }}</p>
                <p class="product-tag">{{ product.tag }}</p>
                <p class="product-description">{{ product.description }}</p>
                <p class="product-star">{{ product.star }}</p>
                <button @click="addToCart(product)" class="add-to-cart-btn">Add to Cart</button>
                <button v-if="isLoggedIn" @click="editProduct(product)" class="add-to-cart-btn edit-btn"
                        style="margin: 5px">Edit
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import Cookies from 'js-cookie'
import AppHeader from '@/components/AppHeader.vue';


export default {
  name: 'HomePage',
  components: {
    AppHeader
  },
  data() {
    return {
      featuredProducts: [],
      filteredProducts: [],
      searchQuery: '',
      selectedCategory: '',
      selectedTag: '',
      selectedPrice: '',
      isLoggedIn: false
    };
  },
  created() {
    this.isLoggedInCookie();
    this.fetchProducts();
  },
  methods: {
    isLoggedInCookie() {
      console.log('Checking if user is logged in');
      this.isLoggedIn = this.checkCookie('userCookie');
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
    fetchProducts() {
      axios.get('http://localhost:8090/product/api/product')
          .then(response => {
            this.featuredProducts = response.data;
            this.filteredProducts = [...this.featuredProducts];
            console.log('Products fetched:', this.featuredProducts);
          })
          .catch(error => {
            console.error('Error fetching products:', error);
          });
    },
    addToCart(product) {
      let cartId = Cookies.get('cartId');
      //console.log('Added to cart:', product.id);
      axios.put(`http://localhost:8090/shop/api/shop/${cartId}/product/${product.id}`)
          .then(response => {
            console.log('Product added to cart:', response.data);
            alert("Artikel toegevoegd aan winkelwagen");
          })
          .catch(error => {
            console.error('Error adding product to cart:', error);
          });
    },
    searchProducts() {
      console.log('Searching for:', this.searchQuery);

      if (this.searchQuery.trim() === '') {
        // If search query is empty, show all products
        this.filteredProducts = [...this.featuredProducts];
      } else {
        // If there's a search query, filter products by name
        this.filteredProducts = this.featuredProducts.filter(product => {
          return product.name.toLowerCase().includes(this.searchQuery.toLowerCase());
        });
      }
    },
    filterProducts() {
      // Logic to filter products
      console.log('Filtering products');
    },
    editProduct(product) {
      console.log('Editing product:', product);
      this.$router.push({ name: 'EditProduct', query: { id: product.id } });
    }
  }
};
</script>

<style scoped>
.products {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.product-card {
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  width: calc(33.333% - 20px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: center;
}

.product-image {
  width: 40%;
  height: 100%;
  object-fit: cover;
  margin-left: auto;
  margin-right: auto;
}

.product-details {
  padding: 15px;
}

.product-name {
  font-size: 1.2em;
  margin: 0 0 10px;
}

.product-price {
  color: #e91e63;
  font-size: 1.1em;
  margin: 0 0 10px;
}

.product-category,
.product-tag,
.product-description,
.product-star {
  margin: 5px;
  padding: 2px 5px;
}

.add-to-cart-btn {
  background-color: #45a049;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.edit-btn {
  background-color: #f57c00;
}

.add-to-cart-btn:hover {
  background-color: #2c3e50;
  color: #dddddd;
}
</style>