package com.example.estabelecimentoscarmo.ui.detalhes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.estabelecimentoscarmo.databinding.ActivityDetalhesBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento

class DetalheEstabelecimentoActivity : AppCompatActivity() {

    private lateinit var bind: ActivityDetalhesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val item = intent.getSerializableExtra("item", Estabelecimento::class.java) as Estabelecimento

        try {
            bind.imgFoto.setImageURI(item.fotoUri.toUri())
        } catch (e: SecurityException) {
            bind.imgFoto.setImageResource(android.R.drawable.ic_menu_report_image)
        }

        bind.txtNome.text = item.nome
        bind.txtCategoria.text = item.categoria
        bind.txtEndereco.text = item.endereco
        bind.txtTelefone.text = item.telefone
        bind.txtSite.text = item.site
        bind.txtPlusCode.text = item.plusCode

        bind.txtTelefone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${item.telefone}")
            }
            startActivity(intent)
        }

        bind.txtEndereco.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(item.endereco)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            startActivity(mapIntent)
        }

        bind.txtSite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.site))
            startActivity(intent)
        }

        bind.txtPlusCode.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(item.plusCode)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            startActivity(mapIntent)
        }
    }
}

