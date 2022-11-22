
let arr = [25,-5,-13,13]
let arrnew = [];
maxMinAvg(arr)
function maxMinAvg(arr) {
  let max = arr[0]
  for (let i = 0; i < arr.length; i++) {
    if (arr[i] > arr[0]) {
      max = arr[i]
    };
  }
  let min = arr[0];
  for (let j = 0; j < arr.length; j++) {
    if (arr[j] < arr[0]) {
      min = arr[j]
    };
  }
  let sum = 0
  for (let k = 0; k < arr.length; k++) {
    sum += arr[k]
  }
  let avg = sum / arr.length;
  arrnew.push(max,min,avg);
  return arrnew;
}
console.log(arrnew)
