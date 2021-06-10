package de.hft.softec.dbsys2.crimemap;

import java.util.List;
import java.util.StringJoiner;

public class GeoJSON {
    
    public static String convertToGeoJSON(List<Crime> crimes) {
        
        // https://datatracker.ietf.org/doc/html/rfc7946

        String json = "{\"type\": \"FeatureCollection\", \"features\": [";

        for (Crime crime : crimes) {
            json += "{\"type\": \"Feature\",";

            json += "\"properties\": {";
            json += "\"id\":\"" + crime.getId() + "\",";
            json += "\"offense\":\"" + crime.getOffense() + "\",";
            json += "\"description\":\"" + crime.getDescription() + "\",";
            json += "\"urlToPolicePressRelease\":\"" + crime.getUrlToPolicePressRelease() + "\",";
            json += "\"timestamp\":\"" + crime.getDateTime() + "\"},";
            
            json += "\"geometry\": { \"type\": \"Point\", \"coordinates\": [";
            json += crime.getLon() + "," + crime.getLat();
            json += "]}},";
        }

        // QUICK AND DIRTY HACK!
        // We just remove the last comma
        json = json.substring(0, json.length() - 1);

        json += "]}";

        return json;
    }

}
