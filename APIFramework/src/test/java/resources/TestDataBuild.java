package resources;

import pojo.AddPlace;
import pojo.Location;

import java.util.Arrays;

public class TestDataBuild {

    public AddPlace addPlacePayload(String name, String language, String address) {
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress(address);
        p.setLanguage(language);
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://www.google.com");
        p.setName(name);
        p.setTypes(Arrays.asList("Shoe Park", "shop"));
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        p.setLocation(location);
        return p;
    }

    public String deletePlacePayload(String placeID)
    {
        return "{\r\n\"place_id\":\""+placeID+"\"\r\n}";
    }
}
