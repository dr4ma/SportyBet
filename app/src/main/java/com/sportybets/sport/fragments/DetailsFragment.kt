package com.sportybets.sport.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sportybets.sport.R
import com.sportybets.sport.databinding.FragmentDetailsBinding
import com.sportybets.sport.databinding.FragmentStartBinding

class DetailsFragment : Fragment() {

    private var mBinding: FragmentDetailsBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.apply {
            when(arguments?.getInt("details") as Int){
                1 -> {
                    header.background = ContextCompat.getDrawable(requireContext(), R.drawable.first)
                    firstStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.first_1)
                    secondStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.first_2)
                }
                2 -> {
                    header.background = ContextCompat.getDrawable(requireContext(), R.drawable.second)
                    firstStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.second_1)
                    secondStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.second_2)
                }
                3 -> {
                    header.background = ContextCompat.getDrawable(requireContext(), R.drawable.third)
                    firstStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.third_1)
                    secondStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.third_2)
                }
                4 -> {
                    header.background = ContextCompat.getDrawable(requireContext(), R.drawable.forth)
                    firstStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.forth_1)
                    secondStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.forth_2)
                }
                5 -> {
                    header.background = ContextCompat.getDrawable(requireContext(), R.drawable.fifth)
                    firstStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.fifth_1)
                    secondStat.background = ContextCompat.getDrawable(requireContext(), R.drawable.fifth_2)
                }
            }
        }
    }
}