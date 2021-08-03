package DataStructures;
import java.io.*;
import java.util.*;

//Structure of Trie
class TrieNode
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
public class BoggleSolver {
    //Hashset to store valid words
    HashSet<String> words=new HashSet<>();
    TrieNode root;

    //Initializing the root of the trie with null
    BoggleSolver()
    {
        root=new TrieNode('\0');
    }

    //inserting into the trie
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
        //make word as true if we reach the end of the word
        curr.isWord=true;

    }
    void findWords(char[][] boggle)
    {
        int m=boggle.length;
        int n=boggle[0].length;

        //creating a visited array to check what all values we have traversed in the matrix
        boolean[][] visited =new boolean[m][n];

        String str = "";   //empty string to store the words created from boggle

        // traverse all matrix elements
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                findWordsUtil(boggle,visited,i,j,str,m,n);
            }
        }
    }

    /**
     * @param boggle A 2d matrix of characters
     * @param visited A 2d matrix to take care whether a given index is visited or not
     * @param i current index
     * @param j current index
     * @param str String to store words formed from boggle
     * @param m row length of boggle matrix
     * @param n column length of boggle matrix
     */
    void findWordsUtil(char[][] boggle, boolean[][] visited, int i, int j, String str, int m, int n)
    {
        visited[i][j]=true;  //making current element of matrix as visited
        str+=boggle[i][j];   //appending the current character from the boggle to string

        if(search(str))  //searching the word on the trie if present word is valid else not valid
        {
            words.add(str);   //storing valid words into the hashset
        }

        for(int row=i-1; row<=i+1 && row<m ; row++)
        {
            for (int col = j - 1; col <= j + 1 && col < n; col++)
            {
                if (row >= 0 && col >= 0 && !visited[row][col])
                    findWordsUtil(boggle, visited, row, col, str, m, n);
            }
        }

        //making the current index as not visited since we need this index for the next iteration
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
            //using FileReader to read the file mydictionary.txt
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
        return  (int)(s.length()*Math.random()); //it returns random index from a string of 26 alphabets
    }
    static void generateBoggle(char [][]boggle)
    {
        String s="abcdefghijklmnopqrstuvwxyz";  //random string of 26 alphabets used to generate boggle randomly
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

        System.out.println("Boggle Board");
        for(int i=0; i<boggle.length; i++)
        {
            for(int j=0; j<boggle[i].length; j++)
            {
                System.out.print(boggle[i][j]+" ");
            }
            System.out.println();
        }
        //finding valid words from the boggle
        boggleSolver.findWords(boggle);

        //printing all the valid words
        System.out.println();
        System.out.println("Valid words");

        Iterator i=boggleSolver.words.iterator(); //using an iterator to traverse the hashset

        while (i.hasNext())
        {
            System.out.print(i.next()+" "); //printing all valid words
        }
        System.out.println();
       // System.out.println(boggleSolver.words);
        System.out.println("Points:"+boggleSolver.words.size());

    }
}