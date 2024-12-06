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
    private JTextField questionField;
    private JTextArea questionsArea;
    private String subjectName;

    public SecondFrame(String subjectName) {
        this.subjectName = subjectName;

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

        JPanel assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));
        JLabel assignmentLabel = new JLabel("과제관");
        assignmentPanel.add(assignmentLabel);
        assignmentPanel.add(Box.createVerticalStrut(10));

        questionsArea = new JTextArea();
        questionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(questionsArea);
        assignmentPanel.add(scrollPane);
        mainPanel.add(assignmentPanel);

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        JLabel questionLabel = new JLabel("질문관");
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(10));

        JTextArea questionTextArea = new JTextArea(10, 20);
        questionTextArea.setEditable(false);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        questionPanel.add(questionScrollPane);
        mainPanel.add(questionPanel);

        frame.add(mainPanel, BorderLayout.CENTER);

        questionField = new JTextField();
        questionField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = questionField.getText();
                if (!question.isEmpty()) {
                    questionsArea.append(question + "\n");
                    questionTextArea.append(question + "\n");
                    questionField.setText("");
                }
            }
        });
        frame.add(questionField, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        JButton completeButton = new JButton("완료");
        bottomPanel.add(completeButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
