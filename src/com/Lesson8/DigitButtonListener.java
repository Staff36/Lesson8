package com.Lesson8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DigitButtonListener implements ActionListener {
private final JTextField textField;
    public DigitButtonListener(JTextField textField){
        this.textField=textField;
    }

@Override
    public void actionPerformed(ActionEvent e) {

        char lastChar='q';
        JButton jButton=(JButton) e.getSource();
        StringBuilder stringBuilder= new StringBuilder(textField.getText());
    if (stringBuilder.length()>1){
        lastChar=stringBuilder.charAt(stringBuilder.length()-1);}

    stringBuilder.append(jButton.getText());
    if ((int)jButton.getText().charAt(0)>47 &&(int) jButton.getText().charAt(0)<58){
        textField.setText(stringBuilder.toString());
    }
    else if((int)lastChar>=48&&(int)lastChar<=57|| lastChar=='q'){
        textField.setText(stringBuilder.toString());
    }
    else{
        stringBuilder.deleteCharAt(stringBuilder.length()-2);
        textField.setText(stringBuilder.toString());
         }

}

}
