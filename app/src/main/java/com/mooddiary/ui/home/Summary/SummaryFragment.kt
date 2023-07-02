package com.mooddiary.ui.home.Summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mooddiary.application.MoodDiaryApplication
import com.mooddiary.databinding.HomeBinding
import com.mooddiary.databinding.SummaryBinding
import com.mooddiary.ui.home.new_edit_page.NewEditFragment
import com.mooddiary.utils.MDViewModelProviderFactory
import kotlinx.coroutines.launch

class SummaryFragment: Fragment() {

    private lateinit var _binding: SummaryBinding
    private val viewModel: MoodDiaryListViewModel by viewModels {
        MDViewModelProviderFactory {
            MoodDiaryListViewModel(
                (requireActivity().application as MoodDiaryApplication).moodDiaryRepository
            )
        }
    }
    private var _adapter: MoodDiaryListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SummaryBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        _adapter = MoodDiaryListAdapter { adapter, pos ->
            onDiaryClick(adapter, pos) }
        _binding.moodDiaryList.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(activity)
        }
        _binding.moodDiaryList.adapter = _adapter
        _binding.moodDiaryList.layoutManager = LinearLayoutManager(activity)
        _binding.fab.setOnClickListener {
            NewEditFragment().show(childFragmentManager, NewEditFragment.id)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.isLoading) {
                        diaryLoading()
                    } else if (it.errorMessage != null) {
                        diaryError(it.errorMessage)
                    } else if (it.moodDiaryList.isEmpty()) {
                        diaryError("No data")
                    } else {
                        _adapter!!.submitList(it.moodDiaryList)
                        diaryLoadSuccess()
                    }
                }
            }
        }
    }

    private fun diaryLoading() {
        _binding.progressBar.visibility = View.VISIBLE
        _binding.errorMessage.visibility = View.GONE
    }

    private fun diaryError(errorMessage: String) {
        _binding.moodDiaryList.visibility = View.GONE
        _binding.progressBar.visibility = View.GONE
        _binding.errorMessage.text = errorMessage
        _binding.errorMessage.visibility = View.VISIBLE
    }

    private fun diaryLoadSuccess() {
        _binding.moodDiaryList.visibility = View.VISIBLE
        _binding.progressBar.visibility = View.GONE
        _binding.errorMessage.visibility = View.GONE
    }

    private fun onDiaryClick(adapter: MoodDiaryListAdapter, position: Int) {
        NewEditFragment.instance(adapter.currentList[position].id!!)
            .show(childFragmentManager, NewEditFragment.id)
    }
}