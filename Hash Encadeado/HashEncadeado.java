import java.util.Random;

public class HashEncadeado {

    static class No {
        int valor;
        No proximo;

        No(int valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    public static void main(String[] args) {
        No[] tabela = new No[100];
        
        Random random = new Random(1090);
        
        int[] ultimosTres = new int[3];

        for (int i = 0; i < 1000; i++) {
            int valor = random.nextInt(100001);
            
            int posicao = valor % 100;
            
            ultimosTres[0] = ultimosTres[1];
            ultimosTres[1] = ultimosTres[2];
            ultimosTres[2] = valor;

            No novoNo = new No(valor);

            if (tabela[posicao] == null) {
                tabela[posicao] = novoNo;
            } else {
                No atual = tabela[posicao];
                while (atual.proximo != null) {
                    atual = atual.proximo;
                }
                atual.proximo = novoNo;
            }
        }

        int posicoesVazias = 0;
        int listasGeradas = 0;
        int tamanhoMaiorLista = 0;
        int indiceMaiorLista = -1;

        for (int i = 0; i < 100; i++) {
            if (tabela[i] == null) {
                posicoesVazias++;
            } else {
                listasGeradas++;
                
                int tamanhoAtual = 0;
                No atual = tabela[i];
                
                while (atual != null) {
                    tamanhoAtual++;
                    atual = atual.proximo;
                }
                
                if (tamanhoAtual > tamanhoMaiorLista) {
                    tamanhoMaiorLista = tamanhoAtual;
                    indiceMaiorLista = i;
                }
            }
        }

        System.out.println("TABELA HASH");
        System.out.println("Listas encadeadas geradas (posições ocupadas): " + listasGeradas);
        System.out.println("Posições vazias: " + posicoesVazias);
        System.out.println("Os três últimos valores inseridos foram: " + 
                           ultimosTres[0] + ", " + 
                           ultimosTres[1] + " e " + 
                           ultimosTres[2]);
        
        System.out.println("\nMAIOR LISTA ENCADEADA");
        System.out.println("Índice na matriz: " + indiceMaiorLista);
        System.out.println("Quantidade de elementos: " + tamanhoMaiorLista);
        System.out.print("Elementos (em ordem de inserção): [ ");
        
        No iterador = tabela[indiceMaiorLista];
        while (iterador != null) {
            System.out.print(iterador.valor);
            if (iterador.proximo != null) {
                System.out.print(" -> ");
            }
            iterador = iterador.proximo;
        }
        System.out.println(" ]");
    }
}