package DataStructures;
import java.io.*;
import java.util.*;
public class BoggleSolver {
//Hashset to store the valid words
HashSet<String> words=new HashSet<>(); 
    static  class TrieNode
    {
        char data;
        TrieNode []child;
        boolean isWord;
        TrieNode(char c)
        {
            this.data=c;
            isWord=false;
            child=new TrieNode[26];
        }
    }
    TrieNode root;
    BoggleSolver()
    {
        root=new TrieNode('\0');
    }
//inserting words into Trie
    void insert(String word)
    {
        TrieNode curr=root;
        for(int i=0; i<word.length(); i++)
        {
            char c=word.charAt(i);
            if(curr.child[c-'a']==null)
                curr.child[c-'a']=new TrieNode(c);

            curr=curr.child[c-'a'];
        }
        curr.isWord=true;

    }
//finding words from  boggle matrix
    void findWords(char[][] boggle)
    {
        int m=boggle.length;
        int n=boggle[0].length;
        boolean[][] visited =new boolean[m][n];
        String str = "";

        // traversing the boggle matrix 
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                findWordsUtil(boggle,visited,i,j,str,m,n);
            }
        }
    }
    void findWordsUtil(char[][] boggle, boolean[][] visited, int i, int j, String str, int m, int n)
    {
        visited[i][j]=true;
        str+=boggle[i][j];
        if(search(str)) {
            words.add(str);
        }


        for(int row=i-1; row<=i+1 && row<m ; row++) {
            for (int col = j - 1; col <= j + 1 && col < n; col++) {
                if (row >= 0 && col >= 0 && !visited[row][col])
                    findWordsUtil(boggle, visited, row, col, str, m, n);
            }
        }


       
        visited[i][j]=false;
    }
//searching whether word is present in Trie or not
    boolean search(String word)
    {
        TrieNode node=getNode(word);
        return node!=null && node.isWord;
    }
    TrieNode getNode(String word)
    {
        TrieNode curr=root;
        for(int i=0; i<word.length(); i++)
        {
            char c=word.charAt(i);
            if(curr.child[c-'a']==null)return null;

            curr=curr.child[c-'a'];
        }
        return curr;
    }
    public static void main(String[] args) {
        BoggleSolver d=new BoggleSolver();
        try
        {
            FileReader fr=new FileReader("mydictionary.txt");
            Scanner sc=new Scanner(fr);
            while (sc.hasNextLine())
            {
                String s=sc.nextLine();
                d.insert(s);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        char[][] boggle ={
                {'p','t','n','i'},
                {'a','e','l','c'},
                {'h','r','n','s'},
                {'a','b','c','d'}
        };
        
        d.findWords(boggle);
        
        System.out.println(d.words);
        System.out.println("Points:"+d.words.size());

    }
}