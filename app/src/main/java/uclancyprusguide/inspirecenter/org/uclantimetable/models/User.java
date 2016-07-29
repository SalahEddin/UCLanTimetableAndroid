package uclancyprusguide.inspirecenter.org.uclantimetable.models;

/**
 * Created by salah on 26/07/16.
 */
public class User {
    private String FULLNAME;

    private String USER_ID;

    private String ACCOUNT_ID;

    private String ACCOUNT_TYPE_ID;

    private String EMAIL;

    private String IS_ACTIVATED;

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public void setACCOUNT_ID(String ACCOUNT_ID) {
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    public String getACCOUNT_TYPE_ID() {
        return ACCOUNT_TYPE_ID;
    }

    public void setACCOUNT_TYPE_ID(String ACCOUNT_TYPE_ID) {
        this.ACCOUNT_TYPE_ID = ACCOUNT_TYPE_ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getIS_ACTIVATED() {
        return IS_ACTIVATED;
    }

    public void setIS_ACTIVATED(String IS_ACTIVATED) {
        this.IS_ACTIVATED = IS_ACTIVATED;
    }

    @Override
    public String toString() {
        return "ClassPojo [FULLNAME = " + FULLNAME + ", USER_ID = " + USER_ID + ", ACCOUNT_ID = " + ACCOUNT_ID + ", ACCOUNT_TYPE_ID = " + ACCOUNT_TYPE_ID + ", EMAIL = " + EMAIL + ", IS_ACTIVATED = " + IS_ACTIVATED + "]";
    }
}
