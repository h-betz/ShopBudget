package com.hbetz.shopbudget;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Hunter on 11/24/2016.
 */

public class Utilities {

    /**
     * Round parameter value to the specified number of decimal places
     *
     * @param value double value being passed that is being rounded
     * @param places number of places to round the value to
     * @return return the rounded double value
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
