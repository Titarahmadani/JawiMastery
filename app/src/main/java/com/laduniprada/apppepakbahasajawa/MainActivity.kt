package com.laduniprada.apppepakbahasajawa

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Menampilkan layout login saat aplikasi dibuka

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        if (!isLoggedIn()) {
            showLoginScreen()
        } else {
            navigateToMainMenu()
        }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun showLoginScreen() {
        setContentView(R.layout.login)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username == "admin" && password == "123") {
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                navigateToMainMenu()
            } else {
                Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener {
            showExitConfirmationDialog()
        }
    }

    private fun navigateToMainMenu() {
        setContentView(R.layout.main_activity)

        val buttonMulai = findViewById<Button>(R.id.buttonMulai)
        buttonMulai.setOnClickListener {
            setContentView(R.layout.menu_layout)

            setupButtonListeners()
            setupBackButtonToStart()
            setupLogoutButton()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Konfirmasi Keluar")
            setMessage("Apakah Anda ingin keluar?")
            setPositiveButton("Ya") { _, _ ->
                sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                finish()
            }
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun setupButtonListeners() {
        val buttonNgoko = findViewById<Button>(R.id.buttonNgoko)
        val buttonKosokBalen = findViewById<Button>(R.id.buttonKosokBalen)
        val buttonPodoTegese = findViewById<Button>(R.id.buttonPodoTegese)
        val buttonDasanama = findViewById<Button>(R.id.buttonDasanama)

        buttonNgoko.setOnClickListener {
            setContentView(R.layout.ngoko_madya)
            setupBackButtonToButtons()
        }
        buttonKosokBalen.setOnClickListener {
            setContentView(R.layout.kosok_balen)
            setupBackButtonToButtons()
        }
        buttonPodoTegese.setOnClickListener {
            setContentView(R.layout.podo_tegese)
            setupBackButtonToButtons()
        }
        buttonDasanama.setOnClickListener {
            setContentView(R.layout.dasanama)
            setupBackButtonToButtons()
        }
    }

    private fun setupBackButtonToStart() {
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack?.setOnClickListener {
            setContentView(R.layout.main_activity)

            val buttonMulai = findViewById<Button>(R.id.buttonMulai)
            buttonMulai.setOnClickListener {
                setContentView(R.layout.menu_layout)

                setupButtonListeners()
                setupBackButtonToStart()
                setupLogoutButton()
            }
        }
    }

    private fun setupBackButtonToButtons() {
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack?.setOnClickListener {
            setContentView(R.layout.menu_layout)
            setupButtonListeners()
            setupBackButtonToStart()
            setupLogoutButton()
        }
    }

    private fun setupLogoutButton() {
        val buttonKeluar = findViewById<Button>(R.id.buttonKeluar)
        buttonKeluar.setOnClickListener {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            showLoginScreen()
        }
    }
}
