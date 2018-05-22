**Send message**
----

* **URL**

  /api/v2/messages/message/create

* **Method:**

  `POST`
  
*  **Body**

   **Required:**<br/>
    {
    	"user":"user",
    	"token":"token",
      "message": "message"
    }

* **Success Response:**

  * **Code:** 200 <br/>
    **Content:**`{"user": "user","message": "message"}`
 
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br/>
    **Content:** `{"user": "user","message": "message"}`

  OR

  * **Code:** 406 NOT_ACCEPTABLE <br/>
    **Content:** `{"user": "user","message": "message"}`
    
  OR
    
  * **Code:** 413 PAYLOAD_TOO_LARGE <br/>
    **Content:** `{"user": "user","message": "message"}`
    
