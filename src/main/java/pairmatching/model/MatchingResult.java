package pairmatching.model;

import java.util.List;

public class MatchingResult {
    private final Course course;
    private final Level level;
    private final Mission mission;
    private final List<Pair> pairs;

    private MatchingResult(Course course, Level level, Mission mission, List<Pair> pairs) {
        this.course = course;
        this.level = level;
        this.mission = mission;
        this.pairs = pairs;
    }

    public static MatchingResult of(Course course, Level level, Mission mission, List<Pair> pairs) {
        return new MatchingResult(course, level, mission, pairs);
    }

    // 특정 Course, Level, Mission의 것인지 확인
    public boolean isMatch(Course course, Level level, Mission mission) {
        return this.course == course && this.level == level && this.mission == mission;
    }

    // 같은 레벨인지 확인 (중복 검사용)
    public boolean isSameLevel(Course course, Level level) {
        return this.course == course && this.level == level;
    }

    // 새로운 페어 목록과 비교해서 겹치는 페어가 있는지 확인
    public boolean hasDuplicatePair(List<Pair> newPairs) {
        for (Pair newPair : newPairs) {
            boolean isDuplicate = pairs.stream()
                    .anyMatch(pair -> pair.isSamePair(newPair));
            if (isDuplicate) {
                return true;
            }
        }
        return false;
    }

    public Course getCourse() {
        return course;
    }

    public Level getLevel() {
        return level;
    }

    public Mission getMission() {
        return mission;
    }

    public List<Pair> getPairs() {
        return pairs;
    }
}
