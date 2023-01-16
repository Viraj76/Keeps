package com.example.notes.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.notes.R
import com.example.notes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding :FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        binding.fbCreate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment2_to_createFragment2)
        }





        return binding.root
    }
}