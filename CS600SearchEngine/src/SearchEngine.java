/**
 *   Title: CS-600 final project
 *   Author:Tao Chai
 *   Web page reading part is learned from Lu li.
 *   StopWords is from google.
 */

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SearchEngine {
    private static Trie myTrie = new Trie();
    private static Map<String, Map<String, Integer>> wordMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        URL p = SearchEngine.class.getResource("infile.dat");
        File f = new File(p.getFile());
        BufferedReader fileReader = new BufferedReader(new FileReader(f));
        String str = null;
        while((str = fileReader.readLine()) !=null) {
            start(str.trim(),0);
        }
        Scanner scan = new Scanner(System.in);
        /// User Interface
        boolean ifquit = false;
        while(!ifquit){
            System.out.print("Enter the word you want to search(Enter q to quit this program!):  ");
            String user = scan.nextLine().trim();
            user = user.toLowerCase();
            if(user.replaceAll("[a-z]", "").trim().length()>0) {
                System.out.println("Wrong input, type again: ");

            }else {
                if(user.equals("q")) break;
                if(myTrie.search(user)){
                    System.out.println(""+user+"---is found in :");
                    Map<String, Integer> StrFrequency = wordMap.get(user);
                    List<WordFreq> wordList = SortByFreq(StrFrequency);
                    int i = 0;
                    while(i<wordList.size()){
                        WordFreq eachWord =wordList.get(i);
                        System.out.println("   "+ eachWord.webSite+":  "+eachWord.frequence+" times");
                        i++;
                    }
                }else{
                    System.out.println("Can not find "+user);
                }
            }
        }
    }

    public static void start(String strurl, int dep) throws IOException {
        StringBuffer sb = new StringBuffer();
        StopWords stopWords = new StopWords();
        URL url = new URL(strurl);
        Document doc = Jsoup.connect(strurl).get();
        // get website content
        Elements links = doc.select("a[href]");
        Element content = doc.getElementsByTag("body").get(0);
        sb.append(content.toString());
        String outPut = Reg(sb.toString());
        String[] strWord = outPut.split("[^a-z]+");
        String strFile = strurl.substring(strurl.lastIndexOf("\\")+1);
        for(int i=0;i<strWord.length;i++) {
            if(strWord[i].length()>0 && !stopWords.is(strWord[i])) {
                String str = strWord[i].toLowerCase();
                myTrie.insert(str);
                if(wordMap.containsKey(str)) {
                    if(wordMap.get(str).containsKey(strFile)) {
                        wordMap.get(str).put(strFile, wordMap.get(str).get(strFile)+1);
                    }else {
                        wordMap.get(str).put(strFile,1);
                    }
                }else {
                    HashMap<String, Integer> map = new HashMap<>();
                    map.put(strFile, 1);
                    wordMap.put(str, map);
                }
            }
        }
        if(dep<1) {
            int dex = 0;
            for (Element link : links) {
                String href = link.attr("abs:href");
                if ((href.startsWith("http:") || href.startsWith("https:"))) {
                    start(href, 1);
                    dex++;
                }
                if(dex ==2) break;
            }
        }
    }
    public static String Reg(String text) {
        String regEx_html = "<[^>]*>";
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(text);
        text = m_html.replaceAll("");
        return text.replaceAll("&nbsp;", " ").replaceAll("&(?:g|l)t", "");
    }
    public static List<WordFreq> SortByFreq(Map<String,Integer> m){
        List<WordFreq> list = new ArrayList<>();
        Iterator<Map.Entry<String,Integer>> entries = m.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, Integer> entry = entries.next();
            WordFreq curr = new WordFreq(entry.getKey(),entry.getValue());
            list.add(curr);
        }
        Collections.sort(list,(o1, o2) -> o1.frequence-o2.frequence);
        return list;
    }

}
