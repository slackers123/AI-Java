package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        float[][] X = {
                {0, 0, 1},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
        float[][] y = {
                {0},
                {1},
                {1},
                {0}
        };

        float[][] syn0 = generateRandomMatrix(3, 4);
        float[][] syn1 = generateRandomMatrix(4, 1);
        for (int i=0;i<60000;i++) {
            float[][] k0 = X;
            float[][] k1 = MatrixSigmoid(dot(k0, syn0));
            float[][] k2 = MatrixSigmoid(dot(k1, syn1));

            float[] k2_error = matrixSubtract(y, k2);
            if ((i % 10000) == 0) {
                System.out.println("Error:" + mean(matrixAbs(k2_error)));
                printFloatMatrix(k2);
            }

            float [] k2_delta = doit(k2, k2_error);
        }
    }

    private static float[] doit(float[][] x, float[] y) {
        float[][] nwY = new float[y.length][0];
        nwY[0] = y;
        float[][] temp = dot(x, nwY);

        return new float[]{};
    }

    private static float[][] dot(float[][] a, float[][] b) {
        int row1, col1, row2, col2;

        row1 = a.length;
        col1 = a[0].length;

        row2 = b.length;
        col2 = b[0].length;

        if(col1 != row2){
            System.out.println("Matrices cannot be multiplied");
        }
        else{
            float[][] prod = new float[row1][col2];

            for(int i = 0; i < row1; i++){
                for(int j = 0; j < col2; j++){
                    for(int k = 0; k < row2; k++){
                        prod[i][j] = prod[i][j] + a[i][k] * b[k][j];
                    }
                }
            }
            return prod;
        }
        return new float[0][0];
    }

    private static void printFloatMatrix(float[][] toPrint) {
        int lenA = toPrint.length;
        int lenB = toPrint[0].length;
        for(int i = 0; i < lenA; i++){
            for(int j = 0; j < lenB; j++){
                System.out.print(toPrint[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static float[][] MatrixReLU(float[][] toRectify) {
        int lenA = toRectify.length;
        int lenB = toRectify[0].length;
        float[][] out = new float[lenA][lenB];
        for(int i=0;i<lenA;i++){
            for (int j=0;j<lenB;j++) {
                out[i][j] = ReLU(toRectify[i][j]);
            }
        }
        return out;
    }

    private static float ReLU(float toRectify) {
        if (toRectify > 0){
            return toRectify;
        }
        else {
            return 0.0f;
        }
    }

    private static float[][] MatrixSigmoid(float[][] toRectify) {
        int lenA = toRectify.length;
        int lenB = toRectify[0].length;
        float[][] out = new float[lenA][lenB];
        for(int i=0;i<lenA;i++){
            for (int j=0;j<lenB;j++) {
                out[i][j] = sigmoid(toRectify[i][j]);
            }
        }
        return out;
    }

    private static float sigmoid(float x) {
        return (float) (1 / (1 + Math.exp(x)));
    }

    private static float[][] generateRandomMatrix(int lenA, int lenB) {
        Random rand = new Random(20);
        float[][] out = new float[lenA][lenB];
        for (int i=0;i < lenA;i++) {
            for (int j=0;j<lenB;j++) {
                if(rand.nextBoolean()) {
                    out[i][j] = -rand.nextFloat();
                }
                else{
                    out[i][j] = rand.nextFloat();
                }
            }
        }
        return out;
    }

    private static float[] matrixSubtract(float[][] a, float[][] b) {
        float[] out = new float[a.length];
        for (int i=0;i<a.length;i++) {
            out[i] = a[i][0] - b[i][0];
        }
        return out;
    }

    private static float[] matrixAbs(float[] x) {
        int lenA = x.length;
        float[] out = new float[lenA];
        for(int i=0;i<lenA;i++){
            for (int j=0;j<1;j++) {
                out[i] = abs(x[i]);
            }
        }
        return out;
    }

    private static float abs(float x) {
        if (x < 0) {
            return x * -1;
        }
        return x;
    }

    private static float mean(float[] toMean){
        int lenA = toMean.length;
        float out = 0;
        for(int i=0;i<lenA;i++){
            for (int j=0;j<1;j++) {
                out += toMean[i];
            }
        }
        return out/(toMean.length);
    }
}
