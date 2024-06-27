package com.zetcode;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;



public class Sokoban extends JFrame {

    private final int OFFSET = 30;

    public Sokoban() {

        initUI();
    }

    public void initUI() {
        
        Board board = new Board();
        add(board);

        setTitle("Sokoban");
        
        setSize(board.getBoardWidth() + OFFSET,
                board.getBoardHeight() + 2 * OFFSET);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {



        EventQueue.invokeLater(() -> {

            Sokoban game = new Sokoban();
            game.setVisible(true);

            SpeechRecognizerService.initialize();

            // 创建语音识别服务实例
            SpeechRecognizerService recognizerService = new SpeechRecognizerService();

            // 启动一个新的线程来处理语音识别，避免主线程阻塞
            new Thread(() -> {
                while (true) {
                    recognizerService.startListening();
                    try {
                        // 等待语音识别过程结束
                        Thread.sleep(1000); // 根据需要调整等待时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    recognizerService.stopListening();

                    // 获取并输出识别结果
                    String result = recognizerService.getResultText();
                    System.out.println("识别结果: " + result);
                    Sokoban.recognitionResult = result;
                    Board board = new Board();
                    board.processVoiceCommand(result);


                }
            }).start();
        });
    }

    public static String recognitionResult;     //在别的地方调用识别结果
}
