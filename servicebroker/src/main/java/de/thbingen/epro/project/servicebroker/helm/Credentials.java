package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Secret;
import lombok.Data;
import lombok.NonNull;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Data
public class Credentials {

    @NonNull
    private Secret secret;

    public String getPassword(String key) {

        Map<String, String> data = secret.getData();
        String passwordEncoded = data.get(key);
        String passwordDecoded = new String(
                Base64.getDecoder().decode(passwordEncoded)
        );

        return passwordDecoded;
    }

    public Map<String, String> getData() {
        Map<String, String> decodedMap = new HashMap<>();
        secret.getData().entrySet().stream()
                .forEach(entry -> decodedMap.put(entry.getKey(), new String(Base64.getDecoder().decode(entry.getValue()))));
        return decodedMap;
    }
}
