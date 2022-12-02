/*
 * This class will build a solution set of possible wordles.  It reduces the unabridged dictionary based on 
 * 1. the number of wrong letters "w"
 * 2. number of correct letters in the wrong location "y"
 * 3. number of correct letters in the correct location "g"
 * 4. it also extracts out any wordles previously used but this is optional in case you want to use non wordle app for testing. 
 */

import java.util.*;
public class SolutionBuilder {
    private ArrayList<String> solutionWords;
    private ArrayList<String> dictionaryOfAllWords;
    private ArrayList<String> oldWordles;
    
    //this constructor takes the dictionary of previously used wordles. both constructors instantiate the oldwordles arrayList but if the dictionary 
    //of old wordles is not passed in the oldWordles arraylist remains null and changes nothing. pure genius. 

    public SolutionBuilder(ArrayList<String> dic, ArrayList<String> dicWordles){

        dictionaryOfAllWords = new ArrayList<String>();
        solutionWords = new ArrayList<String>();
        oldWordles = new ArrayList<String>();

        for(String currWord : dic){
            dictionaryOfAllWords.add(currWord);
        }

        for(String currWord: dicWordles){
            oldWordles.add(currWord);
        }
        
    }
    /*
     * constructor that only takes a dictionary for when we don't want to eliminate the old wordles... like if we want to test/learn.
     * this constructor will also be called by the system when it generates a guess
    */

    public SolutionBuilder(ArrayList<String> dic){
        dictionaryOfAllWords = new ArrayList<String>();
        solutionWords = new ArrayList<String>();
        oldWordles = new ArrayList<String>();

        for(String currWord : dic){
            dictionaryOfAllWords.add(currWord);
        }
    }
    
    //this here is the important method for generation the solutions
    public void generateSolutionDictionary(String goodLetters, String badLetters, String goodLettersInCorrectLocation, HashMap<Integer, String> goodLettersInWrongLocation){
        checkForAllGoodLetters(goodLetters);
        checkForAnyBadLetters(badLetters);
        checkCorrectLettersInCorrectLocation(goodLettersInCorrectLocation);
        checkCorrectLettersInWrongLocation(goodLettersInWrongLocation);
    }

    // these are the get method for the dictionaries.  Keep in mind this returns a reference to the solution words dictionary so don't muddle it.
    public ArrayList<String> getSolutionWords(){
        return solutionWords;
    }
    public ArrayList<String> getDictionaryOfAllWords(){
        return dictionaryOfAllWords;
    }

    //this method is the initial build of the solution set and checks to make sure all words in the dictionary contain every good leter as we know it.  
    //It also ensures old wordles aren't part of the solution if that option was selected;
    private void checkForAllGoodLetters(String goodLetters){
        for(int i = 0; i<dictionaryOfAllWords.size();i++){
            
            if(containsAllChars(dictionaryOfAllWords.get(i), goodLetters) && (!oldWordles.contains(dictionaryOfAllWords.get(i)))){  
                solutionWords.add(dictionaryOfAllWords.get(i));
            }
        }
    }
    //this method ensures that all words that have correct letters in the wrong location are removed from the solution
    private void checkCorrectLettersInWrongLocation(HashMap<Integer, String> wrongLocMap){
        boolean removeWord = false;
        for(int k = 0; k<solutionWords.size();k++){
            String currWord  = solutionWords.get(k); 
            for(int i = 0; i<currWord.length();i++){
                String badLettersInThisLoc = wrongLocMap.get(i);
                char thisLetter = currWord.charAt(i);
                if(badLettersInThisLoc != null && !(badLettersInThisLoc.indexOf(thisLetter) == -1)){
                    removeWord = true;
                    
                }else{
                    removeWord = false;
                }
                if(removeWord){
                    solutionWords.remove(currWord);
                    removeWord = false;
                    k--; //I decrement k because i'm removing words from solution we're looping on.
                    break;
                }

            }
            
        }
    }

    
    //this method removes any word that contains a single bad letter from the solution
    private void checkForAnyBadLetters(String badLetters){
        boolean removeWord = false;
        for(int i = 0; i<solutionWords.size();i++){
            for(int k = 0; k<badLetters.length();k++){
                char indx = badLetters.charAt(k);
                String testWord = solutionWords.get(i);
                if(!(testWord.indexOf(indx) == -1)){
                    removeWord = true;
                    break;
                }else{
                    removeWord = false;
                }
            }
            if(removeWord){
                solutionWords.remove(i);
                i--;
                removeWord = false;
            }
        }
    }
    //this method removes any word that doesn't have the correct letter in a known location (green);
    private void checkCorrectLettersInCorrectLocation(String correctLetters){
        boolean removeWord = false;
        for(int i = 0; i<solutionWords.size();i++){ 
            for(int k = 0; k<correctLetters.length();k++){
                char currentChar = correctLetters.charAt(k);
                if(!(currentChar == ' ')){
                    String currentWord = solutionWords.get(i);
                    if(!(currentWord.charAt(k) == currentChar)){
                        removeWord = true;
                        break;
                    }else{
                        removeWord = false;
                    }
                }
            }

            if(removeWord){
                solutionWords.remove(i);
                i--;
            }
            
        }
    }
    //the following two helper methods are used when checking to see if the guess contains all of the good letters
    private static Set<Character> stringToCharacterSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }

    private boolean containsAllChars(String container, String containee) {
        return stringToCharacterSet(container).containsAll(stringToCharacterSet(containee));
    }
    
    

    

     

}
