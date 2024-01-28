import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Main result window class - continuation of program
public class resultWindowClass {
    // declaring variables
    private String[] userAnswers;
    private String[] answerKey;

    pageFrame resultWindow = new pageFrame();
    JPanel resultbody = createResultPanel();
    JLabel resultTitle = createResultLabel("scoreTitle");
    JLabel scoreLabel = createResultLabel("score");
    private int score = 0;
    private boolean checkAnswers[] = new boolean[5];
    
    JPanel ansPanel = createAnsPanel(125, 425, 2);
    JPanel ansIconPanel = createAnsPanel(50, 75, 1);
    JLabel userAnsLabel = createAnsLabel("Your answers");
    JLabel correctAnsLabel = createAnsLabel("Correct answers");

    resultWindowClass(String[] userAnswers, String[] answerKey) {
        this.userAnswers = userAnswers;
        this.answerKey = answerKey;

        score = calculateScore();
        scoreLabel.setText(score +  "/" + answerKey.length);

        resultbody.add(resultTitle);
        resultbody.add(scoreLabel);
        resultbody.add(ansPanel);
        resultbody.add(ansIconPanel);

        ansPanel.add(userAnsLabel);
        ansPanel.add(correctAnsLabel);
        ansIconPanel.add(new JLabel());
        addAnswersToPanel();

        resultWindow.add(resultbody);
        resultWindow.setVisible(true);
    }

    // functions to create swing components
    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0xFFFFFF));
        panel.setBounds(90, 75, 600, 400);
        panel.setLayout(null);
        panel.setOpaque(true);
        return panel;
    }

    private JLabel createResultLabel(String text) {
        JLabel label = new JLabel();
        if (text.equals("scoreTitle")) {
            label.setText("Score:");
            label.setForeground(new Color(0x000000));
            label.setFont(new Font("Open Sans", Font.PLAIN, 50));
            label.setBounds(175, 30, 200, 50);
        } else if (text.equals("score")) {
            label.setForeground(new Color(0x000000));
            label.setFont(new Font("Open Sans", Font.BOLD, 50));
            label.setBounds(330, 30, 100, 50);
        } else {
        }
        return label;
    }

    private JPanel createAnsPanel(int x_pos, int width, int col) {
        JPanel panel = new JPanel();
        panel.setBounds(x_pos, 90, width, 275);
        panel.setLayout(new GridLayout(6, col, 5, 5));
        panel.setOpaque(false);
        return panel;
    }

    private int calculateScore() {
        int x = 0;
        for (int i = 0; i < answerKey.length; i++) {
            if (userAnswers[i].equals(answerKey[i])) {
                checkAnswers[i] = true;
                x++;
            }
        }
        return x;
    }

    private JLabel createAnsLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(0x000000));
        label.setFont(new Font("DM Sans", Font.BOLD, 22));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        return label;
    }

    // function to show user's answers and the correct answers
    private void addAnswersToPanel() {
        ImageIcon tickIcon = createImagePanel("Green-tick-mark-icon.jpg");
        ImageIcon crossIcon = createImagePanel("Red-cross-mark-icon.jpg");

        for (int i = 0; i < answerKey.length; i++) {
            JLabel quesIcon = createAnsLabel("Q."+(i+1));
            quesIcon.setFont(new Font("DM Sans", Font.PLAIN, 15));
            if (userAnswers[i].equals(answerKey[i])) {
                quesIcon.setIcon(tickIcon);
            } else {
                quesIcon.setIcon(crossIcon);
            }
            quesIcon.setIconTextGap(20);
            ansIconPanel.add(quesIcon);

            JLabel userAns = createAnsLabel(userAnswers[i]);
            userAns.setFont(new Font("DM Sans", Font.PLAIN, 15));
            ansPanel.add(userAns);
            
            JLabel answer = createAnsLabel(answerKey[i]);
            answer.setFont(new Font("DM Sans", Font.PLAIN, 15));
            ansPanel.add(answer);
        }
    }

    private ImageIcon createImagePanel(String imageName) {
        ImageIcon icon = new ImageIcon();
        try {
            BufferedImage originalImage = ImageIO.read(new File(imageName));
            Image resizedImage = originalImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }
}