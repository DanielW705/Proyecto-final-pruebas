package com.example.walkdog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    lateinit var btn_login: Button
    lateinit var user_txtbx: EditText
    lateinit var pwd_txtbx: EditText
    lateinit var error_view: TextView
    private lateinit var fuseLocationClient: FusedLocationProviderClient

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private fun isLocationPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                this,
                "Ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                map.isMyLocationEnabled = true
            else {
                Toast.makeText(
                    this,
                    "Ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (isLocationPermissionGranted()) {
//                map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun TryLogin(userName: String, userPwd: String): Boolean =
        userName == "UserPrueba" && userPwd == "123";

    private fun goToMainMenu() {
        val i = Intent(this, MenuActivity::class.java)
        startActivity((i))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fuseLocationClient = LocationServices.getFusedLocationProviderClient(this)

        enableLocation()

        btn_login = findViewById(R.id.loginButton)

        user_txtbx = findViewById(R.id.usernameEditText)

        pwd_txtbx = findViewById(R.id.passwordEditText)

        error_view = findViewById(R.id.errorTextView)



        btn_login.setOnClickListener {
            if (TryLogin(user_txtbx.text.toString(), pwd_txtbx.text.toString()))
                goToMainMenu()
            else
                error_view.text = "Hay un error en el usuario o en la contraseña"
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error_view.text = ""

            }

            override fun afterTextChanged(s: Editable?) {
            }

        }

        user_txtbx.addTextChangedListener(textWatcher)
        pwd_txtbx.addTextChangedListener(textWatcher)

    }
}