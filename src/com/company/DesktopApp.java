package com.company;

import javax.crypto.BadPaddingException;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;


public class DesktopApp extends JFrame{
    // Определяем настоящую дату:
    int YEAR  = LocalDate.now().getYear();
    int DAY   = LocalDate.now().getDayOfYear();

    // Приложение
    public DesktopApp() {
        super("Cryptograth");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание объектов:
        JLabel       label          = new JLabel      ("Шифровальная машина Cryptograth: 1.0.0"            );
        JLabel       dateText       = new JLabel      ("Введите дату:"                                     );
        JTextField   dateField      = new JTextField  (String.valueOf(LocalDate.ofYearDay(YEAR, DAY)), 6);
        JLabel       textText       = new JLabel      ("Введите желаемый текст:"                           );
        JTextArea    textArea       = new JTextArea   ("Привет мир!", 10, 20                  );
        JRadioButton cipherButton   = new JRadioButton("Зашифровать"                                       );
        JRadioButton decipherButton = new JRadioButton("Расшифровать"                                      );
        JButton      complete       = new JButton     ("Выполнить"                                         );
        JTextArea    output         = new JTextArea   (10, 20                                      );

        // Настройки кнопок:
        textArea.setLineWrap(true);
        output.setLineWrap(true);
        ButtonGroup bg=new ButtonGroup();
        bg.add(cipherButton);bg.add(decipherButton);

        // Создание панели с объектами:
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.CENTER));

        contents.add(label         );
        contents.add(dateText      );
        contents.add(dateField     );
        contents.add(textText      );
        contents.add(textArea      );
        contents.add(cipherButton  );
        contents.add(decipherButton);
        contents.add(complete      );
        contents.add(output        );

        setContentPane(contents);

        // Обработчик событий:
        complete.addActionListener(e -> {
            if (bg.isSelected(cipherButton.getModel())) {
                try {
                    output.setText( new Encryption().Cipher(textArea.getText(), dateField.getText()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if (bg.isSelected(decipherButton.getModel())) {
                try {
                    output.setText( new Encryption().Decipher(textArea.getText(), dateField.getText()));
                } catch (IllegalArgumentException | BadPaddingException ibex) {
                    JOptionPane.showMessageDialog(null, "Неподходящее зашифрованное сообщение.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Выберите зашифровать/расшифровать");
            }
        });

        // Определяем размер окна и выводим его на экран:
        setResizable(false);
        setSize(300, 500);
        setIconImage(new ImageIcon("icon.png").getImage());
        setVisible(true);
    }
    public static void main(String[] args) {
        new DesktopApp();
    }
}


