package com.sep.pcc.model;

import java.util.Date;

public class RequestTest {

    private Long id;
    private Long aco;
    private Date act;
    private String PAN;
    private String cvc;
    private String chn;
    private Date exp;
    private Double amount;

    public RequestTest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAco() {
        return aco;
    }

    public void setAco(Long aco) {
        this.aco = aco;
    }

    public Date getAct() {
        return act;
    }

    public void setAct(Date act) {
        this.act = act;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getChn() {
        return chn;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
