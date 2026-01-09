package pairmatching.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pair {
    private final List<String> crews;

    private Pair(List<String> crews) {
        validate(crews);
        this.crews = crews;
    }

    public static Pair of(List<String> crews) {
        return new Pair(crews);
    }

    public boolean isSamePair(Pair other) {
        Set<String> thisSet = new HashSet<>(this.crews);
        Set<String> otherSet = new HashSet<>(other.crews);

        thisSet.retainAll(otherSet);
        return thisSet.size() >= 2;
    }

    private void validate(List<String> crews) {
        if (crews.size() < 2 || crews.size() > 3) {
            throw new IllegalArgumentException("[ERROR] 페어는 2명 또는 3명이어야 합니다.");
        }
    }

    @Override
    public String toString() {
        return String.join(" : ", crews);
    }
}
