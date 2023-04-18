package org.zerock.ex2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest
class Ex2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void prac() {
		int[] numArr = new int[10];

		for (int i=0; i < numArr.length; i++) {
			System.out.print(numArr[i] = (int)(Math.random() * 10));
		}
		System.out.println();

		for (int i=0; i < numArr.length - 1; i++) {
			boolean change = false;

			for (int j=0; j < numArr.length - 1 - i;  j++) {
				if (numArr[j] > numArr[j+1]) {
					int tmp = numArr[j];
					numArr[j] = numArr[j+1];
					numArr[j+1] = tmp;
					change = true;
				}
			}
			if (!change) break;
		}

		for (int k=0; k < numArr.length; k++) {
			System.out.print(numArr[k]);
		}
		System.out.println();

	}

	@Test
	void prac2() {
		int[] numArr = new int[10];
		int[] counter = new int[10];

		for (int i=0; i < numArr.length; i++) {
			numArr[i] = (int)(Math.random() * 10);
			System.out.print(numArr[i]);
		}

		System.out.println();

		for (int i=0; i < numArr.length; i++) {
			counter[numArr[i]]++;
		}

		for (int i=0; i < counter.length; i++) {
			System.out.println(i + "의 갯수 = " + counter[i]);
		}

	}

	@Test
	void prac3() {
		int[][] score = {
				{100, 100, 100},
				{20, 20, 20},
				{30, 30, 30},
				{40, 40, 40},
				{50, 50, 50}
		};

		int korTotal = 0;
		int engTotal = 0;
		int mathTotal = 0;

		System.out.println("번호  국어  영어  수학  총점  평균");
		System.out.println("===========================");

		for (int i=0; i < score.length; i++) {
			int sum = 0;
			float avg = 0.0f;

			korTotal += score[i][0];
			engTotal += score[i][1];
			mathTotal += score[i][2];

			System.out.printf("%3d", i+1);

			for (int j=0; j < score[i].length; j++) {
				sum += score[i][j];
				System.out.printf("%5d", score[i][j]);
			}

			avg = sum / (float)score[i].length;
			System.out.printf("%5d %5.1f%n", sum, avg);

		}

		System.out.println("========================");
		System.out.printf("총점:%3d  %4d  %4d%n", korTotal, engTotal, mathTotal);

	}

	@Test
	void test() {
		int result = factorial(10);
		System.out.println("result = " + result);
	}

	int factorial(int n) {
		int result = 0;

		if (n == 1) {
			result = 1;
		} else {
			result = n * factorial(n-1);
		}
		return result;
	}

}