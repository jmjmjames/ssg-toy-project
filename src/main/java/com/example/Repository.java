package com.example;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private final List<WiseSaying> wiseSayingList;
    private int wiseSayingLastId;

    Repository() {
        wiseSayingList = new ArrayList<>();
        wiseSayingLastId = 0;
    }

    public WiseSaying findById(int paramId) {
        for (WiseSaying wiseSaying : wiseSayingList) {
            if (wiseSaying.getId() == paramId) {
                return wiseSaying;
            }
        }
        return null;
    }

    public List<WiseSaying> findAll() {
        return wiseSayingList;
        // 다 찾기
    }

    public WiseSaying write(String content, String author) {
        int id = ++wiseSayingLastId;
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        wiseSayingList.add(wiseSaying);

        // 파일저장

        return wiseSaying;
    }

    public void remove(int paramId) {
        WiseSaying foundWiseSaying = findById(paramId);
        wiseSayingList.remove(foundWiseSaying);

        // 파일 삭제
    }

    public void modify(int paramId, String content, String author) {
        WiseSaying foundWiseSaying = findById(paramId);
        foundWiseSaying.setContent(content);
        foundWiseSaying.setAuthor(author);

        // 파일 수정
    }
}
