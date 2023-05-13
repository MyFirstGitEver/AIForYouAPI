package projections;

import java.util.Date;

public class SenderWithMaxDate {
    private int idNguoiGui;
    private Date sentDate;

    public SenderWithMaxDate(int idNguoiGui, Date sentDate) {
        this.idNguoiGui = idNguoiGui;
        this.sentDate = sentDate;
    }

    public SenderWithMaxDate(int idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public int getIdNguoiGui() {
        return idNguoiGui;
    }

    public Date getSentDate() {
        return sentDate;
    }
}