package com.example.a1621638.android_assignment_1.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.a1621638.android_assignment_1.R;

/**
 * Enumeration of note categories, represented as colors.
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public enum Category {

    RED(1), ORANGE(2), YELLOW(3), GREEN(4), LIGHT_BLUE(5), DARK_BLUE(6), PURPLE(7), WHITE(8);

    private int colorId;

    // create a category with a specific color ID.
    Category(int colorId) {
        this.colorId = colorId;
    }

    /**
     * Get the category's color ID.
     * @return
     */
    public int getColorId() {
        return colorId;
    }

    public int getResourceId() {
        switch(colorId) {
            case 1:
                return R.color.redCircleColor;
            case 2:
                return R.color.orangeCircleColor;
            case 3:
                return R.color.yellowCircleColor;
            case 4:
                return R.color.greenCircleColor;
            case 5:
                return R.color.lightBlueCircleColor;
            case 6:
                return R.color.darkBlueCircleColor;
            case 7:
                return R.color.purpleCircleColor;
            case 8:
            default:
                return R.color.whiteCircleColor;
        }
    }

    public static Category fromColorId(int colorId) {
        switch(colorId) {
            case 1:
                return RED;
            case 2:
                return ORANGE;
            case 3:
                return YELLOW;
            case 4:
                return GREEN;
            case 5:
                return LIGHT_BLUE;
            case 6:
                return DARK_BLUE;
            case 7:
                return PURPLE;
            case 8:
            default:
                return WHITE;
        }
    }
}
