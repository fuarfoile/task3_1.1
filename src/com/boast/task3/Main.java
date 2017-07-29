/*
 * Main.java 28/07/2017
 *
 * Created by Bondarenko Oleh
 */


package com.boast.task3;

import com.boast.data.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Payment payment = new Payment();

        Scanner in = new Scanner(System.in);
        String inputLine;

        for (int i = 0; i < Data.goods.length; i++) {
            System.out.println(i + " " + Data.goods[i] + " " + Data.prices[i]);
        }

        do {
            inputLine = in.nextLine();

            if (inputLine.length() > 0) {
                payment.addGoodToBin(Integer.parseInt(inputLine));
            }

        } while (inputLine.length() > 0);

        payment.calcSummary();

        payment.setToPending();

        do {
            inputLine = in.nextLine();
        } while (!inputLine.equals("pay"));

        payment.setToFinish();
    }
}

class Payment {
    enum STATE {
        NEW, PENDING_PAYMENT, FINISHED;
    }

    private static class Bin {
        static HashMap<Integer, Integer> goods = new HashMap<>();
        static float summary = 0;

        static void getSummary () {
            summary = 0;

            System.out.println("Good name       Price       Counts      Good summary");
            System.out.println("____________________________________________________");

            for (HashMap.Entry<Integer, Integer> entry : goods.entrySet()) {
                System.out.print(Data.goods[entry.getKey()]);

                for (int i = 16 - Data.goods[entry.getKey()].length(); i > 0; i--) {
                    System.out.print(" ");
                }

                System.out.print(Data.prices[entry.getKey()]);

                for (int i = 17 - (Data.prices[entry.getKey()]).toString().length(); i > 0; i--) {
                    System.out.print(" ");
                }

                System.out.print(entry.getValue());

                for (int i = 7 - entry.getValue().toString().length(); i > 0; i--) {
                    System.out.print(" ");
                }

                System.out.println(entry.getValue() * Data.prices[entry.getKey()]);

                summary += entry.getValue() * Data.prices[entry.getKey()];
            }

            System.out.println("____________________________________________________");
            System.out.print("Summary: ");

            for (int i = 31; i > 0; i--) {
                System.out.print(" ");
            }

            System.out.println(summary);
        }
    }

    private STATE state = STATE.NEW;

    void addGoodToBin(int goodId) {
        if (Data.goods.length < goodId) {
            System.out.println("Good not consist");
            return;
        }

        if (Bin.goods.containsKey(goodId)) {
            Bin.goods.replace(goodId, Bin.goods.get(goodId) + 1);
        } else {
            Bin.goods.put(goodId, 1);
        }
    }

    void calcSummary() {
        Bin.getSummary();
    }

    void setToPending() {
        state = STATE.PENDING_PAYMENT;
        System.out.println("Pending payment (Type \"pay\")");
    }

    void setToFinish() {
        state = STATE.FINISHED;
        System.out.println("Payment success. Waiting for call from courier");
    }
}