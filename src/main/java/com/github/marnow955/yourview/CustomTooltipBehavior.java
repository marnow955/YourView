package com.github.marnow955.yourview;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CustomTooltipBehavior {

    /**
     * Hack allowing to modify the default behavior of the tooltips.
     *
     * @param openDelay       The open delay, knowing that by default it is set to 1000.
     * @param visibleDuration The visible duration, knowing that by default it is set to 5000.
     * @param closeDelay      The close delay, knowing that by default it is set to 200.
     * @param hideOnExit      Indicates whether the tooltip should be hide on exit,
     *                        knowing that by default it is set to false.
     */
    public static void updateTooltipBehavior(double openDelay, double visibleDuration,
                                             double closeDelay, boolean hideOnExit) {
        try {
            // Get the non public field "BEHAVIOR"
            Field fieldBehavior = Tooltip.class.getDeclaredField("BEHAVIOR");
            // Make the field accessible to be able to get and set its value
            fieldBehavior.setAccessible(true);
            // Get the value of the static field
            Object objBehavior = fieldBehavior.get(null);
            // Get the constructor of the private static inner class TooltipBehavior
            Constructor<?> constructor = objBehavior.getClass().getDeclaredConstructor(
                    Duration.class, Duration.class, Duration.class, boolean.class
            );
            // Make the constructor accessible to be able to invoke it
            constructor.setAccessible(true);
            // Create a new instance of the private static inner class TooltipBehavior
            Object tooltipBehavior = constructor.newInstance(
                    new Duration(openDelay), new Duration(visibleDuration),
                    new Duration(closeDelay), hideOnExit
            );
            // Set the new instance of TooltipBehavior
            fieldBehavior.set(null, tooltipBehavior);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
