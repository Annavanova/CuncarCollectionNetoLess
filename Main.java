package MultyThread.CuncarCollection;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static final int COUNT_WORDS_COUNT = 10_000;
    public static final int LONG_WORDS = 100_000;
    public static final int MAX_COUNT_BLOCK_QUEUE = 100;

    public static final BlockingQueue<String> textsWordAInQueue = new ArrayBlockingQueue<>(MAX_COUNT_BLOCK_QUEUE);
    public static final BlockingQueue<String> textsWordBInQueue = new ArrayBlockingQueue<>(MAX_COUNT_BLOCK_QUEUE);
    public static final BlockingQueue<String> textsWordCInQueue = new ArrayBlockingQueue<>(MAX_COUNT_BLOCK_QUEUE);


    public static void main(String[] args) throws InterruptedException {

        String[] texts = new String[COUNT_WORDS_COUNT];

        Thread genereteText = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText("abc", LONG_WORDS);
                //System.out.println(texts[i]);
                try {
                    textsWordAInQueue.put(texts[i]);
                    textsWordBInQueue.put(texts[i]);
                    textsWordCInQueue.put(texts[i]);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        genereteText.start();

        Thread threadA = new Thread(() -> {
            maxCountWord(textsWordAInQueue, 'a');
        });
        threadA.start();
        threadA.join();
        Thread threadB = new Thread(() -> {
            maxCountWord(textsWordBInQueue, 'b');
        });
        threadB.start();
        threadB.join();
        Thread threadC = new Thread(() -> {
            maxCountWord(textsWordCInQueue, 'c');
        });
        threadC.start();
        threadC.join();
    }

    public static void maxCountWord(BlockingQueue<String> text, char word) {
        String tmpText = "";
        int maxCnt = 0;
        for (int i = 0; i < MAX_COUNT_BLOCK_QUEUE; i++) {
            try {
                String textTmp = text.take();
                int cnt = (int) textTmp.chars().filter(ch -> ch == word).count();
                if (maxCnt < cnt) {
                    maxCnt = cnt;
                    tmpText = textTmp;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("Строка с самым большим количеством символа %s : \n", word);
        System.out.println(tmpText + "-> " + maxCnt);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
