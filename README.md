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
3. 

## Daftar credential untuk mengakses aplikasi ##

1. Username dan Password

    * Username : `user001`, password : `teststaff`
    * Username : `user002`, password : `testmanager`

2. Client Apps

    * Client ID : `clientwebbased`, client secret : `abcd`, grant type : `authorization_code,refresh_token`
    * Client ID : `clientspamobile`, client secret : `abcd`, grant type : `implicit`
    * Client ID : `mobileapp`, client secret : `abcd`, grant type : `password,refresh_token`

## Mendapatkan Access Token dengan Grant Type Authorization Code PKCE ##

http://localhost:8080/oauth2/authorize?client_id=spa-client&redirect_uri=http://127.0.0.1:10000/authorized&response_type=code&scope=openid&state=abcd1234&code_challenge_method=S256&code_challenge=ea3rEXbTCcvWGOL2m6J1lT2VWv-sLrnS2i-UeaNENbw

Lakukan HTTP request untuk mendapatkan `access_token`

```
curl -X POST \
  'http://localhost:8080/oauth2/token' \
  --header 'Accept: application/json' \
  --header 'Authorization: Basic c3BhLWNsaWVudDphYmNk' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'client_id=spa-client' \
  --data-urlencode 'redirect_uri=http://127.0.0.1:10000/authorized' \
  --data-urlencode 'grant_type=authorization_code' \
  --data-urlencode 'code=s2QnpXbI7y2TB89HKK_s52Vm9d0r_XYd_OCPr7_sqvq4i0zApwDSK8g44JZaWoZjUiOAowaXHwknBah133cVmF9ng5noqibE45lAFo3ruKYTwxiDr32K81jzB6z3JyRr' \
  --data-urlencode 'code_verifier=EmJ1jTS245HXMu5dDFc36XlEK02FCfT3BAvbvVfBiXSl'
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
    
## Memperbarui Access Token ##

`access_token` ada masa berlakunya, defaultnya adalah `43199` detik atau `12` jam. Bila habis, kita bisa perbarui menggunakan `refresh_token` sebagai berikut:

```
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic bW9iaWxlYXBwOmFiY2Q=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=EDDF43C76EDFCB75EDFED4A778282434' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmVsYWphciJdLCJ1c2VyX25hbWUiOiJ1c2VyMDAxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWRtaW4iXSwiYXRpIjoiM2Q2NjM1NGUtNjRjNS00MWUyLWE3OTAtNGFiODIxYmFlYTViIiwiZXhwIjoxNTkxOTM0NzIyLCJhdXRob3JpdGllcyI6WyJWSUVXX1RSQU5TQUtTSSJdLCJqdGkiOiI3ODFiNmY3ZC1iM2JhLTQyNTItODI5Ny05MTUwOGRlNWM2YmUiLCJjbGllbnRfaWQiOiJtb2JpbGVhcHAifQ.YAX3vRbGdiHM0HcG9itISGJ1XxmEmzUJsYh5-BIuBfVoYTu28F0f6JN3qGvfmPr2hZzAjhG8wQthwgtwmQXbpLTeWNIVW_v9EAme3feUy83h1Kd8kChg-837H5VfdSHOUulG6QuK_Yp9kD_6UjIASvaHvJsQXtnJy-gcBqofZpth67KGmWihOaHiiYdWoFgEN495tK2FrnERMz0JGDT79lzZUlK2gWcN9_-rcS8wBKPK2zpPm0F25k9mgEtEXznsjwRv12CCdMakNEvOYoUjkgrjq0A7FSWG7JASb2GZjggDRpXTSYd0tzWrGGvm67o-N78myQhKu7VXY5qeZup3fA'
```

Hasilnya sebagai berikut

```
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmVsYWphciJdLCJ1c2VyX25hbWUiOiJ1c2VyMDAxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWRtaW4iXSwiZXhwIjoxNTg5Mzg5MjY5LCJhdXRob3JpdGllcyI6WyJWSUVXX1RSQU5TQUtTSSJdLCJqdGkiOiJmZDdiNjJhMy01ZDU1LTQ4YzctOTYyMi1mYzQzNThjYjhhN2EiLCJjbGllbnRfaWQiOiJtb2JpbGVhcHAifQ.AIUhQAgSB_aPk6a16S9GpqK599QjSsbq9H0u_9sUKpcU8OOxqfm9ftv1kjy8Z6YmzaoJbgQtWQZ9p9EYU4Z8Q2yrJIPY7DG3eKw3ERDt4se_PRznf8Flr-8thW4GW_9swiFToTTgQJADlCb1Lnj1yTS6STpLrJqWnSnVSik9nmV8TZ6BN-nvikhIB0Ll0Cka50UW-69v4d0CZgJpOyVUwOKwOuhmm8iS43vG8kk50pjg-lTdI5JcHy3tv6u0AhT66Wyn4XXgTMA2iHfqPq8Ub3oWn36mUQHqWxfwLkuKK3xqKQTTF9VnwMJLxZs2-utn1u4xlOcQ71OORSafqj2xJQ",
  "token_type": "bearer",
  "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYmVsYWphciJdLCJ1c2VyX25hbWUiOiJ1c2VyMDAxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIiwiYWRtaW4iXSwiYXRpIjoiZmQ3YjYyYTMtNWQ1NS00OGM3LTk2MjItZmM0MzU4Y2I4YTdhIiwiZXhwIjoxNTkxOTM4MDU1LCJhdXRob3JpdGllcyI6WyJWSUVXX1RSQU5TQUtTSSJdLCJqdGkiOiJjNWI3MTg0Mi1jNjkzLTRiMWQtYjY4ZC01MmZiMzczYzZjYWEiLCJjbGllbnRfaWQiOiJtb2JpbGVhcHAifQ.KpvSL3dqM_s30ZDcrbsGw7bBZsqY85cXMubB76ACrS-jxn52yahDgVULu0xP52vPrIR-x3vNFs8Mt7uBhdQnfd40JTOQth-hCkPrC6bvgApt1w9GODuOWlExRJThWjE4kjLWnFV7SSkJGzZVKcnPHujoJV45upOL39K7bFZaFAV9CIs3v7GDYPGMoIRTssukZqtUVBPdcIWjjW3ZM6wnwsibp28eD-0ARzFBvlbG0tWBx0rmFE9pBUU2QbiATaFdh_Eedztv2-ih27QDj-3g-OOKqwM-U91lz--hcBMw94Z3SWvTmxEqqujQCpN5cVCCFNawQcQDS4n4s6-zIqc5jA",
  "expires_in": 43199,
  "scope": "read write admin",
  "jti": "fd7b62a3-5d55-48c7-9622-fc4358cb8a7a"
}
```