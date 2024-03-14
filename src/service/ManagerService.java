package service;

import model.ProductRoom;
import model.Reservation;

import java.time.LocalDate;
import java.util.Scanner;

public class ManagerService {
    private final HotelService hotelService;

    public ManagerService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    Scanner sc = new Scanner(System.in);
    public void displayManagerMode() {
        System.out.println("----------Manager Mode----------");
        System.out.println("1. 예약 현황");
        System.out.println("2. 자산 현황");
        System.out.println("3. 시스템 종료");
        System.out.println("입력 : ");
        int command = sc.nextInt();

        switch (command) {
            case 1 -> reservationStatus();
            case 2 -> assertStatus();
            case 3 -> logOut();
            default -> {
                System.out.println("다시 입력하세요.");
                displayManagerMode();
            }
        }
    }

    public void reservationStatus() { // 1. 예약 현황
        System.out.println();
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 빈객실 찾기");
        System.out.println("2. 예약 찾기");
        System.out.println("3. 오늘 객실 현황");
        System.out.println("4. 뒤로 가기");
        int command = sc.nextInt();

        switch (command) {
            case 1 -> findProductRoomByDate();
            case 2 -> findReservation();
            case 3 -> findReservationByToday();
            case 4 -> displayManagerMode();
            default -> {
                System.out.println("다시 입력하세요.");
                reservationStatus();
            }
        }
    }

    public void findProductRoomByDate() {
        showHotelDate();
        int command = sc.nextInt();

        if (command == 0) {
            reservationStatus();
        }
        else if (0 < command && command <= hotelService.findAvailableDays().size()) {
            findProductRoomIsReservedByDate(hotelService.findAvailableDays().get(command - 1));
        }
        else {
            System.out.println("error");
            findProductRoomByDate();
        }
    }

    public void findProductRoomIsReservedByDate(LocalDate day) {
        System.out.println();
        System.out.println("선택하신 날짜는 " + day + " 입니다.");
        for (ProductRoom room : hotelService.findEmptyProductRoomByDate(day)) {
            System.out.println(room.getRoomType());
        }
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        if (command == 0) {
            reservationStatus();
        } else {
            System.out.println("error");
            findProductRoomIsReservedByDate(day);
        }
    }

    public void findReservation() {
        System.out.println();
        System.out.println("방법을 선택해 주세요");
        System.out.println("1. 이름으로 찾기");
        System.out.println("2. 번호로 찾기");
        System.out.println("3. 날짜로 찾기");
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        switch (command) {
            case 1 -> findReservationByExistingName();
            case 2 -> findReservationByValidatePhoneNumber();
            case 3 -> findReservationByDayChoice();
            case 0 -> reservationStatus();
            default -> {
                System.out.println("다시 입력하세요.");
                findReservation();
            }
        }
    }

    public void findReservationByExistingName() {
        System.out.println();
        System.out.println("이름을 입력해 주세요!");
        String name = sc.next();

        if (hotelService.findReservationByExistingName(name)) {
            findReservationByName(name);
        } else {
            System.out.println("예약 내역이 없습니다.");
            findReservation();
        }
    }

    public void findReservationByName(String name) {
        for (Reservation reservation : hotelService.findReservationByName(name)) {
            System.out.println(reservation.productRoom().getReservedDate() + "\t"
            + reservation.productRoom().getRoomType() + "\t"
            + reservation.guestName() + "\t"
            + reservation.guestPhoneNumber());
        }
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        if  (command == 0) {
            findReservation();
        } else {
            System.out.println("error");
            findReservationByName(name);
        }
    }

    public void findReservationByValidatePhoneNumber() {
        System.out.println();
        System.out.println("번호를 입력해 주세요");
        System.out.println("ex) 000-0000-0000");
        String command = sc.next();

        if (hotelService.validatePhoneNumber(command)) {
            findReservationByExistingPhoneNumber(command);
        } else {
            System.out.println("\n핸드폰 번호의 입력이 올바르지 않습니다.");
            findReservationByValidatePhoneNumber();
        }
    }

    public void findReservationByExistingPhoneNumber(String phoneNumber) {
        if (hotelService.findReservationByExistingPhoneNumber(phoneNumber)) {
            findReservationByExistingPhoneNumber(phoneNumber);
        } else {
            System.out.println("예약된 핸드폰 번호가 없습니다.");
            findReservation();
        }
    }

    public void findReservationByPhoneNumber(String phoneNumber) {
        for (Reservation reservation : hotelService.findReservationByPhoneNumber(phoneNumber)) {
            System.out.println(reservation.productRoom().getReservedDate() + "\t"
            + reservation.productRoom().getRoomType() + "\t"
            + reservation.guestName() + "\t" + reservation.guestPhoneNumber());
        }
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        if (command == 0) {
            findReservation();
        } else {
            System.out.println("error");
            findReservation();
        }
    }

    public void findReservationByDayChoice() {
        showHotelDate();
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        if (command == 0) {
            findReservation();
        } else if (0 < command && command <= hotelService.findAvailableDays().size()) {
            findReservationByExistingDate(hotelService.findAvailableDays().get(command - 1));
        } else {
            System.out.println("error");
            findReservation();
        }
    }

    public void findReservationByExistingDate(LocalDate date) {
        System.out.println();
        System.out.println("선택하신 날짜는 " + date + " 입니다.");
        if (hotelService.findReservationByExistingDate(date)) {
            findReservationByDate(date);
        } else {
            System.out.println("예약된 정보가 없습니다.");
            reservationStatus();
        }
    }

    public void findReservationByDate(LocalDate date) {
        for (Reservation reservation : hotelService.findReservationByDate(date)) {
            System.out.println(reservation.productRoom().getRoomType() + "\t"
            + reservation.guestName() + "\t" + reservation.guestPhoneNumber());
        }
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        if (command == 0) {
            reservationStatus();
        } else {
            System.out.println("error");
            findReservation();
        }
    }

    public void findReservationByToday() {
        LocalDate date = LocalDate.now();
        findReservationByExistingDate(date);
    }

    public void assertStatus() {
        System.out.println("총 금액은 " + hotelService.getAsset() + "원 입니다.");
    }

    public void logOut() {
        System.out.println("로그아웃 되었습니다.");
    }

    private void showHotelDate() {
        int i = 1;
        System.out.println();
        System.out.println("날짜를 선택해 주세요.");
        for (LocalDate day : hotelService.findAvailableDays()) {
            System.out.printf("%d. %10s" + i++, day + "\n");
        }
    }
}
