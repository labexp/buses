package org.labexp.traces;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.labexp.traces.ApiConnector.JSON;


class Put extends AsyncTask<String, String, String> {


    private static OkHttpClient client;

    String response;

    protected String doInBackground(String... params) {
        if (client == null){
            client = new OkHttpClient();
        }
        String sb = params[1];
        RequestBody body = RequestBody.create(JSON, sb);


        Request request = new Request.Builder()
                .url(params[0])
                .put(body)
                .build();


        try (Response response = client.newCall( request).execute()) {
            if (params.length >2 )
            if (params[2].equals("Post")){
                JSONObject jsonObj = new JSONObject(ApiConnector.localtraceId = response.body().string());
                ApiConnector.localtraceId = jsonObj.getString("traceId");

            }
        }
        catch (java.io.IOException x) {
            return "null";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";


    }

}
