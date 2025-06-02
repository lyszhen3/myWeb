package leetcode.algorithm;

import org.junit.jupiter.api.Test;

public class 最大矩形 {

     @Test
     public int maximalRectangle(char[][] matrix) {

          int n = matrix.length;
          int m = matrix[0].length;

          if (n == 0 && m == 0) {
               return 0;
          }

          int[][] matrixTemp = new int[n][m];

          for (int i = 0; i < n; i++) {
               for (int j = 0; j < m; j++) {

                    int x = 0;
                    if (matrix[i][j] == '1') {
                         x = 1;
                    }
                    if (i == 0) {
                         matrixTemp[i][j] = x;
                         continue;
                    }

                    if (x == 0) {
                         matrixTemp[i][j] = 0;
                    } else {
                         matrixTemp[i][j] = matrixTemp[i - 1][j] + x;
                    }

               }
          }

          int area = 0;
          for (int i = 0; i < n; i++) {

               for (int j = 0; j < m; j++) {
                    int len = 0;
                    for (int j2 = j; j2 >= 0; j2--) {
                         if (matrixTemp[i][j] <= matrixTemp[i][j2]) {
                              len++;
                         } else {
                              break;
                         }
                    }
                    for (int j3 = j + 1; j3 < m; j3++) {
                         if (matrixTemp[i][j] <= matrixTemp[i][j3]) {
                              len++;
                         } else {
                              break;
                         }
                    }
                    area = Math.max(area, len * matrixTemp[i][j]);
               }
          }

          return area;

     }

     public static void main(String[] args) {

          char[][] matrix = { { '1', '0', '1', '0', '0' }, { '1', '0', '1', '1', '1' }, { '1', '1', '1', '1', '1' },
                    { '1', '0', '0', '1', '0' } };

          最大矩形 solution = new 最大矩形();
          int maximalRectangle = solution.maximalRectangle(matrix);
          System.out.println(maximalRectangle);
     }
}
