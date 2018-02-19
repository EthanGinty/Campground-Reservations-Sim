package com.techelevator;

public class DollarAmount {

    public static final DollarAmount ZERO_DOLLARS = new DollarAmount(0);
    
    private int totalAmountInCents;
    
    public DollarAmount(int totalAmountInCents) {
        this.totalAmountInCents = totalAmountInCents;
    }
    
    public int getCents() {
    	if((int)(totalAmountInCents % 100) >= 0) {
    		return (int)(totalAmountInCents % 100);
    	} else {
    		return (-1*(int)(totalAmountInCents % 100));
    	}
    }
    
    public int getDollars() {
        return totalAmountInCents / 100;
    }
    
    public boolean isGreaterThan(DollarAmount amountToCompare) {
        return this.totalAmountInCents > amountToCompare.totalAmountInCents;
    }
    
    public boolean isGreaterThanOrEqualTo(DollarAmount amountToCompare) {
        return this.totalAmountInCents >= amountToCompare.totalAmountInCents;
    }
    
    public boolean isLessThan(DollarAmount amountToCompare) {
        return this.totalAmountInCents < amountToCompare.totalAmountInCents;
    }
    
    public boolean isLessThanOrEqualTo(DollarAmount amountToCompare) {
        return this.totalAmountInCents <= amountToCompare.totalAmountInCents;
    }
    
    public boolean isNegative() {
        return totalAmountInCents < 0;
    }
    
    public DollarAmount minus(DollarAmount amountToSubtract) {
        return new DollarAmount(this.totalAmountInCents - amountToSubtract.totalAmountInCents);
    }
    
    public DollarAmount plus(DollarAmount amountToAdd) {
        return new DollarAmount(this.totalAmountInCents + amountToAdd.totalAmountInCents);
    }

    public int compareTo(DollarAmount amountToCompare) {
        if(this.isGreaterThan(amountToCompare)) {
            return 1;
        } else if(this.isLessThan(amountToCompare)) {
            return -1;
        } else {
            return 0;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof DollarAmount) {
            return this.totalAmountInCents == ((DollarAmount)obj).totalAmountInCents;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
    	return totalAmountInCents;
    }
    
    @Override
    public String toString() {
    	if (getCents() == 0 ) {
    		return "$" + getDollars() + "."+ "00";
    	} else if (getCents() < 10) {
    		return "$" + getDollars() + ".0" + getCents();
    	} else {
    	return "$" + getDollars() + "."+ getCents();
    	}
    }
}
