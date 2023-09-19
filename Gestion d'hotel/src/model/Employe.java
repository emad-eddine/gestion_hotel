package model;

import java.io.Serializable;

public class Employe implements Serializable {

    private int employeId;
    private String employeName;
    private String employeBitrh;
    private String employeAddr;
    private String employePhone;
    private String employeUserName;
    private String employePassword;

    public Employe()
    {

    }

    public Employe(int employeId, String employeName, String employeBitrh, String employeAddr, String employePhone, String employeUserName, String employePassword) {
        this.employeId = employeId;
        this.employeName = employeName;
        this.employeBitrh = employeBitrh;
        this.employeAddr = employeAddr;
        this.employePhone = employePhone;
        this.employeUserName = employeUserName;
        this.employePassword = employePassword;
    }

    public int getEmployeId() {
        return employeId;
    }

    public void setEmployeId(int employeId) {
        this.employeId = employeId;
    }

    public String getEmployeName() {
        return employeName;
    }

    public void setEmployeName(String employeName) {
        this.employeName = employeName;
    }

    public String getEmployeBitrh() {
        return employeBitrh;
    }

    public void setEmployeBitrh(String employeBitrh) {
        this.employeBitrh = employeBitrh;
    }

    public String getEmployeAddr() {
        return employeAddr;
    }

    public void setEmployeAddr(String employeAddr) {
        this.employeAddr = employeAddr;
    }

    public String getEmployePhone() {
        return employePhone;
    }

    public void setEmployePhone(String employePhone) {
        this.employePhone = employePhone;
    }

    public String getEmployeUserName() {
        return employeUserName;
    }

    public void setEmployeUserName(String employeUserName) {
        this.employeUserName = employeUserName;
    }

    public String getEmployePassword() {
        return employePassword;
    }

    public void setEmployePassword(String employePassword) {
        this.employePassword = employePassword;
    }
}
