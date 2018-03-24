**Remove word from profanity filter**
----

* **URL**

  /api/v2/remove/banned/word

* **Method:**

  `DELETE`
  
*  **URL PARAMETER**

   **Required:**
   word: word

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `word`
 
* **Error Response:**

  * **Code:** 418 I_AM_A_TEAPOT <br/>
    **Content:** `word`