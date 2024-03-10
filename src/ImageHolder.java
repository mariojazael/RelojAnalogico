
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Calendar;

public class ImageHolder extends JPanel {
    private int radio = 140;
    private int second = Calendar.getInstance().get(Calendar.SECOND);
    private int minute = Calendar.getInstance().get(Calendar.MINUTE);
    private int hour = Calendar.getInstance().get(Calendar.HOUR);
    private Image background;
    private final Image buffer;

    private double[] endSecondsCoordinates = new double[2];
    private double[] endMinutesCoordinates = new double[2];
    private double[] endHoursCoordinates = new double[2];

    public ImageHolder(Image img){
        this.buffer = img;
        setSize(400, 400);
    }

    public void paint(Graphics g){
        if(background == null){
            background = createImage(getWidth(), getHeight());
            Graphics bgGraphics = background.getGraphics();
            bgGraphics.setClip(0, 0, getWidth(), getHeight());
        }

        update(g);
    }

    public void update(Graphics g){
        Calendar calendar = Calendar.getInstance();

        g.drawImage(buffer, 0, 0, this);

        sinchronizeMinutesAndHours(g, calendar);

        sinchronizeSeconds(g, calendar);

        System.out.println(hour + " " + minute + " " + second);
    }

    private void sinchronizeSeconds(Graphics g, Calendar calendar) {
        second = calendar.get(Calendar.SECOND);
        endSecondsCoordinates = calculateEndCoordinates(Calendar.SECOND);
        paintHands(g, endSecondsCoordinates, Color.BLACK);
    }

    private void sinchronizeMinutesAndHours(Graphics g, Calendar calendar) {
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        endMinutesCoordinates = calculateEndCoordinates(Calendar.MINUTE);
        paintHands(g, endMinutesCoordinates, Color.BLUE);
        endHoursCoordinates = calculateEndCoordinates(Calendar.HOUR);
        paintHands(g, endHoursCoordinates, Color.RED);
    }

    private double[] calculateEndCoordinates(final int CONST){
        return CONST == Calendar.SECOND ? getXY(1.6666666667 * (second + 45)) :
                CONST == Calendar.MINUTE ? getXY(1.6666666667 * (minute + 45)) :
                        getXY(8.333333333 * (hour + 45));
    }

    private double[] getXY(double rotator){
        double[] coordinates = new double[2];
        double angulo = 2 * Math.PI * rotator / 100; // Calcular el ángulo para cada punto
        coordinates[0] = (radio * Math.cos(angulo)) + 200; // Calcular la coordenada x
        coordinates[1] = (radio * Math.sin(angulo)) + 200; // Calcular la coordenada y
        return coordinates;
    }

    private void paintHands(Graphics g, double[] endCoordinates, Color color){
        g.setColor(color);
        g.drawLine(200, 200, (int) endCoordinates[0], (int) endCoordinates[1] + 1);
        if(color.equals(Color.BLACK)) playSound();
    }

    private void playSound(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/tic-tac.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error al reproducir el sonido: " + e.getMessage());
        }
    }
}
