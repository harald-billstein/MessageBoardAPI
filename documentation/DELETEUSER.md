**Delete user**
----

* **URL**

  /api/v2/users/user/remove

* **Method:**

  `DELETE`
  
*  **Body**

   * **Required:**
    `{
       "userName": "username",
       "passWord": "password"
    }`

* **Success Response:**

  * **Code:** 200 <br/>
    **Content:** `true`
 
* **Error Response:**

  * **Code:** 404 NOT_FOUND <br/>
    **Content:** `false`