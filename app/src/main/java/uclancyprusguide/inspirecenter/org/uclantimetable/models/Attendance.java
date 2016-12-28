package uclancyprusguide.inspirecenter.org.uclantimetable.models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by salah on 27/12/2016.
 */

public class Attendance implements Serializable {
    @SerializedName("MODULE_ID")
    @Expose
    private Integer mODULEID;
    @SerializedName("MODULE_CODE")
    @Expose
    private Object mODULECODE;
    @SerializedName("MODULE_NAME")
    @Expose
    private Object mODULENAME;
    @SerializedName("MODULE_INFO")
    @Expose
    private String mODULEINFO;
    @SerializedName("SESSION_DATE")
    @Expose
    private String sESSIONDATE;
    @SerializedName("STUDENT_ID")
    @Expose
    private Integer sTUDENTID;
    @SerializedName("STUDENT_NAME")
    @Expose
    private Object sTUDENTNAME;
    @SerializedName("STUDENT_INFO")
    @Expose
    private String sTUDENTINFO;
    @SerializedName("USERNAME")
    @Expose
    private Object uSERNAME;
    @SerializedName("GNUMBER")
    @Expose
    private Object gNUMBER;
    @SerializedName("INTERNAL_STATUS")
    @Expose
    private Integer iNTERNALSTATUS;
    @SerializedName("ATTENDANCE_STATUS")
    @Expose
    private Object aTTENDANCESTATUS;
    @SerializedName("SESSION_TYPE_NAME")
    @Expose
    private Object sESSIONTYPENAME;
    @SerializedName("GROUP_NAME")
    @Expose
    private Object gROUPNAME;
    @SerializedName("ATTENDANCE_PERCENTAGE")
    @Expose
    private Double aTTENDANCEPERCENTAGE;
    @SerializedName("ATTENDANCE_AVERAGE")
    @Expose
    private Double aTTENDANCEAVERAGE;
    @SerializedName("PARTICIPATION")
    @Expose
    private Integer pARTICIPATION;
    @SerializedName("ATTENDANCES")
    @Expose
    private Integer aTTENDANCES;
    @SerializedName("ABSENSES")
    @Expose
    private Integer aBSENSES;
    private final static long serialVersionUID = -2059134643575108832L;

    public Integer getMODULEID() {
        return mODULEID;
    }

    public void setMODULEID(Integer mODULEID) {
        this.mODULEID = mODULEID;
    }

    public Object getMODULECODE() {
        return mODULECODE;
    }

    public void setMODULECODE(Object mODULECODE) {
        this.mODULECODE = mODULECODE;
    }

    public Object getMODULENAME() {
        return mODULENAME;
    }

    public void setMODULENAME(Object mODULENAME) {
        this.mODULENAME = mODULENAME;
    }

    public String getMODULEINFO() {
        return mODULEINFO;
    }

    public void setMODULEINFO(String mODULEINFO) {
        this.mODULEINFO = mODULEINFO;
    }

    public String getSESSIONDATE() {
        return sESSIONDATE;
    }

    public void setSESSIONDATE(String sESSIONDATE) {
        this.sESSIONDATE = sESSIONDATE;
    }

    public Integer getSTUDENTID() {
        return sTUDENTID;
    }

    public void setSTUDENTID(Integer sTUDENTID) {
        this.sTUDENTID = sTUDENTID;
    }

    public Object getSTUDENTNAME() {
        return sTUDENTNAME;
    }

    public void setSTUDENTNAME(Object sTUDENTNAME) {
        this.sTUDENTNAME = sTUDENTNAME;
    }

    public String getSTUDENTINFO() {
        return sTUDENTINFO;
    }

    public void setSTUDENTINFO(String sTUDENTINFO) {
        this.sTUDENTINFO = sTUDENTINFO;
    }

    public Object getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(Object uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public Object getGNUMBER() {
        return gNUMBER;
    }

    public void setGNUMBER(Object gNUMBER) {
        this.gNUMBER = gNUMBER;
    }

    public Integer getINTERNALSTATUS() {
        return iNTERNALSTATUS;
    }

    public void setINTERNALSTATUS(Integer iNTERNALSTATUS) {
        this.iNTERNALSTATUS = iNTERNALSTATUS;
    }

    public Object getATTENDANCESTATUS() {
        return aTTENDANCESTATUS;
    }

    public void setATTENDANCESTATUS(Object aTTENDANCESTATUS) {
        this.aTTENDANCESTATUS = aTTENDANCESTATUS;
    }

    public Object getSESSIONTYPENAME() {
        return sESSIONTYPENAME;
    }

    public void setSESSIONTYPENAME(Object sESSIONTYPENAME) {
        this.sESSIONTYPENAME = sESSIONTYPENAME;
    }

    public Object getGROUPNAME() {
        return gROUPNAME;
    }

    public void setGROUPNAME(Object gROUPNAME) {
        this.gROUPNAME = gROUPNAME;
    }

    public Double getATTENDANCEPERCENTAGE() {
        return aTTENDANCEPERCENTAGE;
    }

    public void setATTENDANCEPERCENTAGE(Double aTTENDANCEPERCENTAGE) {
        this.aTTENDANCEPERCENTAGE = aTTENDANCEPERCENTAGE;
    }

    public Double getATTENDANCEAVERAGE() {
        return aTTENDANCEAVERAGE;
    }

    public void setATTENDANCEAVERAGE(Double aTTENDANCEAVERAGE) {
        this.aTTENDANCEAVERAGE = aTTENDANCEAVERAGE;
    }

    public Integer getPARTICIPATION() {
        return pARTICIPATION;
    }

    public void setPARTICIPATION(Integer pARTICIPATION) {
        this.pARTICIPATION = pARTICIPATION;
    }

    public Integer getATTENDANCES() {
        return aTTENDANCES;
    }

    public void setATTENDANCES(Integer aTTENDANCES) {
        this.aTTENDANCES = aTTENDANCES;
    }

    public Integer getABSENSES() {
        return aBSENSES;
    }

    public void setABSENSES(Integer aBSENSES) {
        this.aBSENSES = aBSENSES;
    }
}
