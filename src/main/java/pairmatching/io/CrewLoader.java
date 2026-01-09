package pairmatching.io;

import static java.nio.charset.StandardCharsets.*;
import static pairmatching.model.Course.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.model.Course;

public class CrewLoader {

    public static final String BACKEND_CREW_FILE = "backend-crew.md";
    public static final String FRONTED_CREW_FILE = "frontend-crew.md";

    public List<String> readBackendCrew() {
        return readFile(BACKEND_CREW_FILE);
    }

    public List<String> readFrontendCrew() {
        return readFile(FRONTED_CREW_FILE);
    }

    private List<String> readFile(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("[ERROR] 파일을 찾을 수 없습니다: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, UTF_8))) {
            return br.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽는 중 오류가 발생했습니다: " + fileName);
        }
    }

    public List<String> getCrewNamesByCourse(Course course) {
        if (course.equals(BACKEND)) {
            return readBackendCrew();
        }
        return readFrontendCrew();
    }
}
