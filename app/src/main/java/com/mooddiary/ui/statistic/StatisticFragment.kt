package com.mooddiary.ui.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mooddiary.databinding.StatisticBinding

class StatisticFragment: Fragment() {
    private lateinit var _binding: StatisticBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StatisticBinding.inflate(inflater, container, false)
        return _binding.root
    }
}