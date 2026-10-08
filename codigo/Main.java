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
}
