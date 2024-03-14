package service;

import model.Guest;

public class UserService {
    public void chargePoint(Guest guest, int point) {
        guest.setPoint(guest.getPoint() + point);
    }
    public void deductPoint(Guest guest, int point) {
        guest.setPoint(guest.getPoint() - point);
    }
}
