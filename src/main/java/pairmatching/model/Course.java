package pairmatching.model;

import java.util.Arrays;

public enum Course {
  BACKEND("백엔드"),
  FRONTEND("프론트엔드");

  private final String description;

  Course(String description) {
    this.description = description;
  }

    public static Course findByName(String name) {
        return Arrays.stream(values())
                .filter(course -> course.description.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 과정입니다."));
    }
}