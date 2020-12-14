import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class File {

    protected String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the string content of the current file
     * @return String file content
     */
    public String readFileContent() {
        InputStream stream = getClass().getResourceAsStream(this.fileName);
        return new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));
    }

    /**
     * Returns the line number of the researched string
     * @param string The string researched
     * @return The line number
     */
    public int getLineNumberOfString(String string) {
        List<String> fileContent =  new ArrayList<String>(Arrays.asList(string.split("\n")));
        int indexNumber = 0;
        return fileContent.indexOf(string);
    }


}
