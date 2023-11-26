import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseloginkotlin.R

class ActividadesAdapter(private var actividades: List<String>) : RecyclerView.Adapter<ActividadesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actividad = actividades[position]
        holder.bind(actividad)
    }

    override fun getItemCount(): Int {
        return actividades.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.activityTitleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.activityDescriptionTextView)

        fun bind(actividad: String) {
            // Asignar datos a las vistas
            titleTextView.text = "Actividad ${adapterPosition + 1}:"
            descriptionTextView.text = actividad
        }
    }
    fun actualizarActividades(nuevasActividades: List<String>) {
        actividades = nuevasActividades
        notifyDataSetChanged()
    }

}
