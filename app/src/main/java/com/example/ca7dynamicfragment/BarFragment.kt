package com.example.ca7dynamicfragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androidplot.util.PixelUtils
import com.androidplot.xy.BarFormatter
import com.androidplot.xy.BarRenderer
import com.androidplot.xy.BoundaryMode
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import com.androidplot.xy.StepMode
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition

class BarFragment : Fragment(R.layout.fragment_bar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Get reference to the XYPlot in fragment_bar.xml
        val plot = view.findViewById<XYPlot>(R.id.BarGraphPlot)

        // 2) Domain labels
        val domainLabels = listOf("1", "2", "3", "4")

        // 3) Our data
        val aValues = listOf(4.3, 2.5, 3.5, 4.5)
        val bValues = listOf(2.4, 4.4, 1.8, 2.8)
        val cValues = listOf(2.0, 2.0, 3.0, 5.0)

        // 4) Create XYSeries
        val seriesA: XYSeries = SimpleXYSeries(aValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "a")
        val seriesB: XYSeries = SimpleXYSeries(bValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "b")
        val seriesC: XYSeries = SimpleXYSeries(cValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "c")

        // 5) Create BarFormatters for each series
        val formatterA = BarFormatter(Color.RED, Color.DKGRAY)
        val formatterB = BarFormatter(Color.BLUE, Color.DKGRAY)
        val formatterC = BarFormatter(Color.GREEN, Color.DKGRAY)

        // 6) Add each series with its formatter
        plot.addSeries(seriesA, formatterA)
        plot.addSeries(seriesB, formatterB)
        plot.addSeries(seriesC, formatterC)

        // 7) Get the BarRenderer and adjust bar group width if desired
        val barRenderer = plot.getRenderer(BarRenderer::class.java)
        barRenderer?.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(30f))

        // 8) Set the domain step and label format
        plot.setDomainStep(StepMode.SUBDIVIDE, domainLabels.size.toDouble())

        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
            override fun format(
                source: Any?,
                toAppendTo: StringBuffer?,
                pos: FieldPosition?
            ): StringBuffer {
                val i = (source as Number).toInt()
                if (i >= 0 && i < domainLabels.size) {
                    toAppendTo?.append(domainLabels[i])
                }
                return toAppendTo!!
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null
            }
        }

        // 9) Adjust the range boundaries
        plot.setRangeBoundaries(0, 6, BoundaryMode.FIXED)
    }
}
