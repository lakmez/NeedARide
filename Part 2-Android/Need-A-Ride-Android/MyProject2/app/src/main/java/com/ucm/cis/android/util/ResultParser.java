package com.ucm.cis.android.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucm.cis.android.db.nOffer;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by LekshmiPriya on 4/30/2016.
 */
public class ResultParser {
  /*  private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jp = null;*/

    public void parse(String result) {
        try {
           JSONArray jsonArray = new JSONArray(result);

         //   for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Type listType = new TypeToken<List<nOffer>>(){}.getType();
                   // Type listType = new TypeToken<List<nOffer>>() {}.getType();
                    List<nOffer> target = new LinkedList<nOffer>();
                    target.add(new nOffer());

                    Gson gson = new Gson();
                    String json = gson.toJson(target, listType);
                    List<nOffer> target2 = gson.fromJson(jsonArray.get(0).toString(), listType);
                    //JSONObject obj1 = new JSONObject(result);
                   // JSONArray results = obj1.getJSONArray("results");
                    //User user = new Gson().fromJson(result, User.class);
                    //String indexForPhone = results.getJSONObject(0).getString("indexForPhone");
                    System.out.println("Hello");

                } catch (Exception e) {
                }
          //  }
        } catch (Exception e) {
        }
    }
}
