package codigo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner teclado = new Scanner(System.in);
    private static ImageService servico = new ImageService();
    private static BufferedImage imagem = null; 
    private static Position inicio = null;

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("FLOOD FILL ");
        if (imagem == null) {
            System.out.println("Imagem: (nenhuma)");
        } else {
            System.out.println("Imagem: " + imagem.getWidth() + "x" + imagem.getHeight());
        }
        if (inicio == null) {
            System.out.println("Início: (não definido)");
        } else {
            System.out.println("Início: X=" + inicio.getX() + ", Y=" + inicio.getY());
        }
        System.out.println("1 - Executar com pilha");
        System.out.println("2 - Executar com fila");
        System.out.println("3 - Escolher imagem");
        System.out.println("4 - Escolher coordenada de início");
        System.out.println("0 - Encerrar");
        System.out.print("Opção: ");
    }

    private static int lerOpcao() {
        String linha = teclado.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int lerInteiro(String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            String linha = teclado.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Valor fora do intervalo (" + min + " a " + max + ").");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

     private static void escolherImagem() {
        System.out.print("Caminho da imagem: ");
        String caminho = teclado.nextLine().trim().replace("\"", "");
        if (caminho.isEmpty()) {
            System.out.println("Nenhum caminho informado.");
            return;
        }
        try {
            imagem = servico.abrir(caminho);
            inicio = null; 
            System.out.println("Imagem carregada: " + imagem.getWidth() + "x" + imagem.getHeight());
            System.out.println("Escolha a coordenada de início (opção 4).");
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void escolherCoordenada() {
        if (imagem == null) {
            System.out.println("Escolha uma imagem primeiro (opção 3).");
            return;
        }
        int x = lerInteiro("Digite X (coluna, 0 a " + (imagem.getWidth() - 1) + "): ", 0, imagem.getWidth() - 1);
        int y = lerInteiro("Digite Y (linha, 0 a " + (imagem.getHeight() - 1) + "): ", 0, imagem.getHeight() - 1);
        inicio = new Position(x, y);
        System.out.println("Coordenada de início definida: X=" + x + ", Y=" + y);
    }

    private static void executar(boolean usarPilha) {
        if (imagem == null) {
            System.out.println("Escolha uma imagem primeiro (opção 3).");
            return;
        }
        if (inicio == null) {
            System.out.println("Escolha a coordenada de início primeiro (opção 4).");
            return;
        }

        System.out.println("Digite a nova cor (valores de 0 a 255):");
        int r = lerInteiro("Vermelho (R): ", 0, 255);
        int g = lerInteiro("Verde (G): ", 0, 255);
        int b = lerInteiro("Azul (B): ", 0, 255);
        int novaCor = 0xFF000000 | (r << 16) | (g << 8) | b;

        BufferedImage copia = servico.copiar(imagem);
        FloodFill floodFill = new FloodFill(servico);
        String pasta = usarPilha ? "saida_pilha" : "saida_fila";

        try {
            int pintados;
            if (usarPilha) {
                pintados = floodFill.preencherComPilha(copia, inicio, novaCor, pasta);
            } else {
                pintados = floodFill.preencherComFila(copia, inicio, novaCor, pasta);
            }

            if (pintados == 0) {
                System.out.println("A nova cor é igual à cor original da região. Nada foi alterado.");
            } else {
                System.out.println("Concluído, Pixels pintados: " + pintados);
                System.out.println("Imagens salvas na pasta: " + pasta);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar as imagens: " + e.getMessage());
        }
    }
}
