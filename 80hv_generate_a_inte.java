import java.util.Timer;
import java.util.TimerTask;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quaternion4f;
import javax.vecmath.Vector3f;

import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;

public class GenerateAInte {
    private Session arSession;
    private Timer timer;
    private float notificationThreshold = 0.5f; // adjust this value to change notification sensitivity

    public GenerateAInte(Session session) {
        arSession = session;
        timer = new Timer();
    }

    public void startNotifier() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkForNotifications();
            }
        }, 0, 500); // check for notifications every 500ms
    }

    private void checkForNotifications() {
        Frame frame = arSession.update();
        for (HitResult hit : frame.hitTestResults) {
            Trackable trackable = hit.getTrackable();
            if (trackable instanceof Plane) {
                Plane plane = (Plane) trackable;
                Matrix4f planeMatrix = plane.getCenterPose();
                Quaternion4f planeRotation = new Quaternion4f();
                planeMatrix.getRotation(planeRotation);
                float planeAngle = planeRotation.angle();

                if (planeAngle > notificationThreshold) {
                    // trigger notification
                    notifyUser("AR/VR module detected!");
                }
            }
        }
    }

    private void notifyUser(String message) {
        System.out.println("Notification: " + message);
        // TO DO: implement actual notification logic here
    }
}