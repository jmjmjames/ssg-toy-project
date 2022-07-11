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
    private final List<WiseSaying> wiseSayingList;
    private int wiseSayingLastId;

    public App() {
        this.sc = new Scanner(System.in);
        this.wiseSayingList = new ArrayList<>();
        this.wiseSayingLastId = 0;
    }

    public void run() {

        System.out.println("== 명언 SSG ==");

        outer:
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            Request request = new Request(cmd);
            switch (request.getPath()) {
                case "등록":
                    write(request);
                    break;
                case "목록":
                    list(request);
                    break;
                case "삭제":
                    remove(request);
                    break;
                case "종료":
                    break outer;
            }
        }
        sc.close();
    }

    private void write(Request request) {
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();
        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        int id = ++wiseSayingLastId;

        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        wiseSayingList.add(wiseSaying);

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    private void list(Request request) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-------------------");

        for (int i = wiseSayingList.size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
        }
    }

    private void remove(Request request) {
        // URL 입력된 id 값
        int paramId = request.getIntParam("id", 0);

        // URL에 입력된 id가 없다면 작업 중지
        if (paramId == 0) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        // URL에 입력된 id에 해당하는 명언 객체 찾기
        WiseSaying foundWiseSaying = null;

        for (WiseSaying wiseSaying : wiseSayingList) {
            if (wiseSaying.getId() == paramId) {
                foundWiseSaying = wiseSaying;
            }
        }

        // 찾지 못했다면 중지
        if (foundWiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", paramId);
            return;
        }

        // 입력된 id에 해당하는 명언객체를 리스트에서 삭제
        wiseSayingList.remove(foundWiseSaying);

        System.out.printf("%d번 명언이 삭제되었습니다.%n", paramId);
    }
}
