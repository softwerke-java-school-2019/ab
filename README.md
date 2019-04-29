# ab

* [usage](#usage)
    + [GET /api/<entity>  options](#get--api--entity---options)
        - [custom colors](#custom-colors)
        - [error handling](#error-handling)


## usage

```
GET /api/<entity>
GET /api/<entity>/id
POST /api/<entity> (Body: JSON)
```
supported entities:

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

### GET /api/<entity>  options
* orderType : String - field to order by
* pageItems : long - number of element on a single page
* page : long - page number
* anything else is considered a filter option

*some fields support range: birthdate, price, manufactureDate, totalPrice, purchaseDateTime, e.g. priceFrom, priceTo e.t.c.*

#### custom colors
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



#### error handling

In case of error this JSON will be returned:

|   Field Name  |  Type  |  Description  |     Example     |
|:-------------:|:------:|:-------------:|:---------------:|
|   error.type  | String | error type    |  invalid format  |
| error.message | String | error details | string 'a' has invalid format |