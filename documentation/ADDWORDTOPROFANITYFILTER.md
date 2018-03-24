**Add word to profanity filter**
----

* **URL**

  /api/v2/add/banned/word

* **Method:**

  `POST`
  
*  **URL PARAMETER**

   **Required:**
   word: word

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `word`
 
* **Error Response:**

  * **Code:** 418 I_AM_A_TEAPOT <br/>
    **Content:** `word`