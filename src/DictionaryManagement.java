//import com.sun.speech.freetts.VoiceManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DictionaryManagement {
    private int size ;
    
    
    
   
   
/**
 * Hàm insertFromCommandline() có chức năng nhập liệu: 
Nhập vào bàn phím số lượng từ vựng (Word). 
Format nhập dữ liệu từ điển Anh – Việt 
Dòng 1: Nhập từ tiếng Anh 
Dòng 2: Nhập giải thích sang tiếng Việt 
 * @return Dictionary 
 */   
    public Dictionary insertFromCommandline(){
         Scanner input = new Scanner(System.in);
        System.out.println("Nhap vao so luong tu! ");
        size = Integer.parseInt(input.nextLine());
       Dictionary dictionary = new Dictionary();
       List<Word> listWords= new ArrayList<Word>();
        for(int i=0; i<size; i++){
            System.out.println("tu thu " + i);
            Word word = new Word();
             String wordTaget = input.nextLine();
              String wordExplain = input.nextLine();
            word.setWord_target(wordTaget);
            word.setWord_explain(wordExplain);
            listWords.add(word);
        }
        dictionary.setWords(listWords);
        return dictionary;
 
    }
/**
 * Hàm insertFromFile() nhập dữ liệu từ điển từ tệp Anh-Viet.txt 
 * @return 
 */
    public Dictionary insertFromFile(){
        Dictionary d = new Dictionary();
        List<Word> listWords= new ArrayList<Word>();
        BufferedReader br = null;
        try{
             File file = new File("Anh-Viet.txt");
             
              
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
            String textInLine;
            while((textInLine = br.readLine()) != null){
                Word word = new Word();
//                String temp;
//                temp = br.readLine();
                String [] lib = textInLine.split("\t"); 
                String target;
                 target = lib[0];
                 String explain = lib[1];
                 word.setWord_explain(explain);
                 word.setWord_target(target);
                 listWords.add(word);

                
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        d.setWords(listWords);
        return d;
    }
/**
 * hàm tra cứu từ điển bằng dòng lệnh
 * @param d 
 */    
    public Word dictionaryLookup( Dictionary d, String word ){
      
      
       for(Word w : d.getWords()){
           if(w.getWord_target().equals(word)){
                           
               return w;
               
           } 
        
    }
        
          
          return null;
}
    public Dictionary AddWord(String wTarget, String wExplain,Dictionary d){
        Word w = new Word();
        
        Scanner input = new Scanner(System.in);
         wTarget = input.nextLine();
        wExplain = input.nextLine();
      
        w.setWord_explain(wExplain);
        w.setWord_target(wTarget);
        d.getWords().add(w);
        //ghi vao file
      BufferedWriter bw = null;
      FileWriter fw = null;
       try {
          File file = new File(""C:\\Users\\ADMIN\\Documents\\GitHub\\asg1-h20-strength\\Anh-Viet.txt");
             fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
             bw.write("\n"+wTarget+wExplain);
       } catch (Exception e) {
            e.printStackTrace();
       }finally{
       try {
            if(bw != null)
               bw.close();
            if(fw!=null)
               fw.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
        return d;
        
        
        
    }
    public List<String> dictionarySearcher(Dictionary d ,String s){
        List<String> dsList = new ArrayList<String>();
        for(Word w : d.getWords()){
            if(w.getWord_target().substring(0, s.length()).equals(s) ){
                dsList.add(w.getWord_target());
            }
        }
        return dsList;
    }
    
    public Dictionary DelWord( String s,Dictionary d){
       Word w = this.dictionaryLookup(d, s);
        //if(w.getWord_explain().equals("khong tim dc tu")) return d;
        //else {
            d.getWords().remove(w);
            return d;
        }
    
    public Dictionary EditWord(String editWT, String editWE, Dictionary d){
    	String wE;
    	Scanner input = new Scanner(System.in);
    	wE=input.nextLine();
        Word word = this.dictionaryLookup(d, wE);
        if(word.getWord_explain().equals("khong tim dc tu")) return d;
        else if(editWT == ""){
            for(Word w: d.getWords()){
                if(w.getWord_target().equals(wE)){
                    
                }
            }
        }
        return d;
    }
    /**
     * 
     * @param d 
     */
    public void dictionaryExportToFile(Dictionary d){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            
            File file = new File("Anh-Viet.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF8"));
            //fw = new FileWriter(file.getAbsoluteFile());
           // bw = new BufferedWriter(fw);
            List<Word> wordsToFile = d.getWords();
             for(Word w: wordsToFile){
                 bw.write(w.getWord_target()+w.getWord_explain()+"\n");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        try {
            if(bw != null)
                bw.close();
            if(fw!=null)
                fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }}
    
