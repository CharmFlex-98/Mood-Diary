package com.mooddiary.ui.home.new_edit_page

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mooddiary.R
import com.mooddiary.application.MoodDiaryApplication
import com.mooddiary.databinding.NewEditMoodDiaryBinding
import com.mooddiary.utils.MDViewModelProviderFactory
import com.mooddiary.utils.toFormattedString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class NewEditFragment : DialogFragment() {
    companion object {
        const val id = "New Edit Fragment"
        const val argumentName = "diaryId"
        fun instance(diaryId: Long): NewEditFragment {
            val res = NewEditFragment()
            val bundle = bundleOf(Pair(argumentName, diaryId))
            res.arguments = bundle

            return res
        }
    }
    private lateinit var _binding: NewEditMoodDiaryBinding
    private val _viewModel: NewEditViewModel by viewModels {
        MDViewModelProviderFactory {
            NewEditViewModel((activity?.application as MoodDiaryApplication).moodDiaryRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewEditMoodDiaryBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.newEditToolbar.setNavigationOnClickListener {
            dismiss()
        }
        _binding.titleField.addTextChangedListener(NewEditTextWatcher { _viewModel.setTitle(it) })
        _binding.contentField.addTextChangedListener(NewEditTextWatcher { _viewModel.setContent(it) })
        _binding.moodValueSelector.setOnValueChangedListener {
            _viewModel.setMoodRating(it)
        }
        _binding.submitButton.setOnClickListener {
            _viewModel.createDiary()
            dismiss()
        }

        if (isEdit()) populateInitialData()
        initStateChangedListeners()
    }

    private fun initStateChangedListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.uiState.collect {
                    val currentTime = LocalDate.now()
                    _binding.dateField.text = (it.date ?: currentTime).toFormattedString()
                }
            }
        }
    }

    private fun isEdit(): Boolean {
        return arguments != null
    }

    private fun populateInitialData() {
        val diaryId = requireArguments().getLong(argumentName)
        _viewModel.setDiaryData(diaryId)
    }

    private class NewEditTextWatcher(private val onTextChangedCB: (String) -> Unit) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            println("text changed")
            onTextChangedCB(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
            return
        }

    }
}