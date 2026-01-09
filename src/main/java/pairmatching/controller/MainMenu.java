package pairmatching.controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum MainMenu {
    MATCHING("1", "페어 매칭", PairController::runMatching),
    LOOKUP("2", "페어 조회", PairController::runLookup),
    CLEAR("3", "페어 초기화", PairController::clear);

    private final String command;
    private final String description;
    private final Consumer<PairController> action;

    MainMenu(String command, String description, Consumer<PairController> action) {
        this.command = command;
        this.description = description;
        this.action = action;
    }

    public static void run(String input, PairController controller) {
        Arrays.stream(values())
                .filter(mainMenu -> mainMenu.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 기능입니다."))
                .action.accept(controller);
    }
}
