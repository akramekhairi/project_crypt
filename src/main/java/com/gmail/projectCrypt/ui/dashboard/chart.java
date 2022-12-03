package com.gmail.projectCrypt.ui.dashboard;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.gmail.projectCrypt.backend.cryptData.SQLConnector;
import com.vaadin.flow.component.html.Div;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class chart extends Div {

    public chart(AtomicReference<String> symbol) throws SQLException {


        Object[] arr = new Object[1];
        String[] dates = new String[1];
        SQLConnector connect = new SQLConnector();
        Statement stmt = connect.SQLConnection();
        //The value from the search bar whether it is a coin's name or symbol is searched using this cross-table query
        /*String extract = "SELECT historical_data.price, historical_data.date FROM historical_data " +
                "INNER JOIN ON coins.symbol = historical_data.symbol" +
                " WHERE historical_data.symbol = '"+symbol+"' OR coins.name = '"+symbol+"'"; */
        String extract = "SELECT price, date FROM historical_data WHERE symbol = '"+symbol+"'";
        ResultSet rs = stmt.executeQuery(extract);
        int a = 0;
        while (rs.next()){
            /*Adding to arrays from resultset*/
            arr[a] = rs.getDouble("price");
            dates[a] = rs.getString("date");
            /* appending to array */
            arr = Arrays.copyOf(arr, arr.length + 1);
            dates = Arrays.copyOf(dates, dates.length +1);
            a++;
        }
        //Adding arrays to series to add to the area chart
        arr = Arrays.copyOf(arr, arr.length - 1);
        dates = Arrays.copyOf(dates, dates.length -1);
        Series s = new Series(String.valueOf(symbol).toUpperCase());
        s.setData(arr);
        //Configuring areachart and all its settings
        ApexCharts areaChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.area)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(false)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
                .withSeries(s)
                .withTitle(TitleSubtitleBuilder.get()
                        .withText(symbol.toString().toUpperCase()+" Graph")
                        .withAlign(Align.left).build())
                .withSubtitle(TitleSubtitleBuilder.get()
                        .withText("Price Movements")
                        .withAlign(Align.left).build())
                .withLabels(dates)
                .withXaxis(XAxisBuilder.get().withType(XAxisType.datetime).build())
                .withYaxis(YAxisBuilder.get()
                        .withOpposite(true).withMin(0).build())
                .withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build())
                .build();
        //add the areachart to be added to the view
        add(areaChart);
        setWidth("100%");
    }
}




