package com.ntnu.shapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingActivity extends Activity implements OnClickListener{
	private ArrayList<Integer> goalValues;
	private ArrayList<String> productNames;
	private Button scanBtn, evaluateBtn;
	private ListView productNamesListView;
	private ArrayAdapter<String> productNamesAdapter;
	private HashMap<String, String> eansForProduct;
	private DB database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		
		Intent intent = getIntent();
		goalValues = intent.getIntegerArrayListExtra("goalValues");
		
		scanBtn = (Button)findViewById(R.id.scan_button);
		scanBtn.setOnClickListener(this);
	
		evaluateBtn = (Button)findViewById(R.id.shopping_complete_button);
		evaluateBtn.setOnClickListener(this);
		
		database = new DB(this);
		database.deleteInsights();
		
		eansForProduct = new HashMap<String, String>();
		productNames = new ArrayList<String>();
		productNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productNames);
		
		productNamesListView = (ListView)findViewById(R.id.scan_content);
		productNamesListView.setClickable(true);
		productNamesListView.setAdapter(productNamesAdapter);
		
		productNamesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int item,
					long arg3) {
				final int itemToRemove = item;
				AlertDialog.Builder dialog = new AlertDialog.Builder(ShoppingActivity.this);
				dialog.setTitle(productNames.get(item));
				dialog.setMessage("Search the web for more information or delete from shopping list?");
				dialog.setNegativeButton("Search the web", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						 Uri uri = Uri.parse("http://www.google.com/search?q=" + productNames.get(itemToRemove));
						 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						 startActivity(intent);
					}
				});
				dialog.setNeutralButton("Save insights", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showInsightDialog(productNames.get(itemToRemove));
						
					}
				});
				dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(eansForProduct.containsKey(productNames.get(itemToRemove))){
							eansForProduct.remove(productNames.get(itemToRemove));							
						}
						productNames.remove(itemToRemove);
						productNamesAdapter.notifyDataSetChanged();
					}
				});
				dialog.show();
			}
		});
		/*
		productNames.add("TEST1");
		productNames.add("TEST2");
		productNames.add("TEST3");
		*/
	}
	
	
	private void showInsightDialog(String productName){
		final String name = productName;
		AlertDialog.Builder insightDialog = new AlertDialog.Builder(ShoppingActivity.this);
		insightDialog.setTitle("Add insight for " + productName);
		final EditText insight = new EditText(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		        LinearLayout.LayoutParams.MATCH_PARENT,
		        LinearLayout.LayoutParams.MATCH_PARENT);
		insight.setLayoutParams(lp);
		insightDialog.setView(insight);
		insight.setHeight(100);
		insightDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				database.addInsight(eansForProduct.get(name), insight.getText().toString());
				
			}
		});
		insightDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		
		insightDialog.show();
	}
	
	//Button listeners
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.scan_button){
			/*try {
				buildXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?><BarcodeLookupResponse><product><ean>5099750442227</ean><name>Thriller</name></product></BarcodeLookupResponse>");
				productNamesAdapter.notifyDataSetChanged();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			}
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		if(v.getId() == R.id.shopping_complete_button){
			Intent intent = new Intent(this, EvaluationActivity.class);
			intent.putStringArrayListExtra("productNames", productNames);
			intent.putIntegerArrayListExtra("goalValues", goalValues);
			startActivity(intent);
		}
		
	}
	//Scannerkode
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if(scanningResult != null){
			String scanContent = scanningResult.getContents();
			//String scanFormat = scanningResult.getFormatName();
			//contentTxt.setText("CONTENT: " + scanContent);
			new RequestTask().execute(scanContent);
		}
		else{
			Toast toast = Toast.makeText(getApplicationContext(), "No scan data recieved", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
		/*
			 * <?xml version="1.0" encoding="UTF-8"?>
		<BarcodeLookupResponse>
			<product>
				<ean>5099750442227</ean>
				<name>Thriller</name>
			</product>
		</BarcodeLookupResponse>
		
		<Error>Barcode not found</Error>
		 */

	public void buildXML(String xml) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource in = new InputSource(new StringReader(xml));
		Document document = builder.parse(in);
		if(document.getFirstChild().getTextContent().equals("Barcode not found")){
			Toast toast = Toast.makeText(this, "The requested barcode was not found", Toast.LENGTH_SHORT);
			toast.show();
		}
		else{
			String productName = document.getElementsByTagName("name").item(0).getTextContent();
			String ean = document.getElementsByTagName("ean").item(0).getTextContent();
			eansForProduct.put(productName, ean);
			
			productNames.add(productName);
			productNamesAdapter.notifyDataSetChanged();
		}
	}

	private class RequestTask extends AsyncTask<String, String, String>{

	    private static final String TAG = "Shapp";

		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
	        InputStream in = entity.getContent();
	        StringBuffer out = new StringBuffer();
	        int n = 1;
	        while (n > 0) {
	            byte[] b = new byte[4096];
	            n = in.read(b);
	            if (n > 0)
	                out.append(new String(b, 0, n));
	        }
	        return out.toString();
	    }
		
		@Override
		protected String doInBackground(String... ean) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			try{
				response = httpclient.execute(new HttpGet("http://www.ean-search.org/api?token=pede8628471&op=barcode-lookup&ean=" + ean[0]));
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK){
					HttpEntity entity = response.getEntity();
					responseString = getASCIIContentFromEntity(entity);
				}
				else{
	                //Closes the connection.
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
			}catch (Exception e){
				return e.getLocalizedMessage();
			}
			return responseString;
		}
		
		protected void onPostExecute(String results){
			try {
				buildXML(results);
			} catch (ParserConfigurationException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			} catch (SAXException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
