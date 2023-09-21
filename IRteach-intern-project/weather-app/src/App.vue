<template>
  <WeatherApp></WeatherApp>
</template>

<script>
// import WeatherApp from './components/WeatherContainer.vue';
import WeatherApp from './components/WeatherContainerVueX.vue';
export default {
  name: 'App',
  components: {
    WeatherApp
  }
}
</script>

<style>


#mainW {
  background-image: url("./assets/img/cold-bg.jpg");
  background-size: cover;
  background-position: bottom;
  transition: 0.4s;
  max-width: 600px;
}

#mainW.warm {
  background-image: url('./assets/img/warm-bg.jpg');
}
</style>
