package com.example.messagingapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.text.TextUtils
import android.util.Log
import com.example.messagingapp.R
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        firebaseUser =auth.currentUser!!


      /*  val dynamicLinks = Firebase.dynamicLinks.dynamicLink {
            link = Uri.parse("http://chatapp.com/login")
            domainUriPrefix = "https://qachatapp.page.link/loginpage"

            // Open links with this app on Android
            androidParameters {
                minimumVersion =1
            }
        }
        val dynamicLinkUri = dynamicLinks.uri

        Firebase.dynamicLinks.getDynamicLink(intent).addOnSuccessListener (this){pendingDynamicLinkData ->
            var deepLink:Uri?=null
            if (pendingDynamicLinkData!=null){
                deepLink=pendingDynamicLinkData.link
                Log.d("LoginActivity","${deepLink.toString()}")

            }

        }.addOnFailureListener (this){e ->
            Log.d("DynamicLinkError",e.localizedMessage)

        }*/

        //check if user login then navigate to user screen
        if (firebaseUser != null) {
            val intent = Intent(
                this@LoginActivity,
                UsersActivity::class.java
            )
            startActivity(intent)

        }
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                etEmail.error = "Email Required"
                etEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                etPassword.error = "Password Required"
                etPassword.requestFocus()
            } else {
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            etEmail.setText("")
                            etPassword.setText("")
                            val intent = Intent(
                                this@LoginActivity,
                                UsersActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "email or password invalid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                UsersActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}
