package pairmatching.controller;

import com.sun.tools.javac.Main;
import pairmatching.model.MatchingResult;
import pairmatching.service.PairService;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

import java.util.Optional;

public class PairController {

    public static final String QUIT = "Q";
    public static final String REMATCH_YES = "네";
    public static final String REMATCH_NO = "아니오";
    private final InputView inputView;
    private final OutputView outputView;
    private final PairService pairService;

    public PairController(InputView inputView, OutputView outputView, PairService pairService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.pairService = pairService;
    }

    public void run() {
        while (true) {
            try {
                outputView.printStart();
                String userInput = inputView.selectFunction();

                if (userInput.equals(QUIT)) {
                    break;
                }
                MainMenu.run(userInput, this);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public void runMatching() {
        outputView.printPairMatching();
        while (true) {
            try {
                String input = inputView.selectPairMatching();

                if (requiresRematch(input) && !confirmRematch()) {
                        continue;
                    }

                processMatching(input);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processMatching(String input) {
        MatchingResult result = pairService.matching(input);
        outputView.printMatchingResult(result);
    }

    private boolean confirmRematch() {
        String answer = inputView.reMatching();
        if (REMATCH_YES.equals(answer)) {
            return true;
        }
        if (REMATCH_NO.equals(answer)) {
            return false;
        }
        throw new IllegalArgumentException("[ERROR] 네 / 아니오 중 하나를 입력해주세요.");
    }

    private boolean requiresRematch(String input) {
        return pairService.getMatchingResult(input).isPresent();
    }

    public void runLookup() {
        outputView.printPairMatching();
        while (true) {
            try {
                String input = inputView.selectPairMatching();
                Optional<MatchingResult> result = pairService.getMatchingResult(input);

                if (result.isPresent()) {
                    outputView.printMatchingResult(result.get());
                } else {
                    outputView.printError();
                }
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public void clear() {
        pairService.clear();
        outputView.printClear();
    }
}