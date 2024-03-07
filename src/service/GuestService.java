package service;

import model.ProductRoom;
import model.Reservation;
import model.Guest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static util.UtilContext.backMessage;
import static util.UtilContext.errorMessage;
import static util.UtilContext.line;
import static util.UtilContext.linewithText;
import static util.Utilcontext.sc;

public class GuestService {
    private final HotelService hotelService;
    private final GuestService guestService = new GuestService();
    private final ProductRoomService productRoomService = new ProductRoom();

    public GuestService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public void displayGuestMode() {
        while (true) {
            lineWithText("Guest Mode");
            System.out.println("1. 로그인");
            System.out.println("2. 회원 가입");
            backMessage();
            int command = sc.nextInt();
            if (command == 0) {
                break;
            } else if (command == 1) {
                signIn();
            } else if (command == 2) {
                signUp();
            } else {
                errorMessage();
            }
        }
    }

}
