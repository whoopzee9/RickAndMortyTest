package com.example.rickandmortytest.fragments

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortytest.R
import com.example.rickandmortytest.adapters.RecyclerAdapter
import com.example.rickandmortytest.databinding.ListFragmentBinding
import com.example.rickandmortytest.viewModels.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListViewModel() as T
            }
        }).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.listViewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

//        val rick = Character("Rick", "alive", "Human", "Earth", "male", Uri.EMPTY)
//        val morty = Character("Morty", "alive", "Human", "Earth", "male", Uri.EMPTY)
        val adapter = RecyclerAdapter(ArrayList(), object: RecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                //Todo handle
            }
        })
        binding.rvRecyclerView.adapter = adapter
        binding.rvRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.fabFavourites.setOnClickListener {
            viewModel.setFavourites()
        }

        viewModel.isFavouritesLiveData().observe(viewLifecycleOwner, {
            if (it) {
                binding.fabFavourites.setImageResource(R.drawable.ic_heart_on)
            } else {
                binding.fabFavourites.setImageResource(R.drawable.ic_heart_off)
            }

        })

        viewModel.getCharactersListLiveData().observe(viewLifecycleOwner, {
            adapter.values = it
            adapter.notifyDataSetChanged()
        })

        viewModel.isLoadingLiveData().observe(viewLifecycleOwner, {
            if (it) {
                binding.pbLoadingProgress.visibility = View.VISIBLE
            } else {
                binding.pbLoadingProgress.visibility = View.GONE
            }
        })
    }

}