package com.bue.shoppingplanner.asynctascs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.bue.shoppingplanner.IntroActivity;
import com.bue.shoppingplanner.model.Currencies;

import android.os.AsyncTask;
import android.util.Log;

public class ExchangesAsyncTask extends AsyncTask<String, Integer, ArrayList<Currencies>> {

	@Override
	protected ArrayList<Currencies> doInBackground(String... params) {
		return setExchangeRates(params[0]);
	}
	
	 protected void onPostExecute(String result) {
         //showDialog(\"Downloaded \" + result + \" bytes\");
     }

	
	private ArrayList<Currencies> setExchangeRates(String url){
		 ArrayList<Currencies> exchanges=new ArrayList<Currencies>();
		 String readExchangesFeed = "{\"base\":\"USD\",\"timestamp\":1368979269,\"rates\":{\"JPY\":103.042417,\"CNY\":6.156293,\"UGX\":2570.494898,\"RON\":3.379399,\"TTD\":6.416162,\"SHP\":0.658664,\"MOP\":8.002303,\"KGS\":48.231999,\"BTC\":0.008189,\"DJF\":177.595001,\"BTN\":54.877267,\"ZAR\":9.375066,\"NOK\":5.854652,\"ILS\":3.662991,\"SYP\":70.135986,\"HTG\":42.513088,\"YER\":214.951753,\"UYU\":18.92423,\"XAU\":7.23E-4,\"BBD\":2,\"EEK\":11.760122,\"FKP\":0.658664,\"MWK\":359.486997,\"IDR\":9751.820715,\"PGK\":2.146533,\"XCD\":2.701275,\"RWF\":649.552243,\"NGN\":158.182191,\"BSD\":1,\"TMT\":2.8525,\"HRK\":5.897213,\"COP\":1842.629613,\"GEL\":1.634925,\"DKK\":5.803815,\"VUV\":93.133332,\"FJD\":1.8298,\"MVR\":15.363075,\"AZN\":0.78465,\"MNT\":1427,\"MGA\":2212.696667,\"WST\":2.301328,\"VEF\":6.291354,\"KMF\":383.611809,\"GNF\":7068.658333,\"SBD\":7.190282,\"KWD\":0.28621,\"BDT\":78.060663,\"MMK\":932.62225,\"TJS\":4.7573,\"JOD\":0.708164,\"NZD\":1.233578,\"PAB\":1,\"MDL\":12.349999,\"CVE\":86.070988,\"CLP\":480.585781,\"KES\":83.843042,\"SRD\":3.275,\"MUR\":31.307084,\"LRD\":74.3797,\"SAR\":3.750227,\"ARS\":5.23538,\"INR\":54.889151,\"EGP\":6.974444,\"PYG\":4209.44232,\"TRY\":1.837194,\"CDF\":922.252433,\"BMD\":1,\"OMR\":0.385016,\"CUP\":22.682881,\"NIO\":24.398615,\"GMD\":33.040738,\"UZS\":2072.978335,\"ZMK\":5227.108333,\"GTQ\":7.799754,\"EUR\":0.778648,\"NPR\":87.724702,\"NAD\":9.367298,\"PHP\":41.20967,\"HUF\":226.843017,\"USD\":1,\"LAK\":7671.755,\"XDR\":0.67175,\"SZL\":9.401611,\"MTL\":0.683602,\"BND\":1.25512,\"TZS\":1630.517843,\"SDG\":4.41033,\"LSL\":9.404656,\"KYD\":0.827607,\"LKR\":126.193482,\"MKD\":47.71198,\"MXN\":12.316021,\"CAD\":1.025732,\"AUD\":1.024311,\"ISK\":123.361249,\"LYD\":1.285463,\"SLL\":4309.501423,\"PKR\":98.502121,\"ANG\":1.78865,\"THB\":29.821308,\"SCR\":11.734825,\"LBP\":1506.43985,\"GHS\":1.994163,\"AED\":3.67314,\"BOB\":6.99448,\"ZMW\":5.364283,\"GIP\":0.658664,\"QAR\":3.64063,\"IRR\":12286.25,\"BHD\":0.376983,\"HNL\":19.032852,\"BWP\":8.326431,\"CLF\":0.02099,\"ALL\":110.013749,\"SEK\":6.690056,\"RSD\":86.122312,\"MYR\":3.019887,\"ETB\":18.6443,\"STD\":19134.033333,\"BGN\":1.523438,\"DOP\":41.098266,\"AMD\":418.80875,\"XPF\":93.244501,\"KRW\":1118.115354,\"JMD\":98.538791,\"MRO\":297.003125,\"JEP\":0.658664,\"LVL\":0.545088,\"BIF\":1569.841667,\"CZK\":20.271899,\"TND\":1.66024,\"ZWL\":322.387247,\"VND\":20933.395825,\"PEN\":2.639632,\"GBP\":0.658664,\"DZD\":79.744144,\"MZN\":29.625,\"AWG\":1.789933,\"XOF\":510.967749,\"CHF\":0.971498,\"KZT\":151.157045,\"UAH\":8.158142,\"RUB\":31.427979,\"BZD\":2.0216,\"TWD\":30.026604,\"BAM\":1.524592,\"SGD\":1.256772,\"MAD\":8.638905,\"BYR\":8684.4525,\"LTL\":2.688946,\"HKD\":7.763061,\"XAG\":0.043853,\"XAF\":510.717164,\"KHR\":3996.086667,\"GYD\":202.968332,\"BRL\":2.032716,\"AFN\":53.913333,\"SVC\":8.753344,\"IQD\":1158.625,\"CRC\":500.2334,\"PLN\":3.250814,\"SOS\":1488.808333,\"TOP\":1.745527,\"AOA\":96.100867,\"KPW\":900},\"license\":\"Data sourced from various providers with public-facing APIs; copyright may apply; resale is prohibited; no warranties given of any kind. All usage is subject to your acceptance of the License Agreement available at: \",\"disclaimer\":\"Exchange rates are provided for informational purposes only, and do not constitute financial advice of any kind. Although every attempt is made to ensure quality, NO guarantees are given whatsoever of accuracy, validity, availability, or fitness for any purpose - please use at your own risk. All usage is subject to your acceptance of the Terms and Conditions of Service, available at:\"}";
				 //readExchangesFeed(url);
		    try {
		      JSONObject jsonObject = new JSONObject(readExchangesFeed);
		        JSONObject jsonObject2 = jsonObject.getJSONObject("rates");	
		        Iterator currencies=jsonObject2.keys();
		        while(currencies.hasNext()){
		        	String currency=(String)currencies.next();
		        	Double rate=jsonObject2.getDouble(currency);
		        	Currencies item=new Currencies(currency, rate);
		        	exchanges.add(item);
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return exchanges;
	}
	
	private String readExchangesFeed(String url) {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e(IntroActivity.class.toString(), "Failed to download file");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }catch (Exception e) {
		      e.printStackTrace();
		    }
	    return builder.toString();
	  }

}
