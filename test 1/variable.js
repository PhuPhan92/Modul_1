
let arr = new Array(+prompt('nhập vào size'))
for(let i=0; i<arr.length; i++){
  arr[i] = Math.floor(Math.random() * 10);
}
console.log(arr)
