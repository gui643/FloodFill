package codigo;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class FloodFill {

    private static final int[] DX = {0, 0, -1, 1};
    private static final int[] DY = {-1, 1, 0, 0};

    private ImageService servico;

    public FloodFill(ImageService servico) {
        this.servico = servico;
    }

    private boolean dentroDosLimites(BufferedImage imagem, int x, int y) {
        return x >= 0 && y >= 0 && x < imagem.getWidth() && y < imagem.getHeight();
    }

    private int calcularIntervalo(BufferedImage imagem) {
        return Math.max(1, (imagem.getWidth() * imagem.getHeight()) / 100);
    }

    public int preencherComPilha(BufferedImage imagem, Position inicio, int novaCor, String pasta) throws IOException {
        int corOriginal = imagem.getRGB(inicio.getX(), inicio.getY());
        if (corOriginal == novaCor) {
            return 0;
        }

        servico.prepararPasta(pasta);
        int intervalo = calcularIntervalo(imagem);
        int passo = 1;
        servico.salvarPasso(imagem, pasta, passo++); 

        Stack pilha = new Stack();
        pilha.push(new Node(inicio));
        int pintados = 0;

        while (!pilha.isEmpty()) {
            Position atual = pilha.pop().getPosition();
            int x = atual.getX();
            int y = atual.getY();

            if (!dentroDosLimites(imagem, x, y)) {
                continue;
            }
            if (imagem.getRGB(x, y) != corOriginal) {
                continue;
            }

            imagem.setRGB(x, y, novaCor);
            pintados++;

            for (int i = 0; i < 4; i++) {
                pilha.push(new Node(new Position(x + DX[i], y + DY[i])));
            }

            if (pintados % intervalo == 0) {
                servico.salvarPasso(imagem, pasta, passo++); 
            }
        }

        servico.salvarPasso(imagem, pasta, passo); 
        return pintados;
    }

    public int preencherComFila(BufferedImage imagem, Position inicio, int novaCor, String pasta) throws IOException {
        int corOriginal = imagem.getRGB(inicio.getX(), inicio.getY());
        if (corOriginal == novaCor) {
            return 0;
        }

        servico.prepararPasta(pasta);
        int intervalo = calcularIntervalo(imagem);
        int passo = 1;
        servico.salvarPasso(imagem, pasta, passo++); 

        Queue fila = new Queue();
        fila.enqueue(new Node(inicio));
        int pintados = 0;

        while (!fila.isEmpty()) {
            Position atual = fila.dequeue().getPosition();
            int x = atual.getX();
            int y = atual.getY();

            if (!dentroDosLimites(imagem, x, y)) {
                continue;
            }
            if (imagem.getRGB(x, y) != corOriginal) {
                continue;
            }

            imagem.setRGB(x, y, novaCor);
            pintados++;

            for (int i = 0; i < 4; i++) {
                fila.enqueue(new Node(new Position(x + DX[i], y + DY[i])));
            }

            if (pintados % intervalo == 0) {
                servico.salvarPasso(imagem, pasta, passo++); 
            }
        }

        servico.salvarPasso(imagem, pasta, passo); 
        return pintados;
    }
}
