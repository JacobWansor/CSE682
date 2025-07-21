package com.example.tennisapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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


class RegisterActivity : AppCompatActivity() {

    private lateinit var apiInterface : RetrofitAPI //Object that communicates with local SQL server

    //Variables for View components. Late initialization, initialized during onCreate()
    private lateinit var emailET : EditText
    private lateinit var usernameET : EditText
    private lateinit var passwordET : EditText
    private lateinit var confirmpasswordET : EditText
    private lateinit var securityquestionSpinner : Spinner
    private lateinit var answerET : EditText

    //Variables to store user input. Retrieved from EditTexts and Spinner
    private lateinit var emailInput : String
    private lateinit var usernameInput : String
    private lateinit var passwordInput : String
    private lateinit var confirmpasswordInput : String
    private lateinit var securityQuestion : String
    private lateinit var answer : String

    private lateinit var submitButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiInterface = RetrofitInstance.getInstance().create(RetrofitAPI::class.java) //create instance of Retrofit API. Used to pass and receive SQL queries from server

        emailET = findViewById(R.id.emailET)
        usernameET = findViewById(R.id.usernameET)
        passwordET = findViewById(R.id.passwordET)
        confirmpasswordET = findViewById(R.id.confirmpasswordET)
        answerET = findViewById(R.id.answerET)
        submitButton = findViewById(R.id.submitButton)

        securityquestionSpinner = findViewById(R.id.securityquestionSpinner)


        //Assign drop down selection items to spinner.
        //Selection items stored in res/values/securityquestions.xml
        //Spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.securityquestions_array,
            R.layout.spinnerdropdownitem
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinnerdropdownitem)
            securityquestionSpinner.adapter = adapter
        }

        submitButton.setOnClickListener {
            emailInput = emailET.text.toString()
            usernameInput = usernameET.text.toString()
            passwordInput = passwordET.text.toString()
            confirmpasswordInput = confirmpasswordET.text.toString()
            securityQuestion = securityquestionSpinner.selectedItem.toString()
            answer = answerET.text.toString()

            /*
            Check if all user input is valid and that nothing was left blank
             */

           if(passwordInput.equals(confirmpasswordInput)){
               val call = apiInterface.createUser(emailInput, usernameInput, passwordInput, securityQuestion, answer) //Tell server to add an entry into 'user' table in 'tennisapp' database.

               call.enqueue(object: Callback<ResponseBody> {
                   override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                       val response = p1.body()!!.string()
                       Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                   }

                   override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                       Toast.makeText(applicationContext, p1.message.toString(), Toast.LENGTH_SHORT).show()
                       val message = p1.message.toString()
                   }
               })
           }
           else{
               Toast.makeText(this,"Passwords do not match", Toast.LENGTH_LONG).show()
           }

        }

    }

    fun userRegistration(userName: String,
                         password: String,
                         securityQuestion: String,
                         securityQuestionAnswer : String)
    {

    }
}
