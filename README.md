# Belajar MultiAuth dengan Spring Security OAuth #

Aplikasi jaman now biasanya ingin juga bisa diakses melalui perangkat ponsel. Untuk itu, kita harus menyediakan API endpoint yang mengeluarkan data dalam bentuk JSON. Tentunya API endpoint ini juga ingin kita amankan dengan authentication (agar bisa diketahui user mana yang mengaksesnya) dan authorization (agar bisa dicek ijin akses user tersebut).

Dengan demikian, kita harus menyediakan beberapa proteksi security, yaitu:

1. Login dengan form seperti aplikasi web pada umumnya. Ini digunakan oleh user yang menggunakan aplikasi melalui antarmuka web.
2. Login dengan OAuth 2 menggunakan grant type Resource Owner Password. Grant type ini digunakan karena aplikasi mobile adalah bikinan kita sendiri (first party).
3. Proteksi url-url tampilan web dengan form-based authentication. Yang belum login disuruh login dulu.
4. Proteksi url API yang diakses aplikasi mobile. Yang mau akses harus bawa `access_token`, didapatkan melalui proses login ke OAuth 2 Authorization Server.

## Daftar URL yang bisa diakses di aplikasi ##

1. Daftar transaksi user (web-based) : [http://localhost:8080/transaksi/list](http://localhost:8080/transaksi/list). Untuk mengakses halaman ini, harus login dulu. 
2. API data transaksi user (REST) : [http://localhost:8080/api/transaksi/](http://localhost:8080/api/transaksi/). Untuk mengakses halaman ini, harus menggunakan Bearer Token.
3. API untuk login OAuth 2 dengan Resource Owner Password : `http://localhost:8080/oauth/token`. Cara loginnya ada di bawah.


## Daftar credential untuk mengakses aplikasi ##

1. Username dan Password

    * Username : `user001`, password : `teststaff`
    * Username : `user002`, password : `testmanager`

2. Client Apps

    * Client ID : `clientwebbased`, client secret : `abcd`, grant type : `authorization_code,refresh_token`
    * Client ID : `clientspamobile`, client secret : `abcd`, grant type : `implicit`
    * Client ID : `mobileapp`, client secret : `abcd`, grant type : `password,refresh_token`

## Login dengan Resource Owner Password ##

Lakukan HTTP request untuk mendapatkan `access_token`

    ```
    curl --location --request POST 'http://localhost:8080/oauth/token' \
    --header 'Authorization: Basic bW9iaWxlYXBwOmFiY2Q=' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=password' \
    --data-urlencode 'username=user001' \
    --data-urlencode 'password=teststaff'
    ```
   
Login yang sukses akan menghasilkan response seperti ini
    
    ```json
    {
      "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmVsYWphciJdLCJ1c2VyX25hbWUiOiJ1c2VyMDAxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWRtaW4iXSwiZXhwIjoxNTg5Mzg1OTIyLCJhdXRob3JpdGllcyI6WyJWSUVXX1RSQU5TQUtTSSJdLCJqdGkiOiIzZDY2MzU0ZS02NGM1LTQxZTItYTc5MC00YWI4MjFiYWVhNWIiLCJjbGllbnRfaWQiOiJtb2JpbGVhcHAifQ.WWVOD9ivEf1nRZgSZ3uIWQBrTGfgsufgG28ZOLWQwW2h8wV_4z3JdJp87lsAkSoNYXD1xko1dwjSe0tz1Uw5ex9u12o4uwECn0ofkzZGi3q7jDd6pD3ypHBCyr1J-kbNF4EWIUVvefz2LElN96AbMtuHUChJmEFAJND2rljMDXZTm1cz-nnXcVkXumqXjDuRWYOFTZgQ4jhE-DrxU2cvIq87CqBVO12zGeGsOOHXi6j9OKhVUVCbxRymU1e8WLFPxEj_f7Fn_EynEksrask2GTw7GghFL5WGalHI_3lGOp9PgA7dJnd1pKWFxZspQj78EqwO8cZTAETZLeBtcL5mFg",
      "token_type": "bearer",
      "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmVsYWphciJdLCJ1c2VyX25hbWUiOiJ1c2VyMDAxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWRtaW4iXSwiYXRpIjoiM2Q2NjM1NGUtNjRjNS00MWUyLWE3OTAtNGFiODIxYmFlYTViIiwiZXhwIjoxNTkxOTM0NzIyLCJhdXRob3JpdGllcyI6WyJWSUVXX1RSQU5TQUtTSSJdLCJqdGkiOiI3ODFiNmY3ZC1iM2JhLTQyNTItODI5Ny05MTUwOGRlNWM2YmUiLCJjbGllbnRfaWQiOiJtb2JpbGVhcHAifQ.YAX3vRbGdiHM0HcG9itISGJ1XxmEmzUJsYh5-BIuBfVoYTu28F0f6JN3qGvfmPr2hZzAjhG8wQthwgtwmQXbpLTeWNIVW_v9EAme3feUy83h1Kd8kChg-837H5VfdSHOUulG6QuK_Yp9kD_6UjIASvaHvJsQXtnJy-gcBqofZpth67KGmWihOaHiiYdWoFgEN495tK2FrnERMz0JGDT79lzZUlK2gWcN9_-rcS8wBKPK2zpPm0F25k9mgEtEXznsjwRv12CCdMakNEvOYoUjkgrjq0A7FSWG7JASb2GZjggDRpXTSYd0tzWrGGvm67o-N78myQhKu7VXY5qeZup3fA",
      "expires_in": 43199,
      "scope": "read write admin",
      "jti": "3d66354e-64c5-41e2-a790-4ab821baea5b"
    }
    ```

## Mengakses API Daftar Transaksi ##

Data transaksi `user001` dapat diakses dengan HTTP request seperti ini 

    ```
    curl --location --request GET 'localhost:8080/api/transaksi/' \
    --header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmVsYWphciJdLCJ1c2VyX25hbWUiOiJ1c2VyMDAxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWRtaW4iXSwiZXhwIjoxNTg5Mzg1OTIyLCJhdXRob3JpdGllcyI6WyJWSUVXX1RSQU5TQUtTSSJdLCJqdGkiOiIzZDY2MzU0ZS02NGM1LTQxZTItYTc5MC00YWI4MjFiYWVhNWIiLCJjbGllbnRfaWQiOiJtb2JpbGVhcHAifQ.WWVOD9ivEf1nRZgSZ3uIWQBrTGfgsufgG28ZOLWQwW2h8wV_4z3JdJp87lsAkSoNYXD1xko1dwjSe0tz1Uw5ex9u12o4uwECn0ofkzZGi3q7jDd6pD3ypHBCyr1J-kbNF4EWIUVvefz2LElN96AbMtuHUChJmEFAJND2rljMDXZTm1cz-nnXcVkXumqXjDuRWYOFTZgQ4jhE-DrxU2cvIq87CqBVO12zGeGsOOHXi6j9OKhVUVCbxRymU1e8WLFPxEj_f7Fn_EynEksrask2GTw7GghFL5WGalHI_3lGOp9PgA7dJnd1pKWFxZspQj78EqwO8cZTAETZLeBtcL5mFg' \
    --header 'Cookie: JSESSIONID=EDDF43C76EDFCB75EDFED4A778282434'
    ```

Hasilnya seperti ini

    ```json
    [
      {
        "id": "t101",
        "pengguna": {
          "id": "u001",
          "username": "user001",
          "nama": "User 001",
          "email": "user001@yopmail.com"
        },
        "waktuTransaksi": "2020-01-01T08:00:00",
        "keterangan": "Transaksi 1 User 001",
        "nilai": 100001.00
      },
      {
        "id": "t102",
        "pengguna": {
          "id": "u001",
          "username": "user001",
          "nama": "User 001",
          "email": "user001@yopmail.com"
        },
        "waktuTransaksi": "2020-01-02T08:00:00",
        "keterangan": "Transaksi 2 User 001",
        "nilai": 100002.00
      },
      {
        "id": "t103",
        "pengguna": {
          "id": "u001",
          "username": "user001",
          "nama": "User 001",
          "email": "user001@yopmail.com"
        },
        "waktuTransaksi": "2020-01-03T08:00:00",
        "keterangan": "Transaksi 3 User 001",
        "nilai": 100003.00
      }
    ]
    ```