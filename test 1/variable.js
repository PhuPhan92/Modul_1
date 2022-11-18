var arr = new Array(10);
    for (let i = 0; i<arr.length; i++){
      arr[i]=  Math.floor((Math.random() * 100) + 1);
    }
    let newArr = arr.reverse();
    console.log(arr);
    console.log(newArr);