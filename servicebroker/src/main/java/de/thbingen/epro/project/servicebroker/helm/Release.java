package de.thbingen.epro.project.servicebroker.helm;

import hapi.release.ReleaseOuterClass;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Release {
    @NonNull
    private ReleaseOuterClass.Release release;


    public boolean isInitialized(){
        return release.isInitialized();
    }

    public boolean hasDeleted(){
        return release.getInfo().hasDeleted();
    }
}
