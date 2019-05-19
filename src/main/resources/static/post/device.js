const api = axios.create({
  baseURL: '/api/device',
  timeout: 5000
})

function sendForm() {
    var deviceTypeSelect = document.getElementById('deviceType')
    var responseDiv = document.getElementById('response')
    responseDiv.classList.add('loading')
    api.post('', {
        'deviceType': deviceTypeSelect.options[deviceTypeSelect.selectedIndex].text,
        'modelName': document.getElementById('modelName').value,
        'manufacturer': document.getElementById('manufacturer').value,
        'colorName': document.getElementById('colorName').value,
        'manufactureDate': document.getElementById('manufactureDate').value,
        'price': document.getElementById('price').value
    }).then(response => {
        responseDiv.textContent = JSON.stringify(response.data)
        responseDiv.classList.remove('loading')
    }).catch(error => {
        if (error.response) {
            responseDiv.textContent = JSON.stringify(error.response.data)
        } else {
            responseDiv.textContent = error+''
        }
        responseDiv.classList.remove('loading')
    })
}