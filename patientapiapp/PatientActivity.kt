package com.example.patientapiapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patientapiapp.adapter.PatientAdapter
import com.example.patientapiapp.network.RetrofitClient
import kotlinx.coroutines.launch

class PatientActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var rvPatients: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        tvWelcome = findViewById(R.id.tvWelcome)
        rvPatients = findViewById(R.id.rvPatients)
        progressBar = findViewById(R.id.progressBar)

        // Tampilkan nama user
        val userName = intent.getStringExtra("USER_NAME") ?: "User"
        tvWelcome.text = "Selamat Datang, $userName"

        // Setup RecyclerView
        adapter = PatientAdapter()
        rvPatients.layoutManager = LinearLayoutManager(this)
        rvPatients.adapter = adapter

        // Load data pasien
        loadPatients()
    }

    private fun loadPatients() {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE

            try {
                // Ambil token dari SharedPreferences
                val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                val token = prefs.getString("token", "")

                if (token.isNullOrEmpty()) {
                    Toast.makeText(this@PatientActivity, "Token tidak ditemukan, silakan login ulang", Toast.LENGTH_SHORT).show()
                    finish()
                    return@launch
                }

                // Panggil API dengan Bearer token
                val response = RetrofitClient.apiService.getPasien("Bearer $token")

                if (response.isSuccessful && response.body()?.success == true) {
                    val patients = response.body()?.data ?: emptyList()
                    adapter.setData(patients)
                } else {
                    val msg = response.body()?.message ?: "Gagal mengambil data pasien"
                    Toast.makeText(this@PatientActivity, msg, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PatientActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}