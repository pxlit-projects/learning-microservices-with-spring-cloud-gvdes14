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

        <button id="toevoegenProduct" type="submit" class="btn-send" >Toevoegen Product</button>
      </form>
    </section>
    <section class="add-product">
      <h3>Ophalen LogService</h3>
      <form class="GetLogForm" @submit.prevent="GetLog">        
        <button @click="getLogFromServer()" type="submit" class="btn-send" >Ophalen Log</button>
      </form>
    </section>

    <div class="logs-table">
      <h2>Received Logs</h2>
      <div class="table-container">
        <table>
          <thead>
          <tr>
            <th>Log ID</th>
            <th>Product ID</th>
            <th>Username</th>
            <th>Description</th>
            <th>DateTime</th>
          </tr>
          </thead>
          <tbody>
          <!-- Beperk de weergave tot de laatste 10 logs -->
          <tr v-for="log in receivedLogs" :key="log.id">
            <td>{{ log.id }}</td>
            <td>{{ log.productId }}</td>
            <td>{{ log.username }}</td>
            <td>{{ log.description }}</td>
            <td>{{ log.datetime }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
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
      receivedLogs: [],
      newProduct: {
        name: '',
        description: '',
        price:'',
        label:'',
        rating: '', // Add rating property
        category: '' // Add category property
      },
      /*
      receivedLogs: {
        id: null,
        productId: null,
        username: null,
        description: null,
        datetime: null
      }*/
    };
  },
  methods: {
    addProduct() {
      const newProduct = {
        name: this.newProduct.name,
        description: this.newProduct.description,
        price: this.newProduct.price,
        label: this.newProduct.label,
        rating: this.newProduct.rating,
        category: this.newProduct.category
      };
      axios.post('http://localhost:8090/product/api/product', newProduct)
        .then(response => {
          console.log('Product added:', response.data);
          alert("Artikel toegvoegd");
          this.featuredProducts.push(response.data);
          // Reset het formulier
          this.newProduct = {
            name: '',
            description: '',
            price: null,
            label: '',
            rating: null,
            category: ''
          };
        })
        .catch(error => {
          console.error('Error adding product:', error);
        });
    },
    getLogFromServer() {
      axios.get('http://localhost:8090/log/api/log') // Todo aanpassen naar GW !
        .then(response => {
          console.log('Log fetched:', response.data);
          this.receivedLogs = response.data;

        })
        .catch(error => {
          console.error('Error fetching log:', error);
        });
    }
  }
};
</script>

<style scoped>
.logs-table {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.table-container {
  max-height: 400px; /* Beperk de hoogte van de tabel */
  overflow-y: auto; /* Voeg verticale scroll toe */
  display: block; /* Zorg ervoor dat de tabel scrollt binnen de container */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

th, td {
  padding: 12px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  background-color: #4CAF50; /* Groene achtergrond voor de header */
  color: white; /* Witte tekstkleur */
  position: sticky; /* Maak de header sticky */
  top: 0; /* Zet de sticky header aan de bovenkant van de tabel */
  z-index: 1; /* Zorg ervoor dat de header boven de tabelinhoud komt */
}

tr:nth-child(even) {
  background-color: #f2f2f2; /* Lichtgrijze achtergrond voor even rijen */
}

tr:hover {
  background-color: #ddd; /* Hover-effect voor rijen */
}

td {
  color: #555; /* Donkergrijze tekst voor cellen */
}

button {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>


