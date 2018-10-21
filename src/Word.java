
public class Word {
   private String word_target; // từ mới
   private String word_explain; // giải nghĩa 
   public Word(){};
   public Word(String tar , String explain){
       word_explain = explain;
       word_target = tar;
   }
    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
   
}
