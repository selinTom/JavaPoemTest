package com.szy.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devov on 2021/2/7.
 */


public class Solution {

    public static void main(String[] args) {
        int time = numOfMinutes(15,0,new int[]{-1,0,0,1,1,2,2,3,3,4,4,5,5,6,6},new int[]{1,1,1,1,1,1,1,0,0,0,0,0,0,0,0});
       System.out.println(time);
    }

    public boolean checkPossibility(int[] nums) {
        //  3  4  2  3
        boolean result = true;
        int exc = 0;
        if(nums.length>1) {
            for (int i = 1; i < nums.length; i++) {
                if(exc>1){
                    break;
                }

                if(nums[i]<nums[i-1]){
                    exc++;
                    int temp = nums[i - 1];
                    int j = i-2;
                    while (j>0 && temp<nums[j]){
                        exc++;
                        j--;
                        if(exc>1){
                            break;
                        }
                    }
                }

            }
        }
        if(exc>1){
            result = false;
        }

        return result;
    }


    //  n = 8    k = 5

    // 1 2 3 4 5 6 7 8

    //1 6 2 5 3 4 7 8

    public int[] constructArray(int n, int k) {
        int[] result = new int[n];
        result[0] = 1;
        if(n>1) {
            int j = 1;
            boolean plus = true;
            for ( int temp = k; temp >= 1; temp--,j++) {
                result[j] = result[j-1]+(plus?temp:-temp);
                plus=!plus;
            }
            if(n-k>1){
                for (int i = k+1;i<n;i++){
                    result[i] = i+1;
                }
            }
        }
        return result;
    }

    //  333 = 3*(10^2+10^1+10^0)

    // 8^5+8^4
    public static String smallestGoodBase(String n) {
        long num = Long.parseLong(n);
        int k = 1;
        while (true){
            System.out.println("===========================");
            long remainder = -1;
            long calc = num;
            boolean findNext = false;
            k++;
            System.out.println("k:"+k);

            while (!findNext){
                long remainderTemp = calc%k;
                System.out.println("remainderTemp:"+remainderTemp);

                if(remainder == -1){
                    remainder = remainderTemp;
                }else{
                    if(remainder!=remainderTemp){
                        findNext = true;
                    }
                }
                long temp = calc/k;
                if(temp == 0){
                    long remainderTemp2 = calc%k;
                    System.out.println("remainderTemp2:"+remainderTemp2);
                    if(remainderTemp2 == remainder){
                        return String.valueOf(k);
                    }else{
                        findNext = true;
                    }
                }else{
                    calc = temp;
                }
            }
            System.out.println("===========================");
        }
    }
     public static  class ListNode {
         int val;
         ListNode next;
         public ListNode() {}
         public ListNode(int val) { this.val = val; }
         public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

         @Override
         public String toString() {
             String result = String.valueOf(val);
             if(next!=null){
                 result+="------->"+next.toString();
             }
             return result;
         }
     }
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode prev = new ListNode();
        prev.next = head;
        ListNode tail = head;
//        ListNode nextNode = setNode(tail,k);



        return null;


    }

//    public ListNode setNode(ListNode tail,int k){
//        ListNode next = new ListNode();
//        for(int i = k;k>1;k--){
//            tail = tail.next;
//            next = tail.next;
//        }
//        reverseList()
//        return next;
//    }

//          p  c
    //         n
//    输入: 1->2->3->4->5->NULL
//    输出: 5->4->3->2->1->NULL

    public static ListNode reverseList(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode prev = null;
        ListNode curr = head;
        while (curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;

    }

//    输入：n = 7, headID = 6, manager = [1,2,3,4,5,6,-1], informTime = [0,6,5,4,3,2,1]
//    输出：21
//    解释：总负责人 id = 6。他将在 1 分钟内通知 id = 5 的员工。
//    id = 5 的员工将在 2 分钟内通知 id = 4 的员工。
//    id = 4 的员工将在 3 分钟内通知 id = 3 的员工。
//    id = 3 的员工将在 4 分钟内通知 id = 2 的员工。
//    id = 2 的员工将在 5 分钟内通知 id = 1 的员工。
//    id = 1 的员工将在 6 分钟内通知 id = 0 的员工。
//    所需时间 = 1 + 2 + 3 + 4 + 5 + 6 = 21 。

    public static  class TreeNode {
        int val;
        List<TreeNode> next = new ArrayList<>();
        public TreeNode() {}
        public TreeNode(int val) { this.val = val; }

        @Override
        public String toString() {
            String result = String.valueOf(val);
            if(next!=null){
                result+="------->"+next.toString();
            }
            return result;
        }
    }

    public static int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        TreeNode main = new TreeNode(informTime[headID]);
        List<Integer>arrayList = new ArrayList<>();
        findLink(n,headID,manager,informTime,main);
        List<Integer>timeList = new ArrayList<>();
        getTime(main,informTime[headID],timeList);
        int min = 0;
        for (int item :timeList){
            min = Math.max(item,min);
        }
        return min;
    }

    public static void findLink(int n, int headID, int[] manager, int[] informTime,TreeNode node){
      for(int i = 0;i<n;i++){
          if(manager[i] == headID){
              TreeNode treeNode = new TreeNode(informTime[i]);
              node.next.add(treeNode);
              findLink(n,i,manager,informTime,treeNode);
          }
      }
    }

    public static void getTime(TreeNode node,int sum,List<Integer> timeList){
       if (node.next.size()!= 0){
          for ( TreeNode item :node.next){
              getTime(item, sum+item.val,timeList);
          }
       }else{
           timeList.add(sum);
       }
    }
}
