package com.example.notes.UI.Fragments


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
;
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.Model.Notes
import com.example.notes.R
import com.example.notes.UI.Activity.SignInActivity
import com.example.notes.UI.Adapter.NotesAdapter
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    lateinit var binding :FragmentHomeBinding
    private val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter : NotesAdapter
    private lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        val staggeredGridLayoutManager = (StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL))
        binding.rvShowAllNotes.layoutManager=staggeredGridLayoutManager


        //Listing all notes
        lifecycleScope.launch{
            viewModel.getNotes().observe(viewLifecycleOwner){
                oldMyNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),it)
                binding.rvShowAllNotes.adapter= adapter
            }
        }

        //Filter All
        binding.filterAll.setOnClickListener {
            binding.filterMedium.setBackgroundResource(R.drawable.select_backgound)
            binding.filterHigh.setBackgroundResource(R.drawable.select_backgound)
            binding.filterLow.setBackgroundResource(R.drawable.select_backgound)
            binding.filterAll.setBackgroundResource(R.drawable.tv_bg)
            lifecycleScope.launch {
                viewModel.getNotes().observe(viewLifecycleOwner){
                    oldMyNotes = it as ArrayList<Notes>
                    adapter = NotesAdapter(requireContext(),it)
                    binding.rvShowAllNotes.adapter=adapter
                }
            }
        }

        //Filter High
        binding.filterHigh.setOnClickListener {
            binding.filterMedium.setBackgroundResource(R.drawable.select_backgound)
            binding.filterHigh.setBackgroundResource(R.drawable.tv_bg)
            binding.filterLow.setBackgroundResource(R.drawable.select_backgound)
            binding.filterAll.setBackgroundResource(R.drawable.select_backgound)
            lifecycleScope.launch{
                viewModel.getHighNotes().observe(viewLifecycleOwner){
                    oldMyNotes = it as ArrayList<Notes>
                    adapter = NotesAdapter(requireContext(),it)
                    binding.rvShowAllNotes.adapter= adapter
                }
            }
        }

        //Filter Medium
        binding.filterMedium.setOnClickListener {
            binding.filterMedium.setBackgroundResource(R.drawable.tv_bg)
            binding.filterHigh.setBackgroundResource(R.drawable.select_backgound)
            binding.filterLow.setBackgroundResource(R.drawable.select_backgound)
            binding.filterAll.setBackgroundResource(R.drawable.select_backgound)

            lifecycleScope.launch{
                viewModel.getMediumNotes().observe(viewLifecycleOwner){
                    oldMyNotes = it as ArrayList<Notes>
                    adapter = NotesAdapter(requireContext(),it)
                    binding.rvShowAllNotes.adapter= adapter
                }
            }
        }

        //Filter Low
        binding.filterLow.setOnClickListener {
            binding.filterMedium.setBackgroundResource(R.drawable.select_backgound)
            binding.filterHigh.setBackgroundResource(R.drawable.select_backgound)
            binding.filterLow.setBackgroundResource(R.drawable.tv_bg)
            binding.filterAll.setBackgroundResource(R.drawable.select_backgound)
            lifecycleScope.launch{
                viewModel.getLowNotes().observe(viewLifecycleOwner){
                    oldMyNotes = it as ArrayList<Notes>
                    adapter = NotesAdapter(requireContext(),it)
                    binding.rvShowAllNotes.adapter= adapter
                }
            }
        }

        //Navigating to create new notes
        binding.fbCreate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment2_to_createFragment2)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_frag_menu,menu)

        val item = menu.findItem(R.id.searchIcon)
            val searchView = item.actionView as SearchView
            searchView.queryHint = "Title OR Subtitle..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    notesFiltering(newText)
                    return true
                }
            })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logoutIcon ->{
                val builder = AlertDialog.Builder(requireActivity())
                val alertDialog = builder.create()

                builder
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes"){dialogInterface,which->
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(requireContext(),SignInActivity::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton("No"){dialogInterface, which->
                        alertDialog.dismiss()
                    }
                    .show()
                    .setCancelable(false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun notesFiltering(newText: String?) {
        val newFilteredList = arrayListOf<Notes>()
        for(i in oldMyNotes){
            if(i.title.contains(newText!!) ||i.subTitle.contains(newText)){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
    }
}