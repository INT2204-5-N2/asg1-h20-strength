import java.util.ArrayList;
import java.util.List;
//import jdk.nashorn.internal.codegen.CompilerConstants;
import java.util.Scanner;

public class DictionaryCommandLine{
    private static DictionaryManagement dictionaryManagement = new  DictionaryManagement();
 
    public static void showAllWords(Dictionary dictionary){
        List<Word> ws = dictionary.getWords();
        System.out.println("No\t |English \t |Vietnamese");
        for(int i=0; i < ws.size(); i++){
            System.out.println( (i+1) +"\t |"+ ws.get(i).getWord_target() + "\t |" +ws.get(i).getWord_explain());
        }

    }
    
    public static void dictionaryBasic() {
        Dictionary d = dictionaryManagement.insertFromCommandline();
        showAllWords(d);
        
    }
   public static void dictionaryAdvanced(){
        Dictionary d = dictionaryManagement.insertFromFile();
       
        showAllWords(d);
        Scanner input = new Scanner(System.in);
        System.out.println("Nhap tu can tra cuu: ");
      
       String word ;
        word =input.nextLine();
        Word w = dictionaryManagement.dictionaryLookup(d, word);
        if(w != null){ 
        System.out.println("tieng viet: "+ w.getWord_explain());
       
       }
        else
        	{
        		System.out.println( "khong tim dc");
        		List<String> a = dictionaryManagement.dictionarySearcher(d,word);// hàm gợi ý đúng k uk 
        		for(String s: a){
        			System.out.println(s);
        	}
        }

    }
    
    public static void main(String[] args) {
    	//Dictionary a= new Dictionary();
    //Dictionary d = dictionaryManagement.insertFromCommandline();
    // Dictionary d1 = dictionaryManagement.insertFromFile();
     dictionaryAdvanced();
      //  dictionaryBasic();
        
    }
}
