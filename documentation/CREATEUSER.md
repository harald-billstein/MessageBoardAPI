**Create user**
----

* **URL**

  /api/v2/user/new

* **Method:**

  `POST`
  
*  **Body**

   **Required:**
    {
    	"userName": "username",
      "passWord": "password"
    }

* **Success Response:**

  * **Code:** 200 <br/>
    **Content:** `{"userName": "username","passWord": "password"}`
 
* **Error Response:**

  * **Code:** 226 IM_USED <br/>
    **Content:** `{"userName": "username","passWord": "password"}`
