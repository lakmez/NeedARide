package com.ucm.cis.android.util;

import com.ucm.cis.android.db.nOffer;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by LekshmiPriya on 5/2/2016.
 */
public class OfferJsonParser {

    public void parse(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);
            jsonArray.length();
            nOffer offer;
            for ( int i=0;i<jsonArray.length();i++) {
                JSONObject obj = (JSONObject) jsonArray.getJSONObject(i).get("content");
                JSONArray src = obj.getJSONArray("source");
                JSONArray dst = obj.getJSONArray("destination");
                //offer.setSource(new nLatLong(, src.getString("address")));
            }
        }catch(Exception e){

        }
    }
}
