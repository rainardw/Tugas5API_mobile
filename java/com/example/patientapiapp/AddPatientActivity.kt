package com.example.patientapiapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.patientapiapp.model.Patient
import com.example.patientapiapp.network.RetrofitClient
import kotlinx.coroutines.launch
import org.json.JSONObject

class AddPatientActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etJenisKelamin: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etNoTelepon: EditText
    private lateinit var btnSimpan: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        etNama = findViewById(R.id.etNama)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        etJenisKelamin = findViewById(R.id.etJenisKelamin)
        etAlamat = findViewById(R.id.etAlamat)
        etNoTelepon = findViewById(R.id.etNoTelepon)
        btnSimpan = findViewById(R.id.btnSimpan)
        progressBar = findViewById(R.id.progressBar)

        btnSimpan.setOnClickListener {
            savePatient()
        }
    }

    private fun savePatient() {
        val nama = etNama.text.toString().trim()
        val tanggalLahir = etTanggalLahir.text.toString().trim()
        val jenisKelamin = etJenisKelamin.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()
        val noTelepon = etNoTelepon.text.toString().trim()

        if (nama.isEmpty() || tanggalLahir.isEmpty() || jenisKelamin.isEmpty() || alamat.isEmpty() || noTelepon.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            showLoading(true)
            try {
                val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                val token = prefs.getString("token", "")

                if (token.isNullOrEmpty()) {
                    Toast.makeText(this@AddPatientActivity, "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // Jangan kirim ID 0, biarkan null agar tidak dikirim di JSON
                val newPatient = Patient(
                    id = null,
                    nama = nama,
                    tanggal_lahir = tanggalLahir,
                    jenis_kelamin = jenisKelamin,
                    alamat = alamat,
                    no_telepon = noTelepon
                )

                val response = RetrofitClient.apiService.createPatient("Bearer $token", newPatient)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        Toast.makeText(this@AddPatientActivity, "Pasien berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        val msg = body?.message ?: "Gagal menambah pasien"
                        Toast.makeText(this@AddPatientActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Ambil pesan error dari errorBody jika ada
                    val errorStr = response.errorBody()?.string()
                    val errorMsg = try {
                        JSONObject(errorStr ?: "").getString("message")
                    } catch (e: Exception) {
                        "Error code: ${response.code()}"
                    }
                    Toast.makeText(this@AddPatientActivity, errorMsg, Toast.LENGTH_SHORT).show()
                    Log.e("AddPatient", "Error Response: $errorStr")
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddPatientActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("AddPatient", "Exception: ", e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        btnSimpan.isEnabled = !isLoading
    }
}