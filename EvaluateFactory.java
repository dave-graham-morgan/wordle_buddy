import java.util.*;
//import java.util.Map;

public class EvaluateFactory {

    private String goodLetters;
    private String badLetters;
    private ArrayList<Character> goodLettersInCorrectLocation;
    private HashMap<Integer, ArrayList<String>> goodLettersInWrongLocation;

    public EvaluateFactory(){
        goodLetters = "";
        badLetters = "";
        goodLettersInCorrectLocation = new ArrayList<Character>();
        goodLettersInWrongLocation = new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> tempArray = new ArrayList<String>();

        for(int i = 0; i<5;i++){ //this feels like a hack.  I'm essentially initializing the arraylist to null so I don't get an out of bounds exception later.  Future study
            goodLettersInCorrectLocation.add(i,' ');
        }
        for(int i = 0; i<5;i++){
            goodLettersInWrongLocation.put(i,tempArray);
        }
    }

    public void evaluateAndBuildSolution(String guess, String evaluationString){
        setGoodLetters(evaluationString,guess);
        setBadLetters(evaluationString,guess);
        setCorrectLetters(evaluationString,guess);
        setGoodLettersInWrongLocation(evaluationString,guess);
    }

    private void setGoodLetters(String evaluationString, String guess){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<evaluationString.length();i++){
            char currLetter = evaluationString.charAt(i);
            char currGuessLetter = guess.charAt(i);
            if(currLetter == 'g'|| currLetter == 'y'){
                if(!(goodLetters.contains(Character.toString(currGuessLetter)))){
                    sb.append(currGuessLetter);
                }
            }
        }
        goodLetters = goodLetters + sb.toString();
    }
    public String getGoodLetters(){
        return goodLetters;
    }

    private void setBadLetters(String evaluationString, String guess){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<evaluationString.length();i++){
            char currLetter = evaluationString.charAt(i);
            char currGuessLetter = guess.charAt(i);
            if(currLetter == 'w' && !(goodLetters.contains(Character.toString(currGuessLetter)))){ ///TODO look here to eliminate duplicates in the bad letters, although I don't think it matters
                sb.append(currGuessLetter);
            }
        }
        badLetters = badLetters + sb.toString();
    }
    public String getBadLetters(){
        return badLetters;
    }

    private void setCorrectLetters(String evaluationString, String guess){
        
        for(int i = 0; i<evaluationString.length();i++){
            char currEvalLetter = evaluationString.charAt(i);
            char currGuessLetter = guess.charAt(i);
            if(currEvalLetter == 'g'){
                goodLettersInCorrectLocation.set(i,currGuessLetter);
            }
        }
       
    }

    public String getCorrectLetters(){
        StringBuilder sb = new StringBuilder();
        for(char currString : goodLettersInCorrectLocation){
            sb.append(currString);
        }
        return sb.toString();  //solution builder is expecting a string for correct letters
    }

    private void setGoodLettersInWrongLocation(String evaluationString, String guess){ //TODO eventually need a way to account for double letters
        for(int i = 0; i<evaluationString.length();i++){
            char currEvalLetter = evaluationString.charAt(i);
            String currGuessLetter = Character.toString(guess.charAt(i));
            
            if(currEvalLetter == 'y'){
                ArrayList<String> currValue = goodLettersInWrongLocation.get(i);
                if(!currValue.isEmpty()){
                    if(!currValue.contains(currGuessLetter)){
                        currValue.add(currGuessLetter);
                    }
                }else{
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add(currGuessLetter);
                    goodLettersInWrongLocation.put(i, tempArrayList);
                }
                
            }
        }
    }

    public HashMap<Integer, String> getGoodLettersInWrongLocation(){
        //I had some legacy code that uses a hashmap of integer -> String but 
        //the variable goodLettersInWrongLocation is Integer -> ArrayList<String>.  I do a quick conversion so
        //I didn't have to refactor code.  cause who cares. 
        HashMap<Integer,String> answer = new HashMap<Integer,String>();
        
        for(Integer currSlot : goodLettersInWrongLocation.keySet()){
            StringBuilder sb = new StringBuilder();
            ArrayList<String> tempArray = goodLettersInWrongLocation.get(currSlot);
            if(tempArray != null){
                for(String currLetter : tempArray){
                    sb.append(currLetter);
                }
            }
            answer.put(currSlot, sb.toString());
        }
        return answer;
    }

}
