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

## Daftar credential untuk mengakses aplikasi ##

1. Username dan Password

    * Username : `user001`, password : `teststaff`
    * Username : `user002`, password : `testmanager`

2. Client Apps

    * Client ID : `spa-client`, client secret : `abcd`, grant type : `authorization_code,refresh_token`

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
  --data-urlencode 'grant_type=authorization_code,refresh_token' \
  --data-urlencode 'code=s2QnpXbI7y2TB89HKK_s52Vm9d0r_XYd_OCPr7_sqvq4i0zApwDSK8g44JZaWoZjUiOAowaXHwknBah133cVmF9ng5noqibE45lAFo3ruKYTwxiDr32K81jzB6z3JyRr' \
  --data-urlencode 'code_verifier=EmJ1jTS245HXMu5dDFc36XlEK02FCfT3BAvbvVfBiXSl'
```

Login yang sukses akan menghasilkan response seperti ini
    
```json
{
  "access_token": "eyJraWQiOiJmOGM3M2VmMS0yODE4LTRhODEtOTIyOC1kM2EwYjYyNzRjMjQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDAyIiwiYXVkIjoic3BhLWNsaWVudCIsIm5iZiI6MTY3ODQ1Mjg4MCwic2NvcGUiOlsib3BlbmlkIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTY3ODQ1MzE4MCwiaWF0IjoxNjc4NDUyODgwfQ.deMwZjdVzVdwyTqyFBCJG6Mwm4WttXAIWvfQYtrq7bKtUzmH0GBuQ3kzGt-_KlhXyxWO9p1YbC0f0nQJsjtOGRcoQj1BNvzoC_pgCYGq-1Vj5DSY1uS6DiEazFJ4TRJS85sfx4BH6fqAgGUo2EgBnbbpyCA9Y-HKUS9bdlj6v90IBpch6_pmWLMKXbS_7Tic8qbihAikiDu1Z5VmposQpC4N6DXBinuKsm7Khk6TOo862BSx97UKd9CdxrOXTZZfgk2GuI09kyVmzkK7M7x4y7_OL_HMDoQA5fTLw6iI6XLzSc04ud9UKaJnkfpXv40BcXmSPxBCCv_AtDR6SupiRQ",
  "refresh_token": "ougihdJcs9mtHOW5XEeYYhZNLiSep7xpQhu8bNuPA50XYpMZuUyIPceQKhxLM4plVddcgeM_n4o0n6ikixAYmgR1ToTzVFSMwB7C59YqcsC0_YS4p9xP8hep-39R3Idj",
  "scope": "openid",
  "id_token": "eyJraWQiOiJmOGM3M2VmMS0yODE4LTRhODEtOTIyOC1kM2EwYjYyNzRjMjQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDAyIiwiYXVkIjoic3BhLWNsaWVudCIsImF6cCI6InNwYS1jbGllbnQiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE2Nzg0NTQ2ODAsImlhdCI6MTY3ODQ1Mjg4MH0.Cyn3_pZKVY-BKMGqcO_lY_GsMYYGK-XkEZE5YLSFF1vZKafQ1IMP8wVio3IE2Y-zsY7RfT0VDCD-QfyuhJb_DTdqWoPUosLCheP29s3GezBKK3J2pDX1mxSTAfdjCACYt9WdoLuHQVJatW6_ZHpe6JOxbpBFuGeOaKRSEYvyhtkYd9-Ld0hag_E7XfgQO3zK7AnDcK4a1I68g60uyN_WoeR8SUZwqmpmiFVw4QXDiAlz7NFO1Rngjn_Et37SzzNwYSMNS3Rq_d_fm2yVL4tfP_3JN85pQrIr6UBptxvJqCtZNYx7PGzQdM0b3JxDRjs0H4hHFFs13tkLIoPlWTuK4A",
  "token_type": "Bearer",
  "expires_in": 299
}
```

## Mengakses API Daftar Transaksi ##

Data transaksi `user001` dapat diakses dengan HTTP request seperti ini 

```
curl -X GET \
  'http://localhost:8080/api/transaksi/' \
  --header 'Accept: application/json' \
  --header 'Authorization: Bearer eyJraWQiOiJmOGM3M2VmMS0yODE4LTRhODEtOTIyOC1kM2EwYjYyNzRjMjQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDAyIiwiYXVkIjoic3BhLWNsaWVudCIsIm5iZiI6MTY3ODQ1Mjg4MCwic2NvcGUiOlsib3BlbmlkIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTY3ODQ1MzE4MCwiaWF0IjoxNjc4NDUyODgwfQ.deMwZjdVzVdwyTqyFBCJG6Mwm4WttXAIWvfQYtrq7bKtUzmH0GBuQ3kzGt-_KlhXyxWO9p1YbC0f0nQJsjtOGRcoQj1BNvzoC_pgCYGq-1Vj5DSY1uS6DiEazFJ4TRJS85sfx4BH6fqAgGUo2EgBnbbpyCA9Y-HKUS9bdlj6v90IBpch6_pmWLMKXbS_7Tic8qbihAikiDu1Z5VmposQpC4N6DXBinuKsm7Khk6TOo862BSx97UKd9CdxrOXTZZfgk2GuI09kyVmzkK7M7x4y7_OL_HMDoQA5fTLw6iI6XLzSc04ud9UKaJnkfpXv40BcXmSPxBCCv_AtDR6SupiRQ'
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
curl -X POST \
  'http://localhost:8080/oauth2/token' \
  --header 'Accept: */*' \
  --header 'User-Agent: Thunder Client (https://www.thunderclient.com)' \
  --header 'Authorization: Basic c3BhLWNsaWVudDphYmNk' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'grant_type=refresh_token' \
  --data-urlencode 'refresh_token=GTBYbXFJIx-fg5HUFRZdXBLwax79fBdBf5hAP_7VWLYQjeXCOq9z9ESU_pAFKrYctZb0KVizjuqZ9-gDGwSK8fynN0FmMse8cD2tyV1v6zXxvyRIcu1Cb8JaT8ZYD6x2'
```

Hasilnya sebagai berikut

```json
{
  "access_token": "eyJraWQiOiI3YTE3MWY2My1lZGZmLTQ0ZTMtYmM3ZS1jOTNkZDk4MjVmMmEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDAxIiwiYXVkIjoic3BhLWNsaWVudCIsIm5iZiI6MTY3ODQ1MzE4NCwic2NvcGUiOlsib3BlbmlkIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTY3ODQ1MzQ4NCwiaWF0IjoxNjc4NDUzMTg0fQ.UK4qvyohhqWfGHUW4crXrosp-bbb3pzo16cLO8vYYeoS44x-PQIMP4kwl_THGzdSI9w5uFB5rpmxYQeLAKi62WEXGbgpRJaqhD77EDNN0CI6eu9H111sZR5ZjYmrXL2Jtjo4omjL9m-y3VFHUJXVLjz_SFwJaLXPd1ivPXb7P6dC0JDlTOXwuGkEIWBiJAJuymvRvHh9LWth-lZEK9dmQYtvh3h9XcauuNNGK3hWd_rwaV83yMT57wQm1QpSSXLOxHJ3YP8wd9W3foQ8IvKkPjW_qMyBwAvtm_YO_mUIqsjB4kxoFQ5sRxULZc2WHSEiUtWQ9sDGBef1PSsRuFuzwA",
  "refresh_token": "GTBYbXFJIx-fg5HUFRZdXBLwax79fBdBf5hAP_7VWLYQjeXCOq9z9ESU_pAFKrYctZb0KVizjuqZ9-gDGwSK8fynN0FmMse8cD2tyV1v6zXxvyRIcu1Cb8JaT8ZYD6x2",
  "scope": "openid",
  "id_token": "eyJraWQiOiI3YTE3MWY2My1lZGZmLTQ0ZTMtYmM3ZS1jOTNkZDk4MjVmMmEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDAxIiwiYXVkIjoic3BhLWNsaWVudCIsImF6cCI6InNwYS1jbGllbnQiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE2Nzg0NTQ5ODQsImlhdCI6MTY3ODQ1MzE4NH0.TMQE31yeB1MDHLiihW_5YxZVWwM4PGtKPm_QWflQoNx88lN9b6avKzC2UH2438o4gcTr5GtDTzCTCi5q3a_IpQdTv3gKOjj6WsZkSnH8zVN2rqhKeUgUMA2gC6sMKzn06IZY9LKvZWPLmUnvQA2pMsqap6oBtlabetcq7lqpByI9G9Q3nBgRAo82-7NiXEPe2ebjOVmKXg_iB-i22Pvb1j-8mpS4w3OG2Hh3N3CaPQHZ5Y8m5G9yqzBVMCHZ9rGn90cf3bQ2A89365SNmxL_3YBlahsK7njhSWoFNneMW3dEgzsSSWoq7QGoRy-XqCqLX7YpA5zyiL-jLASEWLOZEg",
  "token_type": "Bearer",
  "expires_in": 299
}
```