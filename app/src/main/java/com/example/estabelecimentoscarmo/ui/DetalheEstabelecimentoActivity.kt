package com.example.estabelecimentoscarmo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.estabelecimentoscarmo.databinding.ActivityDetalheEstabelecimentoBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento

class DetalheEstabelecimentoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalheEstabelecimentoBinding
    private lateinit var estabelecimento: Estabelecimento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheEstabelecimentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        estabelecimento = intent.getSerializableExtra("estabelecimento", Estabelecimento::class.java) as Estabelecimento
    }

    private fun setupViews() {
        binding.tvNome.text = estabelecimento.nome
        binding.tvCategoria.text = estabelecimento.categoria
        binding.tvEndereco.text = estabelecimento.endereco
        binding.tvTelefone.text = estabelecimento.telefone
        binding.tvSite.text = estabelecimento.site
        binding.imgFoto.setImageResource(estabelecimento.foto)
    }

    private fun setupListeners() {
        binding.btnLigar.setOnClickListener {
            val telFormatted = estabelecimento.telefone.filter { it.isDigit() }
            val intent = Intent(Intent.ACTION_DIAL, "tel:$telFormatted".toUri())
            startActivity(intent)
        }

        binding.btnSite.setOnClickListener {
            var url = estabelecimento.site
            if (!url.startsWith("http")) url = "https://$url"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.btnMapa.setOnClickListener {
            val query = if (estabelecimento.plusCode.isNotBlank()) {
                estabelecimento.plusCode
            } else {
                estabelecimento.endereco
            }
            val uri = Uri.parse("geo:0,0?q=" + Uri.encode(query))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}
