public class Trie {
        TrieNode root;
        public Trie(){
            this.root = new TrieNode();
        }
        public void insert(String s){
            //corner case
            if(s==null || s.length()==0) return;
            // loop s to insert into trie
            TrieNode currNode = this.root;
            for(int i=0; i<s.length();i++){
                int index = s.charAt(i) - 'a';
                if(currNode.children[index]==null){
                    currNode.children[index] = new TrieNode();
                }
                currNode = currNode.children[index];
            }
            currNode.count++;
            currNode.endOfWord = true;
            currNode.value = s;
        }
        public boolean search(String s){
            //corner case
            if(s.length()==0) return false;
            //search trieTree
            TrieNode currNode = this.root;
            for(int i=0;i<s.length();i++){
                int index = s.charAt(i)-'a';
                if(currNode.children[index]==null){
                    return false;
                }
                currNode = currNode.children[index];
            }
            if(currNode!=null && currNode.endOfWord == true) return true;
            return false;
        }
}

