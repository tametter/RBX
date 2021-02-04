package ch.talionis.rbx.android;

import android.content.Context;
import android.util.TypedValue;

public class AndroidUtils {

    public static float dpToPx(Context context, int dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }
}
