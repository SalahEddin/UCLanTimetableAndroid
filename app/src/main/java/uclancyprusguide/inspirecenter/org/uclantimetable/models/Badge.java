package uclancyprusguide.inspirecenter.org.uclantimetable.models;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by salah on 19/07/16.
 */
public class Badge implements Serializable {

    @SerializedName("BADGE_ID")
    @Expose
    private String bADGEID;
    @SerializedName("BADGE_NAME")
    @Expose
    private String bADGENAME;
    @SerializedName("BADGE_DESCRIPTION")
    @Expose
    private String bADGEDESCRIPTION;
    @SerializedName("BADGE_URL")
    @Expose
    private String bADGEURL;
    @SerializedName("BADGE_CALCULATION")
    @Expose
    private String bADGECALCULATION;
    @SerializedName("BADGE_TRIGGER")
    @Expose
    private String bADGETRIGGER;
    @SerializedName("CAMPAIGN_ID")
    @Expose
    private String cAMPAIGNID;
    @SerializedName("CAMPAIGN_NAME")
    @Expose
    private String cAMPAIGNNAME;
    @SerializedName("PROGRAMME_NAME")
    @Expose
    private String pROGRAMMENAME;
    @SerializedName("PROGRAMME_CODE")
    @Expose
    private String pROGRAMMECODE;
    @SerializedName("CREATE_DATE")
    @Expose
    private String cREATEDATE;
    @SerializedName("PREDECESSOR_BADGE_ID")
    @Expose
    private Integer pREDECESSORBADGEID;

    public String getbADGEID() {
        return bADGEID;
    }

    public void setbADGEID(String bADGEID) {
        this.bADGEID = bADGEID;
    }

    public String getbADGENAME() {
        return bADGENAME;
    }

    public void setbADGENAME(String bADGENAME) {
        this.bADGENAME = bADGENAME;
    }

    public String getbADGEDESCRIPTION() {
        return bADGEDESCRIPTION;
    }

    public void setbADGEDESCRIPTION(String bADGEDESCRIPTION) {
        this.bADGEDESCRIPTION = bADGEDESCRIPTION;
    }

    public String getbADGEURL() {
        return bADGEURL;
    }

    public void setbADGEURL(String bADGEURL) {
        this.bADGEURL = bADGEURL;
    }

    public String getbADGECALCULATION() {
        return bADGECALCULATION;
    }

    public void setbADGECALCULATION(String bADGECALCULATION) {
        this.bADGECALCULATION = bADGECALCULATION;
    }

    public String getbADGETRIGGER() {
        return bADGETRIGGER;
    }

    public void setbADGETRIGGER(String bADGETRIGGER) {
        this.bADGETRIGGER = bADGETRIGGER;
    }

    public String getcAMPAIGNID() {
        return cAMPAIGNID;
    }

    public void setcAMPAIGNID(String cAMPAIGNID) {
        this.cAMPAIGNID = cAMPAIGNID;
    }

    public String getcAMPAIGNNAME() {
        return cAMPAIGNNAME;
    }

    public void setcAMPAIGNNAME(String cAMPAIGNNAME) {
        this.cAMPAIGNNAME = cAMPAIGNNAME;
    }

    public String getpROGRAMMENAME() {
        return pROGRAMMENAME;
    }

    public void setpROGRAMMENAME(String pROGRAMMENAME) {
        this.pROGRAMMENAME = pROGRAMMENAME;
    }

    public String getpROGRAMMECODE() {
        return pROGRAMMECODE;
    }

    public void setpROGRAMMECODE(String pROGRAMMECODE) {
        this.pROGRAMMECODE = pROGRAMMECODE;
    }

    public String getcREATEDATE() {
        return cREATEDATE;
    }

    public void setcREATEDATE(String cREATEDATE) {
        this.cREATEDATE = cREATEDATE;
    }

    public Integer getpREDECESSORBADGEID() {
        return pREDECESSORBADGEID;
    }

    public void setpREDECESSORBADGEID(Integer pREDECESSORBADGEID) {
        this.pREDECESSORBADGEID = pREDECESSORBADGEID;
    }
}
