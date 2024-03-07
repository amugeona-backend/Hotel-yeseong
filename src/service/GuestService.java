package service;

import model.ProductRoom;
import model.Reservation;
import model.Guest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class GuestService {
    private final HotelService hotelService;
    private final GuestService guestService = new GuestService();
    private final ProductRoomService productRoomService = new ProductRoom();

    public GuestService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public void displayGuestMode() {
        while (true) {
            System.out.println("----------Guest Mode----------");
            System.out.println("1. 로그인");
            System.out.println("2. 회원 가입");
            Scanner sc = new Scanner(System.in);
            int command = sc.nextInt();
            if (command == 0) {
                break;
            } else if (command == 1) {
                signIn();
            } else if (command == 2) {
                signUp();
            } else {
                System.out.println("error");
            }
        }
    }
    private void signIn() {
        System.out.println("JAVA 호텔 로그인");

    }
}
