package com.example.appnotas.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.easyfingerprint.EasyFingerPrint
import com.example.appnotas.MainActivity
import com.example.appnotas.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    fun abrirDialogo(view: View){

        EasyFingerPrint(this) //Si no queremos hacer la instancia, solo llamamos los metodos, como se muestra abajo
            .setTittle("Sign in")
            .setSubTittle("account@account.com.br")
            .setDescription("In order to use the Fingerprint sensor we need your authorization first.e")
            .setColorPrimary(R.color.colorPrimary)
            .setIcon(ContextCompat.getDrawable(this,R.mipmap.ic_launcher_round))
            .setListern(object : EasyFingerPrint.ResultFingerPrintListern{
                override fun onError(mensage: String, code: Int) { //Lo que arroja un OnError, pasa porque el usaurio cancelo, el Sw no lo soporta etc

                    when(code){
                        EasyFingerPrint.CODE_ERRO_CANCEL -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_GREATER_ANDROID_M -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_HARDWARE_NOT_SUPPORTED -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_NOT_ABLED -> { } // TO DO
                        EasyFingerPrint.CODE_ERRO_NOT_FINGERS -> { } // TO DO
                        EasyFingerPrint.CODE_NOT_PERMISSION_BIOMETRIC -> { } // TO DO
                    }

                    Toast.makeText(this@Login,"Error: $mensage / $code", Toast.LENGTH_SHORT).show()
                }

                override fun onSucess(cryptoObject: FingerprintManagerCompat.CryptoObject?) {
                    //Toast.makeText(this@MainActivity,"Entro",Toast.LENGTH_SHORT).show()
                    //Thread.sleep(5000) //Varios Hilos. Lo duerme por unos segundos
                    Handler().postDelayed({

                        var intent = Intent(this@Login, MainActivity::class.java) //Simula cambiar de una ventana a otra. Navega entre pantallas. Puede pasar parametros
                        //Intent(de donde la estoy llamando, A donde quiero ir) -> Los ::(es estatico) si poenmos clas.java, indicamos que la clase es kotlin, trasladamos de kotlin a java. Para que lo interperete el intent
                        startActivity(intent) //si arriba despues de all le poenmos .apply, es para mandar parametros entre ventanas

                        //el intent manda error, entonces hay que cambiar a this@MainActivity
                    }, 1000)
                }

            })
            .startScan()
    }

    fun onRegister(view:View){
        var intent = Intent(this@Login, Registrar::class.java)
        startActivity(intent)
    }
}