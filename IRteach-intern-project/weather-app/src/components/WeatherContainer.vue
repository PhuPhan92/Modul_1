<template>
  <div id="mainW" :class="typeof weather != 'undefined' && weather.temp_c > 16 ? 'warm' : ''">
    <main>
      <div class="search-box">
        <input type="text" class="search-bar" placeholder="Search..." v-model="query" @keypress="fetchWeather"/>
      </div>

      <div class="weather-wrap" v-if="check">
        <div class="location-box">
          <div class="location">{{ location.name }}, {{ location.country }},{{ location.tz_id }} </div>
          <div class="date">{{ dateBuilder() }}</div>
        </div>

        <div class="weather-box">
          <div class="temp">{{ Math.round(weather.temp_c) }}°c - {{ Math.round(weather.temp_f) }}°f</div>
          <div class="weather">{{ weather.condition.text }}</div>
          <img :src="weather.condition.icon">
        </div>
      </div>
    </main>
    <div>

    </div>
  </div>
</template>
<script>
import { ref } from 'vue'
export default {
  name: 'WeatherApp',
  setup() {
    const api_key = ref('ed7f4635de504a83be233222231108');
    const url_base = ref("http://api.weatherapi.com/v1/");
    let query = ref("");
    let weather = ref({});
    let location = ref({});
    let check = ref(false);
    function fetchWeather(e) {
      if (e.key == "Enter") {
        fetch(`${url_base.value}forecast.json?key=${api_key.value}&q=${query.value}&days=3&aqi=no&alerts=no`)
          .then(res => {
            res.json().then((data) => {
              console.log(data);
              check.value = true;
              weather.value = data.current;
              location.value = data.location
            })
          })
      }
    }
    function dateBuilder() {
      let d = new Date();
      let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
      let days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
      let day = days[d.getDay()];
      let date = d.getDate();
      let month = months[d.getMonth()];
      let year = d.getFullYear();
      return `${day} ${date} ${month} ${year}`;
    }
    return {
      fetchWeather,
      dateBuilder,
      query,
      weather,
      location,
      check
    };
  },
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'montserrat', sans-serif;
}

main {
  min-height: 100vh;
  padding: 25px;

  background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0.25), rgba(0, 0, 0, 0.75));
}

.search-box {
  width: 100%;
  margin-bottom: 30px;
}

.search-box .search-bar {
  display: block;
  width: 100%;
  padding: 15px;

  color: #313131;
  font-size: 20px;

  appearance: none;
  border: none;
  outline: none;
  background: none;

  box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.25);
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 0px 16px 0px 16px;
  transition: 0.4s;
}

.search-box .search-bar:focus {
  box-shadow: 0px 0px 16px rgba(0, 0, 0, 0.25);
  background-color: rgba(255, 255, 255, 0.75);
  border-radius: 16px 0px 16px 0px;
}

.location-box .location {
  color: #FFF;
  font-size: 32px;
  font-weight: 500;
  text-align: center;
  text-shadow: 1px 3px rgba(0, 0, 0, 0.25);
}

.location-box .date {
  color: #FFF;
  font-size: 20px;
  font-weight: 300;
  font-style: italic;
  text-align: center;
}

.weather-box {
  text-align: center;
}

.weather-box .temp {
  display: inline-block;
  padding: 10px 25px;
  color: #FFF;
  font-size: 48px;
  font-weight: 900;

  text-shadow: 3px 6px rgba(0, 0, 0, 0.25);
  background-color: rgba(255, 255, 255, 0.25);
  border-radius: 16px;
  margin: 30px 0px;

  box-shadow: 3px 6px rgba(0, 0, 0, 0.25);
}

.weather-box .weather {
  color: #FFF;
  font-size: 30px;
  font-weight: 700;
  font-style: italic;
  text-shadow: 3px 6px rgba(0, 0, 0, 0.25);
}
</style>