<template>
  <div class="beheerproducten">
    <AppHeader />
    <section class="add-product">
      <h3>Voeg een nieuw product toe</h3>
      <form class="addProductForm" @submit.prevent="addProduct">
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" v-model="newProduct.name" required />
        </div>
        <div class="form-group">
          <label for="price">Price</label>
          <input type="number" id="price" v-model="newProduct.price" required />
        </div>
        <div class="form-group">
          <label for="tags">Tags</label>
          <input id="tags" v-model="newProduct.tags" required placeholder="seperate via ';'" />
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" v-model="newProduct.description" required></textarea>
        </div>
        <div class="form-group">
          <label for="category">Category</label>
          <select id="category" v-model="newProduct.category" required class="dropdown-cat">
            <option value="ELECTRONICS">ELECTRONICS</option>
            <option value="CLOTHING">CLOTHING</option>
            <option value="FOOD">FOOD</option>
            <option value="BOOKS">BOOKS</option>
            <option value="TOYS">TOYS</option>
          </select>
        </div>
        <div class="form-group">
          <label for="rating">Duurzaamheid (1-5)</label>
          <select id="rating" v-model="newProduct.rating" required class="dropdown-rating">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select>
        </div>
        <button id="toevoegenProduct" type="submit" class="btn-send" >Toevoegen Product</button>
      </form>
    </section>
    <section class="add-product">
      <h3>Ophalen LogService</h3>
      <form class="GetLogForm" @submit.prevent="GetLog">        
        <button type="submit" class="btn-send" >Ophalen Log</button>
      </form>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import AppHeader from '@/components/AppHeader.vue';

export default {
  name: 'AdminPage',
  components: {
    AppHeader
  },
  data() {
    return {
      featuredProducts: [],
      newProduct: {
        name: '',
        price: '',
        description: '',
        category: '', // Add category property
        rating: '', // Add rating property
        picture: ''
      }
    };
  },
  methods: {
    addProduct() {
      const newProduct = {
        name: this.newProduct.name,
        price: this.newProduct.price,
        description: this.newProduct.description,
        category: this.newProduct.category,
        rating: this.newProduct.rating,
        image: this.newProduct.picture
      };
      axios.post('http://localhost:8090/product/api/product', newProduct)
        .then(response => {
          console.log('Product added:', response.data);
          this.featuredProducts.push(response.data);
          this.newProduct = { name: 'test', price: '105', description: '', category: '', rating: '', picture: '' };
        })
        .catch(error => {
          console.error('Error adding product:', error);
        });
    },
    addToCart(product) {
      // Logic to add the product to the cart
      console.log('Added to cart:', product);
    }
  }
};
</script>