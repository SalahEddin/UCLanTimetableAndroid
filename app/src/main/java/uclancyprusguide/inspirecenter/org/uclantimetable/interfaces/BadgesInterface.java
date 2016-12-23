package uclancyprusguide.inspirecenter.org.uclantimetable.interfaces;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.Badge;

/**
 * Created by salah on 21/12/2016.
 */

public interface BadgesInterface {
    void onDownloaded(List<Badge> badge);
}
