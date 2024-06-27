package com.zetcode;

import com.iflytek.cloud.speech.*;
import com.zetcode.util.DebugLog;
import com.zetcode.util.JsonParser;
import com.zetcode.util.Version;

public class SpeechRecognizerService {
    private SpeechRecognizer speechRecognize;
    private StringBuilder resultText = new StringBuilder();

    public SpeechRecognizerService() {
        // 初始化听写对象
        speechRecognize = SpeechRecognizer.createRecognizer();
    }

    public void startListening() {
        if (!speechRecognize.isListening()) {
//            System.out.println("*************你说的是：");
            speechRecognize.startListening(recognizerListener);
        } else {
            speechRecognize.stopListening();
        }
    }

    public void stopListening() {
        speechRecognize.stopListening();
    }

    public String getResultText() {
        return resultText.toString();
    }

    /**
     * 听写监听器
     */
    private RecognizerListener recognizerListener = new RecognizerListener() {
        public void onBeginOfSpeech() {
            DebugLog.Log("onBeginOfSpeech enter");
        }

        public void onEndOfSpeech() {
            DebugLog.Log("onEndOfSpeech enter");
        }

        /**
         * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加，显示到Area里
         */
        public void onResult(RecognizerResult results, boolean islast) {
            DebugLog.Log("onResult enter");
            // 如果要解析json结果，请考本项目示例的 com.iflytek.util.JsonParser类
            String text = JsonParser.parseIatResult(results.getResultString());
            resultText.append(text);
            if (islast) {
//                System.out.println(resultText.toString());
            }
        }

        public void onVolumeChanged(int volume) {
            DebugLog.Log("onVolumeChanged enter");
        }

        public void onError(SpeechError error) {
            DebugLog.Log("onError enter");
            if (null != error) {
                DebugLog.Log("onError Code：" + error.getErrorCode());
                System.err.println("Error: " + error.getErrorDescription(true));
            }
        }

        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            DebugLog.Log("onEvent enter");
        }
    };

    public static void initialize() {
        // 初始化
        StringBuffer param = new StringBuffer();
        param.append("appid=" + Version.getAppid());
        SpeechUtility.createUtility(param.toString());
    }
}
