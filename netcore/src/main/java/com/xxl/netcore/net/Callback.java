package com.xxl.netcore.net;

import java.io.IOException;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public interface Callback {

    void onFailure(IOException e);

    void onResponse(String response) throws IOException;
}
