package org.cts.modules.resource.libraries;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class robot_library {
    static String ip = "192.168.2.101";

    public static void executeSkill(String skillID, String location) throws IOException     {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3600, TimeUnit.SECONDS);
        client.setReadTimeout(3600, TimeUnit.SECONDS);
        client.setWriteTimeout(3600, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\r\n\"Execute_Skill\":\"" + skillID + "\"\r\n," +
                "\r\n\"Location\":\"" + location + "\"\r\n" +
                "}");

        Request request = new Request.Builder().url("http://" + ip + ":8080")
                .method("POST",body)
                .addHeader("Content-Type","application/json")
                .build();

        Response response = client.newCall(request).execute();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        executeSkill("Skill_Blue_2","B");
    }
}
