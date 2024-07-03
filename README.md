# MyProducts API

## Overview 📖

MyProducts API is a RESTful web service for managing an online store. It provides endpoints for managing products, supplies, and sales. This API supports filtering and sorting of products, and ensures that business rules are adhered to when creating, updating, and deleting entities.

## Features ✨

- **Product Management** 🛒
  - CRUD operations for products.
  - Filtering by product name or part of the name.
  - Filtering by price (greater than or less than a specified amount).
  - Filtering by product availability.
  - Sorting by product name or price.
  - Limiting the number of returned products.

- **Supply Management** 📦
  - CRUD operations for supplies.
  - Ensures that the supplied quantity is greater than 0.
  - Validates that the product in the supply exists.

- **Sale Management** 🛍️
  - CRUD operations for sales.
  - Ensures that the sold quantity is greater than 0.
  - Validates that the product in the sale exists.
  - Ensures that the sale does not result in negative stock.
  - Includes the purchase cost in the sale document.

## Endpoints 🔗

### Product Endpoints 🛒

- **Get All Products**

  ```http
  GET /products

**Parameters:**

- `name`: Filter products by name.
- `priceGreaterThan`: Filter products with price greater than specified value.
- `priceLessThan`: Filter products with price less than specified value.
- `inStock`: Filter products by availability.
- `sortBy`: Sort products by specified field (name, price).
- `limit`: Limit the number of returned products.

### Product Endpoints

**Get Product By ID**
```http
GET /products/{id}
```

**Create Product**
```http
POST /products
```

**Get Product By ID**
```http
GET /products/{id}
```

**Update Product**
```http
PUT /products/{id}
```

**Delete Product**
```http
DELETE /products/{id}
```

### Supply Endpoints 📦

**Get All Supplies**
```http
GET /supplies
```

**Get Supply By ID**
```http
GET /supplies/{id}
```

**Create Supply**
```http
POST /supplies
```

**Update Supply**
```http
PUT /supplies/{id}
```

**Delete Supply**
```http
DELETE /supplies/{id}
```
### Sale Endpoints 🛍️

**Get All Sales**
```http
GET /sales
```

**Get Sale By ID**
```http
GET /sales/{id}
```

**Create Sale**
```http
POST /sales
```

**Update Sale**
```http
PUT /sales/{id}
```

**Delete Sale**
```http
DELETE /sales/{id}
```

### Validation Rules ✅

#### Product
- Name must be non-null and unique.
- Price must be a positive value.
- Stock must be a non-negative integer.

#### Supply
- Document name is limited to 255 characters.
- Quantity must be greater than 0.
- Product must exist.

#### Sale
- Document name is limited to 255 characters.
- Quantity must be greater than 0.
- Product must exist.
- Sale must not result in negative stock.
- Includes the cost of purchase.

### Running Tests 🧪
JUnit tests are provided for all endpoints, including validation and business rule checks. To run the tests, use the following command:

```bash
mvn test
```

### Getting Started 🚀

#### Prerequisites
- Java 17 or higher
- Maven 3.6.3 or higher
- Docker (for containerized deployment)

#### Building the Project 🏗️
```bash
mvn clean install
```

#### Running the Application ▶️
```bash
mvn spring-boot:run
```
### Deploying with Docker 🐳

#### Build the Docker image 🏗️
```bash
docker build -t myproducts-api .
```

#### Run the Docker container ▶️
```bash
docker run -p 8080:8080 myproducts-api
```
