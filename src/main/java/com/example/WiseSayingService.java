package com.example;

import java.util.List;

public class WiseSayingService {
    private WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public WiseSaying write(String content, String author) {
        return wiseSayingRepository.add(content, author);
    }

    public boolean modify(int id, String content, String author) {
        return wiseSayingRepository.modify(id, content, author);
    }

    public boolean remove(int id) {
        return wiseSayingRepository.remove(id);
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }
}
