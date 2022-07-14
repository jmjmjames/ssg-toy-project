package com.example;

import java.util.List;
import java.util.stream.Collectors;

public class WiseSayingService {
    private WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository(App.getBaseDir());
    }

    public WiseSaying write(String content, String author) {
        return wiseSayingRepository.save(content, author);
    }

    public boolean modify(int id, String content, String author) {
        return wiseSayingRepository.update(id, content, author);
    }

    public boolean remove(int id) {
        return wiseSayingRepository.removeById(id);
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }

    public void dumpToJson() {
        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll();

        String json = "[\n\t" + wiseSayings
                .stream()
                .map(WiseSaying::toJson)
                .collect(Collectors.joining(", ")) + "\n]";

        Util.File.saveToFile("%s/data.json".formatted(App.getBaseDir()), json);
    }
}
