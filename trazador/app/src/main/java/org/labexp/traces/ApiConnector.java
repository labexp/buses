package org.labexp.traces;

/*
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.json.impl.provider.entity.JSONListElementProvider;
import com.sun.jersey.api.client.config.ClientConfig;



import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
*/
import android.os.Build;
import android.support.annotation.RequiresApi;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * This class is a direct connection to the API server .
 */
public class ApiConnector {
  /**
   * jersey client to connect the server.
   */
  private static OkHttpClient client;
  /*private static Client client;
  /**
   * Server url.
   */
  private static String apiBaseUrl ;
  /**
   * Format to save date on the server.
   */
  private static final  String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
  /**
   * sub-url to Post trace start.
   */
  private static final  String POST_START_TRACE = "/buses/v0.1/trace";
  /**
   * sub-url to Post a list of points.
   */
  private static final  String POST_POINTS = "/buses/v0.1/trace/<traceId>/points";
  /**
   * sub-url to Post a stop point.
   */
  private static final  String POST_STOP = "/buses/v0.1/trace/<traceId>/stop";
  /**
   * sub-url to put metadata
   */
  private static final  String PUT_METADATA = "/buses/v0.1/trace/<traceId>/metadata";
  /**
   * sub-url to finalize trace.
   */
  private static final  String PUT_FINALIZE = "/buses/v0.1/trace/<traceId>";
  /**
   * server response ok.
   */
  private static final  int OK_RESPONSE = 200;

  public static final MediaType JSON  = MediaType.parse("application/json; charset=utf-8");

  public static String localtraceId = "";


  /**
   * Starts Jersey client.
   */
  public static void createClient(String url) {
    apiBaseUrl = url;


    /*
    ClientConfig clientConfig = new DefaultClientConfig();
    clientConfig.getFeatures().put(
        JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    client = Client.create(clientConfig);
    */
  }
  /**
   * Starts trace on server.
   * @param deviceId A unique identificator for the client device.
   * @return unique trace identificator
   */
  public static String startTrace(final String deviceId) throws java.io.IOException{
    /*
    // Creates a webResource with the API 
    WebResource webResource = client.resource(apiBaseUrl + POST_START_TRACE);
     */
    // Building the Post content
    String jsonInput = "";
    try {
      jsonInput = new JSONObject()
              .put("deviceId", deviceId)
              .put("timestamp", getDate())
              .toString();
    } catch (JSONException e) {
      e.printStackTrace();
    }

    Post async = new Post();
    try {
      async.execute(apiBaseUrl + POST_START_TRACE, jsonInput, "Post").get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }



    /*
    // POST method
    ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .Post(ClientResponse.class, jsonInput);

    // Check response status code
    if (response.getStatus() != OK_RESPONSE) {
      throw new RuntimeException(
          "Failed : HTTP error code : " + response.getStatus());
    }

    // Reads response
    String output = response.getEntity(String.class);
    String traceId = "";

    try {
      JSONObject result = new JSONObject(output);
      traceId = result.getString("traceId");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return traceId;
*/
    return "";
  }

  /**
   * @param deviceId unique device id
   * @param traceId unique trace id
   * @param points list of points to be add
   * @return server response
   * @throws RuntimeException server connection error
   */
  public static boolean addPoints(
          final String deviceId,
          final String traceId,
          final ArrayList<MapPoint> points)throws RuntimeException {

    /*
    // Creates a webResource with the API url to Post start a trace
    WebResource webResource = client.resource(
            apiBaseUrl + POST_POINTS.replaceFirst("\\{traceId\\}", traceId));
    */
    // Building the Post content

    String url = apiBaseUrl + POST_POINTS.replaceFirst("<traceId>", localtraceId);
    String jsonInput = "";
    try {

        JSONArray jsonPointList = new JSONArray();
        for (MapPoint point : points){
            JSONObject pointElement = new JSONObject()
                    .put("latitude", point.getX()+"")
                    .put("longitude", point.getY()+"");
            jsonPointList.put(pointElement);
        }

        jsonInput = new JSONObject()
                .put("deviceId", deviceId)
                .put("timestamp", getDate())
                .put("points", jsonPointList)
                .toString();
        System.out.println(jsonInput);
        post(url, jsonInput);

    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


    /*
    // POST method
    ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .Post(ClientResponse.class, jsonInput);

    // Check response status code
    if (response.getStatus() != OK_RESPONSE) {
      throw new RuntimeException(
          "Failed : HTTP error code : " + response.getStatus());
    }
    else{
        return true;
    }
    */
    return true;

  }

  
  /**
   * @param deviceId unique device id
   * @param traceId unique trace id
   * @param stop stop position
   * @return server response
   * @throws RuntimeException server connection error
   */
  public static boolean addStop(
          final String deviceId,
          final String traceId,
          final MapPoint stop)throws RuntimeException {
/*
    // Creates a webResource with the API url 
    WebResource webResource = client.resource(
            apiBaseUrl + POST_STOP.replaceFirst("\\{traceId\\}", traceId));
*/
    // Building the Post content
    String url = apiBaseUrl + POST_STOP.replaceFirst("<traceId>", localtraceId);
    String jsonInput = "";
    try {


            JSONObject pointElement =
                    new JSONObject()
                    .put("latitude", stop.getX()+"")
                    .put("longitude", stop.getY()+"");

            jsonInput = new JSONObject()
                    .put("deviceId", deviceId)
                    .put("timestamp", getDate())
                    .put("stop", pointElement)
                    .toString();
            System.out.println(jsonInput);
      post(url, jsonInput);
            
    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    /*
    // POST method
    ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .Post(ClientResponse.class, jsonInput);

    // Check response status code
    if (response.getStatus() != OK_RESPONSE) {
      throw new RuntimeException(
          "Failed : HTTP error code : " + response.getStatus());
    }
    else{
        return true;
    }
    */
    return true;

  }
  
  /**
   * @param deviceId unique device id
   * @param traceId unique trace id
   * @param metadata information about the trace
   * @return server response
   * @throws RuntimeException server connection error
   */
  public static boolean setMetadata(
          final String deviceId,
          final String traceId,
          final Metadata metadata)throws RuntimeException {
/*
    // Creates a webResource with the API url 
    WebResource webResource = client.resource(
            apiBaseUrl + PUT_METADATA.replaceFirst("\\{traceId\\}", traceId));
*/
    String url = apiBaseUrl + PUT_METADATA.replaceFirst("<traceId>", localtraceId);
    // Building the put content
    String jsonInput = "";
    try {

            jsonInput = new JSONObject()
                    .put("deviceId", deviceId)
                    .put("timestamp", getDate())
                    .put("routeCode", metadata.getRouteCode())
                    .put("routeName", metadata.getRouteName())
                    .put("routePrice", metadata.getRoutePrice())
                    .toString();
      put(url, jsonInput);

    } catch (JSONException e) {
        
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    /*
    // Put method
    ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .put(ClientResponse.class, jsonInput);

    // Check response status code
    if (response.getStatus() != OK_RESPONSE) {
      throw new RuntimeException(
          "Failed : HTTP error code : " + response.getStatus());
    }
    else{
        return true;
    }
    */
    return true;

  }

  /**
   * @param traceId unique trace id
   * @return server response
   * @throws RuntimeException server connection error
   */
  public static boolean finished(
          final String traceId)throws RuntimeException {
/*
    // Creates a webResource with the API url
    WebResource webResource = client.resource(
            apiBaseUrl + PUT_FINALIZE.replaceFirst("\\{traceId\\}", traceId));
*/
    String url = apiBaseUrl + PUT_FINALIZE.replaceFirst("<traceId>", localtraceId);
    // Building the Post content
    String jsonInput = "";
    try {

            jsonInput = new JSONObject()
                    .put("status", "finished")
                    .toString();
      put(url, jsonInput);

    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    /*
    // PUT method
    ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .put(ClientResponse.class, jsonInput);

    // Check response status code
    if (response.getStatus() != OK_RESPONSE) {
      throw new RuntimeException(
          "Failed : HTTP error code : " + response.getStatus());
    }
    else{
        return true;
    }
    */
    return true;

  }

  /**
   * @param traceId unique trace id
   * @return server response
   * @throws RuntimeException server connection error
   */
  public static boolean discarded(
          final String traceId)throws RuntimeException {

    /*
    // Creates a webResource with the API url
    WebResource webResource = client.resource(
            apiBaseUrl + PUT_FINALIZE.replaceFirst("\\{traceId\\}", traceId));
*/
    String url = apiBaseUrl + PUT_FINALIZE.replaceFirst("<traceId>", localtraceId);
    // Building the Post content
    String jsonInput = "";
    try {

            jsonInput = new JSONObject()
                    .put("status", "discarded")
                    .toString();

      put(url, jsonInput);

    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    /*
    // PUT method
    ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .put(ClientResponse.class, jsonInput);

    // Check response status code
    if (response.getStatus() != OK_RESPONSE) {
      throw new RuntimeException(
          "Failed : HTTP error code : " + response.getStatus());
    }
    else{
        return true;
    }
    */
    return true;

  }
  
  /**
   * Calculates timeZone and formats date into a string.
   * @return Date with the format of @DATE_FORMAT.
   */
  private static String getDate() {

    Instant instant = Instant.now();
    ZoneId zoneId = ZoneId.of( "America/Costa_Rica" );
    ZonedDateTime zdt = ZonedDateTime.ofInstant( instant , zoneId );
    DateTimeFormatter dtfOut = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return zdt.format(dtfOut).toString();



  }

  public Instant now() {
    return Instant.now();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)

  static String post(String url, String json) throws java.io.IOException {
    Post async = new Post();
    async.execute(url, json);
    return "";


  }

  static String put(String url, String json) throws java.io.IOException {
    Put async = new Put();
    async.execute(url, json);
    return "";


  }


}
