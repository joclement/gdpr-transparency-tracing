import Vue from 'vue'
import Router from 'vue-router'
import Game from "../components/Game";
import Index from "../components/Index";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/game',
      name: 'Game',
      component: Game
    }
  ]
})
