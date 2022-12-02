
import java.util.*;
public class CollectUserData {
    private int numOfGuesses;
    private String oldWordleToggle;
    private HashMap<String, String> userGuesses;
    private String lettersUsedSoFar;
    
    public CollectUserData(){
        numOfGuesses = 0;
        oldWordleToggle = "";
        userGuesses = new HashMap<String, String>();
        lettersUsedSoFar = "";
        initializeUserData();
    }
    
    public  void initializeUserData(){
        Scanner userInput = new Scanner(System.in);
        try{
            System.out.println("do you want to eliminate old wordles? In other words are you doing the wordle for reals or just testing? (y/n)?");
            oldWordleToggle = userInput.nextLine().toLowerCase();
            System.out.println("how many guesses have you made so far? ");
            numOfGuesses = Integer.parseInt(userInput.nextLine());
            
            for(int i = 0; i<numOfGuesses;i++){
                System.out.println("what was your "+(i+1)+" guess?");
                String thisGuess = userInput.nextLine();
                lettersUsedSoFar = lettersUsedSoFar + thisGuess;
                System.out.println("what was the Wordle response for: "+ thisGuess+"?");

                String thisReponse = userInput.nextLine();
                userGuesses.put(thisGuess, thisReponse);
            }
            
        }finally{
            userInput.close();
        }
    }
    public HashMap<String, String> getGuessesAndGuessResponseHashmap(){
        return userGuesses;
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
