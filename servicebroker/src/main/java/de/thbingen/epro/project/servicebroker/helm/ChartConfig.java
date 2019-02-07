package de.thbingen.epro.project.servicebroker.helm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

@NoArgsConstructor
@Getter
public class ChartConfig {
    @NonNull
    private Map<String, Object> configMap = new HashMap<>();

    public ChartConfig(ChartBuilder chartBuilder) {
        String raw = chartBuilder.getChartBuilder().getValues().getRaw();
        this.configMap = new Yaml().load(raw);
    }

    public ChartConfig(@NonNull Map<String, Object> configMap) {
        this.configMap = configMap;
    }

    public boolean isSet(String key) {
        return contains(key);
    }

    public void set(String key, Object value){
        String valueKey = valueKey(key);
        Map<String, Object> containingMap = getContainingMap(key, true);

        containingMap.put(valueKey, value);
    }

    public Object get(String key){
        String valueKey = valueKey(key);
        Map<String, Object> containingMap = getContainingMap(key, false);

        if(containingMap == null)
            return null;

        return containingMap.get(valueKey);
    }

    public void mergeFrom(ChartConfig from){
        mergeFrom(from.configMap, configMap);
    }

    private void mergeFrom(Map<String, Object> valueMap, Map<String, Object> currentPos){
        for(Map.Entry<String, Object> entry : valueMap.entrySet()){
            String key = entry.getKey();
            Object valueOrMap = entry.getValue();

            if(valueOrMap instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) valueOrMap;

                if (currentPos.containsKey(key)){
                    Object valueOrMap2 = currentPos.get(key);

                    if(valueOrMap2 instanceof Map){
                        mergeFrom(map, (Map<String, Object>) valueOrMap2);
                    } else {
                        currentPos.put(key, map);
                    }
                } else {
                    currentPos.put(key, map);
                }
            } else {
                currentPos.put(key, valueOrMap);
            }
        }
    }


    private boolean contains(String key){
        List<String> keys = splitKey(key);

        int i = 0;
        Map<String, Object> tmp = configMap;

        while ( tmp != null && i < keys.size()) {
            String k = keys.get(i);

            if (i == keys.size() - 1){
                return tmp.containsKey(k);
            }
            else if (tmp.containsKey(k)) {
                Object valOrMap = tmp.get(k);

                if(valOrMap != null){
                    if(valOrMap instanceof Map){
                        tmp = (Map) valOrMap;
                    } else
                        return false;
                } else {
                    return false;
                }
            } else{
               return false;
            }

            i++;
        }

        return false;
    }

    private Map<String, Object> getContainingMap(String key, boolean createNewMaps){
        List<String> keys = splitKey(key);
        if(keys.size() == 0)
            return configMap;

        int i = 0;
        Map<String, Object> tmp = configMap;
        while ( tmp != null && i < keys.size() - 1) {
            String k = keys.get(i);
            if (tmp.containsKey(k)) {
                Object valOrMap = tmp.get(k);

                if(valOrMap != null){
                    if(valOrMap instanceof Map){
                        tmp = (Map) valOrMap;
                    } else{
                        if(createNewMaps){
                            Map<String, Object> newMap = new HashMap<>();
                            tmp.put(k, newMap);
                            tmp = newMap;
                        } else
                            return null;
                    }
                }
            } else{
                if(createNewMaps) {
                    Map<String, Object> newMap = new HashMap<>();
                    tmp.put(k, newMap);
                    tmp = newMap;
                } else
                    return null;
            }

            i++;
        }

        return tmp;
    }

    private List<String> splitKey(String key) {
        return new ArrayList<>(Arrays.asList(key.split("\\.")));
    }

    private String valueKey(String key){
        List<String> strings = splitKey(key);
        return strings.get(strings.size()-1);
    }


    public boolean isEmpty(){
        return configMap.entrySet().isEmpty();
    }
}
