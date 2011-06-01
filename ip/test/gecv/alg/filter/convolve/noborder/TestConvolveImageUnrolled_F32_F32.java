/*
 * Copyright 2011 Peter Abeles
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gecv.alg.filter.convolve.noborder;

import gecv.alg.filter.convolve.CompareToStandardConvolution;
import gecv.struct.convolve.Kernel1D_F32;
import gecv.struct.convolve.Kernel2D_F32;
import gecv.struct.image.ImageFloat32;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author Peter Abeles
 */
public class TestConvolveImageUnrolled_F32_F32 {
	CompareToStandardConvolution compareToStandard = new CompareToStandardConvolution(ConvolveImageUnrolled_F32_F32.class);

	@Test
	public void convolve() throws NoSuchMethodException {
		for (int i = 0; i < GenerateConvolvedUnrolled.numUnrolled; i++) {
			Method m = ConvolveImageUnrolled_F32_F32.class.getMethod("convolve",
					Kernel2D_F32.class, ImageFloat32.class, ImageFloat32.class );

			compareToStandard.compareMethod(m, "convolve", i + 1);
		}
	}

	@Test
	public void horizontal() throws NoSuchMethodException {
		for (int i = 0; i < GenerateConvolvedUnrolled.numUnrolled; i++) {
			Method m = ConvolveImageUnrolled_F32_F32.class.getMethod("horizontal",
					Kernel1D_F32.class, ImageFloat32.class, ImageFloat32.class, boolean.class);

			compareToStandard.compareMethod(m, "horizontal", i + 1);
		}
	}

	@Test
	public void vertical() throws NoSuchMethodException {
		for (int i = 0; i < GenerateConvolvedUnrolled.numUnrolled; i++) {
			Method m = ConvolveImageUnrolled_F32_F32.class.getMethod("vertical",
					Kernel1D_F32.class, ImageFloat32.class, ImageFloat32.class, boolean.class);

			compareToStandard.compareMethod(m, "vertical", i + 1);
		}
	}
}