## Internship task

* http://localhost:8080/receipt - endpoint

### Request body (example)
```
{
    "basket":
     [
         {
            "name": "Steak"
         },
         {
            "name": "Apple"
         },
         {
            "name": "Cereals"
         },
         {
            "name": "Cereals"
         },
         {
            "name": "Cereals"
         },
         {
            "name": "Apple"
         }
     ]
}
```

### Response (Receipt)
```
{
    "products": [
        {
            "name": "Cereals",
            "quantity": 3,
            "totalPrice": 24
        },
        {
            "name": "Apple",
            "quantity": 2,
            "totalPrice": 4
        },
        {
            "name": "Steak",
            "quantity": 1,
            "totalPrice": 50
        }
    ],
    "discounts": [
        "GrainsDiscount",
        "TenPercentDiscount"
    ],
    "totalPrice": 59.670
}
```

