package com.example;

import java.util.Scanner;

public class Controller {
    private Scanner sc;
    private Repository repository;
    public Controller(Scanner sc) {
        this.sc = sc;
        repository = new Repository();
    }


    public void write(Request request) {
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();
        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        int id = repository.getWiseSayingLastId() + 1;
        repository.setWiseSayingLastId(id);

        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        repository.getWiseSayingList().add(wiseSaying);

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    public void list(Request request) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-------------------");

        for (int i = repository.getWiseSayingList().size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = repository.getWiseSayingList().get(i);
            System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
        }
    }

    public void modify(Request request) {
        // URL 입력된 id 값
        int paramId = request.getIntParam("id", 0);

        // URL에 입력된 id가 없다면 작업 중지
        if (paramId == 0) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        // URL에 입력된 id에 해당하는 명언 객체 찾기
        WiseSaying foundWiseSaying = repository.findById(paramId);

        // 찾지 못했다면 중지
        if (foundWiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", paramId);
            return;
        }

        System.out.printf("명언(기존) : %s\n", foundWiseSaying.getContent());
        System.out.printf("명언 : ");
        foundWiseSaying.setContent(sc.nextLine());
        System.out.printf("작가(기존) : %s\n", foundWiseSaying.getAuthor());
        System.out.printf("작가 : ");
        foundWiseSaying.setAuthor(sc.nextLine());

        System.out.printf("%d번 명언이 수정되었습니다.\n", paramId);
    }

    public void remove(Request request) {
        // URL 입력된 id 값
        int paramId = request.getIntParam("id", 0);

        // URL에 입력된 id가 없다면 작업 중지
        if (paramId == 0) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        // URL에 입력된 id에 해당하는 명언 객체 찾기
        WiseSaying foundWiseSaying = repository.findById(paramId);

        // 찾지 못했다면 중지
        if (foundWiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.%n", paramId);
            return;
        }

        // 입력된 id에 해당하는 명언객체를 리스트에서 삭제
        repository.getWiseSayingList().remove(foundWiseSaying);

        System.out.printf("%d번 명언이 삭제되었습니다.%n", paramId);
    }
}
