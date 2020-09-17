package MallSimulation;

/**
 * Created by suren on 10/21/19.
 */
public class Boutique {
    final static int MAX_LEVEL = 300;
    int level = 1;
    double nextPrice;
    double startUpgradePrice;
    double rate;

    double rpv;
    double multiplayer;
    double fillCoef = 0.7;
    double A;
    double k;
    double constant;
    double arps;
    int[] bigUpgradeStates;
    int[] newVisitorStates;
    int newVisitorIndex = 0;
    int bigUpgradeIndex = 0;

    Boutique(double rate, double startUpgradePrice, double A, double k, double constant, int[] bigUpgradeStates, int[] newVisitorStates) {
        this.rate = rate;
        this.startUpgradePrice = startUpgradePrice;
        this.A = A;
        this.k = k;
        this.constant = constant;
        this.bigUpgradeStates = bigUpgradeStates;
        this.newVisitorStates = newVisitorStates;
        multiplayer = 1;
        nextPrice = startUpgradePrice;
        rpv = (A * Math.pow(level, k)  + constant) * multiplayer;
        arps = rpv * fillCoef / 20;
    }


    double upgrade() {

        if (level < MAX_LEVEL) {
            System.out.println("Upgrading Boutique " + (level + 1));
            level++;
            double price = nextPrice;
            nextPrice = nextPrice * rate;


            if (newVisitorIndex < newVisitorStates.length && newVisitorStates[newVisitorIndex] == level) {
                multiplayer = multiplayer * 2;
                System.out.println("new visitor mult");
                newVisitorIndex++;
            }else
            if (bigUpgradeIndex < bigUpgradeStates.length && bigUpgradeStates[bigUpgradeIndex] == level) {
                multiplayer = multiplayer * 2;
                System.out.println("big upgrade  mult");
                bigUpgradeIndex++;
            }

            rpv = (A * Math.pow(level, k)  + constant) * multiplayer;
            arps = rpv * fillCoef / 20;

            return price;

        }else {
            return 0;

        }
    }

    double getUpgradePrice() {
        return nextPrice;
    }

    double getRPV() {
        return rpv;
    }


    double getarps() {
        return arps;
    }
    double getUpgradeRevenueDelta() {
        double localMult = multiplayer;
        if (newVisitorIndex<newVisitorStates.length &&  newVisitorStates[newVisitorIndex] == level + 1) {
            localMult = localMult * 2;
        }else if (bigUpgradeIndex <bigUpgradeStates.length && bigUpgradeStates[bigUpgradeIndex] == level + 1) {
            localMult = localMult * 2;
        }
        double nextRpv = (A * Math.pow(level + 1, k)  + constant) * localMult;

        double nextArps = nextRpv * fillCoef / 20;
        return  nextArps - arps;
    }

    double getRate() {
        return rate;
    }


}

