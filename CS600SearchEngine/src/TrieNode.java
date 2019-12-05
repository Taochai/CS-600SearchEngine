public class TrieNode {
    int count;
    String value;
    TrieNode[] children;
    Boolean endOfWord;
//    public TrieNode(char s){
//        this.count = 0;
//        this.value = s;
//        this.children = new TrieNode[26];
//        this.endOfWord = false;
//    }
    public TrieNode(){
        this.count = 0;
        this.children = new TrieNode[26];
        this.endOfWord = false;
    }
}
