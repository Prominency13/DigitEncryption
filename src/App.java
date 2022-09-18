import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        
        // Создание файла
        File file = new File("encryptingTable.txt");
        if (file.createNewFile()){
        System.out.println("File is created!");
        } else{
        System.out.println("File already exists.");
        }
        try (FileWriter writer = new FileWriter(file)) {
            //Задаётся латинский алфавит и составляется собственная таблица кодов
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            int[][] digits = new int[26][3]; //Сначала пишется количество рядов, затем количество столбцов
            int[][] counters = new int[26][1]; //Задается массив счётчиков
            int firstEncryptionDigit = 501;
            for (int i = 0; i < digits.length; i++) {
                for (int j = 0; j < digits[i].length; j++) {
                    digits[i][j] = firstEncryptionDigit;
                    counters[i][0] = 0;
                    writer.write("| " + alphabet.charAt(i) + " | " + digits[i][j] + " | ");
                    System.out.print(alphabet.charAt(i) + " " + digits[i][j] + " ");
                    firstEncryptionDigit++;
                }
                writer.write('\n');
                System.out.println(" ");
            }
            
            System.out.println(" ");
            // Заканчивается алгоритм работы заполнения массивов

            // Ввод слова 
            System.out.println("Введите слово для зашифровки");
            String toEncrypt = s.nextLine();

            // Создание и заполнение массива индексов алфавита в введённом сообщении
            int[] wordsIndexes = new int[toEncrypt.length()];
            int wordIndexesCounter = 0;
            for (int i = 0; i < toEncrypt.length(); i++) {
                for (int j = 0; j < alphabet.length(); j++) {
                    if(toEncrypt.charAt(i) == alphabet.charAt(j)){
                        wordsIndexes[wordIndexesCounter] = j;
                        wordIndexesCounter++;
                    }
                }
            }
            // Конец метода заполнения массива индексов

            // Шифрование сообщения
            int[] encryptedMessage = new int[toEncrypt.length()];
            for (int i = 0; i < encryptedMessage.length; i++) {
                for (int j = 0; j < digits.length; j++) {
                    for (int j2 = 0; j2 < digits[j].length; j2++) {
                        //Важное условие проверки счётчика повторяющихся букв. Если счётчик меньше 3х, то программа продолжает работать как обычно
                        if(counters[j][0]<=2 && j == wordsIndexes[i] && encryptedMessage[i]==0){ 
                            encryptedMessage[i] = digits[j][counters[j][0]];
                            counters[j][0]++;
                            System.out.println(Arrays.toString(encryptedMessage));
                        } else if (counters[j][0]==3 && j == wordsIndexes[i] && encryptedMessage[i]==0){
                            encryptedMessage[i] = digits[j][j2];
                            counters[j][0] = 0;
                        }
                    }
                }
            }
            System.out.println(Arrays.toString(encryptedMessage));
            writer.write("Зашифрованное сообщение: " + encryptedMessage.toString());
            // Завершение шифрования

            // Дешифр
            // Запись индексов зашифрованного сообщения
            String decryptedMessage = new String();
            int[] decryptedMessageIndexes = new int[encryptedMessage.length];
            for (int i = 0; i < encryptedMessage.length; i++) {
                for (int j = 0; j < digits.length; j++) {
                    for (int j2 = 0; j2 < digits[j].length; j2++) {
                        if(encryptedMessage[i] == digits[j][j2]){
                            decryptedMessageIndexes[i] = j;
                        }
                    }
                }
            }
            
            // Дешифр сообщения на основании массива индексов
            for (int i = 0; i < decryptedMessageIndexes.length; i++) {
                for (int j = 0; j < alphabet.length(); j++) {
                    if(decryptedMessageIndexes[i] == j){
                        decryptedMessage += alphabet.charAt(j);
                    }
                }
            }
            System.out.println(decryptedMessage);
            writer.close();
        }
    }
    
}
