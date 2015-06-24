/*
 * The MIT License
 *
 * Copyright 2015 tibo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.debatty.java.stringsimilarity.examples;

import info.debatty.java.utils.SparseDoubleVector;
import java.util.Random;

/**
 *
 * @author tibo
 */
public class SparseDoubleVectorExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int count = 400;
        int size = 1000;
        double threshold = 0.7;
        
        System.out.println("Create some random SparseDoubleVector...");
        Random r = new Random();
        SparseDoubleVector[] data = new SparseDoubleVector[count];
        for (int i = 0; i < count; i++) {
            double[] v = new double[size];
            for (int j = 0; j < size; j++) {
                v[j] = r.nextDouble();
            }
            data[i] = new SparseDoubleVector(v);
        }
        
        
        System.out.println("Compute real similarities...");
        double[][] real_similarities = new double[count][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < i; j++) {
                real_similarities[i][j] = data[i].cosineSimilarity(data[j]);
            }
        }
        
        
        System.out.println("Downsample the vectors using DIMSUM algorithm...");
        for (int i = 0; i < count; i++) {
            try {
                data[i].sampleDIMSUM(threshold, count, size);
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        System.out.println("Compute estimated similarities...");
        int above_threshold = 0;
        int correct = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < i; j++) {
                
                double sim = data[i].cosineSimilarity(data[j]);
                
                if (real_similarities[i][j] >= threshold) {
                    above_threshold++;
                    
                    if (Math.abs(real_similarities[i][j] - sim) / real_similarities[i][j] < 0.2) {
                        correct++;
                    }
                }
            }
        }
        System.out.println("Above threshold: " + above_threshold);
        System.out.println("Correct (max relative error 20%)" + correct);
        System.out.println("(" + Math.round(100.0 * correct / above_threshold) + "%)");
        
    }
}
