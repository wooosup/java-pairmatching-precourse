package pairmatching;

import pairmatching.controller.PairController;
import pairmatching.io.CrewLoader;
import pairmatching.repository.PairMatchingRepository;
import pairmatching.service.PairService;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        PairService pairService = new PairService(new PairMatchingRepository(), new CrewLoader());

        PairController controller = new PairController(inputView, outputView, pairService);

        controller.run();
    }
}
