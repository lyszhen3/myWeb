package leetcode.algorithm;

public class 接雨水 {

     public int trap(int[] height) {

          if (height == null || height.length == 0) {
               return 0;
          }

          int n = height.length;
          int[] leftMax = new int[n];
          int[] rightMax = new int[n];
          for (int l = 0; l < n; l++) {
               int r = n -l -1;
               if (l == 0) {
                    leftMax[l] = height[l];
               } else {
                    leftMax[l] = Math.max(leftMax[l - 1], height[l]);
               }

               if (r == n - 1) {
                    rightMax[r] = height[r];
               } else {
                    rightMax[r] = Math.max(rightMax[r + 1], height[r]);
               }


          }

          int water = 0;
          for (int i = 0; i < n; i++) {
               if (i == 0) {
                    water +=0;
                    continue;
               }
               if (i == n - 1) {
                    water +=0;
                    continue;

               }
               int min = Math.min(leftMax[i -1], rightMax[i + 1]);
               if (min > height[i]) {
                   water += (min - height[i]);
               }   
          }

          return water;
     }

     public static void main(String[] args) {
          接雨水 rain = new 接雨水();
          int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};

          int result = rain.trap(height);
          System.out.println("接雨水的总量: " + result); 
     }
}
