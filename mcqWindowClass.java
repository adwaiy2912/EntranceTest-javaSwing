import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

// Main MCQ window class - continuation of program
public class mcqWindowClass implements ActionListener {
    // declaring MCQ page variables
    pageFrame mcqWindow = new pageFrame();
    JPanel mcqTitlePanel = createMcqPanel(50, 69);
    JPanel mcqBody = createMcqPanel(120, 400);
    JLabel mcqTitle = createMCQLabel("Title");
    JLabel mcqQuesNum = createMCQLabel("QuesNum");

    private String[] mcqQuestionArray = {"<html>Q. What is the primary function of mitochondria in a cell?</html>",
                        "<html>Q. Which gas is responsible for the greenhouse effect on Earth?</html>",
                        "<html>Q. What is the chemical symbol for gold?</html>",
                        "<html>Q. In the context of genetics, what does DNA stand for?</html>",
                        "<html>Q. Which planet is known as the Red Planet in our solar system?</html>"};
    private String[][] mcqOptionsArrray = {{"Protein synthesis", "Energy production", "Waste elimination", "Cell division"},
                        {"Oxygen", "Nitrogen", "Carbon dioxide", "Hydrogen"},
                        {"Go", "Au", "Ag", "Ge"},
                        {"Deoxyribonucleic acid", "Ribonucleic acid", "Deoxyribose nucleotide assembly", "RNA polymerase"},
                        {"Venus", "Saturn", "Jupiter", "Mars"}};
    private int[] mcqUserAnswers = new int[5];
    private String[] mcqAnswerKey = {"Energy production", "Carbon dioxide", "Au", "Deoxyribonucleic acid", "Mars"};

    JLabel mcqQuestion = createMCQLabel("Question");
    ButtonGroup mcqOptionsGrp = new ButtonGroup();
    JRadioButton mcqOption1 = createMcqOption(110);
    JRadioButton mcqOption2 = createMcqOption(150);
    JRadioButton mcqOption3 = createMcqOption(190);
    JRadioButton mcqOption4 = createMcqOption(230);
    JButton mcqPrevButton = createMcqButton(" < Previous ", Color.red, 40);
    JButton mcqNextButton = createMcqButton(" Next > ", Color.green, 400);
    int quesNum = 0;
    ArrayList<JRadioButton> mcqOptionsButtonList = new ArrayList<>();

    mcqWindowClass() {
        setMcqQuestion();
        addOptionButtons();

        mcqTitlePanel.add(mcqTitle);
        mcqTitlePanel.add(mcqQuesNum);

        mcqBody.add(mcqQuestion);
        mcqBody.add(mcqOption1);
        mcqBody.add(mcqOption2);
        mcqBody.add(mcqOption3);
        mcqBody.add(mcqOption4);
        mcqBody.add(mcqPrevButton);
        mcqBody.add(mcqNextButton);

        mcqPrevButton.addActionListener(this);
        mcqNextButton.addActionListener(this);

        mcqWindow.add(mcqTitlePanel);
        mcqWindow.add(mcqBody);
        mcqWindow.setVisible(true);
    }

    // functions to create the MCQ page components
    private JLabel createMCQLabel(String type) {
        JLabel label = new JLabel();
        if (type.equals("Title")) {
            label.setText("MCQ Quiz");
            label.setForeground(new Color(0x000000));
            label.setFont(new Font("Open Sans", Font.BOLD, 35));
            label.setBounds(30, -2, 200, 75);
        } else if (type.equals("QuesNum")) {
            label.setForeground(new Color(0x9295A0));
            label.setFont(new Font("Open Sans", Font.PLAIN, 15));
            label.setBounds(540, 25, 50, 20);
        } else if (type.equals("Question")) {
            label.setForeground(new Color(0x27282B));
            label.setFont(new Font("DM Sans", Font.PLAIN, 25));
            label.setBounds(25, 25, 550, 60);
        } else {
        }
        return label;
    }

    private void addOptionButtons() {
        mcqOptionsGrp.add(mcqOption1);
        mcqOptionsGrp.add(mcqOption2);
        mcqOptionsGrp.add(mcqOption3);
        mcqOptionsGrp.add(mcqOption4);

        mcqOptionsButtonList.add(mcqOption1);
        mcqOptionsButtonList.add(mcqOption2);
        mcqOptionsButtonList.add(mcqOption3);
        mcqOptionsButtonList.add(mcqOption4);

        Arrays.fill(mcqUserAnswers, -1);
    }

    private JPanel createMcqPanel(int y_pos, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0xFFFFFF));
        panel.setBounds(100, y_pos, 600, height);
        panel.setLayout(null);
        panel.setOpaque(true);
        return panel;
    }

    private JRadioButton createMcqOption(int y_pos) {
        JRadioButton radioButton = new JRadioButton();
        radioButton.setFont(new Font("Alegreya Sans", Font.PLAIN, 17));
        radioButton.setFocusable(false);
        radioButton.setBounds(60, y_pos, 300, 25);
        radioButton.setContentAreaFilled(false);
        radioButton.setBorderPainted(false);
        radioButton.setOpaque(false);
        return radioButton;
    }

    private JButton createMcqButton (String text, Color color, int x_pos) {
        JButton button = new JButton();
        button.setText(text);
        button.setForeground(new Color(0xFFFFFF));
        button.setBackground(color);
        button.setFont(new Font("Sofia Sans", Font.BOLD, 20));
        button.setBounds(x_pos, 330, 150, 40);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createBevelBorder(0));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateUserAnswers();

        if (e.getSource() == mcqNextButton) {
            if (quesNum != 4) {
                quesNum++;
                setMcqQuestion();
            } else {
                int choice = JOptionPane.showConfirmDialog(mcqWindow, "Submit Quiz?",
                null, JOptionPane.OK_CANCEL_OPTION);
                
                if (choice == 0) {
                    String[] userAnswers = new String[5];
                    for (int i = 0; i < 5; i++) {
                        if (mcqUserAnswers[i] == -1) {
                            userAnswers[i] = "";
                        } else {
                            userAnswers[i] = mcqOptionsArrray[i][mcqUserAnswers[i]];
                        }
                    }
                    // removes MCQ window and runs the result page
                    mcqWindow.dispose();
                    new resultWindowClass(userAnswers , mcqAnswerKey);
                }
            }
        }

        if (e.getSource() == mcqPrevButton && quesNum != 0) {
            quesNum--;
            setMcqQuestion();
        }

        if (mcqUserAnswers[quesNum] == -1) {
            mcqOptionsGrp.clearSelection();
        } else {
            mcqOptionsButtonList.get(mcqUserAnswers[quesNum]).setSelected(true);
        }
    }

    // changes the question options based on the user input
    private void setMcqQuestion() {
        mcqQuesNum.setText("(" + (quesNum+1) + " of 5)");
        mcqQuestion.setText(mcqQuestionArray[quesNum]);
        mcqOption1.setText(mcqOptionsArrray[quesNum][0]);
        mcqOption2.setText(mcqOptionsArrray[quesNum][1]);
        mcqOption3.setText(mcqOptionsArrray[quesNum][2]);
        mcqOption4.setText(mcqOptionsArrray[quesNum][3]);
    }

    // stores the user answers
    private void updateUserAnswers() {
        if (mcqOption1.isSelected()) {
            mcqUserAnswers[quesNum] = 0;
        } else if (mcqOption2.isSelected()) {
            mcqUserAnswers[quesNum] = 1;
        } else if (mcqOption3.isSelected()) {
            mcqUserAnswers[quesNum] = 2;
        } else if (mcqOption4.isSelected()) {
            mcqUserAnswers[quesNum] = 3;
        }
    }
}