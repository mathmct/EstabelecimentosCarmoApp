package com.example.estabelecimentoscarmo.ui.cadastro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.estabelecimentoscarmo.data.AppDatabase
import com.example.estabelecimentoscarmo.data.EstabelecimentoRepository
import com.example.estabelecimentoscarmo.databinding.ActivityCadastroBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var repo: EstabelecimentoRepository
    private lateinit var launcherGaleria: ActivityResultLauncher<Array<String>>
    private var uriSelecionada: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(this)
        repo = EstabelecimentoRepository(db.estabelecimentoDao())

        setupLauncher()
        setupListeners()
    }

    private fun setupLauncher() {

        // FORÇAR A USAR O OpenDocument (SAF)
        launcherGaleria = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->

            if (uri != null) {

                // PERMISSÃO PERSISTENTE REAL
                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }

                uriSelecionada = uri
                binding.imgPreview.setImageURI(uri)
            }
        }
    }

    private fun setupListeners() {

        binding.btnEscolherFoto.setOnClickListener {
            launcherGaleria.launch(arrayOf("image/*"))
        }

        binding.btnSalvar.setOnClickListener {

            val foto = uriSelecionada?.toString()
            val nome = binding.edtNome.text.toString()
            val categoria = binding.edtCategoria.text.toString()
            val endereco = binding.edtEndereco.text.toString()
            val telefone = binding.edtTelefone.text.toString()
            val site = binding.edtSite.text.toString()
            val plus = binding.edtPlusCode.text.toString()

            if (foto == null || nome.isBlank() || categoria.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novo = Estabelecimento(
                fotoUri = foto,
                nome = nome,
                categoria = categoria,
                endereco = endereco,
                telefone = telefone,
                site = site,
                plusCode = plus
            )

            salvarNoBanco(novo)
        }
    }

    private fun salvarNoBanco(est: Estabelecimento) {
        lifecycleScope.launch(Dispatchers.IO) {
            repo.inserir(est)
            finish()
        }
    }
}
