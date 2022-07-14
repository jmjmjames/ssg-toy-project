package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {
    public static class File {
        public static void saveToFile(String path, String body) {

            new java.io.File(path).delete();

            try (RandomAccessFile stream = new RandomAccessFile(path, "rw");
                 FileChannel channel = stream.getChannel()) {
                byte[] strBytes = body.getBytes();
                ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
                buffer.put(strBytes);
                buffer.flip();
                channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void mkdir(String path) {
            java.io.File dir = new java.io.File(path);
            dir.mkdirs();
        }

        public static String readFromFile(String path) {

            String defaultValue = "";

            try (RandomAccessFile reader = new RandomAccessFile(path, "r")) {
                StringBuilder sb = new StringBuilder();
                String line;
                boolean isFirst = true;

                while ((line = reader.readLine()) != null) {
                    if (!isFirst) {
                        sb.append("\n");
                    }
                    sb.append(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                    isFirst = false;
                }
                return sb.toString();
            } catch (FileNotFoundException e) {
                return defaultValue;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static void deleteDir(String path) {
            Path rootPath = Paths.get(path);
            try (Stream<Path> walk = Files.walk(rootPath)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(java.io.File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void saveNumberToFile(String path, int number) {
            saveToFile(path, number + "");
        }

        public static int readNumberFromFile(String path, int defaultValue) {
            String rs = readFromFile(path);

            if (rs.isEmpty()) {
                return defaultValue;
            }

            return Integer.parseInt(rs);
        }

        public static List<String> getFileNamesFromDir(String path) {
            try (Stream<Path> stream = Files.walk(Paths.get(path), 1)) {
                return stream
                        .filter(file -> !Files.isDirectory(file))
                        .map(Path::getFileName)
                        .map(Path::toString).sorted()
                        .collect(Collectors.toList());
            } catch (IOException e) {
                return new ArrayList<>();
            }
        }
    }

    public static class Json {
        public static Map<String, Object> jsonToMap(String path) {

            String json = File.readFromFile(path);

            if (json.isEmpty()) {
                return null;
            }

            final String[] jsonBits = json
                    .replaceAll("\\{", "")
                    .replaceAll("\\}", "")
                    .split(",");

            final List<Object> bits = Stream.of(jsonBits)
                    .map(String::trim)
                    .flatMap(bit -> Arrays.stream(bit.split(":")))
                    .map(String::trim)
                    .map(s -> s.startsWith("\"") ? s.substring(1, s.length() - 1) : Integer.parseInt(s))
                    .collect(Collectors.toList());

            Map<String, Object> map = IntStream
                    .range(0, bits.size() / 2)
                    .mapToObj(i -> Pair.of((String) bits.get(i * 2), bits.get(i * 2 + 1)))
                    .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue(), (key1, key2) -> key1, LinkedHashMap::new));

            return map;
        }
    }

    static class Pair {
        // Return a map entry (key-value pair) from the specified values
        public static <T, U> Map.Entry<T, U> of(T first, U second) {
            return new AbstractMap.SimpleEntry<>(first, second);
        }
    }
}
