package com.example.doctors.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.doctors.R
import com.example.doctors.databinding.FragmentPremiumAlBinding
import com.example.doctors.databinding.FragmentRandevuAlBinding

class PremiumAlFragment : Fragment() {
    private lateinit var binding : FragmentPremiumAlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPremiumAlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Doctors_detail
        arguments?.let {
            val doctorInfo = RandevuAlFragmentArgs.fromBundle(it).doctorsInfo
            binding.doctorsName.text = doctorInfo.name

            Glide.with(requireContext())
                .load(doctorInfo.image.url)
                .centerCrop()
                .into(binding.doctorsImage)
        }
    }

}