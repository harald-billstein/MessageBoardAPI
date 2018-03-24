**Get latest messages**
----

* **URL**

  /api/v2/latest/messages

* **Method:**

  `GET`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `[
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

  * **Code:** 404 NOT_FOUND <br />

    
