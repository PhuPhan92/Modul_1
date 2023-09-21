<template>
  <div class="container" >
    <img :src=history.icon alt="icon">
    <p class="history-conten">{{ history.weather }}</p>
    <p class="history-conten">{{ history.location }}</p>
    <p class="history-conten">{{ history.time }}</p>
  </div>
</template>
<script>
export default {
  name:"HistoryItem",
  props: ['history'],
}
</script>
<style scoped>
.history-conten {
  color: #FFF;
  font-size: 20px;
  font-weight: 300;
  font-style: italic;
  text-align: center;
}
.container {
  display: flex;
  justify-content: space-between; /* Các phần tử con được căn chỉnh cách đều nhau trên hàng ngang */
  align-items: center; /* Các phần tử con được căn chỉnh theo chiều dọc trên hàng ngang */
  flex-wrap: wrap; /* Các phần tử con sẽ xuống dòng nếu không vừa trong không gian hiển thị */
}
</style>