package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;

/**
 * @author Nearchos Paspallis
 * 03/01/2015 / 20:02.
 */
public class SharingTools
{
    static public void shareApp(final Context context)
    {
        final String title = context.getString(R.string.app_name);
        final String text = context.getString(R.string.Install);
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        final String shareText = context.getString(R.string.Share);
        if(IntentUtils.isIntentAvailable(context, shareIntent))
        {
            context.startActivity(Intent.createChooser(shareIntent, shareText));
        }
        else
        {
            Toast.makeText(context, "No apps available for sharing", Toast.LENGTH_SHORT).show();
        }
    }
}