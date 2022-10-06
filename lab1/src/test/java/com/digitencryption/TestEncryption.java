package com.digitencryption;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class TestEncryption {

    private final Encryption encryption = new Encryption();

    @Test
    public void testReadFile() throws Exception{
        assertEquals("Hello", encryption.readFile());
    }

    @Test
    public void testCalculateCountForEachMap() throws Exception{
        Map<Character, Integer> map = encryption.calculateCountForEachMap(encryption.readFile());
        System.out.println(map.values());
    }

    @Test
    public void testGenerateKeyTable() throws Exception{
        Map<Character, List<Integer>> test = encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()));
        System.out.println(test);
    }

    @Test
    public void testEncryptContent() throws Exception{

        Map<Character, List<Integer>>keyTableContent = encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()));

        String sourceContent = encryption.readFile();
        String tempSourceContent = "";
        String encryptedContent = "";
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
        System.out.println(keyTableContent.toString());
        System.out.println(encryptedContent);
    }

    @Test
    public void testDecryptContent() throws Exception{
        Map<Character, List<Integer>>keyTableContent = encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()));
        String encryptedContent = encryption.encryptContent(encryption.readFile(), keyTableContent);
        String decryptedContent = "";
        System.out.println(encryptedContent + "|");

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
        System.out.println(Arrays.toString(encryptedContentInt));
        
        tempCounter = 0;
        for (int i : encryptedContentInt) {
            for(Entry<Character, List<Integer>> entry : keyTableContent.entrySet()){
                if(entry.getValue().contains(i)){
                    decryptedContent+=entry.getKey();
                } 
            }
        }
        System.out.println(decryptedContent);
        // assertEquals("Hello", encryption.decryptContent(encryption.encryptContent(encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()))),
        // encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()))));
        // System.out.println(encryption.decryptContent(encryption.encryptContent(encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()))),
        //                 encryption.generateKeyTable(encryption.calculateCountForEachMap(encryption.readFile()))));

    }
}
