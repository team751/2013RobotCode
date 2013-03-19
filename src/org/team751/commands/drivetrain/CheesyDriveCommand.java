package org.team751.commands.drivetrain;

import Jama.Matrix;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Timer;
import org.team751.cheesy.StateSpaceController;
import org.team751.cheesy.util.AccelFilterBase;
import org.team751.cheesy.util.ContinuousAccelFilter;
import org.team751.commands.CommandBase;

/*
 * Created by Team 254
 * 
 * See ../../cheesy/COPYING.txt for license information
 */
public class CheesyDriveCommand extends CommandBase {
    
    //Timer for velocity calcs
  Timer brakeTimer_;

  // Distance goal, more goals can be added (angle, velocity, etc.)
  double distanceGoal_;
  double angleGoal_;

  double maxSpeed_;
  double maxAcceleration_;
  double maxAlpha_;
  double maxOmega_;
  double turnOffset_;
  double sumStoppedError_;

  // State space stuff
  double curA_;
  double curV_;
  double curX_;
  double curJeez_; // Jesusfish
  double curWubl_; // Wubbleu
  double curThet_; // Theta
  Matrix y_;
  Matrix r_;
  StateSpaceController ssc_;
  AccelFilterBase straightFilter_;
  AccelFilterBase turnFilter_;
  
  double startingAngle_;
  
  /**
   * Turning PID integral coefficient
   */
  private static final double kITurn = 0.15;

public CheesyDriveCommand(double distance, double angle, double timeout, double maxSpeed, double maxAcceleration, double maxAlpha, double maxOmega) {
  setTimeout(timeout);
  angleGoal_ = angle;
  distanceGoal_ = distance;
  maxSpeed_ = maxSpeed;
  maxAcceleration_ = maxAcceleration;
  maxOmega_ = maxOmega;
  maxAlpha_ = maxAlpha;

  
  brakeTimer_ = new Timer();
  ssc_ = new StateSpaceController(2, 2, 4, StateSpaceController.Controllers.drive);
  y_ = new Matrix(2, 1);
  r_ = new Matrix(4, 1);
  straightFilter_ = new ContinuousAccelFilter();
  turnFilter_ = new ContinuousAccelFilter();
  
  startingAngle_ = 0;

}

public void initialize() {
  navigator.resetEncoderDistance();
  brakeTimer_.reset();
  curA_ = 0.0;
  curV_ = 0.0;
  curX_ = 0.0;
  curJeez_ = 0.0;
  curWubl_ = 0.0;
  curThet_ = 0.0;
  turnOffset_ = 0.0;
  sumStoppedError_ = 0.0;
  //Reset y_ to zeroes
  y_ = new Matrix(2, 1);
  r_ = new Matrix(4, 1);
  ssc_.reset();
  straightFilter_ = new ContinuousAccelFilter();
  turnFilter_ = new ContinuousAccelFilter();
}

protected void execute() {

  double currLeftDist = navigator.getLeftEncoderDistance();
  double currRightDist = navigator.getRightEncoderDistance();

  // Convert from inches to meters and degrees to radians
  double distGoal = distanceGoal_ * 0.0254;
  double maxAcc = maxAcceleration_ * 0.0254;
  double maxVel = maxSpeed_ * 0.0254;
  double angGoal = angleGoal_ * 0.0174532925;
  double maxAlph = maxAlpha_ * 0.0174532925;
  double maxOmeg = maxOmega_ * 0.0174532925;


  straightFilter_.CalcSystem(distGoal - curX_, curV_, 0.0, maxAcc, maxVel, 0.02);
  curA_ = straightFilter_.GetCurrAcc();
  curV_ = straightFilter_.GetCurrVel();
  curX_ = straightFilter_.GetCurrPos();

  turnFilter_.CalcSystem(angGoal - curThet_, curWubl_, 0.0, maxAlph, maxOmeg, 0.02);
  curJeez_ = turnFilter_.GetCurrAcc();
  curWubl_ = turnFilter_.GetCurrVel();
  curThet_ = turnFilter_.GetCurrPos();

  final double robotWidth = .827096;
  double wubbleuFactor = curWubl_ * robotWidth / 2;
  double thetaFactor = curThet_ * robotWidth / 2;
  double theta_measured = (currRightDist - currLeftDist) / robotWidth;
  double theta_gyro = -(navigator.getHeading() - startingAngle_) * 0.0174532925 ;
  double kI = 0.5;
  double KpTurn = 0.3;
  double gyroError = (angGoal + turnOffset_ - theta_measured);
  if (Math.abs(angGoal - curThet_) < 0.0001 && Math.abs(gyroError) < 18.0 * 0.0174532925) {
    
    sumStoppedError_ += gyroError * kITurn;
  } else {
    if (!(Math.abs(angGoal - curThet_) < 0.0001)) {
        sumStoppedError_ *= 0.97;
    }
  }

  double doffset = ((theta_measured - turnOffset_) - theta_gyro) * kI;
  if (doffset > 0.05) {
    doffset = 0.05;
  } else if (doffset < -0.05) {
    doffset = -0.05;
  }
  turnOffset_ += doffset;
  double turnCompensationOffset = (turnOffset_ + sumStoppedError_ + gyroError * KpTurn) * robotWidth;
  r_.set(0, 0, curX_ - turnCompensationOffset / 2.0 - thetaFactor);
  r_.set(0, 1, curV_ - wubbleuFactor);
  r_.set(0, 2, curX_ + turnCompensationOffset / 2.0 + thetaFactor);
  r_.set(0, 3, curV_ + wubbleuFactor);
  
  y_.set(0, 0, currLeftDist);
  y_.set(0, 1, currRightDist);
  ssc_.update(r_, y_);

  driveTrain.setLeftRightMotorOutputs(ssc_.getU().get(0, 0) / 12.0, ssc_.getU().get(1, 0) / 12.0);
  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line1, "curX:%f", curX_);
  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line2, "stoppedError:%f", sumStoppedError_);
  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line3, "thetaFactor:%f", thetaFactor);
  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line4, "turnOffset:%f", turnOffset_);
  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line5, "compensation:", turnCompensationOffset);
  DriverStationLCD::GetInstance()->PrintfLine(DriverStationLCD::kUser_Line6, "%f", 0);
  DriverStationLCD::GetInstance()->UpdateLCD();
  bool angleDone = fabs(drive_->GetGyroAngle() * 0.01714532925 - angGoal) < 0.5;
  boolean leftDone = Math.abs((distGoal - angGoal * robotWidth / 2.0) - currLeftDist) < 0.03;
  boolean rightDone = Math.abs((distGoal + angGoal * robotWidth / 2.0) - currRightDist) < 0.03;

}

public void SetGoals(double distance, double angle) {
  distanceGoal_ = distance;
  angleGoal_ = angle;
}

protected boolean isFinished() {
    if(distanceGoal_ == 0 && angleGoal_ == 0) {
        return true;
}

if(isTimedOut()) {
return true;
}



}

}
