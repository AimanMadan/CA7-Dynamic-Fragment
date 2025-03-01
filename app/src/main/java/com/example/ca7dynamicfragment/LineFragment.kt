package com.example.ca7dynamicfragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androidplot.util.PixelUtils
import com.androidplot.xy.BoundaryMode
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYPlot
import com.androidplot.xy.XYSeries
import com.androidplot.xy.StepMode
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition

class LineFragment : Fragment(R.layout.fragment_line) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Get reference to the XYPlot in fragment_line.xml
        val plot = view.findViewById<XYPlot>(R.id.lineGraphPlot)

        // 2) Domain labels (x-axis labels)
        val domainLabels = listOf("1", "2", "3", "4")

        // 3) data values for each series
        val aValues = listOf(4.3, 2.5, 3.5, 4.5)
        val bValues = listOf(2.4, 4.4, 1.8, 2.8)
        val cValues = listOf(2.0, 2.0, 3.0, 5.0)

        // 4) Create XYSeries
        val seriesA: XYSeries = SimpleXYSeries(aValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "a")
        val seriesB: XYSeries = SimpleXYSeries(bValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "b")
        val seriesC: XYSeries = SimpleXYSeries(cValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "c")

        // 5) Format each series with its own color
        val formatterA = LineAndPointFormatter(Color.RED, Color.RED, null, null)
        val formatterB = LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null)
        val formatterC = LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null)

        // 6) Add series to the plot
        plot.addSeries(seriesA, formatterA)
        plot.addSeries(seriesB, formatterB)
        plot.addSeries(seriesC, formatterC)

        // 7) Set the step size to match the number of domain labels
        plot.setDomainStep(StepMode.SUBDIVIDE, domainLabels.size.toDouble())

        // 8) Format bottom (domain) labels so they show 1,2,3,4
        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
            override fun format(
                source: Any?,
                toAppendTo: StringBuffer?,
                pos: FieldPosition?
            ): StringBuffer {
                val i = (source as Number).toInt()
                // Make sure we don’t go out of bounds
                if (i >= 0 && i < domainLabels.size) {
                    toAppendTo?.append(domainLabels[i])
                }
                return toAppendTo!!
            }

            override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                return null // Not used
            }
        }

        // 9) Adjust the range boundaries (y-axis) so everything fits nicely
        plot.setRangeBoundaries(0, 6, BoundaryMode.FIXED)

        // (Optional) Tweak line thickness or point size
        PixelUtils.init(context)
        formatterA.linePaint.strokeWidth = PixelUtils.dpToPix(2f)
        formatterB.linePaint.strokeWidth = PixelUtils.dpToPix(2f)
        formatterC.linePaint.strokeWidth = PixelUtils.dpToPix(2f)
    }
}
