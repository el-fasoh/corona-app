package com.fasoh.corona.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fasoh.corona.MainActivity
import com.fasoh.corona.MoodArrayAdapter
import com.fasoh.corona.R
import com.fasoh.corona.databinding.FragmentDashboardBinding
import com.fasoh.corona.models.timeline.TimelineDataItem
import com.fasoh.corona.repositories.SettingsRepository
import com.fasoh.corona.ui.MyMarkerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.heetch.countrypicker.Country
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*


class DashboardFragment : Fragment() {

    private val dashboardViewModel by viewModel<DashboardViewModel>()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var chart: LineChart
    private lateinit var activity: MainActivity
    private val settingsRepository: SettingsRepository by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        dashboardViewModel.setup(lifecycle)

        setupChart()

        setupMonthSpinner()

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        dashboardViewModel.timeline.observe(viewLifecycleOwner, Observer {
            binding.chart.invalidate()
            setupAxis(it.maximum, it.minimum)
            setupChartData(it.items)
            Timber.d("Record size: ${it.items.size} | max: ${it.maximum} | minimum: ${it.minimum}")
        })
    }

    private fun setupChartData(data: List<TimelineDataItem>) {
        val values = ArrayList<Entry>()
        data.forEach {
            val input = when {
                it.totalCases.isEmpty() -> 0f
                else -> it.totalCases.toFloat()
            }
            values.add(Entry(it.date!!.split("/")[1].toFloat(), input))
        }

        val lineDataSet: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            lineDataSet = chart.data.getDataSetByIndex(0) as LineDataSet
            lineDataSet.values = values
            lineDataSet.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            lineDataSet = LineDataSet(values, "-- Infections")
            lineDataSet.setDrawIcons(false)
            lineDataSet.enableDashedLine(10f, 5f, 0f)

            lineDataSet.color = Color.BLACK
            lineDataSet.setCircleColor(Color.BLACK)

            lineDataSet.lineWidth = 1f
            lineDataSet.circleRadius = 3f

            lineDataSet.setDrawCircleHole(false)

            lineDataSet.formLineWidth = 1f
            lineDataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            lineDataSet.formSize = 15f

            lineDataSet.valueTextSize = 9f

            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f)

            lineDataSet.setDrawFilled(true)
            lineDataSet.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable =
                    ContextCompat.getDrawable(activity, R.drawable.fade_red)
                lineDataSet.fillDrawable = drawable
            } else {
                lineDataSet.fillColor = Color.BLACK
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataSet) // add the data sets

            val lineData = LineData(dataSets)

            binding.chart.data = lineData
            Timber.e("Chat set")
        }
    }

    private fun setupMonthSpinner() {
        val monthAdapter = ArrayAdapter(
            activity,
            R.layout.spinner_item, getMonths()
        )
        val items = com.heetch.countrypicker.Utils.parseCountries(
            com.heetch.countrypicker.Utils.getCountriesJSON(activity)
        )

        val countryAdapter = MoodArrayAdapter(activity.applicationContext, items)

        binding.month.adapter = monthAdapter
        binding.month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(
                adapter: AdapterView<*>,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                dashboardViewModel.getCountryData(
                    position + 1,
                    (binding.country.selectedItem as Country).isoCode
                )
            }

        }
        binding.month.setSelection(Calendar.getInstance().get(Calendar.MONTH))

        binding.country.adapter = countryAdapter
        binding.country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(
                adapter: AdapterView<*>,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                dashboardViewModel.getCountryData(
                    binding.month.selectedItemPosition + 1,
                    (adapter.selectedItem as Country).isoCode
                )
            }
        }
        var position = -1
        for(i in 0 until items.size){
            if(items[i].isoCode == settingsRepository.defaultCountry){
                position = i
                break
            }
        }

        binding.country.post {
            binding.country.setSelection(position, true)
        }

    }

    private fun getMonths(): List<String> {
        val monthNames = arrayOf(
            "JANUARY",
            "FEBRUARY",
            "MARCH",
            "APRIL",
            "MAY",
            "JUNE",
            "JULY",
            "AUGUST",
            "SEPTEMBER",
            "OCTOBER",
            "NOVEMBER",
            "DECEMBER"
        )
        return monthNames.take(Calendar.getInstance().get(Calendar.MONTH) + 1)
    }

    private fun setupAxis(maximum: Int, minimum: Int) {
        val xAxis = chart.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f)

        val yAxis = chart.axisLeft
        chart.axisRight.isEnabled = false

        yAxis.enableGridDashedLine(10f, 10f, 0f)

        yAxis.mAxisMaximum = (maximum + 1000).toFloat()

        when {
            minimum > 100 -> yAxis.mAxisMinimum = (minimum - 1000).toFloat()
            else -> yAxis.mAxisMinimum = 0f
        }

        chart.animateX(1500)

        val l = chart.legend

        l.form = LegendForm.LINE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    private fun setupChart() {
        chart = binding.chart

        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false

        chart.setTouchEnabled(true)

        chart.setDrawGridBackground(false)

        val mv = MyMarkerView(activity, R.layout.custom_marker_view)
        mv.chartView = chart
        chart.marker = mv

        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        chart.setPinchZoom(true)

    }
}