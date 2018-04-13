package pl.edu.pb.wi.bai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PasswordMaskGeneratorTest {

	PasswordMaskGenerator generator;

	@Before
	public void setUp() {
		generator = new PasswordMaskGenerator(16, 10);
	}

	@Test
	public void shouldGenerateTenDifferentMasks() {
		String[] masks = generator.getMasks();
		assertThat(masks.length).isEqualTo(10);
		for (int i = 0; i < masks.length; i++) {
			for (int j = 0; j < masks.length; j++) {
				if (i == j) {
					continue;
				}
				assertThat(masks[i]).isNotEqualTo(masks[j]);
			}
		}
	}

	@Test
	public void shouldGenerateMasksBetween5And8() {
		String[] masks = generator.getMasks();
		for (int i = 0; i < masks.length; i++) {
			int onesCount = masks[i].replaceAll("0", "").length();
			assertThat(onesCount).isBetween(5, 8);
		}
	}

}
