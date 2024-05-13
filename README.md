##Task for LAT

1. Create a new product
	 "/api/products/create" POST
{
  "name": "NazwaProduktu",
  "description": "OpisProduktu",
  "regularPrice": 99.99,
  "currency": "USD"
}
----------------------------------

2. Get all products 
	"/api/products/all"
----------------------------------

3. Update product data
	 "/api/products/id/{productId}"
 {
  "name": "NazwaProduktu",
  "description": "Opis12321Produktu",
  "regularPrice": 99.99,
  "currency": "USD"
}
----------------------------------

4. Create a new promo code. 
	"/api/promo-codes/create" POST 
{
  "code": "KOD123",
  "expirationDate": "2024-12-31",
  "discountAmount": 10.50,
  "currency": "USD",
  "maxUsages": 100,
}
----------------------------------

5. Get all promo codes.
	 "/api/promo-codes/all"
----------------------------------

6. Get one promo code's details by providing the promo code. The detail should also contain the number of usages.
	  "/api/promo-codes/code/{code}"
    "/api/promo-codes/id/{id}"
----------------------------------

8. Get the discount price by providing a product and a promo code. 
	"/api/sales/calculate/{product}/{code}"
----------------------------------

9. Simulate purchase 
	"/api/sales/buy/{product}/{code}"
----------------------------------

10. [Optional] A sales report: number of purchases and total value by currency (see below) 
	"/api/sales/export"
