package pairmatching.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pairmatching.io.CrewLoader;
import pairmatching.model.Course;
import pairmatching.model.Level;
import pairmatching.model.MatchingResult;
import pairmatching.model.Mission;
import pairmatching.model.Pair;
import pairmatching.repository.PairMatchingRepository;

public class PairService {

    public static final int MAX_TRY = 3;
    public static final int MIN_CREW_SIZE = 2;
    public static final int PAIR_SIZE = 2;
    private final PairMatchingRepository repository;
    private final CrewLoader loader;

    public PairService(PairMatchingRepository repository, CrewLoader loader) {
        this.repository = repository;
        this.loader = loader;
    }

    public MatchingResult matching(String input) {
        MatchingRequest request = parseRequest(input);
        List<String> crewNames = loader.getCrewNamesByCourse(request.getCourse());

        return matchWithRetry(crewNames, request);
    }

    public Optional<MatchingResult> getMatchingResult(String input) {
        MatchingRequest request = parseRequest(input);

        return repository.findByPairs(request.getCourse(), request.getLevel(), request.getMission());
    }

    private MatchingResult matchWithRetry(List<String> crewNames, MatchingRequest request) {
        for (int attempt = 0; attempt < MAX_TRY; attempt++) {
            List<Pair> pairs = createPairs(Randoms.shuffle(crewNames));

            if (!repository.hasDuplicateInLevel(request.getCourse(), request.getLevel(), pairs)) {
                MatchingResult result = MatchingResult.of(request.getCourse(), request.getLevel(),
                        request.getMission(), pairs);
                repository.save(result);
                return result;
            }
        }
        throw new IllegalArgumentException("[ERROR] 매칭에 실패했습니다.");
    }

    private MatchingRequest parseRequest(String input) {
        String[] tokens = splitAndTrim(input);
        Course course = Course.findByName(tokens[0]);
        Level level = Level.findByName(tokens[1]);
        Mission mission = Mission.findByNameAndLevel(tokens[2], level);
        return new MatchingRequest(course, level, mission);
    }

    private String[] splitAndTrim(String input) {
        validateNotNull(input);
        String[] tokens = input.split(",");
        validateTokenCount(tokens);
        return trimAndValidateTokens(tokens);
    }

    private static String[] trimAndValidateTokens(String[] tokens) {
        String[] trimmed = new String[3];
        for (int i = 0; i < 3; i++) {
            trimmed[i] = tokens[i].trim();
            if (trimmed[i].isEmpty()) {
                throw new IllegalArgumentException("[ERROR] 입력 값에 빈 값이 포함되어 있습니다.");
            }
        }
        return trimmed;
    }

    private static void validateTokenCount(String[] tokens) {
        if (tokens.length != 3) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 올바르지 않습니다. 예) 백엔드, 레벨1, 자동차경주");
        }
    }

    private static void validateNotNull(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력이 null 입니다.");
        }
    }

    private List<Pair> createPairs(List<String> shuffled) {
        validateCrewSize(shuffled.size());
        List<List<String>> groups = groupCrews(shuffled);
        return convertToPairs(groups);
    }

    private List<List<String>> groupCrews(List<String> shuffled) {
        List<List<String>> groups = new ArrayList<>();
        int size = shuffled.size();

        for (int i = 0; i + 1 < size; i += PAIR_SIZE) {
            groups.add(List.of(shuffled.get(i), shuffled.get(i + 1)));
        }

        if (size % PAIR_SIZE == 1) {
            groups.get(groups.size() - 1).add(shuffled.get(size - 1));
        }

        return groups;
    }

    private List<Pair> convertToPairs(List<List<String>> groups) {
        List<Pair> pairs = new ArrayList<>(groups.size());
        for (List<String> group : groups) {
            pairs.add(Pair.of(new ArrayList<>(group)));
        }
        return pairs;
    }

    private static void validateCrewSize(int size) {
        if (size < MIN_CREW_SIZE) {
            throw new IllegalArgumentException("[ERROR] 크루는 최소 2명이어야 합니다.");
        }
    }

    public void clear() {
        repository.clear();
    }
}
