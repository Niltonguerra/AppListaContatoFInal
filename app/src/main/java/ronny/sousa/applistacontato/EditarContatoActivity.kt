package ronny.sousa.applistacontato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ronny.sousa.applistacontato.api.RetrofitClient
import ronny.sousa.applistacontato.models.Contato
import kotlin.math.log


class EditarContatoActivity : AppCompatActivity() {
    var contato:Contato?= null
    lateinit var btSalvar:Button
    lateinit var txtNome:EditText
    lateinit var txtEmail:EditText
    lateinit var txtTelefone:EditText
    lateinit var txtEndereco:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_contato)

        btSalvar = findViewById(R.id.btSalvar)

        txtNome = findViewById(R.id.txtNome)
        txtEmail = findViewById(R.id.txtEmail)
        txtTelefone = findViewById(R.id.txtTelefone)
        txtEndereco = findViewById(R.id.txtEndereco)


        if(this.intent.getSerializableExtra("contato")!= null){
            this.contato = this.intent.getSerializableExtra("contato") as Contato
            preencherDados()
        }









        btSalvar.setOnClickListener {
            if(contato!=null){
                atualizarContato()
            }else{
                novoContato()
            }

        }

    }

    private fun novoContato() {
        if(!txtNome.text.toString().isNullOrEmpty())
        {
            contato = Contato(
                _id = "-1",
                nome = txtNome.text.toString(),
                email = txtEmail.text.toString(),
                telefone = txtTelefone.text.toString(),
                endereco = txtEndereco.text.toString(),
                foto = "teste"
            )
            val retrofitCli:RetrofitClient = RetrofitClient()
            //o "!!" é o oposto de "?"
            retrofitCli.contatoService.createContato(contato!!)
                .enqueue(object:Callback<Contato>{
                    override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                        Log.i("Criar contatos","onResponse"+response.body())
                        Toast.makeText(this@EditarContatoActivity,
                            "Contato criado",Toast.LENGTH_LONG).show()
                        this@EditarContatoActivity.finish()
                    }

                    override fun onFailure(call: Call<Contato>, t: Throwable) {
                        Log.e("AtualizaContatos","onFailure",t)
                    }

                })
        }

        this.finish()
    }

    private fun preencherDados() {

        txtNome.setText(contato?.nome)
        txtEndereco.setText(contato?.endereco)
        txtEmail.setText(contato?.email)
        txtTelefone.setText(contato?.telefone)



    }

    private fun atualizarContato() {
        if(!txtNome.text.toString().isNullOrEmpty())
        {
            contato = Contato(
                _id = contato!!._id,
                nome = txtNome.text.toString(),
                email = txtEmail.text.toString(),
                telefone = txtTelefone.text.toString(),
                endereco = txtEndereco.text.toString(),
                foto = "teste"
            )
            val retrofitCli:RetrofitClient = RetrofitClient()
            //o "!!" é o oposto de "?"
            retrofitCli.contatoService.updateContato(contato!!._id,contato!!)
                .enqueue(object:Callback<Contato>{
                    override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                        Log.i("Atualizar Contatos","onResponse"+response.body())
                        Toast.makeText(this@EditarContatoActivity,
                        "Contato Atualizado",Toast.LENGTH_LONG).show()
                        this@EditarContatoActivity.finish()
                    }

                    override fun onFailure(call: Call<Contato>, t: Throwable) {
                        Log.e("AtualizaContatos","onFailure",t)
                    }

                })
        }



    }
}