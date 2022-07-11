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
    public void run() {

        System.out.println("== 명언 SSG ==");

        Scanner sc = new Scanner(System.in);

        // 가장 마지막 명언글의 번호
        List<WiseSaying> wiseSayings = new ArrayList<>();
        int wiseSayingLastId = 0;

        outer:
        while (true) {
            System.out.printf("명령) ");
            String cmd = sc.nextLine().trim();

            Request request = new Request(cmd);
            switch (request.getPath()) {
                case "등록":
                    System.out.printf("명언 : ");
                    String content = sc.nextLine().trim();
                    System.out.printf("작가 : ");
                    String author = sc.nextLine().trim();
                    int id = ++wiseSayingLastId; // 명언 글 번호 증가

                    WiseSaying wiseSaying = new WiseSaying(id, content, author);
                    wiseSayings.add(wiseSaying);

                    System.out.printf("%d번 명언이 등록되었습니다.\n", id);
                    break;
                case "목록":
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("-------------------");
                    for (int i = wiseSayings.size() - 1; i >= 0; i--) {
                        WiseSaying wiseSaying_ = wiseSayings.get(i);
                        System.out.printf("%d / %s / %s%n", wiseSaying_.getId(), wiseSaying_.getContent(), wiseSaying_.getAuthor());
                    }
                    break;
                case "삭제":
                    // URL 입력된 id 값
                    int paramId = request.getIntParam("id", 0);

                    // URL에 입력된 id가 없다면 작업 중지
                    if (paramId == 0) {
                        System.out.println("id를 입력해주세요.");
                        continue;
                    }

                    // URL에 입력된 id에 해당하는 명언 객체 찾기
                    WiseSaying wSaying = null;

                    for (WiseSaying saying : wiseSayings) {
                        if (saying.getId() == paramId) {
                            wSaying = saying;
                        }
                    }

                    // 찾지 못했다면 중지
                    if (wSaying == null) {
                        System.out.printf("%d번 명언은 존재하지 않습니다.%n", paramId);
                        continue;
                    }

                    // 입력된 id에 해당하는 명언객체를 리스트에서 삭제
                    wiseSayings.remove(wSaying);

                    System.out.printf("%d번 명언이 삭제되었습니다.%n", paramId);
                case "종료":
                    break outer;
            }
        }

        sc.close();
    }
}
