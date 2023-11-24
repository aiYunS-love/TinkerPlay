package com.baiyx.tinkerplay.Algorithm;

import java.util.Arrays;

/**
 * @Author: baiyx
 * @Date: 2023年11月24日, 0024 上午 10:46:43
 * @Description: 判断字符串的相似程度算法
 */

public class StringSimilarity {
    public static void main(String[] args) {
        String str1 = "百鱼新";
        String str2 = "白宇鑫";

        int similarity = calculateSimilarity(str1, str2);
        System.out.println("相似程度: " + similarity + "%");
    }

    public static int calculateSimilarity(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(str1.charAt(i - 1), str2.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        int maxLen = Math.max(str1.length(), str2.length());
        int similarity = (int) (((double) (maxLen - dp[str1.length()][str2.length()]) / maxLen) * 100);
        return similarity;
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
