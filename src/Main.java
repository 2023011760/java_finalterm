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
        frame.setSize(400, 300);
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

                JOptionPane.showMessageDialog(frame, "입력한 과목: " + subjectName);

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
