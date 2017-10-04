package ua.student.program;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WetnessFragment_user extends Fragment {
    public ArrayList<Double> y = new ArrayList<Double>();

    String contentText = null;
    String equak;
    double x = 0;
    GraphView graph;
    LineGraphSeries<DataPoint> series;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wetness_process_user, container, false);

        graph  = (GraphView) view.findViewById(R.id.graph);


        series = new LineGraphSeries<DataPoint>();
        //series.setColor(5);


        Button temp = (Button)view.findViewById(R.id.temperature);
        temp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), temperature_download_user.class);
                    startActivity(intent);
                }
                catch (Exception e){
                }
            }
        });
        Button ocv = (Button)view.findViewById(R.id.Light);
        ocv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), LightDownload_user.class);
                    startActivity(intent);
                }
                catch (Exception e){
                }
            }
        });
        // если данные ранее были загружены
        if(contentText!=null){
            Toast.makeText(getActivity(), "Данные загружены", Toast.LENGTH_SHORT).show();
        }

        Button btnFetch = (Button)view.findViewById(R.id.downloadBtn);
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(true){
                    y.clear();
                    new ProgressTask().execute("https://docs.google.com/document/export?format=txt&id=1x7FKgFmYqJLtpjUCFIkq8aR9n8SPxYxFMh6J8RrQ0OA&token=AC4w5VjEgK2grn3_rqwkJfFEBbJvaOEYMQ%3A1495069592484");

                }
            }

        });

        return view;
    }

    private class ProgressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... path) {

            String content;
            try{
                content = getContent(path[0]);
            }
            catch (IOException ex){
                content = ex.getMessage();
            }

            return content;
        }
        @Override
        protected void onPostExecute(String content) {

            contentText=content;
            Toast.makeText(getActivity(), "Данные загружены", Toast.LENGTH_SHORT)
                    .show();
        }

        private String getContent(String path) throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL(path);
                HttpsURLConnection c=(HttpsURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.connect();
                reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder buf=new StringBuilder();
                String line=null;
                double d;


                while ((line=reader.readLine()) != null) {
                    equak = "";
                    int p = line.length();
                    for(int s = 0; s<line.length();s++){
                        try{
                            d = Double.parseDouble(String.valueOf(line.charAt(s)));
                            equak += String.valueOf(line.charAt(s));
                        }
                        catch (Exception e){

                        }
                    }
                    d = Double.valueOf(equak);
                    y.add(d);
                    buf.append(line + "\n");
                }
                return(buf.toString());
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
                x=0;
                for (int i = 0 ; i < y.size(); i++){
                    try {
                        series.appendData(new DataPoint(x, y.get(i)), false, 100);
                        x = x + 1;
                    }catch (Exception e){
                        break;
                    }
                }
                /*graph.getViewport().setMaxX(x);*/
                graph.addSeries(series);
                graph.getViewport().setScalable(true);
                graph.getViewport().setMinY(0.0);
                graph.getViewport().setMaxY(60.0);
                graph.getViewport().setScrollable(true);

            }
        }
    }
}

