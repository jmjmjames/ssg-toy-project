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

    public List<WiseSaying> getWiseSayingList() {
        return wiseSayingList;
    }

    public int getWiseSayingLastId() {
        return wiseSayingLastId;
    }

    public void setWiseSayingLastId(int wiseSayingLastId) {
        this.wiseSayingLastId = wiseSayingLastId;
    }
}
