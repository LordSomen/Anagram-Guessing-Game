/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {
    private static final String TAG = "AnagramDictionary";
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 7;
    private static int worldLen ;
    private Random random = new Random();
    private ArrayList<String> WordList = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<String>();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    static{ worldLen = DEFAULT_WORD_LENGTH;}


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            WordList.add(word);
            wordSet.add(word);
            String temp = sortWords(word);
            if(lettersToWord.containsKey(temp)){
                ArrayList<String> putList = new ArrayList<String>(lettersToWord.get(temp));
                putList.add(word);
                lettersToWord.put(temp , putList);
            }
            else {
                ArrayList<String> tempList = new ArrayList<>() ;
                tempList.add(word);
                lettersToWord.put(temp, tempList);
            }
        }
        /*for(String elem:WordList){
         Log.d(TAG,elem);
        }*/

    }

    public boolean isGoodWord(String word, String base) {

        return wordSet.contains(word);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        String sortedWord = sortWords(targetWord);
        ArrayList<String> result = new ArrayList<String>();
        for(String elem2:WordList){
            String elem = sortWords(elem2);
            if(elem.equals(sortedWord)){
                result.add(elem2);
            }
        }
            return result;
    }



    public ArrayList<String> getAnagramsWithOneMoreLetter(String targetWord) {
        //String sortedWord = sortWords(targetWord);
        ArrayList<String> result = new ArrayList<String>();
        String temp;
        for (char i = 'a';i < 'z' ; i++) {
            temp = targetWord + i;
            String elem = sortWords(temp);
            if (lettersToWord.containsKey(elem)) {
                ArrayList tempList = lettersToWord.get(elem);
                result.addAll(tempList);
            }
        }
        return result;
    }


    public String pickGoodStarterWord() {
        Random rand = new Random();
        while(true) {
            for (int i = rand.nextInt(WordList.size()); i < WordList.size(); i++) {
                String elem = WordList.get(i);
                String sortWord = sortWords(elem);
                int len = lettersToWord.get(sortWord).size();
                if (len >= MIN_NUM_ANAGRAMS && elem.length() == worldLen) {
                    if (worldLen == MAX_WORD_LENGTH) {
                        worldLen = DEFAULT_WORD_LENGTH;
                    } else
                        worldLen++;
                    return elem;
                }
            }
        }
    }

    public String sortWords(String word){

        char[] arr = word.toCharArray();
        Arrays.sort(arr);
        String sortedWord = new String(arr);
        return sortedWord;

    }
}
