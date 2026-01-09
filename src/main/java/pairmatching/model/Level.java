package pairmatching.model;

import java.util.Arrays;

public enum Level {
    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private final String description;

    Level(String description) {
        this.description = description;
    }

    public static Level findByName(String name) {
        return Arrays.stream(values())
                .filter(level -> level.description.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 레벨입니다."));
    }
}