package draft.view.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AboutUsFrame extends JFrame {

    public AboutUsFrame() {
        setTitle("About us");
        setLayout(new BorderLayout());

        JTextPane userInfoPane = new JTextPane();
        userInfoPane.setEditable(false);
        userInfoPane.setText("Ime: Filip Marčić\n" +
                "Indeks: 148/22/RN\n" +
                "Email: fmarcic14822rn@raf.rs\n\n" +
                "Ime: Kosta Baštić\n" +
                "Indeks: 136/24/SI\n" +
                "Email: kbastic14323ri@raf.rs"
                );

        userInfoPane.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(userInfoPane);

        add(scrollPane, BorderLayout.CENTER);

        JLabel pictureLabel = new JLabel();
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/creators.jpg"))).getImage();
        Image scaledImage = image.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        pictureLabel.setIcon(new ImageIcon(scaledImage));
        pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pictureLabel.setBackground(Color.WHITE);
        setLocationRelativeTo(null);

        add(pictureLabel, BorderLayout.NORTH);

        setSize(400, 350);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
