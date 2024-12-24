import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTextField assignmentField;
    private JPanel assignmentPanel;

    public Main() {
        openAssignmentWindow();
    }

    private void openAssignmentWindow() {
        JFrame assignmentFrame = new JFrame("과제 입력");
        assignmentFrame.setSize(400, 200);
        assignmentFrame.getContentPane().setBackground(Color.GRAY);
        assignmentFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("과제를 입력해주세요:");
        assignmentField = new JTextField(15);
        JButton nextButton = new JButton("다음");

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assignmentText = assignmentField.getText();
                if (!assignmentText.isEmpty()) {
                    assignmentFrame.dispose();
                    openInputWindow(assignmentText);
                }
            }
        });

        assignmentFrame.add(label);
        assignmentFrame.add(assignmentField);
        assignmentFrame.add(nextButton);

        assignmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        assignmentFrame.setVisible(true);
    }

    private void openInputWindow(String assignmentText) {
        frame = new JFrame("입력 프로그램");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new GridLayout(1, 1));

        assignmentPanel = createTextBoxPanel("입력한 과제");
        addTextToPanel(assignmentPanel, assignmentText);

        frame.add(assignmentPanel);
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
