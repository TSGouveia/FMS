package org.cts.modules.resource.libraries;

import com.squareup.okhttp.*;
import org.cts.utilities.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class human_library {
        static String ip = Constants.tabletIP;

    public static void executeSkill(String skillID) throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
        client.setReadTimeout(3600, TimeUnit.SECONDS);
        client.setWriteTimeout(3600, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\r\n\"ExecuteSkill\":\"" + skillID + "\"\r\n}");

        Request request = new Request.Builder().url("http://" + ip + ":3001/receive/")
                .method("POST",body)
                .addHeader("Content-Type","application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.headers());

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        executeSkill("Skill_Blue_2");
    }
}
