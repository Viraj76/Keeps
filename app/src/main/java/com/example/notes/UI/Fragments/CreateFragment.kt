package com.example.notes.UI.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.notes.Model.Notes
import com.example.notes.R
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentCreateBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateFragment : Fragment() {
    lateinit var binding: FragmentCreateBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(layoutInflater, container, false)
        binding.greenOval.setImageResource(R.drawable.done)
        binding.greenOval.setOnClickListener {
            priority ="1"
            binding.greenOval.setImageResource(R.drawable.done)
            binding.yellowOval.setImageResource(0)
            binding.redOval.setImageResource(0)
        }
        binding.yellowOval.setOnClickListener {
            priority ="2"
            binding.yellowOval.setImageResource(R.drawable.done)
            binding.greenOval.setImageResource(0)
            binding.redOval.setImageResource(0)
        }
        binding.redOval.setOnClickListener {
            priority ="3"
            binding.redOval.setImageResource(R.drawable.done)
            binding.yellowOval.setImageResource(0)
            binding.greenOval.setImageResource(0)
        }
        binding.fbtnDone.setOnClickListener {
            lifecycleScope.launch{
                createNote(it)
            }
        }
        return binding.root
    }

    private suspend fun createNote(it: View?) {

        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val notes = binding.etNotes.text.toString()
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("MMMM dd, h:mm a")
        val dateTime = simpleDateFormat.format(calendar.time)
        if(title != ""){

            val data = Notes(null, title, subTitle, notes, dateTime.toString(), priority)
            viewModel.addNotes(data)
            Toast.makeText(requireContext(), "Notes Added Successfully", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it!!).navigate(R.id.action_createFragment2_to_homeFragment2)
        }
        else{
            Toast.makeText(requireContext(),"Please Give Some Title!",Toast.LENGTH_SHORT).show()
        }


    }
}