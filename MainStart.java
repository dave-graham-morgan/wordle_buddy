//import java.time.Year;
import java.util.*;

public class MainStart {
    
    
    public static void main(String[] args){
        final String NAMEOFDICTIONARYFILE = "data/betterWords.txt";
        final String NAMEOFWORDLEFILE = "data/allWordles.txt";
        
        //this section will collect user data and use that to build the initial dictionary and wordle dictionary(if wanted)
        CollectUserData userDataObject = new CollectUserData();
        RawDictionaryBuilder rawDictionary = new RawDictionaryBuilder(NAMEOFDICTIONARYFILE);
        SolutionBuilder newSolution;
        
        if(userDataObject.getOldWordleToggle().equals("y")){
            RawDictionaryBuilder rawWordleDictionary = new RawDictionaryBuilder(NAMEOFWORDLEFILE);
            newSolution = new SolutionBuilder(rawDictionary.getDictionary(),rawWordleDictionary.getDictionary());
        }else{
            newSolution = new SolutionBuilder(rawDictionary.getDictionary());
        }
        
        // this section will build the strings needed by the solutionBuilder to downscope the solution dictionary to only those words that are possible solutions;
        HashMap<String, String> userGuesses = userDataObject.getGuessesAndGuessResponseHashmap();
        EvaluateFactory ef = new EvaluateFactory();
        for(String currGuess : userGuesses.keySet()){
            ef.evaluateAndBuildSolution(currGuess, userGuesses.get(currGuess));
        }

        //and now downscope the solution dictionary using the strings generated above:
        newSolution.generateSolutionDictionary(ef.getGoodLetters(),ef.getBadLetters(),ef.getCorrectLetters(),ef.getGoodLettersInWrongLocation());

        //we have a good solution dictionary (within SolutionBuilder obj), we also have an unabridged dictionary(also within SolutionBuilder obj) 
        //and pervious guesses within CollectUserData obj.  Lets generate guesses:
        GuessEngineFactory nextGuess = new GuessEngineFactory(newSolution.getSolutionWords(),userDataObject.getPreviousGuessesReturnedAsArrayList());


        //lets output some shit
        for(String currWord : newSolution.getSolutionWords()){
            System.out.println(currWord);
        }
        System.out.println("there were "+ newSolution.getSolutionWords().size()+ " words remaining in the resultSet");
        System.out.println("your next SolveIt guess should be: "+nextGuess.generateSolveItGuess());
        System.out.println("your next letter elimination guess should be: " 
            + nextGuess.generateLetterEliminationGuess(newSolution.getDictionaryOfAllWords(), userDataObject.getLettersUsedSoFar()));
    

    }     
}

