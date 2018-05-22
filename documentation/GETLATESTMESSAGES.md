**Get latest messages**
----

* **URL**

  /api/v2/messages/retrieve

* **Method:**

  `GET`
  
*  **URL PARAM**
  
   * **Required:**<br>
   userName: username
   token: token

* **Success Response:**

  * **Code:** 200 <br/>
    **Content:** <br/>`[
                      {
                          "user": "User1",
                          "message": "Message1"
                      },
                      {
                          "user": "User2",
                          "message": "Message1"
                      },
                      {
                          "user": "User1",
                          "message": "Message2"
                      },
                      {
                          "user": "User2",
                          "message": "Message2"
                      },
                      {
                          "user": "User1",
                          "message": "Message3"
                      }
                  ]`<br />
                  ...
 
* **Error Response:**

  * **Code:** 404 NOT_FOUND <br/>

    
