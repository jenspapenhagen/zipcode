package de.papenhagen.enities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CSVInput {

    public final long zipCode;
    public final String state;
    public final double lon;
    public final double lat;
}
