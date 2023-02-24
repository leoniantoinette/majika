package com.example.majika.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.majika.MainActivity
import com.example.majika.R
import com.example.majika.database.KeranjangViewModel
import com.example.majika.header.header_pembayaran
import com.example.majika.retrofit.Retrofit
import com.example.majika.retrofit.data.DataPembayaran
import com.example.majika.retrofit.endpoint.PembayaranEndpoint
import kotlinx.android.synthetic.main.pembayaran.*
import kotlinx.coroutines.launch

private const val CAMERA_REQUEST_CODE = 101

class Pembayaran : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pembayaran)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_header, header_pembayaran())
        fragmentTransaction.commit()

        val intent = getIntent();
        val totalPayment = intent.getStringExtra("total");
        val totalTextView = findViewById<TextView>(R.id.totalPayment)
        totalTextView.text = "Total : " + totalPayment

        setupPermission()
        codeScanner()
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    lifecycleScope.launch {
                        checkPaymentWithAPI(it.text)
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }

            scanner_view.setOnClickListener {
                gagal.visibility = View.GONE
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need the camera permission to be able to use this app!", Toast.LENGTH_SHORT)
                } else {
                    // success
                }
            }
        }
    }

    private suspend fun checkPaymentWithAPI(transaction_id: String) {
        val pembayaranAPI = Retrofit.getInstance().create(PembayaranEndpoint::class.java)
        try {
            val response = pembayaranAPI.postPayment(transaction_id)
            if (response.isSuccessful) {
                showPaymentStatus(response.body()!!)
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    private fun showPaymentStatus(dataPembayaran: DataPembayaran) {
        if (dataPembayaran.status == "SUCCESS") {
            berhasil.visibility = View.VISIBLE

            // reset jumlah keranjang
            var keranjangViewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
            keranjangViewModel.resetJumlahKeranjang()

            // delay 5 seconds then redirect to menu
            Handler().postDelayed({
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("fragment", "menu")
                startActivity(i)
            }, 5000)

        } else {
            gagal.visibility = View.VISIBLE
        }
    }
}