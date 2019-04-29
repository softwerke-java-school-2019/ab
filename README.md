# ab

* [usage](#usage)
    + [entities](#entities)
    + [GET /api/<entity> options](#get--api--entity--options)
    + [examples](#examples)
    + [custom colors](#custom-colors)
    + [error handling](#error-handling)

## usage

```
GET /api/<entity>
GET /api/<entity>/id
POST /api/<entity> (Body: JSON)
```
### entities

`customer: `

| Field Name | Type                            | Description                       |   Example  |
|:----------:|:-------------------------------:|-----------------------------------|:----------:|
|  firstName | string                          |                        |    Петр    |
| middleName | string                          |                        |  Петрович  |
|  lastName  | string                          |                        |   Петров   |
|  birthdate | string, date format: dd.MM.YYYY | birth date             | 25.04.2019 |
|     id     | Integer number                  | unique id generated upon creation |    9000    |

`device: `

|    Field Name   |               Type              |             Description             |                Example                |
|:---------------:|:-------------------------------:|:-----------------------------------:|:-------------------------------------:|
|    deviceType   |              string             | device type (from a predefined set) | Smartphone Laptop Smart Watch Tablet  |
|    modelName    |              string             |                                     |              Galaxy S10+              |
| manufactureDate | string, date format: dd.MM.YYYY | date of issue                       |               25.04.2019              |
|   manufacturer  |              string             |                                     |                Samsung                |
|    colorName    |              string             |                                     |                 черный                |
|     colorRGB    |          Integer number         | integer representation (rgb)        |                   0                   |
|      price      |          Integer number         | integer price                       |                12499000               |
|        id       |          Integer number         | unique id generated upon creation   |                1126970                |

`bill: `

|    Field Name    |                     Type                     |             Description            |       Example       |
|:----------------:|:--------------------------------------------:|:----------------------------------:|:-------------------:|
|    customerId    |                Integer number                |                                    |        9001         |
|    totalPrice    |                Integer number                | integer price represented in penny |       31415926      |
| purchaseDateTime | String, date/time format:dd.MM.YYYY HH:mm:ss | purchase time and date             | 01.01.2019 07:50:22 |
|       items      |               List of BillItem               |                                    |                     |


`BillItem: ` (as part of `bill`)

| Field Name |      Type      |           Description           | Example |
|:----------:|:--------------:|:-------------------------------:|:-------:|
|  deviceId  | Integer number |                                 | 1004709 |
|  quantity  | Integer number | number of devices               |   100   |
|    price   | Integer number | price at the moment of purchase |   490   |

* *you should not specify `id` in POST request, otherwise it will be considered as an invalid field*
* *you should only specify `colorName` instead of `colorRgb` for device in POST request, otherwise it will be considered as an invalid field*
* *api doesn't accept empty or null fields*

### GET /api/<entity> options
* `orderType` : String - fields to order by separated by coma, e.g. `manufactureDate,-id`
* `pageItems` : long - number of elements on a single page
* `page` : long - page number
* anything else is considered a filter option

* *some fields support range: `birthdate`, `price`, `manufactureDate`, `totalPrice`, `purchaseDateTime`, e.g. `priceFrom`, `priceTo` e.t.c.*
* *`-` can be used before value of orderType to reverse the result, e.g. `orderType=-id`*

### examples

```
POST /api/device
body:
{
 	"price": "100",
  	"type": "Smartphone",
  	"manufactureDate": "28.04.2019",
  	"colorName": "red",
  	"manufacturer": "Lenovo",
  	"modelName": "ideapod"
}

GET /api/device?priceFrom=90&priceTo=110&orderType=manufacturer
```

### custom colors
```
GET /api/color (no arguments)
POST /api/color (Body: JSON)
```

`color: `

| Field Name |      Type      |            Description            | Example |
|:----------:|:--------------:|:---------------------------------:|:-------:|
|     id     | Integer number | unique id generated upon creation |    0    |
|    name    |     String     | color name                        |   red   |
|     rgb    | Integer number | integer representation (rgb)      |    0    |


#### predefined colors

| Color name |    rgb(r, g, b)    |
|:----------:|:------------------:|
|    black   | rgb(0, 0, 0)       |
|    gray    | rgb(128, 128, 128) |
|     red    | rgb(255, 0, 0)     |
|   golden   | rgb(255, 215, 0)   |
|    blue    | rgb(0, 0, 255)     |
|   silver   | rgb(192, 192, 192) |
|    white   | rgb(255, 255, 255) |
|    brown   | rgb(150, 75, 0)    |
|   orange   | rgb(255, 165, 0)   |
|    beige   | rgb(245, 245, 220) |
|   yellow   | rgb(255, 255, 0)   |
|    green   | rgb(0, 128, 0)     |
| light blue | rgb(66, 170, 255)  |
|   purple   | rgb(139, 0, 255)   |
|    pink    | rgb(252, 15, 192)  |

*color names should be lowercase*


### error handling

In case of error this JSON will be returned:

|   Field Name  |  Type  |  Description  |     Example     |
|:-------------:|:------:|:-------------:|:---------------:|
|   error.type  | String | error type    |  invalid format  |
| error.message | String | error details | string 'a' has invalid format |