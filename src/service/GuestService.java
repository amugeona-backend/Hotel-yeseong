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
    private final UserService userService = new UserService();
    private final ProductRoomService productRoomService = new ProductRoomService();

    public GuestService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    Scanner sc = new Scanner(System.in);
    public void displayGuestMode() {
        while (true) {
            System.out.println("----------Guest Mode----------");
            System.out.println("1. 로그인");
            System.out.println("2. 회원 가입");
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
        phoneNumberContext();
        String phoneNumber = sc.next();

        if (hotelService.validatePhoneNumber(phoneNumber)) {
            if (hotelService.existPhoneNumber(phoneNumber)) {
                Guest guest = hotelService.findGuestByPhoneNumber(phoneNumber);
                displayGuestService(guest);
            } else {
                System.out.println("존재하지 않는 핸드폰 번호 입니다.");
            }
        } else {
            System.out.println("\n핸드폰 번호의 입력이 올바르지 않습니다.");
        }
    }

    private static void phoneNumberContext() {
        System.out.println("ex) 000-0000-0000");
        System.out.println("핸드폰 번호를 입력해주세요.");
        System.out.println("핸드폰 번호 : ");
    }

    private void signUp() {
        System.out.println("JAVA 호텔 회원 가입");
        System.out.println("자신의 정보를 입력하세요.");
        System.out.println("이름 : ");
        String name = sc.next();
        phoneNumberContext();
        String phoneNumber = sc.next();

        if (hotelService.validatePhoneNumber(phoneNumber)) {
            if (!hotelService.existPhoneNumber(phoneNumber)) {
                hotelService.addGuest(new Guest(name, phoneNumber));
                System.out.println("\n회원 가입이 완료되었습니다.");
            } else {
                System.out.println("\n이미 존재하는 핸드폰 번호입니다.");
                signUp();
            }
        } else {
            System.out.println("\n핸드폰 번호의 입력이 올바르지 않습니다!");
            signUp();
        }
    }

    private void displayGuestService(Guest guest) {
        System.out.println();
        System.out.println("반갑습니다. " + guest.getName() + "님");
        System.out.println("----------회원 모드----------");
        System.out.println("1. 호텔 예약하기");
        System.out.println("2. 예약한 호텔 조회하기");
        System.out.println("3. 포인트 충전하기");
        System.out.println("4. 포인트 조회하기");
        System.out.println("5. 예약 취소하기");
        System.out.println("6. 포인트 환전하기");
        System.out.println("7. 로그아웃");
        serviceInputHandling(guest);
    }

    private void serviceInputHandling(Guest guest) {
        System.out.println();
        System.out.println("입력 : ");
        int command = sc.nextInt();

        switch (command) {
            case 1 -> showAvailableDays(guest);
            case 2 -> findReservedHotel(guest);
            case 3 -> chargePoint(guest);
            case 4 -> getGuestPoint(guest);
            case 5 -> showReservation(guest);
            case 6 -> exchangePoint(guest);
            case 7 -> logout(guest);
            default -> {
                System.out.println("다시 입력하세요/");
                displayGuestService(guest);
            }
        }
    }

    private void showAvailableDays(Guest guest) {
        List<LocalDate> availableDays = hotelService.findAvailableDays();
        System.out.println("예약 하실 날짜를 선택해주세요.");

        for (int i = 0; i < availableDays.size(); i++) {
            System.out.printf("%2d, %10s\n", i + 1, availableDays.get(i));
        }
        System.out.println("0. 뒤로 가기");
        int command = sc.nextInt();

        if (command == 0) {
            displayGuestService(guest);
        } else if (command >= 1 && command <= availableDays.size()) {
            showAvailableRoom(command, availableDays, guest);
        } else {
            System.out.println("다시 입력하세요.");
            showAvailableDays(guest);
        }
    }

    private void showAvailableRoom(int command, List<LocalDate> availableDays,Guest guest) {
        LocalDate date = availableDays.get(command - 1);
        List<ProductRoom> productRooms = hotelService.findProductRoomByDate(date);
        System.out.println("----------" + date.toString() + "----------");
        System.out.println("예약하실 객실을 선택해주세요.");
        System.out.println();

        for (int i = 0; i < productRooms.size(); i++) {
            ProductRoom productRoom = productRooms.get(i);
            String isReserved = productRoom.isReserved() ? "예약 가능" : "예약 불가능";
            System.out.printf("%2d. %4d호 | %-8s | %-6d ₩ | %-8s\n", i + 1, productRoom.getRoomNumber(),
                    productRoom.getRoomType(), productRoom.getCost(), isReserved);
        }
        System.out.println("0. 뒤로 가기");
        int roomCommand = sc.nextInt();

        if (roomCommand == 0) {
            showAvailableDays(guest);
        } else if (roomCommand >= 1 && roomCommand <= productRooms.size()) {
            ProductRoom productRoom = productRooms.get(roomCommand - 1);
            if (productRoom.isReserved()) {
                System.out.println("이미 예약된 방입니다.");
                showAvailableRoom(command, availableDays, guest);
            } else {
                selectRoom(guest, productRoom);
            }
        } else {
            System.out.println("다시 입력하세요.");
            showAvailableRoom(command, availableDays, guest);
        }
    }

    private void selectRoom(Guest guest, ProductRoom productRoom) {
        if (productRoom.getCost() <= guest.getPoint()) {
            System.out.println("-------------------------");
            System.out.printf("%-4d호 | %-8s | %-6d\n",
                    productRoom.getRoomNumber(),
                    productRoom.getRoomType(),
                    productRoom.getCost());
            System.out.println("예약 하시겠습니까?");
            System.out.println();
            System.out.println("1. 확인         2. 취소");
            int command = sc.nextInt();

            switch (command) {
                case 1 -> reservationHotel(guest, productRoom);
                case 2 -> displayGuestService(guest);
                default -> {
                    System.out.println("다시 입력하세요.");
                    displayGuestService(guest);
                }
            }
        } else {
            System.out.println(guest.getName() + "님 포인트가 부족합니다.");
            System.out.println("1. 포인트 충전     2. 취소");
            int command = sc.nextInt();

            switch (command) {
                case 1 -> chargePoint(guest);
                case 2 -> displayGuestService(guest);
                default -> {
                    System.out.println("error");
                    displayGuestService(guest);
                }
            }
        }
    }

    private void reservationHotel(Guest guest, ProductRoom productRoom) {
        Reservation reservation = new Reservation(productRoom, guest.getName(),
                guest.getPhoneNumber(), LocalDateTime.now());
        int roomCost = productRoom.getCost();
        hotelService.addReservation(reservation);
        userService.deductPoint(guest, roomCost);
        productRoomService.changeReservationState(productRoom, true);
        System.out.println("예약이 완료되었습니다.");
        displayGuestService(guest);
    }

    private void findReservedHotel(Guest guest) {
        String guestPhoneNumber = guest.getPhoneNumber();
        List<Reservation> reservations = hotelService.findReservationByPhoneNumber(guestPhoneNumber);
        System.out.println("-------------------------");
        showReservationHandling(reservations);
        System.out.println("");
        System.out.println("메인 화면으로 돌아갑니다.");
        displayGuestService(guest);
    }

    private static boolean showReservationHandling(List<Reservation> reservations) {
        if (reservations.size() == 0) {
            System.out.println("조회 가능한 예약이 없습니다.");
            return false;
        }
        int idx = 1;
        for (Reservation reservation : reservations) {
            ProductRoom productRoom = reservation.productRoom();
            System.out.printf("%2d. %10s | %3d호 | %8s | 예약한 시간 : %-15s\n",
                    idx++,
                    productRoom.getReservedDate().toString(),
                    productRoom.getRoomNumber(),
                    productRoom.getRoomType(),
                    reservation.createdAt().toString());
        }
        return true;
    }

    private void chargePoint(Guest guest) {
        System.out.println("충전할 포인트를 입력해주세요.");
        int point = sc.nextInt();
        userService.chargePoint(guest, point);
        hotelService.addAsset(point);
        System.out.println("-------------------------");
        System.out.println("충전이 완료되었습니다.");
        System.out.println(guest.getName() + "님 현재 잔액 : " + guest.getPoint() + " ₩");
        displayGuestService(guest);
    }

    private void getGuestPoint(Guest guest) {
        String guestName = guest.getName();
        int point = guest.getPoint();
        System.out.println(guestName + "님의 현재 포인트 : " + point + " ₩ 입니다.");
        System.out.println("1. 포인트 충전하기     2. 뒤로 가기");
        int command = sc.nextInt();

        switch (command) {
            case 1 -> chargePoint(guest);
            case 2 -> displayGuestService(guest);
            default -> {
                System.out.println("error");
                displayGuestService(guest);
            }
        }
    }

    private void showReservation(Guest guest) {
        String guestPhoneNumber = guest.getPhoneNumber();
        List<Reservation> reservations = hotelService.findReservationByPhoneNumber(guestPhoneNumber);
        System.out.println("-------------------------");

        if (showReservationHandling(reservations)) {
            int command = sc.nextInt();

            if (command >= 1 && command <= reservations.size()) {
                Reservation reservation = reservations.get(command - 1);
                cancelReservation(guest, reservation);
            }
        } else {
            displayGuestService(guest);
        }
    }

    private void cancelReservation(Guest guest, Reservation reservation) {
        ProductRoom productRoom = reservation.productRoom();
        System.out.printf("%10s | %3d호 | %8s | 예약한 시간 : %-15s\n",
                productRoom.getReservedDate().toString(),
                productRoom.getRoomNumber(),
                productRoom.getRoomType(),
                reservation.createdAt().toString());
        System.out.println();
        System.out.println("정말 취소하시겠습니까?");
        System.out.println("1. 예약 취소     2. 뒤로 가기");
        int command = sc.nextInt();

        if (command == 1) {
            int roomCost = productRoom.getCost();
            hotelService.cancelReservation(reservation);
            userService.chargePoint(guest, roomCost);
            productRoomService.changeReservationState(productRoom, false);
            System.out.println("예약이 취소되었습니다.");
        }
        displayGuestService(guest);
    }
    private void exchangePoint(Guest guest) {
        String guestName = guest.getName();
        int point = guest.getPoint();

        System.out.println(guestName + "님의 현재 포인트 : " + point + " ₩ 입니다.");
        System.out.println();
        System.out.println("환전할 금액을 입력해주세요.");
        int money = sc.nextInt();

        if (money > point) {
            System.out.println("최대 환전 금액은 " + point + " ₩ 입니다.");
            exchangePoint(guest);
        } else {
            System.out.println("환전이 완료되었습니다.");
            hotelService.deductAsset(money);
            userService.deductPoint(guest, money);
            displayGuestService(guest);
        }
    }

    private void logout(Guest guest) {
        System.out.println("로그아웃 되었습니다.");
    }
}