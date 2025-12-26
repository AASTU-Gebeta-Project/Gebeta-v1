package gebeta.domain;
public enum Difficulty {
    EASY(2),   
    MEDIUM(4),  
    HARD(8);    

    public final int depth;
    Difficulty(int depth) { this.depth = depth; }
}
