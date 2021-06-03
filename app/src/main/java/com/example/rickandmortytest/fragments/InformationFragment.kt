package com.example.rickandmortytest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.rickandmortytest.R
import com.example.rickandmortytest.api.Settings
import com.example.rickandmortytest.data.Character
import com.example.rickandmortytest.databinding.InformationFragmentBinding
import com.example.rickandmortytest.viewModels.InformationViewModel

class InformationFragment : Fragment() {

    private lateinit var viewModel: InformationViewModel
    private lateinit var binding: InformationFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return InformationViewModel() as T
            }
        }).get(InformationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.information_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.informationViewModel = viewModel


        viewModel.characterLiveData.observe(viewLifecycleOwner) {
            binding.infoIvCharacterImage.load(it.image)
        }

        val character: Character? = arguments?.getParcelable(Settings.INFO)
        viewModel.setCharacterInfo(character)

        return binding.root
    }

}