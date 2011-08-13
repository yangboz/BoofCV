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

package gecv.alg.detect.intensity.impl;

import gecv.misc.AutoTypeImage;
import gecv.misc.CodeGeneratorBase;
import gecv.misc.CodeGeneratorUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * @author Peter Abeles
 */
public class GenerateImplHessianBlobIntensity extends CodeGeneratorBase {
	String className = "ImplHessianBlobIntensity";

	PrintStream out;
	AutoTypeImage inputType;
	AutoTypeImage derivType;

	public GenerateImplHessianBlobIntensity() throws FileNotFoundException {
		out = new PrintStream(new FileOutputStream(className + ".java"));
	}

	@Override
	public void generate() throws FileNotFoundException {
		printPreamble();

		printFuncs(AutoTypeImage.F32);
		printFuncs(AutoTypeImage.S16);

		out.print("\n" +
				"}\n");
	}

	private void printPreamble() {
		out.print(CodeGeneratorUtil.copyright);
		out.print("package gecv.alg.detect.corner.impl;\n" +
				"\n" +
				"import gecv.struct.image.*;\n" +
				"\n" +
				"/**\n" +
				" * <p>\n" +
				" * Implementations of {@link gecv.alg.detect.corner.HessianBlobIntensity}.\n" +
				" * </p>\n" +
				" *\n" +
				" * <p>\n" +
				" * DO NOT MODIFY.  Code has been automatically generated by {@link GenerateImplHessianBlobIntensity}.\n" +
				" * </p>\n" +
				" *\n" +
				" * @author Peter Abeles\n" +
				" */\n" +
				"public class "+className+" {\n\n");
	}

	private void printFuncs( AutoTypeImage derivType )
	{
		this.derivType = derivType;

		printDeterminant();
		printTrace();
	}

	private void printDeterminant() {
		String derivName = derivType.getImageName();
		String sumType = derivType.getSumType();
		String bitWise = derivType.getBitWise();

		out.print("\tpublic static void determinant( ImageFloat32 featureIntensity , "+derivName+" hessianXX, "+derivName+" hessianYY , "+derivName+" hessianXY ) {\n" +
				"\t\tfinal int width = hessianXX.width;\n" +
				"\t\tfinal int height = hessianXX.height;\n" +
				"\n" +
				"\t\tif( featureIntensity == null ) {\n" +
				"\t\t\tfeatureIntensity = new ImageFloat32(width,height);\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tfor( int y = 0; y < height; y++ ) {\n" +
				"\t\t\tint indexXX = hessianXX.startIndex + y*hessianXX.stride;\n" +
				"\t\t\tint indexYY = hessianYY.startIndex + y*hessianYY.stride;\n" +
				"\t\t\tint indexXY = hessianXY.startIndex + y*hessianXY.stride;\n" +
				"\n" +
				"\t\t\tint indexInten = featureIntensity.startIndex + y*featureIntensity.stride;\n" +
				"\n" +
				"\t\t\tfor( int x = 0; x < width; x++ ) {\n" +
				"\t\t\t\t"+sumType+" dxx = hessianXX.data[indexXX++]"+bitWise+";\n" +
				"\t\t\t\t"+sumType+" dyy = hessianYY.data[indexYY++]"+bitWise+";\n" +
				"\t\t\t\t"+sumType+" dxy = hessianXY.data[indexXY++]"+bitWise+";\n" +
				"\n" +
				"\t\t\t\tfeatureIntensity.data[indexInten++] = dxx*dyy - dxy*dxy;\n" +
				"\t\t\t}\n" +
				"\t\t}\n" +
				"\t}\n\n");
	}

	private void printTrace() {
		String derivName = derivType.getImageName();
		String sumType = derivType.getSumType();
		String bitWise = derivType.getBitWise();

		out.print("\tpublic static void trace( ImageFloat32 featureIntensity , "+derivName+" hessianXX, "+derivName+" hessianYY ) {\n" +
				"\t\tfinal int width = hessianXX.width;\n" +
				"\t\tfinal int height = hessianXX.height;\n" +
				"\n" +
				"\t\tif( featureIntensity == null ) {\n" +
				"\t\t\tfeatureIntensity = new ImageFloat32(width,height);\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tfor( int y = 0; y < height; y++ ) {\n" +
				"\t\t\tint indexXX = hessianXX.startIndex + y*hessianXX.stride;\n" +
				"\t\t\tint indexYY = hessianYY.startIndex + y*hessianYY.stride;\n" +
				"\n" +
				"\t\t\tint indexInten = featureIntensity.startIndex + y*featureIntensity.stride;\n" +
				"\n" +
				"\t\t\tfor( int x = 0; x < width; x++ ) {\n" +
				"\t\t\t\t"+sumType+" dxx = hessianXX.data[indexXX++]"+bitWise+";\n" +
				"\t\t\t\t"+sumType+" dyy = hessianYY.data[indexYY++]"+bitWise+";\n" +
				"\n" +
				"\t\t\t\tfeatureIntensity.data[indexInten++] = Math.abs(dxx + dyy);\n" +
				"\t\t\t}\n" +
				"\t\t}\n" +
				"\t}\n\n");
	}

	public static void main( String args[] ) throws FileNotFoundException {
		GenerateImplHessianBlobIntensity gen = new GenerateImplHessianBlobIntensity();
		gen.generate();
	}
}