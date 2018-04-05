**Remove word from profanity filter**
----

* **URL**

  /api/v2/remove/banned/word

* **Method:**

  `DELETE`
  
*  **BODY**

   **Required:**
   {
   	"userName": "username",
   	"token": "token",
   	"word": "word"
   }

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{
                      "success": true,
                      "message": "Word removed to list"
                  }`
 
* **Error Response:**

  * **Code:** 418 I_AM_A_TEAPOT <br/>
    **Content:** `{
                      "success": false,
                      "message": "Something was meant for this list"
                  }`