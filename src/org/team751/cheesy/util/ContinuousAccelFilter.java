package org.team751.cheesy.util;

/**
 * A continuous time acceleration profile.
 *
 * An instance of continuous data is assumed to have a constant rate of change
 * over a period of time. Thus, over any given time increment, acceleration is
 * assumed to be constant. Thus data for this filter does not need to be polled
 * necessarily periodically.
 */
public class ContinuousAccelFilter extends AccelFilterBase {

    public ContinuousAccelFilter(double currPos, double currVel, double currAcc) {
        super(currPos, currVel, currAcc);
    }

    public void CalcSystem(double distance_to_target, double v, double goal_v, double max_a, double max_v, double dt) {

        double dt2 = 0, a = 0, const_time = 0, dtf = 0, af = 0;

        //Assemble an object to pass by reference
        MaxAccelTimeData data = new MaxAccelTimeData();
        data.t1 = dt2;
        data.a1 = a;
        data.ct = const_time;
        data.t2 = dtf;
        data.a2 = af;

        maxAccelTime(distance_to_target, v, goal_v, max_a, max_v, data);

        //Get the data from the passed-by-reference object back into local variables
        dt2 = data.t1;
        a = data.a1;
        const_time = data.ct;
        dtf = data.t2;
        af = data.a2;

        double time_left = dt;
        if (dt2 > time_left) {
            UpdateVals(a, time_left);
        } else {
            UpdateVals(a, dt2);
            time_left -= dt2;
            if (const_time > time_left) {
                UpdateVals(0, time_left);
            } else {
                UpdateVals(0, const_time);
                time_left -= const_time;
                if (dtf > time_left) {
                    UpdateVals(af, time_left);
                } else {
                    UpdateVals(af, dtf);
                    time_left -= dtf;
                    UpdateVals(0, time_left);
                }
            }
        }
    }

    protected void UpdateVals(double acc, double dt) {
        // basic kinematics
        m_currPos += m_currVel * dt + acc * .5 * dt * dt;
        m_currVel += acc * dt;
        m_currAcc = acc;
    }

    private void maxAccelTime(double distance_left, double curr_vel, double goal_vel, double max_a, double max_v, MaxAccelTimeData data) {

        double const_time = 0;
        double start_a;
        if (distance_left > 0) {
            start_a = max_a;
        } else if (distance_left == 0) {
            data.t1 = 0;
            data.a1 = 0;
            data.ct = 0;
            data.t2 = 0;
            data.a2 = 0;
            return;
        } else {
            maxAccelTime(-distance_left, -curr_vel, -goal_vel, max_a, max_v, data);
            data.a1 *= -1;
            data.a2 *= -1;
            return;
        }
        double max_accel_velocity = distance_left * 2 * Math.abs(start_a) + curr_vel * curr_vel;
        if (max_accel_velocity > 0) {
            max_accel_velocity = Math.sqrt(max_accel_velocity);
        } else {
            max_accel_velocity = -Math.sqrt(-max_accel_velocity);
        }

        // Since we know what we'd have to do if we kept after it to decelerate, we know the sign of the acceleration.
        double final_a;
        if (max_accel_velocity > goal_vel) {
            final_a = -max_a;
        } else {
            final_a = max_a;
        }

        // We now know the top velocity we can get to
        double top_v = Math.sqrt((distance_left + (curr_vel * curr_vel) / (2.0 * start_a) + (goal_vel * goal_vel) / (2.0 * final_a)) / (-1.0 / (2.0 * final_a) + 1.0 / (2.0 * start_a)));

        // If it is too fast, we now know how long we get to accelerate for and how long to go at constant velocity
        double accel_time;
        if (top_v > max_v) {
            accel_time = (max_v - curr_vel) / max_a;
            const_time = (distance_left + (goal_vel * goal_vel - max_v * max_v) / (2.0 * max_a)) / max_v;
        } else {
            accel_time = (top_v - curr_vel) / start_a;
        }

        assert (top_v > -max_v);

        data.t1 = accel_time;
        data.a1 = start_a;
        data.ct = const_time;
        data.t2 = (goal_vel - top_v) / final_a;
        data.a2 = final_a;
    }

    /**
     * Stores all the variables that are passed by reference in the C++ version
     * of maxAccelTime(). The Java version passes a reference to an object of
     * this class, in which the values can be changed.
     */
    private static class MaxAccelTimeData {

        /**
         * Time 1
         */
        public double t1;
        /**
         * Acceleration 1
         */
        public double a1;
        /**
         * Constant time
         */
        public double ct;
        /**
         * Time 2
         */
        public double t2;
        /**
         * Acceleration 2
         */
        public double a2;
    }
};
