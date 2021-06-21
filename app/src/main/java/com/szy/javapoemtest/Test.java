package com.szy.javapoemtest;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devov on 2021/3/17.
 */

public  class Test {

    public static void main(String[] args) {
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        ListNode node6= new ListNode(6);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        node5.next = node6;
//        node6.next = node3;
//        Log.i("RRORE",""+Solution.detectCycle(node1));
        TreeNode treeNode0 = new TreeNode(0);
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode7 = new TreeNode(7);
        TreeNode treeNode8 = new TreeNode(8);
        treeNode3.left= treeNode5;
        treeNode3.right = treeNode1;
        treeNode5.left = treeNode6;
        treeNode5.right = treeNode2;
        treeNode2.left = treeNode7;
        treeNode2.right = treeNode4;
        treeNode1.left = treeNode0;
        treeNode1.right = treeNode8;
        Log.i("RRORE","result:"+Solution.lowestCommonAncestor(treeNode3,treeNode5,treeNode1));


    }

      static class ListNode {
         int val;
         ListNode next;
         ListNode(int x) {
             val = x;
             next = null;
         }

         @Override
         public String toString() {
             return "ListNode{" +
                     "val=" + val +
                     ", next=" + next +
                     '}';
         }
     }
//p = y+1
    public static class Solution {
        public static ListNode detectCycle(ListNode head) {
            if (head == null ||head.next == null || head.next.next == null){
                return null;
            }
            ListNode fastNode = head.next.next;
            ListNode slowNode = head;
            while (slowNode != fastNode){
                slowNode = slowNode.next;
                if(fastNode.next == null || fastNode.next.next == null){
                    return null;
                }
                fastNode = fastNode.next.next;
            }
            ListNode meetNode = slowNode;
           ListNode result = head;
           while (result !=meetNode){
               ListNode currNode = meetNode.next;
               while (currNode!=meetNode){
                   Log.i("RRORE","                  ");
                   Log.i("RRORE","currNode:"+currNode.val);
                   Log.i("RRORE","result:"+result.val);
                   Log.i("RRORE","                  ");
                   if(result == currNode){
                       return result;
                   }else{
                       currNode = currNode.next;
                   }
               }
               result = result.next;
           }
           return result;
        }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return test(root, p, q,true,true);
    }
    /*
                        3
                       / \
                      5   1
                     / \ / \
                    6  2 0  8
                      / \
                     7   4
     */

    public static TreeNode test(TreeNode node, TreeNode p, TreeNode q,boolean findP,boolean findQ){
        TreeNode result = null;
        Log.i("RRORE","                                    ");

        Log.i("RRORE","node:"+node.val);
        if(findP){
            findP = findTarget(node, p);
        }
        if(findQ){
            findQ = findTarget(node,q);
        }
        Log.i("RRORE","findP:"+findP+"     findQ:"+findQ);

        if(findP && findQ){
            result = node;
            TreeNode left = test(node.left,p,q,true,true);
            if(left!=null){
                Log.i("RRORE","return "+left.val);
                Log.i("RRORE","                                    ");
                return left;
            }
            TreeNode right = test(node.right,p,q,true,true);
            if(right!=null){
                Log.i("RRORE","return "+right.val);
                Log.i("RRORE","                                    ");
                return right;
            }
            Log.i("RRORE","return "+result.val);
            Log.i("RRORE","                                    ");
            return result;
        }
        Log.i("RRORE","                                    ");
        return null;
    }

    public static boolean findTarget(TreeNode node, TreeNode p){
        if(node == p){
            return true;
        }
        if(node == null){
            return false;
        }
        boolean left = false;
        if(node.left!=null){
            left = findTarget(node.left,p);
        }
        boolean right = false;
        if(node.right!=null){
            right = findTarget(node.right,p);
        }
        return  left || right;
    }
    }
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    /*
        [1,1,1,1]
        [1,1,1,1]
        [1,1,1,1]
        1*1 ..... 1*m
        2*1 ..... 2*m
        .
        .
        .
        n*1 ..... n*m


        n  m
        (n+1)*n/2
        (m+1)*m/2


     */


    public int findSpecialInteger(int[] arr) {

        float length = arr.length*0.25f;
        int count = 0;
        int lastNum = -1;
        for(int a :arr){
            if(lastNum == a){
                count++;
                Log.i("RRORE","a:"+a+"   count:"+count+"   length:"+length);
                if(count>length){
                    return a;
                }
            }else{
                count = 1;
                lastNum = a;
            }
        }
        return arr[0];

    }

    public int getDecimalValue(ListNode head) {
        int sum = 0;
        do{
            sum = sum*2+head.val;
            head = head.next;
        }while (head!=null);
        return sum;

    }

    public List<Integer> sequentialDigits(int low, int high) {

        List<Integer> result = new ArrayList<>();
        int lowSize = 1;
        int highSize = 1;
        int lowMark = 10;
        int highMark = 10;
        while (low>=lowMark){
            lowSize++;
            lowMark = lowMark*10;
        }
        lowMark = lowMark/10;

        while (high>=highMark){
            highSize++;
            highMark = highMark*10;
        }
        highMark = highMark/10;

        for (int i = lowSize;i<=highSize;i++){
            if(i == lowSize){
                if (low / lowMark +i<=10){
                    int head = getNum(low / lowMark,i);
                    if(low<=head && head<=high){
                        result.add(head);
                    }
                    int offset = getAdd(i);
                    int temp = 1;

                    while (low / lowMark +i+temp<=10){
                        head = head+offset;
                        if(head>high){
                            break;
                        }
                        result.add(head);
                        temp++;
                    }
                }
            }else if(i == highSize){
                int start = getNum(1,i);
                int offset = getAdd(i);
                int temp = 0;
                while (start<=high && i+temp<10 ){
                    result.add(start);
                    start = start+offset;
                    temp++;
                }
            }else{
                int start = getNum(1,i);
                int offset = getAdd(i);
                int temp = 0;
                while (i+temp<=9){
                    result.add(start);
                    start = start+offset;
                    temp++;
                }
            }
        }
        return result;
    }

    private int getNum(int head ,int size){
        int a = head;
        int i = 1;
        while (i<size){
            a = a*10+head+i;
            i++;
        }
        return a;
    }


    private int getAdd(int size){
        int a = 1;
        while (size>1){
            a=a*10+1;
            size--;
        }
        return a;

    }
}
