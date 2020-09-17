package MallSimulation;

import guessPicutre.B;

import java.util.ArrayList;

/**
 * Created by suren on 10/21/19.
 */
public class Mall {
    ArrayList<Boutique> mall;
    int time = 0;
    int currentFloors ;
    final int MAX_FLOORS = 10;
    final double[] floorsPrices = {100,250, 10000, 200000, 5000000,100000000 };

    double cash = 150;
    double mallARPS = 0;


    public double  getARPS() {
        mallARPS = 0;
        for (int i = 0; i < mall.size(); i++) {
            mallARPS += mall.get(i).getarps();
        }
        return mallARPS;
    }
    public Mall() {
        currentFloors = -1;
        mall = new ArrayList<>();
    }

    public double addFloor(Boutique[] boutiques) {
        System.out.println("Adding Floor " + (currentFloors + 1));
        if(cash >= floorsPrices[currentFloors + 1]) {
            cash-= floorsPrices[currentFloors + 1];
            mall.add(boutiques[currentFloors +1]);
            currentFloors++;
            return floorsPrices[currentFloors + 1];
        }else {
            return 0;
        }
    }

    public void pay(double price) {
        cash -= price;
    }

    public void speedUpTime(int seconds) {
        mallARPS = 0;
        for (int i = 0; i < mall.size(); i++) {
            mallARPS += mall.get(i).getarps();
        }
        cash = cash + seconds * mallARPS;
        time += seconds;
        System.out.println("Time " + time + " : " + "Cash : " + cash );

    }

    public void makeUpgrade(Boutique[] boutiques) {
        for (int i = 0; i < mall.size(); i++) {
            mallARPS += mall.get(i).getarps();
        }
        //Try New Floor
        double floorCost = floorsPrices[currentFloors + 1];
        double floorARPS = boutiques[currentFloors + 1].getarps();

        double min = floorCost / mallARPS + floorCost / (mallARPS + floorARPS);
        double minCost = floorCost;
        //New floor indicator;
        int minIndex = -1;

        for (int i = 0; i < mall.size(); i++) {
            if (mall.get(i).level == 299) continue;

            double floorUpgradeCost = mall.get(i).getUpgradePrice();
            double floorDelta = mall.get(i).getUpgradeRevenueDelta();
            double value = floorUpgradeCost / mallARPS + floorUpgradeCost / (mallARPS + floorDelta);
            if (value < min) {
                minCost = floorUpgradeCost;
                min = value;
                minIndex = i;
            }
        }
        System.out.println("Best Upgrade " + minIndex  + " after " + ((minCost - cash) / mallARPS)  + " seconds");
        int shiftTime = (int)((minCost - cash) / mallARPS) + 1;

        if (shiftTime > 0) {
            speedUpTime(shiftTime);
        }

        if (minIndex == -1) {
            addFloor(boutiques);
        }else {
            double price = mall.get(minIndex).upgrade();
            pay(price);
        }


    }

    static <T> void f(T[] a) {
        a = (T[]) new Double[6];
    }
    public static void main(String[] args) {
//        Integer[] b = (Integer[]) new Object[10];
        Integer [] a = new  Integer[10];
        f(a);
        int[] bigUpgradeTable1 = {10, 25, 75, 100, 150,  250, 300};
        int[] bigUpgradeTable2 = {5, 10, 25, 50, 75, 100, 150, 200, 250,300};
        int[] newVisitorTable1 = {2, 50, 200};
        int[] newVisitorTable2 = {10, 100, 250};
        Boutique boutique1 = new Boutique(1.09, 50, 5, 1.1, 40, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique2 = new Boutique(1.10, 200, 6, 1.3, 100, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique3 = new Boutique(1.11, 500, 10, 1.5, 300, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique4 = new Boutique(1.12, 1200, 15, 1.7, 800, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique5 = new Boutique(1.12, 3000, 18, 2, 2000, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique6 = new Boutique(1.07, 2000, 18, 1.45, 500, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique7 = new Boutique(1.07, 3000, 20, 1.5, 600, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique8 = new Boutique(1.07, 4000, 25, 1.6, 700, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique9 = new Boutique(1.08, 5000, 30, 1.7, 800, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique10 = new Boutique(1.08, 6000, 40, 1.8, 900, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique11 = new Boutique(1.08, 7000, 45, 1.85, 950, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique12 = new Boutique(1.08, 8000, 50, 1.9, 1000, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique13 = new Boutique(1.09, 9000, 55, 1.95, 1200, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique14 = new Boutique(1.09, 10000, 60, 2.0, 1400, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique15 = new Boutique(1.09, 11000, 65, 2.1, 1600, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique16 = new Boutique(1.09, 12000, 70, 2.15, 1800, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique17 = new Boutique(1.09, 13000, 75, 2.2, 2000, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique18 = new Boutique(1.09, 14000, 80, 2.25, 2200, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique19 = new Boutique(1.09, 15000, 85, 2.3, 2400, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique20 = new Boutique(1.1, 16000, 90, 2.35, 2600, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique21 = new Boutique(1.1, 17000, 100, 2.4, 2800, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique22 = new Boutique(1.1, 18000, 110, 2.45, 3000, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique23 = new Boutique(1.1, 19000, 120, 2.5, 3200, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique24 = new Boutique(1.1, 20000, 130, 2.55, 3400, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique25 = new Boutique(1.11, 21000, 140, 2.6, 3600, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique26 = new Boutique(1.11, 22000, 150, 2.65, 3800, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique27 = new Boutique(1.11, 23000, 160, 2.7, 4000, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique28 = new Boutique(1.12, 24000, 170, 2.75, 4200, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique29 = new Boutique(1.12, 25000, 180, 2.8, 4400, bigUpgradeTable2, newVisitorTable2);
        Boutique boutique30 = new Boutique(1.13, 26000, 190, 2.9, 4600, bigUpgradeTable2, newVisitorTable2);



        Boutique[] boutiques = new Boutique[6];
        boutiques[0] = boutique1;
        boutiques[1] = boutique2;
        boutiques[2] = boutique3;
        boutiques[3] = boutique4;
        boutiques[4] = boutique5;
        boutiques[5] = boutique6;
//        boutiques[6] = boutique7;
//        boutiques[7] = boutique8;
//        boutiques[8] = boutique9;
//        boutiques[9] = boutique10;
//        boutiques[10] = boutique11;
//        boutiques[11] = boutique12;
//        boutiques[12] = boutique13;
//        boutiques[13] = boutique14;
//        boutiques[14] = boutique15;
//        boutiques[15] = boutique16;
//        boutiques[16] = boutique17;
//        boutiques[17] = boutique18;
//        boutiques[18] = boutique19;
//        boutiques[19] = boutique20;
//        boutiques[20] = boutique21;
//        boutiques[21] = boutique22;
//        boutiques[22] = boutique23;
//        boutiques[23] = boutique24;
//        boutiques[24] = boutique25;
//        boutiques[25] = boutique26;
//        boutiques[26] = boutique27;
//        boutiques[27] = boutique28;
//        boutiques[28] = boutique29;
//        boutiques[29] = boutique30;


        Mall mall = new Mall();
       // mall.addFloor(boutiques);
        System.out.println(boutique1.getRPV());
        System.out.println(boutique1.getarps());
        System.out.println(boutique1.getUpgradePrice());
        System.out.println(boutique1.getUpgradeRevenueDelta());
       // System.out.println("Upgrade " +  boutique1.upgrade());


        System.out.println(boutique1.getRPV());
        System.out.println(boutique1.getarps());
        for (int i = 0; i < 300; i++) {
            System.out.println("-------------------------------------------------------");
            System.out.println("TIME = " + mall.time);
            System.out.println("Mall arps =  " + mall.getARPS());
            for (int j = 0 ; j <= mall.currentFloors; j++) {
                System.out.println("Boutique " + j + " Level: " +  mall.mall.get(j).level + " RPV: " +  mall.mall.get(j).getRPV() + " :  arps " + mall.mall.get(j).arps + " mult =" + mall.mall.get(j).multiplayer + " Upgrade cost: " +  mall.mall.get(j).nextPrice);

            }
            mall.makeUpgrade(boutiques);
        }
    }

}
