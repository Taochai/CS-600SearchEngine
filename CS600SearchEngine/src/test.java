public class test {
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("happy");
        t.insert("happy");
        t.insert("happy");
        t.insert("sad");
        t.insert("pathetic");
        System.out.println(t.search("happy"));
        System.out.println(t.search("saddd"));
    }
}
