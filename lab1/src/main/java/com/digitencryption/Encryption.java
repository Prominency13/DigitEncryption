package com.digitencryption;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Encryption {

    public String readFile() throws Exception{
        String path ="src\\message.txt";
        FileReader messageFile = new FileReader(path);
        try (Scanner scan = new Scanner(messageFile)) {
            String sourceContent = "";
                while(scan.hasNextLine()){
                    sourceContent+=scan.nextLine();
                }
            return sourceContent;
        }
    }

    public Map<Character, Integer> calculateCountForEachMap(String sourceContent) throws Exception{
        Map<Character, Integer> countForEachMap = new HashMap<>();
        for (int i = 0; i < sourceContent.length(); i++) {
            int counter = 0;
            char temp = Character.toLowerCase(sourceContent.charAt(i));
            for (int j = 0; j < sourceContent.length(); j++) {
                if(temp == Character.toLowerCase(sourceContent.charAt(j))){
                    counter++;
                }
            }
            countForEachMap.put(Character.toLowerCase(sourceContent.charAt(i)),counter);
        }
        return countForEachMap;
    }

    public Map<Character, List<Integer>> generateKeyTable(Map<Character, Integer> countForEachMap){
        Map<Character, List<Integer>> keyTableContent = new HashMap<>();
        int firstDigit = 328;
        //        for(Map.Entry<Character, List<Integer>> entry : keyTableContent.entrySet()){
            for(Map.Entry<Character, Integer> count : countForEachMap.entrySet()){
                List<Integer> temp = new ArrayList<>();
                for (int i = 0; i < count.getValue(); i++) {
                temp.add(firstDigit);
                firstDigit++;
            }
                keyTableContent.put(count.getKey(), temp);
            }
//        }
        return keyTableContent;
    }

    public String encryptContent(String sourceContent, Map<Character, List<Integer>>keyTableContent){
        String encryptedContent = "";
        String tempSourceContent = "";
// Перенос символов в нижний регистр
        for (int i = 0; i < sourceContent.length(); i++) {
            tempSourceContent += Character.toLowerCase(sourceContent.charAt(i));
        }
        sourceContent = tempSourceContent;

        int counters[] = new int[keyTableContent.size()];

        for (int i = 0; i < sourceContent.length(); i++) {
                for(Map.Entry<Character, List<Integer>> entry: keyTableContent.entrySet()){
                    int counterForCounters = 0;
                        if(sourceContent.charAt(i) == entry.getKey()){
                            List<Integer> temp = new ArrayList<>(entry.getValue());
                            Iterator<Integer> itr = temp.iterator();
                            if(temp.size() > 1){
                                encryptedContent += temp.get(counters[counterForCounters]);
                                encryptedContent += " ";
                                counters[counterForCounters]++;
                            } else {
                                encryptedContent += itr.next();
                                encryptedContent += " ";
                                counterForCounters++;
                            }
                        }
                    }
        }
        return encryptedContent;
    }

    public String decryptContent(String encryptedContent, Map<Character, List<Integer>>keyTableContent){
        String decryptedContent = "";
        // Создание массива для хранения зашифрованных значений
        int tempCounter = 0;
        for (int i = 0; i < encryptedContent.length(); i++) {
            if(encryptedContent.charAt(i) == ' '){
                tempCounter++;
            }
        }
// Преобразование зашифрованного сообщения в массив значений
        int[] encryptedContentInt = new int[tempCounter];
        tempCounter = 0;
        String tempString = "";
        for (int i = 0; i < encryptedContent.length(); i++) {
            if(encryptedContent.charAt(i) != ' '){
                tempString += encryptedContent.charAt(i);
            } else {
                encryptedContentInt[tempCounter] = Integer.parseInt(tempString);
                tempCounter++;
                tempString = "";
                continue;
            }
        }
// Завершение преобразование зашифрованного сообщения в массив значений decryptedContentInt
        tempCounter = 0;
        for (int i : encryptedContentInt) {
            for(java.util.Map.Entry<Character, List<Integer>> entry : keyTableContent.entrySet()){
                if(entry.getValue().contains(i)){
                    decryptedContent+=entry.getKey();
                }
            }
        }
                return decryptedContent;
        }

    public void encryptedMessageToFile (String encryptedContent) throws IOException{
        String path ="src\\encrypted.txt";
            try (FileWriter messageFile = new FileWriter(path)) {
                messageFile.write(encryptedContent);
            }
        }

    public void keyTableToFile (Map<Character, List<Integer>>keyTableContent) throws IOException{
        String path = "src\\keyTableContent.txt";
        try (FileWriter messageFile = new FileWriter(path)) {
            messageFile.write(keyTableContent.toString());
        }
    }
}
