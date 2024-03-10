import javax.swing.*;
import java.awt.*;

public class ImageViewer extends JFrame implements Runnable{
    private JScrollPane panel;
    private ImageHolder imageHolder;

    public ImageViewer(String archivo){
        super("Reloj Analogico");
        Image img = Toolkit.getDefaultToolkit().getImage(archivo);
        imageHolder = new ImageHolder(img);
        panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(panel);
        panel.setViewportView(imageHolder);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 445);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        this.run();
    }


    public static void main(String[] args) {
        new ImageViewer("src/RelojAnalogico.jpg");
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
        }
    }
}
