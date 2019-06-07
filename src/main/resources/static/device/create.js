const api = axios.create({
  baseURL: '/api/device',
  timeout: 5000
})

function sendForm() {
    var deviceTypeSelect = document.getElementById('deviceType')
	var colorSelect = document.getElementById('colorName')
    var responseDiv = document.getElementById('response')
    toggleLoading(true)
    api.post('', {
        'deviceType': deviceTypeSelect.options[deviceTypeSelect.selectedIndex].text,
        'modelName': document.getElementById('modelName').value,
        'manufacturer': document.getElementById('manufacturer').value,
        'colorName': colorSelect.options[colorSelect.selectedIndex].text,
        'manufactureDate': document.getElementById('manufactureDate').value,
        'price': document.getElementById('price').value
    }).then(response => {
		showModal("Success", JSON.stringify(response.data))
    }).catch(handleError).finally(()=>{
		toggleLoading(false)
	})
}

window.onload = function() {
	var responseDiv = document.getElementById('response')
	var deviceTypeLoader = document.getElementById('deviceTypeLoading')
	var colorLoader = document.getElementById('colorLoading')
	api.get('/type').then(response => {
        var types = response.data
        var deviceTypeSelect = document.getElementById('deviceType')
		types.forEach(el=>{
			let option = document.createElement('option')
			option.appendChild(document.createTextNode(el.name))
			deviceTypeSelect.appendChild(option);
		})
    }).catch(handleError).finally(()=>{
		deviceTypeLoader.classList.remove('loading')
	})
	api.get('/color').then(response => {
        var colors = response.data
        var colorSelect = document.getElementById('colorName')
		colors.forEach(el=>{
			let option = document.createElement('option')
			option.appendChild(document.createTextNode(el.name))
			colorName.appendChild(option);
		})
    }).catch(handleError).finally(()=>{
		colorLoader.classList.remove('loading')
	})
}