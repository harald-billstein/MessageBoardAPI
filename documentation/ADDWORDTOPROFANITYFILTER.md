**Add word to profanity filter**
----

* **URL**

  /api/v2/admin/censure/create/word

* **Method:**

  `PUT`
  
*  **BODY**

   **Required:**
  `{
	    "userName": "username",
	    "token": "token",
	    "word": "word"
  }`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{
                      "success": true,
                      "message": "Word added to list"
                  }`
 
* **Error Response:**

  * **Code:** 418 I_AM_A_TEAPOT <br/>
    **Content:** `{
                      "success": false,
                      "message": "Something was not meant for the list"
                  }`