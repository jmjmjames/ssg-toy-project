package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDBTest {

    @BeforeEach
    void beforeEach() {
        Util.File.deleteDir("test_data");
        Util.File.mkdir("test_data");
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
}
