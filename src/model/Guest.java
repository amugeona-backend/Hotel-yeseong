package model;

import static constant.GuestConstant.GUEST_DEFAULT_MONEY;
public class Guest {
    private String name;
    private String phoneNumber;
    private int money = GUEST_DEFAULT_MONEY;

    public Guest (String name, String PhoneNumber) {
        this.name = name;
        this.phoneNumber = PhoneNumber;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney (int money) {
        this.money = money;
    }
}