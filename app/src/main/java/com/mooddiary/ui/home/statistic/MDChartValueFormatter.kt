package com.mooddiary.ui.home.statistic

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mooddiary.utils.toFormattedString
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class MDChartValueFormatter: ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        if (axis is XAxis) {
            println("the value is $value")
            val date = LocalDate.ofEpochDay(value.toLong())
            return date.toFormattedString()
        }

        return super.getAxisLabel(value, axis)
    }

}