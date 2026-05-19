# Tugas5API_mobile
Tugas Pemrograman bergerak mobile materi 7 API

# 📱 Aplikasi Data Pasien (Tugas Materi 7)

Aplikasi Android berbasis Kotlin yang mengimplementasikan konsumsi **REST API** menggunakan library **Retrofit**. Aplikasi ini mencakup fitur autentikasi login, penyimpanan token lokal, dan pengambilan data pasien dengan keamanan *Bearer Token*.

## Fitur Utama
- **Login**: Autentikasi pengguna menggunakan API endpoint `/login`.
- **Token Management**: Menyimpan JWT Token ke `SharedPreferences` setelah login berhasil.
- **Bearer Auth**: Mengirimkan token pada header request `GET /api/pasien`.
- **Data List**: Menampilkan data pasien (Nama, TTL, Alamat, dll) di `RecyclerView`.
- **Loading State**: Indikator loading saat proses network berlangsung.
- **Create Data**: Menambah data pasien baru

## Tech Stack
- **Language**: Kotlin
- **Network**: Retrofit2, OkHttp, Gson Converter
- **UI**: XML Layout, RecyclerView, CardView
- **Storage**: SharedPreferences

## Screenshot
<table>
  <tr>
    <th align="center">Halaman Login</th>
    <th align="center">Halaman Data Pasien</th>
  </tr>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/9634b2e9-cef4-4404-9b59-015932ffa067" width="200"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/27643e3e-0a35-47b6-a7cb-f28eb99b62a6" width="200"/></td>
  </tr>
  <tr>
    <th align="center">Halaman Create Data</th>
    <th align="center">Data Baru Muncul</th>
  </tr>
  <tr>
    <td align="center"><img src="https://github.com/user-attachments/assets/3b03ba1c-87bf-49d7-b3f4-7e732bf37518" width="200"/></td>
    <td align="center"><img src="https://github.com/user-attachments/assets/6ee00f75-7917-477d-b150-588b4ccf4881" width="200"/></td>
  </tr>
</table>
