var products = [
    "Sony Xperia", "Nokia 6", "Xioami Reami Note 4",
    "Apple iPhone 6S", "Xioami Mi 5s Plus", "Apple iPhone 8 Plus",
    "Fujitsu F-04E", "Oppo A71"
]


function displayProduct() {
    let showProduct = "";
    for (let i = 0; i < products.length; i++) {
        showProduct += `
            <tr id='tr_${i}'>
                <td class="text-center">${i + 1}</td>
                <td id="td_${i}">${products[i]}</td>
                <td>
                    <button class="btn btn-success" onclick="editProduct(${i})">Edit</button>
                    <button class="btn btn-warning d-none" onclick="save(${i})">Save</button>
                    <button class="btn btn-danger d-none" onclick='cancel(${i})'>Cancel</button>
                    <button class="btn btn-success" onclick="removeProduct(${i})">Delete</button>
                </td>
            </tr>
        `;
    }

    document.querySelector('.table>tbody').innerHTML = showProduct;
    document.querySelector('.thead-dark th:nth-child(3)').innerHTML = `${products.length} products`;
}

function addProduct() {
    let productName = document.querySelector('#productName').value;
    if (productName == null || productName.trim() == "") {
        alert("Product name is required!");
        return;
    }
    products.push(productName);
    displayProduct();
    clearForm();
}

function clearForm() {
    document.querySelector('#productName').value = "";
}


function removeProduct(index) {
    let confirmed = window.confirm("Are you sure to remove this product?");
    if (confirmed) {
        products.splice(index, 1);
        displayProduct();
    }
}

function editProduct(index){
    document.querySelector(`#tr_${index} .btn-success`).classList.add('d-none');
    document.querySelector(`#tr_${index} .btn-warning`).classList.remove('d-none');
    document.querySelector(`#tr_${index} .btn-danger`).classList.remove('d-none');

    document.querySelector(`#td_${index}`).innerHTML = `<input class="edit-form-control" value='${products[index]}'/>`;
}
function cancel(index){
    document.querySelector(`#tr_${index} .btn-success`).classList.remove('d-none');
    document.querySelector(`#tr_${index} .btn-warning`).classList.add('d-none');
    document.querySelector(`#tr_${index} .btn-danger`).classList.add('d-none');
    document.querySelector(`#td_${index}`).innerHTML = products[index];
}

function save(index){
    let newProductName = document.querySelector(`#td_${index} .edit-form-control`).value;
    products[index] = newProductName;

    cancel(index);
}

displayProduct();