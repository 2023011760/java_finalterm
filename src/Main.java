import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTextField assignmentField;
    private JTextField questionField;
    private JPanel assignmentPanel;
    private JPanel questionPanel;

    public Main() {
        frame = new JFrame("입력 프로그램");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new GridLayout(1, 2));

        assignmentPanel = createTextBoxPanel("과제");
        questionPanel = createTextBoxPanel("질문");

        frame.add(assignmentPanel);
        frame.add(questionPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        assignmentField = new JTextField(10);
        inputPanel.add(assignmentField);

        questionField = new JTextField(10);
        inputPanel.add(questionField);

        JButton submitButton = new JButton("완료");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assignmentText = assignmentField.getText();
                String questionText = questionField.getText();
                if (!assignmentText.isEmpty()) {
                    addTextToPanel(assignmentPanel, assignmentText);
                    assignmentField.setText("");
                }
                if (!questionText.isEmpty()) {
                    addTextToPanel(questionPanel, questionText);
                    questionField.setText("");
                }
            }
        });
        inputPanel.add(submitButton);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel createTextBoxPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(150, 70));
        return panel;
    }

    private void addTextToPanel(JPanel panel, String text) {
        JTextArea textArea = new JTextArea(1, 15);
        textArea.setText(text);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        panel.add(textArea);
        panel.add(Box.createVerticalStrut(5));
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
