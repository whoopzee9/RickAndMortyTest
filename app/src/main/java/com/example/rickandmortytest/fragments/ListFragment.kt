package com.example.rickandmortytest.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortytest.R
import com.example.rickandmortytest.adapters.HeaderFooterAdapter
import com.example.rickandmortytest.adapters.RecyclerAdapter
import com.example.rickandmortytest.api.Settings
import com.example.rickandmortytest.data.Character
import com.example.rickandmortytest.databinding.ListFragmentBinding
import com.example.rickandmortytest.viewModels.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var listAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = requireActivity().getSharedPreferences(Settings.APP_PREFERENCES, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListViewModel(sharedPrefs) as T
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

        setupRecyclerView()

        binding.swSwipeRefreshLayout.setOnRefreshListener {
            listAdapter.refresh()
            binding.swSwipeRefreshLayout.isRefreshing = false
        }

        binding.fabFavourites.setOnClickListener {
            viewModel.setFavourites()
        }

        viewModel.isFavourites.observe(viewLifecycleOwner, {
            viewModel.viewModelScope.launch {
                if (it) {
                    binding.fabFavourites.setImageResource(R.drawable.ic_heart_on)
//                    viewModel.characterList.observe(viewLifecycleOwner, {
//                        println("Observe 1")
//                        val data =
//                        listAdapter.submitData(lifecycle, it.filter { it1 -> it1.isFavourite })
//                        listAdapter.notifyDataSetChanged()
//                    })
                    viewModel.characterList.collectLatest {
                        listAdapter.submitData(it.filter { it1 -> it1.isFavourite })
                        listAdapter.notifyDataSetChanged()
                    }
                } else {
                    binding.fabFavourites.setImageResource(R.drawable.ic_heart_off)
//                    viewModel.characterList.observe(viewLifecycleOwner, {
//                        println("Observe 2")
//                        listAdapter.submitData(lifecycle, it)
//                        listAdapter.notifyDataSetChanged()
//                    })
                    viewModel.characterList.collectLatest {
                        listAdapter.submitData(it)
                        listAdapter.notifyDataSetChanged()
                    }
                }
            }
        })

        return binding.root
    }

    private fun setupAdapter() {
        listAdapter = RecyclerAdapter(object: RecyclerAdapter.OnClickListener {
            override fun onItemClick(item: Character) {
                val bundle = Bundle()
                bundle.putParcelable(Settings.INFO, item)
                findNavController().navigate(R.id.action_listFragment_to_informationFragment, bundle)
            }

            override fun updateSharedPrefs(id: Int, value: Boolean) {
                sharedPrefs.edit().putBoolean(id.toString(), value).apply()
            }

            override fun showToast(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        setupAdapter()

        binding.rvRecyclerView.apply {
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                header = HeaderFooterAdapter(),
                footer = HeaderFooterAdapter()
            )
            layoutManager = LinearLayoutManager(context)
        }


        binding.rvRecyclerView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.fabFavourites.hide()
            } else {
                binding.fabFavourites.show()
            }
//            val fab = binding.fabFavourites
//            if (scrollY > oldScrollY) {
//                val fabBottomMargin = fab.layoutParams.height
//                fab.animate().translationY((2 * fab.height + fabBottomMargin).toFloat())
//                    .setInterpolator(LinearInterpolator()).start()
//            } else {
//                fab.animate().translationY(0F).setInterpolator(LinearInterpolator()).start()
//            }
        }

        listAdapter.addLoadStateListener {
            binding.rvRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.pbLoadingProgress.isVisible = it.refresh == LoadState.Loading
        }
    }

}