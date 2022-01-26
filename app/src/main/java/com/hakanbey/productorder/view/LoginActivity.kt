package com.hakanbey.productorder.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hakanbey.productorder.R
import com.hakanbey.productorder.databinding.ActivityLoginBinding
import com.hakanbey.productorder.extension.*
import com.hakanbey.productorder.model.UserLoginResponse
import com.hakanbey.productorder.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    //  Binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Run Listener Method
        objectListener()
    }

    /**
     * Listener
     */
    private fun objectListener() {
        //  Login Button
        binding.loginBtn.setOnClickListener {
            //  E-posta Boş Bırakıldıysa
            if (binding.loginTxtMail.text.isEmpty()) {
                Toast.makeText(this, "Lütfen e-posta alanını boş bırakmayın.", Toast.LENGTH_SHORT).show()
            }
            //  E-posta Boşluk Kontrolü
            else if (binding.loginTxtMail.isHaveSpace()) {
                Toast.makeText(this, "Lütfen e-posta adresinizde boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            }
            //  Geçerli Bir E-posta Kontrolü
            else if (!binding.loginTxtMail.isValidEmail()) {
                Toast.makeText(this, "Lütfen geçerli bir e-posta adresi girin.", Toast.LENGTH_SHORT).show()
            } else if (binding.loginTxtPassword.isHaveSpace()) {
                Toast.makeText(this, "Lütfen şifrenizde boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else {
                binding.loginBtn.disable()
                val email = binding.loginTxtMail.text.toString()
                val password = binding.loginTxtPassword.text.toString()
                userLogin(email, password)
            }
        }

        //  Register Button
        binding.loginLblRegister.setOnClickListener {
            //  Açılacak yeni sayfayı intente ata
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            //  Yeni sayfayı aç
            startActivity(intent)
            //  Yeni sayfa açılırken animayon ile geçiş yap
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

    /**
     * Login Function
     */
    private fun userLogin(email: String, password: String) {
        showLoading()
        Util.getClient().userLogin(userEmail = email, userPass = password).enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.userLogin?.get(0)?.durum == true) {
                            //  Beni Hatırla
                            if (binding.loginCbRememberMe.isChecked)
                                saveLogin(email, password)
                            //  Kullanıcı Bilgilerini Object'e Aktar
                            Util.USERResponse = data

                            Toast.makeText(this@LoginActivity, data.userLogin[0].mesaj, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, ProductActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, data.userLogin!![0].mesaj, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
                binding.loginBtn.enable()
                hideLoading()
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                binding.loginBtn.enable()
                hideLoading()
            }

        })
    }

    /**
     * Save Login Info SharedPref
     */
    private fun saveLogin(email: String, password: String) {
        val shared = getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    /**
     * Show Loading Effect
     */
    private fun showLoading() {
        binding.incToolbarLogin.toolbarLineProgressBar.show()
    }

    /**
     * Hide Loading Effect
     */
    private fun hideLoading() {
        binding.incToolbarLogin.toolbarLineProgressBar.hide()
    }
}