package de.thbingen.epro.project.servicebroker.helm;

import io.fabric8.kubernetes.api.model.Secret;
import lombok.Data;
import lombok.NonNull;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Storing Kubernetes Secret for specified Service.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@Data
public class Credentials {

    @NonNull
    private Secret secret;

    /**
     * Returning Base64 decoded Password.
     * @param key Name of Password
     * @return Base64 decoded Password
     */
    public String getPassword(String key) {

        Map<String, String> data = secret.getData();
        String passwordEncoded = data.get(key);
        String passwordDecoded = new String(
                Base64.getDecoder().decode(passwordEncoded)
        );

        return passwordDecoded;
    }

    /**
     * Returning Base64 decoded Data Map.
     * @return Base64 decoded Map
     */
    public Map<String, String> getData() {
        Map<String, String> decodedMap = new HashMap<>();
        secret.getData().entrySet().stream()
                .forEach(entry -> decodedMap.put(entry.getKey(), new String(Base64.getDecoder().decode(entry.getValue()))));
        return decodedMap;
    }
}
