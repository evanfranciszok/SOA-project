## endpoints

<summary><code>GET</code> <code><b>/food</b></code> <code>(gets all food items)</code></summary>

<summary><code>GET</code> <code><b>/diet</b></code> <code>(gets all diets)</code></summary>

<summary><code>GET</code> <code><b>/profile</b></code> <code>(gets all profiles)</code></summary>

<summary><code>POST</code> <code><b>/profile/{userId}</b></code> <code>(updates or creates a user profile)</code></summary>

body example (application/json):

```json
{
    "userId": 1,
    "firstName": "Mia",
    "lastName": "Thompson",
    "portionSize": "Medium",
    "Allergies": [],
    "Diet": [
        "Vegan"
    ]
}
```
