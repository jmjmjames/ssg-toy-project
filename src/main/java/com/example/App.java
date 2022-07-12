package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * == 명언 SSG ==
 * 명령) 등록
 * 명언 : 현재를 사랑하라.
 * 작가 : 작자미상
 * 1번 명언이 등록되었습니다.
 * 명령) 등록
 * 명언 : 과거에 집착하지 마라.
 * 작가 : 작자미상
 * 2번 명언이 등록되었습니다.
 * 명령) 목록
 * 번호 / 작가 / 명언
 * ----------------------
 * 2 / 작자미상 / 과거에 집착하지 마라.
 * 1 / 작자미상 / 현재를 사랑하라.
 * 명령) 삭제?id=1
 * 1번 명언이 삭제되었습니다.
 * 명령) 삭제?id=1
 * 1번 명언은 존재하지 않습니다.
 * 명령) 수정?id=2
 * 2번 명언을 수정합니다.
 * 기존 명언 : 과거에 집착하지 마라.
 * 새 명언 : 미래와 과거에 집착하지 마라.
 * 2번 명언이 수정되었습니다.
 * 명령) 목록
 * 번호 / 작가 / 명언
 * ----------------------
 * 2 / 작자미상 / 미래와 과거에 집착하지 마라.
 */
public class App {

    private final Scanner sc;

    public App() {
        this.sc = new Scanner(System.in);
    }

    public void run() {

        System.out.println("== 명언 SSG ==");

        Controller controller = new Controller(sc);

        outer:
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            Request request = new Request(cmd);
            switch (request.getPath()) {
                case "등록":
                    controller.write(request);
                    break;
                case "목록":
                    controller.list(request);
                    break;
                case "수정":
                    controller.modify(request);
                    break;
                case "삭제":
                    controller.remove(request);
                    break;
                case "종료":
                    break outer;
            }
        }
        sc.close();
    }
}
