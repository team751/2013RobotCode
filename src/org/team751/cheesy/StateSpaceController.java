package org.team751.cheesy;

import Jama.Matrix;

/**
 * A state space controller After being set up properly, this controller works
 * as such: Given two matrices: r: the robot's goal position and velocity y: the
 * robot's current left and right encoder values The controller will return a
 * left and right voltage (with 12V being the maximum voltage) to make the robot
 * behave as well as possible
 * 
 * @author Team 254, ported to Java by Sam Crow
 */
public class StateSpaceController {

    /**
     * Enumerates different systems that the controller can control
     */
    public static class Controllers {

        public static final Controllers drive = new Controllers();

        private Controllers() {
        }
    };
    private int num_inputs;
    private int num_outputs;
    private int num_states;
    //the state matrices, calculated and imported from matlab
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private Matrix D;
    private Matrix L;
    private Matrix K;
    // other state matrices
    private Matrix X;
    private Matrix X_hat;
    private Matrix U;
    private Matrix U_max;
    private Matrix U_min;
    private Matrix b_u;
    private Matrix l_y;
    private Matrix l_c;
    private Matrix a_lc;
    private Matrix alc_xhat;
    private Matrix xhatp1;

    public StateSpaceController(int inputs, int outputs, int states, Controllers controller) {

        num_inputs = inputs;
        num_outputs = outputs;
        num_states = states;

        //initalizes all the matrices
        A = new Matrix(num_states, num_states);
        B = new Matrix(num_states, num_outputs);
        C = new Matrix(num_outputs, num_states);
        D = new Matrix(num_outputs, num_outputs);
        L = new Matrix(num_states, num_outputs);
        K = new Matrix(num_outputs, num_states);
        X = new Matrix(num_states, 1);
        X_hat = new Matrix(num_states, 1);
        U = new Matrix(num_outputs, 1);
        U_max = new Matrix(num_outputs, 1);
        U_min = new Matrix(num_outputs, 1);
//  U_tmp = new Matrix(num_states, 1);
        b_u = new Matrix(num_states, 1);
        l_y = new Matrix(num_states, 1);
        l_c = new Matrix(num_states, num_states);
        a_lc = new Matrix(num_states, num_states);
        alc_xhat = new Matrix(num_states, 1);
        xhatp1 = new Matrix(num_states, 1);

        //import the matlab-computed matrix values
        if (controller == Controllers.drive) {
            //taken from src/matlab/drivecontroller.h

            //A
            A.set(0, 0, 1);
            A.set(0, 1, 0.0188990104);
            A.set(0, 2, 0);
            //TODO: fill this in later
        }
    }

    public void reset() {
    }

    public void update(Matrix R, Matrix Y) {
        U = K.times(R.minus(X_hat));

        //Iterate over every value of U
        //If it is greater than the value in the same position in u_max
        //or less than the value in the same position in u_min, clamp it to
        //within that range.
        for (int i = 0; i < num_outputs; i++) {
            double u_i = U.get(i, 0);
            double u_max = U_max.get(i, 0);
            double u_min = U_min.get(i, 0);

            if (u_i > u_max) {
                U.set(i, 0, u_max);
            }
            if (u_i < u_min) {
                U.set(i, 0, u_min);
            }
        }

        b_u = B.times(U);
        l_y = L.times(Y);
        l_c = L.times(C);
        a_lc = A.minus(l_c);
        alc_xhat = a_lc.times(X_hat);
        xhatp1 = alc_xhat.plus(l_y);
        X_hat = xhatp1.plus(b_u);
    }
    
    /**
     * Get a reference to the U matrix that this controller uses
     * @return 
     */
    public Matrix getU() {
        return U;
    }
}
