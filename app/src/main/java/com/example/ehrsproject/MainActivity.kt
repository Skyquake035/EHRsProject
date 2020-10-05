package com.example.ehrsproject

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mChart: LineChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mChart = findViewById(R.id.chart1)
        val mv = MyMarkerView(applicationContext, R.layout.custom_marker_view)
        mv.chartView = mChart
        renderData()
    }

    fun renderData() {
        val llXAxis = LimitLine(10f, "ค่าความดันเลือด")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        val xAxis = mChart!!.xAxis
        xAxis.axisMaximum = 6f
        xAxis.axisMinimum = 0f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);

        val ll1 = LimitLine(215f, "ความดันสูง")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        val ll2 = LimitLine(70f, "ความดันต่ำ")
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
        val leftAxis = mChart!!.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll1)
        leftAxis.addLimitLine(ll2)
        leftAxis.axisMaximum = 350f
        leftAxis.axisMinimum = 0f
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
        mChart!!.axisRight.isEnabled = false
        setData()
    }

    private fun setData() {
        val values = ArrayList<Entry>()
        values.add(Entry(0F, 50F))
        values.add(Entry(1F, 100F))
        values.add(Entry(2F, 80F))
        values.add(Entry(3F, 120F))
        values.add(Entry(4F, 110F))
        values.add(Entry(5F, 150F))
        values.add(Entry(6F, 250F))

        val labels = ArrayList<String>()
        labels.add("1 ม.ค.")
        labels.add("2 ก.พ.")
        labels.add("3 ส.ค.")
        labels.add("5 ก.ย.")
        labels.add("10 ต.ค.")
        labels.add("15 พ.ย.")
        labels.add("วันนี้")
        val xAxis = mChart!!.xAxis

        xAxis.valueFormatter = object : ValueFormatter() {
            override
            fun getFormattedValue(value: Float): String {
                // value is x as index
                return labels[value.toInt()]
            }
        }

        val set1: LineDataSet
        if (mChart!!.data != null &&
                mChart!!.data.dataSetCount > 0) {
            set1 = mChart!!.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            mChart!!.data.notifyDataChanged()
            mChart!!.notifyDataSetChanged()

        } else {
            set1 = LineDataSet(values, "สถิติค่าความดันเลือด")
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)
            set1.circleSize = 6f
            set1.lineWidth = 3f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f

            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.DKGRAY
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            mChart!!.data = data
        }
    }
}