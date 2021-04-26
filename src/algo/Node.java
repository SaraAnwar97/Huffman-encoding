/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author miralalaa
 */
public class Node implements Comparable<Node>{
    
    int freq;
    
    char c ;
    Node left;
    Node right;
// Node parent;
    String code;
    
    
  
    
    
    public Node( char c,int freq) {
      this.freq = freq;
       this.c = c;
       this.left = null;
       this.right =null;
        this.code ="";
       


   
} 
     public Node( ) {
    
       this.left = null;
       this.right =null;
       this.code ="";


   
} 

    @Override
    public int compareTo(Node o) {
        
        if(this.freq > o.freq) {
            return 1;
        } else if (this.freq < o.freq) {
            return -1;
        } else {
            return 0;
        }
    }
}

