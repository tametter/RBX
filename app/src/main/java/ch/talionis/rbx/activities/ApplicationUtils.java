package ch.talionis.rbx.activities;

import android.content.Context;

import ch.talionis.rbx.application.RbxApplication;
import ch.talionis.rbx.router.Router;

public class ApplicationUtils {
    public static RbxApplication getRbxApplication(Context context) {
        return (RbxApplication) context.getApplicationContext();
    }

    public static Router getRouter(Context context) {
        return getRbxApplication(context).getRouter();
    }
}
