package DataStructures;
import java.io.*;
import java.util.*;
class TrieNode
{
    char data;
    //
    TrieNode []child;
    boolean isWord;

    TrieNode(char c)
    {
        this.data=c;
        isWord=false;
        child=new TrieNode[26];
    }
}
public class BoggleSolver {
    HashSet<String> words=new HashSet<>();
    TrieNode root;

    BoggleSolver()
    {
        root=new TrieNode('\0');
    }

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
    void findWords(char[][] boggle)
    {
        int m=boggle.length;
        int n=boggle[0].length;
        boolean[][] visited =new boolean[m][n];
        String str = "";

        // traverse all matrix elements
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                findWordsUtil(boggle,visited,i,j,str,m,n);
            }
        }
    }
    void findWordsUtil(char[][] boggle, boolean[][] visited, int i, int j, String str, int m, int n)
    {
        visited[i][j]=true;
        str+=boggle[i][j];
        if(search(str))
        {
            words.add(str);
        }
        for(int row=i-1; row<=i+1 && row<m ; row++)
        {
            for (int col = j - 1; col <= j + 1 && col < n; col++)
            {
                if (row >= 0 && col >= 0 && !visited[row][col])
                    findWordsUtil(boggle, visited, row, col, str, m, n);
            }
        }

        visited[i][j]=false;
    }
    //searching in the Trie
    boolean search(String word)
    {
        TrieNode node=getNode(word);
        return node != null && node.isWord;
    }
    //search Util
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
     void readDictionaryAndCreateTrie(BoggleSolver boggleSolver)
    {
        try
        {
            FileReader fr=new FileReader("mydictionary.txt");
            Scanner sc=new Scanner(fr);
            while (sc.hasNextLine())
            {
                String s=sc.nextLine();
                boggleSolver.insert(s);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    static int getIndex(String s)
    {
        return  (int)(s.length()*Math.random());
    }
    static void generateBoggle(char [][]boggle)
    {
        String s="abcdefghijklmnopqrstuvwxyz";
        for (int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                int index=getIndex(s);
                boggle[i][j]=s.charAt(index);
            }
        }
    }
    public static void main(String[] args) {
        BoggleSolver boggleSolver=new BoggleSolver();
       boggleSolver.readDictionaryAndCreateTrie(boggleSolver);

       char [][]boggle=new char[4][4];

        //random boggle board generator
        generateBoggle(boggle);

        boggleSolver.findWords(boggle);

        System.out.println(boggleSolver.words);
        System.out.println("Points:"+boggleSolver.words.size());

    }
}