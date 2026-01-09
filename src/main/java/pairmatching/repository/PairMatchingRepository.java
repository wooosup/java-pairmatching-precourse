package pairmatching.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import pairmatching.model.Course;
import pairmatching.model.Level;
import pairmatching.model.MatchingResult;
import pairmatching.model.Mission;
import pairmatching.model.Pair;

public class PairMatchingRepository {
    private final List<MatchingResult> matchingResults = new ArrayList<>();

    public void save(MatchingResult result) {
        matchingResults.removeIf(r -> r.isMatch(result.getCourse(), result.getLevel(), result.getMission()));
        matchingResults.add(result);
    }

    public boolean hasDuplicateInLevel(Course course, Level level, List<Pair> newPairs) {
        List<MatchingResult> levelHistory = matchingResults.stream()
                .filter(result -> result.isSameLevel(course, level))
                .collect(Collectors.toList());
        for (MatchingResult history : levelHistory) {
            if (history.hasDuplicatePair(newPairs)) {
                return true;
            }
        }
        return false;
    }

    public Optional<MatchingResult> findByPairs(Course course, Level level, Mission mission) {
        return matchingResults.stream()
                .filter(result -> result.isMatch(course, level, mission))
                .findFirst();
    }

    public void clear() {
        matchingResults.clear();
    }
}
