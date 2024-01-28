import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Main frame used for creating window
class pageFrame extends JFrame {
    pageFrame() {
        ImageIcon logo = new ImageIcon("Manipal-Uni-Logo.jpg");

        this.setTitle("Admission Entrance Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0x37A7DB));
        this.setLayout(null);
    }
}

// TextFields used for input of username and password for login page
class loginPgTextField extends JTextField {
    loginPgTextField (String text, int y_pos) {
        this.setText(text);
        this.setFont(new Font("Fira Sans", Font.PLAIN, 20));
        this.setForeground(new Color(0x292A2F));
        this.setBounds(110, y_pos, 300, 40);
        
        this.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                setText("");
            }

            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(text);
                }
            }
        });
    }
}

// Used for creating buttons on login window
class loginPgButton extends JButton {
    loginPgButton(String type) {
        if (type.equals("login")) {
            setText("Log In");
            setForeground(new Color(0xFFFFFF));
            setBackground(new Color(0x1d28c9));
            setFont(new Font("Sofia Sans", Font.BOLD, 25));
            setBounds(50, 240, 400, 50);
            setFocusPainted(false);
            setBorder(BorderFactory.createBevelBorder(0));
        } else if (type.equals("forgotPass")) {
            setText("Forgot Password?");
            setFocusPainted(false);
            setFont(new Font("Sofia Sans", Font.ITALIC, 15));
            setBounds(165, 300, 175, 30);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
        } else {
            System.out.println("Invalid input");
        }
    }
}

// Main login window class - start of program
public class loginWindowClass implements ActionListener {
    // declaring and initialising all the variables used for login page
    pageFrame loginWindow = new pageFrame();
    JLabel loginTitle = createTitleLabel();
    JPanel loginBody = createBodyPanel(new Color(0xFFFFFF), 150, 100);
    JPanel loginBodyShadow = createBodyPanel(new Color(0xDEE5E8), 160, 110);
    JLayeredPane loginLayer = createLoginLayer();

    loginPgTextField loginUserName = new loginPgTextField("Username", 110);
    JLabel userNameIconLabel = createImagePanel("Username-icon.jpg", 107);
    loginPgTextField loginUserPass = new loginPgTextField("Password", 175);
    JLabel userPassIconLabel = createImagePanel("Password-icon.jpg", 172);
    loginPgButton userLoginButton = new loginPgButton("login");
    loginPgButton userForgotPass = new loginPgButton("forgotPass");

    loginWindowClass() {
        userLoginButton.addActionListener(this);
        userForgotPass.addActionListener(this);

        loginBody.add(loginTitle);
        loginBody.add(userNameIconLabel);
        loginBody.add(loginUserName);
        loginBody.add(userPassIconLabel);
        loginBody.add(loginUserPass);
        loginBody.add(userLoginButton);
        loginBody.add(userForgotPass);

        loginWindow.add(loginLayer);
        loginWindow.setVisible(true);
    }

    // functions to create the login page components
    private JLabel createTitleLabel() {
        JLabel label = new JLabel();
        label.setText("Login");
        label.setForeground(new Color(0x000000));
        label.setFont(new Font("Open Sans", Font.BOLD, 50));
        label.setBounds(35, 10, 250, 75);
        return label;
    }

    private JPanel createBodyPanel(Color color, int x_pos, int y_pos) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBounds(x_pos, y_pos, 500, 350);
        panel.setLayout(null);
        panel.setOpaque(true);
        return panel;
    }

    private JLayeredPane createLoginLayer() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 800, 600);
        layeredPane.add(loginBodyShadow, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(loginBody, JLayeredPane.DRAG_LAYER);
        return layeredPane;
    }
    
    // shrink the size of image to 45x45 pixels
    private JLabel createImagePanel(String imageName, int y_pos) {
        JLabel label = new JLabel();
        try {
            BufferedImage originalImage = ImageIO.read(new File(imageName));
            Image resizedImage = originalImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);
            label.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        label.setBounds(55, y_pos, 45, 45);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userLoginButton) {
            if (loginUserName.getText().equals("adwaiy2912") && 
                    loginUserPass.getText().equals("adwaiy2912")) {
                // removes the login window and runs the MCQ page
                loginWindow.dispose();
                new mcqWindowClass();
            } else {
                JOptionPane.showMessageDialog(loginWindow, "Invalid Username or Password",
                null, JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == userForgotPass) {
            JOptionPane.showMessageDialog(loginWindow, "Contact Website Admin", 
            null, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}