package com.cezila.passwordmanager.ui.app

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cezila.passwordmanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGerar = findViewById<Button>(R.id.btn_gerar_senha)
        val buttonRecuperar = findViewById<Button>(R.id.btn_recuperar_senha)

        buttonGerar.setOnClickListener {
            viewModel.criarSenha()
        }
        buttonRecuperar.setOnClickListener {
            viewModel.recuperarSenha()
        }
    }
}