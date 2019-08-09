package SO;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        
        
        File text = new File("Entrada.txt");
        
        String linha;
        String[] entradas = new String[2];
        int chegada;
        int duracao;

        Scanner in = new Scanner(System.in);

      
        while(in.hasNextLine()){
            linha = in.nextLine();
            entradas = linha.split(" ");
            
            chegada = parseInt(entradas[0]);
            duracao = parseInt(entradas[1]);
            
            System.out.println(" ");
        }
        /*
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String linha;        
        int chegada;
        int duracao;
        String[] entradas = new String[2];

        try {
            while ((linha = input.readLine()) != null) {
                entradas = linha.split(" ");
                
                chegada = parseInt(entradas[0]);
                duracao = parseInt(entradas[1]);
                
                System.out.println(chegada);
            }   } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }   
}
