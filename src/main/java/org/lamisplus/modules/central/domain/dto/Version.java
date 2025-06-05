package org.lamisplus.modules.central.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
@Data
@Builder
public class Version implements Comparable<Version> {
    private Integer major;
    private Integer minor;
    private Integer patch;
    private static final Comparator<Version> COMP = Comparator
            .comparingInt(Version::getMajor)
            .thenComparingInt(Version::getMinor)
            .thenComparingInt(Version::getPatch);
    @Override
    public int compareTo(@NotNull Version otherVersion) {
        return COMP.compare(this, otherVersion);
    }


    public static Version createVersion (String versionAsString){
        String[] versionAsStringSplit = versionAsString.split("\\.");
        return Version.builder()
                .major(Integer.valueOf(versionAsStringSplit[0]))
                .minor(Integer.valueOf(versionAsStringSplit[1]))
                .patch(Integer.valueOf(versionAsStringSplit[2]))
                .build();
    }

}

