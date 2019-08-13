//package SO;

import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String linha;
        String[] entradas = new String[2];
        int chegada;
        int duracao;

        Scanner in = new Scanner(System.in);

        ArrayList<Processo> processos = new ArrayList<>();

        while (in.hasNextLine()) {
            linha = in.nextLine();
            entradas = linha.split(" ");

            chegada = parseInt(entradas[0]);
            duracao = parseInt(entradas[1]);

            //if (chegada == 0 && duracao == 0) {
            //    break;
            //}

            processos.add(new Processo(chegada, duracao));
        }
        
        ArrayList<Processo> pFCFS = new ArrayList<>();
        ArrayList<Processo> pSJF = new ArrayList<>();
        ArrayList<Processo> pRR = new ArrayList<>();
        
        for(Processo p:processos){
            pFCFS.add(p.clone());
            pSJF.add(p.clone());
            pRR.add(p.clone());
        }
        
        FCFS fcfs = new FCFS();
        fcfs.run(pFCFS);       
        
        SJF sjf = new SJF();
        sjf.run(pSJF);
        
        RR rr = new RR();
        rr.run(pRR);

    }
}

//Processo

class Processo implements Comparable<Processo> {

    int Tchegada;
    int Tpico;

    int Tretorno;
    int Tresposta;
    int Tespera;

    public Processo(int Tchegada, int Tpico) {
        this.Tchegada = Tchegada;
        this.Tpico = Tpico;

        this.Tretorno = -1;
        this.Tresposta = -1;
        this.Tespera = 0;
    }

    public int compareTo(Processo anotherProcesso) {
        if (this.Tpico < anotherProcesso.Tpico) {
            return -1;
        } else if (this.Tpico > anotherProcesso.Tpico) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public Processo clone(){
        int chegada = this.Tchegada;
        int duracao = this.Tpico;
        Processo p = new Processo(chegada, duracao);
        
        return p;
    }
}

//FCFS

class FCFS {

    public void run(ArrayList<Processo> processos) {

        ArrayList<Processo> fila = new ArrayList<>();
        ArrayList<Processo> concluidos = new ArrayList<>();

        int ciclo = 0;

        while (!processos.isEmpty()) {

            //Adiciona processos a cada ciclo
            for (Processo p : processos) {
                if (p.Tchegada == ciclo) {
                    fila.add(p);
                }
            }

            if (!fila.isEmpty()) {

                for (Processo p : fila) {
                    if (fila.get(0).equals(p)) {

                        if (p.Tresposta == -1) {
                            p.Tresposta = ciclo;
                        }

                        p.Tpico--;

                    } else {
                        p.Tespera++;
                    }
                }

                if (fila.get(0).Tpico == 0) {
                    fila.get(0).Tretorno = ciclo + 1;
                    concluidos.add(fila.get(0));
                    fila.remove(0);
                    processos.remove(0);
                }
            }
            ciclo++;
        }

        printOutput(concluidos);
    }

    public void printOutput(ArrayList<Processo> processos) {

        float MTretorno = 0;
        float MTresposta = 0;
        float MTespera = 0;

        for (Processo p : processos) {
            MTretorno += (p.Tretorno - p.Tchegada);
            MTresposta += (p.Tresposta - p.Tchegada);
            MTespera += p.Tespera;
        }

        float size = processos.size();

        MTretorno = MTretorno / size;
        MTresposta = MTresposta / size;
        MTespera = MTespera / size;

        DecimalFormat df = new DecimalFormat("0.0");

        System.out.println("FCFS " + df.format(MTretorno) + " " + df.format(MTresposta) + " " + df.format(MTespera));
    }

}

//SJF

class SJF {

    Boolean preemptivo = false;

    public void run(ArrayList<Processo> processos) {

        ArrayList<Processo> fila = new ArrayList<>();
        ArrayList<Processo> concluidos = new ArrayList<>();

        int ciclo = 0;

        while (!processos.isEmpty()) {

            //Adiciona processos a cada ciclo
            for (Processo p : processos) {
                if (p.Tchegada == ciclo) {
                    fila.add(p);
                    fila = ord(fila);
                }
            }

            //processa fila
            if (!fila.isEmpty()) {

                for (Processo p : fila) {
                    if (fila.get(0).equals(p)) {

                        if (p.Tresposta == -1) {
                            p.Tresposta = ciclo;
                        }

                        p.Tpico--;

                    } else {
                        p.Tespera++;
                    }
                }

                if (fila.get(0).Tpico == 0) {
                    fila.get(0).Tretorno = ciclo + 1;
                    concluidos.add(fila.get(0));
                    fila.remove(0);
                    processos.remove(0);
                }
            }
            ciclo++;
        }
        printOutput(concluidos);
    }

    public void printOutput(ArrayList<Processo> processos) {

        float MTretorno = 0;
        float MTresposta = 0;
        float MTespera = 0;

        for (Processo p : processos) {
            MTretorno += (p.Tretorno - p.Tchegada);
            MTresposta += (p.Tresposta - p.Tchegada);
            MTespera += p.Tespera;
        }

        float size = processos.size();

        MTretorno = MTretorno / size;
        MTresposta = MTresposta / size;
        MTespera = MTespera / size;

        DecimalFormat df = new DecimalFormat("0.0");

        System.out.println("SJF " + df.format(MTretorno) + " " + df.format(MTresposta) + " " + df.format(MTespera));
    }

    public ArrayList<Processo> ord(ArrayList<Processo> processos) {

        int i = 0;
        List<Processo> sorted;
        ArrayList<Processo> lFinal = new ArrayList<>();

        if (processos.get(0).Tresposta != -1 && !preemptivo) {
            i = 1;
            sorted = processos.subList(i, processos.size());
            Collections.sort(sorted);
            lFinal.add(processos.get(0));
            for (Processo p : sorted) {
                lFinal.add(p);
            }
            return lFinal;
        } else {
            Collections.sort(processos);
        }
        return processos;
    }

}

//RR

class RR {

    public void run(ArrayList<Processo> processos) {

        ArrayList<Processo> fila = new ArrayList<>();
        ArrayList<Processo> concluidos = new ArrayList<>();

        int ciclo = 0;
        int quantum = 2;

        Boolean skip = false;

        while (!processos.isEmpty()) {

            //Adiciona processos a cada ciclo
            for (Processo p : processos) {
                if (p.Tchegada == ciclo) {
                    fila.add(p);
                }
            }

            if (quantum == 0) {
                if (!skip) {
                    fila = swap(fila);
                } else {
                    skip = false;
                }
                quantum = 2;
            }

            if (!fila.isEmpty()) {

                for (Processo p : fila) {
                    if (fila.get(0).equals(p)) {

                        if (p.Tresposta == -1) {
                            p.Tresposta = ciclo;
                        }

                        p.Tpico--;

                    } else {
                        p.Tespera++;
                    }
                }

                if (fila.get(0).Tpico == 0) {

                    //Correção quando processos terminam antes do quantum
                    if (quantum == 2) {
                        quantum = 1;
                    }

                    fila.get(0).Tretorno = ciclo + 1;
                    concluidos.add(fila.get(0));
                    fila.remove(0);
                    skip = true;
                    processos.remove(0);
                }
            }
            ciclo++;
            quantum--;
        }

        printOutput(concluidos);
    }

    public void printOutput(ArrayList<Processo> processos) {

        float MTretorno = 0;
        float MTresposta = 0;
        float MTespera = 0;

        for (Processo p : processos) {
            MTretorno += (p.Tretorno - p.Tchegada);
            MTresposta += (p.Tresposta - p.Tchegada);
            MTespera += p.Tespera;
        }

        float size = processos.size();

        MTretorno = MTretorno / size;
        MTresposta = MTresposta / size;
        MTespera = MTespera / size;

        DecimalFormat df = new DecimalFormat("0.0");

        System.out.println("RR " + df.format(MTretorno) + " " + df.format(MTresposta) + " " + df.format(MTespera));
    }

    public ArrayList<Processo> swap(ArrayList<Processo> processos) {

        ArrayList<Processo> AFinal = new ArrayList<>();
        List<Processo> tail;
        Processo head = processos.get(0);

        tail = processos.subList(1, processos.size());
        for (Processo p : tail) {
            AFinal.add(p);
        }
        AFinal.add(head);

        return AFinal;
    }
}