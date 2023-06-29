package com.mooddiary.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mooddiary.R
import com.mooddiary.databinding.CustomValueSelectorBinding
import com.mooddiary.utils.OnValueChangedListener

class ValueSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_MIN_VAL = 0f
        const val DEFAULT_MAX_VAL = 0f
        const val DEFAULT_STEP = 0f
        const val DEFAULT_VAL = 3f
    }

    private var binding: CustomValueSelectorBinding
    private var minValue = DEFAULT_MIN_VAL
    private var maxValue = DEFAULT_MAX_VAL
    private var step = DEFAULT_STEP
    private var value = DEFAULT_VAL
        set(value) {
            field = value
            binding.valueViewer.text = value.toString()
            invalidateView()
        }
    private var _valueChangedListener: OnValueChangedListener<Float>? = null

    init {
        binding = CustomValueSelectorBinding.inflate(LayoutInflater.from(context))
            .also { addView(it.root) }
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ValueSelector, 0, 0)
        println("the typed array is $typedArray")
        typedArray.apply {
            try {
                minValue = getFloat(R.styleable.ValueSelector_minValue, DEFAULT_MIN_VAL)
                maxValue = getFloat(R.styleable.ValueSelector_maxValue, DEFAULT_MAX_VAL)
                step = getFloat(R.styleable.ValueSelector_step, DEFAULT_STEP)
            } finally {
                recycle()
            }
        }

        setupViews()
    }

    private fun setupViews() {
        binding.valueSelectorAdder.setOnClickListener {
            if (value + step <= maxValue) {
                value += step
                binding.valueViewer.text = value.toString()
                _valueChangedListener?.let { it(value) }
            }
        }
        binding.valueSelectorRemover.setOnClickListener {
            if (value - step >= minValue) {
                value -= step
                binding.valueViewer.text = value.toString()
                _valueChangedListener?.let { it(value) }
            }
        }
    }

    fun setOnValueChangedListener(listener: OnValueChangedListener<Float>) {
        _valueChangedListener = listener
    }

    fun setDefaultValue(defaultValue: Float) {
        value = defaultValue
    }

    private fun invalidateView() {
        invalidate()
        requestLayout()
    }
}