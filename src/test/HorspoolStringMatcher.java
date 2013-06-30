/**
 * 
 */
package test;

/**
 * @author viper : Nov 16, 2012
 *
 */
public class HorspoolStringMatcher
{
    private char[] p, t;       // pattern, text
    private int m, n;          // pattern length, text length
    private static int alphabetsize=256;
    private int[] occ;         // occurrence function
    private String matches;    // string of match positions
    private char[] showmatches;// char array that shows matches
    private static String name="Horspool";

    public HorspoolStringMatcher()
    {
        occ=new int[alphabetsize];
    }
       
    /** searches the text tt for the pattern pp
     */ 
    public void search(String tt, String pp)
    {
        setText(tt);
        setPattern(pp);
        horspoolSearch();
    }

    /** sets the text
     */ 
    private void setText(String tt)
    {
        n=tt.length();
        t=tt.toCharArray();
        initmatches();
    }
    
    /** sets the pattern
     */ 
    private void setPattern(String pp)
    {
        m=pp.length();
        p=pp.toCharArray();
        horspoolInitocc();
    }

    /** initializes match positions and the array showmatches
     */ 
    private void initmatches()
    {
        matches="";
        showmatches=new char[n];
        for (int i=0; i<n; i++)
            showmatches[i]=' ';
    }
    
    /** computation of the occurrence function
     */ 
    private void horspoolInitocc()
    {
        int a, j;

        for (a=0; a<alphabetsize; a++)
            occ[a]=-1;

        for (j=0; j<m-1; j++)
        {
            a=p[j];
            occ[a]=j;
        }
    }

    /** searches the text for all occurrences of the pattern
     */ 
    private void horspoolSearch()
    {
        int i=0, j;
        while (i<=n-m)
        {
            j=m-1;
            while (j>=0 && p[j]==t[i+j])
                j--;
            if (j<0) 
            	report(i);
            i+=m-1;
            i-=occ[t[i]];
        }
    }

    /** reports a match
     */
    private void report(int i)
    {
        matches+=i+" ";
        showmatches[i]='^';
    }

    /** returns the match positions after the search
     */ 
    public String getMatches()
    {
        return matches;
    }

    // only for test purposes
    public static void main(String[] args)
    {
        HorspoolStringMatcher stm=new HorspoolStringMatcher();
        System.out.println(name);
        String tt, pp;
        tt="abacadabrabracabracadabrabrabracad";
        pp="abracadabra";
        stm.search(tt, pp);
        System.out.println(pp);
        System.out.println(tt);
        System.out.println( stm.showmatches);
        System.out.println("getMatches: "+stm.getMatches());
        tt="abababaa";
        pp="aba";
        stm.search(tt, pp);
        System.out.println(pp);
        System.out.println(tt);
        System.out.println(stm.showmatches);
        System.out.println(stm.getMatches());
    }

}    // end class HorspoolStringMatcher
