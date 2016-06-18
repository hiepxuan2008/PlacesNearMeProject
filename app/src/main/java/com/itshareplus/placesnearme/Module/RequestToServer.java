package com.itshareplus.placesnearme.Module;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Mai Thanh Hiep on 4/25/2016.
 */
public class RequestToServer {
    static enum MessageType {
        Login, Logout, AskQuestion, AnswerQuestion
    }
    public static enum Method {
        POST, GET
    }

    public static String ServerURL = "http://itshareplus.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void sendRequest(String targetURl, Method method, RequestParams params, ResponseHandlerInterface responseHandler) {
        if (method == Method.GET)
            client.get(ServerURL + targetURl, params, responseHandler);
        else if (method == Method.POST)
            client.post(ServerURL + targetURl, params, responseHandler);
    }
}
