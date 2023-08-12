
# Elmenus- Android coding challenge

A Simple Food Recipes app 


## API Reference

#### Get all meals categories

```http
  GET /http://www.themealdb.com/api/json/v1/${api_key}/categories.php
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Filter Meals by category

```http
  GET /https://www.themealdb.com/api/json/v1/${api_key}/filter.php?c=${category_name}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `c`      | `string` | **Required**. category name        |
|`api_key`  | `string` | **Required**. Your API key        |

#### Get a meal full details

```http
  GET /https://www.themealdb.com/api/json/v1/${api_key}/lookup.php?i=${category_id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `i`      | `string` | **Required**. category id |
|`api_key`  | `string` | **Required**. Your API key        |


## Tech Stack

**Architecture:** MVI, Clean Architecture, Modularization

**Dependency Injection:** Dagger Hilt

**Threading:** Kotlin Coroutines

**Dependency Injection:** Dagger Hilt

**Navigation:** Jetpack Navigation Component, Deep Links

**Security:** NDK

**Unit Testing** Junit, Mockito




## Demo

https://drive.google.com/file/d/1H6HE8kr_U4xkDPqMZ97s59gstYG-51Me/view?usp=share_link

