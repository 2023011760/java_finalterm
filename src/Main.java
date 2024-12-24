import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main {
    private JFrame frame;
    private JTextField assignmentField;
    private JTextField subjectField;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;

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

        nextButton.addActionListener(e -> {
            String subjectText = assignmentField.getText();
            if (!subjectText.isEmpty()) {
                assignmentFrame.dispose();
                openInputWindow(subjectText);
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

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskRenderer());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    toggleTaskCompletion(index);
                }
            }
        });

        JScrollPane taskScrollPane = new JScrollPane(taskList);
        taskScrollPane.setPreferredSize(new Dimension(350, 200));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JTextField inputField = new JTextField(15);
        inputPanel.add(inputField);
        JButton addButton = new JButton("추가");
        inputPanel.add(addButton);

        addButton.addActionListener(e -> {
            String taskText = inputField.getText();
            if (!taskText.isEmpty()) {
                addTaskToList(taskText);
                inputField.setText("");
            }
        });

        inputField.addActionListener(e -> {
            String taskText = inputField.getText();
            if (!taskText.isEmpty()) {
                addTaskToList(taskText);
                inputField.setText("");
            }
        });

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(taskScrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addTaskToList(String taskText) {
        taskListModel.addElement(taskText);
    }

    private void toggleTaskCompletion(int index) {
        String taskText = taskListModel.getElementAt(index);
        if (taskText.startsWith("✔️ ")) {
            taskListModel.setElementAt(taskText.substring(2), index);
        } else {
            taskListModel.setElementAt("✔️ " + taskText, index);
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    class TaskRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value.toString().startsWith("✔️ ")) {
                label.setText("<html><strike>" + value.toString().substring(2) + "</strike></html>");
            } else {
                label.setText(value.toString());
            }
            return label;
        }
    }
}
