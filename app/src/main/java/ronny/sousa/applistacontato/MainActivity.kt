package ronny.sousa.applistacontato

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ronny.sousa.applistacontato.adapters.AdapterContatos
import ronny.sousa.applistacontato.api.RetrofitClient

import ronny.sousa.applistacontato.models.Contato
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var recyclerContatos:RecyclerView

    lateinit var btnCadastrar: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerContatos = findViewById(R.id.recyclerContatos)

        btnCadastrar = findViewById(R.id.fabCadastrar)



        recyclerContatos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        var retrofitCli:RetrofitClient = RetrofitClient()
        retrofitCli.contatoService.getAllContatos().enqueue(
            object: Callback<List<Contato>>{
                override fun onResponse(
                    call: Call<List<Contato>>,
                    response: Response<List<Contato>>
                ) {
                    if(response.body()!=null) {

                        var adapter: AdapterContatos =
                            AdapterContatos(this@MainActivity, response.body()!!)
                        recyclerContatos.adapter = adapter
                    }

                }

                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    Log.e("API", "Falha ao carregar contatos", t )
                }

            }
        )

        btnCadastrar.setOnClickListener { abrirCadastro() }



    }


    override fun onResume() {
        super.onResume()
        var retrofitCli:RetrofitClient = RetrofitClient()
        retrofitCli.contatoService.getAllContatos().enqueue(
            object: Callback<List<Contato>>{
                override fun onResponse(
                    call: Call<List<Contato>>,
                    response: Response<List<Contato>>
                ) {
                    if(response.body()!=null) {

                        var adapter: AdapterContatos =
                            AdapterContatos(this@MainActivity, response.body()!!)
                        recyclerContatos.adapter = adapter
                    }else{
                        Toast.makeText(this@MainActivity,"não carregou",Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    Log.e("API", "Falha ao carregar contatos", t )
                }

            }
        )
    }

    private fun abrirCadastro() {
        var intent:Intent = Intent(this,EditarContatoActivity::class.java)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1 && resultCode==1)
        {


        }
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {

        val adapter:AdapterContatos = recyclerContatos.adapter as AdapterContatos
        val posicao:Int = adapter.posicaoClicada
        var contato:Contato? = null

        if(posicao>=0){
            contato = adapter.listaContatos.get(posicao)

        }


        if(item.itemId==R.id.menu_item_mapa){
            if (contato != null){
                val intent:Intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+contato.endereco))
                startActivity(intent)
            }
        }

        else if(item.itemId==R.id.menu_item_editar){
            if (contato != null){
                val intent:Intent = Intent(this, EditarContatoActivity::class.java)
                intent.putExtra("contato",contato)
                startActivity(intent)
            }
        }

        else if(item.itemId==R.id.menu_item_excluir){

        }

        else if(item.itemId==R.id.menu_item_ligacao){

        }


        return true
    }
}