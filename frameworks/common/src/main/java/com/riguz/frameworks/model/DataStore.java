package com.riguz.frameworks.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    private DataStore() {

    }

    public static final DataStore instance = new DataStore();

    static {
        try {
            instance.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<String, AppInfo> data = new HashMap<>();

    public AppInfo get(String app) {
        return data.get(app);
    }

    private void load() throws IOException {
        List<String> apps = readResource("apps.txt");
        List<String> events = readResource("data.txt");
        for (int i = 0; i < apps.size(); i++) {
            data.put(apps.get(i), new AppInfo(apps.get(i), events.get(i)));
        }
    }

    private static List<String> readResource(String name) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        DataStore.class.getClassLoader().getResourceAsStream(name)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
