# ab

HW1?

## usage

```
	GET /api/<entity>/get
    GET /api/<entity>/id/{id}
    POST /api/<entity>/create (Body: JSON)
```
list of entities and their JSON-representation:
* `customer {first_name: String, middle_name: String, last_name: String, birth_date: long}`
* `device {price: String, type: String, color_name: String, color_rgb: int, issuer: String, model: String}`
* `bill {client_id: long, items_list: [BillItem], date: long, time: long}`

BillItem has JSON-form `{device_id: long, quantity: int, price: String}` and is not considered an entity

### /get options
* orderBy : String - not required
  * id
  * price_total (Bill)
  * cliend_id (Bill)
  * date (Bill, Device)
  * first_name (Customer)
  * middle_name (Customer)
  * last_name (Customer)
  * birth_date (Customer)
  * price (Device)
  * type (Device)
  * color_name (Device)
  * color_rgb (Device)
  * issuer (Device)
  * model (Device)
* filterBy : String - not required
  * anything from orderBy (except for id)
  * price_total_equal (Bill)
  * price_total_less (Bill)
  * price_total_more (Bill)
* filterValue : String - required if filterBy is set
* offset : long - not required
* count : long - not required

### request examples

```
	GET /api/bill/get?orderBy=date
    GET /api/customer/get?filterBy=last_name&filterValue=Privet&orderBy=middle_name
	POST /api/bill/create {"client_id":2,"items_list":[{"device_id":1,"quantity":22,"price":34}],"date":2,"time":69}
```
### response examples
```
	[{"price":30000,"type":"computer","color_name":"green","color_rgb":65280,"issuer":"Sony","model":"B","id":0},{"price":9000,"type":"phone","color_name":"red","color_rgb":16711680,"issuer":"Sony","model":"A","id":1},{"price":12000,"type":"phone","color_name":"green","color_rgb":65280,"issuer":"Samsung","model":"C","id":2}]

    [{"first_name":"Borisov","middle_name":"Aleksey","last_name":"Mikhailovich","birth_date":100,"id":0},{"first_name":"Kurilenko","middle_name":"Vlad","last_name":"Privet","birth_date":90,"id":1},{"first_name":"Solyanov","middle_name":"Ivan","last_name":"Privet","birth_date":200,"id":2}]
```