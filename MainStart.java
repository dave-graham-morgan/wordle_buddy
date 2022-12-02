import java.util.ArrayList;

public class MainStart {
    
    
    public static void main(String[] args){
        final String NAMEOFDICTIONARYFILE = "data/betterWords.txt";
        final String NAMEOFWORDLEFILE = "data/allWordles.txt";
        
        //this section will collect user data and use that to build the initial dictionary and wordle dictionary(if wanted)
        CollectUserData userDataObject = new CollectUserData();
        RawDictionaryBuilder rawDictionary = new RawDictionaryBuilder(NAMEOFDICTIONARYFILE);
        SolutionBuilder newSolution;
        String oldWordleToggle = userDataObject.setOldWordleToggle();
        
        if(oldWordleToggle.equals("y")){
            RawDictionaryBuilder rawWordleDictionary = new RawDictionaryBuilder(NAMEOFWORDLEFILE);
            newSolution = new SolutionBuilder(rawDictionary.getDictionary(),rawWordleDictionary.getDictionary());
        }else{
            newSolution = new SolutionBuilder(rawDictionary.getDictionary());
        }
        
        EvaluateFactory ef = new EvaluateFactory();
        
        for(int i = 0; i<6;i++){
            ArrayList<String> userGuesses = userDataObject.getNextGuessAndEvaluationString(i);
            String thisGuess = userGuesses.get(0);
            String thisResponse = userGuesses.get(1);
            if(thisResponse.equals("ggggg")){
                System.out.println("Well done... wordleBuddy solved the puzzle in: "+(i+1)+" guesses.");
                break;
            }
            ef.evaluateAndBuildSolution(thisGuess, thisResponse);
            newSolution.generateSolutionDictionary(ef.getGoodLetters(),ef.getBadLetters(),ef.getCorrectLetters(),ef.getGoodLettersInWrongLocation());
            ArrayList<String> previousGuessesArrayList = userDataObject.getPreviousGuessesReturnedAsArrayList();
            GuessEngineFactory nextGuess = new GuessEngineFactory(newSolution.getSolutionWords(),previousGuessesArrayList);

            // lets output some shit
            for (String currWord : newSolution.getSolutionWords()) {
                System.out.println(currWord);
            }
            System.out.println(
                    "there were " + newSolution.getSolutionWords().size() + " words remaining in the resultSet");
            System.out.println("your next SolveIt guess should be: " + nextGuess.generateSolveItGuess());
            System.out.println("your next letter elimination guess should be: "
                    + nextGuess.generateLetterEliminationGuess(newSolution.getDictionaryOfAllWords(),
                            userDataObject.getLettersUsedSoFar()));
        } 
    }     
}

