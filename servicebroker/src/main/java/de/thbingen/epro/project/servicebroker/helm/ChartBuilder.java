package de.thbingen.epro.project.servicebroker.helm;

import hapi.chart.ChartOuterClass;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChartBuilder {
    @NonNull
    private final ChartOuterClass.Chart.Builder chartBuilder;
}
