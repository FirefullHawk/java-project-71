import hexlet.code.Differ;
import hexlet.code.Formatter;
import hexlet.code.Parser;
import hexlet.code.KeyDiff;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class MainTest {
    private static String expectedStylish;

    private static String expectedPlain;

    private static String expectedJSON;

    private static Path getAbsolutePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    private static String getDataFromFile(Path absoluteFilePath) throws Exception {
        return Files.readString(absoluteFilePath).trim();
    }

    @BeforeAll
    public static void expectedAnswer() throws Exception {
        String rightFileStylish = "./src/test/resources/rightFile/expected_stylish.txt";
        String rightFilePlain = "./src/test/resources/rightFile/expected_plain.txt";
        String rightFileJSON = "./src/test/resources/rightFile/expected_json.json";

        expectedStylish = getDataFromFile(getAbsolutePath(rightFileStylish));
        expectedPlain = getDataFromFile(getAbsolutePath(rightFilePlain));
        expectedJSON = getDataFromFile(getAbsolutePath(rightFileJSON));
    }

    @ParameterizedTest
    @ValueSource(strings = { "json", "yml" })
    public void workTestFormatStyle(String extension) throws Exception {
        String stylish = "stylish";
        String plain = "plain";
        String json = "json";

        String fileOne = "./src/test/resources/" + extension + "/file1." + extension;
        String fileTwo = "./src/test/resources/" + extension + "/file2." + extension;

        String actualDefault = Differ.generate(fileOne, fileTwo);
        assertEquals(expectedStylish, actualDefault);

        String actualStylish = Differ.generate(fileOne, fileTwo, stylish);
        assertEquals(expectedStylish, actualStylish);

        String actualPlain = Differ.generate(fileOne, fileTwo, plain);
        assertEquals(expectedPlain, actualPlain);

        String actualJSON = Differ.generate(fileOne, fileTwo, json);
        assertEquals(expectedJSON, actualJSON);
    }

    @Test
    public void testErrors() {
        List<KeyDiff> someList = new ArrayList<>();

        String someData = "";

        Throwable thrownDiffer = assertThrows(IOException.class, ()
                -> Differ.generate("fileOne", "fileTwo"));
        assertNotNull(thrownDiffer.getMessage());

        Throwable thrownParser = assertThrows(IOException.class, ()
                -> Parser.parser(someData, "son"));
        assertNotNull(thrownParser.getMessage());

        Throwable thrownFormatter = assertThrows(IOException.class, ()
                -> Formatter.format(someList, "lain"));
        assertNotNull(thrownFormatter.getMessage());
    }
}
