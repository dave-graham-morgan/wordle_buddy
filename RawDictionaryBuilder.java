/*
 * This class will take a dictionary of all words and eliminate any words 
 * whose length != NUMBER_OF_WORDS and any words that contain puncutation 
 */
import edu.duke.*;
import java.util.*;

public class RawDictionaryBuilder {
    private final int NUMBER_OF_WORDS = 5;
    private ArrayList<String> dictionary;
    
    public RawDictionaryBuilder(String fileName){
        dictionary = new ArrayList<String>();
        readInWords(fileName);
    } 
    
    public ArrayList<String> getDictionary(){
        return dictionary;
    }

    private void readInWords(String dictionaryFileName){
        dictionary.clear();
        FileResource dict = new FileResource(dictionaryFileName);

        for(String thisWord : dict.words()){
             if(thisWord.length() == NUMBER_OF_WORDS && (!hasPunctionation(thisWord))){
                dictionary.add(thisWord.toLowerCase());
            }
        }

    }

    private boolean hasPunctionation(String word){
        for(int i = 0;i<word.length();i++){
            if(!Character.isLetter(Character.valueOf(word.charAt(i)))){
                return true;
            }

        }
        return false;
    }
}
