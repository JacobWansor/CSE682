package com.example.tennisapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tennisapp.R
import com.example.tennisapp.api.RetrofitAPI
import com.example.tennisapp.api.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var apiInterface : RetrofitAPI //Object that communicates with local SQL server

    private lateinit var usernameET : EditText
    private lateinit var passwordET : EditText
    private lateinit var usernameInput : String //Variable to store username that is typed into EditText box
    private lateinit var passwordInput : String //Variable to store password that is typed into EditText box
    private lateinit var loginButton : Button
    private lateinit var registerButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiInterface = RetrofitInstance.getInstance().create(RetrofitAPI::class.java) //create instance of Retrofit API. Used to pass and receive SQL queries from server

        //Initialize interface variables by finding IDs in login.xml
        usernameET = findViewById(R.id.usernameET)
        passwordET = findViewById(R.id.passwordET)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)


        //Define behavior on button click
        loginButton.setOnClickListener {
            usernameInput = usernameET.text.toString()
            passwordInput = passwordET.text.toString()

            if(usernameInput.isEmpty() || passwordInput.isEmpty()){
                Toast.makeText(this, "Empty Field", Toast.LENGTH_LONG).show()
            }
            else{
                val call = apiInterface.userLogin(usernameInput, passwordInput) //Tell server to to query database for entry with matching username and password

                call.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                        if(p1.message().equals("Unauthorized")){
                            Toast.makeText(applicationContext, "Invalid Username or Password", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val intent = Intent(applicationContext,MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                        Toast.makeText(applicationContext, p1.message.toString(), Toast.LENGTH_SHORT).show()
                        val message = p1.message.toString()
                    }
                })
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}