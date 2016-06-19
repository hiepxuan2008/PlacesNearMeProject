package com.itshareplus.placesnearme.Module;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public class LoginToServer {
        String TAG_LOG = QueryNewsFeed.class.getName();
        final static String URL = "";

        LoginToServerListener listener;
        String username;
        String password;

        public LoginToServer(LoginToServerListener listener, String username, String password) {
            this.listener = listener;
            this.username = username;
            this.password = password;
        }

        public void execute() {
            RequestParams params = new RequestParams();
            params.put("cid", "login");
            params.put("username", username);
            params.put("password", password);

            RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    parseJSON(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    listener.OnLoginToServerFailed("Login failed!");
                }
            });
        }

        private void parseJSON(JSONObject response) {
            try {
                int error = response.getInt("error");
                if (error == 1) {
                    String error_message = response.getString("error_message");
                    listener.OnLoginToServerFailed(error_message);
                    return;
                }
                JSONObject resultObject = response.getJSONObject("result");
                int userID = resultObject.getInt("user_id");
                if (userID <= 0)
                    userID = 0;
                String avatar = resultObject.getString("avatar");

                listener.OnLoginToServerSuccess(userID, avatar);
            } catch (Exception e) {
                listener.OnLoginToServerFailed("parseJSON failed");
            }
        }
}
