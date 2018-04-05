**Get user**
----

* **URL**

  /api/v2/user/get

* **Method:**

  `GET`
  
*  **Body**

   * **Required:**
    `{
       "userName": "username",
       "passWord": "password"
    }`

* **Success Response:**

  * **Code:** 200 <br/>
    **Content:** `{"userName": "username","token": "token"}`
 
* **Error Response:**

  * **Code:** 226 IM_USED <br/>
    **Content:** `none`