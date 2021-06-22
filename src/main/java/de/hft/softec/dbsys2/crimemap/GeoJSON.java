package de.hft.softec.dbsys2.crimemap;

import java.util.List;

public class GeoJSON {
    
    public static String convertToGeoJSON(List<Crime> crimes) {
        
        // https://datatracker.ietf.org/doc/html/rfc7946

        String json = "{\"type\": \"FeatureCollection\", \"features\": [";

        for (Crime crime : crimes) {
            json += "{\"type\": \"Feature\",";

            json += "\"properties\": {";
            json += "\"id\":\"" + crime.getId() + "\",";
            json += "\"district\":\"" + crime.getDistrict().getName() + "\",";
            json += "\"offense\":\"" + crime.getOffense().getName() + "\",";
            json += "\"description\":\"" + crime.getDescription() + "\",";
            json += "\"timestamp\":\"" + crime.getDateOfCrime() + "\"},";
            
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
