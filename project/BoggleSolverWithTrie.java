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
public class BoggleSolverWithTrie {
    //Hashset to store valid words
   static HashSet<String> words=new HashSet<>();
   static HashSet<String> nvWords=new HashSet<>();
    TrieNode root;

    //Initializing the root of the trie with null
    BoggleSolverWithTrie()
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
     void readDictionaryAndCreateTrie(BoggleSolverWithTrie boggleSolverWithTrie)
    {
        try
        {
            //using FileReader to read the file mydictionary.txt
            FileReader fr=new FileReader("mydictionary.txt");
            Scanner sc=new Scanner(fr);

            while (sc.hasNextLine())
            {
                String s=sc.nextLine();
                boggleSolverWithTrie.insert(s);
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
    static void print(char [][]boggle)
    {
        for(int i=0; i<boggle.length; i++)
        {
            for(int j=0; j<boggle[i].length; j++)
            {
                System.out.print(boggle[i][j]+" ");
            }
            System.out.println();
        }

    }
    static void getInputWords(char [][]boggle)
    {
        int points=0;
        print(boggle);
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter valid strings from the Board");
        String s[]=sc.nextLine().split(" ");
        for(String str:s)
        {
            if(words.contains(str))
            {
                points++;
            }
            else
            {
                nvWords.add(str);
            }
        }
        if(!nvWords.isEmpty())
        {
            System.out.print(nvWords);
            System.out.println(" are not valid words");
        }
        System.out.println("Total points you got:"+points);
        System.out.println("\nTotal Valid Words:"+words.size());
        System.out.println(words);
    }
    public static void main(String[] args) {
        BoggleSolverWithTrie boggleSolverWithTrie =new BoggleSolverWithTrie();
        boggleSolverWithTrie.readDictionaryAndCreateTrie(boggleSolverWithTrie);
        char [][]boggle=new char[4][4];

        //random boggle board generator
        generateBoggle(boggle);

        //To find valid words from the board
        boggleSolverWithTrie.findWords(boggle);

        //Play
        System.out.println("Lets Play Boggle Game");

        System.out.println("press 1 to play and 2 to get Solution");
        Scanner sc=new Scanner(System.in);
        int pressed=sc.nextInt();
        if(pressed==1)
        {
            getInputWords(boggle);
        }
        else
        {
            print(boggle);
            System.out.println("Total valid words:"+words.size());
            //Valid words
            System.out.println(boggleSolverWithTrie.words);
        }

    }
}