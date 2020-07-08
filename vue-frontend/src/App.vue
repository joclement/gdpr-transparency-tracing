<template>
  <div id="app">
    <Header></Header>
    <Nav></Nav>
    <router-view/>
  </div>
</template>

<script>
import Nav from "./components/Nav";
import Header from "./components/Header";

export default {
  name: 'App',
  components: {Header, Nav}
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
