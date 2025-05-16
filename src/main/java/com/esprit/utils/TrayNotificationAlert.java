package com.esprit.utils;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Utility class for showing notifications using ControlsFX
 */
public class TrayNotificationAlert {

    // Define notification types to replace tray.notification.NotificationType
    public enum NotificationType {
        SUCCESS, 
        INFORMATION, 
        WARNING, 
        ERROR
    }
    
    // Define animation types to replace tray.animations.AnimationType
    public enum AnimationType {
        FADE,
        SLIDE
    }

    /**
     * Shows a notification with the specified parameters
     * 
     * @param title Title of the notification
     * @param msg Message content
     * @param notifType Type of notification (SUCCESS, INFORMATION, WARNING, ERROR)
     * @param notifAnimation Animation type (FADE, SLIDE, POPUP)
     * @param notifDuration Duration for the notification to be displayed
     */
    public static void notif(String title, String msg, NotificationType notifType, AnimationType notifAnimation,
            Duration notifDuration) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(msg)
                .hideAfter(notifDuration);
        
        // Set position (top-right is standard)
        notification.position(Pos.TOP_RIGHT);
        
        // Show with appropriate notification type
        switch (notifType) {
            case SUCCESS:
                notification.showInformation();
                break;
            case INFORMATION:
                notification.showInformation();
                break;
            case WARNING:
                notification.showWarning();
                break;
            case ERROR:
                notification.showError();
                break;
            default:
                notification.showInformation();
                break;
        }
    }
}
