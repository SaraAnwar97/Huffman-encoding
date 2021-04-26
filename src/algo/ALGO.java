/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

public class ALGO {

    static String IO = "test_huffman.txt";
    public static void main(String[] args) throws IOException {
        Instant start;
        Instant finish;
        System.out.println("Choose:\n\t1.Compress File\n\t2.Decompress file\n\t3.Exit\n");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                start = Instant.now();
                compress(IO);
                 finish = Instant.now();
                System.out.println("Execution time of compression: " + Duration.between(start, finish).toMillis() + " milliseconds");
                System.out.println("Compression ratio = " + ratio(IO,"compressed.txt"));
                main(args);
                break;
            case 2:
                start = Instant.now();
                decompress("compressed.txt", "decomp.txt");
                finish = Instant.now();
                System.out.println("Execution time of decompression: " + Duration.between(start, finish).toMillis() + " milliseconds");
                main(args);
                break;
            
            case 3:
                break;
        }

    }
static void compress(String filename) throws IOException

{
        HashMap<Character, Integer> test = new HashMap<>();
        HashMap<Character, String> codes = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<>();

   

                File file = new File(IO);
                BufferedReader br = new BufferedReader(new FileReader(file));

                int r;
                while ((r = br.read()) != -1) {
                    char ch = (char) r;
                  

                    if (test.containsKey(ch)) {
                        test.put(ch, test.get(ch) + 1);

                    } else {

                        test.put(ch, 1);

                    }
                   
                }


                br.close();
                Node n = Huffman(test);
                generateCode(n, "", codes);

                for (Character ch : codes.keySet()) {
                    Character key = ch;
                    String code = codes.get(ch);
                    System.out.println(key + ":" + code);
}
                  writeFile("compressed.txt", codes);
                

}
    static Node Huffman(HashMap<Character, Integer> table) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>(table.size(), new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.freq - o2.freq;
            }
        });

        for (int i = 0; i < table.size(); i++) {
            pq.add(new Node((char) table.keySet().toArray()[i], table.get(table.keySet().toArray()[i])));
        }

        Node root = null;
        while (pq.size() > 1) {
            Node n = pq.peek();
            pq.poll();
            Node n2 = pq.peek();
            pq.poll();

            Node f = new Node();
            f.freq = n.freq + n2.freq;

            f.c = '\0';

            f.left = n;
            f.right = n2;
            root = f;
            pq.add(f);

        }

        return pq.peek();
    }

    static void generateCode(Node root, String s, HashMap<Character, String> codes) {
        if (root == null) {
            return;
        }
        generateCode(root.left, (s.substring(0, s.length()) + "0"), codes);
        generateCode(root.right, (s.substring(0, s.length()) + "1"), codes);
        root.code = s;
        if (root.c != '\0') {
         
            codes.put(root.c, root.code);
        }

    }

    static void writeFile(String filename, HashMap<Character, String> codes) throws FileNotFoundException, IOException {
        String buffer = "";
        String remainder ;
        byte buffer1 ;
        Integer test;

        int bufferelements = 0;
        String code;
        File file = new File(IO);
        File f = new File(filename);
       
        OutputStream opStream = new FileOutputStream(filename, true);
        StringBuilder str = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        int r;
       // StringBuilder n = new StringBuilder();
        FileWriter fw = new FileWriter(f);
        for (Character ch : codes.keySet()) {
            if(ch != '\0'){
            Character key = ch;
            String temp = codes.get(ch);
            temp = key  + temp;
            //n.append(key + temp);
            
            fw.write(temp);
            fw.write("~");
            }
        }
        fw.write("|");
        fw.close();
        while ((r = br.read()) != -1) {
            char ch = (char) r;
            //ba2ra character character mil input file to replace it with code and write in output file
            code = codes.get(ch);
            bufferelements += code.length();
            //buffer = buffer + code;
            str.append(code);
             }
          
        
      
     String s = str.toString();
     
     int count = 0;
     while((s.length() + 8)%8 != 0)
     {
         s+= "0";
         count++;
     }
    String bin = Integer.toBinaryString(count);
       while((bin.length()) < 8)
     {
         bin = "0" + bin;
         
     }
       s = bin + s;
        byte[] out = new byte[s.length()/8];
        int ii =0;
        int index=0;
        Integer test1;
        while(ii < s.length()/8)
        {
            test1 = Integer.parseUnsignedInt(s.substring(index, index+8), 2);
            
            out[ii]= test1.byteValue();
            index = index+8;
            ii++;
        }
        Files.write(Paths.get("compressed.txt"), out, StandardOpenOption.APPEND);
       

    br.close();


    }

    static void decompress(String input, String output) throws FileNotFoundException, IOException {
        File file = new File(input);
        File file2 = new File(output);
        HashMap<String, Character> codes1 = new HashMap<>();
        String s = "";
        String decoded = "";
        FileWriter fw = new FileWriter(file2);
        FileInputStream fin = new FileInputStream(input);
        boolean flag = true;
        Character key = null;
        String temp2 = "";
        String coded ;
        boolean newEntry = true;
        int bytecounter = 0;
        HashMap<Character, String> codes = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int r;
        while ((r = br.read()) != -1) {
            char ch = (char) r;
            if (ch == '|') {
                    for (Character m : codes.keySet()) {
                    Character fo = m;
                    String code = codes.get(m);
                    System.out.println(fo + ":" + code);
                }

                bytecounter++;
                for (Map.Entry<Character, String> entry : codes.entrySet()) {
                    codes1.put(entry.getValue(), entry.getKey());
                }
                flag = false;
                break;
            }
            if(ch == '~')
            {
                newEntry = true;
                bytecounter++;
                continue;
            }else{
                if(newEntry)
                {
                    bytecounter++;
                    key=ch;
                    codes.put(ch, "");
                    newEntry = false;
                }else{
                    bytecounter++;
                    codes.put(key, codes.get(key) + ch);
                }
            }
            

            }

        
        byte[] content = Files.readAllBytes(Paths.get(input));
        Character temp;
       int i;
        String s1 = "";
       
        System.out.println("checkpoint");
            StringBuilder str = new StringBuilder(); 
            int cc = content[bytecounter];
            StringBuilder decod = new StringBuilder(); 
        for ( i = bytecounter+1; i < content.length; i++) { //takes so much time
     
            str.append(String.format("%8s", Integer.toBinaryString(content[i] & 0xFF)).replace(' ', '0'));
        }
        
         coded = str.toString();
        coded = coded.substring(0, coded.length()-cc);
        System.out.println("checkpoint2");
         for ( i = 0; i < coded.length(); i++) {
                    s = s + coded.charAt(i);

                    if (codes1.containsKey(s)) {
                       char c = codes1.get(s);
                      
                        decod.append(c);
                        s = "";
                    }
                }
        System.out.println("checkpoint3");
      
        fw.write(decod.toString());
        fw.close();
        br.close();
        System.out.println("checkpoint4");


        
    
    }
   
    
    static String ratio(String input, String output) throws IOException
    {
         DecimalFormat df = new DecimalFormat("#.##");
         String formatted; 
        long size1 = Files.size(new File(input).toPath());
        System.out.println("Size of uncompressed file: " + size1/1000 + " kilobytes");
        long size2 = Files.size(new File(output).toPath());
        System.out.println("Size of uncompressed file: " + size2/1000 + " kilobytes");
        formatted = df.format((double)size2/size1);
        return formatted;
    }
    
  
  
    
    
    
}
