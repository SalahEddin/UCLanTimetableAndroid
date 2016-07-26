package uclancyprusguide.inspirecenter.org.uclantimetable.data;

/**
 * Created by salah on 26/07/16.
 */
public class JSONRoom {
    private String ROOM_TYPE_ID;

    private String ROOM_ID;

    private String CAPACITY;

    private String ROOM_CODE;

    private String BOOKABLE;

    private String BARCODE;

    private String ROOM_TYPE_NAME;

    private String BOOKABLE_DESCRIPTION;

    public String getROOM_TYPE_ID() {
        return ROOM_TYPE_ID;
    }

    public void setROOM_TYPE_ID(String ROOM_TYPE_ID) {
        this.ROOM_TYPE_ID = ROOM_TYPE_ID;
    }

    public String getROOM_ID() {
        return ROOM_ID;
    }

    public void setROOM_ID(String ROOM_ID) {
        this.ROOM_ID = ROOM_ID;
    }

    public String getCAPACITY() {
        return CAPACITY;
    }

    public void setCAPACITY(String CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    public String getROOM_CODE() {
        return ROOM_CODE;
    }

    public void setROOM_CODE(String ROOM_CODE) {
        this.ROOM_CODE = ROOM_CODE;
    }

    public String getBOOKABLE() {
        return BOOKABLE;
    }

    public void setBOOKABLE(String BOOKABLE) {
        this.BOOKABLE = BOOKABLE;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getROOM_TYPE_NAME() {
        return ROOM_TYPE_NAME;
    }

    public void setROOM_TYPE_NAME(String ROOM_TYPE_NAME) {
        this.ROOM_TYPE_NAME = ROOM_TYPE_NAME;
    }

    public String getBOOKABLE_DESCRIPTION() {
        return BOOKABLE_DESCRIPTION;
    }

    public void setBOOKABLE_DESCRIPTION(String BOOKABLE_DESCRIPTION) {
        this.BOOKABLE_DESCRIPTION = BOOKABLE_DESCRIPTION;
    }

    @Override
    public String toString() {
        return "ClassPojo [ROOM_TYPE_ID = " + ROOM_TYPE_ID + ", ROOM_ID = " + ROOM_ID + ", CAPACITY = " + CAPACITY + ", ROOM_CODE = " + ROOM_CODE + ", BOOKABLE = " + BOOKABLE + ", BARCODE = " + BARCODE + ", ROOM_TYPE_NAME = " + ROOM_TYPE_NAME + ", BOOKABLE_DESCRIPTION = " + BOOKABLE_DESCRIPTION + "]";
    }
}
