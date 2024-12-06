import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTextField textField;

    public Main() {
        frame = new JFrame("과목 입력");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("과목의 이름을 적어주십시오.");
        label.setFont(new Font("돋움", Font.PLAIN, 20));
        label.setForeground(Color.BLACK);
        frame.add(label);

        textField = new JTextField(30);
        textField.setPreferredSize(new Dimension(300, 40));
        frame.add(textField);

        JButton nextButton = new JButton("다음");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectName = textField.getText();
                new SecondFrame(subjectName);
                frame.dispose();
            }
        });

        frame.add(Box.createVerticalStrut(20));
        frame.add(nextButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

class SecondFrame {
    private JFrame frame;
    private JTextField assignmentField;
    private JTextField questionField;
    private JPanel assignmentPanel;
    private JPanel questionPanel;

    public SecondFrame(String subjectName) {
        frame = new JFrame("텍스트박스 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new BorderLayout());

        JLabel subjectLabel = new JLabel("과목: " + subjectName);
        subjectLabel.setFont(new Font("돋움", Font.PLAIN, 20));
        subjectLabel.setForeground(Color.BLACK);
        frame.add(subjectLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));
        JLabel assignmentLabel = new JLabel("과제");
        assignmentPanel.add(assignmentLabel);
        assignmentPanel.add(Box.createVerticalStrut(10));

        assignmentField = new JTextField();
        JButton assignmentButton = new JButton("완료");
        assignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assignmentText = assignmentField.getText();
                if (!assignmentText.isEmpty()) {
                    JTextArea textArea = new JTextArea(1, 20);
                    textArea.setText(assignmentText);
                    textArea.setEditable(false);
                    assignmentPanel.add(textArea);
                    assignmentPanel.add(Box.createVerticalStrut(5));
                    assignmentField.setText("");
                    assignmentPanel.revalidate();  // 레이아웃 갱신
                    assignmentPanel.repaint();
                }
            }
        });
        assignmentPanel.add(assignmentField);
        assignmentPanel.add(assignmentButton);
        mainPanel.add(assignmentPanel);

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        JLabel questionLabel = new JLabel("질문");
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(10));

        questionField = new JTextField();
        JButton questionButton = new JButton("완료");
        questionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String questionText = questionField.getText();
                if (!questionText.isEmpty()) {
                    JTextArea textArea = new JTextArea(1, 20);
                    textArea.setText(questionText);
                    textArea.setEditable(false);
                    questionPanel.add(textArea);
                    questionPanel.add(Box.createVerticalStrut(5));
                    questionField.setText("");
                    questionPanel.revalidate();  // 레이아웃 갱신
                    questionPanel.repaint();
                }
            }
        });
        questionPanel.add(questionField);
        questionPanel.add(questionButton);
        mainPanel.add(questionPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
