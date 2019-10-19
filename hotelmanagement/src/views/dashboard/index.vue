<template>
  <div class="app-container">
    
  </div>
</template>

<script>

 export default {
    name: 'app',

    mounted() {
      console.log({router: this.$router.options.routes});
    }
  }
</script>
