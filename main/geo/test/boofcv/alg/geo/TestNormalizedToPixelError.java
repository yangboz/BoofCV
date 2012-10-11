package boofcv.alg.geo;

import georegression.geometry.RotationMatrixGenerator;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.se.Se3_F64;
import org.ejml.data.DenseMatrix64F;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestNormalizedToPixelError {

	// intrinsic camera parameters
	double fx = 60;
	double skew = 0.01;
	double fy = 80;

	DenseMatrix64F K = new DenseMatrix64F(3,3,true,fx,skew,200,0,fy,150,0,0,1);
	Se3_F64 worldToCamera;

	// normalized image coordinates
	Point2D_F64 n0,n1;
	// pixel coordinates
	Point2D_F64 p0,p1;

	public TestNormalizedToPixelError() {
		worldToCamera = new Se3_F64();
		RotationMatrixGenerator.eulerXYZ(0.05, 0.05, -0.02, worldToCamera.R);
		worldToCamera.getT().set(0.3,-0.02,0.05);

		Point3D_F64 X = new Point3D_F64(0.1,-0.02,3);

		n0 = PerspectiveOps.renderPixel(worldToCamera,null,X);
		p0 = PerspectiveOps.renderPixel(worldToCamera,K,X);

		p1 = p0.copy();
		p1.x += 0.2;
		p1.y += -0.3;

		n1 = PerspectiveOps.convertPixelToNorm(K,p1.x,p1.y,null);
	}

	@Test
	public void usingPixels() {
		NormalizedToPixelError alg = new NormalizedToPixelError(fx,fy,skew);

		double expected = p0.distance2(p1);
		double found = alg.errorSq(n0,n1);

		assertEquals(expected,found,1e-8);
	}

	@Test
	public void usingDoubles() {
		NormalizedToPixelError alg = new NormalizedToPixelError(fx,fy,skew);

		double expected = p0.distance2(p1);
		double found = alg.errorSq(n0.x,n0.y,n1.x,n1.y);

		assertEquals(expected, found, 1e-8);
	}
}