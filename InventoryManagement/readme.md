## endpoints

<summary><code>GET</code> <code><b>/{userId}</b></code> <code>(gets all inventory of a Userode></summary>

code

`body example (application/json):`

```json
{
    "userId": "1",
    "food_inventory": [
        {
            "name": "Apple",
            "quantity": 5,
            "expiry_date": "2024-04-10"
        },
        {
            "name": "Chicken Breast",
            "quantity": 2,
            "expiry_date": "2024-04-15"
        },
        {
            "name": "Rice",
            "quantity": 1,
            "expiry_date": "2024-05-01"
        }
    ]
}
```
