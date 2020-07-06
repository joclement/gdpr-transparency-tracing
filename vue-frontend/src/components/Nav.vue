<template>
    <div class="nav">
      <ul>
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/about">About</router-link></li>
        <li><router-link to="/legal">Legal</router-link></li>
        <li><router-link to="/game">Game</router-link></li>
      </ul>
    </div>
</template>

<script>

    export default {
        name: "Nav"
    }
</script>

<style scoped>
  ul {
    display: inline;
  }
  li {
    display: inline;
  }

</style>
