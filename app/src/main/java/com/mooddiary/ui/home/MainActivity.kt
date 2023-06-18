package com.mooddiary.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mooddiary.R
import com.mooddiary.application.MoodDiaryApplication
import com.mooddiary.databinding.ActivityMainBinding
import com.mooddiary.utils.MDViewModelProviderFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MoodDiaryListViewModel by viewModels {
        MDViewModelProviderFactory {
            MoodDiaryListViewModel(
                (application as MoodDiaryApplication).moodDiaryRepository
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildRecyclerView()

        setContentView(binding.root)
    }

    private fun buildRecyclerView() {
        val adapter = MoodDiaryListAdapter()
        binding.moodDiaryList.adapter = adapter
        binding.moodDiaryList.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.moodDiaryList.isNotEmpty()) {
                        adapter.submitList(it.moodDiaryList)
                        diaryLoadSuccess()
                    } else if (it.isLoading) {
                        diaryLoading()
                    } else if (it.errorMessage != null) {
                        diaryError(it.errorMessage)
                    }
                }
            }
        }
    }

    private fun diaryLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
    }

    private fun diaryError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.text = errorMessage
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun diaryLoadSuccess() {
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
    }
}