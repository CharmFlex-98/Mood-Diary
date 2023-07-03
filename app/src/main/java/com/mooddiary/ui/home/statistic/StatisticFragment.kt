package com.mooddiary.ui.home.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mooddiary.application.MoodDiaryApplication
import com.mooddiary.application.getMoodDiaryApplication
import com.mooddiary.databinding.StatisticBinding
import com.mooddiary.utils.ChartEntry
import com.mooddiary.utils.MDViewModelProviderFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class StatisticFragment : Fragment() {
    private lateinit var binding: StatisticBinding
    private val viewModel: StatisticViewModel by viewModels {
        MDViewModelProviderFactory { StatisticViewModel((requireActivity().getMoodDiaryApplication().moodDiaryRepository)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateChart(it.chartData)
                }
            }
        }
    }

    private fun updateChart(chartData: List<ChartEntry>) {
        val lineData = LineData(LineDataSet(chartData, "Your Mood"))
        binding.lineChartView.apply {
            data = lineData
            xAxis.valueFormatter = MDChartValueFormatter()
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            invalidate()
        }
    }
}