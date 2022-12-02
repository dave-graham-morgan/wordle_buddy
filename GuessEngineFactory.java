
import java.util.*;

/*what will we need for the new guess engine: 
 * we will need the solution dictionary (for solveItGuess) and to iterate our choices
 * we will need the unabridged dictionary (for letterEliminationGuess)
 * we will need the list of guesses so far... at least I think we will.. so we don't reuse them
 * 
*/
public class GuessEngineFactory {
    private ArrayList<String> solutionDictionary; 
    private ArrayList<String> previousDictionary;
    
    public GuessEngineFactory(ArrayList<String> solutionWords, ArrayList<String> previousWords){
        solutionDictionary = deepCopyHelperMethod(solutionWords);
        previousDictionary = deepCopyHelperMethod(previousWords);
    }

    private ArrayList<String> deepCopyHelperMethod(ArrayList<String> master){
        ArrayList<String> copy = new ArrayList<String>();
            for(String currWord : master){
            copy.add(currWord);
        }
        return copy;
     }

    //TODO create and complete an engine for eliminating letters rather than making a good guess
    public String generateLetterEliminationGuess(ArrayList<String> unabridgedDictionary, String usedLetters){
        ArrayList<String> dictOfWordsWithAllNewLetters = downsizeDictionary(unabridgedDictionary, usedLetters);

        //and selects the word that results in the smallest solutionset

        return null; //TODO replace
    }

    private ArrayList<String> downsizeDictionary(ArrayList<String> unabridged, String usedLetters){
        ArrayList<String> answer = new ArrayList<String>();
        for(String currWord : unabridged){
            boolean containsAUsedLetter = false;
            for(int i = 0; i<usedLetters.length();i++){
                char currLetter = usedLetters.charAt(i);
                if(currWord.indexOf(currLetter) != -1){
                    containsAUsedLetter = true;
                    break;
                }
            }
            if(!containsAUsedLetter){
                answer.add(currWord);
            }
        }
        return answer;
    }
    
    //this method will generate a SolveIt guess which will contain all known 
    //letters and all known letters in the correct position (if any);
    //it will also not contain any wrong letters... obviously.
    public ArrayList<String> generateSolveItGuess(){
        HashMap <String, Integer> results = new HashMap<String, Integer>();
        for(String currWordle : solutionDictionary){
            WordleBot wb = new WordleBot(currWordle);
            for(String currTestWord : solutionDictionary){
                String thisWordlesEvaluationString = wb.evaluateGuess(currTestWord);

                EvaluateFactory evaluator = new EvaluateFactory();
                evaluator.evaluateAndBuildSolution(currWordle, thisWordlesEvaluationString); //todo use a static method that returns a string
                
                SolutionBuilder sb = new SolutionBuilder(solutionDictionary);
                sb.generateSolutionDictionary(evaluator.getGoodLetters(), evaluator.getBadLetters(), evaluator.getCorrectLetters(),evaluator.getGoodLettersInWrongLocation());
                Integer countForThisTest = sb.getSolutionWords().size();
                if(results.containsKey(currWordle)){
                    Integer countSoFar = results.get(currWordle);
                    Integer totalCount = countSoFar + countForThisTest;
                    results.put(currWordle, totalCount);
                }else{
                    results.put(currWordle, countForThisTest);
                }
                
            }
        }
        return getGuess(results);
    }
        
    
    private ArrayList<String> getGuess( HashMap <String, Integer> results){
    Integer smallestCount = 10000000;//initiallizing to an arbitrarily large number
    ArrayList<String> bestGuess = new ArrayList<String>();
    for(String currWord : results.keySet()){
        Integer thisCount = results.get(currWord);
        if(thisCount<smallestCount){
            smallestCount = thisCount;
        }
    }
    for(String currWord : results.keySet()){
        if(results.get(currWord)==smallestCount){
            bestGuess.add(currWord);
        }
    }
        return bestGuess;
    }

}//test changes
