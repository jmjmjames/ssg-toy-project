package com.example;

import java.util.Scanner;

public class App {

    private final Scanner sc;
    public static String mode = "prod";

    public App(Scanner sc) {
        this.sc = sc;
    }

    public static String getBaseDir() {
        return mode + "_data";
    }

    public void run() {

        System.out.println("== 명언 SSG ==");

        WiseSayingController controller = new WiseSayingController(sc);

        outer:
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            Rq rq = new Rq(cmd);
            switch (rq.getPath()) {
                case "등록":
                    controller.write();
                    break;
                case "목록":
                    controller.list();
                    break;
                case "수정":
                    controller.modify(rq);
                    break;
                case "삭제":
                    controller.remove(rq);
                    break;
                case "빌드":
                    controller.build();
                    break;
                case "종료":
                    break outer;

            }
        }
        sc.close();
    }
}
