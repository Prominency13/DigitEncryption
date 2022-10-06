import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@JUnit
public class Tests {

    @Test
    public void testReadFile() {
        String path = "\\message.txt";
        File messageFile = new File(path);
        String sourceContent = Test.readSourceFile(path);
        assertEqual("test123", sourceContent);
    }

    @Test
    public void testCalculateCountForEachCharMap() {
        //
        String sourceContent = "";
    }

    @Test
    public Map<Character, Integer> calculateCountForEachCharMap()

    @Test
    public void testMax() {
        String sourceContent = readSourceFile(String path);
        Map<Character, Integer> countForEachCharMap = calculateCountForEachCharMap(sourceContent);
        Map<Character, Float> frequencyForEachCharMap = calculateFrequencyForEachCharMap(countForEachCharMap);
        Map<Character, int[]> keyTableContent = generateKeyTable(frequencyForEachCharMap);
        String encryptedContent = encryptContent(sourceContent, keyTableContent);
        String decryptedContent = decryptContent(encryptedContent, keyTableContent);
        writeToFile(filePath, keyTableContent);
        writeToFile(filePath, encryptContent);
        writeToFile(filePath, decryptedContent);
    }
}
