<template>
  <div class="order-confirmation">
    <AppHeader />
    <section class="cart">
      <h3>Bevestiging van uw bestelling</h3>
      <p>Bedankt voor uw bestelling, {{ customer.name }}!</p>
      <p>Uw bestelnummer is: <strong>#{{ order.id }}</strong></p>

      <div>
        <table class="cart-table">
          <thead>
          <tr>
            <th>Naam</th>
            <th>Beschrijving</th>
            <th>Prijs / stuk</th>
            <th>Aantal</th>
            <th>Totaal</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="product in cart.products" :key="product.id">
            <td>{{ product.name }}</td>
            <td>{{ product.description }}</td>
            <td>{{ product.price }} EUR</td>
            <td>{{ product.quantity }}</td>
            <td>{{ (product.price * product.quantity).toFixed(2) }} EUR</td>
          </tr>
          </tbody>
        </table>

        <div class="order-summary" style="width: 90%; text-align: right">
          <h4>Totaalbedrag: {{ totalPrice }} EUR</h4>
        </div>

        <!-- Betaalknop -->
        <div class="payment-actions" style="width: 90%; text-align: right; margin-top: 20px;">
          <button class="payment-button" @click="makePayment">Betaal</button>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';
import Cookies from "js-cookie";
import axios from "axios";

export default {
  name: 'OrderConfirmation',
  components: {
    AppHeader
  },
  data() {
    return {
      customer: {
        name: 'Gregory Vervoort',
        email: 'gregory.vervoort@student.pxl.be',
        phone: '+32 476 12 23 34',
        address: 'Muntstraat 1',
        zipcode: '3000',
        country: 'België'
      },
      cart: {
        id: null,
        name: null,
        description: null,
        price: 0,
        category: null,
        label: null,
        rating: 0
      },
      order: {
        id: this.generateOrderId(), // Voorbeeld bestelnummer
        paymentMethod: 'Creditcard' // Voorbeeld betaalmethode
      }
    };
  },
  computed: {

    totalPrice() {
      if (!this.cart.products || this.cart.products.length === 0) {
        return "0.00";
      }
      return this.cart.products
          .reduce((total, product) => {
            const price = parseFloat(product.price) || 0; // Zorgt ervoor dat een ongeldig 'price'-veld niet leidt tot een fout
            return total + price;
          }, 0)
          .toFixed(2); // Formatteer naar twee decimalen
    }
  },
  created() {
    this.fetchCart();
  },
  methods: {
    fetchCart() {
      let cartId = Cookies.get('cartId');
      //console.log("Ophalen van cartId : ", cartId)
      axios.get(`http://localhost:8090/shop/api/shop/${cartId}`)
          .then(async response => {
            //console.log('Full response:', response.data); // Volledige Axios-response
            const cartData = response.data;
            console.log('Data in cart :', cartData);

            this.cart = {
              ...cartData,
              products: cartData.productIds || []
            };

            const products = [];
            for (const productId of this.cart.products) {
              try {
                const product = await this.fetchProductDetails(productId);
                console.log("product :" , product);
                product.quantity = 1;
                products.push(product);
              } catch (error) {
                console.error(`Error fetching product with ID ${productId}:`, error);
              }
            }
            console.log(products);
            this.cart.products = products;
          })
          .catch(error => {
            console.error('Error fetching cart:', error);
          });
    },
    // Ophalen van de producten in de winkelwagen
    fetchProductDetails(productId) {
      return axios.get(`http://localhost:8090/product/api/product/${productId}`)
          .then(response => {
            console.log('Product details fetched:', response.data);
            return response.data; // Zorg ervoor dat de data hier wordt geretourneerd
          })
          .catch(error => {
            console.error('Error fetching product details:', error);
          });
    },
    makePayment() {
      // Simulatie van een betaling
      let cartId = Cookies.get('cartId');
      console.log('Betaling simulatie', cartId);

      axios.post(`http://localhost:8090/shop/api/shop/payment/${cartId}`)
          .then(response => {
            console.log('Payment ok:', response.data);
            this.$router.push({ name: 'PaymentConfirmation', params: { orderId: response.data.id } });
          })
          .catch(error => {
            console.error('Error placing payment:', error);
          });
    },
      generateOrderId() {
        const date = new Date();
        const year = date.getFullYear().toString();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        const randomPart = Math.floor(1000 + Math.random() * 9000); // Willekeurig getal tussen 1000 en 9999
        return `${year}${month}${day}${randomPart}`; // Formaat: YYYYMMDDxxxx
      }
    }
};
</script>
<style scoped>
/* Algemeen voor cart en order-confirmation */
.cart {
  padding: 20px;
}

.cart-table {
  width: 80%;
  margin: 20px auto;
  border-spacing: 0px 30px;
}

.cart-table th {
  padding: 10px;
  background-color: #4caf50;
  font-weight: bold;
  text-decoration: underline;
  color: #fff;
}

.cart-table td {
  padding: 10px;
  border-bottom: 1px solid red;
}

.order-summary {
  text-align: right;
  margin-top: 20px;
  font-size: 1.2em;
  font-weight: bold;
}

/* Specifiek voor order-confirmation */
.order-confirmation h3 {
  color: #4caf50;
  margin-bottom: 10px;
}

.order-confirmation p {
  margin: 5px 0;
}
</style>

