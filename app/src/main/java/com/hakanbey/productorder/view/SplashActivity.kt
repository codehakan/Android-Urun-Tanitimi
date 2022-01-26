package com.hakanbey.productorder.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.hakanbey.productorder.R
import com.hakanbey.productorder.databinding.ActivitySplashBinding
import com.hakanbey.productorder.extension.enable
import com.hakanbey.productorder.model.UserLoginResponse
import com.hakanbey.productorder.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    //  Binding
    private lateinit var binding: ActivitySplashBinding

    //  CountDownTimer
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arr = arrayOf(
            "Uygulama başlatılıyor...",
            "Uygulama sürümü kontrol ediliyor...",
            "Sunucu bağlantısı bekleniyor..."
        )

        //  Initialize
        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                when (val second: Int = (millisUntilFinished / 1000).toInt()) {
                    2 -> {
                        binding.splashLblDescription.text = arr[second]
                    }
                    1 -> {
                        binding.splashLblDescription.text = arr[second]
                    }
                    0 -> {
                        binding.splashLblDescription.text = arr[second]
                    }
                }
            }

            override fun onFinish() {
                controlLogin()
            }

        }.start()
    }

    /**
     * Login Function
     */
    private fun userLogin(email: String, password: String) {
        Util.getClient().userLogin(userEmail = email, userPass = password).enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.userLogin?.get(0)?.durum == true) {
                            //  Kullanıcı Bilgilerini Object'e Aktar
                            Util.USERResponse = data

                            val intent = Intent(this@SplashActivity, ProductActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                            finish()
                        } else {
                            Toast.makeText(this@SplashActivity, data.userLogin!![0].mesaj, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@SplashActivity, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Toast.makeText(this@SplashActivity, t.toString(), Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }

    /**
     * Control User Information SharedPreferences
     */
    private fun controlLogin() {
        val shared = getSharedPreferences("login", Context.MODE_PRIVATE)
        val email: String? = shared.getString("email", "")
        val password: String? = shared.getString("password", "")
        if ((email != null && email.isNotEmpty()) && (password != null && password.isNotEmpty())) {
            userLogin(email, password)
        } else {
            //  Açılacak yeni sayfayı intente ata
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            //  Yeni sayfayı aç
            startActivity(intent)
            //  Yeni sayfa açılırken animayon ile geçiş yap
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            //  Splash sayfasını öldür
            finish()
        }
    }
}