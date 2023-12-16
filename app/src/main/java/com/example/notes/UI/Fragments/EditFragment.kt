package com.example.notes.UI.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.Model.Notes
import com.example.notes.R
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentEditBinding
import com.example.notes.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {
    val oldNotes by navArgs<EditFragmentArgs>()
    lateinit var binding: FragmentEditBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(layoutInflater,container,false)
       setHasOptionsMenu(true)
        binding.etEditTitle.setText(oldNotes.data.title)  // setText() for Editable
        binding.etEditSubtitle.setText(oldNotes.data.subTitle)
        binding.etEditNotes.setText(oldNotes.data.notes)

        when(oldNotes .data.priority){
            "1"->
            {
                priority="1"
                binding.greenOval.setImageResource(R.drawable.done)
                binding.yellowOval.setImageResource(0)
                binding.redOval.setImageResource(0)
            }
            "2"->{
                priority="1"
                binding.yellowOval.setImageResource(R.drawable.done)
                binding.greenOval.setImageResource(0)
                binding.redOval.setImageResource(0)
            }
            "3"->{
                priority="1"
                binding.redOval.setImageResource(R.drawable.done)
                binding.yellowOval.setImageResource(0)
                binding.greenOval.setImageResource(0)
            }
        }
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
            updateNotes(it)
        }

        return binding.root
    }
    private fun updateNotes(it:View) {
        val title = binding.etEditTitle.text.toString()
        val subTitle = binding.etEditSubtitle.text.toString()
        val notes = binding.etEditNotes.text.toString()
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("MMMM dd, h:mm a")
        val dateTime = simpleDateFormat.format(calendar.time)
        val data = Notes(
            id = oldNotes.data.id,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date= dateTime.toString(),
            priority = priority,
            currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        )
        viewModel.updateNotes(data)
        Toast.makeText(requireContext(), "Notes Updated Successfully", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(it).navigate(R.id.action_editFragment_to_homeFragment2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.deleteIcon){
            val bottomSheetDialog  = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(R.layout.dialogue_delete)
            val textViewYes=bottomSheetDialog.findViewById<TextView>(R.id.dialogueYes)
            val textViewNo=bottomSheetDialog.findViewById<TextView>(R.id.dialogueNo)
            textViewYes?.setOnClickListener {
                lifecycleScope.launch{
                    viewModel.deleteNotes(oldNotes.data.id!!)
                }
                bottomSheetDialog.dismiss()
                Toast.makeText(requireContext(),"Note Deleted Successfully!",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editFragment_to_homeFragment2)
            }
            textViewNo?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }
}