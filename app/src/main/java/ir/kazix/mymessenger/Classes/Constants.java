package ir.kazix.mymessenger.Classes;

import com.android.volley.Request;

public class Constants {

    public static final String SERVER_NAME = "https://kazix.ir";

    public static final String SERVER_NODE_JS = "http://kazix.ir";
    public static final String SERVER_NODE_JS_PORT = ":3000";

    public static final String REQUEST_URI_SIGN_UP = "/sign_up.php";
    public static final String REQUEST_URI_REGISTER = "/register.php";
    public static final String REQUEST_URI_SIGN_IN = "/sign_in.php";
    public static final String REQUEST_URI_LOG_OUT = "/log_out.php";

    public static final int VOLLEY_GET = Request.Method.GET;
    public static final int VOLLEY_POST = Request.Method.POST;
}
