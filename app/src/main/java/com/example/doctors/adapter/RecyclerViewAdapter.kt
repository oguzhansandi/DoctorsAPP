package com.example.doctors.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctors.R
import com.example.doctors.databinding.DoctorsItemLayoutBinding
import com.example.doctors.model.DoctorsModel

class RecyclerViewAdapter(private val doctorsList : ArrayList<DoctorsModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    private var filteredList = ArrayList(doctorsList)

    interface Listener{
        fun onItemClick(doctorsModel: DoctorsModel)
    }

    class RowHolder(val binding: DoctorsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(doctorsModel: DoctorsModel, position: Int, listener : Listener){
            itemView.setOnClickListener {
                listener.onItemClick(doctorsModel)
            }

            val url = doctorsModel.image.url
            Glide
                .with(itemView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.doctorImage);

            binding.doctorName.text = doctorsModel.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = DoctorsItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(filteredList[position], position, listener)
    }




    fun updateList(newDoctorList :ArrayList<DoctorsModel>){
        filteredList.clear()
        filteredList.addAll(newDoctorList)
        notifyDataSetChanged()
    }

}