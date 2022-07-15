package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDBTest {

    @BeforeEach
    void beforeEach() {
        Util.File.deleteDir("test_data");
        Util.File.mkdir("test_data");
    }

    @Test
    void 특정_폴더에_들어있는_JSON_파일들만_모아서_객체화_시킨_후_리스트에_담기() {
        WiseSaying wiseSaying1 = new WiseSaying(1, "내 사전에 불가능은 없다.", "나폴레옹");
        WiseSaying wiseSaying2 = new WiseSaying(2, "나의 죽음을 적들에게 알리지 마라.", "이순신");

        Util.File.saveToFile("test_data/1.json", wiseSaying1.toJson());
        Util.File.saveToFile("test_data/2.json", wiseSaying2.toJson());
        Util.File.saveNumberToFile("test_data/last_id.txt", 2);


        List<String> fileNames = Util.File.getFileNamesFromDir("test_data")
                .stream() // 리스트를 스트림으로 변환
                .filter(fileName -> fileName.endsWith(".json"))
                .toList();// 스트림을 다시 리스트로 변환

        List<WiseSaying> wiseSayings = fileNames
                .stream()
                .map(fileName -> Util.Json.jsonToMap("test_data/" + fileName))
                .filter(Objects::nonNull)
                .map(WiseSaying::new)
                .toList();

//        List<WiseSaying> wiseSayings = new ArrayList<>();
//        for (String fileName : fileNames) {
//            String rs = Util.readFromFile("test_data/" + fileName);
//            Map<String, Object> map = Util.jsonToMap(rs);
//            WiseSaying wiseSaying = new WiseSaying(map);
//            wiseSayings.add(wiseSaying);
//        }
//
//        assertEquals(wiseSaying1, wiseSayings.get(0));
//        assertEquals(wiseSaying2, wiseSayings.get(1));
    }

    @Test
    void 특정_폴더에_존재하는_모든_파일의_이름들을_가져온다() {
        Util.File.saveNumberToFile("test_data/1.txt", 1);
        Util.File.saveNumberToFile("test_data/2.txt", 2);
        Util.File.saveNumberToFile("test_data/3.txt", 3);

        List<String> fileNames = Util.File.getFileNamesFromDir("test_data");

        assertEquals("1.txt", fileNames.get(0));
        assertEquals("2.txt", fileNames.get(1));
        assertEquals("3.txt", fileNames.get(2));
    }

    @Test
    void 파일에_숫자_저장() {
        Util.File.saveNumberToFile("test_data/last_id.txt", 100);
        int rs = Util.File.readNumberFromFile("test_data/last_id.txt", 0);

        assertEquals(100, rs);
    }

    @Test
    void 파일에_있는_JSON을_객체로_변환() {
        WiseSaying wiseSaying = new WiseSaying(1, "내 사전에 불가능은 없다.", "나폴레옹");
        Util.File.saveToFile("test_data/1.json", wiseSaying.toJson());

        String rs = Util.File.readFromFile("test_data/1.json");
        Map<String, Object> map = Util.Json.jsonToMap(rs);
        WiseSaying loadedWiseSaying = new WiseSaying(map);

        assertEquals(1, map.get("id"));
        assertEquals("내 사전에 불가능은 없다.", map.get("content"));
        assertEquals(wiseSaying, loadedWiseSaying);
    }

    @Test
    void 파일에_있는_JSON을_맵으로_변환() {
        WiseSaying wiseSaying = new WiseSaying(1, "내 사전에 불가능은 없다.", "나폴레옹");
        Util.File.saveToFile("test_data/1.json", wiseSaying.toJson());

        String rs = Util.File.readFromFile("test_data/1.json");
        Map<String, Object> map = Util.Json.jsonToMap(rs);

        assertEquals(1, map.get("id"));
        assertEquals("내 사전에 불가능은 없다.", map.get("content"));
        assertEquals("나폴레옹", map.get("author"));
    }

    @Test
    void 파일에_객체를_저장() {
        WiseSaying wiseSaying = new WiseSaying(1, "내 사전에 불가능은 없다.", "나폴레옹");
        Util.File.saveToFile("test_data/1.json", wiseSaying.toJson());

        String rs = Util.File.readFromFile("test_data/1.json");

        assertEquals(wiseSaying.toJson(), rs);
    }

    @Test
    void 파일에_내용쓰기() {
        Util.File.saveToFile("test_data/1.json", "내용\n내용");

        String rs = Util.File.readFromFile("test_data/1.json");

        assertEquals("내용\n내용", rs);
    }

    @Test
    void 추출() {
        String s = Util.File.readFromFile("./prod_data/test");

        String s1 = s.replace("\"id\":", "");
        String s2 = s1.replace("\"content\":\"", "");
        String s3 = s2.replace("\"author\":\"", "");
        String s4 = s3.replaceAll("[\\{\\,\\}\"]", "");
        String trim = s4.trim();


//        System.out.println("s3 = " + s3);
        System.out.println("s = " + trim);
        Util.File.saveToFile("./prod_data/init", trim);
    }

    @Test
    void 등록() {
        String s = Util.File.readFromFile("./prod_data/init");
        AppTestRunner.run(s);
    }
}
