package com.itshareplus.placesnearme.Module;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public interface LoginToServerListener {
    void OnLoginToServerStart();
    void OnLoginToServerSuccess(int userId, String avatar); //Ha hoi phan giai sau
    void OnLoginToServerFailed(String error_message);
}
