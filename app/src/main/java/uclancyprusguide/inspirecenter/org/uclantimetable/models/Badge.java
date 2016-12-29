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
    private Integer bADGEID;
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
    private Integer bADGETRIGGER;
    @SerializedName("CAMPAIGN_ID")
    @Expose
    private Integer cAMPAIGNID;
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
    @SerializedName("PREDECESSOR_BADGE_NAME")
    @Expose
    private String pREDECESSORBADGENAME;

    public Integer getBADGEID() {
        return bADGEID;
    }

    public void setBADGEID(Integer bADGEID) {
        this.bADGEID = bADGEID;
    }

    public String getBADGENAME() {
        return bADGENAME;
    }

    public void setBADGENAME(String bADGENAME) {
        this.bADGENAME = bADGENAME;
    }

    public String getBADGEDESCRIPTION() {
        return bADGEDESCRIPTION;
    }

    public void setBADGEDESCRIPTION(String bADGEDESCRIPTION) {
        this.bADGEDESCRIPTION = bADGEDESCRIPTION;
    }

    public String getBADGEURL() {
        return bADGEURL;
    }

    public void setBADGEURL(String bADGEURL) {
        this.bADGEURL = bADGEURL;
    }

    public String getBADGECALCULATION() {
        return bADGECALCULATION;
    }

    public void setBADGECALCULATION(String bADGECALCULATION) {
        this.bADGECALCULATION = bADGECALCULATION;
    }

    public Integer getBADGETRIGGER() {
        return bADGETRIGGER;
    }

    public void setBADGETRIGGER(Integer bADGETRIGGER) {
        this.bADGETRIGGER = bADGETRIGGER;
    }

    public Integer getCAMPAIGNID() {
        return cAMPAIGNID;
    }

    public void setCAMPAIGNID(Integer cAMPAIGNID) {
        this.cAMPAIGNID = cAMPAIGNID;
    }

    public String getCAMPAIGNNAME() {
        return cAMPAIGNNAME;
    }

    public void setCAMPAIGNNAME(String cAMPAIGNNAME) {
        this.cAMPAIGNNAME = cAMPAIGNNAME;
    }

    public String getPROGRAMMENAME() {
        return pROGRAMMENAME;
    }

    public void setPROGRAMMENAME(String pROGRAMMENAME) {
        this.pROGRAMMENAME = pROGRAMMENAME;
    }

    public String getPROGRAMMECODE() {
        return pROGRAMMECODE;
    }

    public void setPROGRAMMECODE(String pROGRAMMECODE) {
        this.pROGRAMMECODE = pROGRAMMECODE;
    }

    public String getCREATEDATE() {
        return cREATEDATE;
    }

    public void setCREATEDATE(String cREATEDATE) {
        this.cREATEDATE = cREATEDATE;
    }

    public Integer getPREDECESSORBADGEID() {
        return pREDECESSORBADGEID;
    }

    public void setPREDECESSORBADGEID(Integer pREDECESSORBADGEID) {
        this.pREDECESSORBADGEID = pREDECESSORBADGEID;
    }

    public String getPREDECESSORBADGENAME() {
        return pREDECESSORBADGENAME;
    }

    public void setPREDECESSORBADGENAME(String pREDECESSORBADGENAME) {
        this.pREDECESSORBADGENAME = pREDECESSORBADGENAME;
    }

}
