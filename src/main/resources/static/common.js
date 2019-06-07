const PAGE_ITEMS = 1

var entityTemplate = null;

function showModal(title, text) {
	var modalTitle = document.getElementById('modalTitle')
	var modalText = document.getElementById('modalText')
	var modal = document.getElementById('modal')
	modalTitle.textContent = title+""
	modalText.textContent = text+""
	modal.classList.add('active')
}

function retreiveEntityTemplate(name) {
	const mstApi = axios.create({
	  baseURL: '/'+name,
	  timeout: 5000
	})
	mstApi.get(name+'.mst').then(response => {
        entityTemplate = response.data
    }).catch(handleError).finally(()=>{
		console.log('!!!!!')
		toggleLoading(false)
	})
}

function toggleLoading(on) {
	var button = document.getElementById('submitButton')
	if(on) {
		button.classList.add('loading')
		button.classList.add('disabled')
	} else {
		button.classList.remove('loading')
		button.classList.remove('disabled')
	}
}

function newDiv() {
	let div = document.createElement('div')
	for(let i = 0; i < arguments.length; i++) {
		div.classList.add(arguments[i])
	}
}	

function appendText(el, text) {
	el.appendChild(document.createTextNode(text))
}

function deleteChildren(el) {
	while (el.firstChild) {
		el.removeChild(el.firstChild);
	}
}

function htmlToElement(html) {
    var template = document.createElement('template');
    html = html.trim();
    template.innerHTML = html;
    return template.content.firstChild;
}

function handleError(error) {
	 if (error.response) {
		showModal(error.response.data.error.type, error.response.data.error.message)
	} else {
		showModal('error', error)
	}
}

function closeModal(title, text) {
	var modal = document.getElementById('modal')
	modal.classList.remove('active')
}

function closeListModal(title, text) {
	var modal = document.getElementById('modalList')
	modal.classList.remove('active')
}

function modalListInitLoading() {
	var prev = document.getElementById('modalListPrev')
	var next = document.getElementById('modalListNext')
	prev.classList.add('loading', 'disabled')
	next.classList.add('loading', 'disabled')
}

function modalListOnPage(page, blockNext) {
	var prev = document.getElementById('modalListPrev')
	var next = document.getElementById('modalListNext')
	var title = document.getElementById('modalListTitle')
	prev.classList.remove('loading', 'disabled')
	next.classList.remove('loading', 'disabled')
	if(page == 1) {
		prev.classList.add('disabled')
	}
	if(blockNext) {
		next.classList.add('disabled')
	}
	curPage = page
	title.textContent = "page #"+curPage
}

function listModalChange(cur, next) {
	modalListInitLoading()
	api.get('', {
		params: Object.assign(baseQuery, {
			page: next
		})
	}).then(response => {
		showListModal(el => {
			populateList(el, response.data)
		})
		modalListOnPage(next)
    }).catch((error) => {
		if(error.response.data.error.type == "empty result") {
			modalListOnPage(cur, true)
		} else {
			handleError(error)
			modalListOnPage(cur)
		}
	})
}

function listModalNext() {
	listModalChange(curPage, curPage+1)
}

function listModalPrev() {
	listModalChange(curPage, curPage-1)
}


function showListModal(func) {
	var modal = document.getElementById('modalList')
	var list = document.getElementById('modalListContent')
	func(list)
	if(!modal.classList.contains('active')) {
		modal.classList.add('active')
		var prev = document.getElementById('modalListPrev')
		var next = document.getElementById('modalListNext')
		prev.classList.add('disabled')
		next.classList.remove('disabled')
		prev.classList.remove('loading')
		next.classList.remove('loading')
	}
}