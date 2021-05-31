package com.example.rickandmortytest.fragments

import android.content.Context
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortytest.R
import com.example.rickandmortytest.adapters.HeaderFooterAdapter
import com.example.rickandmortytest.adapters.RecyclerAdapter
import com.example.rickandmortytest.api.NetworkService
import com.example.rickandmortytest.databinding.ListFragmentBinding
import com.example.rickandmortytest.viewModels.ListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        val listAdapter = RecyclerAdapter(object: RecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                //Todo handle
            }
        })

        binding.rvRecyclerView.apply {
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                header = HeaderFooterAdapter(),
                footer = HeaderFooterAdapter()
            )
            layoutManager = LinearLayoutManager(context)
        }

        listAdapter.addLoadStateListener {
            binding.rvRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.pbLoadingProgress.isVisible = it.refresh == LoadState.Loading
        }

        lifecycleScope.launch {
            viewModel.characterList.collect {
                listAdapter.submitData(it)
            }
        }

//        binding.fabFavourites.setOnClickListener {
//            viewModel.setFavourites()
//        }
//
//        viewModel.isFavouritesLiveData().observe(viewLifecycleOwner, {
//            if (it) {
//                binding.fabFavourites.setImageResource(R.drawable.ic_heart_on)
//                listAdapter.values = listAdapter.values.filter { it1 -> it1.isFavourite }
//                listAdapter.notifyDataSetChanged()
//            } else {
//                binding.fabFavourites.setImageResource(R.drawable.ic_heart_off)
//                viewModel.getCharactersListLiveData().value?.let { list -> listAdapter.values = list }
//                listAdapter.notifyDataSetChanged()
//            }
//
//        })
//
//        viewModel.getCharactersListLiveData().observe(viewLifecycleOwner, {
//            listAdapter.values = it
//            listAdapter.notifyDataSetChanged()
//        })
//

        val APP_PREFERENCES = "RickAndMortyPrefs"
        val sharedPrefs = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

    }

}