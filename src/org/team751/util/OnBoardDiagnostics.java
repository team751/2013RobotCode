package org.team751.util;

import InsightLT.DecimalData;
import InsightLT.InsightLT;
import edu.wpi.first.wpilibj.DriverStation;
import org.team751.commands.CommandBase;
import org.team751.tasks.PeriodicTask;

/**
 * Accesses robot diagnostic information and displays it on the Insight LT.
 * @author Sam Crow
 */
public class OnBoardDiagnostics extends PeriodicTask {

    private InsightLT insight;
    
    private DecimalData batteryVoltage;
	
	private DecimalData heading = new DecimalData("Hdg:");
	private DecimalData headingChange = new DecimalData("HDrift:");
	
	private Differentiator headingDiff = new Differentiator();
    
    /**
     * If the display has been started with {@link InsightLT#startDisplay() }.
     */
    private boolean started = false;
    
    public OnBoardDiagnostics() {
        insight = new InsightLT(InsightLT.FOUR_ZONES);
        
        batteryVoltage = new DecimalData("Vbat:");
        insight.registerData(batteryVoltage, 1);
		insight.registerData(heading, 3);
		insight.registerData(headingChange, 4);
    }
    
    protected void run() {
        //Start the display, if it is nto yet started
        if(!started) {
            insight.startDisplay();
            started = true;
        }
        
        batteryVoltage.setData(DriverStation.getInstance().getBatteryVoltage());
        synchronized(CommandBase.navigator) {
			double headingNow = CommandBase.navigator.getHeading();
			heading.setData(headingNow);
			headingChange.setData(headingDiff.getDerivative(headingNow));
		}
    }
    
}
