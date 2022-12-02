/*
 * This class is used by the system to evaluate automated guesses against a 
 * known sample wordle.  
 */

import java.util.ArrayList;
import java.util.Random;

public class WordleBot {
    private String currentWordle;

    public WordleBot(String passedInWordle){
        currentWordle = passedInWordle;
    }

    public WordleBot(ArrayList<String> dictionary){
        Random newRandomWord = new Random();
        int randomInt = newRandomWord.nextInt(dictionary.size());
        currentWordle = dictionary.get(randomInt);
    }

    public String evaluateGuess(String guess){
        StringBuilder response = new StringBuilder();
        for(int i = 0; i<guess.length();i++){
            char currLetter = guess.charAt(i);
           
            if(currLetter == currentWordle.charAt(i)){
                response.append('g');
             
            }else if(currentWordle.indexOf(currLetter)!= -1){
                response.append('y');
                
            }else if(currentWordle.indexOf(currLetter) == -1){
                response.append('w');
            }else{
                System.out.println("something went wrong in evaluateGuess WordleBot Class");
            }
        }

        return response.toString();
    }

    public String getWordle(){
        return currentWordle;
    }

}
