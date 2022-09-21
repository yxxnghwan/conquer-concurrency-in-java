package com.techcoursetalk.concurrency.deadlock;

public class DeadLockEx {
    public static Cloth tShirt = new TShirt();
    public static Cloth pants = new Pants();

    public void wearTShirtThenWearPants() {
        synchronized (tShirt) {
            wearCloth(tShirt);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
            synchronized (pants) {
                wearCloth(pants);
            }
        }
    }
    public void wearPantsThenWearTShirt() {
        synchronized (pants) {
            wearCloth(pants);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
            synchronized (tShirt) {
                wearCloth(tShirt);
            }
        }
    }

    private void wearCloth(final Cloth cloth) {
        cloth.wear();
    }
}
