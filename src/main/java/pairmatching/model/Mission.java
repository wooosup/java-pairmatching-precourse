package pairmatching.model;

import static pairmatching.model.Level.*;

import java.util.Arrays;

public enum Mission {
    RACING("자동차경주", LEVEL1),
    LOTTO("로또", LEVEL1),
    BASEBALL("숫자야구게임", LEVEL1),
    CART("장바구니", LEVEL2),
    PAYMENT("결제", LEVEL2),
    SUBWAY("지하철노선도", LEVEL2),
    PERFORMANCES("성능개선", LEVEL4),
    DEPLOYMENT("배포", LEVEL4);

    private final String description;
    private final Level level;

    Mission(String description, Level level) {
        this.description = description;
        this.level = level;
    }

    public static Mission findByNameAndLevel(String name, Level level) {
        return Arrays.stream(values())
                .filter(mission -> mission.description.equals(name) && mission.level == level)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 레벨에 존재하지 않는 미션입니다."));
    }
}

