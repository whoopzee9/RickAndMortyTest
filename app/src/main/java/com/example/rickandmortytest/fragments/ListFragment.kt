package com.example.rickandmortytest.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortytest.R
import com.example.rickandmortytest.adapters.HeaderFooterAdapter
import com.example.rickandmortytest.adapters.RecyclerAdapter
import com.example.rickandmortytest.api.NetworkService
import com.example.rickandmortytest.api.Settings
import com.example.rickandmortytest.data.Character
import com.example.rickandmortytest.databinding.ListFragmentBinding
import com.example.rickandmortytest.viewModels.ListViewModel
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var listAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkService.instance.initDatabase(requireContext())
        sharedPrefs =
            requireActivity().getSharedPreferences(Settings.APP_PREFERENCES, Context.MODE_PRIVATE)
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
            lifecycleScope.launch {
                if (it) {
                    binding.fabFavourites.setImageResource(R.drawable.ic_heart_on)
                    viewModel.characterList.observe(viewLifecycleOwner, {
                        listAdapter.submitData(lifecycle, it.filter { it1 -> it1.isFavourite })
                        listAdapter.notifyDataSetChanged()
                    })
                } else {
                    binding.fabFavourites.setImageResource(R.drawable.ic_heart_off)
                    viewModel.characterList.observe(viewLifecycleOwner, {
                        listAdapter.submitData(lifecycle, it)
                        listAdapter.notifyDataSetChanged()
                    })
                }
            }
        })

        return binding.root
    }

    private fun setupAdapter() {
        listAdapter = RecyclerAdapter(object : RecyclerAdapter.EventHandler {
            override fun onItemClick(item: Character) {
                val bundle = Bundle()
                bundle.putParcelable(Settings.INFO, item)
                findNavController().navigate(
                    R.id.action_listFragment_to_informationFragment,
                    bundle
                )
            }

            override fun updateSharedPrefs(id: Int, value: Boolean) {
                sharedPrefs.edit().putBoolean(id.toString(), value).apply()
            }

            override fun deleteFavourite(position: Int) {
                if (viewModel.isFavourites.value == true) {
                    listAdapter.notifyItemRemoved(position)
                }
            }
        })

        listAdapter.addLoadStateListener {
            binding.rvRecyclerView.isVisible = it.refresh != LoadState.Loading
            binding.pbLoadingProgress.isVisible = it.refresh == LoadState.Loading
        }
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
        }
    }

}