<template>
  <div class="beheerproducten">
    <AppHeader />
    <section class="add-product">
      <h3>Update geselecteerde product</h3>
      <form class="addProductForm" @submit.prevent="addProduct">
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" v-model="newProduct.name" required />
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" v-model="newProduct.description" required></textarea>
        </div>
        <div class="form-group">
          <label for="price">Price</label>
          <input type="number" id="price" v-model="newProduct.price" required />
        </div>
        <div class="form-group">
          <label for="tags">Tags</label>
          <input id="tags" v-model="newProduct.label" required placeholder="seperate via ';'" />
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

        <button @click="updateProduct()" id="toevoegenProduct" type="submit" class="btn-send" >Update Product</button>
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
        description: '',
        price:'',
        label:'',
        rating: '', // Add rating property
        category: '' // Add category property
      }
    };
  },
  created() {
    this.fetchProducts();
  },
  methods: {
    fetchProducts() {
      let selectedProduct = this.$route.query.id; // Get the product ID from the URL
      console.log('Fetching product :' + selectedProduct);
      axios.get(`http://localhost:8090/product/api/product/${selectedProduct}`)
          .then(response => {
            console.log('Product fetched:', response.data);
            // Populate `newProduct` with fetched data
            this.newProduct = {
              name: response.data.name || '',
              description: response.data.description || '',
              price: response.data.price || '',
              label: response.data.label || '',
              rating: response.data.rating || '', // Set rating value
              category: response.data.category || '' // Set category value
            };
          })
          .catch(error => {
            console.error('Error fetching product:', error);
          });
    },
    updateProduct() {
      let selectedProduct = this.$route.query.id; // Get the product ID from the URL
      console.log('Updating product :' + selectedProduct);
      axios.put(`http://localhost:8090/product/api/product/${selectedProduct}`, this.newProduct)
          .then(response => {
            console.log('Product updated:', response.data);
            alert("Artikel geupdate");
            this.$router.push('/');
          })
          .catch(error => {
            console.error('Error updating product:', error);
          });
    }
  }
};
</script>