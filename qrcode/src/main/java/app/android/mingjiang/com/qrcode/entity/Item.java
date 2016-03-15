package app.android.mingjiang.com.qrcode.entity;

import java.util.Date;

/**
 * Created by SunYi on 2015/12/14/0014.
 */
public class Item {
    private String finsishCode;
    private String QRcode;
    private String status;
    private Date time;

    public Item() {

    }


    public Item(String finsishCode, String QRcode, String status, Date time) {
        this.finsishCode = finsishCode;
        this.QRcode = QRcode;
        this.status = status;
        this.time = time;

    }

    public String getFinsishCode() {
        return finsishCode;
    }

    public void setFinsishCode(String finsishCode) {
        this.finsishCode = finsishCode;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Item{" +
                "finsishCode='" + finsishCode + '\'' +
                ", QRcode='" + QRcode + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                '}';
    }
}
