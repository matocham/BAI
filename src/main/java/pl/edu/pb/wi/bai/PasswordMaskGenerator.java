package pl.edu.pb.wi.bai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class PasswordMaskGenerator {
	private static final int MIN_LENGTH = 5;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int[] VALUES = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768,
			65536 };

	private int maxLength;
	private int numberOfMasks;
	private Random random = new Random(System.currentTimeMillis());

	public PasswordMaskGenerator(int passwordLength, int numberOfMasks) {
		super();
		this.maxLength = passwordLength / 2;
		if (maxLength < MIN_LENGTH) {
			maxLength = MIN_LENGTH;
		}
		this.numberOfMasks = numberOfMasks;
	}

	public String[] getMasks() {
		int currentMaskPos = 0;
		List<String> masks = new ArrayList<>();

		while (currentMaskPos < numberOfMasks) {
			int value;
			String bits;
			do {
				value = generateValue();
				bits = convertToBits(value);
			} while (masks.contains(bits));
			masks.add(bits);
			currentMaskPos++;
		}
		return masks.toArray(new String[numberOfMasks]);
	}

	private int generateValue() {
		int onesInMask = random.nextInt(maxLength - MIN_LENGTH) + MIN_LENGTH;
		int result = 0;
		List<Integer> selectedNumbers = new ArrayList<>();
		for (int i = 0; i < onesInMask; i++) {
			int nextNumber;
			do {
				nextNumber = random.nextInt(VALUES.length);
				if (selectedNumbers.size() == VALUES.length) {
					throw new RuntimeException("All values already used");
				}

			} while (selectedNumbers.contains(nextNumber));
			selectedNumbers.add(nextNumber);
			result += VALUES[nextNumber];
		}
		return result;
	}

	private String convertToBits(int value) {
		String binary = Integer.toBinaryString(value);
		if (binary.length() > MAX_PASSWORD_LENGTH) {
			binary = binary.substring(0, MAX_PASSWORD_LENGTH);
		} else {
			binary = StringUtils.rightPad(binary, MAX_PASSWORD_LENGTH, '0');
		}
		return binary;
	}
}
