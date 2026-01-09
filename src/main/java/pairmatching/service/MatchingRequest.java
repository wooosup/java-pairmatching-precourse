package pairmatching.service;

import pairmatching.model.Course;
import pairmatching.model.Level;
import pairmatching.model.Mission;

public class MatchingRequest {
    private final Course course;
    private final Level level;
    private final Mission mission;

    public MatchingRequest(Course course, Level level, Mission mission) {
        this.course = course;
        this.level = level;
        this.mission = mission;
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
}
