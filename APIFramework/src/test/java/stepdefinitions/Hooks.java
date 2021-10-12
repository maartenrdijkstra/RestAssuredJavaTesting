package stepdefinitions;


import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        // execute this code only when place_id is null
        StepDefinition m = new StepDefinition();
        if (StepDefinition.placeId == null) {
            m.addPlacePayloadWith("Shetty", "French", "Asia");
            m.user_calls_with_post_http_request("ADD_PLACE_API", "POST");
            m.verifyWhetherPlace_IdMapsToUsing("Shetty", "GET_PLACE_API");
        }
    }
}
