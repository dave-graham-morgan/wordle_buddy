
import java.util.*;
public class CollectUserData {
    private String oldWordleToggle;
    private HashMap<String, String> userGuesses;
    private String lettersUsedSoFar;
    
    public CollectUserData(){
        oldWordleToggle = "";
        userGuesses = new HashMap<String, String>();
        lettersUsedSoFar = "";
    }

    public ArrayList<String> getNextGuessAndEvaluationString(int i){
        Scanner userCapture = new Scanner(System.in);
        ArrayList<String> response = new ArrayList<>();
       
        System.out.println("what was your "+(i+1)+" guess?");
        String thisGuess = "";
        thisGuess = userCapture.nextLine().toLowerCase();
        response.add(thisGuess);
        lettersUsedSoFar = lettersUsedSoFar + thisGuess;
        System.out.println("what was the Wordle response for: "+ thisGuess+"?");
        String thisResponse = userCapture.nextLine();
        response.add(thisResponse);
        userGuesses.put(thisGuess,thisResponse);
        
        return response;
    }

    public String setOldWordleToggle(){
        Scanner userInput = new Scanner(System.in);
        System.out.println("do you want to eliminate old wordles? (y/n)?");
        oldWordleToggle = userInput.nextLine().toLowerCase();
        return oldWordleToggle;
    }
    
    public ArrayList<String> getPreviousGuessesReturnedAsArrayList(){
        ArrayList<String> answer = new ArrayList<String>();
        for(String currWord : userGuesses.keySet()){
            answer.add(currWord);
        }

        return answer;
    }

    public String getOldWordleToggle(){
        return oldWordleToggle;
    }

    public String getLettersUsedSoFar(){
        return lettersUsedSoFar;
    }
}
