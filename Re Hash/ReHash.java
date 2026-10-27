import java.util.Random;

public class ReHash {

    public static void main(String[] args) {
        int tamanhoMatriz = 100;
        int limiteOcupacao = 90;
        
        int[] matriz = new int[tamanhoMatriz];
        for (int i = 0; i < tamanhoMatriz; i++) {
            matriz[i] = -1;
        }

        Random random = new Random(1090);
        
        int totalColisoes = 0;
        int ocupacao = 0;
        int totalTentativas = 100;
        
        int[] elementos = new int[totalTentativas];
        int[] colisoesPorElemento = new int[totalTentativas];
        boolean[] foiInserido = new boolean[totalTentativas];

        for (int i = 0; i < totalTentativas; i++) {
            int valor = random.nextInt(10000);
            if (valor < 0) valor = valor * -1;
            
            elementos[i] = valor;
            
            if (ocupacao >= limiteOcupacao) {
                foiInserido[i] = false;
                continue;
            }

            int indice = valor % tamanhoMatriz;
            int colisoesDesteElemento = 0;

            while (matriz[indice] != -1) {
                totalColisoes++;
                colisoesDesteElemento++;
                indice = (indice + 1) % tamanhoMatriz;
            }

            matriz[indice] = valor;
            ocupacao++;
            colisoesPorElemento[i] = colisoesDesteElemento;
            foiInserido[i] = true;
        }

        int idx1 = -1, idx2 = -1, idx3 = -1;
        int max1 = -1, max2 = -1, max3 = -1;

        for (int i = 0; i < totalTentativas; i++) {
            if (foiInserido[i]) {
                int colisoes = colisoesPorElemento[i];
                if (colisoes > max1) {
                    max3 = max2; idx3 = idx2;
                    max2 = max1; idx2 = idx1;
                    max1 = colisoes; idx1 = i;
                } else if (colisoes > max2) {
                    max3 = max2; idx3 = idx2;
                    max2 = colisoes; idx2 = i;
                } else if (colisoes > max3) {
                    max3 = colisoes; idx3 = i;
                }
            }
        }

        System.out.println("RELATÓRIO DE INSERÇÃO");
        System.out.println("Ocupação final: " + ocupacao + " elementos (Bloqueado ao atingir 90%).");
        System.out.println("Total de colisões em toda a matriz: " + totalColisoes);
        
        System.out.println("\nOs 3 elementos que mais colidiram antes de encontrar vaga:");
        if (idx1 != -1) System.out.println("1º: Elemento " + elementos[idx1] + " (Colisões: " + max1 + ")");
        if (idx2 != -1) System.out.println("2º: Elemento " + elementos[idx2] + " (Colisões: " + max2 + ")");
        if (idx3 != -1) System.out.println("3º: Elemento " + elementos[idx3] + " (Colisões: " + max3 + ")");

        System.out.println("\nPosições livres na matriz:");
        for (int i = 0; i < tamanhoMatriz; i++) {
            if (matriz[i] == -1) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        System.out.println("\nTESTE DE BUSCA");
        if (idx1 != -1) buscarElemento(matriz, elementos[idx1], tamanhoMatriz);
        if (idx2 != -1) buscarElemento(matriz, elementos[idx2], tamanhoMatriz);
        if (idx3 != -1) buscarElemento(matriz, elementos[idx3], tamanhoMatriz);
    }

    public static void buscarElemento(int[] matriz, int valor, int tamanho) {
        int indiceBase = valor % tamanho;
        int indiceAtual = indiceBase;
        int saltos = 0;

        System.out.print("Buscando elemento " + valor + " (Hash inicial: " + indiceBase + "): ");

        while (matriz[indiceAtual] != -1 && saltos < tamanho) {
            if (matriz[indiceAtual] == valor) {
                System.out.println("Encontrado no índice " + indiceAtual + " após " + saltos + " pulo(s) (colisões na busca).");
                return;
            }
            indiceAtual = (indiceAtual + 1) % tamanho;
            saltos++;
        }

        System.out.println("Elemento não encontrado na matriz.");
    }
}