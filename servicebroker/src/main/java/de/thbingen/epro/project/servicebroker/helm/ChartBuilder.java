package de.thbingen.epro.project.servicebroker.helm;

import hapi.chart.ChartOuterClass;
import hapi.chart.ConfigOuterClass;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * Wrapper/Adapter class for {@link hapi.chart.ChartOuterClass.Chart.Builder}.
 * ChartBuilder are helmcharts that can be configured with a {@link ChartConfig} and installed via {@link HelmClient}
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class ChartBuilder {
    @NonNull
    private final ChartOuterClass.Chart.Builder chartBuilder;

    public ChartConfig getChartConfig() {
        String raw = chartBuilder.getValues().getRaw();
        Map<String, Object> confMap = new Yaml().load(raw);

        return new ChartConfig(confMap);
    }

    public void setChartConfig(ChartConfig chartConfig) {
        String raw = new Yaml().dump(chartConfig.getConfigMap());
        ConfigOuterClass.Config conf = ConfigOuterClass.Config.newBuilder().setRaw(raw).build();

        chartBuilder.setValues(conf);
    }
}
