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

package gecv.abst.detect.corner;

import gecv.abst.detect.extract.CornerExtractor;
import gecv.struct.QueueCorner;
import gecv.struct.image.ImageBase;

/**
 * Abstracted class for extracting corners from a the image's gradient.
 *
 * @see gecv.alg.detect.corner
 * @see gecv.abst.detect.extract
 *
 * @author Peter Abeles
 */
public class CornerDetectorGradient<I extends ImageBase> extends CornerDetectorBase<I>{

	// computes the corner intensity image
	CornerIntensityGradient<I> intensity;

	public CornerDetectorGradient(CornerIntensityGradient<I> intensity, CornerExtractor extractor,
								  int maxFoundCorners) {
		super(extractor,maxFoundCorners);
		this.intensity = intensity;
	}

	/**
	 * Computes corners from image gradients.
	 *
	 * @param derivX image derivative in along the x-axis.
	 * @param derivY image derivative in along the y-axis.
	 */
	public void process( I derivX , I derivY ) {
		intensity.process(derivX,derivY);
		extractor.process( intensity.getIntensity(), candidateCorners,requestedFeatureNumber,foundCorners);
	}
}
