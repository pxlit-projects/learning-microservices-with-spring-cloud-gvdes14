<template>
  <div class="payment-confirmation">
    <AppHeader />
    <section class="confirmation">
      <h3>Betaalbevestiging</h3>
      <p>Bedankt, {{ customer.name }}!</p>
      <p>Uw betaling is succesvol verwerkt.</p>

      <div class="return-actions">
        <button @click="returnToHome" class="return-button">Ga naar Home</button>
      </div>
    </section>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';

export default {
  name: 'PaymentConfirmation',
  components: {
    AppHeader,
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
      orderId: this.$route.params.orderId, // Haal het bestelnummer op via de route
      totalAmount: '123.45', // Voorbeeldbedrag, kan dynamisch worden geladen
      paymentMethod: 'Creditcard', // Voorbeeld, kan worden aangepast
      paymentDate: new Intl.DateTimeFormat('nl-NL', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      }).format(new Date()),    };
  },
  methods: {
    removeCookie(name) {
      document.cookie = `${name}=; Max-Age=-99999999;`;
    },
    returnToHome() {
      this.removeCookie('cartId');
      this.$router.push({ name: 'HomePage' });
    },
  },
};
</script>

<style scoped>
.payment-confirmation {
  padding: 20px;
  text-align: center;
}

.confirmation h3 {
  color: #4caf50;
  margin-bottom: 15px;
}

.payment-details {
  margin: 20px auto;
  width: 60%;
  text-align: left;
  font-size: 1.1em;
}

.payment-details ul {
  list-style: none;
  padding: 0;
}

.payment-details li {
  margin-bottom: 10px;
}

.return-actions {
  margin-top: 30px;
}

.return-button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  font-size: 1em;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.return-button:hover {
  background-color: #45a049;
}
</style>
