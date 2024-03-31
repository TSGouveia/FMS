package org.cts.modules.resource.libraries;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class kitt_library {

    public static Semaphore kittBeingUsed = new Semaphore(1);

    static String ip = "192.168.2.28";

    public static void executeTransporte(String skillID) throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
        client.setReadTimeout(3600, TimeUnit.SECONDS);
        client.setWriteTimeout(3600, TimeUnit.SECONDS);
        Request request = new Request.Builder().url("http://192.168.2.28/passadeiras?skill=" + skillID).build();
        try {
            kitt_library.kittBeingUsed.acquire();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Response response = client.newCall(request).execute();
        response.body().close();
        kitt_library.kittBeingUsed.release();
    }

    public static void executeStation(String skillID) throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
        client.setReadTimeout(3600, TimeUnit.SECONDS);
        client.setWriteTimeout(3600, TimeUnit.SECONDS);
        Request request = new Request.Builder().url("http://192.168.2.28/estacao?skill=" + skillID).build();
        try {
            kitt_library.kittBeingUsed.acquire();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Response response = client.newCall(request).execute();
        response.body().close();
        kitt_library.kittBeingUsed.release();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 2; i++) {
            kitt_library.executeTransporte("AB");
            Thread.sleep(1000);
            kitt_library.executeTransporte("BC");
            Thread.sleep(1000);
            kitt_library.executeTransporte("CD");
            Thread.sleep(1000);
            kitt_library.executeStation("0");
            Thread.sleep(1000);
            kitt_library.executeStation("1");
            Thread.sleep(1000);
            kitt_library.executeTransporte("DE");
            Thread.sleep(1000);
            kitt_library.executeTransporte("EF");
            Thread.sleep(1000);
            kitt_library.executeTransporte("FA");
            Thread.sleep(1000);
        }
    }

    //Rede: Kit FMS
    //Pass: demo01FMS

}
