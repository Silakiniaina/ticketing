package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.dash.mvc.annotation.Numeric;
import mg.dash.mvc.annotation.Required;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightArg {

    @Numeric
    private int id;

    @Required
    @Numeric
    private int planeId;

    @Required
    @Numeric
    private int departureCityId;

    @Required
    @Numeric
    private int arrivalCityId;

    @Required
    private String departureDatetime;

    @Required
    private String arrivalDatetime;
}