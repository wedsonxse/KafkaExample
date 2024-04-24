package com.kafka.quickstart.adapters;

import com.kafka.quickstart.domain.Listener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class Ingestor implements  Runnable{
    List<String> origins = new ArrayList<>();
    List<Listener> listeners = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();

    public Ingestor(String origin, Listener listener){
        this.origins.add(origin);
        this.listeners.add(listener);
    }

    public Thread getThreadInstance(){
        return new Thread(this);
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
                this.emit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void emit() throws Exception {
        try{
            this.origins.parallelStream().forEach(url ->
                    this.listeners.forEach(listener ->
                    {
                        try {
                            listener.listen(this.invokeUrl(url));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                );
        }catch(Exception e){
            throw new Exception(e);
        }

    }

    private String invokeUrl(String urlString) throws Exception {
        try{
            Request req = new Request.Builder().url(urlString).build();
            Response response = this.client.newCall(req).execute();
            return response.body().string();
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    };
}
