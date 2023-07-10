package com.sportybets.sport.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sportybets.sport.R
import com.sportybets.sport.databinding.FragmentStartBinding
import com.sportybets.sport.utils.KEY_INTENT
import com.sportybets.sport.utils.LoadScreenSettings

class StartFragment : Fragment() {

    private var mBinding: FragmentStartBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(activity?.intent?.extras?.getSerializable(KEY_INTENT) as LoadScreenSettings){
            LoadScreenSettings.WEB -> findNavController().navigate(R.id.action_startFragment_to_webFragment)
            LoadScreenSettings.ERROR -> findNavController().navigate(R.id.action_startFragment_to_noConnectionFragment)
            else -> {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.apply {
            first.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("details", 1)
                findNavController().navigate(R.id.action_startFragment_to_detailsFragment, bundle)
            }
            second.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("details", 2)
                findNavController().navigate(R.id.action_startFragment_to_detailsFragment, bundle)
            }
            third.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("details", 3)
                findNavController().navigate(R.id.action_startFragment_to_detailsFragment, bundle)
            }
            forth.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("details", 4)
                findNavController().navigate(R.id.action_startFragment_to_detailsFragment, bundle)
            }
            fifth.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("details", 5)
                findNavController().navigate(R.id.action_startFragment_to_detailsFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}