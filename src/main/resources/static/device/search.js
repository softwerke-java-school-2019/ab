const api = axios.create({
  baseURL: '/api/device',
  timeout: 5000
})
//TODO: scope
//TODO: https -> //
//TODO: vue.js???
//TODO: sort options
//TODO: alert -> text message
//TODO: 
var deviceColors = []
var baseQuery = {}
var curPage = 1

function getHexColor(number){
    return "#"+ ('000000' + ((number)>>>0).toString(16)).slice(-6)
}

function populateList(el, data) {
	deleteChildren(el)
	data.forEach((device)=>{
		let itemHTML = Mustache.render(entityTemplate, Object.assign(device, {
			'colorRgb': getHexColor(device.colorRgb)
		}))
		el.appendChild(htmlToElement(itemHTML))
	})
}

function sendForm() {
    var deviceTypeSelect = document.getElementById('deviceType')
	var colorSelect = document.getElementById('colorName')
    var responseDiv = document.getElementById('response')
    toggleLoading(true)
	var options = {
        'deviceType': deviceTypeSelect.options[deviceTypeSelect.selectedIndex].text,
        'modelName': document.getElementById('modelName').value,
        'manufacturer': document.getElementById('manufacturer').value,
        'colorName': colorSelect.options[colorSelect.selectedIndex].text,
        'manufactureDate': document.getElementById('manufactureDate').value,
        'price': document.getElementById('price').value
    }
	baseQuery = {
		pageItems: PAGE_ITEMS
	}
	curPage = 1
	for(key in options) {
		if(options[key]) {
			baseQuery[key] = options[key]
		}
	}
    api.get('', {
		params: baseQuery
	}).then(response => {
		showListModal(el => {
			populateList(el, response.data)
		})
		modalListOnPage(curPage)
    }).catch(handleError).finally(()=>{
		toggleLoading(false)
	})
}

window.onload = function() {
	retreiveEntityTemplate('device')
	var responseDiv = document.getElementById('response')
	var deviceTypeLoader = document.getElementById('deviceTypeLoading')
	var colorLoader = document.getElementById('colorLoading')
	api.get('/type').then(response => {
        var types = response.data
        var deviceTypeSelect = document.getElementById('deviceType')
		types.forEach(el=>{
			let option = document.createElement('option')
			appendText(option, el.name)
			deviceTypeSelect.appendChild(option);
		})
    }).catch(handleError).finally(()=>{
		deviceTypeLoader.classList.remove('loading')
	})
	api.get('/color').then(response => {
        var colors = response.data
		deviceColors = colors
        var colorSelect = document.getElementById('colorName')
		colors.forEach(el=>{
			let option = document.createElement('option')
			appendText(option, el.name)
			colorName.appendChild(option);
		})
    }).catch(handleError).finally(()=>{
		colorLoader.classList.remove('loading')
	})
}