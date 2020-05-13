# Belajar MultiAuth dengan Spring Security OAuth #

1. Username dan Password

    * Username : `user001`, password : `teststaff`
    * Username : `user002`, password : `testmanager`

2. Client Apps

    * Client ID : `clientwebbased`, client secret : `abcd`, grant type : `authorization_code,refresh_token`
    * Client ID : `clientspamobile`, client secret : `abcd`, grant type : `implicit`
    * Client ID : `mobileapp`, client secret : `abcd`, grant type : `password,refresh_token`

3. Login dengan Resource Owner Password

    ```
    curl --location --request POST 'http://localhost:8080/oauth/token' \
    --header 'Authorization: Basic bW9iaWxlYXBwOmFiY2Q=' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=password' \
    --data-urlencode 'username=user001' \
    --data-urlencode 'password=teststaff'
    ```