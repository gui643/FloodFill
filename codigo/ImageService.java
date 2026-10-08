package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageService {

     public BufferedImage abrir(String caminho) throws IOException {
        File arquivo = new File(caminho);
        if (!arquivo.exists() || !arquivo.isFile()) {
            throw new IOException("Arquivo não encontrado: " + caminho);
        }
        BufferedImage lida = ImageIO.read(arquivo);
        if (lida == null) {
            throw new IOException("O arquivo não é uma imagem válida (use BMP, PNG, JPG ou GIF).");
        }
        return copiar(lida);
    }

    public BufferedImage copiar(BufferedImage origem) {
        BufferedImage copia = new BufferedImage(origem.getWidth(), origem.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = copia.createGraphics();
        g.drawImage(origem, 0, 0, Color.WHITE, null);
        g.dispose();
        return copia;
    }

    public void prepararPasta(String pasta) throws IOException {
        File dir = new File(pasta);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Não foi possível criar a pasta: " + pasta);
        }
        File[] arquivos = dir.listFiles();
        if (arquivos != null) {
            for (File f : arquivos) {
                if (f.getName().startsWith("passo_") && f.getName().endsWith(".bmp")) {
                    f.delete();
                }
            }
        }
    }

    public void salvarPasso(BufferedImage imagem, String pasta, int numero) throws IOException {
        String nome = String.format("passo_%04d.bmp", numero);
        File destino = new File(pasta, nome);
        if (!ImageIO.write(imagem, "bmp", destino)) {
            throw new IOException("Não foi possível salvar o arquivo: " + destino.getPath());
        }
    }
}
