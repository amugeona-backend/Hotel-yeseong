package controller;

import service.GuestService;
import service.HotelService;
import service.ManagerService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static service.HotelService.getHotelService;
import static util.UtilContext.errorMessage;
import static util.UtilContext.sc;

public class HotelController {
    HotelService hotelService = getHotelService();
    GuestService guestService = new getGuestService(hotelService);
    ManagerService manageService = new ManagerService(hotelService);
}
