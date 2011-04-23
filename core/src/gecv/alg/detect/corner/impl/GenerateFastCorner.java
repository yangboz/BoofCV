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

package gecv.alg.detect.corner.impl;

import gecv.misc.CodeGeneratorUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Code generator for {@link gecv.alg.detect.corner.FastCornerIntensity}.
 *
 * @author Peter Abeles
 */
public class GenerateFastCorner {
	PrintStream out;

	String className;
	String typeInput;
	String dataInput;
	String bitWise;
	String dataTol;

	public void createAll() throws FileNotFoundException {
		create_I8();
		create_F32();
	}

	public void create_I8() throws FileNotFoundException {
		className = "FastCorner12_I8";
		typeInput = "ImageInt8";
		dataInput = "byte";
		dataTol = "int";
		bitWise = " & 0xFF";

		createFile();
	}

	public void create_F32() throws FileNotFoundException {
		className = "FastCorner12_F32";
		typeInput = "ImageFloat32";
		dataInput = "float";
		dataTol = "float";
		bitWise = "";

		createFile();
	}

	public void createFile() throws FileNotFoundException {
		out = new PrintStream(new FileOutputStream(className + ".java"));

		printPreamble();
		printDetection();

		out.println("}");
	}

	private void printPreamble() {
		out.print(CodeGeneratorUtil.copyright);
		out.print("package gecv.alg.detect.corner.impl;\n" +
				"\n" +
				"import gecv.alg.detect.corner.FastCornerIntensity;\n" +
				"import gecv.misc.DiscretizedCircle;\n" +
				"import gecv.struct.QueueCorner;\n" +
				"import gecv.struct.image.ImageFloat32;\n");
		if( typeInput.compareTo("ImageFloat32") != 0 )
			out.print("import gecv.struct.image."+typeInput+";\n");
		out.print("\n" +
				"/**\n" +
				" * <p>\n" +
				" * An implementation of {@link FastCornerIntensity} algorithm that is designed to be\n" +
				" * more easily read and verified for correctness.\n" +
				" * <p/>\n" +
				" *\n" +
				" * @author Peter Abeles\n" +
				" */\n" +
				"public class "+className+" implements FastCornerIntensity<"+typeInput+"> {\n" +
				"\n" +
				"\tprivate int minCont;\n" +
				"\tprivate final static int radius = 3;\n" +
				"\n" +
				"\t// how similar do the pixel in the circle need to be to the center pixel\n" +
				"\tprivate "+dataTol+" pixelTol;\n" +
				"\n" +
				"\t// corner intensity image\n" +
				"\tprivate ImageFloat32 featureIntensity;\n" +
				"\n" +
				"\t// list of pixels that might be corners.\n" +
				"\tprivate QueueCorner candidates;\n" +
				"\n" +
				"\t/**\n" +
				"\t * @param imgWidth Input image width.\n" +
				"\t * @param imgHeight input image height.\n" +
				"\t * @param pixelTol The difference in intensity value from the center pixel the circle needs to be.\n" +
				"\t * @param minCont  The minimum number of continuous pixels that a circle needs to be a corner.\n" +
				"\t */\n" +
				"\tpublic "+className+"( int imgWidth , int imgHeight , int pixelTol, int minCont) {\n" +
				"\t\tthis.pixelTol = pixelTol;\n" +
				"\t\tthis.minCont = minCont;\n" +
				"\n" +
				"\t\tfeatureIntensity = new ImageFloat32(imgWidth, imgHeight);\n" +
				"\n" +
				"\t\tcandidates = new QueueCorner(imgWidth);\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic ImageFloat32 getIntensity() {\n" +
				"\t\treturn featureIntensity;\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic QueueCorner getCandidates() {\n" +
				"\t\treturn candidates;\n" +
				"\t}\n" +
				"\n" +
				"\t@Override\n" +
				"\tpublic int getRadius() {\n" +
				"\t\treturn radius;\n" +
				"\t}\n\n");
	}

	public void printDetection() {
		out.print("\t@Override\n" +
				"\tpublic void process( "+typeInput+" img ) {\n" +
				"\t\tcandidates.reset();\n" +
				"\t\tfinal "+dataInput+"[] data = img.data;\n" +
				"\n" +
				"\t\tfinal int width = img.getWidth();\n" +
				"\t\tfinal int yEnd = img.getHeight() - radius;\n" +
				"\t\tfinal int stride = img.stride;\n" +
				"\n" +
				"\t\t// relative offsets of pixel locations in a circle\n" +
				"\t\tint []offsets = DiscretizedCircle.imageOffsets(radius, stride);\n"+
				"\n" +
				"\t\tfinal float[] inten = featureIntensity.data;\n" +
				"\n" +
				"\t\tint offA = offsets[0];\n" +
				"\t\tint offB = offsets[4];\n" +
				"\t\tint offC = offsets[8];\n" +
				"\t\tint offD = offsets[12];\n" +
				"\n" +
				"\t\tfor (int y = radius; y < yEnd; y++) {\n" +
				"\t\t\tint rowStart = img.startIndex + stride * y;\n" +
				"\t\t\tint endX = rowStart + width - radius;\n" +
				"\t\t\tint intenIndex = featureIntensity.startIndex + y*featureIntensity.stride+radius;"+
				"\n" +
				"\t\t\tfor (int index = rowStart + radius; index < endX; index++) {\n" +
				"\n" +
				"\t\t\t\t// quickly eliminate bad choices by examining 4 points spread out\n" +
				"\t\t\t\t"+dataTol+" center = data[index]"+bitWise+";\n" +
				"\n" +
				"\t\t\t\t"+dataTol+" a = data[index + offA]"+bitWise+";\n" +
				"\t\t\t\t"+dataTol+" b = data[index + offB]"+bitWise+";\n" +
				"\t\t\t\t"+dataTol+" c = data[index + offC]"+bitWise+";\n" +
				"\t\t\t\t"+dataTol+" d = data[index + offD]"+bitWise+";\n" +
				"\n" +
				"\t\t\t\t"+dataTol+" thresh = center - pixelTol;\n" +
				"\n" +
				"\t\t\t\tint action = 0;\n" +
				"\n" +
				"\t\t\t\t// check to see if it is significantly below the center pixel\n" +
				"\t\t\t\tif (a < thresh && c < thresh) {\n" +
				"\t\t\t\t\tif (b < thresh) {\n" +
				"\t\t\t\t\t\taction = -1;\n" +
				"\t\t\t\t\t} else if (d < thresh) {\n" +
				"\t\t\t\t\t\taction = -1;\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t} else if (b < thresh && d < thresh) {\n" +
				"\t\t\t\t\tif (a < thresh) {\n" +
				"\t\t\t\t\t\taction = -1;\n" +
				"\t\t\t\t\t} else if (c < thresh) {\n" +
				"\t\t\t\t\t\taction = -1;\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t} else {\n" +
				"\t\t\t\t\t// see if it is significantly more than the center pixel\n" +
				"\t\t\t\t\tthresh = center + pixelTol;\n" +
				"\n" +
				"\t\t\t\t\tif (a > thresh && c > thresh) {\n" +
				"\t\t\t\t\t\tif (d > thresh) {\n" +
				"\t\t\t\t\t\t\taction = 1;\n" +
				"\t\t\t\t\t\t} else if (b > thresh) {\n" +
				"\t\t\t\t\t\t\taction = 1;\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t\tif (b > thresh && d > thresh) {\n" +
				"\t\t\t\t\t\tif (a > thresh) {\n" +
				"\t\t\t\t\t\t\taction = 1;\n" +
				"\t\t\t\t\t\t} else if (c > thresh) {\n" +
				"\t\t\t\t\t\t\taction = 1;\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\t// can't be a corner here so just continue to the next pixel\n" +
				"\t\t\t\tif (action == 0) {\n" +
				"\t\t\t\t\tinten[intenIndex] = 0F;\n" +
				"\t\t\t\t\tcontinue;\n" +
				"\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\tboolean isCorner = false;\n" +
				"\n" +
				"\t\t\t\t// move until it finds a valid pixel\n" +
				"\t\t\t\t"+dataTol+" totalDiff = 0;\n" +
				"\n" +
				"\t\t\t\t// see if the first pixel is valid or not\n" +
				"\t\t\t\t"+dataTol+" val = a - center;\n" +
				"\t\t\t\tif ((action == -1 && val < -pixelTol) || val > pixelTol) {\n" +
				"\t\t\t\t\t// if it is valid then it needs to deal with wrapping\n" +
				"\t\t\t\t\tint i;\n" +
				"\t\t\t\t\t// find the point a bad pixel is found\n" +
				"\t\t\t\t\ttotalDiff += val;\n" +
				"\t\t\t\t\tfor (i = 1; i < offsets.length; i++) {\n" +
				"\t\t\t\t\t\tval = (data[index + offsets[i]]"+bitWise+") - center;\n" +
				"\n" +
				"\t\t\t\t\t\tif (action == -1) {\n" +
				"\t\t\t\t\t\t\tif (val >= -pixelTol) break;\n" +
				"\t\t\t\t\t\t} else if (val <= pixelTol) break;\n" +
				"\n" +
				"\t\t\t\t\t\ttotalDiff += val;\n" +
				"\t\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\t\tint frontLength = i;\n" +
				"\n" +
				"\t\t\t\t\tif (frontLength < minCont) {\n" +
				"\t\t\t\t\t\t// go the other direction\n" +
				"\t\t\t\t\t\tfor (i = offsets.length - 1; i >= 0; i--) {\n" +
				"\t\t\t\t\t\t\tval = (data[index + offsets[i]]"+bitWise+") - center;\n" +
				"\n" +
				"\t\t\t\t\t\t\tif (action == -1) {\n" +
				"\t\t\t\t\t\t\t\tif (val >= -pixelTol) break;\n" +
				"\t\t\t\t\t\t\t} else if (val <= pixelTol) break;\n" +
				"\t\t\t\t\t\t\ttotalDiff += val;\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t\tif (offsets.length - 1 - i + frontLength >= minCont) {\n" +
				"\t\t\t\t\t\t\tisCorner = true;\n" +
				"\t\t\t\t\t\t}\n" +
				"\t\t\t\t\t} else {\n" +
				"\t\t\t\t\t\tisCorner = true;\n" +
				"\t\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\t} else {\n" +
				"\t\t\t\t\t// find the first good pixel\n" +
				"\t\t\t\t\tint start;\n" +
				"\t\t\t\t\tfor (start = 0; start < offsets.length; start++) {\n" +
				"\t\t\t\t\t\tval = (data[index + offsets[start]]"+bitWise+") - center;\n" +
				"\n" +
				"\t\t\t\t\t\tif (action == -1) {\n" +
				"\t\t\t\t\t\t\tif (val < -pixelTol) break;\n" +
				"\t\t\t\t\t\t} else if (val > pixelTol) break;\n" +
				"\t\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\t\t// find the point where the good pixels stop\n" +
				"\t\t\t\t\tint stop;\n" +
				"\t\t\t\t\tfor (stop = start + 1; stop < offsets.length; stop++) {\n" +
				"\t\t\t\t\t\tval = (data[index + offsets[stop]]"+bitWise+") - center;\n" +
				"\n" +
				"\t\t\t\t\t\tif (action == -1) {\n" +
				"\t\t\t\t\t\t\tif (val >= -pixelTol) break;\n" +
				"\t\t\t\t\t\t} else if (val <= pixelTol) break;\n" +
				"\t\t\t\t\t\ttotalDiff += val;\n" +
				"\t\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\t\tisCorner = stop - start >= minCont;\n" +
				"\n" +
				"\t\t\t\t}\n" +
				"\n" +
				"\t\t\t\tif (isCorner) {\n" +
				"\t\t\t\t\tinten[intenIndex] = action == -1 ? -totalDiff : totalDiff;\n" +
				"\t\t\t\t\t// declare room for more features\n" +
				"\t\t\t\t\tif( candidates.isFull() ) {\n" +
				"\t\t\t\t\t\tcandidates.resize(candidates.getMaxSize()*2);\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t\tcandidates.add( index-rowStart , y );\n" +
				"\t\t\t\t} else {\n" +
				"\t\t\t\t\tinten[intenIndex] = 0F;\n" +
				"\t\t\t\t}\n" +
				"\t\t\t}\n" +
				"\t\t}\n" +
				"\t}\n");
	}

	public static void main( String args[] ) throws FileNotFoundException {
		GenerateFastCorner gen = new GenerateFastCorner();

		gen.createAll();
	}
}
