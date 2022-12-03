package com.gmail.projectCrypt.backend.cryptData;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;



public class API {

    private static String apiKey = "97f035fe-195f-49fb-bf54-83a72a48c137";

    public static void main() {
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("start","1"));
        parameters.add(new BasicNameValuePair("limit","100"));
        parameters.add(new BasicNameValuePair("convert","USD"));
        parameters.add(new BasicNameValuePair("sort_dir", "desc"));
        parameters.add(new BasicNameValuePair("cryptocurrency_type", "coins"));
        parameters.add(new BasicNameValuePair("aux", ""));

        try {
            String result = makeAPICall(uri, parameters);
            jsonDataParser(result);
            System.out.println(result);
            System.out.println("Data parsed :)");
        } catch (IOException e) {
            System.out.println("Error: cannot access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
    }

    //Preprovided by API documentation
    private static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }

   //Example JSON data

                                /*
                            {
                              {
                                "data": [
                                {
                                "id": 1,
                                "name": "Bitcoin",
                                "symbol": "BTC",
                                "slug": "bitcoin",
                                "cmc_rank": 5,
                                "num_market_pairs": 500,
                                "circulating_supply": 16950100,
                                "total_supply": 16950100,
                                "max_supply": 21000000,
                                "last_updated": "2018-06-02T22:51:28.209Z",
                                "date_added": "2013-04-28T00:00:00.000Z",
                                "tags": [
                                "mineable"
                                ],
                                "platform": null,
                                "quote": {
                                    "USD": {
                                        "price": 9283.92,
                                        "volume_24h": 7155680000,
                                        "percent_change_1h": -0.152774,
                                        "percent_change_24h": 0.518894,
                                        "percent_change_7d": 0.986573,
                                        "market_cap": 158055024432,
                                        "last_updated": "2018-08-09T22:53:32.000Z"
                                    },
                                "BTC": {
                                    "price": 1,
                                    "volume_24h": 772012,
                                    "percent_change_1h": 0,
                                    "percent_change_24h": 0,
                                    "percent_change_7d": 0,
                                    "market_cap": 17024600,
                                    "last_updated": "2018-08-09T22:53:32.000Z"
                                }
                                }
                                }
                                ],
                                "status": {
                                "timestamp": "2018-06-02T22:51:28.209Z",
                                "error_code": 0,
                                "error_message": "",
                                "elapsed": 10,
                                "credit_count": 1
                                }
                                }
                             */



    private static void jsonDataParser(String result) {

        //THIS IS ORGANIZED IN ORDER OF THE DATA, AND HOW IT SHOWS IN THE JSON EXAMPLE ABOVE.
        //Data will be unstructured. It will go through every object and save this data inside it
        JSONObject obj = new JSONObject(result);

        //Storing and creating a Hashtable where the name of the currency and an object containing its data is recorded
        Hashtable<String, cryptCurrencyPriceData> cryptocurrency = new Hashtable<>();

        for(int i = 0; i < 100; i++) {

            cryptCurrencyPriceData ccpd = new cryptCurrencyPriceData();
            //after creating a new object we add the data into the ccpd object then add it into the ArrayList or Hashmap
            //information about the cryptocurrency being added, basic string and name formats.
            ccpd.setName(obj.getJSONArray("data").getJSONObject(i).getString("name"));
            ccpd.setSlug(obj.getJSONArray("data").getJSONObject(i).getString("slug"));
            ccpd.setSymbol(obj.getJSONArray("data").getJSONObject(i).getString("symbol"));
            ccpd.setDateAdded(obj.getJSONArray("data").getJSONObject(i).getString("date_added"));
            
            //actual numerical data (Number) being inserted into the obj
            ccpd.setPrice(obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("price"));
            ccpd.setVolume_24h(obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("volume_24h"));
            ccpd.setPercent_change_1h(obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_1h"));
            ccpd.setPercentChange_24h(obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h"));
            ccpd.setPercent_change_7d(obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_7d"));
            ccpd.setMarket_cap(obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote").getJSONObject("USD").getDouble("market_cap"));

            //adding ths object of Cryptocurrencies into a ArrayList<Object>
            cryptocurrency.put(ccpd.getSymbol(), ccpd);
            }
            try {
                updateDB(cryptocurrency);
            } catch(SQLException sqle) {
                sqle.getErrorCode();
            }



    }

    //END JSON PARSER

    //START UPDATE DB METHOD

    private static void updateDB(Map<String, cryptCurrencyPriceData> cryptocurrency) throws SQLException {
        //Using DDL INSERT
        for(Map.Entry<String, cryptCurrencyPriceData> entry : cryptocurrency.entrySet()) {
            String sql = "INSERT INTO coins" +
                    "(name,price,symbol,slug,date_added,volume_24h,percent_change_1h,percent_change_24h,percent_change_7d,market_cap)" + "VALUES("
                    + "'" + entry.getValue().getName() + "'" + ","
                    + entry.getValue().getPrice() + ","
                    + "'" + entry.getValue().getSymbol() + "'" + ","
                    + "'" + entry.getValue().getSlug() + "'" + ","
                    + "'" + entry.getValue().getDateAdded() + "'" + ","
                    + entry.getValue().getVolume_24h() + ","
                    + entry.getValue().getPercent_change_1h() + ","
                    + entry.getValue().getPercentChange_24h() + ","
                    + entry.getValue().getPercent_change_7d() + ","
                    + entry.getValue().getMarket_cap() + ")";

            String sql2 = "INSERT INTO historical_data" +
                    "(symbol, date, price)" + "VALUES("
                    + "'" + entry.getValue().getSymbol() + "'" + ","
                    + "'" + entry.getValue().getDateAdded() + "'" + ","
                    + entry.getValue().getPrice() + ")";

            SQLConnector connect = new SQLConnector();
            Statement statement = connect.SQLConnection();
            statement.executeUpdate(sql);
            statement.executeUpdate(sql2);
        }

    }

}

