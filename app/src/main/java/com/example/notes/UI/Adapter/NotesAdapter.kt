package com.example.notes.UI.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Model.Notes
import com.example.notes.R
import com.example.notes.UI.Fragments.HomeFragmentDirections
import com.example.notes.databinding.ItemBinding

class NotesAdapter(val context: Context, private var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    fun filtering(newFilteredList: ArrayList<Notes>) {
        notesList = newFilteredList
        notifyDataSetChanged()
    }

    class notesViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val data=notesList[position]
       holder.binding.cvTitle.text=data.title
        holder.binding.cvSubtitle.text=data.subTitle
        holder.binding.cvDate.text=data.date
        when(data.priority){
            "1"->
                holder.binding.cvOval.setBackgroundResource(R.drawable.green_oval)
            "2"->
                holder.binding.cvOval.setBackgroundResource(R.drawable.yellow_oval)
            "3"->
                holder.binding.cvOval.setBackgroundResource(R.drawable.red_oval)
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToEditFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun getItemCount(): Int = notesList.size
}