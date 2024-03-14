package controller;

import service.GuestService;
import service.HotelService;
import service.ManagerService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static service.HotelService.getHotelService;

public class HotelController {
    HotelService hotelService = getHotelService();
    GuestService guestService = new GuestService(hotelService);
    ManagerService manageService = new ManagerService(hotelService);

    public void run() {
        hotelService.initRoom();
        displayMode();
    }

    public void displayMode() {
        System.out.println("환영합니다. JAVA 호텔 입니다.");
        modeInputHandling();
    }

    public void modeInputHandling() {
        System.out.println();
        System.out.println("Mode를 선택해주세요.");
        System.out.println("1. Guest Mode");
        System.out.println("2. Manager Mode");
        Scanner sc = new Scanner(System.in);
        int command = sc.nextInt();

        switch (command) {
            case 1 -> { // Guest Mode
                guestService.displayGuestMode();
                modeInputHandling();
            }
            case 2 -> { // Manager Mode
                if (passwordCheck()) {
                    manageService.displayManagerMode();
                    modeInputHandling();
                } else {
                    passwordNotMatchMessage();
                    modeInputHandling();
                }
            }
            default -> { // error
                System.out.println("다시 입력하세요.");;
                modeInputHandling();
            }
        }
    }

    public boolean passwordCheck() { // 비밀번호 확인
        System.out.println("관리자 비밀번호를 입력해주세요 : ");
        Scanner sc = new Scanner(System.in);
        int cmd = sc.nextInt();
        try {
            return encryption(String.valueOf(sc.nextInt())).equals(hotelService.getHotelPassword());
        } catch (Exception e) {
            return false;
        }
    }

    public void passwordNotMatchMessage() { // 비밀번호 불일치
        System.out.println("비밀번호가 일치하지 않습니다.");
        System.out.println("시스템이 돌아갑니다.");
    }

    private String encryption(String password) throws NoSuchAlgorithmException {
        // 암호화. SHA-256:256bit(32byte) 해시 생성. 결과를 원래 값으로 해독할 수는 X
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        // 바이트로 해시된 데이터를 16진수 string 값으로 얻기 위한 변환기
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
