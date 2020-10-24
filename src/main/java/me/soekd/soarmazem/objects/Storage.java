package me.soekd.soarmazem.objects;

import me.soekd.soarmazem.Main;

public class Storage {

    private int cactuses;
    private int hoppers;
    private boolean trustedPermission;
    private boolean memberPermission;
    private boolean warnWhenFull;
    private int timesSell;

    public Storage(int cactuses, int hoppers, boolean trustedPermission, boolean memberPermission, boolean warnWhenFull, int timesSell) {
        this.cactuses = cactuses;
        this.hoppers = hoppers;
        this.trustedPermission = trustedPermission;
        this.memberPermission = memberPermission;
        this.warnWhenFull = warnWhenFull;
        this.timesSell = timesSell;
    }

    public int getStorageLimit() {
        return Main.getInstance().getConfig().getInt("StorageConfiguration.Hopper.DefaultSpaceAmount") + (hoppers * Main.getInstance().getConfig().getInt("StorageConfiguration.Hopper.SpacePerHopper"));
    }

    public boolean isMemberPermission() {
        return memberPermission;
    }

    public void setMemberPermission(boolean memberPermission) {
        this.memberPermission = memberPermission;
    }

    public int getHoppers() {
        return hoppers;
    }

    public void setHoppers(int hoppers) {
        this.hoppers = hoppers;
    }

    public boolean isTrustedPermission() {
        return trustedPermission;
    }

    public void setTrustedPermission(boolean trustedPermission) {
        this.trustedPermission = trustedPermission;
    }

    public boolean isWarnWhenFull() {
        return warnWhenFull;
    }

    public void setWarnWhenFull(boolean warnWhenFull) {
        this.warnWhenFull = warnWhenFull;
    }

    public int getTimesSell() {
        return timesSell;
    }

    public void setTimesSell(int timesSell) {
        this.timesSell = timesSell;
    }

    public void removeTimesSell() {
        this.timesSell--;
    }

    public int getCactuses() {
        return cactuses;
    }

    public void setCactuses(int cactuses) {
        this.cactuses = cactuses;
    }

    public void addHoppers() {
        hoppers++;
    }

    public void addCactuses() {
        cactuses++;
    }

    public void removeCactuses() {
        cactuses--;
    }

    public boolean isFull() {
        return getStorageLimit() <= cactuses;
    }

    public double getMoneyValue() {
        return cactuses * Main.getInstance().getConfig().getDouble("SellConfigurations.UnitPrice");
    }

    public boolean canSell() {
        return timesSell > 0;
    }


}
