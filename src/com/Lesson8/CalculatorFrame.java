package com.Lesson8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorFrame extends JFrame {

    public CalculatorFrame() {
        //Создание объектов
        setTitle("Calculator");
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int)screenSize.getWidth()/3),((int)screenSize.getWidth()/5),300,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Создание панелей
        setLayout(new BorderLayout());
        //Верхняя панель
        JPanel top= new JPanel();
        add(top,BorderLayout.NORTH);
        JTextField outputField = new JTextField();
        outputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                StringBuilder sb= new StringBuilder();
                if((e.getKeyCode()>48&&e.getKeyCode()<58)||(e.getKeyCode()>95&&e.getKeyCode()<106))
                    outputField.setText(outputField.getText()+e.getKeyChar());
                else if ((outputField.getText().length()>0)&&
                        (e.getKeyChar()=='*'
                        || e.getKeyChar()=='/'
                        || e.getKeyChar()=='+'
                        || e.getKeyChar()=='-'
                        || e.getKeyChar()=='%')
                       &&
                        (outputField.getText().charAt(outputField.getText().length()-1)=='*'
                        ||outputField.getText().charAt(outputField.getText().length()-1) =='/'
                        ||outputField.getText().charAt(outputField.getText().length()-1) =='+'
                        ||outputField.getText().charAt(outputField.getText().length()-1) =='-'
                        ||outputField.getText().charAt(outputField.getText().length()-1) =='%'
                        ))
                    {sb.append(outputField.getText());
                    sb.deleteCharAt(outputField.getText().length()-1);
                    outputField.setText(sb.toString()+e.getKeyChar());
                }else if((outputField.getText().length()>0)
                        &&(e.getKeyChar()=='*'
                        || e.getKeyChar()=='/'
                        || e.getKeyChar()=='+'
                        || e.getKeyChar()=='-'
                        || e.getKeyChar()=='%')){
                    outputField.setText(outputField.getText()+e.getKeyChar());
                }else if (e.getKeyChar()=='.'){
                    putDotInField(outputField);
                }else if (e.getKeyCode()==10){
                    outputField.setText(getSolution(outputField));
                }
            }

        });
        top.setLayout(new BorderLayout());
        top.add(outputField,BorderLayout.CENTER);
        outputField.setEditable(false);
        top.setVisible(true);
        //Средняя панельс цифрами
        JPanel bottom= new JPanel();
        add(bottom, BorderLayout.CENTER);
        bottom.setLayout(new GridLayout(4,3));
        DigitButtonListener digitButtonListener= new DigitButtonListener(outputField);
        addDigitRow(7,bottom,digitButtonListener);
        addDigitRow(4,bottom,digitButtonListener);
        addDigitRow(1,bottom,digitButtonListener);
        JButton zero= new JButton(""+0);
        zero.addActionListener(digitButtonListener);
        bottom.add(zero);
        JButton dot= new JButton(".");
        dot.addActionListener((e)-> {
            putDotInField(outputField);
        });
        bottom.add(dot);
        JButton clear= new JButton("C");
        bottom.add(clear);
        clear.addActionListener((e)->outputField.setText(""));
        bottom.setVisible(true);
        //Панель арифместических операторов
        JPanel right = new JPanel();
        add(right,BorderLayout.EAST);
        right.setLayout(new GridLayout(4,2));
        addButton("+",right,digitButtonListener);
        addButton("-",right,digitButtonListener);
        addButton("*",right,digitButtonListener);
        addButton("/",right,digitButtonListener);
        JButton sqrt= new JButton("sqrt");
        right.add(sqrt);
        sqrt.addActionListener((e)->{
                outputField.setText(String.valueOf(Math.sqrt(calculate(outputField.getText()))));
        });
        JButton evaluate= new JButton("=");
        right.add(evaluate);
        evaluate.addActionListener((e)->outputField.setText(getSolution(outputField)));

        right.setVisible(true);
        setVisible(true);
    }

    private void addDigitRow(int startNumInRow,JPanel bottom,DigitButtonListener buttonListener){
        for (int i = 0; i <3 ; i++) {
            addButton(startNumInRow+i,bottom,buttonListener);

        }

    }

    private void addButton(String textOnButton, JPanel panel,DigitButtonListener buttonListener){
        JButton jButton= new JButton(textOnButton);
        jButton.addActionListener(buttonListener);
        panel.add(jButton);
    }

    private void addButton(int digitOnButton, JPanel panel,DigitButtonListener digitButtonListener){
        JButton jButton= new JButton(String.valueOf(digitOnButton));
        jButton.addActionListener(digitButtonListener);
        panel.add(jButton);
    }

    private double calculate(String outputValue) {

        if (outputValue.charAt(0)=='-')
            outputValue="0"+outputValue;
        if (!outputValue.contains("+") && !outputValue.contains("-") && !outputValue.contains("*") && !outputValue.contains("/")) {
            return Double.parseDouble(outputValue);
        }
        int i;
        for (i = outputValue.length() - 1; i >= 0; i--) {
            if (outputValue.charAt(i) == '+' || outputValue.charAt(i) == '-') {
                break;
            }
        }
        if (i < 0) {
            for (i = outputValue.length() - 1; i >= 0; i--) {
                if (outputValue.charAt(i) == '*' || outputValue.charAt(i) == '/') {
                    break;
                }
            }
        }
       String leftSide = outputValue.substring(0, i);
       String rightSide = outputValue.substring(i + 1, outputValue.length());

        double result = 0;
        if (rightSide==""){
            result=calculate(leftSide);
        }else
            {
        switch (outputValue.charAt(i)) {
            case '+':
                result = calculate(leftSide) + calculate(rightSide);
                break;
            case '-':
                result = calculate(leftSide) - calculate(rightSide);
                break;
            case '*':
                result = calculate(leftSide) * calculate(rightSide);
                break;
            case '/':
                double right = calculate(rightSide);

                if (right == 0) //if denominator is zero
                { throw new ArithmeticException("illegal denominator");

                } else {
                    result = calculate(leftSide) / right;
                }
                break;
         }
        }
        return result;
    }
    private String getSolution(JTextField outputField){
        return String.valueOf(calculate(outputField.getText()));
    }
    private void putDotInField(JTextField outputField){
        if (outputField.getText().length()==0){
            outputField.setText(""+0+'.');
        }else
        {
            for (int i = outputField.getText().length()-1; i >=0 ; i--) {
                if (outputField.getText().charAt(i)=='*'||
                        outputField.getText().charAt(i)=='/'||
                        outputField.getText().charAt(i)=='+'||
                        outputField.getText().charAt(i)=='-'||
                        outputField.getText().charAt(i)=='%'){
                    break;
                }else if (outputField.getText().charAt(i)=='.'){
                    return;
                }
            }
            outputField.setText(outputField.getText()+'.');
        }
    }
}

