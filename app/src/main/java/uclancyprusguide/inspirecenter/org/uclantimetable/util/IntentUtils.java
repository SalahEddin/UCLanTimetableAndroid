package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * @author Nearchos
 *         Created: 09-Apr-16
 */
public class IntentUtils {

    static boolean isIntentAvailable(Context context, String action)
    {
        return isIntentAvailable(context, new Intent(action));
    }

    static public boolean isIntentAvailable(final Context context, final Intent intent)
    {
        if(context == null) return false;
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
