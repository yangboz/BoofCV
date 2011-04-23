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

package gecv.alg.filter.convolve.impl;

import gecv.struct.convolve.Kernel1D_I32;
import gecv.struct.image.ImageInt16;

/**
 * <p>
 * Unrolls the convolution kernel to reduce array accessing and save often used variables to the stack.
 * </p>
 *
 * <p>
 * Unrolling the image being convolved resulting in an additional 10% performance boost on a Core i7 processor,
 * see commented out code below.  Due to the added complexity it was decided that this performance boost was
 * not worth it.  By comparison, unrolling the kernel causes a performance boost between 2 and 3 times.
 * </p>
 * 
 * <p>
 * DO NOT MODIFY.  This class was automatically generated by {@link GenerateConvolvedUnrolled}.
 * </p>
 *
 * @author Peter Abeles
 */
public class ConvolveImageUnrolled_I16_I16 {
	public static boolean horizontal( Kernel1D_I32 kernel ,
								   ImageInt16 image, ImageInt16 dest,
								   boolean includeBorder) {
		switch( kernel.width ) {
			case 3:
				horizontal3(kernel,image,dest,includeBorder);
				break;

			case 5:
				horizontal5(kernel,image,dest,includeBorder);
				break;

			case 7:
				horizontal7(kernel,image,dest,includeBorder);
				break;

			case 9:
				horizontal9(kernel,image,dest,includeBorder);
				break;

			case 11:
				horizontal11(kernel,image,dest,includeBorder);
				break;

			default:
				return false;
		}
		return true;
	}

	public static boolean vertical( Kernel1D_I32 kernel ,
								   ImageInt16 image, ImageInt16 dest,
								   boolean includeBorder) {
		switch( kernel.width ) {
			case 3:
				vertical3(kernel,image,dest,includeBorder);
				break;

			case 5:
				vertical5(kernel,image,dest,includeBorder);
				break;

			case 7:
				vertical7(kernel,image,dest,includeBorder);
				break;

			case 9:
				vertical9(kernel,image,dest,includeBorder);
				break;

			case 11:
				vertical11(kernel,image,dest,includeBorder);
				break;

			default:
				return false;
		}
		return true;
	}

	public static void horizontal3( Kernel1D_I32 kernel ,
									ImageInt16 image, ImageInt16 dest,
									boolean includeBorder) {
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];

		final int radius = kernel.getRadius();

		final int yBorder = includeBorder ? 0 : radius;

		final int width = image.getWidth();
		final int height = image.getHeight()-yBorder;

		for( int i = yBorder; i < height; i++ ) {
			int indexDst = dest.startIndex + i*dest.stride+radius;
			int j = image.startIndex+ i*image.stride;
			final int jEnd = j+width-radius;

			for( j += radius; j < jEnd; j++ ) {
				int indexSrc = j-radius;
				int total = (dataSrc[indexSrc++])*k1;
				total += (dataSrc[indexSrc++])*k2;
				total += (dataSrc[indexSrc])*k3;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void horizontal5( Kernel1D_I32 kernel ,
									ImageInt16 image, ImageInt16 dest,
									boolean includeBorder) {
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];

		final int radius = kernel.getRadius();

		final int yBorder = includeBorder ? 0 : radius;

		final int width = image.getWidth();
		final int height = image.getHeight()-yBorder;

		for( int i = yBorder; i < height; i++ ) {
			int indexDst = dest.startIndex + i*dest.stride+radius;
			int j = image.startIndex+ i*image.stride;
			final int jEnd = j+width-radius;

			for( j += radius; j < jEnd; j++ ) {
				int indexSrc = j-radius;
				int total = (dataSrc[indexSrc++])*k1;
				total += (dataSrc[indexSrc++])*k2;
				total += (dataSrc[indexSrc++])*k3;
				total += (dataSrc[indexSrc++])*k4;
				total += (dataSrc[indexSrc])*k5;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void horizontal7( Kernel1D_I32 kernel ,
									ImageInt16 image, ImageInt16 dest,
									boolean includeBorder) {
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];
		final int k6 = kernel.data[5];
		final int k7 = kernel.data[6];

		final int radius = kernel.getRadius();

		final int yBorder = includeBorder ? 0 : radius;

		final int width = image.getWidth();
		final int height = image.getHeight()-yBorder;

		for( int i = yBorder; i < height; i++ ) {
			int indexDst = dest.startIndex + i*dest.stride+radius;
			int j = image.startIndex+ i*image.stride;
			final int jEnd = j+width-radius;

			for( j += radius; j < jEnd; j++ ) {
				int indexSrc = j-radius;
				int total = (dataSrc[indexSrc++])*k1;
				total += (dataSrc[indexSrc++])*k2;
				total += (dataSrc[indexSrc++])*k3;
				total += (dataSrc[indexSrc++])*k4;
				total += (dataSrc[indexSrc++])*k5;
				total += (dataSrc[indexSrc++])*k6;
				total += (dataSrc[indexSrc])*k7;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void horizontal9( Kernel1D_I32 kernel ,
									ImageInt16 image, ImageInt16 dest,
									boolean includeBorder) {
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];
		final int k6 = kernel.data[5];
		final int k7 = kernel.data[6];
		final int k8 = kernel.data[7];
		final int k9 = kernel.data[8];

		final int radius = kernel.getRadius();

		final int yBorder = includeBorder ? 0 : radius;

		final int width = image.getWidth();
		final int height = image.getHeight()-yBorder;

		for( int i = yBorder; i < height; i++ ) {
			int indexDst = dest.startIndex + i*dest.stride+radius;
			int j = image.startIndex+ i*image.stride;
			final int jEnd = j+width-radius;

			for( j += radius; j < jEnd; j++ ) {
				int indexSrc = j-radius;
				int total = (dataSrc[indexSrc++])*k1;
				total += (dataSrc[indexSrc++])*k2;
				total += (dataSrc[indexSrc++])*k3;
				total += (dataSrc[indexSrc++])*k4;
				total += (dataSrc[indexSrc++])*k5;
				total += (dataSrc[indexSrc++])*k6;
				total += (dataSrc[indexSrc++])*k7;
				total += (dataSrc[indexSrc++])*k8;
				total += (dataSrc[indexSrc])*k9;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void horizontal11( Kernel1D_I32 kernel ,
									ImageInt16 image, ImageInt16 dest,
									boolean includeBorder) {
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];
		final int k6 = kernel.data[5];
		final int k7 = kernel.data[6];
		final int k8 = kernel.data[7];
		final int k9 = kernel.data[8];
		final int k10 = kernel.data[9];
		final int k11 = kernel.data[10];

		final int radius = kernel.getRadius();

		final int yBorder = includeBorder ? 0 : radius;

		final int width = image.getWidth();
		final int height = image.getHeight()-yBorder;

		for( int i = yBorder; i < height; i++ ) {
			int indexDst = dest.startIndex + i*dest.stride+radius;
			int j = image.startIndex+ i*image.stride;
			final int jEnd = j+width-radius;

			for( j += radius; j < jEnd; j++ ) {
				int indexSrc = j-radius;
				int total = (dataSrc[indexSrc++])*k1;
				total += (dataSrc[indexSrc++])*k2;
				total += (dataSrc[indexSrc++])*k3;
				total += (dataSrc[indexSrc++])*k4;
				total += (dataSrc[indexSrc++])*k5;
				total += (dataSrc[indexSrc++])*k6;
				total += (dataSrc[indexSrc++])*k7;
				total += (dataSrc[indexSrc++])*k8;
				total += (dataSrc[indexSrc++])*k9;
				total += (dataSrc[indexSrc++])*k10;
				total += (dataSrc[indexSrc])*k11;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void vertical3( Kernel1D_I32 kernel,
								 ImageInt16 image, ImageInt16 dest,
								 boolean includeBorder)
	{
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];

		final int radius = kernel.getRadius();

		final int imgWidth = dest.getWidth();
		final int imgHeight = dest.getHeight();

		final int yEnd = imgHeight-radius;

		final int xBorder = includeBorder ? 0 : radius;

		for( int y = radius; y < yEnd; y++ ) {
			int indexDst = dest.startIndex+y*dest.stride+xBorder;
			int i = image.startIndex+y*image.stride;
			final int iEnd = i+imgWidth-xBorder;

			for( i += xBorder; i < iEnd; i++ ) {
				int indexSrc = i-radius*image.stride;

				int total = dataSrc[indexSrc] * k1;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k2;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k3;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void vertical5( Kernel1D_I32 kernel,
								 ImageInt16 image, ImageInt16 dest,
								 boolean includeBorder)
	{
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];

		final int radius = kernel.getRadius();

		final int imgWidth = dest.getWidth();
		final int imgHeight = dest.getHeight();

		final int yEnd = imgHeight-radius;

		final int xBorder = includeBorder ? 0 : radius;

		for( int y = radius; y < yEnd; y++ ) {
			int indexDst = dest.startIndex+y*dest.stride+xBorder;
			int i = image.startIndex+y*image.stride;
			final int iEnd = i+imgWidth-xBorder;

			for( i += xBorder; i < iEnd; i++ ) {
				int indexSrc = i-radius*image.stride;

				int total = dataSrc[indexSrc] * k1;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k2;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k3;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k4;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k5;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void vertical7( Kernel1D_I32 kernel,
								 ImageInt16 image, ImageInt16 dest,
								 boolean includeBorder)
	{
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];
		final int k6 = kernel.data[5];
		final int k7 = kernel.data[6];

		final int radius = kernel.getRadius();

		final int imgWidth = dest.getWidth();
		final int imgHeight = dest.getHeight();

		final int yEnd = imgHeight-radius;

		final int xBorder = includeBorder ? 0 : radius;

		for( int y = radius; y < yEnd; y++ ) {
			int indexDst = dest.startIndex+y*dest.stride+xBorder;
			int i = image.startIndex+y*image.stride;
			final int iEnd = i+imgWidth-xBorder;

			for( i += xBorder; i < iEnd; i++ ) {
				int indexSrc = i-radius*image.stride;

				int total = dataSrc[indexSrc] * k1;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k2;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k3;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k4;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k5;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k6;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k7;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void vertical9( Kernel1D_I32 kernel,
								 ImageInt16 image, ImageInt16 dest,
								 boolean includeBorder)
	{
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];
		final int k6 = kernel.data[5];
		final int k7 = kernel.data[6];
		final int k8 = kernel.data[7];
		final int k9 = kernel.data[8];

		final int radius = kernel.getRadius();

		final int imgWidth = dest.getWidth();
		final int imgHeight = dest.getHeight();

		final int yEnd = imgHeight-radius;

		final int xBorder = includeBorder ? 0 : radius;

		for( int y = radius; y < yEnd; y++ ) {
			int indexDst = dest.startIndex+y*dest.stride+xBorder;
			int i = image.startIndex+y*image.stride;
			final int iEnd = i+imgWidth-xBorder;

			for( i += xBorder; i < iEnd; i++ ) {
				int indexSrc = i-radius*image.stride;

				int total = dataSrc[indexSrc] * k1;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k2;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k3;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k4;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k5;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k6;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k7;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k8;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k9;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

	public static void vertical11( Kernel1D_I32 kernel,
								 ImageInt16 image, ImageInt16 dest,
								 boolean includeBorder)
	{
		final short[] dataSrc = image.data;
		final short[] dataDst = dest.data;

		final int k1 = kernel.data[0];
		final int k2 = kernel.data[1];
		final int k3 = kernel.data[2];
		final int k4 = kernel.data[3];
		final int k5 = kernel.data[4];
		final int k6 = kernel.data[5];
		final int k7 = kernel.data[6];
		final int k8 = kernel.data[7];
		final int k9 = kernel.data[8];
		final int k10 = kernel.data[9];
		final int k11 = kernel.data[10];

		final int radius = kernel.getRadius();

		final int imgWidth = dest.getWidth();
		final int imgHeight = dest.getHeight();

		final int yEnd = imgHeight-radius;

		final int xBorder = includeBorder ? 0 : radius;

		for( int y = radius; y < yEnd; y++ ) {
			int indexDst = dest.startIndex+y*dest.stride+xBorder;
			int i = image.startIndex+y*image.stride;
			final int iEnd = i+imgWidth-xBorder;

			for( i += xBorder; i < iEnd; i++ ) {
				int indexSrc = i-radius*image.stride;

				int total = dataSrc[indexSrc] * k1;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k2;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k3;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k4;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k5;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k6;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k7;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k8;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k9;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k10;
				indexSrc += image.stride;
				total += (dataSrc[indexSrc])*k11;

				dataDst[indexDst++] = ( short )total;
			}
		}
	}

}
