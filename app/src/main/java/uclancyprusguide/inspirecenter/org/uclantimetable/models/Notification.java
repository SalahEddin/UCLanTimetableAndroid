package uclancyprusguide.inspirecenter.org.uclantimetable.models;

import java.io.Serializable;

/**
 * Created by salah on 29/07/16.
 * JSON response
 */
public class Notification implements Serializable {
    private String EXPIRY_DATE;

    private String NOTIFICATION_STATUS;

    private String NOTIFICATION_TYPE_NAME;

    private String USER_ID;

    private String CREATE_DATE;

    private String PUBLISH_DATE;

    private String NOTIFICATION_TITLE;

    private String IMPORTANT;

    private String NOTIFICATION_TYPE_ID;

    private String NOTIFICATION_ID;

    private String NOTIFICATION_URL;

    private String NOTIFICATION_TEXT;

    public String getEXPIRY_DATE() {
        return EXPIRY_DATE;
    }

    public void setEXPIRY_DATE(String EXPIRY_DATE) {
        this.EXPIRY_DATE = EXPIRY_DATE;
    }

    public String getNOTIFICATION_STATUS() {
        return NOTIFICATION_STATUS;
    }

    public void setNOTIFICATION_STATUS(String NOTIFICATION_STATUS) {
        this.NOTIFICATION_STATUS = NOTIFICATION_STATUS;
    }

    public String getNOTIFICATION_TYPE_NAME() {
        return NOTIFICATION_TYPE_NAME;
    }

    public void setNOTIFICATION_TYPE_NAME(String NOTIFICATION_TYPE_NAME) {
        this.NOTIFICATION_TYPE_NAME = NOTIFICATION_TYPE_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getPUBLISH_DATE() {
        return PUBLISH_DATE;
    }

    public void setPUBLISH_DATE(String PUBLISH_DATE) {
        this.PUBLISH_DATE = PUBLISH_DATE;
    }

    public String getNOTIFICATION_TITLE() {
        return NOTIFICATION_TITLE;
    }

    public void setNOTIFICATION_TITLE(String NOTIFICATION_TITLE) {
        this.NOTIFICATION_TITLE = NOTIFICATION_TITLE;
    }

    public String getIMPORTANT() {
        return IMPORTANT;
    }

    public void setIMPORTANT(String IMPORTANT) {
        this.IMPORTANT = IMPORTANT;
    }

    public String getNOTIFICATION_TYPE_ID() {
        return NOTIFICATION_TYPE_ID;
    }

    public void setNOTIFICATION_TYPE_ID(String NOTIFICATION_TYPE_ID) {
        this.NOTIFICATION_TYPE_ID = NOTIFICATION_TYPE_ID;
    }

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getNOTIFICATION_URL() {
        return NOTIFICATION_URL;
    }

    public void setNOTIFICATION_URL(String NOTIFICATION_URL) {
        this.NOTIFICATION_URL = NOTIFICATION_URL;
    }

    public String getNOTIFICATION_TEXT() {
        return NOTIFICATION_TEXT;
    }

    public void setNOTIFICATION_TEXT(String NOTIFICATION_TEXT) {
        this.NOTIFICATION_TEXT = NOTIFICATION_TEXT;
    }

    @Override
    public String toString() {
        return "ClassPojo [EXPIRY_DATE = " + EXPIRY_DATE + ", NOTIFICATION_STATUS = " + NOTIFICATION_STATUS + ", NOTIFICATION_TYPE_NAME = " + NOTIFICATION_TYPE_NAME + ", USER_ID = " + USER_ID + ", CREATE_DATE = " + CREATE_DATE + ", PUBLISH_DATE = " + PUBLISH_DATE + ", NOTIFICATION_TITLE = " + NOTIFICATION_TITLE + ", IMPORTANT = " + IMPORTANT + ", NOTIFICATION_TYPE_ID = " + NOTIFICATION_TYPE_ID + ", NOTIFICATION_ID = " + NOTIFICATION_ID + ", NOTIFICATION_URL = " + NOTIFICATION_URL + ", NOTIFICATION_TEXT = " + NOTIFICATION_TEXT + "]";
    }

    public boolean isRead() {
        int status = Integer.parseInt(NOTIFICATION_STATUS);
        byte byteStatus = (byte) status;
        return (byteStatus & 0b00000001) == 1;
    }

    public boolean isDeleted() {
        int status = Integer.parseInt(NOTIFICATION_STATUS);
        byte byteStatus = (byte) status;
        return (byteStatus >> 1 & 0b00000001) == 1;
    }

    public boolean isArchived() {
        int status = Integer.parseInt(NOTIFICATION_STATUS);
        byte byteStatus = (byte) status;
        return (byteStatus >> 2 & 0b00000001) == 1;
    }
}
