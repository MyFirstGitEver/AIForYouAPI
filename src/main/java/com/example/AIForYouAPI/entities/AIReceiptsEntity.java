package com.example.AIForYouAPI.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ai_receipts")
public class AIReceiptsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private int idNguoiMua, idModel;
    private Date receiptDate;

    public AIReceiptsEntity() {

    }

    public AIReceiptsEntity(AIModelEntity model, int idNguoiMua) {
        this.idNguoiMua = idNguoiMua;
        idModel = model.getId();
        receiptDate = new Date();
    }

    public int getIdNguoiMua() {
        return idNguoiMua;
    }

    public int getIdModel() {
        return idModel;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setIdNguoiMua(int idNguoiMua) {
        this.idNguoiMua = idNguoiMua;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }
}