/*
 * Copyright (c) 2011, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://www.boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.io.image;

import boofcv.gui.image.ImagePanel;
import boofcv.gui.image.ShowImages;
import boofcv.struct.image.ImageBase;

import java.awt.image.BufferedImage;

/**
 * Simple class for playing back an image sequence.
 *
 * @author Peter Abeles
 */
public class PlaybackImageSequence<T extends ImageBase> extends ProcessImageSequence<T> {

	ImagePanel panel;


	public PlaybackImageSequence(SimpleImageSequence<T> sequence) {
		super(sequence);
	}

	@Override
	public void processFrame(T image) {

	}

	@Override
	public void updateGUI(BufferedImage guiImage, T origImage) {
		if (panel == null) {
			panel = ShowImages.showWindow(guiImage, "Image Sequence");
			addComponent(panel);
		} else {
			panel.setBufferedImage(guiImage);
			panel.repaint();
		}
	}
}