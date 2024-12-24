import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTextField assignmentField;
    private JTextField subjectField;
    private JTextArea taskArea;

    public Main() {
        openAssignmentWindow();
    }

    private void openAssignmentWindow() {
        JFrame assignmentFrame = new JFrame("과제 입력");
        assignmentFrame.setSize(400, 200);
        assignmentFrame.getContentPane().setBackground(Color.GRAY);
        assignmentFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("과목의 이름을 적어주십시오.");
        assignmentField = new JTextField(15);
        JButton nextButton = new JButton("다음");

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectText = assignmentField.getText();
                if (!subjectText.isEmpty()) {
                    assignmentFrame.dispose();
                    openInputWindow(subjectText);
                }
            }
        });

        assignmentFrame.add(label);
        assignmentFrame.add(assignmentField);
        assignmentFrame.add(nextButton);

        assignmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        assignmentFrame.setVisible(true);
    }

    private void openInputWindow(String subjectText) {
        frame = new JFrame("입력 프로그램");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createTitledBorder("과목명"));
        subjectField = new JTextField(subjectText);
        subjectField.setEditable(false);
        titlePanel.add(subjectField);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBorder(BorderFactory.createTitledBorder("과제"));
        taskArea = new JTextArea(10, 30);
        taskArea.setEditable(false);
        taskPanel.add(taskArea);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JTextField inputField = new JTextField(15);
        inputPanel.add(inputField);
        JButton addButton = new JButton("추가");
        inputPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskText = inputField.getText();
                if (!taskText.isEmpty()) {
                    addTaskToArea(taskText);
                    inputField.setText("");
                }
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskText = inputField.getText();
                if (!taskText.isEmpty()) {
                    addTaskToArea(taskText);
                    inputField.setText("");
                }
            }
        });

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(taskPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addTaskToArea(String taskText) {
        taskArea.append("📌 " + taskText + "\n");
    }

    public static void main(String[] args) {
        new Main();
    }
}
