import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

// 메인 클래스
public class Main {
    private JFrame frame; // 메인 프레임
    private JTextField assignmentField; // 과제 입력 필드
    private JTextField subjectField; // 과목명 표시 필드
    private DefaultListModel<String> taskListModel; // 과제 목록을 관리하는 모델
    private JList<String> taskList; // 과제를 표시하는 리스트
    private File currentSubjectFolder; // 현재 과목의 폴더

    public Main() {
        openAssignmentWindow(); // 초기 과제 입력 창 열기
    }

    // 과제 입력 창을 여는 메서드
    private void openAssignmentWindow() {
        JFrame assignmentFrame = new JFrame("과제 입력"); // 과제 입력 프레임 생성
        assignmentFrame.setSize(220, 200);
        assignmentFrame.getContentPane().setBackground(Color.GRAY);
        assignmentFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("과목의 이름을 적어주십시오."); // 과목 입력 라벨
        assignmentField = new JTextField(15); // 과목 입력 필드
        JButton nextButton = new JButton("다음"); // 다음 버튼
        JButton listButton = new JButton("목록"); // 목록 버튼

        // 다음 버튼 클릭 시 과목 입력 창 닫고 입력 화면 열기
        nextButton.addActionListener(e -> {
            String subjectText = assignmentField.getText();
            if (!subjectText.isEmpty()) {
                assignmentFrame.dispose();
                openInputWindow(subjectText);
            }
        });

        // 목록 버튼 클릭 시 과목 목록 화면 열기
        listButton.addActionListener(e -> {
            assignmentFrame.dispose();
            openSubjectList();
        });

        // 프레임에 컴포넌트 추가
        assignmentFrame.add(label);
        assignmentFrame.add(assignmentField);
        assignmentFrame.add(nextButton);
        assignmentFrame.add(listButton);

        assignmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        assignmentFrame.setVisible(true); // 프레임 표시
    }

    // 과제 입력 화면을 여는 메서드
    private void openInputWindow(String subjectText) {
        currentSubjectFolder = new File(subjectText); // 과목 이름으로 폴더 생성
        if (!currentSubjectFolder.exists()) {
            currentSubjectFolder.mkdir(); // 폴더가 없으면 생성
        }

        frame = new JFrame("입력 프로그램"); // 입력 프로그램 프레임 생성
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setLayout(new BorderLayout());

        // 제목 패널 생성
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createTitledBorder("과목명"));
        subjectField = new JTextField(subjectText); // 과목명 표시 필드
        subjectField.setEditable(false); // 수정 불가
        titlePanel.add(subjectField);

        // 과제 목록 모델 및 리스트 생성
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskRenderer());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    toggleTaskCompletion(index); // 과제 클릭 시 완료 상태 토글
                }
            }
        });

        // 과제 리스트를 위한 스크롤 패널 생성
        JScrollPane taskScrollPane = new JScrollPane(taskList);
        taskScrollPane.setPreferredSize(new Dimension(350, 200));

        // 입력 패널 생성
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JTextField inputField = new JTextField(15); // 과제 입력 필드
        inputPanel.add(inputField);
        JButton addButton = new JButton("추가"); // 추가 버튼
        inputPanel.add(addButton);
        JButton completeButton = new JButton("완료"); // 완료 버튼
        inputPanel.add(completeButton);

        // 추가 버튼 클릭 시 과제 추가
        addButton.addActionListener(e -> {
            String taskText = inputField.getText();
            if (!taskText.isEmpty()) {
                addTaskToList(taskText); // 과제 목록에 추가
                saveTaskToFile(taskText); // 파일에 저장
                inputField.setText(""); // 입력 필드 초기화
            }
        });

        // 완료 버튼 클릭 시 모든 과제 저장 후 폴더 뷰로 전환
        completeButton.addActionListener(e -> {
            saveAllTasks(); // 모든 과제 저장
            openFolderView(subjectText); // 폴더 뷰 열기
        });

        // 입력 필드에서 엔터를 눌렀을 때 과제 추가
        inputField.addActionListener(e -> {
            String taskText = inputField.getText();
            if (!taskText.isEmpty()) {
                addTaskToList(taskText); // 과제 목록에 추가
                saveTaskToFile(taskText); // 파일에 저장
                inputField.setText(""); // 입력 필드 초기화
            }
        });

        // 프레임에 컴포넌트 추가
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(taskScrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true); // 프레임 표시

        loadTasksFromFile(); // 파일에서 과제 로드
    }

    // 과제 목록에 과제 추가
    private void addTaskToList(String taskText) {
        taskListModel.addElement("📌 " + taskText); // 이모지와 함께 추가
    }

    // 과제를 파일에 저장
    private void saveTaskToFile(String taskText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentSubjectFolder + "/tasks.txt", true))) {
            writer.write("📌 " + taskText); // 이모지와 함께 저장
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 파일에서 과제 로드
    private void loadTasksFromFile() {
        File taskFile = new File(currentSubjectFolder + "/tasks.txt");
        if (taskFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    taskListModel.addElement(line); // 파일에서 읽은 과제 추가
                }
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리
            }
        }
    }

    // 모든 과제를 저장하는 메서드
    private void saveAllTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentSubjectFolder + "/completed_tasks.txt"))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.get(i)); // 모든 과제 저장
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 폴더 뷰 열기
    private void openFolderView(String subjectText) {
        JFrame folderFrame = new JFrame(subjectText); // 과목 이름으로 프레임 생성
        folderFrame.setSize(300, 400);
        folderFrame.getContentPane().setBackground(Color.GRAY);
        folderFrame.setLayout(new BoxLayout(folderFrame.getContentPane(), BoxLayout.Y_AXIS));

        JButton folderButton = new JButton("📁 " + subjectText); // 폴더 버튼 생성
        folderButton.addActionListener(e -> {
            folderFrame.dispose(); // 폴더 뷰 닫기
            openInputWindow(subjectText); // 과목 입력 화면 열기
        });

        folderFrame.add(folderButton); // 프레임에 버튼 추가
        folderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        folderFrame.setVisible(true); // 프레임 표시
    }

    // 과제 완료 상태 토글
    private void toggleTaskCompletion(int index) {
        String taskText = taskListModel.getElementAt(index);
        if (taskText.startsWith("✔️ ")) {
            taskListModel.setElementAt(taskText.substring(2), index); // 완료 상태 해제
        } else {
            taskListModel.setElementAt("✔️ " + taskText.substring(2), index); // 완료 상태 설정
        }
    }

    // 저장된 과목 목록 열기
    private void openSubjectList() {
        JFrame subjectFrame = new JFrame("저장된 과목 목록");
        subjectFrame.setSize(300, 400);
        subjectFrame.getContentPane().setBackground(Color.GRAY);
        subjectFrame.setLayout(new BoxLayout(subjectFrame.getContentPane(), BoxLayout.Y_AXIS));

        File folder = new File("."); // 현재
