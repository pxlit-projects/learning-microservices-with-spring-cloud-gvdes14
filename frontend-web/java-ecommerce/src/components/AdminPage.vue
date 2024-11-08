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
          <label for="description">Description</label>
          <textarea id="description" v-model="newProduct.description" required></textarea>
        </div>
        <div class="form-group">
          <label for="picture">Picture</label>
          <input type="text" id="picture" v-model="newProduct.picture" required />
        </div>
        <button type="submit">Toevoegen Product</button>
      </form>
    </section>
    <section class="add-product">
      <h3>Ophalen LogService</h3>
      <form class="addProductForm" @submit.prevent="addProduct">        
        <button type="submit">Ophalen Log</button>
      </form>
    </section>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';

export default {
  name: 'AdminPage',
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
      newProduct: {
        name: '',
        price: '',
        description: '',
        picture: ''
      }
    };
  },
  methods: {
    addProduct() {
      const newProduct = {
        id: this.featuredProducts.length + 1,
        name: this.newProduct.name,
        price: `$${this.newProduct.price}`,
        description: this.newProduct.description,
        image: this.newProduct.picture
      };
      this.featuredProducts.push(newProduct);
      this.newProduct = { name: '', price: '', description: '', picture: '' };
    },
    addToCart(product) {
      // Logic to add the product to the cart
      console.log('Added to cart:', product);
    }
  }
};
</script>