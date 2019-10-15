package si.matijav.kamino.ui.residents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import si.matijav.kamino.data.Resident
import si.matijav.kamino.databinding.ResidentItemBinding


class ResidentAdapter(val viewModel: ResidentsViewModel) :
    RecyclerView.Adapter<ResidentAdapter.ResidentViewHolder>() {

    var residents: List<Resident> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ResidentItemBinding.inflate(layoutInflater, parent, false)
        itemBinding.viewModel = viewModel
        return ResidentViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return residents.size
    }

    override fun onBindViewHolder(holder: ResidentViewHolder, position: Int) {
        val resident = residents.get(position)
        holder.bind(resident)
    }

    class ResidentViewHolder(val binding: ResidentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resident: Resident) {
            binding.resident = resident
            binding.executePendingBindings()
        }
    }
}