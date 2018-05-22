**Get user**
----

* **URL**

  /api/v2/users/user/retrieve

* **Method:**

  `GET`
  
*  **Param**

   * **Required:**
    <br/>
       "userName": "username"
    <br/>
       "passWord": "password"
    

* **Success Response:**

  * **Code:** 200 <br/>
    **Content:** <br/>`{"userName": "username","token": "token"}`
 
* **Error Response:**

  * **Code:** 226 IM_USED <br/>
    **Content:** `none`
