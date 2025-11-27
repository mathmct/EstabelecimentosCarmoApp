package com.example.estabelecimentoscarmo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.estabelecimentoscarmo.R
import com.example.estabelecimentoscarmo.model.Estabelecimento
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Estabelecimento::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun estabelecimentoDao(): EstabelecimentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val callback = object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Após criação do DB, insere dados iniciais em background
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = database.estabelecimentoDao()
                                dao.inserirTodos(initialEstabelecimentos(context))
                            }
                        }
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "estabelecimentos_db"
                )
                    .addCallback(callback)
                    .build()

                INSTANCE = instance
                instance
            }
        }

        private fun initialEstabelecimentos(context: Context): List<Estabelecimento> {
            val pkg = context.packageName
            fun resUri(name: String) = "android.resource://$pkg/drawable/$name"

            return listOf(
                Estabelecimento(
                    fotoUri = resUri("habitus"),
                    nome = "Habitus Academia",
                    categoria = context.getString(R.string.categoria_academia),
                    endereco = "Av. 7 de Setembro, 1142 - Jardim do Carmo, Araraquara - SP",
                    site = "https://habitusacademia.com.br",
                    telefone = "(16)333328202",
                    plusCode = "6R29+67 Araraquara, State of São Paulo"
                ),
                Estabelecimento(
                    fotoUri = resUri("larosa"),
                    nome = "La Rosa - Cantina Italiana",
                    categoria = context.getString(R.string.categoria_restaurante),
                    endereco = "R. Pedro Álvares Cabral, 1139 - Centro, Araraquara - SP, 14800-210",
                    site = "http://cantinalarosa.com.br/",
                    telefone = "(16)33979007",
                    plusCode = "6R29+C8 Centro, Araraquara, State of São Paulo"
                ),
                Estabelecimento(
                    fotoUri = resUri("eobicho"),
                    nome = "É o Bicho - Clínica Veterinária",
                    categoria = context.getString(R.string.categoria_veterinaria),
                    endereco = "Av. 15 de Novembro, 1177 - Carmo, Araraquara - SP, 14801-030",
                    site = "https://pt-br.facebook.com/eobichoararaquara/",
                    telefone = "(16)996240360",
                    plusCode = "6R29+F4 Araraquara, State of São Paulo"
                ),
                Estabelecimento(
                    fotoUri = resUri("semprevale"),
                    nome = "Sempre Vale Supermercados",
                    categoria = context.getString(R.string.categoria_supermercado),
                    endereco = "R. Imac. Conceição, 1511 - Jardim do Carmo, Araraquara - SP, 14801-400",
                    site = "http://www.semprevale.com.br/",
                    telefone = "(16)33347828",
                    plusCode = "6R28+HC Araraquara, State of São Paulo"
                ),
                Estabelecimento(
                    fotoUri = resUri("boteco"),
                    nome = "Boteco Pé na Cova",
                    categoria = context.getString(R.string.categoria_bar),
                    endereco = "Av. Brasil, 1037 - Centro, Araraquara - SP, 14801-050",
                    site = "https://botecopenacova.com.br/",
                    telefone = "(16)34614080",
                    plusCode = "6R39+CG Centro, Araraquara, State of São Paulo"
                ),
                Estabelecimento(
                    fotoUri = resUri("mrjames"),
                    nome = "Mr James - Escola de Inglês",
                    categoria = context.getString(R.string.categoria_escola),
                    endereco = "Rua Comendador Pedro Morganti, 1449 - Centro, Araraquara - SP, 14801-395",
                    site = "http://www.misterjames.com.br/",
                    telefone = "(16)988348888",
                    plusCode = "6R28+CV Centro, Araraquara, State of São Paulo"
                )
            ).sortedBy { it.nome }
        }
    }
}
