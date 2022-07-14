package com.example;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WiseSayingRepository {

    private String baseDir;

    public WiseSayingRepository(String baseDir) {
        this.baseDir = baseDir;
    }


    public void save(WiseSaying wiseSaying) {
        Util.File.mkdir("%s/wise_saying".formatted(baseDir));
        String body = wiseSaying.toJson();
        Util.File.saveToFile("%s/wise_saying/%d.json".formatted(baseDir, wiseSaying.getId()), body);
    }

    public WiseSaying save(String content, String author) {
        int id = getLastId() + 1;

        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        save(wiseSaying);

        saveLastId(id);

        return wiseSaying;
    }

    public boolean update(int id, String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(id, content, author);

        save(wiseSaying);
        return true;
    }

    private void saveLastId(int id) {
        Util.File.saveToFile("%s/wise_saying/last_id.txt".formatted(baseDir), id + "");
    }

    public List<WiseSaying> findAll() {
        String path = "%s/wise_saying".formatted(baseDir);
        List<String> fileNames = Util.File.getFileNamesFromDir(path);

        List<Integer> fileIds = fileNames
                .stream()
                .filter(fileName -> !fileName.equals("last_id.txt"))
                .map(fileName -> fileName.replace(".json", ""))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        return fileIds
                .stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public WiseSaying findById(int id) {
        String path = "%s/wise_saying/%d.json".formatted(baseDir, id);

        if (!(new File(path).exists())) return null;

        Map<String, Object> map = Util.Json.jsonToMap(path);

        if (map == null) return null;

        return new WiseSaying(map);
    }

    public int getLastId() {
        String lastId = Util.File.readFromFile("%s/wise_saying/last_id.txt".formatted(baseDir));

        if (lastId.isEmpty()) return 0;
        return Integer.parseInt(lastId);
    }

    public boolean removeById(int id) {
        String path = "%s/wise_saying/%d.json".formatted(baseDir, id);
        return new File(path).delete();
    }

}

