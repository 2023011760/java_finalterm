import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;

public class Main {
    private JFrame frame;
    private JTextField assignmentField;
    private JTextField subjectField;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private File currentSubjectFolder;

    public Main() {
        openAssignmentWindow();
    }

    private void openAssignmentWindow() {
        JFrame assignmentFrame = new JFrame("과제 입력");
        assignmentFrame.setSize(220, 200);
        assignmentFrame.getContentPane().setBackground(Color.GRAY);
        assignmentFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("과목의 이름을 적어주십시오.");
        assignmentField = new JTextField(15);
        JButton nextButton = new JButton("다음");
        JButton listButton = new JButton("목록");

        nextButton.addActionListener(e -> {
            String subjectText = assignmentField.getText();
            if (!subjectText.isEmpty()) {
                assignmentFrame.dispose();
                openInputWindow(subjectText);
            }
        });

        listButton.addActionListener(e -> {
            assignmentFrame.dispose();
            openSubjectList();
        });

        assignmentFrame.add(label);
        assignmentFrame.add(assignmentField);
        assignmentFrame.add(nextButton);
        assignmentFrame.add(listButton);

        assignmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        assignmentFrame.setVisible(true);
    }

    private void openInputWindow(String subjectText) {
        currentSubjectFolder = new File(subjectText);
        if (!currentSubjectFolder.exists()) {
            currentSubjectFolder.mkdir();
        }

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
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
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
        JButton completeButton = new JButton("완료");
        inputPanel.add(completeButton);

        addButton.addActionListener(e -> {
            String taskText = inputField.getText();
            if (!taskText.isEmpty()) {
                addTaskToList(taskText);
                saveTaskToFile(taskText);
                inputField.setText("");
            }
        });

        completeButton.addActionListener(e -> {
            saveAllTasks();
            openFolderView(subjectText);
        });

        inputField.addActionListener(e -> {
            String taskText = inputField.getText();
            if (!taskText.isEmpty()) {
                addTaskToList(taskText);
                saveTaskToFile(taskText);
                inputField.setText("");
            }
        });

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(taskScrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        loadTasksFromFile();
    }

    private void addTaskToList(String taskText) {
        taskListModel.addElement("📌 " + taskText);
    }

    private void saveTaskToFile(String taskText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentSubjectFolder + "/tasks.txt", true))) {
            writer.write("📌 " + taskText);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasksFromFile() {
        File taskFile = new File(currentSubjectFolder + "/tasks.txt");
        if (taskFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    taskListModel.addElement(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAllTasks() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentSubjectFolder + "/completed_tasks.txt"))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFolderView(String subjectText) {
        JFrame folderFrame = new JFrame(subjectText);
        folderFrame.setSize(300, 400);
        folderFrame.getContentPane().setBackground(Color.GRAY);
        folderFrame.setLayout(new BoxLayout(folderFrame.getContentPane(), BoxLayout.Y_AXIS));

        JButton folderButton = new JButton("📁 " + subjectText);
        folderButton.addActionListener(e -> {
            folderFrame.dispose();
            openInputWindow(subjectText);
        });

        folderFrame.add(folderButton);
        folderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        folderFrame.setVisible(true);
    }

    private void toggleTaskCompletion(int index) {
        String taskText = taskListModel.getElementAt(index);
        if (taskText.startsWith("✔️ ")) {
            taskListModel.setElementAt(taskText.substring(2), index);
        } else {
            taskListModel.setElementAt("✔️ " + taskText.substring(2), index);
        }
    }

    private void openSubjectList() {
        JFrame subjectFrame = new JFrame("저장된 과목 목록");
        subjectFrame.setSize(300, 400);
        subjectFrame.getContentPane().setBackground(Color.GRAY);
        subjectFrame.setLayout(new BoxLayout(subjectFrame.getContentPane(), BoxLayout.Y_AXIS));

        File folder = new File(".");
        File[] subjectFolders = folder.listFiles(File::isDirectory);

        if (subjectFolders != null) {
            for (File subjectFolder : subjectFolders) {
                JButton subjectButton = new JButton(subjectFolder.getName());
                subjectButton.addActionListener(e -> {
                    subjectFrame.dispose();
                    openInputWindow(subjectFolder.getName());
                });
                subjectFrame.add(subjectButton);
            }
        }

        subjectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        subjectFrame.setVisible(true);
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
