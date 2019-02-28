package de.thbingen.epro.project.servicebroker.helm;

import hapi.release.ReleaseOuterClass;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Has methods to check if installing or uninstalling via {@link HelmClient} was successful
 * Wrapper/Adapter class for {@link hapi.release.ReleaseOuterClass.Release}
 */
@RequiredArgsConstructor
@Getter
public class Release {
    @NonNull
    private ReleaseOuterClass.Release release;


    /**
     * Get if installation was successful
     * @return
     */
    public boolean isInitialized(){
        return release.isInitialized();
    }

    /**
     * Get if uninstalling was successful
     * @return
     */
    public boolean hasDeleted(){
        return release.getInfo().hasDeleted();
    }
}
