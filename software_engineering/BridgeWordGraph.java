package software_engineering;

import java.util.*;

public class BridgeWordGraph {
    private Map<String, List<String>> graph;

    public BridgeWordGraph(String text) {
        graph = new HashMap<>();
        buildGraph(text);
    }

    private void buildGraph(String text) {
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            graph.computeIfAbsent(words[i], k -> new ArrayList<>()).add(words[i + 1]);
        }
    }

    public List<String> getBridgeWords(String word1, String word2) {
        List<String> bridgeWords = new ArrayList<>();
        List<String> successors = graph.get(word1);
        if (successors != null) {
            for (String word : successors) {
                List<String> nextSuccessors = graph.get(word);
                if (nextSuccessors != null && nextSuccessors.contains(word2)) {
                    bridgeWords.add(word);
                }
            }
        }
        return bridgeWords;
    }

    public String generateNewText(String inputText) {
        String[] words = inputText.split("\\s+");
        StringBuilder newText = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            newText.append(words[i]).append(" ");
            List<String> bridgeWords = getBridgeWords(words[i], words[i + 1]);
            if (!bridgeWords.isEmpty()) {
                Random rand = new Random();
                String bridgeWord = bridgeWords.get(rand.nextInt(bridgeWords.size()));
                newText.append(bridgeWord).append(" ");
            }
        }
        newText.append(words[words.length - 1]);

        return newText.toString();
    }

    public static void main(String[] args) {
        String previousText = "I am learning natural language processing and it is very interesting";
        String inputText = "I am learning processing";

        // 构建图
        BridgeWordGraph graph = new BridgeWordGraph(previousText);

        // 生成新文本
        String newText = graph.generateNewText(inputText);
        System.out.println(newText);
    }
}
