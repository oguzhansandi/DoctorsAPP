package com.example.doctors.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doctors.R
import com.example.doctors.adapter.RecyclerViewAdapter
import com.example.doctors.databinding.FragmentHomeBinding
import com.example.doctors.model.DoctorsModel
import kotlinx.coroutines.CoroutineExceptionHandler


class HomeFragment : Fragment(R.layout.fragment_home), RecyclerViewAdapter.Listener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = RecyclerViewAdapter(arrayListOf(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = recyclerViewAdapter
        binding.cvNoResult.setBackgroundResource(R.drawable.circle_background)

        viewModel.loadData()
        handleClick()
        observeLiveData()

    }


    private fun observeLiveData() {
        viewModel.doctorsLiveList.observe(viewLifecycleOwner) { doctorsLiveList ->
            if (doctorsLiveList.size > 0) {
                recyclerViewAdapter.updateList(doctorsLiveList)

            }
        }

        viewModel.isListEmpty.observe(viewLifecycleOwner) { isListEmpty ->
            if (isListEmpty) {
                binding.cvRecycler.visibility = View.GONE
                binding.cvNoResult.visibility = View.VISIBLE
            } else {
                binding.cvRecycler.visibility = View.VISIBLE
                binding.cvNoResult.visibility = View.GONE
            }

        }
    }

    private fun handleClick() {

        binding.erkek.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.kadin.isChecked = false
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "male",
                        female = false,
                        male = true,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )
            } else {
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "",
                        female = false,
                        male = false,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )


            }

        }
        binding.kadin.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.erkek.isChecked = false
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "female",
                        female = true,
                        male = false,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )
            } else {
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "",
                        female = false,
                        male = false,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )

            }

        }


        binding.searchText.addTextChangedListener { text ->
            if (binding.erkek.isChecked) {
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "male",
                        female = false,
                        male = true,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )
            } else if (binding.kadin.isChecked) {
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "female",
                        female = true,
                        male = false,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )
            } else {
                recyclerViewAdapter.updateList(
                    viewModel.filterByGenderAndListForName(
                        text = binding.searchText.text.toString(),
                        gender = "",
                        female = false,
                        male = false,
                        doctorList = viewModel.doctorsLiveList.value ?: arrayListOf()
                    )
                )

            }


        }


    }


    override fun onItemClick(doctorsModel: DoctorsModel) {
        val userStatus = viewModel.getUserStatus()
        when (doctorsModel.status) {
            "premium" ->
                if (userStatus == "premium") {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToRandevuAlFragment(doctorsModel)
                    Navigation.findNavController(requireView()).navigate(action)
                } else {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToPremiumAlFragment(doctorsModel)
                    Navigation.findNavController(requireView()).navigate(action)
                }

            "free" -> {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToRandevuAlFragment(doctorsModel)
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
    }


}