package ronny.sousa.applistacontato.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import ronny.sousa.applistacontato.R
import ronny.sousa.applistacontato.models.Contato
import java.util.zip.Inflater

class AdapterContatos(var contexto:Context , var listaContatos:List<Contato>) : Adapter<AdapterContatos.MeuViewHolder>() {

    var posicaoClicada:Int = -1


    class MeuViewHolder(itemView: View,val contexto:Context) : RecyclerView.ViewHolder(itemView),View.OnCreateContextMenuListener {


        init{
            itemView.setOnCreateContextMenuListener(this);
        }

        //cria um menu suspenso para realizar ações para cada item do menu
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            var menuInflater:MenuInflater = MenuInflater(contexto)
            menuInflater.inflate(R.menu.menu_contato,menu)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        var inflater:LayoutInflater = LayoutInflater.from(contexto)
        var view =inflater.inflate(R.layout.contato_item,parent,false)
        return MeuViewHolder(view,contexto)

    }

    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        val contato:Contato = listaContatos.elementAt(position)
        val txtNome:TextView = holder.itemView.findViewById(R.id.txtNome)
        val txtTelefone:TextView = holder.itemView.findViewById(R.id.txtTelefone)
        txtNome.text = contato.nome
        txtTelefone.text = contato.telefone

        holder.itemView.setOnLongClickListener {  v->
            posicaoClicada  = holder.adapterPosition
            false
        }
    }

    override fun getItemCount(): Int {
        return  this.listaContatos.size
    }


}