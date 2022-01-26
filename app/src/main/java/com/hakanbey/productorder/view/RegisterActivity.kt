package com.hakanbey.productorder.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hakanbey.productorder.R
import com.hakanbey.productorder.databinding.ActivityRegisterBinding
import com.hakanbey.productorder.extension.disable
import com.hakanbey.productorder.extension.enable
import com.hakanbey.productorder.extension.isHaveSpace
import com.hakanbey.productorder.extension.isValidEmail
import com.hakanbey.productorder.model.UserRegisterResponse
import com.hakanbey.productorder.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    //  Binding
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Run Listener Method
        objectListener()
    }

    /**
     * Listener
     */
    private fun objectListener() {
        //  Kayıt Ol Butonu
        binding.registerBtn.setOnClickListener {
            if (binding.registerTxtMail.text.toString().isEmpty()) {
                Toast.makeText(this, "Lütfen e-posta adresinizde boş kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (!binding.registerTxtMail.isValidEmail()) {
                Toast.makeText(this, "Lütfen geçerli bir e-posta adresi girin.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtMail.isHaveSpace()) {
                Toast.makeText(this, "Lütfen e-posta adresinizde boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtName.text.isEmpty()) {
                Toast.makeText(this, "Lütfen adınızı yazın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtName.isHaveSpace()) {
                Toast.makeText(this, "Lütfen adınızda boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtSurname.text.isEmpty()) {
                Toast.makeText(this, "Lütfen soyadınızı yazın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtSurname.isHaveSpace()) {
                Toast.makeText(this, "Lütfen soyadınızda boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPhone.text.isEmpty()) {
                Toast.makeText(this, "Lütfen telefon numaranızı yazın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPhone.isHaveSpace()) {
                Toast.makeText(this, "Lütfen telefon numaranızda boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPassword.text.isEmpty()) {
                Toast.makeText(this, "Lütfen şifrenizi yazın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPassword.isHaveSpace()) {
                Toast.makeText(this, "Lütfen şifrenizde boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtConfirmPassword.text.isEmpty()) {
                Toast.makeText(this, "Lütfen şifrenizin tekrarını yazın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtConfirmPassword.isHaveSpace()) {
                Toast.makeText(this, "Lütfen şifrenizin tekrarınında boşluk kullanmayın.", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPassword.text.toString() != binding.registerTxtConfirmPassword.text.toString()) {
                Toast.makeText(this, "Girilen şifreler birbiri ile uyuşmuyor.", Toast.LENGTH_SHORT).show()
            } else if (!binding.registerCbAgreement.isChecked) {
                Toast.makeText(this, "Lütfen üyelik sözleşmesini onaylayın.", Toast.LENGTH_SHORT).show()
            } else {
                val userMail: String = binding.registerTxtMail.text.toString()
                val userName: String = binding.registerTxtName.text.toString()
                val userSurname: String = binding.registerTxtSurname.text.toString()
                val userPhone: String = binding.registerTxtPhone.text.toString()
                val userPass: String = binding.registerTxtPassword.text.toString()
                binding.registerBtn.disable()
                registerUser(userMail, userName, userSurname, userPhone, userPass)
            }
        }

        //  Geri Butonu
        binding.registerBtnBack.setOnClickListener {
            //  Geri Dön
            finish()
            //  Sayfa Değiştirme Animasyonu
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
    }

    /**
     * Register
     */
    private fun registerUser(userMail: String, userName: String, userSurname: String, userPhone: String, userPass: String) {
        Util.getClient().userRegister(userMail, userName, userSurname, userPhone, userPass)
            .enqueue(object : Callback<UserRegisterResponse> {
                override fun onResponse(call: Call<UserRegisterResponse>, response: Response<UserRegisterResponse>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val data = response.body()!!
                            if (data.user[0].durum) {
                                Toast.makeText(this@RegisterActivity, data.user[0].mesaj, Toast.LENGTH_SHORT).show()
                                finish()
                                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
                            } else {
                                Toast.makeText(this@RegisterActivity, data.user[0].mesaj, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                    }
                    binding.registerBtn.enable()
                }

                override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.toString(), Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                    binding.registerBtn.enable()
                }

            })
    }
}