<template>
  <div class="order-confirmation">
    <AppHeader />
    <section class="confirmation">
      <h3>Bevestiging van uw bestelling</h3>
      <p>Bedankt voor uw bestelling, {{ customer.name }}!</p>
      <p>Uw bestelnummer is: <strong>#{{ order.id }}</strong></p>

      <div class="order-details">
        <h4>Bestelde Producten:</h4>
        <table class="order-table">
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
          <tr v-for="product in order.products" :key="product.id">
            <td>{{ product.name }}</td>
            <td>{{ product.description }}</td>
            <td>{{ product.price }} EUR</td>
            <td>{{ product.quantity }}</td>
            <td>{{ (product.price * product.quantity).toFixed(2) }} EUR</td>
          </tr>
          </tbody>
        </table>

        <div class="order-summary">
          <h4>Totaalbedrag: {{ totalAmount }} EUR</h4>
          <p>Betaald met: {{ order.paymentMethod }}</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';

export default {
  name: 'OrderConfirmation',
  components: {
    AppHeader
  },
  data() {
    return {
      customer: {
        name: 'Jan Jansen', // Voorbeeld klantnaam
      },
      order: {
        id: '20231234', // Voorbeeld bestelnummer
        products: [
          {
            id: 1,
            name: 'Product A',
            description: 'Een voorbeeld productbeschrijving.',
            price: 25.5,
            quantity: 2
          },
          {
            id: 2,
            name: 'Product B',
            description: 'Nog een voorbeeld productbeschrijving.',
            price: 15.0,
            quantity: 1
          },
          {
            id: 3,
            name: 'Product C',
            description: 'Een derde voorbeeld product.',
            price: 5.75,
            quantity: 4
          }
        ],
        paymentMethod: 'Creditcard' // Voorbeeld betaalmethode
      }
    };
  },
  computed: {
    totalAmount() {
      return this.order.products
          .reduce((total, product) => total + product.price * product.quantity, 0)
          .toFixed(2);
    }
  }
};
</script>

<style scoped>
.order-confirmation {
  padding: 20px;
}

.confirmation h3 {
  color: #4caf50;
  margin-bottom: 10px;
}

.order-details {
  margin-top: 20px;
}

.order-table {
  width: 80%;
  margin: 20px auto;
  border-spacing: 0;
  border-collapse: collapse;
}

.order-table th,
.order-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: left;
}

.order-table th {
  background-color: #4caf50;
  color: #fff;
}

.order-summary {
  text-align: right;
  margin-top: 20px;
  font-size: 1.2em;
  font-weight: bold;
}
</style>
