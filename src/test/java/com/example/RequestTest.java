package com.example;

import org.junit.jupiter.api.Test;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    @Test
    void getIntParam() {
        Request request = new Request("삭제?id=1");
        int id = request.getIntParam("id", 0);

        assertEquals(1, id);
    }

    @Test
    void getIntParam_2() {
        Request request = new Request("검색?id=7&no=11");
        int id = request.getIntParam("id", 0);
        int no = request.getIntParam("no", 0);

        assertEquals(7, id);
        assertEquals(11, no);
    }

    @Test
    void getPath() {
        Request request = new Request("삭제?id=1");
        String path = request.getPath();

        assertEquals("삭제", path);
    }

    @Test
    void urlTest() {
        Request request = new Request("삭제?id=1");
        Map<String, String> map = request.getQueryParams();

        assertTrue(map.containsKey("id"));
        assertEquals(map.get("id"), "1");
    }
}